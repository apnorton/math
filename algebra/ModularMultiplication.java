package math.algebra;

import math.NumberTheory;

public class ModularMultiplication implements GroupOperation<Integer> {
  private int p;
  
  /**
    * @param p the modulus for this operation; assumed to be prime. (Undefined behavior if not.)
    */
  public ModularMultiplication(int p) {
    //Make sure the modulus is an acceptable value
    if (p < 2 || p > Math.sqrt(Integer.MAX_VALUE))
      throw new IllegalArgumentException("Provided modulus is too large for integer multiplication");
    
    this.p = p;
  }
  
  public Integer op(Integer a, Integer b) {
    if (!isValid(a))
      throw new IllegalArgumentException("Noninvertible element detected: " + a);
    if (!isValid(b))
      throw new IllegalArgumentException("Noninvertible element detected: " + b);

    return normalize(a*b);
  }
  
  //Returns the additive inverse of an integer modulo p
  public Integer inverseOf(Integer a) {
    if (!isValid(a))
      throw new IllegalArgumentException("Noninvertible element detected: " + a);
    return (int) NumberTheory.modInverse(a, p);
  }
  
  public boolean isIdentity(Integer a) {
    return a == 1;
  }
  
  //Converts a given integer into its standard representation modulo p.
  private int normalize(int a) {
    int retVal = a % p;
    if (retVal < 0) retVal += p;
    
    return retVal;
  }
  
  //Ensures this element is valid for this operation (has an inverse)
  private boolean isValid(int a) {
    return ((a % p) != 0);
  }
  
}