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
  private GamePlay gp;
  private Readable input;
  private Appendable output;

  /**
   * Initializes gp, human, and the fleet
   */
  @BeforeEach
  public void setUp() {
    input = new StringReader("");
    output = new StringBuilder();

    gp = new GamePlay(input, output);
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
    String inputString = "6 6\n1 1 1 1\n0 0 0 1 0 2 0 3 0 4";
    Readable inputShots = new StringReader(inputString);
    GamePlay gp1 = new GamePlay(inputShots, output);
    Human human1 = new Human(gp1);

    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(0, 1));
    shots.add(new Coord(0, 2));
    shots.add(new Coord(0, 3));
    Map<ShipType, Integer> fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 1);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);
    gp1.getBoardSize();;
    gp1.getFleet();
    human1.setup(6, 6, fleet);

    assertEquals(shots, human1.takeShots());
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