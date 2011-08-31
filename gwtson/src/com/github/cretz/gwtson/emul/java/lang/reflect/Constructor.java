package java.lang.reflect;

public class Constructor<T> extends AccessibleObject implements GenericDeclaration {

  public T newInstance() {
    throw new UnsupportedOperationException();
  }
  
  public void setAccessible(boolean val) {
    throw new UnsupportedOperationException();
  }
}
