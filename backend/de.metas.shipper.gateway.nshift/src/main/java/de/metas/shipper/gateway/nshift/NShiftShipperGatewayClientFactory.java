package de.metas.shipper.gateway.nshift;

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
	@Override
	public ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		return new NShiftShipperGatewayClient();
	}
}
