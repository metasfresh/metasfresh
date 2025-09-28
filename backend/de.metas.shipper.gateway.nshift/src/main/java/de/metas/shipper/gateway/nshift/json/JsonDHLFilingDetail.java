package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JsonDHLFilingDetail {

    @JsonProperty("KindID")
    JsonDHLFilingDetailKind kindId;

    @JsonProperty("Value")
    String value;
}
