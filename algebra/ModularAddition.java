package math.algebra;

public class ModularAddition implements GroupOperation<Integer> {
  private int n;
  
  public ModularAddition(int n) {
    //Make sure the modulus is an acceptable value
    if (n < 2 || n > Integer.MAX_VALUE / 2)
      throw new IllegalArgumentException("Provided modulus is too large for integer addition");
    
    this.n = n;
  }
  
  public Integer op(Integer a, Integer b) {
    return normalize(a+b);
  }
  
  //Returns the additive inverse of an integer modulo n
  public Integer inverseOf(Integer a) {
    int retVal = a % n;
    
    if (retVal > 0)
      retVal = -retVal + n;
    else if (retVal < 0)
      retVal = -retVal;
    
    return retVal;
  }
  
  public boolean isIdentity(Integer a) {
    return a == 0;
  }
  
  //Converts a given integer into its standard representation modulo p.
  private int normalize(int a) {
    int retVal = a % n;
    if (retVal < 0) retVal += n;
    
    return retVal;
  }
}