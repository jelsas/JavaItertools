package itertools.functions;

import java.io.IOException;

import itertools.IterableIterator;
import itertools.iterator.FileLineIterator;

public class Files {
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
