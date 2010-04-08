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
package itertools.functions;

import java.io.IOException;
import java.util.Iterator;

import itertools.IBuilder;
import itertools.iterator.FileLineIterator;

/**
 * A few static utility {@link Mapper} objects for operating on files.
 * 
 * @author jelsas
 * 
 */
public class Files {
  /**
   * Opens a filename String as a file.
   */
  public static final Mapper<String, Iterable<String>> OPEN = new _openFiles();

  private static class _openFiles implements Mapper<String, Iterable<String>> {
    public Iterable<String> map(String input) {
      try {
        return new IBuilder<String>(new FileLineIterator(input));
      } catch (IOException e) {
        return null;
      }
    }
  }

}
