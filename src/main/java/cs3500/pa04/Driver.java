package cs3500.pa04;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.GamePlay;
import cs3500.pa03.model.Ai;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.json.ShipAdapter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point6
   *
   * @param args - no command line args required
   */ //TODO remove exception
  public static void main(String[] args) throws IOException {
      Appendable output = System.out;
      Readable input = new InputStreamReader(System.in);
      GamePlay gp = new GamePlay(input, output);
    if (args.length == 2) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      Socket server = new Socket(host, port);
      Player ai = new Ai(gp);
      ProxyController proxyController = new ProxyController(server, ai);
      proxyController.run();
    } else if (args.length == 0) {
      gp.startGame();
    }
  }
}