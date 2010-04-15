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
import itertools.iterator.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A collection of static methods to aid creation of specialized iterators. All
 * of these decorator methods take an Iterator or Iterable and return an
 * (Iterable) IBuilder object.
 * 
 * @author jelsas
 * 
 */
public class Itertools {

  /**
   * Test the condition on the elements of the iterator.
   * 
   * @param <E>
   * @param it
   *          The iterator
   * @param c
   *          The condition
   * @return True if the condition holds for all elements, false otherwise.
   */
  public static <E> boolean all(Iterator<E> it, final Condition<E> c) {
    while (it.hasNext()) {
      if (!c.condition(it.next())) { return false; }
    }
    return true;
  }

  /**
   * See {@link #all(Iterator, Condition)}.
   */
  public static <E> boolean all(Iterable<E> it, final Condition<E> c) {
    return all(it.iterator(), c);
  }

  /**
   * Test the condition on the elements of the iterator.
   * 
   * @param <E>
   * @param it
   *          The iterator
   * @param c
   *          The condition
   * @return True if the condition holds for any element, false otherwise.
   */
  public static <E> boolean any(Iterator<E> it, final Condition<E> c) {
    while (it.hasNext()) {
      if (c.condition(it.next())) { return true; }
    }
    return false;
  }

  /**
   * See {@link #any(Iterator, Condition)}.
   */
  public static <E> boolean any(Iterable<E> it, final Condition<E> c) {
    return any(it.iterator(), c);
  }

  /**
   * Creates an Iterable to chain the provided iterator collection together. See
   * {@link ChainedIterator}.
   * 
   * @param <E>
   * @param iterators
   * @return An iterable.
   */
  public static <E> IBuilder<E> chain(Iterable<? extends Iterable<E>> iterators) {
    return chain(iterators.iterator());
  }

  /**
   * See {@link #chain(Iterable)}.
   */
  public static <E> IBuilder<E> chain(Iterable<E>... iterators) {
    return chain(Arrays.asList(iterators));
  }

  /**
   * See {@link #chain(Iterable)}.
   */
  public static <E> IBuilder<E> chain(Iterator<? extends Iterable<E>> iterators) {
    return new IBuilder<E>(new ChainedIterator<E>(iterators));
  }

  /**
   * Creates an Iterable that counts forever. See {@link CountingIterator}.
   * 
   * @param start
   *          Starting number.
   * @return An iterable.
   */
  public static IBuilder<Integer> count(int start) {
    return new IBuilder<Integer>(new CountingIterator(start));
  }

  /**
   * Creates an Iterable that counts forever. See {@link CountingIterator}.
   * 
   * @return An iterable.
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
   * @return An iterable.
   */
  public static <E> IBuilder<E> cycle(Iterable<E> iterator) {
    return cycle(iterator.iterator());
  }

  /**
   * Creates an Iterable that cycles through the provided iterator indefinitely.
   * See {@link CyclingIterator}.
   * 
   * @param <E>
   * @param iterator
   * @return An iterable.
   */
  public static <E> IBuilder<E> cycle(Iterator<E> iterator) {
    return new IBuilder<E>(new CyclingIterator<E>(iterator));
  }

  /**
   * See {@link DropwhileIterator}.
   */
  public static <E> IBuilder<E> dropwhile(Iterable<E> iterator,
      final Condition<E> condition) {
    return dropwhile(iterator.iterator(), condition);
  }

  /**
   * See {@link DropwhileIterator}.
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
   * See {@link #filter(Iterator, Condition)}.
   */
  public static <E> IBuilder<E> filter(Iterable<E> iterator,
      final Condition<? super E> keep) {
    return filter(iterator.iterator(), keep);
  }

  /**
   * Filters the provided iterator with the condition, keeping elements where
   * keep.condition(element) evaluates to true.
   * 
   * @param <E>
   * @param iterator
   * @param keep
   *          The filtering condition
   * @return An iterator with some elements filtered.
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
   * iterator. The {@link Grouper#group(Object, Object)} function defines
   * whether adjascent elements in the iterator belong in the same group.
   * Similar to unix's <tt>uniq</tt> command. See {@link GroupingIterator}.
   * 
   * @param <E>
   * @param iterator
   *          The underlying iterator.
   * @param grouper
   *          The grouping function.
   * @return An iterable over groups.
   */
  public static <E> IBuilder<Iterator<E>> groupby(Iterable<E> iterator,
      final Grouper<E> grouper) {
    return groupby(iterator.iterator(), grouper);
  }

  /**
   * Creates an Iterable over sequential groups of elements in the provided
   * iterator. The {@link Grouper#group(Object, Object)} function defines
   * whether adjascent elements in the iterator belong in the same group.
   * Similar to unix's <tt>uniq</tt> command. See {@link GroupingIterator}.
   * 
   * @param <E>
   * @param iterator
   *          The underlying iterator.
   * @param grouper
   *          The grouping function.
   * @return An iterable over groups.
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
   * See {@link #groupby(Iterator, Grouper, int)}.
   * 
   * @param <E>
   * @param iterator
   * @param grouper
   * @param maxGroupSize
   * @return An iterable over groups.
   */
  public static <E> IBuilder<Iterator<E>> groupby(Iterable<E> iterator,
      final Grouper<E> grouper, int maxGroupSize) {
    return groupby(iterator.iterator(), grouper, maxGroupSize);
  }

  /**
   * Creates an Iterable over sequential groups of elements in the provided
   * iterator, limiting the maximum group size to <tt>maxGroupSize</tt>. The
   * {@link Grouper#group(Object, Object)} function defines whether adjascent
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
   * @return An iterable over groups.
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
   * Similar to {@link #lookup(Iterable, Map, Object)} but maps to null if an
   * input element is missing.
   */
  public static <I, O> IBuilder<O> lookup(Iterable<I> collection,
      final Map<I, O> map) {
    return lookup(collection.iterator(), map);
  }

  /**
   * Maps each element of the iterable to the corresponding value in the
   * provided map, or the default value if missing.
   * 
   * @param <I>
   *          Input type.
   * @param <O>
   *          Output type.
   * @param collection
   *          The input iterable
   * @param map
   *          The map from input to output types.
   * @param defaultValue
   *          The value to return if an input object is missing from the map.
   * @return An interable over output types.
   */
  public static <I, O> IBuilder<O> lookup(Iterable<I> collection,
      final Map<I, O> map, O defaultValue) {
    return lookup(collection.iterator(), map, defaultValue);
  }

  /**
   * Similar to {@link #lookup(Iterator, Map, Object)} but maps to null if an
   * input element is missing.
   */
  public static <I, O> IBuilder<O> lookup(Iterator<I> collection,
      final Map<I, O> map) {
    return lookup(collection, map, null);
  }

  /**
   * Maps each element of the iterable to the corresponding value in the
   * provided map, or the default value if missing.
   * 
   * @param <I>
   *          Input type.
   * @param <O>
   *          Output type.
   * @param collection
   *          The input iterable
   * @param map
   *          The map from input to output types.
   * @param defaultValue
   *          The value to return if an input object is missing from the map.
   * @return An interable over output types.
   */
  public static <I, O> IBuilder<O> lookup(Iterator<I> collection,
      final Map<I, O> map, O defaultValue) {
    return map(collection, new LookupMapper<I, O>(map, defaultValue));
  }

  /**
   * See {@link #map(Iterator, Mapper)}.
   */
  public static <I, O> IBuilder<O> map(Iterable<I> collection,
      final Mapper<I, O> mapper) {
    return map(collection.iterator(), mapper);
  }

  /**
   * Creates an Iterable over the input, applying the {@link Mapper#map(Object)}
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
   * See {@link #merge(Iterator, Comparator)}.
   */
  public static <E> IBuilder<E> merge(
      Iterable<? extends Iterable<E>> iterators, Comparator<E> comp) {
    return merge(iterators.iterator(), comp);
  }

  /**
   * Merges the provided iterators so that the resulting Iterable is in sorted
   * order according to the comparator. It is assumed that the provided
   * iterators are in sorted order. See {@link MergingIterator} and
   * {@link #merge(Iterator)} for merging by the natural ordering.
   * 
   * @param <E>
   * @param iterators
   *          The underlying iterators.
   * @param comp
   *          The comparator.
   * @return An merged iterable.
   */
  public static <E> IBuilder<E> merge(
      Iterator<? extends Iterable<E>> iterators, Comparator<E> comp) {
    return new IBuilder<E>(new MergingIterator<E>(iterators, comp));
  }

  /**
   * Merges the provided iterators so that the resulting Iterable is in sorted
   * order according to the object's natrual order. It is assumed that the
   * provided iterators are in sorted order. See {@link MergingIterator} and
   * {@link #merge(Iterator, Comparator)}.
   * 
   * @param <E>
   * @param iterators
   *          The underlying iterators.
   * @return A merged iterable.
   */
  public static <E extends Comparable<? super E>> IBuilder<E> merge(
      Iterator<? extends Iterable<E>> iterators) {
    Comparator<E> comp = new Comparator<E>() {
      public int compare(E o1, E o2) {
        return o1.compareTo(o2);
      }
    };

    return merge(iterators, comp);
  }

  /**
   * See {@link #merge(Iterator, Comparator)}.
   */
  public static <E extends Comparable<? super E>> IBuilder<E> merge(
      Iterable<? extends Iterable<E>> iterators) {
    Comparator<E> comp = new Comparator<E>() {
      public int compare(E o1, E o2) {
        return o1.compareTo(o2);
      }
    };

    return merge(iterators, comp);
  }

  /**
   * See {@link #merge(Iterator)}
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
   * @return An iterable over lines in the file.
   * @throws IOException
   *           If an error occurred opening the file.
   */
  public static IBuilder<String> open(File file) throws IOException {
    return new IBuilder<String>(new FileLineIterator(file));
  }

  /**
   * Opens the specified file for reading. See {@link FileLineIterator}.
   * 
   * @param filename
   *          The file to open.
   * @param bufferSize
   *          Buffer size of underlying file buffer.
   * @return An iterable over lines in the file.
   * @throws IOException
   *           If an error occurred opening the file.
   */
  public static IBuilder<String> open(File file, int bufferSize)
      throws IOException {
    return new IBuilder<String>(new FileLineIterator(file, bufferSize));
  }

  /**
   * Opens the specified file for reading. See {@link FileLineIterator}.
   * 
   * @param filename
   *          The file to open.
   * @return An iterable over lines in the file.
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
   *          Buffer size of underlying file buffer.
   * @return An iterable over lines in the file.
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
   * @return An iterable that always reutrns the same item.
   */
  public static <E> IBuilder<E> repeat(E item) {
    return new IBuilder<E>(new RepeatingIterator<E>(item));
  }

  /**
   * See {@link #slice(Iterator, int, int, int)}.
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
   * @return An iterable.
   */
  public static <E> IBuilder<E> slice(Iterator<E> it, int start, int stop,
      int by) {
    return new IBuilder<E>(new SlicingIterator<E>(it, start, stop, by));
  }

  /**
   * See {@link TakewhileIterator}.
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
   * See {@link #zip(Iterator)}.
   */
  public static <E> IBuilder<List<E>> zip(
      Iterable<? extends Iterable<E>> iterators) {
    return zip(iterators.iterator());
  }

  /**
   * Creates an iterable to iterate over the provided iterators in parallel.
   * Each return List contains the next element from each of the provided
   * iterators in order, or null if that iterator is exhausted. See
   * {@link ZippingIterator}.
   * 
   * @param <E>
   * @param iterators
   * @return An iterable over a list of items in each input iterator.
   */
  public static <E> IBuilder<List<E>> zip(
      Iterator<? extends Iterable<E>> iterators) {
    return new IBuilder<List<E>>(new ZippingIterator<E>(iterators));
  }

  /**
   * See {@link #zip(Iterator)}.
   */
  public static <E> IBuilder<List<E>> zip(Iterable<E>... iterators) {
    return zip(Arrays.asList(iterators));
  }
}
