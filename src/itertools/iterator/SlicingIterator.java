/*
   Copyright 2010 Jonathan L. Elsas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package itertools.iterator;

import java.util.Iterator;

/**
 * An iterator that slices (selects a subset of) the provided iterator.
 * Eqiuvalent to iterating over an array as follows:
 * 
 * <code>
 * Object[] x = // initialize
 * for (int i = sliceStart; i < sliceStop && i < x.length; i+=sliceBy) {
 *   Object next = x[i];
 * }
 * </code>
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public class SlicingIterator<E> implements Iterator<E> {
  EnumeratingIterator<E> it;
  int sliceStart = 0, sliceStop = Integer.MAX_VALUE, sliceBy = 1;
  private E next = null;

  /**
   * Create a SlicingIterator.
   * 
   * @param it
   *          The underlying iterator
   * @param sliceStart
   *          The offset to start returning elements. <tt>sliceStart &lt;=0</tt>
   *          starts at the beginning of the iterator.
   * @param sliceStop
   *          The offset to stop returning elements. <tt>sliceStop &lt; 0</tt>
   *          stops at the end of the iterator.
   * @param sliceBy
   *          Step size. <tt>sliceBy &lt;=0</tt> equivalent to specifying
   *          <tt>sliceBy=1</tt>.
   */
  public SlicingIterator(Iterator<E> it, int sliceStart, int sliceStop,
      int sliceBy) {
    this.it = new EnumeratingIterator<E>(it);
    this.sliceStart = (sliceStart >= 0) ? sliceStart : 0;
    this.sliceStop = (sliceStop >= 0) ? sliceStop : Integer.MAX_VALUE;
    this.sliceBy = (sliceBy > 0) ? sliceBy : 1;

    if (this.sliceStart > this.sliceStop)
      throw new IllegalArgumentException("sliceStart > sliceStop");

    next = advanceNext();
  }

  public boolean hasNext() {
    return (next != null);
  }

  public E next() {
    E tmp = next;
    next = advanceNext();
    return tmp;
  }

  public void remove() {
    // TODO: can we support this? Not with the current implementation of caching
    // the next element.
    throw new UnsupportedOperationException();
  }

  private E advanceNext() {
    if (!it.hasNext()) return null;

    E tmp = it.next();

    // advance past sliceStart
    while (it.currentCount() < sliceStart && it.hasNext()) {
      tmp = it.next();
    }

    // skip by sliceBy, making sure we don't proceed past sliceStop
    while ((it.currentCount() - sliceStart) % sliceBy != 0 && it.hasNext()) {
      tmp = it.next();
    }

    if (it.currentCount() >= sliceStop) {
      tmp = null;
    }

    return tmp;
  }
}
