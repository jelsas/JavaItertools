package itertools.functions;

import java.io.IOException;

import itertools.IterableIterator;
import itertools.iterator.FileLineIterator;

/**
 * A few static utility {@link Mapper} objects for operating on files.
 * 
 * @author jelsas
 * 
 */
public class Files {
  /**
   * Opens a filename String as a file.
   */
  public static final BranchingMapper<String, IterableIterator<String>, String> OPEN = new _openFiles();

  private static class _openFiles implements
      BranchingMapper<String, IterableIterator<String>, String> {
    public IterableIterator<String> map(String input) {
      try {
        return new IterableIterator<String>(new FileLineIterator(input));
      } catch (IOException e) {
        return null;
      }
    }
  }

}
