#include <stdint.h>
#include <algorithm>

namespace Math {
  namespace NumberTheory {
    /**
      * Computes the GCD of two 64-bit, unsigned integers
      * gcd(x, 0) = x for all x
      */
    uint64_t gcd(uint64_t a, uint64_t b);
    
    /**
      * Computes the coefficents to solve the Bezout Identity; i.e.
      * this finds x and y such that x*a + y*b = gcd(a, b).
      * Return a triple so I can also get the gcd!
      * (Don't want to waste work)
      */
    void bezout(int64_t a, int64_t b, int64_t* x, int64_t* y, int64_t* d);
    
    bool is_prime(uint64_t n);
    uint64_t mod_pow(uint64_t a, uint64_t n, uint64_t m);
  }
}
