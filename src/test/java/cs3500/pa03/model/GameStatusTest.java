package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for GameStatus class
 */
class GameStatusTest {
  private GameStatus gs;
  private List<Ship> fleetWithNoSunk;
  private List<Ship> fleetWithOneSunk;
  private Ship ship1;
  private Ship ship2;
  private Ship ship3;
  private List<Coord> occupiedPositions1;
  private List<Coord> occupiedPositions2;
  private List<Coord> occupiedPositions3;

  /**
   * Initializes gs, the fleets, the Ships, and the Coords
   */
  @BeforeEach
  public void setUp() {
    gs = new GameStatus();
    fleetWithNoSunk = new ArrayList<>();
    fleetWithOneSunk = new ArrayList<>();

    occupiedPositions1 = new ArrayList<>();
    Coord s1 = new Coord(0, 0);
    Coord s2 = new Coord(0, 1);
    Coord s3 = new Coord(0, 2);
    occupiedPositions1.add(s1);
    occupiedPositions1.add(s2);
    occupiedPositions1.add(s3);
    ship1 = new Ship(ShipType.SUBMARINE, occupiedPositions1);

    occupiedPositions2 = new ArrayList<>();
    Coord s4 = new Coord(1, 0);
    Coord s5 = new Coord(2, 0);
    Coord s6 = new Coord(3, 0);
    occupiedPositions2.add(s4);
    occupiedPositions2.add(s5);
    occupiedPositions2.add(s6);
    ship2 = new Ship(ShipType.SUBMARINE, occupiedPositions2);

    occupiedPositions3 = new ArrayList<>();
    ship3 = new Ship(ShipType.BATTLESHIP, occupiedPositions3);

    fleetWithNoSunk.add(ship1);
    fleetWithNoSunk.add(ship2);

    fleetWithOneSunk.add(ship1);
    fleetWithOneSunk.add(ship2);
    fleetWithOneSunk.add(ship3);
  }

  /**
   * Tests if the checkSunk method works properly
   */
  @Test
  public void testCheckSunk() {
    assertEquals(fleetWithNoSunk, gs.checkSunk(fleetWithNoSunk));
    assertEquals(fleetWithNoSunk, gs.checkSunk(fleetWithOneSunk));
  }


}