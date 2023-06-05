package cs3500.pa03.view;


import cs3500.pa03.model.Board;

/**
 * Represents a Display class that displays messages for the user
 *
 */
public class Display {
  /**
   * Display a prompt to the user
   *
   * @param prompt the message that needs to be shown to the user
   */
  public void showPrompt(String prompt) {
    System.out.println(prompt);
  }

  /**
   * Display a board to the user
   *
   * @param board the board that needs to be shown to the user
   */
  public void displayBoard(Board board) {
    char[][] grid = board.getGrid();
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }
}
