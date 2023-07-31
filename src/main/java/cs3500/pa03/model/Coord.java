package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Coord class represents a coordinate with x and y values on a board.
 */
public class Coord {
  int x;
  int y;

  /**
   * Constructs a new Coord object with the specified x and y values.
   *
   * @param x the x-coordinate value
   * @param y the y-coordinate value
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Retrieves the x-coordinate value of the coordinate.
   *
   * @return the x-coordinate value
   */
  public int getXcoord() {
    return x;
  }

  /**
   * Retrieves the y-coordinate value of the coordinate.
   *
   * @return the y-coordinate value
   */
  public int getYcoord() {
    return y;
  }

  /**
   * Checks if the board at the specified coordinate is empty.
   *
   * @param board the board to check
   * @return true if the board at the coordinate is empty, false otherwise
   */
  public boolean checkEmpty(Board board) {
    return board.board[x][y].compareTo('0') == 0;
  }

  /**
   * Checks if the board at the specified coordinate contains a ship.
   *
   * @param board the board to check
   * @return true if the board at the coordinate contains a ship, false otherwise
   */
  public boolean checkShip(Board board) {
    return board.board[x][y].compareTo('B') == 0;
  }

  /**
   * Checks if the board at the specified coordinate represents a hit or shot.
   *
   * @param board the board to check
   * @return true if the board at the coordinate represents a hit or shot, false otherwise
   */
  public boolean checkHitOrShot(Board board) {
    return board.board[x][y].compareTo('S') == 0
        || board.board[x][y].compareTo('H') == 0;
  }
}
