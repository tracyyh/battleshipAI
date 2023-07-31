package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The OpponentBoard class represents the game board for the opponent.
 */
public class OpponentBoard extends Board {

  List<Ship> ships = new ArrayList<>();

  /**
   * Constructs a new OpponentBoard object with the specified height and width.
   *
   * @param height the height of the opponent's board
   * @param width  the width of the opponent's board
   */
  public OpponentBoard(int height, int width) {
    super(height, width);
  }

  /**
   * Marks the shots taken on the opponent's board.
   *
   * @param shotsTaken the list of coordinates representing the shots taken
   */
  public void markShots(List<Coord> shotsTaken) {
    super.markShots(shotsTaken);
  }

  /**
   * Marks the hits taken on the opponent's board.
   *
   * @param hitsTaken the list of coordinates representing the hits taken
   */
  public void markDamage(List<Coord> hitsTaken) {
    super.markDamage(hitsTaken);
  }
}
