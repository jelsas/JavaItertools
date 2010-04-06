package itertools.iterator;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class TakewhileIteratorTest {

  @Test
  public void testTakewhileIterator() {
    Iterator<Integer> it = new TakewhileIterator<Integer>(
        new CountingIterator()) {
      @Override
      public boolean condition(Integer item) {
        return item < 10;
      }
    };
    
    for (int i = 0; i < 10; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
    assertFalse(it.hasNext());
  }

}
