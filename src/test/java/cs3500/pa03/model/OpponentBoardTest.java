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
 * This is the test class for OpponentBoard
 */
class OpponentBoardTest {
  private OpponentBoard ob;

  /**
   * Initializes ob
   */
  @BeforeEach
  public void setUp() {
    ob = new OpponentBoard(10, 10);
  }

  /**
   * Tests if the getHeight method works properly
   */
  @Test
  public void testGetHeight() {
    assertEquals(10, ob.getHeight());
  }

  /**
   * Tests if the getWidth method works properly
   */
  @Test
  public void testGetWidth() {
    assertEquals(10, ob.getWidth());
  }

  /**
   * Tests if the initialize method and getGrid method work properly
   */
  @Test
  public void testInitializeAndGetGrid() {
    ob.initialize();
    char[][] expected = new char[10][10];
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        expected[row][col] = '-';
      }
    }
    assertArrayEquals(expected, ob.getGrid());
  }

  /**
   * Tests if the placeShip method and allSunk method work properly
   */
  @Test
  public void testPlaceShipAndAllSunk() {
    ob.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship ship1 = new Ship(ShipType.SUBMARINE, occupied);
    ob.placeShip(ship1);
    assertFalse(ob.allSunk());
  }

  /**
   * Tests if the updateMissed method works properly
   */
  @Test
  public void testUpdateMissed() {
    ob.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship ship1 = new Ship(ShipType.SUBMARINE, occupied);
    ob.placeShip(ship1);
    List<Coord> missed = new ArrayList<>();
    missed.add(new Coord(1, 1));
    missed.add(new Coord(2, 1));
    missed.add(new Coord(3, 2));
    ob.updateMissed(missed);
    char[][] expected = new char[10][10];
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        expected[row][col] = '-';
      }
    }
    expected[1][1] = '/';
    expected[1][2] = '/';
    expected[2][3] = '/';
    assertArrayEquals(expected, ob.getGrid());
  }

  /**
   * Tests if the updateHit method works properly
   */
  @Test
  public void testUpdateHit() {
    ob.initialize();
    List<Coord> occupied = new ArrayList<>();
    occupied.add(new Coord(0, 0));
    occupied.add(new Coord(0, 1));
    occupied.add(new Coord(0, 2));
    Ship ship1 = new Ship(ShipType.SUBMARINE, occupied);
    ob.placeShip(ship1);
    List<Coord> hit = new ArrayList<>();
    hit.add(new Coord(0, 0));
    hit.add(new Coord(0, 1));
    hit.add(new Coord(0, 2));
    ob.updateHit(hit);
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
    ob.updateMissed(missed);

    assertTrue(ob.allSunk());
    assertArrayEquals(expected, ob.getGrid());
  }
}