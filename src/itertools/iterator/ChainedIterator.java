package itertools.iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Chains a set of iterators together.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class ChainedIterator<E> implements Iterator<E> {
  private LinkedList<Iterator<E>> iterator_chain = new LinkedList<Iterator<E>>();
  private E next;

  public ChainedIterator(Collection<Iterator<E>> iterators) {
    for (Iterator<E> i : iterators) {
      iterator_chain.add(i);
    }
    advanceIterator();
  }

  public boolean hasNext() {
    return (next != null);
  }

  public E next() {
    if (next == null) {
      throw new NoSuchElementException();
    }
    return advanceIterator();
  }

  public void remove() {
    if (iterator_chain.isEmpty()) {
      throw new IllegalStateException();
    }
    iterator_chain.getFirst().remove();
  }

  /**
   * Advances to the next item, returning the current.
   * 
   * @return
   */
  private E advanceIterator() {
    E last = next;
    while (!iterator_chain.isEmpty()) {
      if (iterator_chain.getFirst().hasNext()) {
        next = iterator_chain.getFirst().next();
        return last;
      } else {
        iterator_chain.removeFirst();
      }
    }
    next = null;
    return last;
  }
}
