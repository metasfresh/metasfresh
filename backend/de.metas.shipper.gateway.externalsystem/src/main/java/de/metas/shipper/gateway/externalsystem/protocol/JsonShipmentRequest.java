package de.metas.shipper.gateway.externalsystem.protocol;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonShipmentRequest
{
	Map<String, Object> shipperConfig;
	// TODO
}
