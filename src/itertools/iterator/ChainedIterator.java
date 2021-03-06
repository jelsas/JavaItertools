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
import java.util.NoSuchElementException;

/**
 * Chains a set of iterators together.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class ChainedIterator<E> implements Iterator<E> {
  private LinkedList<Iterator<E>> iterator_chain = new LinkedList<Iterator<E>>();
  private E next;

  public ChainedIterator(Iterator<? extends Iterable<E>> iterators) {
    while (iterators.hasNext()) {
      Iterable<E> i = iterators.next();
      if (i == null) continue;
      iterator_chain.add(i.iterator());
    }
    advanceIterator();
  }

  public boolean hasNext() {
    return (next != null);
  }

  public E next() {
    if (next == null) { throw new NoSuchElementException(); }
    return advanceIterator();
  }

  public void remove() {
    // We can't suport this since its possible the last returned item is from an
    // interator we no longer have a handle on.
    throw new UnsupportedOperationException();
  }

  /**
   * Advances to the next item, returning the current.
   * 
   * @return
   */
  private E advanceIterator() {
    E last = next;
    while (!iterator_chain.isEmpty()) {
      if (iterator_chain.getFirst().hasNext()) {
        next = iterator_chain.getFirst().next();
        return last;
      } else {
        iterator_chain.removeFirst();
      }
    }
    next = null;
    return last;
  }
}
