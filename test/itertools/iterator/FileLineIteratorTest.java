package itertools.iterator;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FileLineIteratorTest {
  File tempFile;
  String[] lines = { "one", "two", "three", "four" };

  @Before
  public void setUp() throws Exception {
    tempFile = File.createTempFile(this.getClass().getName(), null);
    BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
    for (String l : lines) {
      out.write(l);
      out.newLine();
    }
    out.close();
  }

  @Test
  public void testFileLineIterator() {
    FileLineIterator it;
    try {
      it = new FileLineIterator(tempFile.getAbsolutePath());
    } catch (IOException e) {
      fail(e.getMessage());
      return;
    }

    for (int i = 0; i < lines.length; ++i) {
      assertTrue(it.hasNext());
      assertEquals(lines[i], it.next());
    }
    assertFalse(it.hasNext());
  }

}
