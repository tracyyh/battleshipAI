package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an ai Player in BattleSalvo
 */
public class AiPlayer extends AbsPlayer {

  String name = "AI Player";

  /**
   * Contructor for an Ai Player
   */

  public AiPlayer() {}

  /**
   * Returns this ai's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this ai's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    Random rand = new Random();
    int floatingShips = playerBoard.numShipsAlive();
    List<Coord> shots = new ArrayList<>();

    for (int i = 0; i < floatingShips; i++) {
      int x;
      int y;
      boolean validInput = false;
      Coord possible;

      do {
        x = rand.nextInt(widthThresh);
        y = rand.nextInt(heightThresh);
        possible = new Coord(x, y);

        if (possible.checkEmpty(opponentBoard)) {
          validInput = true;
          opponentBoard.board[x][y] = 'S';
        }
      } while (!validInput);

      shots.add(possible);
    }
    opponentBoard.markShots(shots);
    return shots;
  }

  /**
   * Notifies the ai that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }

}