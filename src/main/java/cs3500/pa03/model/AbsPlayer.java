package cs3500.pa03.model;

import cs3500.pa03.view.UserInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * represents a player in the game
 */
public abstract class AbsPlayer implements Player {
  PlayerBoard playerBoard;
  OpponentBoard opponentBoard;
  int heightThresh;
  int widthThresh;

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return UserInput.askForName();
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.playerBoard = new PlayerBoard(width, height);
    this.opponentBoard = new OpponentBoard(width, height);
    playerBoard.placeShips(specifications);
    this.heightThresh = height;
    this.widthThresh = width;

    return playerBoard.ships;
  }

  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   * ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> damage = new ArrayList<>();
    List<Coord> missed = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      if (c.checkShip(playerBoard)) {
        damage.add(c);
      } else {
        missed.add(c);
      }
    }
    playerBoard.markDamage(damage);
    playerBoard.markShots(missed);
    return damage;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    opponentBoard.markDamage(shotsThatHitOpponentShips);
  }

  @Override
  public void endGame(GameResult result, String reason) {

  }

  /**
   * Returns a players board
   */
  public PlayerBoard getPlayerBoard() {
    return playerBoard;
  }

  /**
   * Returns a players view of their opponents board
   */
  public OpponentBoard getOpponentBoard() {
    return opponentBoard;
  }

}
