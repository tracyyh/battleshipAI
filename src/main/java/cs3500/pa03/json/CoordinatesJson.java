package cs3500.pa03.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa03.model.Coord;
import java.util.List;


/**
 * docs
 *
 * @param list list
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") List<JsonNode> list) {

}
