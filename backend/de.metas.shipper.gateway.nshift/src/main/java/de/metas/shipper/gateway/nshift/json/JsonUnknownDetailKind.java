package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonUnknownDetailKind
{
	UNKNOWN(0);

	@JsonValue
	private final int jsonValue;
}
