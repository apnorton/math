package math;

public class Combinatorics { 
  /**
    * Given an integer n, returns the next integer with the same number of set bits.
    * <p>
    * Known as Gosper's hack.  Source: http://programmers.stackexchange.com/a/67087/51334
    * Useful for iterating over combinations containing k elements.
    */
    
    public static int gosper(int n) {
      int c = n & -n;
      int r = n + c;
      return (((r^n) >>> 2) / c) | r;
    }
   /**
    * Computes the binomial coefficient $\binom{n}{k}$.
    * <p>
    * I'm careful with division and multiplication here to preserve (as much as possible)
    * the validity of the answer accouting for overflow.
    */
  public static long binom(long n, long k) {
    if (k > (n>>1)) return binom(n, n-k);
    long res = n;
    
    for (int i = 1; i < k; i++) {
      res = (res * (n - i)) / (i+1);
    }
    
    return res;
  }
  
  /**
    * Computes the binomial coefficient \(\binom{n}{r} \pmod p\), where \(p\) is a prime number
    * Uses Lucas' Theorem and modular inverses.
    * Assumes that \(p\) is prime--otherwise, undefined behavior results.
    * @param n the \(n\) in \(\binom{n}{r} \pmod p\)
    * @param r the \(r\) in \(\binom{n}{r} \pmod p\)
    * @param p the \(p\) in \(\binom{n}{r} \pmod p\)
    * @return the result of \(\binom{n}{r} \pmod p\)
    */
  public static long binomMod(long n, long r, int p) {
    long retVal = 1;
    
    //If n and r are less than p, then we just use the standard process
    if (n < p && r < p) {      
      //Computes the denominator
      for (long i = n; i > n-r; i--)
        retVal = (retVal * i) % p;

      //Now, compute the denominator
      for (long i = r; i > 0; i--)
        retVal = (retVal * NumberTheory.modInverse(i, p)) % p;
      //Note: "denominator" is a figure of speech--
      //  really, I'm multiplying by the modular inverse
    }
    else { //We use Lucas' Theorem!
      long nCurr, rCurr; //The current digit of n and r in base p.
      while(r > 0 || n > 0) {
        //Grab the digits
        nCurr = n % p; 
        rCurr = r % p;
        
        //The return value is the product (mod p) of binom(nCurr, rCurr)
        retVal = (retVal * binomMod(nCurr, rCurr, p)) % p;
        
        //Strip off the digits we've just used
        n /= p; 
        r /= p;
      }
    }
    return retVal;
  }
}