package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.PrintDisplay;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiPlayerTest {

  private AiPlayer aiPlayer;
  private PlayerBoard playerBoard;
  private OpponentBoard opponentBoard;

  @BeforeEach
  public void before() {
    int height = 10; // Example height value
    int width = 10; // Example width value

    Map<ShipType, Integer> numShips =
        PlayerBoard.selectedShips(1, 2, 3, 4);

    aiPlayer = new AiPlayer();
    aiPlayer.setup(height, width, numShips);

    playerBoard = aiPlayer.getPlayerBoard();
    opponentBoard = aiPlayer.getOpponentBoard();
  }

  @Test
  void name() {
  }

  @Test
  void setup() {
    assertNotNull(playerBoard);
    assertNotNull(opponentBoard);

    ByteArrayOutputStream testLog = new ByteArrayOutputStream(2048);

    Runnable testBoard = () -> PrintDisplay.printBoard(playerBoard);
    testBoard.run();

    System.out.println("This is the test: " + testLog.toString(StandardCharsets.UTF_8));

    String board = "B B B B 0 B B B B B \n"
        + "0 B B B 0 0 0 0 0 0 \n"
        + "0 0 B 0 0 0 0 0 B 0 \n"
        + "0 0 B 0 B B B 0 B 0 \n"
        + "0 0 B 0 0 0 0 0 B 0 \n"
        + "0 0 B 0 B B B 0 B 0 \n"
        + "0 0 B 0 0 0 0 B B 0 \n"
        + "0 0 B 0 0 0 0 B 0 0 \n"
        + "0 0 B B B B 0 B 0 0 \n"
        + "0 0 0 0 0 B B B B 0 ";

    // assertEquals(board, testBoard2);

  }

  @Test
  void takeShots() {
    List<Coord> shots = aiPlayer.takeShots();

    assertNotNull(shots);
    assertFalse(shots.isEmpty());
  }

  @Test
  void reportDamage() {
    List<Coord> opponentShotsOnBoard = new ArrayList<>();
    Coord cor1 = new Coord(1, 3);
    Coord cor2 = new Coord(1, 4);
    Coord cor3 = new Coord(1, 5);
    opponentShotsOnBoard.add(cor1);
    opponentShotsOnBoard.add(cor2);
    opponentShotsOnBoard.add(cor3);
    List<Coord> damageShots = aiPlayer.reportDamage(opponentShotsOnBoard);

    for (Coord c : damageShots) {
      assertTrue(c.checkHitOrShot(playerBoard));
    }

  }

  @Test
  void successfulHits() {
  }

  @Test
  void endGame() {
  }

  @Test
  void getPlayerBoard() {
  }

  @Test
  void getOpponentBoard() {
  }
}