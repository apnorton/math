package math.algebra;

import java.util.*;

/**
  * This class defines a "field" from abstract algebra
  * A field is defined as a set of elements of type E, with two operations: 
  *   -an "additive" operation (ADD)
  *   -a "multiplicative" operation (MUL)
  * There are inverses for both the additive operation and multiplicative operation, as well as 
  * identity elements.
  */
public class Field<E> {
  //Holds the group operations:
  private GroupOperation<E> addOp;
  private GroupOperation<E> mulOp;
  
  /**
    * Takes the two field operations as parameters
    */
  public Field(GroupOperation<E> add, GroupOperation<E> mul) {
    this.addOp = add;
    this.mulOp = mul;
  }
  
  public E add(E a, E b) {
    return addOp.op(a, b);
  }
  
  public E subtract(E a, E b) {
    return addOp.op(a, addOp.inverseOf(b));
  }
  
  public E multiply(E a, E b) {
    return mulOp.op(a, b);
  }
  
  public E divide(E a, E b) {
    return mulOp.op(a, mulOp.inverseOf(b));
  }
  
  public E additiveInverseOf(E a) {
    return addOp.inverseOf(a);
  }
  
  public E multiplicativeInverseOf(E a) {
    return mulOp.inverseOf(a);
  }
  
  /**
    * @return true iff this is the multiplicative identity
    */
  public boolean isOne(E a) {
    return mulOp.isIdentity(a);
  }
  
  /**
    * @return true iff this is the additive identity
    */
  public boolean isAdditiveIdentity(E a) {
    return addOp.isIdentity(a);
  }
}