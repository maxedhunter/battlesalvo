package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * JSON structure looks like:
 * [
 *    {"x": 4, "y": 2},
 *    {"x": 7, "y": 1}
 * ]
 * Represents coordinates (a list of coords).
 *
 * @param shots list of coordinate shots
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") List<Coord> shots) {
}
