package com.rptsec.translator;

import com.rptsec.translator.SplCmdParsers.*;

import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Translate SPL to another query language like SQL, or variants of SQL.
 * Usage:
 *    SplTranslator splTr = new SplTranslator();
 *    String sqlQuery = splTr.translate(splCommandStr);
 */
public class SplTranslator {

  /**
   * Used to specify the output type of SPL Translation.
   */
  public enum SplConvertorType { kSimpleSql, kBigQuery }

  /**
   * Denotes the type of SPL command. Eg- SORT, FIELD, etc.
   */
  public enum SplCommandType {
    kFirstCommand,
    kStats,
    kFields,
    kRename,
    kSort,
    kDedup,
    kHead,
    kLookUp,  // https://answers.splunk.com/answers/588630/understanding-the-lookup-command.html
    kJoin,
    kSearch
  }

  public static class ParsedSpl {
    List<SplCommand> commands;
    String text;
  }

  public static class SplCommand {
    // public SplQuery subQuery;
    public String text;  // v2
    public SplCommandType commandType;  // v2
    public boolean isStartingCommand = false;
  }

  public static class SimpleSplCommand extends SplCommand {
    public List<String> tokens;
  }

  public static class SubsearchSplCommand extends SplCommand {
    public String sqlQuery;
    public String projectedField;
  }

  public static class JoinInfo {
    public String tableName;
    public String queryBasedDataset;
    public String leftAlias;
    public String rightAlias;
    public String onCondition;
    public String joinType;
  }

  public static final String SUB_SEARCH_SUBSTITUTE_TEXT = "[SS]";

  private Logger logger = LogManager.getLogger(SplTranslator.class);
  private SplConvertorType convertorType;

  public SplTranslator() {
    this(SplConvertorType.kSimpleSql);
  }

  public SplTranslator(SplConvertorType convertorType) {
    this.convertorType = convertorType;
  }


  public String translate(String splCommandWithPipes) {
    ParsedSpl parsedSpl = parseSpl(splCommandWithPipes);
    IConvertToSql convertor = null;
    if (convertorType == SplConvertorType.kSimpleSql) {
      convertor = new ToSimpleSql(parsedSpl);
    }
    if (convertorType == SplConvertorType.kBigQuery) {
      convertor = new ToBigQuery();
    }

    return convertor.convert();
  }

  public ParsedSpl parseSpl(String splCommandWithPipes) {
    List<SplCommand> splCommandList = parseSplQueryText(splCommandWithPipes);
    if (splCommandList == null) {
      logger.error("Parsing error");
      return null;
    }
    ParsedSpl parsedSpl = new ParsedSpl();
    parsedSpl.text = splCommandWithPipes;
    parsedSpl.commands = splCommandList;
    return parsedSpl;
  }

  private List<SplCommand> parseSplQueryText(String splQueryRawText) {

    // NOTE: Below is mostly an obsolete comment. Get rid of it.
    // The limitation with below regex splitting is that it will not split based on
    // '(' or ')'.
    // So the SPL query should contain space before and after a brace - '(' or ')'
    // Eg: "source=foobar user=rohit ( id>10 OR id=1 )
    // String[] tokens = splQueryRawText.split("\\s+");

    List<SplCommand> splCommandList = new LinkedList<>();
    int currCommandStartPos = 0;
    StringBuilder currCommand = null;
    int subsearchEndIndex = -1, subsearchStartIndex = -1;
    String subsearchStr = "";
    for (int i = 0; i < splQueryRawText.length(); ++i) {
      char c = splQueryRawText.charAt(i);
      switch (c) {
        case '"':
          // Go till end of quote.
          i = findEndOfString(splQueryRawText, i+1);
          // TODO: check for i == -1
          if (i == -1) {
            return null;  // TODO: check for cleanup.
          }
          break;

        case '|':
          // Marks the end of current command.
          currCommand = new StringBuilder();
          if (subsearchEndIndex != -1) {
            currCommand.append(splQueryRawText, currCommandStartPos, subsearchStartIndex);  // note end index is exclusive.
            currCommand.append(SUB_SEARCH_SUBSTITUTE_TEXT);
            currCommand.append(splQueryRawText, subsearchEndIndex + 1, i);
            subsearchStr = splQueryRawText.substring(subsearchStartIndex + 1, subsearchEndIndex);
            logger.info("subsearch command found : " + subsearchStr);
          } else {
            currCommand.append(splQueryRawText, currCommandStartPos, i);
          }
          // Create the SPL command.
          SplCommand splCommand = splCommandFromText(currCommand.toString(), (currCommandStartPos == 0),
                subsearchCommandFromText(subsearchStr));
          if (splCommand == null) {
            logger.error("Could not parse the SPL command from String.");
            return null;
          } else {
            splCommandList.add(splCommand);
          }
          currCommandStartPos = i + 1;
          subsearchEndIndex = subsearchStartIndex = -1;
          subsearchStr = "";
          break;

        case '[':
          // Marks the start of subsearch.
          subsearchStartIndex = i;
          subsearchEndIndex = findEndofSubsearch(splQueryRawText, i+1);
          i = subsearchEndIndex;
          break;
      }
    }

    // The last command in the pipe is not created yet. We need to create it.
    if (currCommandStartPos != splQueryRawText.length()) {
      currCommand = new StringBuilder();
      if (subsearchEndIndex != -1) {
        currCommand.append(splQueryRawText, currCommandStartPos, subsearchStartIndex);  // note end index is exclusive.
        currCommand.append(SUB_SEARCH_SUBSTITUTE_TEXT);
        currCommand.append(splQueryRawText, subsearchEndIndex + 1, splQueryRawText.length());
        subsearchStr = splQueryRawText.substring(subsearchStartIndex + 1, subsearchEndIndex);
      } else {
        currCommand.append(splQueryRawText, currCommandStartPos, splQueryRawText.length());
      }
      // Create the SPL command.
      SplCommand splCommand = splCommandFromText(currCommand.toString(), (currCommandStartPos == 0),
          subsearchCommandFromText(subsearchStr));
      if (splCommand == null) {
        logger.error("Could not parse the SPL command from String.");
        return null;
      } else {
        splCommandList.add(splCommand);
      }
    }

    return splCommandList;
  }

  private SubsearchSplCommand subsearchCommandFromText(String subsearchStr) {
    if ((subsearchStr == null) || subsearchStr.isEmpty()) {
      return null;
    }

    ParsedSpl parsedSpl = parseSpl(subsearchStr);
    IConvertToSql subsearchConvertor = null;
    if (this.convertorType == SplConvertorType.kSimpleSql) {
      subsearchConvertor = new ToSimpleSql(parsedSpl);
    } else if (this.convertorType  == SplConvertorType.kBigQuery) {
      subsearchConvertor = new ToBigQuery();
    } else {
      logger.error("Unsupported output format: " + this.convertorType);
    }

    String convertedQueryForSubSearch =  subsearchConvertor.convert();

    SubsearchSplCommand subsearchSplCommand = new SubsearchSplCommand();
    List<String> projectedFields = subsearchConvertor.getProjectedFieldsAfterAliasing();
    subsearchSplCommand.projectedField = projectedFields.get(0);  // Choose first and ignore the rest.
    subsearchSplCommand.sqlQuery = convertedQueryForSubSearch;
    logger.info("Subsearch query: " + subsearchSplCommand.sqlQuery);
    logger.info("Subsearch projection: " + subsearchSplCommand.projectedField);
    return subsearchSplCommand;
  }

  private SplCommand splCommandFromText(String text, boolean isStartOfQueryText,
                                        SubsearchSplCommand subsearchSplCommand) {
    SplCommand splCommand;
    if (isStartOfQueryText) {
      splCommand = SearchCmd.parse(text.trim(), true, subsearchSplCommand);
    } else {
      splCommand = parseSplCommandStr(text.trim(), subsearchSplCommand);
    }
    return splCommand;
  }

  private int findEndofSubsearch(String text, int start) {
    int nestingCounter = 1;
    for (int i = start; i < text.length(); ++i) {
      char c = text.charAt(i);
      if (c == '"') {
        i = findEndOfString(text, i);
        continue;
      }
      if (c == '[') {
        nestingCounter++;
      }
      if (c == ']') {
        nestingCounter--;
        if (nestingCounter == 0) {
          return i;
        }
      }
    }
    return -1;
  }

  private int findEndOfString(String text, int start) {
    for (int i = start; i < text.length(); ++i) {
      char c = text.charAt(i);
      if (c == '"') {
        return i;
      }
    }
    return -1;
  }

  private SplCommand parseSplCommandStr(String cmdStr, SubsearchSplCommand subsearchSplCommand) {
    // logger.debug("Parsing non-first SPL command: " + cmdStr);
    // Try each command parse one by one.
    SplCommand splCommand = null;
    splCommand = FieldsCmd.parse(cmdStr);
    if (splCommand != null) {
      return splCommand;
    }

    splCommand = RenameCmd.parse(cmdStr);
    if (splCommand != null) {
      logger.info(((RenameCmd)splCommand).getRenamedFields().get(0).getValue());
      return splCommand;
    }

    splCommand = StatsCmd.parse(cmdStr);
    if (splCommand != null) {
      return splCommand;
    }

    splCommand = SortCmd.parse(cmdStr);
    if (splCommand != null) {
      return splCommand;
    }

    splCommand = HeadCmd.parse(cmdStr);
    if (splCommand != null) {
      return splCommand;
    }

    splCommand = DedupCmd.parse(cmdStr);
    if (splCommand != null) {
      return splCommand;
    }

    splCommand = JoinCmd.parse(cmdStr, subsearchSplCommand);
    if (splCommand != null) {
      return splCommand;
    }

    return null;
  }

}

// NOTES:
//
// Definitions:
// 1. Generating commands : A search command that generates events from one or more indexes
//    without transforming them. (i.e. It fetches information from the indexes, without any transformations)
//    Eg - search, metadata, loadjob, inputcsv, inputlookup, dbinspect, datamodel, pivot, tstats, eventcount.
//    https://docs.splunk.com/Splexicon:Generatingcommand
//
// Subsearches
// The first command in a subsearch must be "generating command".
// Two main uses
//  a) Parameterize one search, using the output of another search.
//  b) Run a separate search and add the output to the first search using the append command.
// "append"/"appendcols" and "join" can also accept subsearches else it is used only as an argument to "search".
