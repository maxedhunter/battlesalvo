package cs3500.pa04;

import cs3500.pa03.controller.GamePlay;
import cs3500.pa03.model.Ai;
import cs3500.pa03.model.Player;
import cs3500.pa04.controller.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point6
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Appendable output = System.out;
    Readable input = new InputStreamReader(System.in);
    GamePlay gp = new GamePlay(input, output);
    if (args.length == 2) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      Socket server;

      try {
        server = new Socket(host, port);
      } catch (IOException e) {
        throw new RuntimeException("Unable to create socket.");
      }

      Player ai = new Ai(gp);

      ProxyController proxyController;
      try {
        proxyController = new ProxyController(server, ai);
      } catch (IOException e) {
        throw new RuntimeException("Unable to create Proxy Controller.");
      }
      proxyController.run();
    } else if (args.length == 0) {
      gp.startGame();
    } else {
      throw new IllegalArgumentException("Please provide valid arguments");
    }
  }
}