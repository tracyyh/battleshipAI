package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * represents a SetupJson
 *
 * @param height    height of the board
 * @param width     width of the board
 * @param fleetSpec the fleet specifications to be added
 */
public record SetupJson(
    @JsonProperty("height") int height,
    @JsonProperty("width") int width,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {

}
