package itertools.functions;

/**
 * Static access to many common string functions for use as Mappers.
 * 
 * TODO: may also want to add parameterized mappers (split on regex, replace,
 * etc)
 * 
 * @author jelsas
 * 
 */
public class Strings {
  /** Calls String.trim(). */
  public static final Mapper<String, String> TRIM = new _trim();
  /** Calls String.split("\\s+"). */
  public static final Mapper<String, String[]> SPLIT = new _split();
  /** Calls String.toUpper(). */
  public static final Mapper<String, String> UPPER = new _upper();
  /** Calls String.toLower(). */
  public static final Mapper<String, String> LOWER = new _lower();
  /** Calls Integer.parseInt(String). */
  public static final Mapper<String, Integer> TO_INT = new _parseInt();
  /** Calls Long.parseLong(String). */
  public static final Mapper<String, Long> TO_LONG = new _parseLong();
  /** Calls Float.parseFloat(String). */
  public static final Mapper<String, Float> TO_FLOAT = new _parseFloat();
  /** Calls Double.parseDouble(). */
  public static final Mapper<String, Double> TO_DOUBLE = new _parseDouble();

  private static class _trim implements Mapper<String, String> {
    public String map(String input) {
      if (input == null) return null;
      return input.trim();
    }
  }

  private static class _split implements Mapper<String, String[]> {
    public String[] map(String input) {
      if (input == null) return null;
      return input.split("\\s+");
    }
  }

  private static class _upper implements Mapper<String, String> {
    public String map(String input) {
      if (input == null) return null;
      return input.toUpperCase();
    }
  }

  private static class _lower implements Mapper<String, String> {
    public String map(String input) {
      if (input == null) return null;
      return input.toLowerCase();
    }
  }

  private static class _parseInt implements Mapper<String, Integer> {
    public Integer map(String input) {
      if (input == null) return null;
      return Integer.parseInt(input);
    }
  }

  private static class _parseLong implements Mapper<String, Long> {
    public Long map(String input) {
      if (input == null) return null;
      return Long.parseLong(input);
    }
  }

  private static class _parseFloat implements Mapper<String, Float> {
    public Float map(String input) {
      if (input == null) return null;
      return Float.parseFloat(input);
    }
  }

  private static class _parseDouble implements Mapper<String, Double> {
    public Double map(String input) {
      if (input == null) return null;
      return Double.parseDouble(input);
    }
  }
}
