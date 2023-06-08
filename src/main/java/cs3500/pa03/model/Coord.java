package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a coordinate on the board
 */
public class Coord {
  // the row the coordinate is on (x coordinate)
  private int row;
  // the column the coordinate is on (y coordinate)
  private int col;

  /**
   * Constructor for the Coord class, initializes row and col
   *
   * @param row x coordinate on the board
   * @param col y coordinate on the board
   */
  public Coord(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Getter method for the row
   *
   * @return row on the board
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Getter method for the col
   *
   * @return column on the board
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Override the equal method to check if the row and col are equal for two Coord
   *
   * @return wether the given object is equal to the Coord
   */
  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Coord pos)) {
      return false;
    }
    return this.getX() == pos.getX() && this.getY() == pos.getY();
  }

  /**
   * Override the hashCode method, produce a hashCode for the Coord
   *
   * @return the hashCode of the Coord
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + this.getY();
    result = 31 * result + this.getX();
    return result;
  }
}
