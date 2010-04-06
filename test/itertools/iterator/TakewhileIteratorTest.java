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

import org.junit.Test;

public class TakewhileIteratorTest {

  @Test
  public void testTakewhileIterator() {
    Iterator<Integer> it = new TakewhileIterator<Integer>(
        new CountingIterator()) {
      @Override
      public boolean condition(Integer item) {
        return item < 10;
      }
    };
    
    for (int i = 0; i < 10; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
    assertFalse(it.hasNext());
  }

}
