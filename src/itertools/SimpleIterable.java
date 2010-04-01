package itertools;

import java.util.Iterator;

/**
 * A simple Iterable wrapper anound an iterator, to help in enhanced for loops.
 * Note: this is a single-use Iterable: subsequent calls to {@link #iterator()}
 * will return the same, possibly empty, iterator object.
 * 
 * @author jelsas
 * 
 * @param <T>
 */
public class SimpleIterable<T> implements Iterable<T> {
  Iterator<T> it;

  public SimpleIterable(Iterator<T> it) {
    this.it = it;
  }

  public Iterator<T> iterator() {
    return it;
  }
}
