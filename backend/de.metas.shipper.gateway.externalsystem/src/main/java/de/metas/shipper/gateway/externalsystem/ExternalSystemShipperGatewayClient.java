package de.metas.shipper.gateway.externalsystem;

import com.google.common.collect.ImmutableMap;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.shipper.gateway.externalsystem.config.ExternalSystemShipperConfig;
import de.metas.shipper.gateway.externalsystem.protocol.JsonShipmentRequest;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

@Builder
class ExternalSystemShipperGatewayClient implements ShipperGatewayClient
{
	@NonNull final ExternalSystemShipperConfig config;
	@NonNull final ExternalSystemMessageSender externalSystemMessageSender;

	@Override
	public @NonNull ShipperGatewayId getShipperGatewayId() {return config.getShipperGatewayId();}

	@Override
	public @NonNull DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		JsonShipmentRequest.builder()
				.shipperConfig(config.toJson())
				// TODO implement
				.build();

		// TODO send to external systems and get the response
		externalSystemMessageSender.send(JsonExternalSystemRequest.builder()
				.orgCode(null)
				.externalSystemName(null)
				.command(null)
				.adPInstanceId(null)
				.externalSystemConfigId(null)
				.parameters(ImmutableMap.of())
				.traceId(null)
				.writeAuditEndpoint(null)
				.externalSystemChildConfigValue(null)
				.build());

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
