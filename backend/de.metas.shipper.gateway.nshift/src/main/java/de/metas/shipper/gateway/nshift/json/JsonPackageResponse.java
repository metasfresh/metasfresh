package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonPackageResponse.JsonPackageResponseBuilder.class)
public class JsonPackageResponse {

    @JsonProperty("PkgNo")
    String pkgNo;
}
