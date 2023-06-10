package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * JSON structure looks like:
 * [
 *    {"coord":{"x":0,"y":0},
 *    "length":3,
 *    "direction":"VERTICAL"}
 * ]
 * Represents fleet (a list of ships).
 *
 * @param fleet the fleet of the player
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapter> fleet) {
}
