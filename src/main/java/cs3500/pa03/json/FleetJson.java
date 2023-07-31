package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * represents a FleetJson
 *
 * @param list the list of serialized ships
 */
public record FleetJson(
    @JsonProperty("fleet") List<JsonNode> list) {
}
