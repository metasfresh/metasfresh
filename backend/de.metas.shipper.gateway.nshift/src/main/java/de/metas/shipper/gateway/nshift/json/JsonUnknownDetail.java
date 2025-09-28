package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JsonUnknownDetail {

    @JsonProperty("KindID")
    JsonUnknownDetailKind kindId;

    @JsonProperty("Value")
    String value;
}
