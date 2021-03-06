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

import itertools.functions.Condition;
import itertools.functions.Grouper;
import itertools.functions.LookupMapper;
import itertools.functions.Mapper;
import itertools.iterator.PeekableIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A utility class to help building iterators. Most of these methods provide the
 * same functionality as the static methods in {@link Itertools} but provide a
 * more sane syntax.
 * 
 * @author jelsas
 * 
 * @param <T>
 */
public class IBuilder<T> implements Iterable<T> {
  private Iterator<T> it;

  public IBuilder(T... t) {
    this.it = Arrays.asList(t).iterator();
  }

  public IBuilder(Iterable<T> it) {
    this.it = it.iterator();
  }

  public IBuilder(Iterator<T> it) {
    this.it = it;
  }

  public List<T> list() {
    List<T> l = new ArrayList<T>();
    for (T t : this) {
      l.add(t);
    }
    return l;
  }

  public T peek() {
    if (!(it instanceof PeekableIterator)) it = new PeekableIterator<T>(it);
    return ((PeekableIterator<T>) it).peek();
  }

  public Iterator<T> iterator() {
    return it;
  }

  /**
   * See {@link Itertools#cycle(Iterable)}.
   */
  public IBuilder<T> cycle() {
    return Itertools.cycle(it);
  }

  /**
   * See {@link Itertools#dropwhile(Iterable, Condition)}
   */
  public IBuilder<T> dropwhile(final Condition<T> condition) {
    return Itertools.dropwhile(it, condition);
  }

  /**
   * See {@link Itertools#filter(Iterable, Condition)}
   */
  public IBuilder<T> filter(final Condition<? super T> keep) {
    return Itertools.filter(it, keep);
  }

  /**
   * See {@link Itertools#groupby(Iterable, Grouper)}.
   */
  public IBuilder<Iterator<T>> groupby(final Grouper<T> grouper) {
    return Itertools.groupby(it, grouper);
  }

  /**
   * See {@link Itertools#groupby(Iterable, Grouper, int)}.
   */
  public IBuilder<Iterator<T>> groupby(final Grouper<T> grouper, int maxSize) {
    return Itertools.groupby(it, grouper, maxSize);
  }

  /**
   * See {@link Itertools#map(Iterable, Mapper)}.
   */
  public <O> IBuilder<O> map(final Mapper<T, O> mapper) {
    return Itertools.map(it, mapper);
  }

  /**
   * See {@link Itertools#lookup(Iterable, Map)}.
   */
  public <O> IBuilder<O> lookup(final Map<T, O> map) {
    return Itertools.lookup(it, map);
  }
  
  /**
   * See {@link Itertools#lookup(Iterable, Map, Object)}.
   */
  public <O> IBuilder<O> lookup(final Map<T, O> map, O defaultValue) {
    return Itertools.lookup(it, map, defaultValue);
  }

  /**
   * See {@link Itertools#slice(Iterable, int, int, int)}.
   */
  public IBuilder<T> slice(int start, int stop, int by) {
    return Itertools.slice(it, start, stop, by);
  }

  /**
   * See {@link Itertools#takewhile(Iterator, Condition)}
   */
  public IBuilder<T> takewhile(final Condition<T> condition) {
    return Itertools.takewhile(it, condition);
  }

}
