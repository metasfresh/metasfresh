package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonOrderDataDetailKind {
    ORDER_TOTAL_VALUE(514),
    ORDER_VAT(515);

    @JsonValue
    private final int jsonValue;
}
