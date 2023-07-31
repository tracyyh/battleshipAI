package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.PrintDisplay;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ShipTest {

  @Test
  void isSunk() {

    PlayerBoard board = new PlayerBoard(3, 3);
    PrintDisplay.printBoard(board);

    List<Coord> locations = new ArrayList<>();
    locations.add(new Coord(0, 0));
    locations.add(new Coord(0, 1));
    locations.add(new Coord(0, 2));
    locations.add(new Coord(1, 0));
    locations.add(new Coord(1, 1));
    locations.add(new Coord(1, 2));
    locations.add(new Coord(2, 0));
    locations.add(new Coord(2, 1));
    locations.add(new Coord(2, 2));

    Ship ship = new Ship(ShipType.CARRIER, locations);

    assertFalse(ship.isSunk(board));

    PrintDisplay.printBoard(board);

    board.markDamage(locations);
    PrintDisplay.printBoard(board);

    assertTrue(ship.isSunk(board));
  }


}