package itertools;

import itertools.functions.Grouper;
import itertools.functions.Mapper;
import itertools.iterator.ChainedIterator;
import itertools.iterator.CyclingIterator;
import itertools.iterator.GroupingIterator;
import itertools.iterator.MappingIterator;
import itertools.iterator.MergingIterator;
import itertools.iterator.RepeatingIterator;
import itertools.iterator.ZippingIterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of static methods to aid creation of specialized iterators. Most
 * of these correspond to functions defined in Python's itertools library.
 * 
 * @author jelsas
 * 
 */
public class Itertools {

  /**
   * Creates an Iterable to chain the provided iterator collection together.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> Iterable<E> chain(Collection<Iterator<E>> iterators) {
    return new SimpleIterable<E>(new ChainedIterator<E>(iterators));
  }

  /**
   * See {@link #chain(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> Iterable<E> chain(Iterator<E>... iterators) {
    return chain(Arrays.asList(iterators));
  }

  /**
   * Creates an Iterable that cycles through the provided iterator indefinitely.
   * 
   * @param <E>
   * @param iterator
   * @return
   */
  public static <E> Iterable<E> cycle(Iterator<E> iterator) {
    return new SimpleIterable<E>(new CyclingIterator<E>(iterator));
  }

  /**
   * Creates an Iterable over sequential groups of elements in the provided
   * iterator. The {@link Grouper.group(Object, Object)} function defines
   * whether adjascent elements in the iterator belong in the same group.
   * Similar to unix's <tt>uniq</tt> command.
   * 
   * @param <E>
   * @param iterator
   *          The underlying iterator.
   * @param grouper
   *          The grouping function.
   * @return An iterator over groups.
   */
  public static <E> Iterable<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper) {
    return groupby(iterator, grouper, Integer.MAX_VALUE);
  }

  /**
   * Creates an Iterable over sequential groups of elements in the provided
   * iterator, limiting the maximum group size to <tt>maxGroupSize</tt>. The
   * {@link Grouper.group(Object, Object)} function defines whether adjascent
   * elements in the iterator belong in the same group. Similar to unix's
   * <tt>uniq</tt> command. <br/>
   * Note: limiting the maximum group size can be useful to limit memory
   * consumption when the underlying iterator is large.
   * 
   * @param <E>
   * @param iterator
   *          The underlying iterator.
   * @param grouper
   *          The underlying iterator.
   * @param maxGroupSize
   *          The maximum size of a group. If a group exceeds this size, it will
   *          be split into more than one group.
   * @return
   */
  public static <E> Iterable<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper, int maxGroupSize) {
    return new SimpleIterable<Iterator<E>>(new GroupingIterator<E>(iterator,
        maxGroupSize) {
      @Override
      public boolean group(E e1, E e2) {
        return grouper.group(e1, e2);
      }
    });
  }

  /**
   * Creates an Iterable over the input, applying the {@link Mapper.map(Object)}
   * function to each element.
   * 
   * @param <I>
   *          Input type.
   * @param <O>
   *          Output type.
   * @param iterator
   *          Underlying itertor over the input type.
   * @param mapper
   *          Mapping function.
   * @return An iterator over the output type.
   */
  public static <I, O> Iterable<O> map(Iterator<I> iterator,
      final Mapper<I, O> mapper) {
    return new SimpleIterable<O>(new MappingIterator<I, O>(iterator) {
      @Override
      public O map(I in) {
        return mapper.map(in);
      }
    });
  }

  /**
   * Merges the provided iterators so that the resulting Iterable is in sorted
   * order according to the comparator. It is assumed that the provided
   * iterators are in sorted order.
   * 
   * @param <E>
   * @param iterators
   *          The underlying iterators.
   * @param comparator
   *          The comparator.
   * @return An merged iterable.
   */
  public static <E> Iterable<E> merge(Collection<Iterator<E>> iterators,
      Comparator<E> comparator) {
    return new SimpleIterable<E>(new MergingIterator<E>(iterators, comparator));
  }

  /**
   * Merges the provided iterators so that the resulting Iterable is in sorted
   * order according to the object's natrual order. It is assumed that the
   * provided iterators are in sorted order.
   * 
   * @param <E>
   * @param iterators
   *          The underlying iterators.
   * @return A merged iterable.
   */
  public static <E extends Comparable<E>> Iterable<E> merge(
      Collection<Iterator<E>> iterators) {
    Comparator<E> comp = new Comparator<E>() {
      public int compare(E o1, E o2) {
        return o1.compareTo(o2);
      }
    };

    return merge(iterators, comp);
  }

  /**
   * See {@link #merge(Collection)}
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E extends Comparable<E>> Iterable<E> merge(
      Iterator<E>... iterators) {
    return merge(Arrays.asList(iterators));
  }

  /**
   * Creates an iterable always repeating the provided item.
   * 
   * @param <E>
   *          The item type.
   * @param item
   *          The item.
   * @return
   */
  public static <E> Iterable<E> repeat(E item) {
    return new SimpleIterable<E>(new RepeatingIterator<E>(item));
  }

  /**
   * Creates an iterable to iterate over the provided iterators in parallel.
   * Each return List contains the next element from each of the provided
   * iterators in order, or null if that iterator is exhausted.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> Iterable<List<E>> zip(Collection<Iterator<E>> iterators) {
    return new SimpleIterable<List<E>>(new ZippingIterator<E>(iterators));
  }

  /**
   * See {@link #zip(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> Iterable<List<E>> zip(Iterator<E>... iterators) {
    return zip(Arrays.asList(iterators));
  }
}
