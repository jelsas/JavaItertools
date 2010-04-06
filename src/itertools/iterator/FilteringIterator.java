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
 * Wraps an iterator, only retaining elements for which {@link #keep(Object)}
 * returns true.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public abstract class FilteringIterator<E> implements Iterator<E> {
  private Iterator<E> it;
  private E next = null;

  public FilteringIterator(Iterator<E> it) {
    this.it = it;
    advanceNext();
  }

  public boolean hasNext() {
    return (next != null);
  }

  public E next() {
    if (next == null) {
      throw new NoSuchElementException();
    } else {
      return advanceNext();
    }
  }

  public void remove() {
    it.remove();
  }

  /**
   * Advances to the next item, returning the current.
   * 
   * @return
   */
  private E advanceNext() {
    E last = next;
    while (it.hasNext()) {
      next = it.next();
      if (keep(next)) {
        return last;
      }
    }
    next = null;
    return last;
  }

  public abstract boolean keep(E object);
}
