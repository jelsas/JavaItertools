package itertools.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An iterator that groups items based on the group(E, E) function. Like unix's
 * 'uniq' or python's itertools.groupby
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public abstract class GroupingIterator<E> implements Iterator<Iterator<E>> {
  private E current = null;
  private ArrayList<E> internalStorage = new ArrayList<E>();
  Iterator<E> it;
  int maxGroupSize = Integer.MAX_VALUE;

  /**
   * Creates a new GroupingIterator wrapping it. Note: during iterations an
   * entire group will be stored in memory. See
   * {@link #GroupingIterator(Iterator, int)} to limit the group size.
   * 
   * @param it
   */
  public GroupingIterator(Iterator<E> it) {
    this(it, Integer.MAX_VALUE);
  }

  /**
   * Creates a new GroupingIterator wrapping it. Limits the size of the group to
   * maxGroupSize elements. This parameter is useful when wrapping disk-backed
   * iterators to avoid reading large groups into memory.
   * 
   * @param it
   * @param maxGroupSize
   *          The maximum size of a group.
   */
  public GroupingIterator(Iterator<E> it, int maxGroupSize) {
    this.it = it;
    if (maxGroupSize > 0) {
      this.maxGroupSize = maxGroupSize;
    }
    if (it.hasNext()) {
      current = it.next();
    }
  }

  public boolean hasNext() {
    return (current != null || it.hasNext());
  }

  /**
   * Returns the next group of data as a list. Important Notes:
   * <ol>
   * <li>Calling this method consumes the underlying iterator</li>
   * <li>The returned list is an unmodifyable view of the internal buffered
   * storage. The next call to next_as_list() or next() will change the contents
   * of this underlying storage. To retain a view of this data, a copy must be
   * made</li>
   * </ol>
   */
  public List<E> next_as_list() {
    if (current == null) {
      throw new NoSuchElementException();
    }
    internalStorage.clear();
    // add the first element
    internalStorage.add(current);
    E next = null;
    while (it.hasNext()) {
      next = it.next();
      if (internalStorage.size() >= maxGroupSize) {
        break;
      }

      if (group(current, next)) {
        // we're grouping these two, so add it to our list.
        internalStorage.add(next);
        // and null it to indicate we've returned it.
        next = null;
      } else {
        // next holds a valid element, which hasn't been returned.
        break;
      }
    }
    current = next;
    return Collections.unmodifiableList(internalStorage);
  }

  /**
   * Returns the next group of data as an iterator. Note: this consumes the
   * unerlying iterator.
   */
  public Iterator<E> next() {
    return next_as_list().iterator();
  }

  public void remove() {
    it.remove();
  }

  public abstract boolean group(E e1, E e2);
}
