package com.rptsec.translator;

import com.rptsec.translator.SplCmdParsers.*;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ToSimpleSql implements IConvertToSql {
  private static class SqlKeywords {
    private static String JOIN = "JOIN";
    private static String INNER_JOIN = "INNER JOIN";
    private static String RIGHT_JOIN = "RIGHT JOIN";
    private static String LEFT_JOIN = "LEFT JOIN";
    private static String WHERE = "WHERE ";
    private static String FROM = "FROM ";
    private static String SELECT = "SELECT ";
  }

  // Map of { fieldName -> fieldName, isDistinct, aggrExpr, alias }
  //
  // queryBuilder()
  //  .forDataSource()
  //  .withSelect(fieldName, tableName)
  //  .withDistinctSelect(colName)
  //  .withAggregationFor(colName, aggrExpr, alias, groupBy)
  //  .withAlias(colName, alias)
  //  .filteredBy(whereClause)
  //  .filteredByUsingSubQuery(fieldName, subQuery)
  //  .sortedBy
  //  .joinedWithTable()  ==> The moment we do join.. the first table is built, and joined with this table.
  //  .joinedWithQuery()  ==>  -- same as above --
  //  .build()
  //
  //  After a query is built, if filteredBy() or withSelect() is invoked then that clause will be rebuilt.
  //  Thinking about it, you can keep re-building each time a SplCommand is encountered.

  private Logger logger = LogManager.getLogger(ToSimpleSql.class);
  private Boolean inError = false;
  private String tableName = null;
  private String whereClause = null;
  private HashMap<String, Boolean> selectionWithDistinct = new HashMap<>();
  private HashMap<String, String> aliasMap = new HashMap<>();
  private HashMap<String, Boolean> sortMap = new HashMap<>();  // True for Ascending.
  // i.e. col -> avg(col).
  private HashMap<String, String> groupOperationColMap = new HashMap<>();
  private String groupByCol = null;
  private int headCount = 0;
  // Currently only one join info is held.
  // This needs to be refactored to hold more joins that are piped to the main query.
  private SplTranslator.JoinInfo joinInfo = null;
  private SplTranslator.ParsedSpl parsedSpl = null;

  public ToSimpleSql(SplTranslator.ParsedSpl parsedSpl) {
    this.parsedSpl = parsedSpl;
    buildSql(this.parsedSpl);
  }

  @Override
  public String convert() {
    return generateSimpleSqlQuery();
  }

  private void buildSql(SplTranslator.ParsedSpl parsedSpl) {

    for (SplTranslator.SplCommand command: parsedSpl.commands) {
      switch (command.commandType) {
        case kSearch:
          SearchCmd searchCmd = (SearchCmd) command;
          this.addTableName(searchCmd.getTableName(), searchCmd.getTableType());
          this.addWhereClause(searchCmd.getTableName(), searchCmd.getWhereClauseStr());
              //SearchCmd.addExplicitAND(searchCmd.getWhereClauseStr()));
          break;

        case kStats:
          StatsCmd statsCmd = (StatsCmd) command;
          String groupByCol = (statsCmd.getAggrByList() == null) ? "" : statsCmd.getAggrByList().get(0);
          String aggrFunc = statsCmd.getAggregationsList().get(0)[0];
          String colInAggrFunc = statsCmd.getAggregationsList().get(0)[1];
          logger.info(aggrFunc + " " + colInAggrFunc + " " + groupByCol);
          this.addGroupByDetails(groupByCol, aggrFunc, colInAggrFunc);
          break;

        case kHead:
          HeadCmd headCmd = (HeadCmd) command;
          int headCount = headCmd.getCount();
          this.addTopLimit(headCount);
          break;

        case kSort:
          SortCmd sortCmd = (SortCmd) command;
          List<AbstractMap.SimpleImmutableEntry<String, Boolean>> sortColsWithOrder =
              sortCmd.getSortColsWithOrder();
          String sortCol = sortColsWithOrder.get(0).getKey();
          boolean sortAsc = sortColsWithOrder.get(0).getValue();
          this.addOrderBy(sortCol, sortAsc);
          break;

        case kDedup:
          DedupCmd dedupCmd = (DedupCmd) command;
          for (String dedupField: dedupCmd.getFields()) {
            this.updateDistinctFieldSelection(dedupField);
          }
          break;

        case kRename:
          RenameCmd renameCmd = (RenameCmd) command;
          String origColName = renameCmd.getRenamedFields().get(0).getKey();
          String newColName = renameCmd.getRenamedFields().get(0).getValue();
          for (AbstractMap.SimpleImmutableEntry<String, String> fields: renameCmd.getRenamedFields()) {
            this.addAlias(fields.getKey(), fields.getValue());
            logger.info("Add an alias: |" + fields.getKey() + "|" + fields.getValue() + "|");
          }
          break;

        case kFields:
          FieldsCmd fieldsCmd = (FieldsCmd) command;
          List<String> selectCols = fieldsCmd.getFields();
          this.updateFieldSelection(selectCols);
          if (fieldsCmd.getPlusMinus() != null) {
            // TODO: Handle +/- sign with fields command.
          }
          break;

        case kJoin:
          this.joinWith( ((JoinCmd) command).getJoinInfo() );
          break;
      }

      if (this.hasError()) {
        logger.error("Error while building Sql Query.");
        return;
      }
    }

  }

  private void addTableName(String tableName, String tableType) {
    if (this.tableName != null) {
      this.inError = true;
      return;
    }

    this.tableName = tableName;
  }

  private void addWhereClause(String unusedTableName, String whereCondStr) {
    if (tableName == null) {
      this.inError = true;
      return;
    }

    if (this.whereClause == null) {
      this.whereClause = whereCondStr;
      return;
    }

    this.whereClause = "(" + this.whereClause +")" + " AND " + whereCondStr;
  }

  private boolean hasError() {
    return this.inError;
  }

  private void addTopLimit(int headCount) {
    this.headCount = headCount;
  }

  private void addOrderBy(String sortCols, boolean sortAsc) {
    this.sortMap.put(sortCols, sortAsc);
    // TODO: Also add as a "SELECT" if it does not already exist.
    if (!this.selectionWithDistinct.containsKey(sortCols)) {
      this.selectionWithDistinct.put(sortCols, false);
    }
  }

  private void updateDistinctFieldSelection(String distinctCol) {
    if (tableName == null) {
      this.inError = true;
      return;
    }

    this.selectionWithDistinct.put(distinctCol, true);
  }

  private void addAlias(String origColName, String newColName) {
    if (tableName == null) {
      this.inError = true;
      return;
    }

    if (this.aliasMap.containsKey(origColName)) {
      this.inError = true;
      return;
    }

    this.aliasMap.put(origColName, newColName);
  }

  private void updateFieldSelection(List<String> selectCols) {
    if (tableName == null) {
      this.inError = true;
      return;
    }

    for (String colName: selectCols) {
      if (this.selectionWithDistinct.containsKey(colName)) {
        continue;
      }
      this.selectionWithDistinct.put(colName, false);
    }
  }

  // Needed when the search command is a subsearch that is used in where clause of outer query.
  public List<String> getProjectedFieldsAfterAliasing() {
    List<String> list = new ArrayList<>();
    if ((this.selectionWithDistinct == null) || this.selectionWithDistinct.isEmpty()) {
      return list;
    }

    for (String field: this.selectionWithDistinct.keySet()) {
      if (this.aliasMap.containsKey(field)) {
        list.add(this.aliasMap.get(field));
      } else {
        list.add(field);
      }
    }
    return list;
  }

  private String makeSelectClause() {
    // Form the Selection clause.
    List<String> items = new LinkedList<>();
    for (Map.Entry<String, Boolean> entries : this.selectionWithDistinct.entrySet()) {
      String colName = entries.getKey();
      String colNameText = colName;
      if (this.groupOperationColMap.containsKey(colName)) {
        colNameText = this.groupOperationColMap.get(colName);
      }
      if (this.aliasMap.containsKey(colName)) {
        colNameText = colName + " AS " + this.aliasMap.get(colName);
      }
      if (entries.getValue()) {
        colNameText = "DISTINCT " + colName;
      }
      items.add(colNameText);
    }

    String selectionClause = (items.size() == 0) ? "*" : String.join(", ", items);
    String topClauseText = "";
    if (this.headCount != 0) {
      topClauseText = "TOP(" + Integer.toString(this.headCount) + ") ";
    }

    return SqlKeywords.SELECT + topClauseText + selectionClause + " ";
  }

  private String makeSortByClause() {
    String sortClauseText = "";
    if (this.sortMap.size() > 0) {
      String[] sortArr = this.sortMap.entrySet()
          .stream()
          .map((x) -> (x.getValue() ? x.getKey() : x.getKey() + " " + "desc"))
          .collect(Collectors.toList())
          .stream()
          .toArray(String[]::new);
      sortClauseText = " ORDER BY " + String.join(", ", sortArr);
    }
    return sortClauseText;
  }

  public String generateSimpleSqlQuery() {
    String whereClause = (this.whereClause == null) ? "" : (" " + SqlKeywords.WHERE + this.whereClause);
    String groupByText = (this.groupOperationColMap.size() == 0) ? "" : (" GROUP BY " + this.groupByCol);

    String query = makeSelectClause() + SqlKeywords.FROM + this.tableName + whereClause + groupByText + makeSortByClause();
    if (this.joinInfo != null) {
      String joinTypeStr = " " + getSqlJoinTypeStr(joinInfo.joinType) + " ";
      String joinCondition = joinInfo.onCondition;
      String joinDataSet = (joinInfo.queryBasedDataset == null) ? joinInfo.tableName : ("(" + joinInfo.queryBasedDataset + ")");
      String leftTableWithAlias = (joinInfo.leftAlias.isEmpty() ? this.tableName : (this.tableName + " " + joinInfo.leftAlias));
      String rightTableWithAlias = (joinInfo.rightAlias.isEmpty() ? joinDataSet : (joinDataSet + " " + joinInfo.rightAlias));
      query = makeSelectClause()
          + SqlKeywords.FROM + leftTableWithAlias + joinTypeStr + rightTableWithAlias + " ON " + joinCondition
          + whereClause + groupByText + makeSortByClause();
    }

    return query;
  }

  private String getSqlJoinTypeStr(String joinType) {
    switch (joinType) {
      case "":
        return SqlKeywords.JOIN;

      case "inner":
        return SqlKeywords.INNER_JOIN;

      case "outer":
        return SqlKeywords.RIGHT_JOIN;

      case "left":
        return SqlKeywords.LEFT_JOIN;
    }
    return null;
  }


  private void addGroupByDetails(String groupByCol, String aggrFunc, String colInAggrFunc) {
    this.groupByCol = groupByCol;
    this.groupOperationColMap.put(colInAggrFunc, aggrFunc);
    if (!this.selectionWithDistinct.containsKey(colInAggrFunc)) {
      this.selectionWithDistinct.put(colInAggrFunc, false);
    }
  }

  private void joinWith(SplTranslator.JoinInfo joinInfo) {
    this.joinInfo = joinInfo;
  }

}
