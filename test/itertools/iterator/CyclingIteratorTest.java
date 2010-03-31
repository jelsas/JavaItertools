package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class CyclingIteratorTest {
  String[] data = { "a", "b", "c", "d" };
  Iterator<String> dataIt;

  @Before
  public void setUp() throws Exception {
    dataIt = Arrays.asList(data).iterator();
  }

  @Test
  public void testCyclingIterator() {
    CyclingIterator<String> it = new CyclingIterator<String>(dataIt);
    for (int i = 0; i < 100; ++i) {
      assertTrue(it.hasNext());
      assertEquals(data[i % data.length], it.next());
    }
  }

}
