package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for Coord class
 */
class CoordTest {
  private Coord c1;
  private Coord c2;
  private Coord c3;
  private Coord c4;

  /**
   * Initializes c1, c2, c3, and c4
   */
  @BeforeEach
  public void setUp() {
    c1 = new Coord(0, 0);
    c2 = new Coord(0, 0);
    c3 = new Coord(1, 2);
    c4 = new Coord(0, 1);
  }

  /**
   * Tests if the getRow method works properly
   */
  @Test
  public void testGetRow() {
    assertEquals(1, c3.getRow());
  }

  /**
   * Tests if the getCol method works properly
   */
  @Test
  public void testGetCol() {
    assertEquals(2, c3.getCol());
  }

  /**
   * Tests if the equals method works properly
   */
  @Test
  public void testEquals() {
    assertTrue(c1.equals(c2));
    assertTrue(c1.equals(c1));
    assertFalse(c1.equals(c3));
    assertFalse(c1.equals(c4));
    assertFalse(c1.equals("Hi"));
  }

  /**
   * Tests if the hashCode method works properly
   */
  @Test
  public void testHashCode() {
    assertEquals(16400, c3.hashCode());
  }
}