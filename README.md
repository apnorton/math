Math Repository
===============

This is my repository of Math-related Java code.  It is a package that can be imported.

See the `docs` folder for Doxygen-generated documentation that lists all current features.

Sequences
---------

One feature of this library with which I am quite happy is the idea of a "Sequence."  Essentially, a sequence is an iterable function defined for all integral values.
To create a sequence, all one needs to do is extend `Sequence` and override the `get(long n)` method.  This method should compute and return the value of the sequence at n
in an "iterator-friendly" way--this method should be optimized for repeated calls to incrementing values of `n`.

If you want your sequence to be defined on a particular range of values for `n`, for example `2 <= n <= 439`, set the `startIndex` and `stopIndex` fields when you construct your sequence.  
It is the responsibility of the your sequence's `get` method to ensure that no calls are made outside of this range.

To add
------

  -  NumberTheory.java
      - phi: Euler's totient
      - modInverse: Compute the inverse of an element modulo n
      - fib: Returns list of Fibonacci numbers
  -  Combinatorics.java
      - fact: factorial (should throw error on overflow)
      - binom: binomial coefficient (should throw error on overflow)
  -  Graph.java
  -  Tree.java
  -  Matrix.java