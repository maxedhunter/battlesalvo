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
//
    GamePlay gp = new GamePlay(input, output);
//    gp.startGame();

    List<Coord> coords =
        new ArrayList<>(Arrays.asList(new Coord(0, 0),
            new Coord(0, 1), new Coord(0, 2)));
    Ship ship = new Ship(ShipType.SUBMARINE, coords);
    ShipAdapter shipAdapter = new ShipAdapter(ship);
    ObjectMapper mapper = new ObjectMapper();

    System.out.println(mapper.writeValueAsString(new Coord(0,0)));
    System.out.println(
        mapper.writeValueAsString(shipAdapter));
    System.out.println(mapper.writeValueAsString(coords));
    List<ShipAdapter> fleet = new ArrayList<>();
    fleet.add(new ShipAdapter(ship));
    fleet.add(new ShipAdapter(ship));
    fleet.add(new ShipAdapter(ship));
    fleet.add(new ShipAdapter(ship));
    System.out.println(mapper.writeValueAsString(fleet));
    Socket server = new Socket("0.0.0.0", 35001);
    Player Ai = new Ai(gp);
    ProxyController proxyController = new ProxyController(server, Ai);
    proxyController.run();
  }
}