package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.GamePlay;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for Human class
 */
class HumanTest {
  private Human human;
  private Map<ShipType, Integer> fleet;

  /**
   * Initializes gp, human, and the fleet
   */
  @BeforeEach
  public void setUp() {
    Readable input = new StringReader("");
    Appendable output = new StringBuilder();

    GamePlay gp = new GamePlay(input, output);
    human = new Human(gp);
    fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);
  }

  /**
   * Tests if the name method works properly
   */
  @Test
  public void testName() {
    assertEquals("Player", human.name());
  }

  /**
   * Tests if the setup method generates a board properly and place ships randomly
   */
  @Test
  public void testSetUp() {
    assertEquals(4, human.setup(10, 10, fleet).size());
  }

  /**
   * Tests if the takeShot method works properly
   */
  @Test
  public void takeShot() {

  }

  /**
   * Tests if the reportDamage method works properly
   */
  @Test
  public void testReportDamage() {
    human.setup(10, 10, fleet);
    List<Coord> opponentShotsOnBoard = new ArrayList<>();
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        opponentShotsOnBoard.add(new Coord(row, col));
      }
    }
    List<Coord> successfulHit = new ArrayList<>();
    human.successfulHits(successfulHit);
    assertEquals(18, human.reportDamage(opponentShotsOnBoard).size());
    human.endGame(GameResult.LOST, "All ships are sunk");
  }
}