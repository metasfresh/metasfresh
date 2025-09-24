package de.metas.shipper.gateway.nshift;

import de.metas.shipper.gateway.nshift.client.NShiftShipperGatewayClient;
import de.metas.shipper.gateway.nshift.config.NShiftConfig;
import de.metas.shipper.gateway.nshift.config.NShiftConfigRepository;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NShiftShipperGatewayClientFactory implements ShipperGatewayClientFactory
{
	@NonNull private final NShiftConfigRepository configRepository;

	@Override
	public ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		final NShiftConfig config = configRepository.getByShipperId(shipperId);

		return NShiftShipperGatewayClient.builder()
				.config(config)
				.build();
	}
}
