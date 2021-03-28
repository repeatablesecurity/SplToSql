package com.rptsec;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.rptsec.translator.SplTranslator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App 
{

  public static void main( String[] args ) throws IOException {
    String splQueryTxt = String.join(" ", Files.readAllLines(Paths.get(args[0])));
    System.out.println("Translating : " + splQueryTxt);
    SplTranslator splTranslator = new SplTranslator();
    System.out.println(splTranslator.translate(splQueryTxt));
  }
}
