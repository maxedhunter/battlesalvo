package cs3500.pa04.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ShipAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }


  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * Delegates the corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      join();
    } else if ("setup".equals(name)) {
      setUp(arguments);
    } else if ("take-shots".equals(name)) {
      takeShots();
    } else if ("report-damage".equals(name)) {
      reportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      successfulHits(arguments);
    } else if ("end-game".equals(name)) {
      endGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Serializes the JSON response to a join request.
   */
  public void join() {
    // Create the arguments JSON object
    ObjectNode arguments = mapper.createObjectNode();
    arguments.put("name", this.player.name());
    arguments.put("game-type", String.valueOf(GameType.SINGLE));

    MessageJson response = new MessageJson("join", arguments);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }

  public void setUp(JsonNode arguments) {
    System.out.println(arguments);
    int width = arguments.get("width").asInt();
    int height = arguments.get("height").asInt();
    JsonNode fleetSpecNode = arguments.get("fleet-spec");
    int carrierCount = fleetSpecNode.get("CARRIER").asInt();
    int battleshipCount = fleetSpecNode.get("BATTLESHIP").asInt();
    int destroyerCount = fleetSpecNode.get("DESTROYER").asInt();
    int submarineCount = fleetSpecNode.get("SUBMARINE").asInt();
    Map<ShipType, Integer> fleetSpec = new HashMap<>();
    fleetSpec.put(ShipType.CARRIER, carrierCount);
    fleetSpec.put(ShipType.BATTLESHIP, battleshipCount);
    fleetSpec.put(ShipType.DESTROYER, destroyerCount);
    fleetSpec.put(ShipType.SUBMARINE, submarineCount);
    List<Ship> ships = this.player.setup(height, width, fleetSpec);
    List<ShipAdapter> fleet = new ArrayList<>();
    for (Ship ship : ships) {
      fleet.add(new ShipAdapter(ship));
    }
    FleetJson fleetJson = new FleetJson(fleet);
    JsonNode fleetArg = mapper.convertValue(fleetJson, JsonNode.class);
    MessageJson response = new MessageJson("setup", fleetArg);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    System.out.println(jsonResponse);
    this.out.println(jsonResponse);
  }

  public void takeShots() {
    List<Coord> shots = this.player.takeShots();
    CoordinatesJson shotsJson = new CoordinatesJson(shots);
    JsonNode shotsArg = mapper.convertValue(shotsJson, JsonNode.class);
    MessageJson response = new MessageJson("take-shots", shotsArg);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    System.out.println(jsonResponse);
    this.out.println(jsonResponse);
  }

  public void reportDamage(JsonNode arguments) {
    CoordinatesJson coordinatesJson = mapper.convertValue(arguments, CoordinatesJson.class);
    System.out.println(coordinatesJson);
    List<Coord> damaged = this.player.reportDamage(coordinatesJson.shots());
    CoordinatesJson damagedJson = new CoordinatesJson(damaged);
    JsonNode damagedArg = mapper.convertValue(damagedJson, JsonNode.class);
    MessageJson response = new MessageJson("report-damage", damagedArg);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    System.out.println(jsonResponse);
    this.out.println(jsonResponse);
  }

  public void successfulHits(JsonNode arguments) {
    System.out.println(arguments);
    JsonNode coordinatesNode = arguments.get("coordinates");
    List<Coord> hits = new ArrayList<>();
    for (JsonNode coordinateNode : coordinatesNode) {
      int x = coordinateNode.get("x").asInt();
      int y = coordinateNode.get("y").asInt();
      hits.add(new Coord(x, y));
    }
    this.player.successfulHits(hits);
    ObjectNode returnArguments = mapper.createObjectNode();
    MessageJson response = new MessageJson("successful-hits", returnArguments);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    System.out.println(jsonResponse);
    this.out.println(jsonResponse);
  }

  /**
   * Handles the ending of a game.
   *
   * @param arguments
   * @return
   */
  private void endGame(JsonNode arguments) {
    String result = arguments.get("result").asText();
    String reason = arguments.get("reason").asText();

    if (result.equals("WIN")) {
      this.player.endGame(GameResult.WON, reason);
    }

    if (result.equals("DRAW")) {
      this.player.endGame(GameResult.DRAW, reason);
    }

    if (result.equals("LOSE")) {
      this.player.endGame(GameResult.LOST, reason);
    }

    ObjectNode returnArguments = mapper.createObjectNode();
    MessageJson response = new MessageJson("end-game", returnArguments);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    this.out.println(jsonResponse);
  }
}
