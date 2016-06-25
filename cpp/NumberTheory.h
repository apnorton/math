#include <stdint.h>
#include <algorithm>

namespace Math {
  namespace NumberTheory {
    /**
     * Computes the GCD of two 64-bit, unsigned integers
     * gcd(x, 0) = x for all x
     */
    uint64_t gcd(uint64_t a, uint64_t b);
    
    bool is_prime(uint64_t n);
    uint64_t mod_pow(uint64_t a, uint64_t n, uint64_t m);
  }
}
