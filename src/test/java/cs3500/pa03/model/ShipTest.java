package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for Ship class
 */
class ShipTest {
  private Ship ship1;
  private List<Coord> occupiedPositions1;
  private List<Coord> occupiedPositions2;

  /**
   * Initializes ship1, occupiedPositions1, and occupiedPositions2
   */
  @BeforeEach
  public void setUp() {
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
    occupiedPositions2.add(s4);
    occupiedPositions2.add(s5);
  }

  /**
   * Tests if the getOccupiedPosition method works properly
   */
  @Test
  public void testGetOccupiedPosition() {
    List<Coord> expectedPositions = new ArrayList<>();
    expectedPositions.add(new Coord(0, 0));
    expectedPositions.add(new Coord(0, 1));
    expectedPositions.add(new Coord(0, 2));
    assertEquals(expectedPositions, ship1.getOccupiedPositions());
  }

  /**
   * Tests if the setOccupiedPosition method works properly
   */
  @Test
  public void testSetOccupiedPosition() {
    List<Coord> expectedPositions = new ArrayList<>();
    expectedPositions.add(new Coord(1, 0));
    expectedPositions.add(new Coord(2, 0));
    ship1.setOccupiedPositions(occupiedPositions2);
    assertEquals(expectedPositions, ship1.getOccupiedPositions());
  }
}