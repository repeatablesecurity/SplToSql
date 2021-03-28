package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.LinkedList;
import java.util.List;

public class FieldsCmd extends SplTranslator.SplCommand {
  private static String FIELDS = "FIELDS";
  private static String FIELDS_LOWERCASE = "fields";
  private List<String> fields = null;

  private String plusMinus = null;
  private FieldsCmd() {
    this.commandType = SplTranslator.SplCommandType.kFields;
  }

  public static FieldsCmd parse(String cmdStr) {
    cmdStr = cmdStr.trim();
    if (!cmdStr.startsWith(FIELDS) && !cmdStr.startsWith(FIELDS_LOWERCASE)) {
      return null;
    }

    SplLexer splLexer = new SplLexer(CharStreams.fromString(cmdStr));
    SplParser splParser = new SplParser(new CommonTokenStream(splLexer));
    SplParser.FieldsCommandContext fieldsCommandContext = splParser.fieldsCommand();
    FieldsCmd fieldsCmd = new FieldsCmd();
    fieldsCmd.fields = new LinkedList<>();
    for (SplParser.ProjectionFieldContext pfc: fieldsCommandContext.projectionFieldList().projectionField()) {
      fieldsCmd.fields.add(pfc.getText());
    }
    // TODO: fieldsCommandContext.fieldsPlusMinus().getText()
    if (fieldsCommandContext.fieldsPlusMinus() != null) {
      fieldsCmd.plusMinus = fieldsCommandContext.fieldsPlusMinus().getText();
    }
    return fieldsCmd;
  }

  public List<String> getFields() {
    return fields;
  }

  public String getPlusMinus() {
    return plusMinus;
  }

}
