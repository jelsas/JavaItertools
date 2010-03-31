package itertools.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a wrapper around iterator-like objects that only provide a getNext()
 * method which returns null when iteration is done.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public abstract class IteratorWrapper<E> implements Iterator<E> {

  private E next = null;

  public E next() {
    if (hasNext()) {
      E tmp = next;
      next = null;
      return tmp;
    } else {
      throw new NoSuchElementException();
    }
  }

  public boolean hasNext() {
    if (next == null) {
      next = getNext();
    }

    return (next != null);
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  public abstract E getNext();
}
