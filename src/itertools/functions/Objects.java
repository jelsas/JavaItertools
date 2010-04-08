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
 * @author jelsas
 * 
 */
public class Objects {
  public static final Condition<? super Object> NOT_NULL = new _notNull<Object>();
  public static final Condition<? super Object> IS_NULL = new _isNull<Object>();

  private static class _notNull<E> implements Condition<E> {
    public boolean condition(E item) {
      return (item != null);
    }
  }

  private static class _isNull<E> implements Condition<E> {
    public boolean condition(E item) {
      return (item == null);
    }
  }
}
