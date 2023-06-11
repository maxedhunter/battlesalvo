package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This is the test class for GameResult enum
 */
class GameResultTest {
  /**
   * Check if the values in the GameResult enum are being retrieved properly
   */
  @Test
  public void values() {
    GameResult[] expectedOrder = {GameResult.WIN,
        GameResult.DRAW,
        GameResult.LOSE};
    GameResult[] actualOrder = GameResult.values();
    assertArrayEquals(expectedOrder, actualOrder);
  }

  /**
   * check if valueOf() is working properly for the GameResult enum
   */
  @Test
  public void valueOf() {
    assertEquals(GameResult.WIN, GameResult.valueOf("WIN"));
    assertEquals(GameResult.DRAW, GameResult.valueOf("DRAW"));
    assertEquals(GameResult.LOSE, GameResult.valueOf("LOSE"));
  }

}