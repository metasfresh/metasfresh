package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonLineResponse {

    @JsonProperty("Pkgs")
    List<JsonPackageResponse> pkgs;
}
