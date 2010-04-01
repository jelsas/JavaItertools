package itertools.iterator;

import java.util.Collection;
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

  public MergingIterator(Collection<Iterator<E>> iterators,
      Comparator<? super E> comparator) {
    q = new PriorityQueue<QueueElement<E>>(iterators.size());
    for (Iterator<E> i : iterators) {
      if (i.hasNext()) q.add(new QueueElement<E>(i, comparator));
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
