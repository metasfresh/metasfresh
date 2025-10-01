package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonLineResponse.JsonLineResponseBuilder.class)
public class JsonLineResponse {

    @JsonProperty("Pkgs")
    List<JsonPackageResponse> pkgs;
}
