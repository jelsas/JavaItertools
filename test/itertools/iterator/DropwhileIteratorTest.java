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
import itertools.IBuilder;

import org.junit.Test;

public class DropwhileIteratorTest {

  @Test
  public void testDropwhileIterator() {
    // Create an ibuilder that counts to 100
    IBuilder<Integer> ibuilder = new IBuilder<Integer>(new CountingIterator());
    ibuilder = ibuilder.slice(0, 100, 1);

    // Dropwhile less than 80
    DropwhileIterator<Integer> it = new DropwhileIterator<Integer>(ibuilder
        .iterator()) {
      @Override
      public boolean condition(Integer item) {
        return item < 80;
      }
    };

    int i;
    for (i = 80; i < 100; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
    assertEquals(i, 100);
    assertFalse(it.hasNext());

  }

}
