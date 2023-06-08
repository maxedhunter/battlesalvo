package cs3500.pa04;

import cs3500.pa03.controller.GamePlay;
import java.io.InputStreamReader;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point6
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Appendable output = System.out;
    Readable input = new InputStreamReader(System.in);

    GamePlay gp = new GamePlay(input, output);
    gp.startGame();
  }
}