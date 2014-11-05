package math;

public class Matrix<E> {
  private E[][] m;
  
  public Matrix(E[][] array) {
    m = Arrays.deepcopy(array);
  }
}