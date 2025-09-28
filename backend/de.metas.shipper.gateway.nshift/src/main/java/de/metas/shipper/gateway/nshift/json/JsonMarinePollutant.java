package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonMarinePollutant {
    EDGMP_UNKNOWN(0),
    EDGMP_NONE(1),
    EDGMP_IMDG(2),
    EDGMP_ADR(3);

    @JsonValue
    private final int jsonValue;
}
