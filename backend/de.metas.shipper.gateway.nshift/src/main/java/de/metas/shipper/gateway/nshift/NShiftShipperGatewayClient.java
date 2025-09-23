package de.metas.shipper.gateway.nshift;

import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import lombok.NonNull;

import java.util.List;

public class NShiftShipperGatewayClient implements ShipperGatewayClient
{
	@Override
	public @NonNull ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@Override
	public @NonNull DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		throw new UnsupportedOperationException("Not supported yet."); // TODO
	}

	@Override
	public @NonNull List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		throw new UnsupportedOperationException("Not supported yet."); // TODO
	}
}
