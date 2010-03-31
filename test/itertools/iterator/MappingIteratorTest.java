package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class MappingIteratorTest {
  String[] data = { "ONE", "TWO", "three", "FoUr" };
  Iterator<String> it = null;

  @Before
  public void setUp() throws Exception {
    it = Arrays.asList(data).iterator();
  }

  @Test
  public void testMappingIterator() {
    it = new MappingIterator<String, String>(it) {
      @Override
      public String map(String in) {
        return in.toLowerCase();
      }
    };

    for (int i = 0; i < data.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(data[i].toLowerCase(), it.next());
    }
    assertFalse(it.hasNext());
  }

}
