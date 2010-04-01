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

  public E peek() {
    if (hasNext()) return next;

    throw new NoSuchElementException();
  }

}
