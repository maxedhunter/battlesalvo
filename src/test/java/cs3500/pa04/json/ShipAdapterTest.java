package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for ship adapter
 */
class ShipAdapterTest {

  /**
   * Tests getting the start coordinate.
   */
  @Test
  void testGetStart() {
    List<Coord> coords =
        new ArrayList<>(Arrays.asList(new Coord(0, 0),
            new Coord(0, 1), new Coord(0, 2)));
    Ship ship = new Ship(ShipType.SUBMARINE, coords);

    ShipAdapter shipAdapter = new ShipAdapter(ship);
    assertEquals(new Coord(0, 0), shipAdapter.getCoord());
  }

  /**
   * Tests getting the length of a ship (adapted).
   */
  @Test
  void testGetLength() {
    List<Coord> coords =
        new ArrayList<>(Arrays.asList(new Coord(0, 0),
            new Coord(0, 1), new Coord(0, 2)));
    Ship ship = new Ship(ShipType.SUBMARINE, coords);

    ShipAdapter shipAdapter = new ShipAdapter(ship);
    assertEquals(3, shipAdapter.getLength());
  }

  /**
   * Tests getting the direction of a ship.
   */
  @Test
  void testGetDirection() {
    // vertical
    List<Coord> coords =
        new ArrayList<>(Arrays.asList(new Coord(0, 0),
            new Coord(0, 1), new Coord(0, 2)));
    Ship ship = new Ship(ShipType.SUBMARINE, coords);

    ShipAdapter shipAdapter = new ShipAdapter(ship);
    assertEquals(Direction.VERTICAL, shipAdapter.getDirection());

    // horizontal
    List<Coord> coords1 =
        new ArrayList<>(Arrays.asList(new Coord(1, 0),
            new Coord(2, 0), new Coord(3, 0)));
    Ship ship1 = new Ship(ShipType.SUBMARINE, coords1);

    ShipAdapter shipAdapter1 = new ShipAdapter(ship1);
    assertEquals(Direction.HORIZONTAL, shipAdapter1.getDirection());
  }

  /**
   * Represents testing creating a ship adapter json.
   */
  @Test
  void testJsonCreation() {
    ShipAdapter shipAdapter = new ShipAdapter(new Coord(0, 0), 3, Direction.VERTICAL);
    ObjectMapper mapper = new ObjectMapper();

    assertEquals(new Coord(0, 0), shipAdapter.getCoord());
    assertEquals(3, shipAdapter.getLength());
    assertEquals(Direction.VERTICAL, shipAdapter.getDirection());

    try {
      assertEquals("{\"coord\":{\"x\":0,\"y\":0},\"length\":3,\"direction\":\"VERTICAL\"}",
          mapper.writeValueAsString(shipAdapter));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}