package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.OpponentBoard;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.PlayerBoard;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.PrintDisplay;
import cs3500.pa03.view.UserInput;
import java.util.List;
import java.util.Map;

/**
 * The BattleSalvo class serves as a controller for the Battleship game. It manages the player's
 * board, opponent's board, and the player objects. It facilitates the gameplay by coordinating
 * between the players and handling game logic.
 */
public class BattleSalvo implements ControlInt {
  int width;
  int height;
  public static PlayerBoard playerBoard;
  public static OpponentBoard opponentBoard;
  private static Player player;
  private static AiPlayer opponent;
  Map<ShipType, Integer> selectedShips;
  List<Coord> playerFired;
  List<Coord> oppFired;
  List<Coord> playerShipsHit;
  List<Coord> oppShipsHit;
  boolean gameOver;

  /**
   * Constructs a BattleSalvo object with the specified players.
   *
   * @param player the player object representing the current player
   */
  public BattleSalvo(Player player) {
    this.player = player;
  }

  //    else if (player instanceof AiPlayer){
  //      this.player = player;
  //      this.height = ((AiPlayer) player).heightThresh;
  //      this.width = ((AiPlayer) player).widthThresh;
  //      this.playerBoard = new PlayerBoard(height, width);
  //      this.opponentBoard = new OpponentBoard(height, width);
  //      this.opponent = new AiPlayer(height, width, ships[0], ships[1], ships[2], ships[3]);
  //    }


  /**
   * Runs a full game of BattleSalvo
   */
  public void run() {
    if (player instanceof ManualPlayer) {
      int[] size = UserInput.askForTwoInt("Enter two numbers between 6 and 15 inclusive"
          + " (height and then width):\n", 6, 15);
      this.height = size[0];
      this.width = size[1];
      this.playerBoard = new PlayerBoard(height, width);
      this.opponentBoard = new OpponentBoard(height, width);
      int min = Math.min(height, width);
      int[] ships = UserInput.askForFourInt("Please enter the amount of ships desired in this"
          +
          " order: Carrier, Battleship, Destroyer, Submarine.\nThere must be at least one of each and"
          + " cannot exceed a total of " + min + " ships on the board.\n", min);
      selectedShips = player.getPlayerBoard().selectedShips(ships[0], ships[1], ships[2], ships[3]);
      //      this.playerBoard = ((ManualPlayer) player).getPlayerBoard();
      //      player.setup(height,width, selectedShips);
      playerBoard.placeShips(selectedShips);
      player.setup(height, width, selectedShips);

      this.opponent = new AiPlayer();
      opponent.setup(height, width, selectedShips);
      gameOver = false;

      player.name();

      while (!gameOver) {
        System.out.println("Your Board:");
        PrintDisplay.printBoard(player.getPlayerBoard());
        System.out.println("Opponent Board:");
        PrintDisplay.printBoard(player.getOpponentBoard());

        takeShots();

        reportDamage();

        successfulHits();

        checkEnd();
      }
    }
  }

  /**
   * has the player and the opponent take shots
   */
  public void takeShots() {
    playerFired = player.takeShots();
    oppFired = opponent.takeShots();
  }

  /**
   * reports the damages of both boards
   */
  public void reportDamage() {
    playerShipsHit = opponent.reportDamage(playerFired);
    oppShipsHit = player.reportDamage(oppFired);
  }

  /**
   * shows the successful hits of both boards
   */
  public void successfulHits() {
    player.successfulHits(playerShipsHit);
    opponent.successfulHits(oppShipsHit);
  }

  /**
   * checks whether the end is reached and the corresponding response if it has ended
   */
  public void checkEnd() {
    if (playerBoard.shipsSunk() && opponent.getPlayerBoard().shipsSunk()) {
      PrintDisplay.printAtEnd("Game over: Draw");
      gameOver = true;
    } else if (playerBoard.shipsSunk()) {
      PrintDisplay.printAtEnd("Game over: You lost :(");
      gameOver = true;
    } else if (opponent.getPlayerBoard().shipsSunk()) {
      PrintDisplay.printAtEnd("Game over: You won!");
      gameOver = true;
    }
  }
}
