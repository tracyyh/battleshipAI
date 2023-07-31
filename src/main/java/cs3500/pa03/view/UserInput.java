package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The UserInput class provides methods for asking the user for input and returning arrays or lists
 * of integers based on the user's input.
 */
public class UserInput {

  public static String askForName() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please print your name");
    String thing = scanner.nextLine();
    System.out.println(thing);
    return thing;
  }

  /**
   * Asks the user for two integers within a specified range and returns them as an array.
   *
   * @param text the prompt text to display to the user
   * @param min the minimum allowed value for the integers
   * @param max the maximum allowed value for the integers
   * @return an array containing the two integers entered by the user
   */
  public static int[] askForTwoInt(String text, int min, int max) {
    Scanner scanner = new Scanner(System.in);
    int num1;
    int num2;
    System.out.print(text);

    num1 = scanner.nextInt();
    num2 = scanner.nextInt();

    while (num1 < min || num1 > max || num2 < min || num2 > max) {
      System.out.println("Both numbers must be between " + min + " and " + max
          + ". Please try again.");
      num1 = scanner.nextInt();
      num2 = scanner.nextInt();
    }

    int[] numbers = new int[2];
    numbers[0] = num1;
    numbers[1] = num2;

    return numbers;
  }

  /**
   * Asks the user for four integers and returns them as an array. The sum of the four integers
   * must not exceed a specified maximum value, and all four integers must be greater than zero.
   *
   * @param text the prompt text to display to the user
   * @param max the maximum sum allowed for the four integers
   * @return an array containing the four integers entered by the user
   */
  public static int[] askForFourInt(String text, int max) {
    Scanner scanner = new Scanner(System.in);

    System.out.print(text);

    int num1;
    num1 = scanner.nextInt();
    int num2;
    num2 = scanner.nextInt();
    int num3;
    num3 = scanner.nextInt();
    int num4;
    num4 = scanner.nextInt();

    int sum = num1 + num2 + num3 + num4;

    while (sum > max || num1 == 0 || num2 == 0 || num3 == 0 || num4 == 0) {
      System.out.println("Numbers must be greater than zero and can not add up to a \n"
          + "number greater than " + max);
      num1 = scanner.nextInt();
      num2 = scanner.nextInt();
      num3 = scanner.nextInt();
      num4 = scanner.nextInt();
      sum = num1 + num2 + num3 + num4;
    }

    int[] numbers = new int[4];
    numbers[0] = num1;
    numbers[1] = num2;
    numbers[2] = num3;
    numbers[3] = num4;

    return numbers;
  }

  /**
   * Asks the user for a specified number of coordinate pairs and returns as a list of Coords.
   * The coordinates must be within specified height and width thresholds.
   *
   * @param count the number of coordinate pairs to ask for
   * @param heightThresh the maximum allowed value for the y-coordinate
   * @param widthThresh the maximum allowed value for the x-coordinate
   * @return a list of Coord objects representing the entered coordinates
   */
  public static List<Coord> askCoords(int count, int heightThresh, int widthThresh) {
    Scanner scanner = new Scanner(System.in);
    List<Coord> shots = new ArrayList<>();

    System.out.println("Please enter " + count + " shots:");
    for (int i = 0; i < count; i++) {
      int x;
      int y;
      boolean validInput = false;
      x = scanner.nextInt();
      y = scanner.nextInt();

      do {
        if (x >= 0 && y >= 0 && x <= widthThresh - 1 && y <= heightThresh - 1) {
          validInput = true;
        } else {
          System.out.println("Invalid coordinates. Please enter most recent coordinate entered with"
              + " an x between zero and " + (widthThresh - 1) + " and a y between 0 and "
              + (heightThresh - 1));
          x = scanner.nextInt();
          y = scanner.nextInt();
        }
      } while (!validInput);

      shots.add(new Coord(y, x));
    }
    return shots;
  }
}
