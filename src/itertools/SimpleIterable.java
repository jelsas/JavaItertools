package itertools;

import java.util.Iterator;

/**
 * A simple Iterable wrapper anound an iterator, to help in enhanced for loops.
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
