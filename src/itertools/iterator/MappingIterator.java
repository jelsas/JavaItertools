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
 * Wraps an interator producing I's and produces O's
 * 
 * @author jelsas
 * 
 * @param <I>
 *          Input type
 * @param <O>
 *          Output type
 */
public abstract class MappingIterator<I, O> implements Iterator<O> {
  private Iterator<I> in;

  public MappingIterator(Iterator<I> in) {
    this.in = in;
  }

  public boolean hasNext() {
    return in.hasNext();
  }

  public O next() {
    return map(in.next());
  }

  public void remove() {
    in.remove();
  }

  /**
   * Function mapping I to O.
   * 
   * @param in
   *          Input
   * @return Output
   */
  public abstract O map(I in);

}
