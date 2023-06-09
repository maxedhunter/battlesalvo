package cs3500.pa04.controller;

import cs3500.pa03.controller.GamePlay;
import cs3500.pa03.model.Human;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.MessageJson;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Represents tests for a Proxy Controller.
 */
class ProxyControllerTest {
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
  }

  /**
   * Tests the controller's run method.
   */
//  @Test
//  void testRun() {
//    MessageJson messageJson = new MessageJson("join", null);
//    try {
//      ProxyController controller = new ProxyController(new Socket(), new Human(gamePlay));
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//
//
//
//  }

}