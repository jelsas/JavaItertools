package itertools.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Returns elements while the implemented condition(E) method returns true. When
 * iteration finishes, the element for which the condition failed to hold is
 * availabe via the getLast() method.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public abstract class TakewhileIterator<E> implements Iterator<E> {
  Iterator<E> it;
  E next = null;
  boolean done = false;

  public TakewhileIterator(Iterator<E> it) {
    this.it = it;
    advanceNext();
  }

  public boolean hasNext() {
    return (!done && next != null);
  }

  public E next() {
    if (done || next == null) { throw new NoSuchElementException(); }
    E tmp = next;
    advanceNext();
    return tmp;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  public E getLast() {
    return next;
  }

  /**
   * Returns null if there are no more elements in the interator or if the
   * contition fails. Sets next to the next element in it, or null if the
   * iterator is empty.
   * 
   * @return
   */
  private E advanceNext() {
    if (!it.hasNext()) {
      next = null;
      return null;
    }

    next = it.next();
    if (!condition(next)) {
      done = true;
      return null;
    } else {
      return next;
    }
  }

  public abstract boolean condition(E item);

}
