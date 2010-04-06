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
 * Interface providing a {@link #group(Object, Object)} function.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public interface Grouper<E> {
  /**
   * The function to define the groups.
   * 
   * @param e1
   *          The first item to compare.
   * @param e2
   *          The second item to compare.
   * @return true if the two items should be in the same group, false otherwise.
   */
  public boolean group(E e1, E e2);
}
