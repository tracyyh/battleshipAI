package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * represents a ShipJson
 *
 * @param coord     the first coordinate of the ship
 * @param length    length of the ship
 * @param direction direction the ship is facing
 */
public record ShipJson(

    @JsonProperty("coord") JsonNode coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {
}

