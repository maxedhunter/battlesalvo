package cs3500.pa03.view;

import java.io.IOException;

/**
 * Represents a mock appendable that will always throw errors.
 */
public class MockAppendable implements Appendable {

  /**
   * Will throw an IOException.
   *
   * @param csq The character sequence to append.  If {@code csq} is
   *            {@code null}, then the four characters {@code "null"} are
   *            appended to this Appendable.
   * @return an exception
   * @throws IOException
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  /**
   * Will throw an IOException.
   *
   * @param csq   The character sequence from which a subsequence will be
   *              appended.  If {@code csq} is {@code null}, then characters
   *              will be appended as if {@code csq} contained the four
   *              characters {@code "null"}.
   * @param start The index of the first character in the subsequence
   * @param end   The index of the character following the last character in the
   *              subsequence
   * @return an IOException
   * @throws IOException
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  /**
   * Will throw an IOException.
   *
   * @param c The character to append
   * @return an IOException
   * @throws IOException
   */
  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
