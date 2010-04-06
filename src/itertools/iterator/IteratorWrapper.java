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
 * Provides a wrapper around iterator-like objects that only provide a getNext()
 * method which returns null when iteration is done.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public abstract class IteratorWrapper<E> implements Iterator<E> {

  private E next = null;

  public E next() {
    if (hasNext()) {
      E tmp = next;
      next = null;
      return tmp;
    } else {
      throw new NoSuchElementException();
    }
  }

  public boolean hasNext() {
    if (next == null) {
      next = getNext();
    }

    return (next != null);
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }

  public abstract E getNext();
}
