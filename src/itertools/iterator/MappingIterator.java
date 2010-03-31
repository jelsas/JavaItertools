package itertools.iterator;

import java.util.Iterator;

/**
 * Wraps an interator producing I's and produces O's
 * 
 * @author jelsas
 * 
 * @param <I>
 *          Input type
 * @param <O>
 *          Output type
 */
public abstract class MappingIterator<I, O> implements Iterator<O> {
  private Iterator<I> in;

  public MappingIterator(Iterator<I> in) {
    this.in = in;
  }

  public boolean hasNext() {
    return in.hasNext();
  }

  public O next() {
    return map(in.next());
  }

  public void remove() {
    in.remove();
  }

  /**
   * Function mapping I to O.
   * 
   * @param in
   *          Input
   * @return Output
   */
  public abstract O map(I in);

}
