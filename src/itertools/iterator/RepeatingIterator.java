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
 * Repeats the same item forever.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class RepeatingIterator<E> implements Iterator<E> {
  private E item;

  public RepeatingIterator(E item) {
    this.item = item;
  }

  /**
   * Always returns true.
   */
  public boolean hasNext() {
    return true;
  }

  /**
   * Always returns the same item.
   */
  public E next() {
    return item;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

}
