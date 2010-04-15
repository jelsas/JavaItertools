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

import java.util.Map;

/**
 * Mapper that looks up input items in the provided map.
 * 
 * @author jelsas
 * 
 */
public class LookupMapper<I, O> implements Mapper<I, O> {
  private O defaultValue;
  private Map<I, O> map;

  public LookupMapper(Map<I, O> map, O defaultValue) {
    this.map = map;
    this.defaultValue = defaultValue;
  }

  public O map(I input) {
    O value = map.get(input);
    if (value == null) {
      return defaultValue;
    } else {
      return value;
    }
  }

}
