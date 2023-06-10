package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.GamePlay;
import cs3500.pa03.model.Ai;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a Proxy Controller.
 * (uses code from ProxyDealer testing examples)
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;

  private Readable input;
  private Appendable output;
  GamePlay gamePlay;

  /**
   * Initializes gp, human, and the fleet
   */
  @BeforeEach
  public void setUp() {
    input = new StringReader("");
    output = new StringBuilder();
    gamePlay = new GamePlay(input, output);
    testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
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

  @Test
  void testSetUp() {

  }


  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString();
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
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