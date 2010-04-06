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
import java.util.NoSuchElementException;

/**
 * An iterator that groups items based on the group(E, E) function. Like unix's
 * 'uniq' or python's itertools.groupby. As opposed to {@link GroupingIterator},
 * this iterator doesn't read the entire group into memory.
 * 
 * Caveat: Calling hasNext() or next() on this object invalidates the previously
 * return group iterator, advancing the underlying iterator to the next group
 * (or the end if there are no more groups).
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public abstract class LazyGroupingIterator<E> implements Iterator<Iterator<E>> {
  PushbackIterator<E> it;
  TakewhileIterator<E> currentGroup;

  /**
   * Creates a new GroupingIterator wrapping it.
   * 
   * @param it
   */
  public LazyGroupingIterator(Iterator<E> it) {
    this.it = new PushbackIterator<E>(it);
  }

  private void consumeCurrentGroup() {
    if (currentGroup != null) {
      while (currentGroup.hasNext()) {
        currentGroup.next();
      }

      // push the last one back into the iterator
      E last = currentGroup.getLast();
      if (last != null) {
        it.pushBack(currentGroup.getLast());
      }

      // and null the current group iterator so we don't do this again
      currentGroup = null;
    }
  }

  /**
   * Note: this invalides the previously returned group's iterator.
   */
  public boolean hasNext() {
    consumeCurrentGroup();
    return it.hasNext();
  }

  /**
   * Returns the next group of data as an iterator. Note: this invalides the
   * previously returned group's iterator.
   */
  public Iterator<E> next() {
    if (!hasNext()) { throw new NoSuchElementException(); }

    final E first = it.next();
    it.pushBack(first);
    currentGroup = new TakewhileIterator<E>(it) {
      @Override
      public boolean condition(E item) {
        // Ensure no groups are empty.
        if (item == first) {
          return true;
        } else {
          return group(first, item);
        }
      }
    };
    return currentGroup;
  }

  public void remove() {
    // Pushback iterator doesn't support this, so it'll throw an Exception
    it.remove();
  }

  /**
   * The function to define the groups in this iterator.
   * 
   * @param e1
   *          The first item to compare.
   * @param e2
   *          The second item to compare.
   * @return true if the two items should be in the same group, false otherwise.
   */
  public abstract boolean group(E e1, E e2);

}
