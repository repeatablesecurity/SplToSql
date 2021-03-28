package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;

public class SortCmd extends SplTranslator.SplCommand {
  private static String KEYWORD = "SORT";
  private static String KEYWORD_LOWERCASE = "sort";
  private List<AbstractMap.SimpleImmutableEntry<String, Boolean>> sortColsWithOrder = null;

  private SortCmd() {
    this.commandType = SplTranslator.SplCommandType.kSort;
  }

  public List<AbstractMap.SimpleImmutableEntry<String, Boolean>> getSortColsWithOrder() {
    return sortColsWithOrder;
  }

  public static SortCmd parse(String cmdStr) {
    cmdStr = cmdStr.trim();
    if (!cmdStr.startsWith(KEYWORD) && !cmdStr.startsWith(KEYWORD_LOWERCASE)) {
      return null;
    }

    SplLexer splLexer = new SplLexer((CharStreams.fromString(cmdStr)));
    SplParser splParser = new SplParser((new CommonTokenStream(splLexer)));
    SplParser.SortCommandContext sortCommandContext = splParser.sortCommand();
    SplParser.SortByClauseContext sortByClauseContext = sortCommandContext.sortByClause();

    SortCmd sortCmd = new SortCmd();
    sortCmd.sortColsWithOrder = new LinkedList<>();
    for (SplParser.FieldWithOperatorContext fc: sortByClauseContext.fieldWithOperator()) {
      Boolean isPlus = true;
      if (fc.plusMinusOperator() != null) {
        if (fc.plusMinusOperator().getText().contentEquals("-")) {
          isPlus = false;
        }
      }
      String field = fc.field().getText();
      sortCmd.sortColsWithOrder.add(new AbstractMap.SimpleImmutableEntry<>(field, isPlus));
    }

    return sortCmd;
  }
}
