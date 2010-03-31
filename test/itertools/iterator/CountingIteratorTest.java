package itertools.iterator;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountingIteratorTest {

  @Test
  public void testCountingIterator() {
    CountingIterator it = new CountingIterator();
    for (int i = 0; i < 10; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
  }

  @Test
  public void testCountingIteratorInt() {
    int start = 100;
    CountingIterator it = new CountingIterator(start);
    for (int i = start; i < start + 10; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
  }

}
