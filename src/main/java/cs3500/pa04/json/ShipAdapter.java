package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import java.util.List;

//TODO double check that these do not fail later

/**
 * Converts our ship into a server ship format for JSON.
 */
public class ShipAdapter {
  private final Coord coord;
  private final int length;
  private final Direction direction;
  private Ship ship;

  /**
   * Initializes the new ship format.
   *
   * @param ship the ship to be formatted
   */
  public ShipAdapter(Ship ship) {
    this.ship = ship;
    this.coord = initCoord();
    this.length = initLength();
    this.direction = initDirection();
  }

  /**
   * JSON constructor for ship.
   *
   * @param coord starting coord
   * @param length length of a ship
   * @param direction direction of a ship
   */
  @JsonCreator
  public ShipAdapter(@JsonProperty("coord") Coord coord, @JsonProperty("length") int length,
                     @JsonProperty("direction") Direction direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  /**
   * Initializes the starting coord.
   *
   * @return the starting coord
   */
  private Coord initCoord() {
    return ship.getOccupiedPositions().get(0);
  }

  /**
   * Gets the starting coordinate of a ship
   *
   * @return the starting coordinate
   */
  public Coord getCoord() {
    return this.coord;
  }

  /**
   * Initializes the length of a ship.
   *
   * @return number of coords occupied
   */
  public int initLength() {
    return ship.getOccupiedPositions().size();
  }

  /**
   * Returns the length of a ship.
   *
   * @return number of coords occupied
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Initializes the direction of a ship.
   *
   * @return the direction of the ship
   */
  public Direction initDirection() {
    List<Coord> coords = ship.getOccupiedPositions();
    for (int i = 0; i < ship.getOccupiedPositions().size() - 1; i++) {
      if (coords.get(i).getY() == coords.get(i + 1).getY()) {
        return Direction.HORIZONTAL;
      }
    }
    return Direction.VERTICAL;
  }

  /**
   * Determines a ship's direction.
   *
   * @return the ship's direction
   */
  public Direction getDirection() {
    return this.direction;
  }
}
