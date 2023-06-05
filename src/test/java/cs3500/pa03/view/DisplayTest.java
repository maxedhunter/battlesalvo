package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.OpponentBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for the Display class
 */
class DisplayTest {
  Appendable output;
  private Display display;

  /**
   * Initializes display
   */
  @BeforeEach
  public void setUp() {
    output = new StringBuilder();
    display = new Display(output);
  }


  /**
   * Test if the message is being shown correctly to the user
   */
  @Test
  public void testShowPrompt() {
    String prompt = "This is a test prompt.";
    display.showPrompt(prompt);
    assertEquals(prompt + System.lineSeparator(), output.toString());
  }

  /**
   * Tests whether the message handles an append error.
   */
  @Test
  public void testShowPromptException() {
    //TODO
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
    assertEquals(expected, output.toString());
  }

  /**
   * Tests whether the display board method handles an appending error.
   */
  @Test
  void testDisplayBoardException() {
    //TODO
  }
}