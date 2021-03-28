package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class FirstCmdParser {
  private static Logger logger = LogManager.getLogger(FirstCmdParser.class);
  private static int kTableNameIndex = 0;
  private static int kTableTypeIndex = 1;
  private static int kWhereClauseIndex = 2;

  private static String[] getTokensTillClosingBracket(String[] tokens, int startingIndex) {
    int count = 1;
    int i;
    for (i = startingIndex + 1; i < tokens.length; ++i) {
      String t = tokens[i];
      if (t.compareTo("(") == 0) {
        count++;
      } else if (t.compareTo(")") == 0) {
        count--;
      }

      if (count == 0) {
        break;
      }
    }

    int tokensToInclude = i - startingIndex;
    String[] ret = new String[tokensToInclude];
    for (int j = 0; j < tokensToInclude; j++) {
      ret[j] = tokens[startingIndex + j];
    }
    return ret;
  }

  private static boolean isKv(String t) {
    int pos = t.indexOf("=");
    return  ((pos > 0) && (pos < (t.length() - 1) ));
  }

  private static String parseWhereCondition(String[] tokens, int startFrom) {
    String condition = null;
    boolean lastTokenIsOR = false;
    for (int i = startFrom; i < tokens.length; ++i) {
      String token = tokens[i];
      if (token.compareTo("OR") == 0) {
        lastTokenIsOR = true;
        continue;
      }

      if (token.compareTo("AND") == 0) {
        continue;
      }

      if (token.compareTo("(") == 0) {
        String[] t2 = getTokensTillClosingBracket(tokens, i + 1);
        String subCondition = parseWhereCondition(t2, 0);
        if (condition == null) {
          condition = "(" + subCondition + ")";
        } else {
          String op = (lastTokenIsOR ? "OR" : "AND");
          condition = condition + " " + op + " (" + subCondition + ")";
          lastTokenIsOR = false;
        }
        i += t2.length + 1;
        continue;
      }

      if (isKv(token)) {
        if (condition == null) {
          condition = token;
        } else {
          String op = (lastTokenIsOR ? "OR" : "AND");
          condition = condition + " " + op + " " + token;
          lastTokenIsOR = false;
        }
        continue;
      }

      // Log that there is an error.
      return null;
    }

    return condition;
  }

  private static String getTableType(String token) {
    if (token.contains("source=")) {
      return "source";
    }

    if (token.contains("sourcetype=")) {
      return "sourcetype";
    }

    return null;
  }

  private static String getTableName(String token, String tableType) {
    // logger.debug(token.trim());
    // logger.debug(token.substring(tableType.length() + 1));
    return token.substring(tableType.length() + 1);
  }

  public static SplTranslator.SplCommand tryParse(String cmdStr) {
    // Supports the following types:
    //  source=mytable
    //  source=mytable mycol=foo
    //  source=mytable mycol=foo mycol2=foo2
    //  source=mytable (mycolumn1=foo1 OR mycolumn2=foo2) mycol3=foo3
    //  source=mytable AND (mycolumn1=foo1 OR mycolumn2=foo2) AND mycol3=foo3
    //  All of above combo with "sourcetype=my*"
    //
    // Objective: Get (1) TableName
    //                (2) Where clauses
    // Parsing logic -
    //  First step - match for "source" and "sourcetype"
    //  (Then bucket conditions into simpleAndClauses, and bracketClauses.)
    //  Second step - Infer whether keyword or condition or bracket.
    //    If bracket - Infer condition tree from bracket
    //    Else infer keyword operator

    logger.info("Trying to parse the first command: " + cmdStr);
    String[] tokens = cmdStr.split("\\s+");
    // logger.debug("Splitting the first command: ");
    // logger.debug(Arrays.toString(tokens));
    String tableType = getTableType(tokens[0]);
    if (tableType == null) {
      return null;
    }

    // logger.info("Table type: " + tableType);
    String tableName = getTableName(tokens[0], tableType);
    if (tableName == null) {
      logger.error("Could not parse table name and/or table type.");
      return null;
    }

    // logger.debug("Table name: ", tableName);
    SplTranslator.SimpleSplCommand splCommand = new SplTranslator.SimpleSplCommand();
    splCommand.text = cmdStr;
    splCommand.commandType = SplTranslator.SplCommandType.kFirstCommand;
    splCommand.isStartingCommand = true;
    splCommand.tokens = new ArrayList<>(3);
    splCommand.tokens.add(kTableNameIndex, tableName);
    splCommand.tokens.add(kTableTypeIndex, tableType);

    if (tokens.length == 1) {
      return splCommand;
    }

    String whereCondStr = null;
    if (tokens[1].compareTo("AND") == 0) {
      whereCondStr = parseWhereCondition(tokens, 2);
    } else {
      whereCondStr = parseWhereCondition(tokens, 1);
    }

    if (whereCondStr == null) {
      logger.error("Could not parse whereCondition.");
      return null;
    }

    splCommand.tokens.add(kWhereClauseIndex, whereCondStr);

    return splCommand;
  }

  /**
   *
   * @param tokens
   * @return
   */
  public static String getTableName(List<String> tokens) {
    return tokens.get(kTableNameIndex);
  }

  /**
   *
   * @param tokens
   * @return
   */
  public static String getTableType(List<String> tokens) {
    return tokens.get(kTableTypeIndex);
  }

  public static String getWhereClauseStr(List<String> tokens) {
    return tokens.get(kWhereClauseIndex);
  }
}
