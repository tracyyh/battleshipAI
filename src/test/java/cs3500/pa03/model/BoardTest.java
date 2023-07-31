package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
  int height = 7;
  int width = 7;
  PlayerBoard board = new PlayerBoard(height, width);
  OpponentBoard oppBoard = new OpponentBoard(height, width);

  @BeforeEach
  void before() {
    board = new PlayerBoard(height, width);
    oppBoard = new OpponentBoard(height, width);
  }

  @Test
  void newBoard() {
    int height = 7;
    int width = 7;
    PlayerBoard board = new PlayerBoard(height, width);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        assertTrue(board.board[i][j] == '0');
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        assertTrue(oppBoard.board[i][j] == '0');
      }
    }
  }

  @Test
  void markShots() {

    List<Coord> shotsTaken = Arrays.asList(
        new Coord(1, 2),
        new Coord(3, 4)
    );

    board.markShots(shotsTaken);
    oppBoard.markShots(shotsTaken);

    assertEquals('S', board.board[1][2]);
    assertEquals('S', board.board[3][4]);

    assertEquals('S', oppBoard.board[1][2]);
    assertEquals('S', oppBoard.board[3][4]);
  }

  @Test
  void markDamage() {

    List<Coord> hitsTaken = Arrays.asList(
        new Coord(0, 0),
        new Coord(2, 3)
    );

    board.markDamage(hitsTaken);
    oppBoard.markDamage(hitsTaken);

    assertEquals('H', board.board[0][0]);
    assertEquals('H', board.board[2][3]);
    assertEquals('H', oppBoard.board[0][0]);
    assertEquals('H', oppBoard.board[2][3]);
  }

  @Test
  void placeShips() {

    Map<ShipType, Integer> shipSpecifications = Map.of(
        ShipType.CARRIER, 1,
        ShipType.BATTLESHIP, 1,
        ShipType.DESTROYER, 1,
        ShipType.SUBMARINE, 1
    );

    List<Ship> placedShips = board.placeShips(shipSpecifications);

    assertNotNull(placedShips);
    assertEquals(4, placedShips.size());

    for (Ship ship : placedShips) {
      for (Coord coord : ship.locations) {
        assertEquals('B', board.board[coord.getXcoord()][coord.getYcoord()]);
      }
    }
  }

  @Test
  void getRandomEmptyCoords() {

    board.board[1][1] = '0';
    board.board[2][1] = '0';
    board.board[3][1] = '0';

    List<Coord> randomEmptyCoords = board.getRandomEmptyCoords(3);

    assertNotNull(randomEmptyCoords);

    assertEquals(3, randomEmptyCoords.size());

    for (Coord coord : randomEmptyCoords) {
      assertTrue(coord.checkEmpty(board));
    }
  }

  @Test
  public void testAllShipsSunk() {
    Coord co1 = new Coord(1, 3);
    Coord co2 = new Coord(1, 4);
    Coord co3 = new Coord(1, 5);
    Coord co4 = new Coord(1, 6);
    List coords = new ArrayList<>();
    coords.add(co1);
    coords.add(co2);
    coords.add(co3);
    coords.add(co4);

    Coord cor1 = new Coord(1, 3);
    Coord cor2 = new Coord(1, 4);
    Coord cor3 = new Coord(1, 5);
    List coords2 = new ArrayList<>();
    coords2.add(cor1);
    coords2.add(cor2);
    coords2.add(cor3);

    Ship ship1 = new Ship(ShipType.DESTROYER, coords);
    Ship ship2 = new Ship(ShipType.SUBMARINE, coords2);
    PlayerBoard board = new PlayerBoard(6, 6);
    board.ships.add(ship1);
    board.ships.add(ship2);

    for (Ship ship : board.ships) {
      ship.sunk = true;
    }

    assertTrue(board.shipsSunk());
  }

  @Test
  void getHeight() {
    PlayerBoard board = new PlayerBoard(4, 1);
    assertTrue(board.getHeight() == 4);

    OpponentBoard oppBoard = new OpponentBoard(4, 1);
    assertTrue(oppBoard.getHeight() == 4);

  }

  @Test
  void getWidth() {
    PlayerBoard board = new PlayerBoard(4, 1);
    assertTrue(board.getWidth() == 1);

    OpponentBoard oppBoard = new OpponentBoard(4, 1);
    assertTrue(oppBoard.getWidth() == 1);
  }
}