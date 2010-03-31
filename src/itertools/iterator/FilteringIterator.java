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
