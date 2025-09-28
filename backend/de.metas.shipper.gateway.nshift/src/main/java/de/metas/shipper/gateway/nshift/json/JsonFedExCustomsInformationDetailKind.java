package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonFedExCustomsInformationDetailKind {
    UNKNOWN(0),
    CUSTOMS_AMOUNT(174),
    CUSTOMS_CURRENCY(175);

    @JsonValue
    private final int jsonValue;
}
