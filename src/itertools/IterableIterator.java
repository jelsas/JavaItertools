package itertools;

import java.util.Iterator;

/**
 * A simple Iterable wrapper around an iterator, to help in enhanced for loops
 * and chaining together {@link Itertools} calls. Note: this is a single-use
 * Iterable: subsequent calls to {@link #iterator()} will return the same,
 * possibly empty, iterator object.
 * 
 * @author jelsas
 * 
 * @param <T>
 */
public class IterableIterator<T> implements Iterable<T>, Iterator<T> {
  Iterator<T> it;

  public IterableIterator(Iterable<T> it) {
    this.it = it.iterator();
  }

  public IterableIterator(Iterator<T> it) {
    this.it = it;
  }

  public Iterator<T> iterator() {
    return it;
  }

  public boolean hasNext() {
    return it.hasNext();
  }

  public T next() {
    return it.next();
  }

  public void remove() {
    it.remove();
  }
}
