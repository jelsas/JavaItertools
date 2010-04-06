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

import itertools.IBuilder;
import itertools.Itertools;

import org.junit.Test;

/**
 * @author jelsas
 * 
 */
public class ChunkingIteratorTest {

  /**
   * Test method for
   * {@link itertools.iterator.ChunkingIterator#ChunkingIterator(java.util.Iterator, int)}
   * .
   */
  @Test
  public void testChunkingIterator() {
    Iterator<Integer> count = Itertools.count();
    count = Itertools.slice(count, 0, 100, 1);

    int chunkSize = 7;
    ChunkingIterator<Integer> chunks = new ChunkingIterator<Integer>(count,
        chunkSize);

    int lastValue = -1;
    while (chunks.hasNext()) {
      Iterator<Integer> chunk = chunks.next();
      int thisChunkSize = 0;
      while (chunk.hasNext()) {
        ++thisChunkSize;
        int next = chunk.next().intValue();
        assertEquals(++lastValue, next);
      }
      assertTrue(thisChunkSize <= chunkSize);
    }

    assertEquals(lastValue, 99);
  }

}
