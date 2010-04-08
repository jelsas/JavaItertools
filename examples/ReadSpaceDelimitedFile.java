/*
   Copyright 2010 Jonathan L. Elsas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

import static itertools.Itertools.*;
import itertools.IBuilder;
import itertools.functions.Condition;
import itertools.functions.Strings;

/**
 * JavaItertools examples.
 * 
 * @author jelsas
 * 
 */
public class ReadSpaceDelimitedFile {

  /**
   * Reads a whitespace delimited file, skipping empty lines and comments
   * (starting with "#").
   * 
   * @param filename
   * @throws Exception
   */
  public static void run1(String filename) throws Exception {
    // Open the file, getting an iterator over lines
    IBuilder<String> lines = open(filename);

    // Strip leading/trailing whitespace
    lines = lines.map(Strings.TRIM);

    // Convert to lower-case
    lines = lines.map(Strings.LOWER);

    // Skip empth lines and lines starting with "#". Check for null
    // in case something happened to the underlying file reader.
    lines = lines.filter(new Condition<String>() {
      public boolean condition(String line) {
        return (line != null && line.length() > 0 && line.charAt(0) != '#');
      }
    });

    // Split on whitespace
    // Note: this changes the type of IBuilder, now iterating over String[]
    // instead of String
    IBuilder<String[]> splitLines = lines.map(Strings.SPLIT);

    // Finally, iterate over these string arrays.
    // Note: Nothing is read from the file until this iteration starts.
    for (String[] s : splitLines) {
      // do something.
    }
  }

  /**
   * Same as {@link #run1(String)}, but more compactly.
   * 
   * @param filename
   * @throws Exception
   */
  public static void run2(String filename) throws Exception {

    // Define a condition for filtering comments & empty lines. Check for null
    // in case something happened to the underlying file reader.
    Condition<String> isContent = new Condition<String>() {
      public boolean condition(String line) {
        return (line != null && line.length() > 0 && line.charAt(0) != '#');
      }
    };

    // Open the file, strip whitespace, convert to lower case, filter comments &
    // empty lines, and split on whitespace all in one command
    Iterable<String[]> lines = open(filename).map(Strings.TRIM)
                                             .map(Strings.LOWER)
                                             .filter(isContent)
                                             .map(Strings.SPLIT);

    // Finally, iterate over these string arrays.
    // Note: Nothing is read from the file until this iteration starts.
    for (String[] s : lines) {
      // do something.
    }
  }
}
