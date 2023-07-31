package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a CoordJson
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */
public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

}
