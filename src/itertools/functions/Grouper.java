package itertools.functions;

/**
 * Interface providing a {@link #group(Object, Object)} function.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public interface Grouper<E> {
  public boolean group(E e1, E e2);
}
