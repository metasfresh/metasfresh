package de.metas.shipper.gateway.nshift.client;

import de.metas.shipper.gateway.nshift.NShiftConstants;
import de.metas.shipper.gateway.nshift.config.NShiftConfig;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
public class NShiftShipperGatewayClient implements ShipperGatewayClient
{
	@NonNull private final NShiftConfig config;
	
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
