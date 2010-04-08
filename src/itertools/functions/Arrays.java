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
package itertools.functions;

/**
 * A few static utility classes to deal with iterators over array types. Note:
 * to get these correctly generic-ized, call Arrays.<E>method().
 * 
 * @author jelsas
 * 
 */
public class Arrays {

  public static <E> Grouper<E[]> selectGrouper(int idx) {
    return new _groupbyElement<E>(idx);
  }

  public static <E> Mapper<E[], E> selectMapper(int idx) {
    return new _elementMapper<E>(idx);
  }

  private static class _groupbyElement<E> implements Grouper<E[]> {
    int idx;

    public _groupbyElement(int idx) {
      this.idx = idx;
    }

    public boolean group(E[] e1, E[] e2) {
      return e1[idx].equals(e2[idx]);
    }
  }

  private static class _elementMapper<E> implements Mapper<E[], E> {
    int idx;

    public _elementMapper(int idx) {
      this.idx = idx;
    }

    public E map(E[] input) {
      return input[idx];
    }

  }

}
