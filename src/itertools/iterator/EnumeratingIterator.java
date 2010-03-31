package itertools.iterator;

import java.util.Iterator;

/**
 * Iterator that keeps a count of the number of times next() has been called
 * (starting at zero). {@link #currentCount()} retrieves this count.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class EnumeratingIterator<E> implements Iterator<E> {
  private Iterator<E> it;
  private int count = -1;

  public EnumeratingIterator(Iterator<E> it) {
    this.it = it;
  }

  public boolean hasNext() {
    return it.hasNext();
  }

  public E next() {
    ++count;
    return it.next();
  }

  public void remove() {
    it.remove();
  }

  public int currentCount() {
    return count;
  }

}
