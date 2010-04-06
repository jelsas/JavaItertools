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
  private Iterator<E> it;

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
