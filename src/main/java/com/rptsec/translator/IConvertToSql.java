package com.rptsec.translator;

import java.util.List;

/**
 * Interface implemented by convertors that take in ParsedSpl and output
 * the equivalent in SQL or its variant.
 */
public interface IConvertToSql {
  // Convert the SPL command into desired format.
  public String convert();
  // Get the list of projected fields in the query.
  public List<String> getProjectedFieldsAfterAliasing();
}
