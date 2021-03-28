package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class HeadCmd  extends SplTranslator.SplCommand{
  public static String KEYWORD = "HEAD";
  public static String KEYWORD_LOWERCASE = "head";
  private int count = 10;  // default.

  private HeadCmd() {
    this.commandType = SplTranslator.SplCommandType.kHead;
  }

  public int getCount() {
    return count;
  }

  public static HeadCmd parse(String cmdStr) {
    cmdStr = cmdStr.trim();
    if (!cmdStr.startsWith(KEYWORD) && !cmdStr.startsWith(KEYWORD_LOWERCASE)) {
      return null;
    }

    SplLexer splLexer = new SplLexer(CharStreams.fromString(cmdStr));
    SplParser splParser = new SplParser(new CommonTokenStream(splLexer));
    SplParser.HeadCommandContext headCommandContext = splParser.headCommand();

    HeadCmd headCmd = new HeadCmd();
    if (headCommandContext.count != null) {
      headCmd.count = Integer.parseInt(headCommandContext.count.getText());
    }

    return headCmd;
  }
}
