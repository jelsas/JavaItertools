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

import java.util.Iterator;

import itertools.Itertools;

import org.junit.Test;

public class PeekableIteratorTest {

  @Test
  public void testPeekableIterator() {
    Iterator<Integer> it = Itertools.count();
    it = Itertools.slice(it, 0, 100, 1).iterator();
    PeekableIterator<Integer> pit = new PeekableIterator<Integer>(it);

    int i;
    for (i = 0; i < 100; ++i) {
      assertTrue(pit.hasNext());
      assertEquals(i, pit.peek().intValue());
      assertEquals(i, pit.next().intValue());
      if (i < 100 - 1) assertEquals(i + 1, pit.peek().intValue());
    }

    assertFalse(pit.hasNext());
  }

}
