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

public class FilteringIteratorTest {
  String[] data = { "one", "two", "three", "four" };
  Iterator<String> it;

  @Before
  public void setUp() throws Exception {
    it = Arrays.asList(data).iterator();
  }

  @Test
  public void testFilteringIterator() {
    it = new FilteringIterator<String>(it) {
      @Override
      public boolean keep(String object) {
        return object.indexOf('o') >= 0;
      }
    };

    String[] expected = { data[0], data[1], /* data[2] filtered */data[3] };
    for (int i = 0; i < expected.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(expected[i], it.next());
    }
    assertFalse(it.hasNext());
  }

}
