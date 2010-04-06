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

import org.junit.Test;

public class CountingIteratorTest {

  @Test
  public void testCountingIterator() {
    CountingIterator it = new CountingIterator();
    for (int i = 0; i < 10; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
  }

  @Test
  public void testCountingIteratorInt() {
    int start = 100;
    CountingIterator it = new CountingIterator(start);
    for (int i = start; i < start + 10; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
  }

}
