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

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Merges a set of iterators together according the comparator. It is assumed
 * that the provided iterators are already in sorted order.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class MergingIterator<E> implements Iterator<E> {
  private PriorityQueue<QueueElement<E>> q;

  public MergingIterator(Iterable<? extends Iterable<E>> iterators,
      Comparator<? super E> comparator) {
    q = new PriorityQueue<QueueElement<E>>();
    for (Iterable<E> i : iterators) {
      Iterator<E> ii = i.iterator();
      if (ii.hasNext()) q.add(new QueueElement<E>(ii, comparator));
    }
  }

  public boolean hasNext() {
    return !q.isEmpty();
  }

  public E next() {
    if (q.isEmpty()) throw new NoSuchElementException();
    QueueElement<E> e = q.poll();
    E next = e.it.next();
    if (e.it.hasNext()) q.add(e);
    return next;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  /**
   * Private utility class to provide custom comparator to the PriorityQueue.
   * 
   * @author jelsas
   * 
   * @param <F>
   */
  private class QueueElement<F> implements Comparable<QueueElement<F>> {
    PeekableIterator<F> it;
    Comparator<? super F> comp;

    public QueueElement(Iterator<F> it, Comparator<? super F> comp) {
      this.it = new PeekableIterator<F>(it);
      this.comp = comp;
    }

    public int compareTo(QueueElement<F> o) {
      return comp.compare(it.peek(), o.it.peek());
    }

  }
}
