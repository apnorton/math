#include <string>
#include <iostream>
#include <vector>

namespace Math {
  namespace Tests {
    typedef bool(*TestPointer)();

    class TestManager {
      public:
        TestManager();
        ~TestManager();
        void add_test(TestPointer to_add, std::string description);
        void run_tests();

      private:
        std::vector<TestPointer> tests;
        std::vector<std::string> descriptions;
    };
  }
}
