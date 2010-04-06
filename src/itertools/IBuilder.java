package itertools;

import itertools.functions.Grouper;
import itertools.functions.Mapper;

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
public class IBuilder<T> implements Iterable<T> {
  private Iterator<T> it;

  public IBuilder(Iterable<T> it) {
    this.it = it.iterator();
  }

  public IBuilder(Iterator<T> it) {
    this.it = it;
  }

  public Iterator<T> iterator() {
    return it;
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
}
