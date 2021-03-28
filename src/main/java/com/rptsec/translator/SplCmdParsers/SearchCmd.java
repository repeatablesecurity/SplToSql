package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;

public class SearchCmd extends SplTranslator.SplCommand {
  private static String KEYWORD = "SEARCH";
  private static String KEYWORD_LOWERCASE = "search";
  private static Logger logger = LogManager.getLogger(SearchCmd.class);
  private String tableName;
  private String tableType;

  private String whereClauseStr;

  private SearchCmd() {
    commandType = SplTranslator.SplCommandType.kSearch;
  }

  public String getTableName() {
    return tableName;
  }

  public String getTableType() {
    return tableType;
  }

  public String getWhereClauseStr() {
    return whereClauseStr;
  }

  // Does not work with all cases. Issues are -
  //  - Replace space with AND in quoted strings
  //  - Does not handle ()
  public static String addExplicitAND(String whereClause) {
    String[] splitStrings = whereClause.split("\\s+");
    if (splitStrings.length == 2)  {
      return splitStrings[0] + " AND " + splitStrings[1];
    }
    ArrayList<String> strListWithAND = new ArrayList<>(splitStrings.length * 2);
    strListWithAND.add(splitStrings[0]);
    for (int i = 1; i < splitStrings.length; ++i) {
      if (isAndOr(splitStrings[i])) {
        strListWithAND.add(splitStrings[i]);
      } else {
        if (isAndOr(splitStrings[i-1])) {
          strListWithAND.add(splitStrings[i]);
        } else {
          strListWithAND.add("AND");
          strListWithAND.add(splitStrings[i]);
        }
      }
    }
    return String.join(" ", strListWithAND);
  }

  public static Boolean isAndOr(String str) {
    if ((str.compareTo("AND") == 0) ||
        (str.compareTo("OR") == 0) ||
        (str.contentEquals("or")) ||
        (str.contentEquals("and"))) {
      return true;
    }
    return false;
  }

  public static SearchCmd parse(String cmdStr) {
    return parse(cmdStr, false, null);
  }

  public static SearchCmd parse(String cmdStr,
                         boolean isStartOfQuery,
                         SplTranslator.SubsearchSplCommand subsearchSplCommand) {
    // If it is not the start of query then the cmdStr must start with 'SEARCH'.
    if (!isStartOfQuery && !cmdStr.trim().startsWith(KEYWORD)
        && !cmdStr.startsWith(KEYWORD_LOWERCASE)) {
      return null;
    }

    SplLexer splLexer = new SplLexer(CharStreams.fromString(cmdStr));
    SplParser splParser = new SplParser(new CommonTokenStream(splLexer));
    SplParser.SearchCommandContext searchCommandContext = splParser.searchCommand();

    String tableName = searchCommandContext.datasetSpecifier().datasetName.getText();
    String whereClauseStr = searchCommandContext.whereClause().getText();

    // searchCommandContext.whereClause().s

    whereClauseStr = fixWhereClause(whereClauseStr);
    logger.info(whereClauseStr);
    if (subsearchSplCommand != null) {
      String substitute = subsearchSplCommand.projectedField + " IN (" + subsearchSplCommand.sqlQuery +  ")";
      whereClauseStr = whereClauseStr.replace(SplTranslator.SUB_SEARCH_SUBSTITUTE_TEXT, substitute);
      logger.info("subs for substring: " + whereClauseStr);
    }

    // splCommand.isStartingCommand = isStartOfQuery;
    SearchCmd searchCmd = new SearchCmd();
    searchCmd.tableName = tableName;
    searchCmd.tableType = "source";
    searchCmd.whereClauseStr = whereClauseStr;

    return searchCmd;
  }

  private static String fixWhereClause(String w) {
    SplLexer splLexer = new SplLexer(CharStreams.fromString(w));
    SplParser splParser = new SplParser(new CommonTokenStream(splLexer));
    SplParser.ParseExpContext parseExpContext = splParser.parseExp();
    LinkedList<String> tokens = new LinkedList<>();
    for (ParseTree p: parseExpContext.children) {
      Helper.traverseTree(p, tokens);
    }
    LinkedList<String> fixedList = Helper.addExplicitAnd(tokens);
    fixedList = Helper.fixForLIKEOperator(fixedList);
    return String.join("", fixedList);
  }

  private static class Helper {
    enum TokenType {
      SubSearch,
      OpeningParens,
      ClosingParens,
      ArithmeticOperator,
      IdOrVal,
      BooleanLogicalOperator,
      Whitespace
    }

    public static void traverseTree(ParseTree pt, LinkedList<String> tokens) {
      if (pt.getChildCount() == 0) {
        if (pt.getText().contentEquals("<EOF>")) {
          return;
        }
        tokens.add(pt.getText());
        //logger.info(pt.getText());
        return;
      }
      for (int i = 0; i < pt.getChildCount(); ++i) {
        traverseTree(pt.getChild(i), tokens);
      }
    }

    private static TokenType getTokenType(String token) {
      if (token.contentEquals("[SS]")) {
        return TokenType.SubSearch;
      }
      if (token.contentEquals("(")) {
        return TokenType.OpeningParens;
      }
      if (token.contentEquals(")")) {
        return TokenType.ClosingParens;
      }
      if (token.contentEquals("OR") ||
          token.contentEquals("AND")) {
        return TokenType.BooleanLogicalOperator;
      }
      if (token.contentEquals("=")
          || token.contentEquals("!=")
          || token.contentEquals(">")
          || token.contentEquals("<")
          ) {
        return TokenType.ArithmeticOperator;
      }
      if (token.startsWith(" ")) {
        return TokenType.Whitespace;
      }

      return TokenType.IdOrVal;
    }

    public static LinkedList<String> fixForLIKEOperator(LinkedList<String> tokens) {
      LinkedList<String> newList = new LinkedList<>();
      TokenType prevTokenType = TokenType.Whitespace;
      String strToAdd = null;
      for (String t: tokens) {
        strToAdd = t;
        TokenType currTokenType = getTokenType(t);
        if ((prevTokenType == TokenType.ArithmeticOperator) && (currTokenType == TokenType.IdOrVal)) {
          // Curr token is RHS of a comparison.
          if (t.contains("*") || t.contains("?")) {
            if (newList.getLast().contentEquals("=")) {
              t.replaceAll("\\*", "%");
              newList.removeLast();
              newList.add(" LIKE ");
            }
          }
        }
        newList.add(strToAdd);
        prevTokenType = currTokenType;
      }
      return newList;
    }

    public static LinkedList<String> addExplicitAnd(LinkedList<String> tokens) {
      boolean expectingLogicalOperator = false;
      LinkedList<String> newList = new LinkedList<>();
      TokenType prevTokenType = TokenType.Whitespace;
      for (String t: tokens) {
        if (t.contentEquals("<EOF>")) {
          continue;
        }
        //logger.info(t + " " + getTokenType(t));
        TokenType currTokenType = getTokenType(t);

        if (((prevTokenType == TokenType.ArithmeticOperator) && (currTokenType == TokenType.IdOrVal))
            // a comparison
            || (currTokenType == TokenType.SubSearch)
            // Or a subsearch
        ) {
          // Curr token is RHS of a comparison.
          expectingLogicalOperator = true;
          newList.add(t);
          prevTokenType = currTokenType;
          continue;
        }

        if (currTokenType == TokenType.BooleanLogicalOperator) {
          expectingLogicalOperator = false;
          newList.add(t);
          prevTokenType = currTokenType;
          continue;
        }

        if (expectingLogicalOperator) {
          if ((currTokenType == TokenType.IdOrVal)
              || (currTokenType == TokenType.OpeningParens)){
            // Need to add an AND.
            newList.add("AND ");
            newList.add(t);
            expectingLogicalOperator = false;
            prevTokenType = currTokenType;
            continue;
          }
        }

        newList.add(t);
        prevTokenType = currTokenType;
      }

      // logger.info("New string: " + String.join("", newList));
      return newList;
    }

  }
}
