package itertools.iterator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ZippingIteratorTest {
  String[][] data = { { "a", "b", "c" }, { "1", "2", "3" },
      { "w", "x", "y", "z" } };
  List<Iterator<String>> iters;

  @Before
  public void setUp() throws Exception {
    iters = new ArrayList<Iterator<String>>(data.length);
    for (int i = 0; i < data.length; ++i) {
      iters.add(Arrays.asList(data[i]).iterator());
    }
  }

  @Test
  public void testZippingIterator() {
    Iterator<List<String>> it = new ZippingIterator<String>(iters);

    // find the length of the longest iterator
    int max_index = 0;
    for (String[] d : data) {
      max_index = Math.max(max_index, d.length);
    }

    for (int col = 0; col < max_index; ++col) {
      assertTrue(it.hasNext());
      List<String> next = it.next();
      assertEquals(next.size(), data.length);
      for (int row = 0; row < next.size(); ++row) {
        if (data[row].length <= col) {
          assertNull(next.get(row));
        } else {
          assertEquals(next.get(row), data[row][col]);
        }
      }
    }
  }
}
