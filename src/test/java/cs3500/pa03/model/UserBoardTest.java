package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for UserBoard class
 */
class UserBoardTest {
  private UserBoard ub;

  /**
   * Initializes ub
   */
  @BeforeEach
  public void setUp() {
    ub = new UserBoard(10, 10);
  }

  /**
   * Tests if the getHeight method works properly
   */
  @Test
  public void testGetHeight() {
    assertEquals(10, ub.getHeight());
  }

  /**
   * Tests if the getWidth method works properly
   */
  @Test
  public void testGetWidth() {
    assertEquals(10, ub.getWidth());
  }

  /**
   * Tests if the initialize method and getGrid method work properly
   */
  @Test
  public void testInitializeAndGetGrid() {
    ub.initialize();
    char[][] expected = new char[10][10];
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        expected[row][col] = '-';
      }
    }
    assertArrayEquals(expected, ub.getGrid());
  }

  /**
   * Tests if the placeShip method and allSunk method work properly
   */
  @Test
  public void testPlaceShipAndAllSunk() {
    ub.initialize();
    List<Coord> occupiedS = new ArrayList<>();
    occupiedS.add(new Coord(0, 0));
    occupiedS.add(new Coord(0, 1));
    occupiedS.add(new Coord(0, 2));
    Ship submarine = new Ship(ShipType.SUBMARINE, occupiedS);
    ub.placeShip(submarine);

    List<Coord> occupiedD = new ArrayList<>();
    occupiedD.add(new Coord(1, 0));
    occupiedD.add(new Coord(1, 1));
    occupiedD.add(new Coord(1, 2));
    occupiedD.add(new Coord(1, 3));
    Ship destroyer = new Ship(ShipType.DESTROYER, occupiedD);
    ub.placeShip(destroyer);

    List<Coord> occupiedB = new ArrayList<>();
    occupiedB.add(new Coord(2, 0));
    occupiedB.add(new Coord(2, 1));
    occupiedB.add(new Coord(2, 2));
    occupiedB.add(new Coord(2, 3));
    occupiedB.add(new Coord(2, 4));
    Ship battleShip = new Ship(ShipType.BATTLESHIP, occupiedB);
    ub.placeShip(battleShip);

    List<Coord> occupiedC = new ArrayList<>();
    occupiedC.add(new Coord(2, 0));
    occupiedC.add(new Coord(2, 1));
    occupiedC.add(new Coord(2, 2));
    occupiedC.add(new Coord(2, 3));
    occupiedC.add(new Coord(2, 4));
    Ship carrier = new Ship(ShipType.CARRIER, occupiedB);
    ub.placeShip(carrier);

    assertFalse(ub.allSunk());
  }

  /**
   * Tests if the updateMissed method works properly
   */
  @Test
  public void testUpdateMissed() {
    ub.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship ship1 = new Ship(ShipType.SUBMARINE, occupied);
    ub.placeShip(ship1);
    List<Coord> missed = new ArrayList<>();
    missed.add(new Coord(1, 1));
    missed.add(new Coord(2, 1));
    missed.add(new Coord(3, 2));
    ub.updateMissed(missed);
    char[][] expected = new char[10][10];
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        expected[row][col] = '-';
      }
    }
    expected[0][0] = 'S';
    expected[1][0] = 'S';
    expected[2][0] = 'S';
    expected[1][1] = '/';
    expected[1][2] = '/';
    expected[2][3] = '/';
    assertArrayEquals(expected, ub.getGrid());
  }

  /**
   * Tests if the updateHit method works properly
   */
  @Test
  public void testUpdateHit() {
    ub.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship ship1 = new Ship(ShipType.SUBMARINE, occupied);
    ub.placeShip(ship1);
    List<Coord> hit = new ArrayList<>();
    hit.add(new Coord(0, 0));
    hit.add(new Coord(0, 1));
    hit.add(new Coord(0, 2));
    ub.updateHit(hit);
    char[][] expected = new char[10][10];
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        expected[row][col] = '-';
      }
    }
    expected[0][0] = '*';
    expected[1][0] = '*';
    expected[2][0] = '*';

    List<Coord> missed = new ArrayList<>();
    missed.add(new Coord(0, 0));
    missed.add(new Coord(0, 1));
    missed.add(new Coord(0, 2));
    ub.updateMissed(missed);

    assertTrue(ub.allSunk());
    assertArrayEquals(expected, ub.getGrid());
  }
}