package com.rptsec.translator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

class SplTranslatorTest {
  Logger logger = LogManager.getLogger(SplTranslatorTest.class);

  String replaceNewlineWithSpace(String s) {
    StringBuilder builder = new StringBuilder(s);
    for (int i = 0; i < builder.length(); ++i) {
      if ((builder.charAt(i) == '\n')
          || (builder.charAt(i) == '\r')) {
        builder.setCharAt(i, ' ');
      }
    }
    return builder.toString();
  }

  @Test
  void splToSqlTestsUsingYAMLFile() {
    Yaml yaml = new Yaml();
    InputStream inputStream = this.getClass()
        .getClassLoader()
        .getResourceAsStream("splTests.yaml");
    Map<String, Object> contentMap = yaml.load(inputStream);

    logger.info("Loading testdata for: " + contentMap.get("desc"));
    ArrayList<Map<String, String>> testData = (ArrayList<Map<String, String>>) contentMap.get("testData");

    int count = 1;
    for (Map<String, String> t: testData) {
      if (t.containsKey("spl") && t.containsKey("sql")) {
        String splQuery = t.get("spl");
        String expectedSqlQuery = t.get("sql");
        if ((t.get("skip") != null)) {
          logger.info("Skipping this one -");
          logger.info(splQuery);
          continue;
        }
        String testLabel = (t.containsKey("label") ? t.get("label") : "");
        logger.info("Test number: #" + count + " " + testLabel + "\n" + splQuery.trim());
        SplTranslator splTr = new SplTranslator();
        assertEquals(expectedSqlQuery.trim(),
            replaceNewlineWithSpace(splTr.translate(splQuery)));
        logger.info("PASSED\n");
        count++;
      }
    }
  }

}

// https://erikbern.com/2018/08/30/i-dont-want-to-learn-your-garbage-query-language.html

