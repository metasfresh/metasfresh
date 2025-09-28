package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonDHLFilingDetailKind {
    UNKNOWN(0),
    FILING_TYPE(501),
    FTSR(502),
    INT(503),
    AES4EIN(504);

    @JsonValue
    private final int jsonValue;
}
