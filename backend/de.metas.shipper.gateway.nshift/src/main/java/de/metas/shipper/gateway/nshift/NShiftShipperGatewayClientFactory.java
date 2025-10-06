package de.metas.shipper.gateway.nshift;

import de.metas.shipper.gateway.commons.converters.v1.JsonShipperConverter;
import de.metas.shipper.gateway.commons.model.ShipmentOrderLogRepository;
import de.metas.shipper.gateway.commons.model.ShipperConfig;
import de.metas.shipper.gateway.commons.model.ShipperConfigRepository;
import de.metas.shipper.gateway.nshift.client.NShiftShipmentService;
import de.metas.shipper.gateway.nshift.client.NShiftShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class NShiftShipperGatewayClientFactory
{
	@NonNull private final ShipperConfigRepository configRepository;
	@NonNull private final JsonShipperConverter jsonConverter;
	@NonNull private final ShipmentOrderLogRepository shipmentOrderLogRepository;
	@NonNull private final NShiftShipmentService shipmentService;

	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		final ShipperConfig config = configRepository.getByShipperId(shipperId);

		return NShiftShipperGatewayClient.builder()
				.shipperConfig(config)
				.jsonConverter(jsonConverter)
				.shipmentOrderLogRepository(shipmentOrderLogRepository)
				.shipmentService(shipmentService)
				.build();
	}
}
