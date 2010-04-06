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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ChainedIteratorTest {
  String[][] data = { { "a", "b", "c" }, { "1", "2", "3" },
      { "w", "x", "y", "z" } };
  List<Iterator<String>> iters;

  @Before
  public void setUp() throws Exception {
    iters = new ArrayList<Iterator<String>>(data.length);
    for (int i = 0; i < data.length; ++i) {
      iters.add(Arrays.asList(data[i]).iterator());
    }
  }

  @Test
  public void testChainedIterator() {
    ChainedIterator<String> it = new ChainedIterator<String>(iters);
    for (int row = 0; row < data.length; ++row) {
      for (int col = 0; col < data[row].length; ++col) {
        assertTrue("it.hasNext() at element : " + data[row][col], it.hasNext());
        assertEquals(data[row][col], it.next());
      }
    }
    assertFalse(it.hasNext());
  }

}
