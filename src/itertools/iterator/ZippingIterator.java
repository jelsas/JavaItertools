package itertools.iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Wraps a few iterators, iterating over each in parallel. Note: all the wrapped
 * iterators must be of the same type. At each iteration, returns an array
 * containing the next element from each iterator, or null if that iterator has
 * reached the end. {@link ZippingIterator#hasNext()} returns true if at least
 * one of the underlying iterators returns true.
 * 
 * @author jelsas
 * 
 * @param <E>
 *          Type of objecs in each of the iterators.
 */
public class ZippingIterator<E> implements Iterator<List<E>> {
  List<Iterator<E>> iterators;
  ArrayList<E> internalStorage;

  public ZippingIterator(Collection<Iterator<E>> iterators) {
    this.iterators = new ArrayList<Iterator<E>>(iterators);
    internalStorage = new ArrayList<E>(iterators.size());
  }

  public boolean hasNext() {
    // see if at least one of the iterators has next.
    for (Iterator<E> it : iterators) {
      if (it.hasNext())
        return true;
    }
    return false;
  }

  public List<E> next() {
    internalStorage.clear();
    boolean found = false;
    for (int i = 0; i < iterators.size(); ++i) {
      if (iterators.get(i).hasNext()) {
        found = true;
        internalStorage.add(iterators.get(i).next());
      } else {
        internalStorage.add(null);
      }
    }

    if (!found) {
      throw new NoSuchElementException();
    } else {
      return Collections.unmodifiableList(internalStorage);
    }
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

}
