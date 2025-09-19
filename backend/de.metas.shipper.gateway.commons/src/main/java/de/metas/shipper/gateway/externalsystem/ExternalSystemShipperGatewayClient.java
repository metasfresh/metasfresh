package de.metas.shipper.gateway.externalsystem;

import de.metas.shipper.gateway.externalsystem.config.ExternalSystemShipperConfig;
import de.metas.shipper.gateway.externalsystem.protocol.JsonShipmentRequest;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
class ExternalSystemShipperGatewayClient implements ShipperGatewayClient
{
	@NonNull final ExternalSystemShipperConfig config;

	@Override
	public @NonNull String getShipperGatewayId() {return config.getShipperGatewayId();}

	@Override
	public @NonNull DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		JsonShipmentRequest.builder()
				.shipperConfig(config.toJson())
				// TODO implement
				.build();
		
		// TODO send to external systems and get the response
		// externalSystemMessageSender.send(JsonExternalSystemRequest)

		throw new UnsupportedOperationException("not implemented yet"); // TODO 
		// JsonShipmentResponse.builder()
		// 		.build();
		
		// TODO return Future<DeliveryOrder> ?
	}

	@Override
	public @NonNull List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		throw new UnsupportedOperationException("not implemented yet"); // TODO 
	}

}
