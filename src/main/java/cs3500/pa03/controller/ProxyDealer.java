package cs3500.pa03.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.json.CoordJson;
import cs3500.pa03.json.CoordinatesJson;
import cs3500.pa03.json.EndGameJson;
import cs3500.pa03.json.FleetJson;
import cs3500.pa03.json.JoinJson;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;
import cs3500.pa03.json.SetupJson;
import cs3500.pa03.json.ShipJson;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * represents the proxy controller that communicates with the AI and the server
 */
public class ProxyDealer implements ControlInt {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  //  private final Player opponent;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");
  private final JsonUtils utils = new JsonUtils();
  private int shotsToTake;
  private int shotsTaken;

  /**
   * constructor
   *
   * @param server the server being played on
   * @param player the AI player
   * @throws IOException if input/output error
   */
  public ProxyDealer(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
    this.shotsTaken = 0;
  }

  /**
   * runs the program with the server
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        System.out.printf(mapper.writeValueAsString(message));
        delegateMessage(message);
      }
    } catch (IOException e) {
      System.err.println("Disconnected from server");
    }
  }

  /**
   * delegates the JSON objects to the appropriate method
   *
   * @param message JSON message
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * handles the case where player joins the server
   */
  private void handleJoin() {
    JoinJson joinJson = new JoinJson("ho.es@northeastern", "SINGLE");
    JsonNode jsonResponse = utils.serializeRecord(joinJson);
    this.out.println(convertToMessage("join", jsonResponse));

  }

  /**
   * hands the case where the boards and ships are being set up
   *
   * @param arguments includes the width, height, and fleet specifications
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupJson = this.mapper.convertValue(arguments, SetupJson.class);
    List<JsonNode> ships = new ArrayList<>();

    List<Ship> list = player.setup(setupJson.width(), setupJson.height(), setupJson.fleetSpec());
    for (Ship s : list) {
      Coord first = s.firstCoord();
      JsonNode firstCoord = coordFromxAndy(first.getYcoord(), first.getXcoord());
      ShipJson shipJson = new ShipJson(firstCoord, s.shipSize(), s.findDirection());
      ships.add(utils.serializeRecord(shipJson));
    }
    FleetJson fleetJson = new FleetJson(ships);
    JsonNode jsonResponse = utils.serializeRecord(fleetJson);
    this.out.println(convertToMessage("setup", jsonResponse));
    this.shotsToTake = setupJson.width() * setupJson.height();
  }

  /**
   * handles the case where the players are taking shots
   */
  private void handleTakeShots() {
    List<Coord> shots;
    int shipsAlive = player.getPlayerBoard().numShipsAlive();
    if (shotsTaken < shotsToTake && shipsAlive + shotsTaken < shotsToTake) {
      shots = player.takeShots();
      shotsTaken += shots.size();
    } else {
      // how do we make it take less shots than the amount of ships that are alive
      //
      shots = new ArrayList<>();
    }
    this.out.println(convertToMessage("take-shots", coordToCoordinates(shots)));
  }

  /**
   * handles the case where the damage to the ships is being reported
   *
   * @param arguments the coordinates of the shots
   */
  private void handleReportDamage(JsonNode arguments) {
    List<Coord> shots =
        coordinatesToCoord(this.mapper.convertValue(arguments, CoordinatesJson.class));
    List<Coord> damage = player.reportDamage(shots);
    this.out.println(convertToMessage("report-damage", coordToCoordinates(damage)));
  }

  /**
   * handles the case where successful hits are shown
   *
   * @param arguments the coordinates of the shots
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    List<Coord> shots =
        coordinatesToCoord(this.mapper.convertValue(arguments, CoordinatesJson.class));
    player.successfulHits(shots);
    this.out.println(convertToMessage("successful-hits", utils.serializeRecord(null)));
  }

  /**
   * handles the case where the game ends for a specific reason
   *
   * @param arguments result and reason why the game ended
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameJson = this.mapper.convertValue(arguments, EndGameJson.class);
    player.endGame(endGameJson.result(), endGameJson.reason());
    this.out.println(convertToMessage("end-game", utils.serializeRecord(null)));
  }

  /**
   * converts the JsonNode to a MessageJson
   *
   * @param name         of the method
   * @param jsonResponse what is being returned to the server
   * @return the new MessageJson returned to the server
   */
  private JsonNode convertToMessage(String name, JsonNode jsonResponse) {
    MessageJson message = new MessageJson(name, jsonResponse);
    return utils.serializeRecord(message);
  }

  /**
   * serializes a new coordinate
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return the serialized coordinate
   */
  private JsonNode coordFromxAndy(int x, int y) {
    CoordJson firstJson = new CoordJson(x, y);
    return utils.serializeRecord(firstJson);
  }

  /**
   * converts a list of coordinates into a CoordinatesJson
   *
   * @param coords list to be converted
   * @return the serialized coordinatesJson
   */
  public JsonNode coordToCoordinates(List<Coord> coords) {
    List<JsonNode> jsonCoords = new ArrayList<>();
    for (Coord c : coords) {
      JsonNode coordJson = coordFromxAndy(c.getYcoord(), c.getXcoord());
      jsonCoords.add(coordJson);
    }
    CoordinatesJson coordsJson = new CoordinatesJson(jsonCoords);
    return utils.serializeRecord(coordsJson);
  }

  /**
   * converts a CoordinatesJson into a list of coordinates
   *
   * @param coordinates the CoordinatesJson to be converted
   * @return the list of coordinates
   */
  public List<Coord> coordinatesToCoord(CoordinatesJson coordinates) {
    List<Coord> shots = new ArrayList<>();
    for (int i = 0; i < coordinates.list().size(); i++) {
      JsonNode coord = coordinates.list().get(i);
      CoordJson currentJson = this.mapper.convertValue(coord, CoordJson.class);
      Coord current = new Coord(currentJson.y(), currentJson.x());
      shots.add(current);
    }
    return shots;
  }
}
