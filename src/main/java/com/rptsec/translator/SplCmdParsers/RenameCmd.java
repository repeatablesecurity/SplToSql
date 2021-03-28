package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;

public class RenameCmd extends SplTranslator.SplCommand {
  public static String KEYWORD = "RENAME";
  public static String KEYWORD_LOWERCASE = "rename";
  private List<AbstractMap.SimpleImmutableEntry<String, String>> renamedFields = null;

  private RenameCmd() {
    this.commandType = SplTranslator.SplCommandType.kRename;
  }

  public List<AbstractMap.SimpleImmutableEntry<String, String>> getRenamedFields() {
    return renamedFields;
  }

  public static RenameCmd parse(String cmdStr) {
    cmdStr = cmdStr.trim();
    if (!cmdStr.startsWith(KEYWORD) && !cmdStr.startsWith(KEYWORD_LOWERCASE)) {
      return null;
    }

    SplLexer splLexer = new SplLexer(CharStreams.fromString(cmdStr));
    SplParser splParser = new SplParser(new CommonTokenStream(splLexer));
    SplParser.RenameCommandContext renameCommandContext = splParser.renameCommand();

    RenameCmd renameCmd = new RenameCmd();
    renameCmd.renamedFields = new LinkedList<>();
    for (SplParser.RenamedFieldContext fc: renameCommandContext.renamedField()) {
      renameCmd.renamedFields.add(
          new AbstractMap.SimpleImmutableEntry(fc.sourceField.getText(), fc.targetField.getText()));
    }
    return renameCmd;
  }
}
