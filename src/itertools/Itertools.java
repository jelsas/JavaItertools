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
import itertools.iterator.*;

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
  public static <E> IBuilder<E> chain(Iterable<? extends Iterable<E>> iterators) {
    return new IBuilder<E>(new ChainedIterator<E>(iterators.iterator()));
  }

  /**
   * See {@link #chain(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IBuilder<E> chain(Iterable<E>... iterators) {
    return chain(Arrays.asList(iterators));
  }

  /**
   * See {@link #chain(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IBuilder<E> chain(Iterator<? extends Iterable<E>> iterators) {
    return new IBuilder<E>(new ChainedIterator<E>(iterators));
  }

  /**
   * Creates an Iterable that counts forever. See {@link CountingIterator}.
   * 
   * @param start
   *          Starting number.
   * @return
   */
  public static IBuilder<Integer> count(int start) {
    return new IBuilder<Integer>(new CountingIterator(start));
  }

  /**
   * Creates an Iterable that counts forever. See {@link CountingIterator}.
   * 
   * @return
   */
  public static IBuilder<Integer> count() {
    return new IBuilder<Integer>(new CountingIterator());
  }

  /**
   * Creates an Iterable that cycles through the provided iterator indefinitely.
   * See {@link CyclingIterator}.
   * 
   * @param <E>
   * @param iterator
   * @return
   */
  public static <E> IBuilder<E> cycle(Iterable<E> iterator) {
    return new IBuilder<E>(new CyclingIterator<E>(iterator.iterator()));
  }

  /**
   * Creates an Iterable that cycles through the provided iterator indefinitely.
   * See {@link CyclingIterator}.
   * 
   * @param <E>
   * @param iterator
   * @return
   */
  public static <E> IBuilder<E> cycle(Iterator<E> iterator) {
    return new IBuilder<E>(new CyclingIterator<E>(iterator));
  }

  /**
   * See {@link DropwhileIterator}.
   * 
   * @param <E>
   * @param iterator
   * @param condition
   * @return
   */
  public static <E> IBuilder<E> dropwhile(Iterator<E> iterator,
      final Condition<E> condition) {
    return new IBuilder<E>(new DropwhileIterator<E>(iterator) {
      @Override
      public boolean condition(E item) {
        return condition.condition(item);
      }
    });
  }

  /**
   * See {@link FilteringIterator}.
   * 
   * @param <E>
   * @param iterator
   * @param condition
   *          Condition object that returns true to keep objects.
   * @return
   */
  public static <E> IBuilder<E> filter(Iterator<E> iterator,
      final Condition<? super E> keep) {
    return new IBuilder<E>(new FilteringIterator<E>(iterator) {
      @Override
      public boolean keep(E item) {
        return keep.condition(item);
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
  public static <E> IBuilder<Iterator<E>> groupby(Iterable<E> iterator,
      final Grouper<E> grouper) {
    return groupby(iterator.iterator(), grouper);
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
  public static <E> IBuilder<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper) {
    return new IBuilder<Iterator<E>>(new LazyGroupingIterator<E>(iterator) {
      @Override
      public boolean group(E e1, E e2) {
        return grouper.group(e1, e2);
      }
    });
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
  public static <E> IBuilder<Iterator<E>> groupby(Iterator<E> iterator,
      final Grouper<E> grouper, int maxGroupSize) {
    return new IBuilder<Iterator<E>>(new GroupingIterator<E>(iterator,
        maxGroupSize) {
      @Override
      public boolean group(E e1, E e2) {
        return grouper.group(e1, e2);
      }
    });
  }

  /**
   * Terse utility method for creating an IBuilder over a collection.
   * 
   * @param <E>
   * @param array
   * @return
   */
  public static <E> IBuilder<E> i(Collection<E> c) {
    return new IBuilder<E>(c);
  }

  /**
   * Terse utility method for creating an IBuilder over an array.
   * 
   * @param <E>
   * @param array
   * @return
   */
  public static <E> IBuilder<E> i(E[] array) {
    return new IBuilder<E>(array);
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
  public static <I, O> IBuilder<O> map(Iterable<I> collection,
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
  public static <I, O> IBuilder<O> map(Iterator<I> iterator,
      final Mapper<I, O> mapper) {
    return new IBuilder<O>(new MappingIterator<I, O>(iterator) {
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
  public static <E> IBuilder<E> merge(
      Iterable<? extends Iterable<E>> iterators, Comparator<E> comp) {
    return new IBuilder<E>(new MergingIterator<E>(iterators, comp));
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
  public static <E extends Comparable<E>> IBuilder<E> merge(
      Iterable<? extends Iterable<E>> iterators) {
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
  public static <E extends Comparable<E>> IBuilder<E> merge(
      Iterable<E>... iterators) {
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
  public static IBuilder<String> open(String filename) throws IOException {
    return new IBuilder<String>(new FileLineIterator(filename));
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
  public static IBuilder<String> open(String filename, int bufferSize)
      throws IOException {
    return new IBuilder<String>(new FileLineIterator(filename, bufferSize));
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
  public static <E> IBuilder<E> repeat(E item) {
    return new IBuilder<E>(new RepeatingIterator<E>(item));
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
  public static <E> IBuilder<E> slice(Iterable<E> it, int start, int stop,
      int by) {
    return slice(it.iterator(), start, stop, by);
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
  public static <E> IBuilder<E> slice(Iterator<E> it, int start, int stop,
      int by) {
    return new IBuilder<E>(new SlicingIterator<E>(it, start, stop, by));
  }

  /**
   * See {@link TakewhileIterator}.
   * 
   * @param <E>
   * @param iterator
   * @param condition
   * @return
   */
  public static <E> IBuilder<E> takewhile(Iterator<E> iterator,
      final Condition<E> condition) {
    return new IBuilder<E>(new TakewhileIterator<E>(iterator) {
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
  public static <E> IBuilder<List<E>> zip(
      Iterable<? extends Iterable<E>> iterators) {
    return new IBuilder<List<E>>(new ZippingIterator<E>(iterators));
  }

  /**
   * See {@link #zip(Collection)}.
   * 
   * @param <E>
   * @param iterators
   * @return
   */
  public static <E> IBuilder<List<E>> zip(Iterable<E>... iterators) {
    return zip(Arrays.asList(iterators));
  }
}
