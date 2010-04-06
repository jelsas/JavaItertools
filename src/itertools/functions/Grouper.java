package itertools.functions;

/**
 * Interface providing a {@link #group(Object, Object)} function.
 * 
 * @author jelsas
 * 
 * @param <E>
 */
public interface Grouper<E> {
  /**
   * The function to define the groups. Note: this function must return true if
   * passed the same object as both arguments.
   * 
   * @param e1
   *          The first item to compare.
   * @param e2
   *          The second item to compare.
   * @return true if the two items should be in the same group, false otherwise.
   */
  public boolean group(E e1, E e2);
}
