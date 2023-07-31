package cs3500.pa03.view;

import cs3500.pa03.model.Board;

/**
 * Used to print a specified item to the display
 */
public class PrintDisplay {
  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param board BattleSalvo board
   */
  public static void printBoard(Board board) {
    for (int i = 0; i < board.getHeight(); i++) {
      for (int j = 0; j < board.getWidth(); j++) {
        System.out.print(board.board[i][j] + " ");
      }
      System.out.println();
    }
  }

  /**
   * Prints the given text to the display at the end of a game
   *
   * @param text the text to be displayed
   */
  public static void printAtEnd(String text) {
    System.out.println(text);
  }
}
