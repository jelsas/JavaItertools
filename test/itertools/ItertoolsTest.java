package itertools;

import static itertools.Itertools.*;

import itertools.functions.Grouper;
import itertools.functions.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ItertoolsTest {
  String[][] data = { { "a", "b", "c" }, { "1", "2", "3" },
      { "w", "x", "y", "z" } };
  List<Iterator<String>> iters;
  int totalNumElements;

  @Before
  public void setUp() throws Exception {
    totalNumElements = 0;
    iters = new ArrayList<Iterator<String>>(data.length);
    for (int i = 0; i < data.length; ++i) {
      totalNumElements += data[i].length;
      iters.add(Arrays.asList(data[i]).iterator());
    }
  }

  @Test
  public void testChainCollectionOfIteratorOfE() {
    ArrayList<String> chained = new ArrayList<String>();
    for (String[] s : data) {
      chained.addAll(Arrays.asList(s));
    }
    int idx = 0;
    for (String s : chain(iters)) {
      assertEquals(chained.get(idx), s);
      ++idx;
    }
    assertEquals(totalNumElements, idx);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testChainIteratorOfEArray() {
    ArrayList<String> chained = new ArrayList<String>();
    for (String[] s : data) {
      chained.addAll(Arrays.asList(s));
    }
    int idx = 0;
    for (String s : chain(iters.get(0), iters.get(1), iters.get(2))) {
      assertEquals(chained.get(idx), s);
      ++idx;
    }
    assertEquals(totalNumElements, idx);
  }

  @Test
  public void testCycle() {
    int idx = 0;
    for (String s : cycle(iters.get(0))) {
      assertEquals(data[0][idx % data[0].length], s);
      if (++idx > 100) break;
    }
  }

  @Test
  public void testGroupby() {
    Grouper<String> grouper = new Grouper<String>() {
      public boolean group(String e1, String e2) {
        char c1 = e1.charAt(0), c2 = e2.charAt(0);
        return (Character.isDigit(c1) && Character.isDigit(c2))
            || (Character.isLetter(c1) && Character.isLetter(c2));
      }
    };

    int i = 0;
    for (Iterator<String> ss : groupby(chain(iters), grouper)) {
      int j = 0;
      while (ss.hasNext()) {
        String s = ss.next();
        assertEquals(data[i][j], s);
        ++j;
      }
      ++i;
    }
  }

  @Test
  public void testMap() {
    Mapper<String, String> upperer = new Mapper<String, String>() {
      public String map(String input) {
        return input.toUpperCase();
      }
    };
    int idx = 0;
    for (String s : map(iters.get(0), upperer)) {
      assertEquals(data[0][idx].toUpperCase(), s);
      ++idx;
    }
    assertEquals(data[0].length, idx);
  }

  @Test
  public void testMerge() {
    ArrayList<String> all = new ArrayList<String>();
    for (String[] ss : data) {
      for (String s : ss) {
        all.add(s);
      }
    }
    Collections.sort(all);
    int idx = 0;
    for (String s : merge(iters)) {
      assertEquals(all.get(idx), s);
      idx++;
    }
    assertEquals(totalNumElements, idx);
  }

  @Test
  public void testRepeat() {
    int idx = 0;
    final String x = "foo";
    for (String s : repeat(x)) {
      assertSame(x, s);
      if (++idx > 100) break;
    }
  }

  @Test
  public void testZipCollectionOfIteratorOfE() {
    int group = 0;
    for (List<String> g : zip(iters)) {
      assertEquals(data.length, g.size());
      for (int j = 0; j < data.length; ++j) {
        if (group >= data[j].length)
          assertNull(g.get(j));
        else
          assertEquals(data[j][group], g.get(j));
      }
      ++group;
    }
  }

  @Test
  public void testZipIteratorOfEArray() {
    int group = 0;
    for (List<String> g : zip(iters.get(0), iters.get(1), iters.get(2))) {
      assertEquals(data.length, g.size());
      for (int j = 0; j < data.length; ++j) {
        if (group >= data[j].length)
          assertNull(g.get(j));
        else
          assertEquals(data[j][group], g.get(j));
      }
      ++group;
    }
  }

}
