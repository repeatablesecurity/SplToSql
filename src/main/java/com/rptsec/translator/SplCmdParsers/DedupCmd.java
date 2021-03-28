package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.LinkedList;
import java.util.List;

public class DedupCmd extends SplTranslator.SplCommand {
  private static String KEYWORD = "DEDUP";
  private static String KEYWORD_LOWERCASE = "dedup";
  private List<String> fields = null;
  private DedupCmd() {
    this.commandType = SplTranslator.SplCommandType.kDedup;
  }

  public List<String> getFields() {
    return fields;
  }

  public static DedupCmd parse(String cmdStr) {
    cmdStr = cmdStr.trim();
    if (!cmdStr.startsWith(KEYWORD) && !cmdStr.startsWith(KEYWORD_LOWERCASE)) {
      return null;
    }

    SplLexer splLexer = new SplLexer(CharStreams.fromString(cmdStr));
    SplParser splParser = new SplParser(new CommonTokenStream(splLexer));
    SplParser.DedupCommandContext dedupCommandContext = splParser.dedupCommand();
    List<String> fieldsToDedup = new LinkedList<>();
    for (SplParser.FieldContext fieldContext: dedupCommandContext.fieldList().field()) {
      fieldsToDedup.add(fieldContext.getText().trim());
    }

    DedupCmd dedupCmd = new DedupCmd();
    dedupCmd.fields = fieldsToDedup;
    return dedupCmd;
  }
}
