package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;


class UserInputTest {

  UserInput ui = new UserInput();

  @Test
  void askNameTest() {
    String data = "ahhhh";
    System.setIn(new ByteArrayInputStream(data.getBytes()));
    String stuff = ui.askForName();
    assertEquals(data, stuff);
  }

  @Test
  void askForTwoInt() {
    String data = "8\n15";
    System.setIn(new ByteArrayInputStream(data.getBytes()));
    int[] stuff = ui.askForTwoInt("ahhh", 1, 16);
    int[] numbers = new int[]{8, 15};
    // assertEquals(Arrays.stream(numbers).toArray(), Arrays.stream(stuff).toArray());
    assertTrue(Arrays.equals(numbers, stuff));

  }

  @Test
  void askForFourInt() {
    String data = "1\n2\n3\n4";
    System.setIn(new ByteArrayInputStream(data.getBytes()));
    int[] stuff = ui.askForFourInt("bleh bleh bleh", 16);
    int[] numbers = new int[]{1, 2, 3, 4};
    assertTrue(Arrays.equals(numbers, stuff));
  }

  @Test
  void askCoords() {
    Coord c1 = new Coord(2, 0);
    Coord c2 = new Coord(0, 1);
    Coord c3 = new Coord(2, 1);
    List<Coord> thing = new ArrayList<>();
    thing.add(c1);
    thing.add(c2);
    thing.add(c3);

    String data = "10\n10\n0\n2\n1\n0\n2\n2\n";
    System.setIn(new ByteArrayInputStream(data.getBytes()));
    List<Coord> stuff = ui.askCoords(3, 3, 3);

    System.setIn(new ByteArrayInputStream(data.getBytes()));

    assertEquals(thing.get(0).getXcoord(), stuff.get(0).getXcoord());
    assertEquals(thing.get(0).getYcoord(), stuff.get(0).getYcoord());
    assertEquals(thing.get(1).getXcoord(), stuff.get(1).getXcoord());
    assertEquals(thing.get(1).getYcoord(), stuff.get(1).getYcoord());
  }
}