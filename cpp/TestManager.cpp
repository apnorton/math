#include "TestManager.h"

using namespace std;
using namespace Math::Tests;

TestManager::TestManager() {
  // Do nothing (setup is trivial)
}

TestManager::~TestManager() {
  // Clear out the vectors  
  tests.clear();
  descriptions.clear();
}

void TestManager::add_test(TestPointer test_pointer, string test_description) {
  tests.push_back(test_pointer);
  descriptions.push_back(test_description);
}

void TestManager::run_tests() {
  int num_tests  = tests.size(); // = descriptions.size()
  int num_passed = 0;
  for (int i = 0; i < num_tests; i++) {
    cout << "Running: " << descriptions[i] << endl;
    bool passed = tests[i]();
    if (passed) {
      cout << "Test passed!" << endl << endl;
      num_passed++;
    }
    else {
      cout << "Test failed. :(" << endl << endl;
    }
  }

  cout << "Passed " << num_passed << "/" 
       << num_tests << " tests." << endl;
}
