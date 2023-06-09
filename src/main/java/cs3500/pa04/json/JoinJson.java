package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a join message to the server
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType gameType) {
}
