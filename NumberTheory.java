package MathStuff;

import java.util.*;

public class NumberTheory {
   public static void main(String[] args) {
      //Place tests here...
   }
   

   //My variables for caching the result of the function below
   private static ArrayList<Integer> primeList;
   private static int lastPrimeUpperLimit = -1;
   /**
    *  Uses the Sieve of Eratosthenes to find the primes less than or equal to a limit.  Runs in O(N^2) time.
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
         
         //This one is prime!  Add it to the list...
         retVal.add(2*i + 3);
         if (2*i+3 > lastPrimeUpperLimit) primeList.add(2*i+3);
         
         //Mark off multiples
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
      int maxFactor = N/(2 + N&1); //If an odd number, the maximum factor is N/3.  If even, max factor is N/2.
      ArrayList<Integer> primes = getPrimes(maxFactor); //Holds all possible prime factors
      ArrayList<Integer> factors = new ArrayList<Integer>(); //Holds the actual factors
      
      int currIndex = 0;
      int testPrime = 0;
      while (N > 1) {
         testPrime = primes.get(currIndex);
         
         if (N % testPrime == 0) { //If it is a factor...
            factors.add(testPrime);
            N /= testPrime;
         }
         else { //Otherwise, next prime!
            currIndex++;
         }
         
      }
      
      return factors;
   }
}
