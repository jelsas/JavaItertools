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

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Cycles through the provided iterator forever.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class CyclingIterator<E> implements Iterator<E> {
  private LinkedList<E> storage = new LinkedList<E>();
  private Iterator<E> it;
  private int cycles = 0;

  public CyclingIterator(Iterator<E> it) {
    this.it = it;
  }

  /**
   * Always returns true.
   */
  public boolean hasNext() {
    return true;
  }

  public E next() {
    if (it.hasNext()) {
      if (cycles == 0) {
        E next = it.next();
        storage.add(next);
        return next;
      } else {
        return it.next();
      }
    } else {
      ++cycles;
      it = storage.iterator();
      return it.next();
    }
  }

  public void remove() {
    it.remove();
  }

  public int getNumCycles() {
    return cycles;
  }

}
