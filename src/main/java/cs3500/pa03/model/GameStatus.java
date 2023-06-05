package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class GameStatus that represents the status of the game
 *
 */
public class GameStatus {
  /**
   * Check how many ships are not sunk yet for a fleet
   *
   * @param fleet the fleet of a player
   * @return list of ships that are still alive
   *
   */
  public List<Ship> checkSunk(List<Ship> fleet) {
    List<Ship> nonSunkShips = new ArrayList<>();
    for (Ship ship : fleet) {
      if (!ship.getOccupiedPositions().isEmpty()) {
        nonSunkShips.add(ship);
      }
    }
    return nonSunkShips;
  }
}
