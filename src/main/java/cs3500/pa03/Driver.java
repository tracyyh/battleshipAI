package cs3500.pa03;

import cs3500.pa03.controller.BattleSalvo;
import cs3500.pa03.controller.ControlInt;
import cs3500.pa03.controller.ProxyDealer;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.ManualPlayer;
import java.io.IOException;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {

  private static void runClient(String host, int port) throws IOException {
    try {
      Socket server = new Socket(host, port);
      ControlInt proxyDealer = new ProxyDealer(server, new AiPlayer());
      proxyDealer.run();
    } catch (IOException e) {
      throw new IOException("Invalid host or port argument.");
    }
  }

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    System.out.println("Hello from Battle Salvo - PA04 Template Repo");

    if (args.length == 0) {
      ManualPlayer player = new ManualPlayer();
      BattleSalvo salvoGame = new BattleSalvo(player);
      salvoGame.run();
    } else if (args.length == 2) {
      try {
        Driver.runClient(args[0], Integer.parseInt(args[1]));
      } catch (IOException e) {
        new IOException("Invalid host or port argument.");
      }
    }
  }
}