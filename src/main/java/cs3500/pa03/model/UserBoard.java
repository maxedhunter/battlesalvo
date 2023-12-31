package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * UserBoard class that implements Board and represents the User's board
 */
public class UserBoard implements Board {
  // height of the board
  private final int height;
  // width of the board
  private final int width;
  // the board grid
  private final char[][] grid;
  // positions occupied
  private final List<Coord> occupiedPositions = new ArrayList<>();

  /**
   * Constructor for UserBoard class, initializes the height, width, and grid
   *
   * @param height number of rows on the board
   * @param width number of columns on the board
   */
  public UserBoard(int height, int width) {
    this.height = height;
    this.width = width;
    grid = new char[height][width];
  }

  /**
   * Getter method for the height
   *
   * @return height of the board
   */
  @Override
  public int getHeight() {
    return this.height;
  }

  /**
   * Getter method for the width
   *
   * @return width of the board
   */
  @Override
  public int getWidth() {
    return this.width;
  }

  /**
   * Getter method for the grid
   *
   * @return grid of the board
   */
  @Override
  public char[][] getGrid() {
    return this.grid;
  }

  /**
   * Initialized the board with '-' representing empty or unknown spots
   */
  @Override
  public void initialize() {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        grid[row][col] = '-';
      }
    }
  }

  /**
   * Check if the ships on the board are all sunk
   *
   * @return whether all the ships are sunk or not
   */
  @Override
  public boolean allSunk() {
    int hit = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (grid[row][col] == '*') {
          hit++;
        }
      }
    }
    return hit == occupiedPositions.size();
  }

  /**
   * Places the ship on the board with the first letter of the ship's ShipType representing it
   *
   * @param ship the ship that needs to be placed on the board
   */
  @Override
  public void placeShip(Ship ship) {
    List<Coord> coords = ship.getOccupiedPositions();
    for (Coord pos : coords) {
      if (ship.getShipType() == ShipType.CARRIER) {
        grid[pos.getY()][pos.getX()] = 'C';
      }
      if (ship.getShipType() == ShipType.BATTLESHIP) {
        grid[pos.getY()][pos.getX()] = 'B';
      }
      if (ship.getShipType() == ShipType.DESTROYER) {
        grid[pos.getY()][pos.getX()] = 'D';
      }
      if (ship.getShipType() == ShipType.SUBMARINE) {
        grid[pos.getY()][pos.getX()] = 'S';
      }
      this.occupiedPositions.add(pos);
    }
  }

  /**
   * Update the board with '/' representing the missed shots
   *
   * @param shots the shots from the other player
   */
  @Override
  public void updateMissed(List<Coord> shots) {
    for (Coord pos : shots) {
      if (grid[pos.getY()][pos.getX()] != '*') {
        grid[pos.getY()][pos.getX()] = '/';
      }
    }
  }

  /**
   * Update the board with '*' representing shots that hit the ships on the board
   *
   * @param shotsHit the shots from the other player
   */
  @Override
  public void updateHit(List<Coord> shotsHit) {
    for (Coord pos : shotsHit) {
      grid[pos.getY()][pos.getX()] = '*';
    }
  }
}
