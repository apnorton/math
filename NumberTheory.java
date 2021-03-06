package math;

import java.util.*;

public class NumberTheory {  
  public static void main(String[] args) {
    System.out.println(divisorFunction(0, Integer.parseInt(args[0])));
  }
  
  /**
    * The Golden Ratio.
    * $$\phi = \frac{1+\sqrt{5}}{2} \approx 1.61803$$
    */
  public final static double phi = (1 + Math.sqrt(5))/2;
  
  //My variables for caching the result of the function below
  private static ArrayList<Integer> primeList;
  private static int lastPrimeUpperLimit = -1;
  /**
    *  Uses the Sieve of Eratosthenes to find the primes less than or equal to a limit.  Runs in \(\mathcal{O}(N\lg(\lg(N)))\) time.
    *  <p>
    *  Result is stored in a private static field for fast additional calls.
    *  @param N Searches for primes less than or equal to this limit
    *  @return An ArrayList&lt;Integer&gt; containing retrieved primes (in order).
    *  Runs an implementation of the Sieve of Eratosthenes.
    */
  public static ArrayList<Integer> getPrimes(int N) {
    ArrayList<Integer> retVal = new ArrayList<Integer>(); //Holds the primes
    
    //If I've already called this once this run, reuse the data.
    if (N <= lastPrimeUpperLimit) {
      int primeListSize = primeList.size();
      for (int i = 0; i < primeListSize && primeList.get(i) < N; i++)
      retVal.add(primeList.get(i));
      
      return retVal;
    }
    
    //Make sure that stuff is initialized!
    if (primeList == null) {
      primeList = new ArrayList<Integer>();
      primeList.add(2); //Taking care of that even prime...
    }
    
    boolean[] sieve = new boolean[(N-1)/2]; //Primes are false, composites are true
    
    retVal.add(2); //Add that pesky even prime.
    
    for (int i = 0; i < sieve.length; i++) {
      //Skip the iterations if this index is composite...
      if (sieve[i]) continue;
      
      //This one is prime!  Add it to the lists...
      retVal.add(2*i + 3);
      if (2*i+3 > lastPrimeUpperLimit) primeList.add(2*i+3);
      
      //Mark off all N/(2*p) multiples, where p is the current prime.  
      //(We leave off the even multiples, hence the 2)
      for (int j = 3*i+3; j < sieve.length; j+=2*i+3) sieve[j] = true; //TODO: Start at the square of p!
    }
    
    //Cache your work!
    lastPrimeUpperLimit = N;
    
    return retVal;
  }
  
  /**
    *  Factors a number using trial division, testing only the primes under the square root of the input
    *  @param N The integer to factor
    *  @return a list of factors, accounting for multiplicity.
    */
  public static ArrayList<Integer> getFactors(int N) {
    int primeLimit = (int)(Math.sqrt(N)); //Only one prime factor can be greater than the sqrt of N&1
    ArrayList<Integer> primes = getPrimes(primeLimit); //Holds all possible prime factors
    ArrayList<Integer> factors = new ArrayList<Integer>(); //Holds the actual factors
    
    int testPrime = 0;
    for (int i = 0; N > 1 && i < primes.size(); i++) {
      testPrime = primes.get(i);
      
      //Divide out all counts of the current prime
      while (N % testPrime == 0) {
        factors.add(testPrime);
        N /= testPrime;
      }
    }
    
    //In this case, the current value of N is the *only* prime factor greater than sqrt(N)
    if (N != 1) 
      factors.add(N);
    
    return factors;
  }
  
  /**
    * The divisor function, \(\sigma\)
    * <p>
    * Implements the divisor function, as found here: http://en.wikipedia.org/wiki/Divisor_function
    * When \(x\) is 1, then this returns the sum of the divisors of n (counting n itself)
    * When \(x\) is 0, it returns the number of divisors of n.
    * @param x the divisor function parameter
    * @param N The integer to process
    * @return the value of the divisor function evaluated at $x, N$.
    */
  public static long divisorFunction(int x, int N) {
    Set<Integer> distinctPrimeFactors = new HashSet<Integer>(getFactors(N));
    long retVal = 1;
    for (Integer p : distinctPrimeFactors) {
      int m = (int)Math.pow(p, x);
      long toMultiply = 1;
      int currMul = m;
      do {
        toMultiply += currMul;
        currMul *= m;
        N /= p;
      } while (N % p == 0);
      
      retVal *= toMultiply;
    }
    return retVal;
  }
  
  /**
    * The number of integers between 0 and N (exclusive) that are coprime to N
    */
  public static int totient(int N) {
    List<Integer> primes = getFactors(N);
    int retVal = N;
    for (Integer p : primes) {
      retVal = (retVal / p) * (p-1);
    }
    
    return retVal;
  }
  
  /**
    * Determines the number of divisors of an integer
    * Operates in sub-linearithmic time, plus time for computing primes under \(\sqrt{N}\).
    * @param N The integer of which we're counting divisors.
    * @return the number of divisors of N
    */
  public static int divisorCt(int N) {
    List<Integer> primes = getPrimes((int)Math.sqrt(N));
    int divisorCt = 1;
    
    for (int i = 0; N > 1 && i < primes.size(); i++) {
      int exponent = 0;
      int currPrime = primes.get(i);
      
      //While the current prime is a factor, divide it out!
      while(N % currPrime == 0) {
        N /= currPrime;
        exponent++;
      }
      
      //(exponent+1) options for this exponent, so account for that many new cases
      divisorCt *= (exponent + 1);
    }
    
    //If N != 1, then N is the single prime factor greater than sqrt(N).
    if (N != 1) divisorCt *= 2;
    
    return divisorCt;
  }
  
  /**
    * Computes the gcd of arbitrarily many integers
    * 
    * @param args a variable-length argument list, of which to take the GCD.
    * @return the greatest common divisor of a and b
    */
  public static long gcd(long... args) {
    //Take care of illegal argument
    if (args.length < 1) {
      throw new IllegalArgumentException("gcd requires at least one argument");
    }
    else if (args.length == 1) { //GCD(a) = a
      return args[0];
    }
    else if (args.length == 2) { //Standard Euclidean algorithm
      //Ensure that a >= b
      long a = Math.max(args[0], args[1]);
      long b = Math.min(args[0], args[1]);
      
      //Euclidean algorithm
      long tmp;
      while (b > 0) {
        tmp = b;
        b = a % tmp;
        a = tmp;
      }
      
      return a;
    }
    else { // if (args.length > 2) -- this is the Euclidean algorithm for n integers
      //Adapted from Knuth AOCP, 4.5.2 Algorithm C 
      long d = args[0];
      
      for (int i = 1; d != 1 && i < args.length; i++) {
        d = gcd(args[i], d);
      }
      
      return d;
    }
  }
  
  /**
    * Computes the nth triangular number.
    * <p>
    * These are computed with the formula \(T_n = \sum_{k=1}^n k = \frac{n(n+1)}{2}\).
    * Care is taken to avoid unnecessary overflow.
    * @param n the index
    * @return the nth triangular number
    */
  public static long triangle(long n) {
    if ((n & 1) == 1)
      return ((n+1)/2)*n;
    else
      return (n/2)*(n+1);
  }
  
  /**
    * This is an inverse for Binet's formula.  
    * <p>
    * Formula from <a href="http://math.stackexchange.com/a/374760/23353">Math.SE</a>:
    * $$n=\left[ \log_\phi \sqrt{5}(F_n-\frac{1}{2}) \right],\quad n \ge 3$$
    * (where \([x]\) is the rounding function.)
    * I've manually corrected the case where \(0\le n \lt 3\), and negative inputs return -1.
    * <p>
    * It is safe to return an integer, because all long values of fib are less than fib(93).
    *
    * @param f the input number
    * @return the index of the largest Fibonacci number less than or equal to f.
    */
  public static int inverseFibonacci(long f) {
    if (f < 0) return -1;
    else if (f == 0) return 0;
    else if (f <= 2) return 1;
    else {
      return (int) Math.round(Math.log(Math.sqrt(5) * (f - 0.5))/Math.log(phi));
    }
  }

  /**
    * Computes the inverse of n modulo p using the Extended Euclidean Algorithm.
    * Assumes that n and p are coprime; undefined behavior results if they are not.
    *
    * @param n a number coprime to p
    * @param p the modulus
    * @return the inverse of n modulo p
    */
  public static long modInverse(long n, int p) {    
    long q, r0, r1, r2, s0, s1, s2, t0, t1, t2;
    
    if (n > p) n %= p; //Save some time
    
    //Initial conditions
    r0 = p; r1 = n;
    s0 = 1; s1 = 0;
    t0 = 0; t1 = 1;
    
    //Now, we use the extended euclidean algorithm
    while(r1 > 0) { //r2 is the "current r," r1 is "one r ago," and r0 is "two r's ago"
      q = r0 / r1;
      r2 = r0 - r1*q;
      
      s2 = s0 - q*s1;
      t2 = t0 - q*t1;
      
      s0 = s1; s1 = s2;
      t0 = t1; t1 = t2;
      r0 = r1; r1 = r2;
    }
    
    if (t0 < 0) t0 += p; //Make positive!
    
    return t0; //Coefficient of n in the equation "xn+yp = 1"
  } 
  
  /**
    * Returns true if the provided integer is prime
    * <p>
    * Uses repeated Miller-Rabin tests, treating the first 12 primes as bases.
    * Valid for all values < 2^64 (and a little bit above); do not extend for use with BigIntegers.
    * Runs in O(lg^3 N) time.  
    * To compute all primes less than N, this takes O(lg^3(N!)) = O(N lg^3 N) time.
    * <p>
    * The idea of this algorithm is to write n-1 = (2^s)d where d is odd and s is non-negative.
    * Then, n is an a-SPRP if either a^d = 1 (mod n) or (a^d)^2^r = -1 (mod n) for some non-negative r less than s.
    * From OEIS, if n is a-SPRP for the first 12 primes and n < 2^64, then n is prime.
    * <p>
    * Sources: https://primes.utm.edu/prove/prove2_3.html and http://oeis.org/A014233.
    * @param n the number to test
    * @return true iff n is prime
    */
  public static boolean isPrime(long n) {
    int[] b = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
    boolean isPrime = true;
    for (int i = 0; i < b.length && b[i] < n; i++) {
      long d = n-1;
      long pow = (d & ~(d-1)); //2^s
      
      while ((d&1)==0) d >>= 1;
      
      long ad = modPow(b[i], d, n);
      boolean isSPRP = (ad == 1);
      for (long r = 1; !isSPRP && r < pow; r <<= 1)
        isSPRP = (modPow(ad, r, n) == (n-1));
      
      isPrime &= isSPRP;
    }
    
    return isPrime && !(n==0 || n==1);
  }
  
  /**
    * Performs modular exponentiation
    * <p>
    * Uses the "method of repeated squares" to compute the result in O(lg N) time, where N is the exponent.
    */
  public static long modPow(long a, long n, long m) {
    long retVal = 1;
    
    for (long currMul = a%m; n > 0; n >>= 1) {
      if ((n&1) == 1) retVal = (retVal * currMul) % m;
      currMul = (currMul * currMul) % m;
    }
    
    return retVal;
  }
  
  /**
    * Calculates the nth partition number
    * <p>
    * This uses Euler's Pentagonal Theorem, so it takes O(n) time.
    */
  /*public static long partition(int n) {
    if (n == 0) return 1;
    if (n  < 0) return 0
  }*/
}