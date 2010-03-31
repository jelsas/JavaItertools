package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class EnumeratingIteratorTest {
  String[] data = { "a", "b", "c", "d" };
  Iterator<String> it;

  @Before
  public void setUp() throws Exception {
    it = Arrays.asList(data).iterator();
  }

  @Test
  public void testEnumeratingIterator() {
    EnumeratingIterator<String> eit = new EnumeratingIterator<String>(it);
    for (int i = 0; i < data.length; ++i) {
      assertTrue(eit.hasNext());
      assertEquals(eit.next(), data[i]);
      assertEquals(eit.currentCount(), i);
    }
    assertFalse(eit.hasNext());
  }

}
