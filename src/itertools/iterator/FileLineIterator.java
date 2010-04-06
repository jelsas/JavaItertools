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
package itertools.iterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Wraps a BufferedReader to provide iteration over the lines in the file.
 * 
 * @author jelsas
 * 
 */
public class FileLineIterator extends IteratorWrapper<String> {
  BufferedReader in;

  public FileLineIterator(String filename) throws IOException {
    in = new BufferedReader(new FileReader(filename));
  }

  public FileLineIterator(String filename, int bufferSize) throws IOException {
    in = new BufferedReader(new FileReader(filename), bufferSize);
  }

  /**
   * Returns the next line of the file or null if there are no more lines, or if
   * an error occurred reading the file. The file is close & cleaned up if an
   * error occurred or the end of the file is reached.
   */
  @Override
  public String getNext() {
    if (in == null) return null;
    String next;
    try {
      next = in.readLine();
    } catch (IOException e) {
      cleanup();
      next = null;
    }
    return next;
  }

  private void cleanup() {
    try {
      in.close();
    } catch (IOException e) {
      // ignore
    }
    in = null;
  }
}
