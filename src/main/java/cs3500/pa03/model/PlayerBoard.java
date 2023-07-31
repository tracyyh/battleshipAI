package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The PlayerBoard class represents the game board for a player.
 */
public class PlayerBoard extends Board {

  List<Ship> ships = new ArrayList<>();

  /**
   * Constructs a new PlayerBoard object with the specified height and width.
   *
   * @param height the height of the player's board
   * @param width  the width of the player's board
   */
  public PlayerBoard(int height, int width) {
    super(height, width);
  }

  /**
   * Marks the shots taken on the player's board.
   *
   * @param shotsTaken the list of coordinates representing the shots taken
   */
  public void markShots(List<Coord> shotsTaken) {
    super.markShots(shotsTaken);
  }

  /**
   * Marks the hits taken on the player's board.
   *
   * @param hitsTaken the list of coordinates representing the hits taken
   */
  public void markDamage(List<Coord> hitsTaken) {

    super.markDamage(hitsTaken);
  }

  /**
   * Marks the ships on the player's board.
   *
   * @param ships the list of ships to be marked on the board
   */
  public void markShips(List<Coord> ships) {
    super.markShips(ships);
  }


  /**
   * Makes new ships given type and their number of occurances on the board
   *
   * @param numOfShips the mapped values of each shiptype
   * @return the list of ships with their locations
   */
  public List<Ship> placeShips(Map<ShipType, Integer> numOfShips) {
    List<Ship> placedShips = super.placeShips(numOfShips);
    this.ships = placedShips;
    return placedShips;
  }

  /**
   * Returns a random list of empty coordinates of a certain length in a column or row
   *
   * @param size the size of the empty space
   * @return the list of coordinates
   */
  public List<Coord> getRandomEmptyCoords(int size) {
    return super.getRandomEmptyCoords(size);
  }


}
