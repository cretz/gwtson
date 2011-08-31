package java.io;

public class StringWriter implements Writer {

  @Override
  public void close() throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new UnsupportedOperationException();
  }

}
