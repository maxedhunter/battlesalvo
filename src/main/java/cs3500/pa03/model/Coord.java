package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a coordinate on the board
 */
public class Coord {
  // the column the coordinate is on (x coordinate)
  private final int x;
  // the row the coordinate is on (y coordinate)
  private final int y;

  /**
   * Constructor for the Coord class, initializes row and col
   *
   * @param x x coordinate on the board
   * @param y y coordinate on the board
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int x,
               @JsonProperty("y") int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Getter method for the row
   *
   * @return row on the board
   */
  public int getX() {
    return this.x;
  }

  /**
   * Getter method for the col
   *
   * @return column on the board
   */
  public int getY() {
    return this.y;
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
