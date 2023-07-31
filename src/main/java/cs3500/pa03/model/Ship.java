package cs3500.pa03.model;

import java.util.List;

/**
 * The Ship class represents a ship on a game board.
 */
public class Ship {
  ShipType type;
  List<Coord> locations;
  boolean sunk;

  /**
   * Constructs a new Ship object with the specified type and locations.
   *
   * @param type      the type of the ship
   * @param locations the list of coordinates representing the ship's locations
   */
  public Ship(ShipType type, List<Coord> locations) {
    this.type = type;
    this.locations = locations;
    this.sunk = false;
  }

  /**
   * Checks if the ship is sunk.
   *
   * @return true if the ship is sunk, false otherwise
   */
  public boolean isSunk(Board board) {
    boolean isSunk = true;
    for (Coord c : locations) {
      isSunk = isSunk && c.checkHitOrShot(board);
    }

    this.sunk = isSunk;
    return sunk;
  }

  /**
   * gets the first coordinate of the ship
   *
   * @return the first coordinate of the ship
   */
  public Coord firstCoord() {
    return locations.get(0);
  }

  /**
   * returns the size of the ship
   *
   * @return the size of the ship
   */
  public int shipSize() {
    return type.toSize();
  }

  /**
   * finds the direction of the ship
   *
   * @return the direction of the ship
   */
  public String findDirection() {
    int x = locations.get(0).getXcoord();
    boolean sameX = true;
    for (Coord c : locations) {
      if (c.getXcoord() != x) {
        sameX = false;
        break;
      }
    }
    if (sameX) {
      return "HORIZONTAL";
    } else {
      return "VERTICAL";
    }
  }
}

