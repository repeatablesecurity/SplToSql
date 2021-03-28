package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StatsCmd extends SplTranslator.SplCommand {
  private static String KEYWORD = "STATS";
  private static String KEYWORD_LOWERCASE = "stats";
//  private String aggregationExpr = null;
//  private String fieldInAggregationExpr = null;
//  private String aggregateByField = null;
  private List<String[]> aggregationsList = null;
  private List<String> aggrByList = null;

  private StatsCmd() {
    commandType = SplTranslator.SplCommandType.kStats;
  }
//
//  public String getAggregationExpr() {
//    return aggregationExpr;
//  }
//
//  public String getFieldInAggregationExpr() {
//    return fieldInAggregationExpr;
//  }
//
//  public String getAggregateByField() {
//    return aggregateByField;
//  }


  public List<String[]> getAggregationsList() {
    return aggregationsList;
  }

  public List<String> getAggrByList() {
    return aggrByList;
  }

  public static StatsCmd parse(String cmdStr) {
    cmdStr = cmdStr.trim();
    if (!cmdStr.startsWith(KEYWORD) && !cmdStr.startsWith(KEYWORD_LOWERCASE)) {
      return null;
    }

    SplLexer splLexer = new SplLexer((CharStreams.fromString(cmdStr)));
    SplParser splParser = new SplParser((new CommonTokenStream(splLexer)));
    SplParser.StatsCommandContext statsCommandContext = splParser.statsCommand();
    List<String[]> aggrList = new LinkedList<>();
    for (SplParser.StatsAggregationExprContext exprContext : statsCommandContext.statsAggregationExpr()) {
      String aggrExprWithCol = exprContext.statsAggrOpWithCol().getText();
      String fieldInAggrExpr = exprContext.statsAggrOpWithCol().field().getText();
      String alias = (exprContext.field() == null) ? null : exprContext.field().getText();
      aggrList.add(new String[]{aggrExprWithCol, fieldInAggrExpr, alias});
    }
    LinkedList<String> aggrByFieldList = new LinkedList<>();
    for (SplParser.FieldContext fc : statsCommandContext.fieldList().field()) {
      aggrByFieldList.add(fc.getText());
    }

    StatsCmd statsCmd = new StatsCmd();
    statsCmd.aggrByList = aggrByFieldList;
    statsCmd.aggregationsList = aggrList;
    return statsCmd;
    // ---
//    String colsStr = cmdStr.substring(KEYWORD.length());
//    List<String> kv = Arrays.asList(colsStr.split("\\s*BY\\s*"));
//    if (kv.size() != 2) {
//      return null;
//    }
//
//    statsCmd.aggregationExpr = kv.get(0).trim();
//    statsCmd.aggregateByField = kv.get(1).trim();
//    statsCmd.fieldInAggregationExpr = statsCmd.aggregationExpr.substring(
//        statsCmd.aggregationExpr.indexOf("(") + 1, statsCmd.aggregationExpr.indexOf(")"));
//    return statsCmd;
  }
}
