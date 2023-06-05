package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for ShipGenerator class
 */
class ShipGeneratorTest {
  private ShipGenerator sg;
  private UserBoard ub;
  private List<Coord> occupied;
  private PrintStream standardOut;
  private ByteArrayOutputStream outputStream;

  /**
   * Initializes sg, ub, and occupied
   */
  @BeforeEach
  public void setUp() {
    sg = new ShipGenerator();
    ub = new UserBoard(10, 10);
    occupied = new ArrayList<>();
  }

  /**
   * Tests if the generateRandomShip method works properly
   */
  @Test
  public void testGenerateRandomShip() {
    Ship ship1 = sg.generateRandomShip(ShipType.CARRIER, ub, occupied);
    occupied.addAll(ship1.getOccupiedPositions());
    Ship ship2 = sg.generateRandomShip(ShipType.CARRIER, ub, occupied);
    assertNotEquals(ship1, ship2);
  }

  /**
   * Tests if the isValidPos method works properly
   */
  @Test
  public void testIsValidPos() {
    standardOut = System.out;

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    UserBoard ub2 = new UserBoard(1, 2);
    Ship sub = sg.generateRandomShip(ShipType.CARRIER, ub2, occupied);
    occupied.addAll(sub.getOccupiedPositions());

    System.setOut(standardOut);
    String output = outputStream.toString();

    assertEquals("Try run again with bigger board or less large ships\n", output);
  }

  /**
   * Tests if the isPosOccupied method works properly
   */
  @Test
  public void testIsPosOccupiedCol() {
    UserBoard ub3 = new UserBoard(3, 6);
    Ship ship1 = sg.generateRandomShip(ShipType.CARRIER, ub3, occupied);
    occupied.addAll(ship1.getOccupiedPositions());
    Ship ship2 = sg.generateRandomShip(ShipType.CARRIER, ub3, occupied);
    occupied.addAll(ship2.getOccupiedPositions());
    Ship ship3 = sg.generateRandomShip(ShipType.CARRIER, ub3, occupied);
    assertNotEquals(ship1, ship2);
    assertNotEquals(ship2, ship3);
  }

  /**
   * Tests if the getShipSize method works properly
   */
  @Test
  public void testGetShipSize() {
    occupied.clear();
    Ship ship1 = sg.generateRandomShip(ShipType.CARRIER, ub, occupied);
    assertEquals(6, ship1.getOccupiedPositions().size());
    Ship ship2 = sg.generateRandomShip(ShipType.BATTLESHIP, ub, occupied);
    assertEquals(5, ship2.getOccupiedPositions().size());
    Ship ship3 = sg.generateRandomShip(ShipType.DESTROYER, ub, occupied);
    assertEquals(4, ship3.getOccupiedPositions().size());
    Ship ship4 = sg.generateRandomShip(ShipType.SUBMARINE, ub, occupied);
    assertEquals(3, ship4.getOccupiedPositions().size());
  }
}