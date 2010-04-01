package itertools.iterator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MergingIteratorTest {
  String[][] data = { { "a", "d", "g", "j" }, { "b", "e", "h", "k" },
      { "c", "f", "i", "l" }, { "m" } };
  List<Iterator<String>> iters;

  @Before
  public void setUp() throws Exception {
    iters = new ArrayList<Iterator<String>>();
    for (String[] s : data) {
      iters.add(Arrays.asList(s).iterator());
    }
  }

  @Test
  public void testMergingIterator() {
    // merged order should be lexical order of all the data.
    List<String> allData = new ArrayList<String>();
    for (String[] ss : data) {
      for (String s : ss) {
        allData.add(s);
      }
    }
    Collections.sort(allData, String.CASE_INSENSITIVE_ORDER);

    MergingIterator<String> mit = new MergingIterator<String>(iters,
        String.CASE_INSENSITIVE_ORDER);

    for (int i = 0; i < allData.size(); ++i) {
      assertTrue(mit.hasNext());
      String trueNext = allData.get(i);
      String mergedNext = mit.next();
      assertEquals(trueNext, mergedNext);
    }
    assertFalse(mit.hasNext());
  }

}
