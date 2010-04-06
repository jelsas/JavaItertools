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

/**
 * Iterator that keeps a count of the number of times next() has been called
 * (starting at zero). {@link #currentCount()} retrieves this count.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class EnumeratingIterator<E> implements Iterator<E> {
  private Iterator<E> it;
  private int count = -1;

  public EnumeratingIterator(Iterator<E> it) {
    this.it = it;
  }

  public boolean hasNext() {
    return it.hasNext();
  }

  public E next() {
    ++count;
    return it.next();
  }

  public void remove() {
    it.remove();
  }

  public int currentCount() {
    return count;
  }

}
