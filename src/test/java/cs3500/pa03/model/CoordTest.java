package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CoordTest {

  @Test
  void getxCoord() {
    Coord coord = new Coord(5, 0);
    assertTrue(coord.getXcoord() == 5);
  }

  @Test
  void getyCoord() {
    Coord coord = new Coord(5, 0);
    assertTrue(coord.getYcoord() == 0);
  }

  @Test
  void testCheckEmpty() {
    PlayerBoard board = new PlayerBoard(5, 5);
    Coord coord = new Coord(0, 0);

    assertTrue(coord.checkEmpty(board));

    board.board[coord.getXcoord()][coord.getYcoord()] = 'B';

    assertFalse(coord.checkEmpty(board));
  }

  @Test
  void testCheckShip() {
    PlayerBoard board = new PlayerBoard(5, 5);
    Coord coord = new Coord(0, 0);

    assertFalse(coord.checkShip(board));

    board.board[coord.getXcoord()][coord.getYcoord()] = 'B';

    assertTrue(coord.checkShip(board));
  }

  @Test
  void testCheckHitOrShot() {
    PlayerBoard board = new PlayerBoard(5, 5);
    Coord coord = new Coord(0, 0);

    assertFalse(coord.checkHitOrShot(board));

    board.board[coord.getXcoord()][coord.getYcoord()] = 'H';

    assertTrue(coord.checkHitOrShot(board));
  }
}