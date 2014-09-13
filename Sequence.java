package math;

import java.util.*;

public abstract class Sequence<T extends Number> implements Iterable<T>{
  private final long startIndex;
  private final boolean finiteSequence;
  private final long stopIndex;
  
  /**
    * Creates a finite sequence with the given start and stop indices
    * @param startIndex The first index of a sequence.  Typically 0 or 1.
    * @param stopIndex The final index of this sequence.  Only use if implementation of sequence will not support values beyond this.
    */
  public Sequence(long startIndex, long stopIndex) {
    this.startIndex = startIndex;
    this.stopIndex = stopIndex;
    this.finiteSequence = true;
  }
  
  /**
    * Creates an "infinite" sequence with the given start index
    * @param startIndex The first index of a sequence.  Typically 0 or 1.
    */
  public Sequence(long startIndex) { 
    this.startIndex = startIndex;
    this.stopIndex = Long.MAX_VALUE; //To initialize... it's a dummy value
    this.finiteSequence = false;
  }
  
  /**
    * Creates an "infinite" sequence with a start index of zero
    */
  public Sequence() {
    this(0);
  }
  
  /**
    * @param n the index
    * @return the nth value of the sequence
    */
  public abstract T get(long n);
  
  /**
    * @return true iff this is a finite sequence
    */
  public boolean isFinite() {
    return this.finiteSequence;
  }
  
  /**
    * @return the starting index of the sequence
    */
  public long getStartIndex() {
    return this.startIndex;
  }
  
  /**
    * @return the stop index of the sequence, or Long.MAX_VALUE if infinite
    */
  public long getStopIndex() {  
    return this.stopIndex;
  }
  
  /**
    * @return an interator over this sequence
    */
  public Iterator<T> iterator() {
    return new SequenceIterator();
  }
  
  private class SequenceIterator implements Iterator<T> {
    private boolean overflow = false;
    private long currIndex;
    
    public SequenceIterator() {
      currIndex = startIndex;
    }
    
    /**
      * Returns true if the next element can be computed
      * <p>
      * Sequences *should* be infinite, but sometimes we want to restrict their size.
      * This method returns false in exactly two cases:
      *   - The sequence is finite, and the next item would exceed the stopIndex of the sequence.
      *   - The sequence is "infinite," but the next index would overflow a long.
      * @return true iff the next value can be computed
      */
    public boolean hasNext() {
      if (isFinite() && currIndex > stopIndex)
        return false;
      else if (overflow)
        return false;
      else
        return true; 
    }
    
    /**
      * @return the next element in the sequnce
      */
    public T next() {
      if (currIndex == Long.MAX_VALUE) 
        overflow = true;
        
      return get(currIndex++);
    }
  }
}