package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class SlicingIteratorTest {
  String[] data = { "a", "b", "c", "d", "e", "f", "g" };
  Iterator<String> iter;

  @Before
  public void setUp() throws Exception {
    iter = Arrays.asList(data).iterator();
  }

  @Test
  public void testSlicingIterator_full() {
    SlicingIterator<String> it = new SlicingIterator<String>(iter, -1, -1, -1);
    for (int i = 0; i < data.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(data[i], it.next());
    }
  }

  @Test
  public void testSlicingIterator_start() {
    int start = 3;
    SlicingIterator<String> it = new SlicingIterator<String>(iter, start, -1,
        -1);
    int i;
    for (i = start; i < data.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(data[i], it.next());
    }
    assertEquals(data.length, i);
  }

  @Test
  public void testSlicingIterator_stop() {
    int stop = 5;
    SlicingIterator<String> it = new SlicingIterator<String>(iter, -1, stop, -1);
    int i;
    for (i = 0; i < stop; ++i) {
      assertTrue(it.hasNext());
      assertEquals(data[i], it.next());
    }
    assertEquals(stop, i);
  }

  @Test
  public void testSlicingIterator_step() {
    int step = 5;
    SlicingIterator<String> it = new SlicingIterator<String>(iter, -1, -1, step);
    int i;
    for (i = 0; i < data.length; i += step) {
      assertTrue(it.hasNext());
      assertEquals(data[i], it.next());
    }
    assertTrue(i >= data.length);
  }

  @Test
  public void testSlicingIterator_all() {
    int start = 1, stop = 5, step = 2;
    SlicingIterator<String> it = new SlicingIterator<String>(iter, start, stop,
        step);
    int i;
    for (i = start; i < stop; i += step) {
      assertTrue(it.hasNext());
      assertEquals(data[i], it.next());
    }
    assertEquals(stop, i);
  }
}
