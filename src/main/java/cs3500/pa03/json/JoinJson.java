package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a JoinJson
 *
 * @param gitHubUser the gitHub username
 * @param gameType   the type of game being played
 */
public record JoinJson(
    @JsonProperty("name") String gitHubUser,
    @JsonProperty("game-type") String gameType) {
}
