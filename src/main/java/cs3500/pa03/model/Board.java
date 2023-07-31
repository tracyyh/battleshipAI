package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The Board class represents an abstract game board.
 */
public abstract class Board {
  int height;
  int width;
  List<Ship> ships = new ArrayList<>();

  public Character[][] board;

  /**
   * Constructs a new Board object with the specified height and width.
   *
   * @param height the height of the board
   * @param width  the width of the board
   */
  Board(int height, int width) {
    this.height = height;
    this.width = width;
    this.board = newBoard(height, width);
  }

  /**
   * Creates a new board with the specified height and width, filled with '0' characters.
   *
   * @param height the height of the board
   * @param width  the width of the board
   * @return the newly created board
   */
  static Character[][] newBoard(int height, int width) {
    Character[][] board = new Character[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = '0';
      }
    }
    return board;
  }

  /**
   * Marks the shots taken on the board.
   *
   * @param shotsTaken the list of coordinates representing the shots taken
   */
  public void markShots(List<Coord> shotsTaken) {
    for (Coord place : shotsTaken) {
      board[place.getXcoord()][place.getYcoord()] = 'S';
    }
  }

  /**
   * Marks a single shot taken on the board.
   *
   * @param shotTaken the list of coordinates representing the shots taken
   */
  public void markShots(Coord shotTaken) {
    board[shotTaken.getXcoord()][shotTaken.getYcoord()] = 'S';
  }

  /**
   * Marks the hits taken on the board.
   *
   * @param hitsTaken the list of coordinates representing the hits taken
   */
  public void markDamage(List<Coord> hitsTaken) {
    for (Coord place : hitsTaken) {
      board[place.getXcoord()][place.getYcoord()] = 'H';
    }
  }

  /**
   * Marks the ships on the player's board.
   *
   * @param ships the list of ships to be marked on the board
   */
  public void markShips(List<Coord> ships) {
    for (Coord coord : ships) {
      board[coord.getXcoord()][coord.getYcoord()] = 'B';
    }
  }

  /**
   * Makes new ships given type and their number of occurances on the board
   *
   * @param numOfShips the mapped values of each shiptype
   * @return the list of ships with their locations
   */
  public List<Ship> placeShips(Map<ShipType, Integer> numOfShips) {
    List<Ship> placedShips = new ArrayList<>();
    int carriers = numOfShips.get(ShipType.CARRIER);
    for (int i = 0; i < carriers; i++) {
      List<Coord> carrLoc = getRandomEmptyCoords(6);
      placedShips.add(new Ship(ShipType.CARRIER, carrLoc));
      markShips(carrLoc);
    }
    int battleships = numOfShips.get(ShipType.BATTLESHIP);
    for (int j = 0; j < battleships; j++) {
      List<Coord> battLoc = getRandomEmptyCoords(5);
      placedShips.add(new Ship(ShipType.BATTLESHIP, battLoc));
      markShips(battLoc);
    }
    int destroyers = numOfShips.get(ShipType.DESTROYER);
    for (int k = 0; k < destroyers; k++) {
      List<Coord> destLoc = getRandomEmptyCoords(4);
      placedShips.add(new Ship(ShipType.DESTROYER, destLoc));
      markShips(destLoc);
    }
    int submarines = numOfShips.get(ShipType.SUBMARINE);
    for (int l = 0; l < submarines; l++) {
      List<Coord> subLoc = getRandomEmptyCoords(3);
      placedShips.add(new Ship(ShipType.SUBMARINE, subLoc));
      markShips(subLoc);
    }
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
    Random random = new Random();
    List<Coord> emptyCoords = new ArrayList<>();
    boolean isValidCoordinate = false;

    boolean getRow = random.nextBoolean();

    if (getRow) {
      int rowIndex = random.nextInt(height);
      int startColIndex = random.nextInt(width - size + 1);

      isValidCoordinate = true;
      // possibly put into a while loop to make sure all the coords are empty
      for (int col = startColIndex; col < startColIndex + size; col++) {
        Coord coord = new Coord(rowIndex, col);
        if (!coord.checkEmpty(this)) {
          isValidCoordinate = false;
          break;
        }
        emptyCoords.add(coord);
      }
    } else {
      int colIndex = random.nextInt(width);
      int startRowIndex = random.nextInt(height - size + 1);

      isValidCoordinate = true;
      // possibly put into a while loop to make sure all the coords are empty
      for (int row = startRowIndex; row < startRowIndex + size; row++) {
        Coord coord = new Coord(row, colIndex);
        if (!coord.checkEmpty(this)) {
          isValidCoordinate = false;
          break;
        }
        emptyCoords.add(coord);
      }
    }

    if (isValidCoordinate) {
      return emptyCoords;
    }

    return getRandomEmptyCoords(size);
  }

  /**
   * Creates a map of selected ship specifications with their corresponding quantities.
   *
   * @param carrier    the quantity of carriers selected
   * @param battleship the quantity of battleships selected
   * @param dest       the quantity of destroyers selected
   * @param sub        the quantity of submarines selected
   * @return a map containing ship types as keys and their corresponding quantities as values
   */
  public static Map<ShipType, Integer> selectedShips(
      int carrier, int battleship, int dest, int sub) {
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, carrier);
    specs.put(ShipType.BATTLESHIP, battleship);
    specs.put(ShipType.DESTROYER, dest);
    specs.put(ShipType.SUBMARINE, sub);
    return specs;
  }

  /**
   * Checks if all ships on the board are sunk.
   *
   * @return true if all ships are sunk, false otherwise
   */
  public boolean shipsSunk() {
    boolean sunkShips = true;
    for (Ship s : ships) {
      if (!s.isSunk(this)) {
        sunkShips = false;
        break;
      }
    }
    return sunkShips;
  }


  /**
   * Retrieves the number of ships that are still alive on the board.
   *
   * @return the number of ships that are not yet sunk
   */
  public int numShipsAlive() {
    int shipsAlive = 0;
    for (Ship ship : ships) {
      if (!ship.isSunk(this)) {
        shipsAlive += 1;
      }
    }
    return shipsAlive;
  }

  /**
   * Retrieves the height of the board.
   *
   * @return the height of the board
   */
  public int getHeight() {
    return height;
  }

  /**
   * Retrieves the width of the board.
   *
   * @return the width of the board
   */
  public int getWidth() {
    return width;
  }
}

