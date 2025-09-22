package de.metas.shipper.gateway.externalsystem;

import de.metas.shipper.gateway.externalsystem.config.ExternalSystemShipperConfigService;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalSystemShipperGatewayClientFactory implements ShipperGatewayClientFactory
{
	@NonNull private final ExternalSystemShipperConfigService configService;

	@Override
	public ShipperGatewayId getShipperGatewayId() {return null; /* default */}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		return ExternalSystemShipperGatewayClient.builder()
				.config(configService.getConfig(shipperId))
				.build();
	}
}
