package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class FilteringIteratorTest {
  String[] data = { "one", "two", "three", "four" };
  Iterator<String> it;

  @Before
  public void setUp() throws Exception {
    it = Arrays.asList(data).iterator();
  }

  @Test
  public void testFilteringIterator() {
    it = new FilteringIterator<String>(it) {
      @Override
      public boolean keep(String object) {
        return object.indexOf('o') >= 0;
      }
    };

    String[] expected = { data[0], data[1], /* data[2] filtered */data[3] };
    for (int i = 0; i < expected.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(expected[i], it.next());
    }
    assertFalse(it.hasNext());
  }

}
