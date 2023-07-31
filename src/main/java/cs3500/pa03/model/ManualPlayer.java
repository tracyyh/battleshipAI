package cs3500.pa03.model;

import cs3500.pa03.view.PrintDisplay;
import cs3500.pa03.view.UserInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a user player in a game of BattleSalvo.
 */
public class ManualPlayer extends AbsPlayer {
  public ManualPlayer() {
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    int floatingShips = playerBoard.numShipsAlive();
    int height = playerBoard.getHeight();
    int width = playerBoard.getWidth();

    List<Coord> userInput = UserInput.askCoords(floatingShips, height, width);
    getOpponentBoard().markShots(userInput);
    return userInput;
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }

}
