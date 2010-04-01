package itertools;

import itertools.functions.Grouper;
import itertools.functions.Mapper;
import itertools.iterator.ChainedIterator;
import itertools.iterator.CyclingIterator;
import itertools.iterator.GroupingIterator;
import itertools.iterator.MappingIterator;
import itertools.iterator.RepeatingIterator;
import itertools.iterator.ZippingIterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Utility static classes.
 * 
 * @author jelsas
 * 
 */
public class Itertools {
  private static <E> Iterator<E> chainI(Collection<Iterator<E>> iterators) {
    return new ChainedIterator<E>(iterators);
  }

  public static <E> Iterable<E> chain(Collection<Iterator<E>> iterators) {
    return new SimpleIterable<E>(chainI(iterators));
  }

  private static <E> Iterator<E> chainI(Iterator<E>... iterators) {
    return chainI(Arrays.asList(iterators));
  }

  public static <E> Iterable<E> chain(Iterator<E>... iterators) {
    return new SimpleIterable<E>(chainI(iterators));
  }

  private static <E> Iterator<E> cycleI(Iterator<E> iterator) {
    return new CyclingIterator<E>(iterator);
  }

  public static <E> Iterable<E> cycle(Iterator<E> iterator) {
    return new SimpleIterable<E>(cycleI(iterator));
  }

  private static <E> Iterator<Iterator<E>> groupbyI(Iterator<E> iterator,
      final Grouper<E> grouper) {
    return groupbyI(iterator, grouper, Integer.MAX_VALUE);
  }

  private static <E> Iterator<Iterator<E>> groupbyI(Iterator<E> iterator,
      final Grouper<E> grouper, int maxGroupSize) {
    return new GroupingIterator<E>(iterator, maxGroupSize) {
      @Override
      public boolean group(E e1, E e2) {
        return grouper.group(e1, e2);
      }
    };
  }

  public static <E> Iterable<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper) {
    return new SimpleIterable<Iterator<E>>(groupbyI(iterator, grouper));
  }

  public static <E> Iterable<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper, int maxGroupSize) {
    return new SimpleIterable<Iterator<E>>(groupbyI(iterator, grouper,
        maxGroupSize));
  }

  private static <I, O> Iterator<O> mapI(Iterator<I> iterator,
      final Mapper<I, O> mapper) {
    return new MappingIterator<I, O>(iterator) {
      @Override
      public O map(I in) {
        return mapper.map(in);
      }
    };
  }

  public static <I, O> Iterable<O> map(Iterator<I> iterator,
      final Mapper<I, O> mapper) {
    return new SimpleIterable<O>(mapI(iterator, mapper));
  }

  private static <E> Iterator<E> repeatI(E item) {
    return new RepeatingIterator<E>(item);
  }

  public static <E> Iterable<E> repeat(E item) {
    return new SimpleIterable<E>(repeatI(item));
  }

  private static <E> Iterator<List<E>> zipI(Collection<Iterator<E>> iterators) {
    return new ZippingIterator<E>(iterators);
  }

  public static <E> Iterable<List<E>> zip(Collection<Iterator<E>> iterators) {
    return new SimpleIterable<List<E>>(zipI(iterators));
  }

  private static <E> Iterator<List<E>> zipI(Iterator<E>... iterators) {
    return new ZippingIterator<E>(Arrays.asList(iterators));
  }

  public static <E> Iterable<List<E>> zip(Iterator<E>... iterators) {
    return new SimpleIterable<List<E>>(zipI(iterators));
  }
}
