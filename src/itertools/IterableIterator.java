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
package itertools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * A simple Iterable wrapper around an iterator, to help in enhanced for loops
 * and chaining together {@link Itertools} calls. Note: this is a single-use
 * Iterable: subsequent calls to {@link #iterator()} will return the same,
 * possibly empty, iterator object.
 * 
 * @author jelsas
 * 
 * @param <T>
 */
public class IterableIterator<T> implements Iterable<T>, Iterator<T> {
  Iterator<T> it;

  public IterableIterator(T[] a) {
    it = Arrays.asList(a).iterator();
  }

  public IterableIterator(Collection<T> c) {
    it = c.iterator();
  }

  public IterableIterator(Iterable<T> it) {
    this.it = it.iterator();
  }

  public IterableIterator(Iterator<T> it) {
    this.it = it;
  }

  public Iterator<T> iterator() {
    return it;
  }

  public boolean hasNext() {
    return it.hasNext();
  }

  public T next() {
    return it.next();
  }

  public void remove() {
    it.remove();
  }
}
