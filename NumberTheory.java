package math;

import java.util.*;

public class NumberTheory {
  public static void main(String[] args) {
    System.out.printf("The prime factors of %d are %s\n", 1281942112, getFactors(1281942112 ));
    
  }
  

  //My variables for caching the result of the function below
  private static ArrayList<Integer> primeList;
  private static int lastPrimeUpperLimit = -1;
  /**
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
}
