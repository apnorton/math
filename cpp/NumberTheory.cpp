#include "NumberTheory.h"
#include<iostream>
using namespace Math;

/**
  * Uses the Euclidean Algorithm to compute the GCD of two integers
  */
uint64_t NumberTheory::gcd(uint64_t a, uint64_t b) {
  if (b > a) {
    std::swap(a, b);
  }
  if (!b) {   // gcd(a, 0) = a
    return a;
  }

  int tmp;
  while (b) {
    tmp = a;
    a   = b;
    b   = tmp % b;
  }
  
  return a; 
}

/**
  * Finds solutions (x, y) to Bezout's identity:
  *     x*a + y*b = gcd(a, b)
  *
  * Note that x and y need not be positive.
  * This also computes the GCD, but we throw away the work because
  * I don't have a "triple" class yet.
  *
  * Can be used to compute the modular inverse of an integer. 
  * If (a, m) != 1, undefined behavior results. (m need not be prime)
  * Returns the solution to the equation
  * ax+by = gcd(a, b);
  */
void NumberTheory::bezout(int64_t a, int64_t b, int64_t* x, int64_t* y, int64_t* d) {
  // We have three sequences, with a 2nd-order recurrence
  int64_t r0, r1, r2,
          s0, s1, s2,
          t0, t1, t2,
          q;

  r1 = a, r2 = b;
  s1 = 1; s2 = 0;
  t1 = 0; t2 = 1;
  
  // Need r1 >= r2
  if (a < b)
    std::swap(r1, r2);
  while (r2 != 0) {
    // Perform a shift down a level:
    r0 = r1; r1 = r2;
    s0 = s1; s1 = s2;
    t0 = t1; t1 = t2;
    
    q  = r0 / r1;   // TODO: remove this division bc speed (should be possible)
    r2 = r0 - q*r1;
    s2 = s0 - q*s1;
    t2 = t0 - q*t1;
  }
  
  *d = r1;
  if (a >= b) { // No swap on r1 and r2 happened above
    *x = s1;
    *y = t1;
  }
  else { // We had a swap before! Better swap return values
    *x = t1;
    *y = s1;
  }
}

/**
  * Returns true if the provided integer is prime
  * 
  * Uses repeated Miller-Rabin tests, treating the first 12 primes as bases.
  * Valid for all values < 2^64 (and a little bit above); do not extend for use with BigIntegers.
  * Runs in O(lg^3 N) time.  
  * To compute all primes less than N, this takes O(lg^3(N!)) = O(N lg^3 N) time.
  * 
  * The idea of this algorithm is to write n-1 = (2^s)d where d is odd and s is non-negative.
  * Then, n is an a-SPRP if either a^d = 1 (mod n) or (a^d)^2^r = -1 (mod n) for some non-negative r less than s.
  * From OEIS, if n is a-SPRP for the first 12 primes and n < 2^64, then n is prime.
  * 
  * Sources: https://primes.utm.edu/prove/prove2_3.html and http://oeis.org/A014233.
  * @param n the number to test
  * @return true iff n is prime
  */
bool NumberTheory::is_prime(uint64_t n) {
  uint32_t arraySize = 12;
  uint64_t b[]       = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
  bool isPrime = true;
  for (uint32_t i = 0; i < arraySize && b[i] < n && isPrime; i++) {
    uint64_t d = n-1;
    uint64_t pow = (d & ~(d-1)); //2^s
    
    while ((d&1)==0) d >>= 1;
    
    uint64_t ad = mod_pow(b[i], d, n);
    bool isSPRP = (ad == 1);
    for (long r = 1; !isSPRP && r < pow; r <<= 1)
      isSPRP = (mod_pow(ad, r, n) == (n-1));
    
    isPrime &= isSPRP;
  }
  
  return isPrime && !(n==0 || n==1);
}

/**
  * Performs modular exponentiation
  * 
  * Uses the "method of repeated squares" to compute the result in O(lg N) time, where N is the exponent.
  */
uint64_t NumberTheory::mod_pow(uint64_t a, uint64_t n, uint64_t m) {
  uint64_t retVal = 1;
  
  for (uint64_t currMul = a%m; n > 0; n >>= 1) {
    if ((n&1) == 1) retVal = (retVal * currMul) % m;
    currMul = (currMul * currMul) % m;
  }
  
  return retVal;
}
