package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa03.json.JoinJson;
import cs3500.pa03.json.JsonUtils;
import cs3500.pa03.json.MessageJson;
import cs3500.pa03.model.AiPlayer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyDealerTest {

  private ByteArrayOutputStream testLog;
  private ProxyDealer dealer;
  private String join;
  private String setup;
  private String setupResponse;
  private String takeShots;
  private String takeShotsResponse;
  private String reportDamage;
  private String reportDamageResponse;
  private String successfulHits;
  private String successfulHitsResponse;
  private String endGame;
  private String endGameResponse;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    join = "{\n"
        + "\t\"method-name\": \"join\",\n"
        + "\t\"arguments\": {}\n"
        + "}";
    setup = "{\n"
        + "\t\"method-name\": \"setup\",\n"
        + "\t\"arguments\": {\n"
        + "\t\t\"width\": 10,\n"
        + "\t\t\"height\": 10,\n"
        + "\t\t\"fleet-spec\": {\n"
        + "\t\t\t\"CARRIER\": 2,\n"
        + "\t\t\t\"BATTLESHIP\": 4,\n"
        + "\t\t  \"DESTROYER\": 1,\n"
        + "\t\t\t\"SUBMARINE\": 3\n"
        + "\t\t}\n"
        + "\t}\n"
        + "}";
    setupResponse = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[";
    takeShots = "{\n"
        + "\t\"method-name\": \"take-shots\",\n"
        + "\t\"arguments\": {}\n"
        + "}";
    takeShotsResponse = "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[";
    reportDamage = "{\n"
        + "\t\"method-name\": \"report-damage\",\n"
        + "\t\"arguments\": {\n"
        + "\t\t\"coordinates\": [\n"
        + "\t\t\t{\"x\": 0, \"y\": 1},\n"
        + "\t\t\t{\"x\": 3, \"y\": 2}\n"
        + "\t\t]\n"
        + "\t}\n"
        + "}";
    reportDamageResponse = "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[";
    successfulHits = "{\n"
        + "\t\"method-name\": \"successful-hits\",\n"
        + "\t\"arguments\": {\n"
        + "\t\t\"coordinates\": [\n"
        + "\t\t\t{\"x\": 0, \"y\": 1},\n"
        + "\t\t\t{\"x\": 3, \"y\": 2}\n"
        + "\t\t]\n"
        + "\t}\n"
        + "}";
    successfulHitsResponse = "{\"method-name\":\"successful-hits\",\"arguments\":{}";
    endGame = "{\n"
        + "\t\"method-name\": \"end-game\",\n"
        + "\t\"arguments\": {\n"
        + "\t\t\"result\": \"WIN\",\n"
        + "\t\t\"reason\": \"Player 1 sank all of Player 2's ships\"\n"
        + "\t}\n"
        + "}";
    endGameResponse = "{\"method-name\":\"end-game\",\"arguments\":{}";
    assertEquals("", logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * tests the method handleJoin
   */
  @Test
  public void testJoin() {
    JoinJson joinJson = new JoinJson("ho.es@northeastern", "SINGLE");
    JsonNode sampleMessage = createSampleMessage("join", joinJson);
    Mocket socket = new Mocket(this.testLog, List.of(join));

    try {
      this.dealer = new ProxyDealer(socket, new AiPlayer());
    } catch (IOException e) {
      fail();
    }
    this.dealer.run();
    assertEquals(sampleMessage.toString() + "\n", logToString());
  }

  /**
   * tests the method handleSetup
   */
  @Test
  public void testSetup() {
    Mocket socket = new Mocket(this.testLog, List.of(setup));

    try {
      this.dealer = new ProxyDealer(socket, new AiPlayer());
    } catch (IOException e) {
      fail();
    }
    this.dealer.run();
    assertTrue(logToString().contains(setupResponse));
  }

  /**
   * tests the method handleTakeShots
   */
  @Test
  public void testTakeShots() {
    Mocket socket = new Mocket(this.testLog, List.of(join, setup, takeShots));

    try {
      this.dealer = new ProxyDealer(socket, new AiPlayer());
    } catch (IOException e) {
      fail();
    }
    this.dealer.run();
    assertTrue(logToString().contains(takeShotsResponse));
  }

  /**
   * tests the method handleReportDamage
   */
  @Test
  public void testReportDamage() {
    Mocket socket = new Mocket(this.testLog, List.of(join, setup, takeShots, reportDamage));

    try {
      this.dealer = new ProxyDealer(socket, new AiPlayer());
    } catch (IOException e) {
      fail();
    }
    this.dealer.run();
    assertTrue(logToString().contains(reportDamageResponse));
  }

  /**
   * tests the method handleSuccessfulHits
   */
  @Test
  public void testSuccessfulHits() {
    Mocket socket = new Mocket(this.testLog, List.of(join, setup, takeShots, reportDamage,
        successfulHits));

    try {
      this.dealer = new ProxyDealer(socket, new AiPlayer());
    } catch (IOException e) {
      fail();
    }
    this.dealer.run();
    assertTrue(logToString().contains(successfulHitsResponse));
  }

  /**
   * tests the method handleEndGame
   */
  @Test
  public void testEndGame() {
    Mocket socket = new Mocket(this.testLog, List.of(join, setup, takeShots, reportDamage,
        successfulHits, endGame));

    try {
      this.dealer = new ProxyDealer(socket, new AiPlayer());
    } catch (IOException e) {
      fail();
    }
    this.dealer.run();
    assertTrue(logToString().contains(endGameResponse));
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
