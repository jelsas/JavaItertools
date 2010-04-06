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
