package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the JSON format with an end game.
 */
public record EndGameJson(@JsonProperty("result") String result,
                          @JsonProperty("reason") String reason) {
}
