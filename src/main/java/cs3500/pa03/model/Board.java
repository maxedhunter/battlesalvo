package cs3500.pa03.model;

import java.util.List;

/**
 * Board class that represents the player's board
 */
public interface Board {
  /**
   * Getter method for the height
   *
   * @return height of the board
   */
  int getHeight();

  /**
   * Getter method for the width
   *
   * @return width of the board
   */
  int getWidth();

  /**
   * Getter method for the grid
   *
   * @return grid of the board
   */
  char[][] getGrid();

  /**
   * Initialized the board with '-' representing empty or unknown spots
   */
  void initialize();

  /**
   * Check if the ships on the board are all sunk
   *
   * @return whether all the ships are sunk or not
   */
  boolean allSunk();

  /**
   * Places the ship on the board with the first letter of the ship's ShipType representing it
   *
   * @param ship the ship that needs to be placed on the board
   */
  void placeShip(Ship ship);

  /**
   * Update the board with '/' representing the missed shots
   *
   * @param shots the shots from the other player
   */
  void updateMissed(List<Coord> shots);

  /**
   * Update the board with '*' representing shots that hit the ships on the board
   *
   * @param shotsHit the shots from the other player
   */
  void updateHit(List<Coord> shotsHit);
}
