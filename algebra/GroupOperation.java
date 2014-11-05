package math.algebra;

/**
  * This interface describes an operation on elements of type E.
  */
public interface GroupOperation<E> {
  /**
    * 
    */
  public E op(E a, E b);
  
  public E inverseOf(E a);
  
  public boolean isIdentity(E a);
}