package itertools.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Drops elements while the implemented condition(E) method returns true.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public abstract class DropwhileIterator<E> implements Iterator<E> {
  PeekableIterator<E> it;

  public DropwhileIterator(Iterator<E> it) {
    this.it = new PeekableIterator<E>(it);
    // We just advance the iterator past the condition point
    while (this.it.hasNext() && condition(this.it.peek())) {
      this.it.next();
    }
  }

  public boolean hasNext() {
    return it.hasNext();
  }

  public E next() {
    return it.next();
  }

  public void remove() {
    // Peekable iterator doesn't support this, but let it throw the exception.
    it.remove();
  }

  public abstract boolean condition(E item);

}
