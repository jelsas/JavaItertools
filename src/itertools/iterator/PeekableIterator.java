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
import java.util.NoSuchElementException;

/**
 * An iterator that lets you peek at the next element without acutally removing
 * it.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class PeekableIterator<E> implements Iterator<E> {
  private Iterator<E> it;
  private E next = null;

  public PeekableIterator(Iterator<E> it) {
    this.it = it;
  }

  public boolean hasNext() {
    if (next != null) return true;

    if (it.hasNext()) {
      next = it.next();
      return true;
    } else {
      return false;
    }
  }

  public E next() {
    if (next != null) {
      E tmp = next;
      next = null;
      return tmp;
    } else {
      return it.next();
    }
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns the next element without advancing the iterator.
   * 
   * @return The next element.
   */
  public E peek() {
    if (hasNext()) return next;

    throw new NoSuchElementException();
  }

}
