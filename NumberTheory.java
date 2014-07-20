package math;

import java.util.*;

public class NumberTheory {
  public static void main(String[] args) {
    System.out.printf("The prime factors of %d are %s\n", 1281942112, getFactors(1281942112));
    System.out.printf("The number of divisors of %d is %d\n", 97, divisorCt(97));
    System.out.printf("gcd(%d, %d) = %d\n", 134, 583, gcd(134, 583));
    System.out.printf("gcd(%d, %d) = %d\n", 1281942112, 1123124281942112L, gcd(1281942112, 1123124281942112L));
    System.out.printf("gcd(%d, %d, %d) = %d\n", 45, 15, 65, gcd(45, 15, 65));
  }
  

  //My variables for caching the result of the function below
  private static ArrayList<Integer> primeList;
  private static int lastPrimeUpperLimit = -1;
  /**
    *  getPrimes(N)
    *  Uses the Sieve of Eratosthenes to find the primes less than or equal to a limit.  Runs in O(N*lg(lg(N))) time. (?)
    *  Result is stored in a private static field for fast additional calls.
    *  @param N Searches for primes less than or equal to this limit
    *  @return An ArrayList<Integer> containing retrieved primes (in order).
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
      for (int j = 3*i+3; j < sieve.length; j+=2*i+3) sieve[j] = true;
    }
    
    //Cache your work!
    lastPrimeUpperLimit = N;
    
    return retVal;
  }
  
  /**
    *  getFactors(N)
    *  Factors a number using trial division, testing only the primes under the square root of the input
    *  @param N The integer to factor
    *  @return an ArrayList<Integer> of factors, accounting for multiplicity.
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
    * divisorCt(N)
    * Determines the number of divisors of an integer
    * Operates in sub-linearithmic time, plus time for computing primes under sqrt(N).
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
    * gcd(a, b, c, ...)
    * 
    * @param args a variable-length argument list, of which to take the GCD.
    * @return the greatest common divisor of a and b
    */
  public static long gcd(long... args) {
    //Take care of illegal argument
    if (args.length < 1) {
      throw new IllegalArgumentException("gcd requires more than one argument");
    }
    else if (args.length == 1) { //GCD(a) = a
      return args[0];
    }
    else if (args.length == 2) { //Standard Euclidean algorithm
      long a = Math.max(args[0], args[1]);
      long b = Math.min(args[0], args[1]);
      long tmp;
      
      //Euclidean algorithm
      while (b > 0) {
        tmp = b;
        b = a % tmp;
        a = tmp;
      }
      
      return a;
    }
    else if (args.length > 2) { //Euclidean algorithm for n integers
      //Adapted from Knuth AOCP, 4.5.2 Algorithm C 
      long d = args[0];
      
      for (int i = 1; d != 1 && i < args.length; i++) {
        d = gcd(args[i], d);
      }
      
      return d;
    }
    
    return -1; //Never happens, but Java needs a return...
  }
}
