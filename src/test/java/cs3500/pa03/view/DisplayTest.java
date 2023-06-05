package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.OpponentBoard;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for the Display class
 */
class DisplayTest {
  private final InputStream systemIn = System.in;
  private final PrintStream systemOut = System.out;
  private ByteArrayOutputStream testOut;
  private Display display;

  /**
   * Set the output
   */
  @BeforeEach
  public void setUpOutput() {
    testOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(testOut));
  }

  /**
   * Get the output
   */
  private String getOutput() {
    return testOut.toString();
  }

  /**
   * Restore the input and output to system default
   */
  @AfterEach
  public void restoreSystemInputOutput() {
    System.setIn(systemIn);
    System.setOut(systemOut);
  }

  /**
   * Initializes display
   */
  @BeforeEach
  public void setUp() {
    display = new Display();
  }


  /**
   * Test if the message is being shown correctly to the user
   */
  @Test
  public void testShowPrompt() {
    String prompt = "This is a test prompt.";
    display.showPrompt(prompt);
    assertEquals(prompt + System.lineSeparator(), getOutput());
  }

  /**
   * Test if the message is being shown correctly to the user
   */
  @Test
  void testDisplayBoard() {
    OpponentBoard board = new OpponentBoard(6, 6);
    board.initialize();
    String expected = "- - - - - - \n"
        + "- - - - - - \n"
        + "- - - - - - \n"
        + "- - - - - - \n"
        + "- - - - - - \n"
        + "- - - - - - \n";

    display.displayBoard(board);
    assertEquals(expected, getOutput());
  }
}