Math Repository
===============

This is my repository of Math-related Java code.  It is a package that can be imported.

See the `docs` folder for JavaDoc-generated documentation that lists all current features.  To use the documentation, you must have internet connection; currently, the documentation relies on connectivity to the MathJax website to render mathematical forumlae.  I will fix this, it's just not on the top of my priority list right now.

Field
-----

I am working towards a generalized linear system solver, slowly but surely.  Specifically, I want to be able to row-reduce matrices over arbitrary fields; for example, row reduce over the Rational numbers, or over the Integers modulo 13, etc.  Therefore, I have created a `Field` class; this is as close as I can (currently) reasonably get to the concept of a [mathematical field](http://en.wikipedia.org/wiki/Field_%28mathematics%29) from modern algebra.

Note that my `Field` class has a generic parameter; this is to specify a general element of the underlying set.  To construct a `Field` object, you need two `GroupOperation`s.  A `GroupOperation` is an iterface that specifies an invertible binary operation that has an identity element.

I will add more documentation for these features; however, with a bit of knowledge of algebra, one can figure out the organization quite easily.

Sequences
---------

One feature of this library with which I am quite happy is the idea of a "Sequence."  Essentially, a sequence is an iterable function defined for all integral values.
To create a sequence, all one needs to do is extend `Sequence` and override the `get(long n)` method.  This method should compute and return the value of the sequence at n
in an "iterator-friendly" way--this method should be optimized for repeated calls to incrementing values of `n`.

If you want your sequence to be defined on a particular range of values for `n`, for example `2 <= n <= 439`, set the `startIndex` and `stopIndex` fields when you construct your sequence.  
It is the responsibility of the your sequence's `get` method to ensure that no calls are made outside of this range.

As an example of a use-case, consider the following program that prints out all even Fibonacci numbers less than 1000:

```
public static void main(String[] args) {
  Sequence<Long> fibSeq = new FibonacciSequence();
  
  //Iterate over all Fibonacci numbers that can be represented by a Long
  for (Long f : fibSeq) {
    if (f > 1000) break; //Stop of you pass 1000
    
    //Print out the even ones
    if (f % 2 == 0) 
      System.out.println(f);
  }
}
```

Important sequences, such as the sequence of prime numbers, the Fibonacci sequence, etc., can be made into classes that extend Sequence, allowing intuitive iteration over all values within a certain range.

To add
------

  -  NumberTheory.java
      - phi: Euler's totient
      - fib: Returns list of Fibonacci numbers
  -  LinearAlgebra.java 
      - Performs linear algebra over an arbitrary field
      - row reduction over a field
      - Determinant
      - Eigenvalue?
  -  Graph.java
  -  Tree.java
  -  Matrix.java
