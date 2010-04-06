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

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class MappingIteratorTest {
  String[] data = { "ONE", "TWO", "three", "FoUr" };
  Iterator<String> it = null;

  @Before
  public void setUp() throws Exception {
    it = Arrays.asList(data).iterator();
  }

  @Test
  public void testMappingIterator() {
    it = new MappingIterator<String, String>(it) {
      @Override
      public String map(String in) {
        return in.toLowerCase();
      }
    };

    for (int i = 0; i < data.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(data[i].toLowerCase(), it.next());
    }
    assertFalse(it.hasNext());
  }

}
