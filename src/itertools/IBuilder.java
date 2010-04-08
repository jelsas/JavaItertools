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
import itertools.functions.Mapper;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A utility class to help building iterators. Most of these methods provide the
 * same functionality as the static methods in {@link Itertools} but provide a
 * more sane syntax.
 * 
 * @author jelsas
 * 
 * @param <T>
 */
public class IBuilder<T> implements Iterable<T>, Iterator<T> {
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

  /**
   * See {@link Itertools.cycle}.
   * 
   * @return
   */
  public IBuilder<T> cycle() {
    it = Itertools.cycle(it);
    return this;
  }

  /**
   * See {@link Itertools.dropwhile}
   * 
   * @param condition
   * @return
   */
  public IBuilder<T> dropwhile(final Condition<T> condition) {
    it = Itertools.dropwhile(it, condition);
    return this;
  }

  /**
   * See {@link Itertools.filter}
   * 
   * @param keep
   * @return
   */
  public IBuilder<T> filter(final Condition<? super T> keep) {
    it = Itertools.filter(it, keep);
    return this;
  }

  /**
   * See {@link Itertools.groupby}.
   * 
   * @param grouper
   * @return
   */
  public IBuilder<Iterator<T>> groupby(Grouper<T> grouper) {
    IBuilder<Iterator<T>> i = new IBuilder<Iterator<T>>(Itertools.groupby(it,
        grouper).it);
    return i;
  }

  /**
   * See {@link Itertools.groupby}.
   * 
   * @param grouper
   * @param maxSize
   * @return
   */
  public IBuilder<Iterator<T>> groupby(Grouper<T> grouper, int maxSize) {
    IBuilder<Iterator<T>> i = new IBuilder<Iterator<T>>(Itertools.groupby(it,
        grouper, maxSize).it);
    return i;
  }

  /**
   * See {@link Itertools.map}.
   * 
   * @param <O>
   * @param mapper
   * @return
   */
  public <O> IBuilder<O> map(Mapper<T, O> mapper) {
    IBuilder<O> i = new IBuilder<O>(Itertools.map(it, mapper).it);
    return i;
  }

  /**
   * See {@link Itertools.slice}.
   * 
   * @param start
   * @param stop
   * @param by
   * @return
   */
  public IBuilder<T> slice(int start, int stop, int by) {
    it = Itertools.slice(it, start, stop, by);
    return this;
  }

  /**
   * See {@link Itertools.takewhile}
   * 
   * @param condition
   * @return
   */
  public IBuilder<T> takewhile(final Condition<T> condition) {
    it = Itertools.takewhile(it, condition);
    return this;
  }

}
