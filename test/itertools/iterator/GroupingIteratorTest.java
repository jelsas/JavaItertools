package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class GroupingIteratorTest {
  // ..index:.......0.....1.....2.....3.....4.....5.....6.....7.....8.....9
  String[] data = { "a1", "a2", "a3", "b1", "b2", "c1", "d1", "d2", "d3", "e1" };
  String[][] data_groups = { { data[0], data[1], data[2] },
      { data[3], data[4] }, { data[5] }, { data[6], data[7], data[8] },
      { data[9] } };
  String[][] data_groups_max2 = { { data[0], data[1] }, { data[2] },
      { data[3], data[4] }, { data[5] }, { data[6], data[7] }, { data[8] },
      { data[9] } };

  Iterator<String> it;

  @Before
  public void setUp() throws Exception {
    it = Arrays.asList(data).iterator();
  }

  @Test
  public void testGroupingIteratorIteratorOfE() {
    GroupingIterator<String> g = new GroupingIterator<String>(it) {
      @Override
      public boolean group(String e1, String e2) {
        return e1.charAt(0) == e2.charAt(0);
      }
    };

    for (int group = 0; group < data_groups.length; ++group) {
      assertTrue(g.hasNext());
      Iterator<String> nextGroup = g.next();
      for (int i = 0; i < data_groups[group].length; ++i) {
        assertTrue(nextGroup.hasNext());
        assertEquals(data_groups[group][i], nextGroup.next());
      }
    }
  }

  @Test
  public void testGroupingIteratorIteratorOfEInt() {
    GroupingIterator<String> g = new GroupingIterator<String>(it, 2) {
      @Override
      public boolean group(String e1, String e2) {
        return e1.charAt(0) == e2.charAt(0);
      }
    };

    for (int group = 0; group < data_groups_max2.length; ++group) {
      assertTrue(g.hasNext());
      Iterator<String> nextGroup = g.next();
      for (int i = 0; i < data_groups_max2[group].length; ++i) {
        assertTrue(nextGroup.hasNext());
        assertEquals(data_groups_max2[group][i], nextGroup.next());
      }
    }
  }
}
