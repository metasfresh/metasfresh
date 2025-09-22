package de.metas.shipper.gateway.externalsystem.config;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import de.metas.shipping.ShipperGatewayId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class ExternalSystemShipperConfig
{
	@NonNull ShipperGatewayId shipperGatewayId;

	@JsonValue
	public Map<String, Object> toJson()
	{
		// TODO
		return ImmutableMap.of();
	}
}
