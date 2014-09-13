package math;

/**
  * @author apnorton
  * @date 9/13/2014 
  *
  * @brief A sequence object consisting of all values of the Fibonacci sequence that fit in a long (indices 0-92).
  * @par Implementation notes
  * Beyond the 93rd index, one cannot store the values in a long.  Uses a 93-element array as a memo for computation.
  */
public class FibonacciSequence extends Sequence<Long> {
  private long[] memo = new long[93];
  
  //The Fibonacci sequence can be stored in a Long iff the index is less than 93
  public FibonacciSequence() {
    super(0, 92); //Only defined on 0 to 92.
    
    memo[1] = 1;
  }
  
  /**
    * Uses memoization to compute the n-th Fibonacci number
    * @param n the index
    * @return the n-th Fibonacci number
    */
  @Override
  public Long get(long n) throws IllegalArgumentException {
    int i = (int) n; //Don't need a long index
    
    if (i < getStartIndex())
      throw new IllegalArgumentException("The Fibonacci sequence is only defined for non-negative integer indices.");
    else if (i > getStopIndex())
      throw new IllegalArgumentException("The value of fib(i) would overflow a long with the provided value of i.");
    else if (i == 0)
      return 0L;
    else if (memo[i] == 0) //If we haven't computed the values yet
      memo[i] = memo[i-1] + memo[i-2];
      
    return memo[i];
  }
}