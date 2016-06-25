#include "Tests.h"


using namespace std;
using namespace Math;

int main() {
  Tests::test_manager.add_test(&Tests::gcd_test,     string("gcd_test: Tests the GCD function"));
  Tests::test_manager.add_test(&Tests::mod_pow_test, string("mod_pow_test: Tests the mod_pow function."));
  Tests::test_manager.add_test(&Tests::is_prime_test, string("is_prime_test: Tests the is_prime (Miller-Rabin) function."));

  Tests::test_manager.run_tests();
  return 0;
}

// Checks that the gcd function returns proper values in several
// example cases
bool Tests::gcd_test() {
  bool passed = true;
  bool test_result = true;

  int numTests     = 4;
  int firstArgs[]  = {15, 21, 2,  0, 20, };
  int secondArgs[] = {21, 15, 2, 20,  0, };
  int answers[]    = { 3,  3, 2, 20, 20, };

  // Check gcd of 15 and 21
  int a, b;
  for (int i = 0; i < numTests; i++) {
    // Get the values of "a" and "b" to test with
    a = firstArgs[i];
    b = secondArgs[i];

    cout << "\tgcd(" << a << ", " << b << ") = " << answers[i] << ": ";

    test_result = NumberTheory::gcd(firstArgs[i], secondArgs[i]) == answers[i];
    cout << (test_result ? "PASSED" : "FAILED") << endl;
    passed &= test_result;
  }

  return passed;
}

// Tests the modular exponentiation function
bool Tests::mod_pow_test() {
  bool passed      = true;
  bool test_result = true;
  
  uint32_t numTests = 4; 
  uint64_t base[]     = {     1531,     3452,      0,   17, };
  uint64_t exponent[] = { 12421095, 10124088, 124215,    0, };
  uint64_t modulus[]  = {    12521,    12459,    240, 2424, };
  uint64_t answer[]   = {      533,     1240,      0,    1, };
  
  uint64_t b, e, m;
  for (uint32_t i = 0; i < numTests; i++) {
    // Get the base, exponent, modulus triple
    b = base[i];
    e = exponent[i];
    m = modulus[i];
    
    // Get the result
    uint64_t to_check = NumberTheory::mod_pow(b, e, m);
    
    // Check result
    test_result = to_check == answer[i];
    passed &= test_result;
    
    // Output
    cout << "\t" << b << "^" << e << " = " << answer[i] << " (mod " << m << ") : ";
    cout << (test_result ? "PASSED" : "FAILED") << endl;
  }
  
  return passed;
}

bool Tests::is_prime_test() {
    bool passed = true;
    
    uint32_t numTests = 10;
    uint64_t number[] = {    2,    3, 47297, 58907, 1000000007L,    16,    75,  2000, 4328142,     0, };
    bool     answer[] = { true, true,  true,  true,        true, false, false, false,   false, false, };
    
    uint64_t p;
    bool test_result;
    for (uint32_t i = 0; i < numTests; i++) {
      p = number[i];
     
      // Check result
      test_result = NumberTheory::is_prime(p) == answer[i];
      passed     &= test_result;
      
      // UI
      if (answer[i])
        cout << "\t" << p << " is prime : ";
      else 
        cout << "\t" << p << " is not prime : ";
      
      cout << (test_result ? "PASSED" : "FAILED") << endl;
    }
    
    return passed;
}
