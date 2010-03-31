package itertools.iterator;

import java.util.Iterator;

public class CountingIterator implements Iterator<Integer> {
  private int count;

  public CountingIterator() {
    this(0);
  }

  public CountingIterator(int start) {
    count = start;
  }

  /**
   * Always returns true.
   */
  public boolean hasNext() {
    return true;
  }

  public Integer next() {
    return new Integer(count++);
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  /**
   * An unboxed version of {@link #next()}.
   * 
   * @return
   */
  public int nextInt() {
    return count++;
  }

}
