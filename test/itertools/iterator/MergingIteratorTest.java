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
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MergingIteratorTest {
  String[][] data = { { "a", "d", "g", "j" }, { "b", "e", "h", "k" },
      { "c", "f", "i", "l" }, { "m" } };
  List<Iterable<String>> iters;

  @Before
  public void setUp() throws Exception {
    iters = new ArrayList<Iterable<String>>();
    for (String[] s : data) {
      iters.add(Arrays.asList(s));
    }
  }

  @Test
  public void testMergingIterator() {
    // merged order should be lexical order of all the data.
    List<String> allData = new ArrayList<String>();
    for (String[] ss : data) {
      for (String s : ss) {
        allData.add(s);
      }
    }
    Collections.sort(allData, String.CASE_INSENSITIVE_ORDER);

    MergingIterator<String> mit = new MergingIterator<String>(iters,
        String.CASE_INSENSITIVE_ORDER);

    for (int i = 0; i < allData.size(); ++i) {
      assertTrue(mit.hasNext());
      String trueNext = allData.get(i);
      String mergedNext = mit.next();
      assertEquals(trueNext, mergedNext);
    }
    assertFalse(mit.hasNext());
  }

}
