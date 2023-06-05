package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This is the test class for ShipType enum
 */
class ShipTypeTest {
  /**
   * Check if the values in the ShipType enum are being retrieved properly
   */
  @Test
  public void values() {
    ShipType[] expectedOrder = {ShipType.CARRIER,
        ShipType.BATTLESHIP,
        ShipType.DESTROYER,
        ShipType.SUBMARINE};
    ShipType[] actualOrder = ShipType.values();
    assertArrayEquals(expectedOrder, actualOrder);
  }

  /**
   * check if valueOf() is working properly for the ShipType enum
   */
  @Test
  public void valueOf() {
    assertEquals(ShipType.CARRIER, ShipType.valueOf("CARRIER"));
    assertEquals(ShipType.BATTLESHIP, ShipType.valueOf("BATTLESHIP"));
    assertEquals(ShipType.DESTROYER, ShipType.valueOf("DESTROYER"));
    assertEquals(ShipType.SUBMARINE, ShipType.valueOf("SUBMARINE"));
  }
}