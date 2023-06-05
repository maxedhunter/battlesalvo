package cs3500.pa03.view;


import cs3500.pa03.model.Board;
import java.io.IOException;

/**
 * Represents a Display class that displays messages for the user.
 */
public class Display {
  Appendable output;

  /**
   * Initializes the output stream.
   *
   * @param output where the program presents information
   */
  public Display(Appendable output) {
    this.output = output;
  }

  /**
   * Displays a prompt to the user with a line break.
   *
   * @param prompt the message that needs to be shown to the user
   */
  public void showPrompt(String prompt) {
    try {
      output.append(prompt).append(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to append the provided prompt.");
    }
  }

  /**
   * Display a board to the user.
   *
   * @param board the board that needs to be shown to the user
   */
  public void displayBoard(Board board) {
    char[][] grid = board.getGrid();

    try {
      for (int i = 0; i < board.getHeight(); i++) {
        for (int j = 0; j < board.getWidth(); j++) {
          output.append(grid[i][j] + " ");
        }
        output.append(System.lineSeparator());
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to append the provided board.");
    }
  }
}
