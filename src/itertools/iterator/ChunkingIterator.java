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
 * An iterator that chunks elements into chunkSize iterators.
 * 
 * @author jelsas
 * 
 */
public class ChunkingIterator<E> extends LazyGroupingIterator<E> {
  EnumeratingIterator<E> it;
  private int chunkSize;

  /**
   * @param it
   *          The underlying iterator
   * @param chunkSize
   *          The maximum number of items in the chunk
   */
  public ChunkingIterator(Iterator<E> it, int chunkSize) {
    super(new EnumeratingIterator<E>(it));
    this.it = (EnumeratingIterator<E>) super.it.it;
    this.chunkSize = chunkSize;
  }

  @Override
  public boolean group(E e1, E e2) {
    return (it.currentCount() % chunkSize != 0);
  }

}
