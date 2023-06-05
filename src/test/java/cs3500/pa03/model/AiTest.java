package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.GamePlay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for Ai class
 */
class AiTest {
  private GamePlay gp;
  private Ai computer;
  private Map<ShipType, Integer> fleet;

  /**
   * Initializes gp, computer, and the fleet
   */
  @BeforeEach
  public void setUp() {
//    gp = new GamePlay(); //TODO
    computer = new Ai(gp);
    fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);
  }

  /**
   * Tests of the name method returns the name
   */
  @Test
  public void testName() {
    assertEquals("AI", computer.name());
  }

  /**
   * Tests the setup method is setting up board and placing ships properly
   */
  @Test
  public void testSetUp() {
    assertEquals(4, computer.setup(6, 6, fleet).size());
  }

  /**
   * Tests the takeShot method is generating shots properly
   */
  @Test
  public void takeShot() {
    computer.setup(6, 7, fleet);
    computer.takeShots();
    computer.takeShots();
    computer.takeShots();
    computer.takeShots();
    computer.takeShots();
    computer.takeShots();
    computer.takeShots();
    computer.takeShots();
    computer.takeShots();
    assertEquals(4, computer.takeShots().size());
    assertEquals(2, computer.takeShots().size());
  }

  /**
   * Tests the reportDamage method
   */
  @Test
  public void testReportDamage() {
    computer.setup(10, 10, fleet);
    List<Coord> opponentShotsOnBoard = new ArrayList<>();
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        opponentShotsOnBoard.add(new Coord(row, col));
      }
    }
    List<Coord> successfulHit = new ArrayList<>();
    computer.successfulHits(successfulHit);
    assertEquals(18, computer.reportDamage(opponentShotsOnBoard).size());
    computer.endGame(GameResult.LOST, "All ships are sunk");
  }
}