package itertools.iterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Wraps a BufferedReader to provide iteration over the lines in the file.
 * 
 * @author jelsas
 * 
 */
public class FileLineIterator extends IteratorWrapper<String> {
  BufferedReader in;

  public FileLineIterator(String filename) throws IOException {
    in = new BufferedReader(new FileReader(filename));
  }

  public FileLineIterator(String filename, int bufferSize) throws IOException {
    in = new BufferedReader(new FileReader(filename), bufferSize);
  }

  /**
   * Returns the next line of the file or null if there are no more lines, or if
   * an error occurred reading the file. The file is close & cleaned up if an
   * error occurred or the end of the file is reached.
   */
  @Override
  public String getNext() {
    if (in == null) return null;
    String next;
    try {
      next = in.readLine();
    } catch (IOException e) {
      cleanup();
      next = null;
    }
    return next;
  }

  private void cleanup() {
    try {
      in.close();
    } catch (IOException e) {
      // ignore
    }
    in = null;
  }
}
