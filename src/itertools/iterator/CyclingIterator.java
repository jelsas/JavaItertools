package itertools.iterator;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Cycles through the provided iterator forever.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class CyclingIterator<E> implements Iterator<E> {
  private LinkedList<E> storage = new LinkedList<E>();
  private Iterator<E> it;
  private int cycles = 0;

  public CyclingIterator(Iterator<E> it) {
    this.it = it;
  }

  /**
   * Always returns true.
   */
  public boolean hasNext() {
    return true;
  }

  public E next() {
    if (it.hasNext()) {
      if (cycles == 0) {
        E next = it.next();
        storage.add(next);
        return next;
      } else {
        return it.next();
      }
    } else {
      ++cycles;
      it = storage.iterator();
      return it.next();
    }
  }

  public void remove() {
    it.remove();
  }

  public int getNumCycles() {
    return cycles;
  }

}
