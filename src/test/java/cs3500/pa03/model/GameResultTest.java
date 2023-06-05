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
    GameResult[] expectedOrder = {GameResult.WON,
        GameResult.DRAW,
        GameResult.LOST};
    GameResult[] actualOrder = GameResult.values();
    assertArrayEquals(expectedOrder, actualOrder);
  }

  /**
   * check if valueOf() is working properly for the GameResult enum
   */
  @Test
  public void valueOf() {
    assertEquals(GameResult.WON, GameResult.valueOf("WON"));
    assertEquals(GameResult.DRAW, GameResult.valueOf("DRAW"));
    assertEquals(GameResult.LOST, GameResult.valueOf("LOST"));
  }

}