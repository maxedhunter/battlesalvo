package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for coordinates json object.
 */
class CoordinatesJsonTest {
  @Test
  void testCreateCoordinate() {
    List<Coord> coords =
        new ArrayList<>(Arrays.asList(new Coord(0, 0),
            new Coord(0, 1), new Coord(0, 2)));

    ObjectMapper mapper = new ObjectMapper();
    try {
      assertEquals("[{\"x\":0,\"y\":0},{\"x\":0,\"y\":1},{\"x\":0,\"y\":2}]",
          mapper.writeValueAsString(coords));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }
}