package itertools.iterator;

import static org.junit.Assert.*;
import itertools.IBuilder;

import org.junit.Test;

public class DropwhileIteratorTest {

  @Test
  public void testDropwhileIterator() {
    // Create an ibuilder that counts to 100
    IBuilder<Integer> ibuilder = new IBuilder<Integer>(new CountingIterator());
    ibuilder = ibuilder.slice(0, 100, 1);

    // Dropwhile less than 80
    DropwhileIterator<Integer> it = new DropwhileIterator<Integer>(ibuilder
        .iterator()) {
      @Override
      public boolean condition(Integer item) {
        return item < 80;
      }
    };

    int i;
    for (i = 80; i < 100; ++i) {
      assertTrue(it.hasNext());
      assertEquals(i, it.next().intValue());
    }
    assertEquals(i, 100);
    assertFalse(it.hasNext());

  }

}
