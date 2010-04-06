package itertools;

import itertools.functions.Grouper;
import itertools.functions.Mapper;

import java.util.Iterator;

/**
 * A utility class to help building iterators.
 * @author jelsas
 *
 * @param <T>
 */
public class IBuilder<T> implements Iterable<T> {
  private Iterator<T> it;

  public IBuilder() {
    it = null;
  }

  public IBuilder(Iterator<T> it) {
    this.it = it;
  }

  public Iterator<T> iterator() {
    return it;
  }

  public IBuilder<T> cycle() {
    it = Itertools.cycle(it);
    return this;
  }

  public IBuilder<Iterator<T>> groupby(Grouper<T> grouper) {
    IBuilder<Iterator<T>> i = new IBuilder<Iterator<T>>(Itertools.groupby(it,
        grouper));
    return i;
  }

  public IBuilder<Iterator<T>> groupby(Grouper<T> grouper, int maxSize) {
    IBuilder<Iterator<T>> i = new IBuilder<Iterator<T>>(Itertools.groupby(it,
        grouper, maxSize));
    return i;
  }

  public <O> IBuilder<O> map(Mapper<T, O> mapper) {
    IBuilder<O> i = new IBuilder<O>(Itertools.map(it, mapper));
    return i;
  }

  public IBuilder<T> slice(int start, int stop, int by) {
    it = Itertools.slice(it, start, stop, by);
    return this;
  }
}
