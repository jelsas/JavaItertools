package itertools.iterator;

import java.util.Iterator;

/**
 * Repeats the same item forever.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class RepeatingIterator<E> implements Iterator<E> {
  private E item;

  public RepeatingIterator(E item) {
    this.item = item;
  }

  /**
   * Always returns true.
   */
  public boolean hasNext() {
    return true;
  }

  /**
   * Always returns the same item.
   */
  public E next() {
    return item;
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

}
