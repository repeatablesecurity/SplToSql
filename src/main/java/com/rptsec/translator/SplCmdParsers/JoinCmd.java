package com.rptsec.translator.SplCmdParsers;

import com.rptsec.translator.SplTranslator;
import com.rptsec.translator.splantlr.SplLexer;
import com.rptsec.translator.splantlr.SplParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class JoinCmd extends SplTranslator.SplCommand{
  private static String KEYWORD = "JOIN";
  private static String KEYWORD_LOWERCASE = "join";
  private SplTranslator.JoinInfo joinInfo = null;

  public SplTranslator.JoinInfo getJoinInfo() {
    return joinInfo;
  }

  private JoinCmd() {
    commandType = SplTranslator.SplCommandType.kJoin;
  }

  public static JoinCmd parse(String cmdStr, SplTranslator.SubsearchSplCommand subsearchSplCommand) {
    if (cmdStr.startsWith(KEYWORD) || cmdStr.startsWith(KEYWORD_LOWERCASE)) {
      SplLexer splLexer = new SplLexer((CharStreams.fromString(cmdStr)));
      SplParser splParser = new SplParser((new CommonTokenStream(splLexer)));
      SplParser.JoinStrContext joinStrContext = splParser.joinStr();

      JoinCmd joinCmd = new JoinCmd();
      SplTranslator.JoinInfo joinInfo = new SplTranslator.JoinInfo();
      joinInfo.joinType = joinStrContext.joinType().joinTypeValue().getText();
      if (subsearchSplCommand != null) {
        joinInfo.queryBasedDataset = subsearchSplCommand.sqlQuery;
      } else {
        joinInfo.tableName = joinStrContext.dataset().getText();
      }
      joinInfo.leftAlias = joinStrContext.joinConstraints().leftAlias().getText();
      joinInfo.rightAlias = joinStrContext.joinConstraints().rightAlias().getText();
      joinInfo.onCondition = joinStrContext.joinConstraints().joinEqualities().getText();
      joinCmd.joinInfo = joinInfo;
      return joinCmd;
    }

    return null;
  }

  public static JoinCmd parse(String cmdStr) {
    return parse(cmdStr, null);
  }
}
