package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents a setup message from the server.
 */
public record SetupJson(@JsonProperty ("width") int width,
                        @JsonProperty ("height") int height,
                        @JsonProperty ("fleet-spec") JsonNode fleetSpec) {
}
