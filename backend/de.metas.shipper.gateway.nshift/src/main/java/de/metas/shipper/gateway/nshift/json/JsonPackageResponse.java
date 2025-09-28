package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPackageResponse {

    @JsonProperty("PkgNo")
    String pkgNo;
}
