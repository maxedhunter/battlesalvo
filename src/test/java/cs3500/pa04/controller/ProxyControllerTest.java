package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa03.controller.GamePlay;
import cs3500.pa03.model.Ai;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a Proxy Controller.
 * (uses code from ProxyDealer testing examples)
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  GamePlay gamePlay;
  private final ObjectMapper mapper = new ObjectMapper();
  Map<ShipType, Integer> fleet;


  /**
   * Initializes gp, human, and the fleet
   */
  @BeforeEach
  public void setUp() {
    Readable input = new StringReader("");
    Appendable output = new StringBuilder();
    gamePlay = new GamePlay(input, output);
    testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    fleet = new HashMap<>();
    fleet.put(ShipType.CARRIER, 2);
    fleet.put(ShipType.BATTLESHIP, 4);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 3);
  }

  /**
   * Tests the controller's run method.
   */
  @Test
  void testJoin() {
    JsonNode messageJson = createSampleMessage("join", null);

    Mocket socket = new Mocket(this.testLog, List.of(messageJson.toString()));

    try {
      controller = new ProxyController(socket, new Ai(gamePlay));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.controller.run();

    String expected =
        "{\"method-name\":\"join\",\"arguments\":{\"name\":"
            + "\"shuerry\",\"game-type\":\"SINGLE\"}}\n";

    assertEquals(expected, logToString());
  }

  /**
   * Tests setting up a fleet.
   */
  @Test
  void testSetUp() {
    // create the setup request
    ObjectNode fleetSpec = mapper.createObjectNode();
    fleetSpec.put("CARRIER", 2);
    fleetSpec.put("BATTLESHIP", 4);
    fleetSpec.put("DESTROYER", 1);
    fleetSpec.put("SUBMARINE", 3);

    SetupJson setUpRequest = new SetupJson(10, 10, fleetSpec);
    JsonNode request = createSampleMessage("setup", setUpRequest);

    // make sure the correct setup is being passed in
    assertEquals(
        "{\"method-name\":\"setup\",\"arguments\":{\"width\":10,\"height\":10,\""
            + "fleet-spec\":{\"CARRIER\":2,\"BATTLESHIP\":4,\"DESTROYER\":1,\"SUBMARINE\":3}}}",
        request.toString());

    Mocket socket = new Mocket(this.testLog, List.of(request.toString()));

    try {
      controller = new ProxyController(socket, new Ai(gamePlay));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.controller.run();

    // tests if a fleet json is returned to the server
    responseToClass(FleetJson.class);
  }

  /**
   * Tests for returning shots to the server.
   */
  @Test
  void testTakeShots() {
    Ai ai = new Ai(gamePlay);
    ai.setup(10, 10, fleet);

    JsonNode messageJson = createSampleMessage("take-shots", null);

    Mocket socket = new Mocket(this.testLog, List.of(messageJson.toString()));

    try {
      controller = new ProxyController(socket, ai);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.controller.run();

    // tests if a coordinates json is returned to the server
    responseToClass(CoordinatesJson.class);
  }

  /**
   * Tests reporting damage.
   */
  @Test
  void testReportDamage() {
    Ai ai = new Ai(gamePlay);
    ai.setup(10, 10, fleet);

    List<Coord> coords = new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2),
            new Coord(0, 3), new Coord(0, 4), new Coord(0, 5)));

    CoordinatesJson coordinatesJson = new CoordinatesJson(coords);
    JsonNode messageJson = createSampleMessage("report-damage", coordinatesJson);

    Mocket socket = new Mocket(this.testLog, List.of(messageJson.toString()));

    try {
      controller = new ProxyController(socket, ai);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.controller.run();

    // tests if a coordinates json is returned to the server
    responseToClass(CoordinatesJson.class);
  }

  /**
   * Tests reporting successful hits.
   */
  @Test
  void testSuccessfulHits() {
    Ai ai = new Ai(gamePlay);
    ai.setup(10, 10, fleet);

    List<Coord> coords = new ArrayList<>(
        Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2),
            new Coord(0, 3), new Coord(0, 4), new Coord(0, 5)));

    CoordinatesJson coordinatesJson = new CoordinatesJson(coords);
    JsonNode messageJson = createSampleMessage("successful-hits", coordinatesJson);

    Mocket socket = new Mocket(this.testLog, List.of(messageJson.toString()));

    try {
      controller = new ProxyController(socket, ai);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.controller.run();

    // tests if a coordinates json is returned to the server
    responseToClass(CoordinatesJson.class);
  }

  /**
   * Tests the game end response.
   */
  @Test
  void testEndGame() {
    Ai ai = new Ai(gamePlay);
    EndGameJson endGameJson =
        new EndGameJson(String.valueOf(GameResult.WIN),
            "Player 1 sank all of Player 2's ships");

    JsonNode messageJson = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(messageJson.toString()));

    try {
      controller = new ProxyController(socket, ai);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    this.controller.run();

    assertEquals("{\"method-name\":\"end-game\",\"arguments\":{}}\n", logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class. Modified from
   * mock example since each JSON is in MessageJson, this code goes one level deeper
   * and attempts to parse the MessageJson arguments.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());

      // first convert to MessageJson
      MessageJson messageJson = jsonParser.readValueAs(MessageJson.class);

      // now parse arguments of MessageJson
      JsonParser jsonParser1 = new ObjectMapper().createParser(messageJson.arguments().toString());
      jsonParser1.readValueAs(classRef);
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}