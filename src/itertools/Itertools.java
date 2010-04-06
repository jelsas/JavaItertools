package itertools;

import itertools.functions.Condition;
import itertools.functions.Grouper;
import itertools.functions.Mapper;
import itertools.iterator.ChainedIterator;
import itertools.iterator.CountingIterator;
import itertools.iterator.CyclingIterator;
import itertools.iterator.DropwhileIterator;
import itertools.iterator.FileLineIterator;
import itertools.iterator.GroupingIterator;
import itertools.iterator.MappingIterator;
import itertools.iterator.MergingIterator;
import itertools.iterator.RepeatingIterator;
import itertools.iterator.SlicingIterator;
import itertools.iterator.TakewhileIterator;
import itertools.iterator.ZippingIterator;

import java.io.IOException;
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
   * Creates an Iterable to chain the provided iterator collection together. See
   * {@link ChainedIterator}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IterableIterator<E> chain(Collection<Iterator<E>> iterators) {
    return new IterableIterator<E>(new ChainedIterator<E>(iterators));
  }

  /**
   * See {@link #chain(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IterableIterator<E> chain(Iterator<E>... iterators) {
    return chain(Arrays.asList(iterators));
  }

  /**
   * See {@link #chain(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IterableIterator<E> chain(
      Iterator<? extends Iterator<E>> iterators) {
    return new IterableIterator<E>(new ChainedIterator<E>(iterators));
  }

  /**
   * Creates an Iterable that counts forever. See {@link CountingIterator}.
   * 
   * @param start
   *          Starting number.
   * @return
   */
  public static IterableIterator<Integer> count(int start) {
    return new IterableIterator<Integer>(new CountingIterator(start));
  }

  /**
   * Creates an Iterable that counts forever. See {@link CountingIterator}.
   * 
   * @return
   */
  public static IterableIterator<Integer> count() {
    return new IterableIterator<Integer>(new CountingIterator());
  }

  /**
   * Creates an Iterable that cycles through the provided iterator indefinitely.
   * See {@link CyclingIterator}.
   * 
   * @param <E>
   * @param iterator
   * @return
   */
  public static <E> IterableIterator<E> cycle(Iterator<E> iterator) {
    return new IterableIterator<E>(new CyclingIterator<E>(iterator));
  }

  /**
   * See {@link DropwhileIterator}.
   * 
   * @param <E>
   * @param iterator
   * @param condition
   * @return
   */
  public static <E> IterableIterator<E> dropwhile(Iterator<E> iterator,
      final Condition<E> condition) {
    return new IterableIterator<E>(new DropwhileIterator<E>(iterator) {
      @Override
      public boolean condition(E item) {
        return condition.condition(item);
      }
    });
  }

  /**
   * Creates an Iterable over sequential groups of elements in the provided
   * iterator. The {@link Grouper.group(Object, Object)} function defines
   * whether adjascent elements in the iterator belong in the same group.
   * Similar to unix's <tt>uniq</tt> command. See {@link GroupingIterator}.
   * 
   * @param <E>
   * @param iterator
   *          The underlying iterator.
   * @param grouper
   *          The grouping function.
   * @return An iterator over groups.
   */
  public static <E> IterableIterator<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper) {
    return groupby(iterator, grouper, Integer.MAX_VALUE);
  }

  /**
   * Creates an Iterable over sequential groups of elements in the provided
   * iterator, limiting the maximum group size to <tt>maxGroupSize</tt>. The
   * {@link Grouper.group(Object, Object)} function defines whether adjascent
   * elements in the iterator belong in the same group. Similar to unix's
   * <tt>uniq</tt> command. See {@link GroupingIterator}.<br/>
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
  public static <E> IterableIterator<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper, int maxGroupSize) {
    return new IterableIterator<Iterator<E>>(new GroupingIterator<E>(iterator,
        maxGroupSize) {
      @Override
      public boolean group(E e1, E e2) {
        return grouper.group(e1, e2);
      }
    });
  }

  /**
   * Terse utility method for creating an IterableIterator over a collection.
   * 
   * @param <E>
   * @param array
   * @return
   */
  public static <E> IterableIterator<E> i(Collection<E> c) {
    return new IterableIterator<E>(c);
  }

  /**
   * Terse utility method for creating an IterableIterator over an array.
   * 
   * @param <E>
   * @param array
   * @return
   */
  public static <E> IterableIterator<E> i(E[] array) {
    return new IterableIterator<E>(array);
  }

  /**
   * See {@link #map(Iterator, Mapper)}.
   * 
   * @param <I>
   * @param <O>
   * @param collection
   * @param mapper
   * @return
   */
  public static <I, O> IterableIterator<O> map(Collection<I> collection,
      final Mapper<I, O> mapper) {
    return map(collection.iterator(), mapper);
  }

  /**
   * Creates an Iterable over the input, applying the {@link Mapper.map(Object)}
   * function to each element. See {@link MappingIterator}.
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
  public static <I, O> IterableIterator<O> map(Iterator<I> iterator,
      final Mapper<I, O> mapper) {
    return new IterableIterator<O>(new MappingIterator<I, O>(iterator) {
      @Override
      public O map(I in) {
        return mapper.map(in);
      }
    });
  }

  /**
   * Merges the provided iterators so that the resulting Iterable is in sorted
   * order according to the comparator. It is assumed that the provided
   * iterators are in sorted order. See {@link MergingIterator} and
   * {@link #merge(Collection)} for merging by the natural ordering.
   * 
   * @param <E>
   * @param iterators
   *          The underlying iterators.
   * @param comp
   *          The comparator.
   * @return An merged iterable.
   */
  public static <E> IterableIterator<E> merge(
      Collection<? extends Iterator<E>> iterators, Comparator<E> comp) {
    return new IterableIterator<E>(new MergingIterator<E>(iterators, comp));
  }

  /**
   * Merges the provided iterators so that the resulting Iterable is in sorted
   * order according to the object's natrual order. It is assumed that the
   * provided iterators are in sorted order. See {@link MergingIterator} and
   * {@link #merge(Collection, Comparator)}.
   * 
   * @param <E>
   * @param iterators
   *          The underlying iterators.
   * @return A merged iterable.
   */
  public static <E extends Comparable<E>> IterableIterator<E> merge(
      Collection<? extends Iterator<E>> iterators) {
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
  public static <E extends Comparable<E>> IterableIterator<E> merge(
      Iterator<E>... iterators) {
    return merge(Arrays.asList(iterators));
  }

  /**
   * Opens the specified file for reading. See {@link FileLineIterator}.
   * 
   * @param filename
   *          The file to open.
   * @return
   * @throws IOException
   *           If an error occurred opening the file.
   */
  public static IterableIterator<String> open(String filename)
      throws IOException {
    return new IterableIterator<String>(new FileLineIterator(filename));
  }

  /**
   * Opens the specified file for reading. See {@link FileLineIterator}.
   * 
   * @param filename
   *          The file to open.
   * @param bufferSize
   *          Buffer size for underlying file reader.
   * @return
   * @throws IOException
   *           If an error occurred opening the file.
   */
  public static IterableIterator<String> open(String filename, int bufferSize)
      throws IOException {
    return new IterableIterator<String>(new FileLineIterator(filename,
        bufferSize));
  }

  /**
   * Creates an iterable always repeating the provided item. See
   * {@link RepeatingIterator}.
   * 
   * @param <E>
   *          The item type.
   * @param item
   *          The item.
   * @return
   */
  public static <E> IterableIterator<E> repeat(E item) {
    return new IterableIterator<E>(new RepeatingIterator<E>(item));
  }

  /**
   * Creates an iterable selecting subsets of the provided iterator. See
   * {@link SlicingIterator}.
   * 
   * @param <E>
   * @param it
   *          The underlying iterator.
   * @param start
   *          The start position. &lt;0 starts at zero.
   * @param stop
   *          The stop position. &lt;0 iterates to the end of the <tt>it</it>.
   * @param by
   *          The step size. &lt;0 equivalent to a step size of 1.
   * @return
   */
  public static <E> IterableIterator<E> slice(Iterator<E> it, int start,
      int stop, int by) {
    return new IterableIterator<E>(new SlicingIterator<E>(it, start, stop, by));
  }

  /**
   * See {@link TakewhileIterator}.
   * 
   * @param <E>
   * @param iterator
   * @param condition
   * @return
   */
  public static <E> IterableIterator<E> takewhile(Iterator<E> iterator,
      final Condition<E> condition) {
    return new IterableIterator<E>(new TakewhileIterator<E>(iterator) {
      @Override
      public boolean condition(E item) {
        return condition.condition(item);
      }
    });
  }

  /**
   * Creates an iterable to iterate over the provided iterators in parallel.
   * Each return List contains the next element from each of the provided
   * iterators in order, or null if that iterator is exhausted. See
   * {@link ZippingIterator}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IterableIterator<List<E>> zip(
      Collection<Iterator<E>> iterators) {
    return new IterableIterator<List<E>>(new ZippingIterator<E>(iterators));
  }

  /**
   * See {@link #zip(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IterableIterator<List<E>> zip(Iterator<E>... iterators) {
    return zip(Arrays.asList(iterators));
  }
}
