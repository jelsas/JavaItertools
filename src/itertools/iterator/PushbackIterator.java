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

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * An iterator that allows pushing elements back into the iteration.
 * @author jelsas
 *
 * @param <E>
 */
public class PushbackIterator<E> implements Iterator<E> {
  private Deque<E> q = new LinkedList<E>();
  Iterator<E> it;

  public PushbackIterator(Iterator<E> it) {
    this.it = it;
  }

  public boolean hasNext() {
    if (q.isEmpty()) {
      return it.hasNext();
    } else {
      return true;
    }
  }

  public E next() {
    if (q.isEmpty()) {
      return it.next();
    } else {
      return q.pop();
    }
  }

  /**
   * Add this element back to the iterator.
   * @param e
   */
  public void pushBack(E e) {
    q.push(e);
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

}
