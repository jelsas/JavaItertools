package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class RepeatingIteratorTest {
  private static final String S = "foo";

  @Test
  public void testRepeatingIterator() {
    Iterator<String> it = new RepeatingIterator<String>(S);
    for (int i = 0; i < 100; ++i) {
      assertTrue(it.hasNext());
      assertSame(it.next(), S); // Same, not just equal
    }
    assertTrue(it.hasNext());
  }
}
