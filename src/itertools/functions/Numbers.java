package itertools.functions;

/**
 * Static access to many common numeric functions for use as {@link Mapper}s.
 * 
 * @author jelsas
 * 
 */
public class Numbers {
  /** Converts any numeric type to an Integer */
  public static final Mapper<? extends Number, Integer> INT = new _int();
  /** Converts any numeric type to an Long */
  public static final Mapper<? extends Number, Long> LONG = new _long();
  /** Converts any numeric type to an Float */
  public static final Mapper<? extends Number, Float> FLOAT = new _float();
  /** Converts any numeric type to an Double */
  public static final Mapper<? extends Number, Double> DOUBLE = new _double();

  private static class _int implements Mapper<Number, Integer> {
    public Integer map(Number input) {
      return input.intValue();
    }
  }

  private static class _long implements Mapper<Number, Long> {
    public Long map(Number input) {
      return input.longValue();
    }
  }

  private static class _float implements Mapper<Number, Float> {
    public Float map(Number input) {
      return input.floatValue();
    }
  }

  private static class _double implements Mapper<Number, Double> {
    public Double map(Number input) {
      return input.doubleValue();
    }
  }
}
