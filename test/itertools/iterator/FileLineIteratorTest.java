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

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FileLineIteratorTest {
  File tempFile;
  String[] lines = { "one", "two", "three", "four" };

  @Before
  public void setUp() throws Exception {
    tempFile = File.createTempFile(this.getClass().getName(), null);
    tempFile.deleteOnExit();
    BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
    for (String l : lines) {
      out.write(l);
      out.newLine();
    }
    out.close();
  }

  @Test
  public void testFileLineIterator() {
    FileLineIterator it;
    try {
      it = new FileLineIterator(tempFile.getAbsolutePath());
    } catch (IOException e) {
      fail(e.getMessage());
      return;
    }

    for (int i = 0; i < lines.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(lines[i], it.next());
    }
    assertFalse(it.hasNext());
  }

}
