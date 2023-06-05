package cs3500.pa03.model;

import java.util.List;

/**
 * Represent a single ship
 */
public class Ship {
  // type of the ship
  ShipType shipType;
  // positions that the ship occupied
  private List<Coord> occupiedPositions;

  /**
   * Constructor for the Ship class, initializes the shipType and occupiedPositions
   *
   * @param type type of the ship
   * @param occupied occupied positions of the ship
   */
  public Ship(ShipType type, List<Coord> occupied) {
    this.shipType = type;
    this.occupiedPositions = occupied;
  }

  /**
   * Getter method for the occupiedPositions
   *
   * @return occupiedPositions
   */
  public List<Coord> getOccupiedPositions() {
    return this.occupiedPositions;
  }

  /**
   * Set the occupiedPositions to the given list of Coord
   *
   * @param occupiedPositions updated occupiedPositions
   */
  public void setOccupiedPositions(List<Coord> occupiedPositions) {
    this.occupiedPositions = occupiedPositions;
  }
}
