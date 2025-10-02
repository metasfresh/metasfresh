package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonShipmentMessageKind
{
	UNKNOWN(0),
	DRIVER(1),
	CARRIER(2),
	RECEIVER(3);

	@JsonValue
	private final int jsonValue;
}