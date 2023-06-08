package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a helper ShipGenerator class for generating random ship
 */
public class ShipGenerator {

  /**
   * Generate a random ship either horizontally or vertically without overlapping
   *
   * @param shipType type of the ship
   * @param board the board to place the ship on
   * @param occupied occupied positions on the board
   * @return a Ship generated
   */
  public static Ship generateRandomShip(ShipType shipType, Board board, List<Coord> occupied) {
    long startTime = System.currentTimeMillis();
    Random random = new Random();
    int row;
    int col;
    boolean isVertical;

    int shipSize = getShipSize(shipType);
    List<Coord> occupiedPos = new ArrayList<>();

    while (occupiedPos.isEmpty()) {
      row = random.nextInt(board.getHeight());
      col = random.nextInt(board.getWidth());
      isVertical = random.nextBoolean();
      occupiedPos.clear();

      for (int i = 0; i < shipSize; i++) {
        int currentRow;
        int currentCol;

        if (isVertical) {
          currentRow = row;
          currentCol = col + i;
        } else {
          currentRow = row + i;
          currentCol = col;
        }

        if (isValidPos(currentRow, currentCol, board)
            && !isPosOccupied(new Coord(currentCol, currentRow), occupied)) {
          occupiedPos.add(new Coord(currentCol, currentRow));
        } else {
          occupiedPos.clear();
          break;
        }
      }
      // Check if the loop has been running for too long
      long currentTime = System.currentTimeMillis();
      long elapsedTime = currentTime - startTime;
      long maxExecutionTime = 10000; // Maximum execution time in milliseconds

      if (elapsedTime > maxExecutionTime) {
        System.out.println("Try run again with bigger board or less large ships");
        break;
      }
    }
    return new Ship(shipType, occupiedPos);
  }

  /**
   * A helper method for generateRandomShip that checks if a position is within the board range
   *
   * @param row the x coordinate
   * @param col the y coordinate
   * @param board the board to place the ship on
   * @return whether the position is valid or not
   *
   */
  private static boolean isValidPos(int col, int row, Board board) {
    return row >= 0 && row < board.getWidth()
        && col >= 0 && col < board.getHeight();
  }

  /**
   * A helper method for generateRandomShip that checks if a position is occupied or not
   *
   * @param pos the position on the board
   * @param occupied occupied positions on the board
   * @return whether the position is occupied or not
   *
   */
  private static boolean isPosOccupied(Coord pos, List<Coord> occupied) {
    return occupied.contains(pos);
  }

  /**
   * A helper method for generateRandomShip that gets the size for the ship
   *
   * @param shipType type of the ship
   * @return the size of the ship
   *
   */
  private static int getShipSize(ShipType shipType) {
    if (shipType == ShipType.CARRIER) {
      return 6;
    } else if (shipType == ShipType.BATTLESHIP) {
      return 5;
    } else if (shipType == ShipType.DESTROYER) {
      return 4;
    } else {
      return 3;
    }
  }
}
