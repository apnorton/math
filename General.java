package math;

import java.util.*;

/**
  * This class contains general tools that are useful for a broad range of mathematics, but aren't
  * tied to a specific subject
  */
public class General {
  /**
    * Adds up a bunch of numbers
    */
  public static long sum(long... a) {
    long sum = 0;
    for (long n : a) sum += n;
    return sum;
  }
  
  /**
    * Multiplies a bunch of numbers
    */
  public static long multiply(long... a) {
    long product = 1;
    for (long n : a) product *= n;
    return product;
  }
  
  /**
    * Determines if two positive integers are permutations of one another.
    * <p>
    * Two positive integers are permutations if they contain the same digits in the same quantities.
    * For instance, 1234 and 4321 are permutations.  Also, 1203 and 1230 are permutations of 
    * each other, while 0123 is not.  (Leading zeros are not allowed in number definitions.)
    * Finally, n is a permutation of itself, for all integers n.
    */
  public static boolean isPermute(int a, int b) {
    if (a < 0 || b < 0) return false;
    
    int[] aDigitCt = new int[10];
    int[] bDigitCt = new int[10];
    while (a > 0) {
      aDigitCt[a % 10]++;
      a /= 10;
    }
    while (b > 0) {
      bDigitCt[b % 10]++;
      b /= 10;
    }
    
    return Arrays.equals(aDigitCt, bDigitCt);
  }
  
  /**
    * Returns true if the provided number is a palindrome, false otherwise.
    * @param n the number
    * @return true if the provided number is a palindrome, false otherwise.
    */
  public static boolean isPalindrome(long n) {
    char[] digits = Long.toString(n).toCharArray();
    boolean retVal = true;
    
    for (int i = 0; i < digits.length / 2; i++) {
      retVal &= digits[i] == digits[digits.length - i - 1];
    }
    
    return retVal;
  }  
}