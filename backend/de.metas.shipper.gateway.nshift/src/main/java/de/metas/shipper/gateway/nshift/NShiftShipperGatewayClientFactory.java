package de.metas.shipper.gateway.nshift;

import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.shipper.gateway.commons.converters.v1.JsonShipperConverter;
import de.metas.shipper.gateway.commons.mapping.ShipperMappingConfigRepository;
import de.metas.shipper.gateway.commons.CarrierProductAllocationService;
import de.metas.shipper.gateway.commons.model.ShipmentOrderLogRepository;
import de.metas.shipper.gateway.commons.model.ShipperConfigRepository;
import de.metas.shipper.gateway.commons.servicelevel.ShipperServiceLevelConfigRepository;
import de.metas.shipper.gateway.nshift.client.NShiftShipperGatewayClient;
import de.metas.shipper.gateway.nshift.client.ShipAdvisorService;
import de.metas.shipper.gateway.nshift.client.ShipmentDispatchService;
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
	@NonNull private final ShipperConfigRepository configRepository;
	@NonNull private final JsonShipperConverter jsonConverter;
	@NonNull private final ShipmentOrderLogRepository shipmentOrderLogRepository;
	@NonNull private final ShipmentDispatchService shipmentDispatchService;
	@NonNull private final ShipAdvisorService shipAdvisorService;
	@NonNull private final ShipperMappingConfigRepository shipperMappingConfigRepository;
	@NonNull private final ShipperServiceLevelConfigRepository serviceLevelConfigRepository;
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull private final CarrierProductAllocationService carrierProductAllocationService;


	@Override
	public ShipperGatewayId getShipperGatewayId() {return NShiftConstants.SHIPPER_GATEWAY_ID;}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		return NShiftShipperGatewayClient.builder()
				.shipperConfig(configRepository.getByShipperId(shipperId))
				.mappingConfigs(shipperMappingConfigRepository.getByShipperId(shipperId))
				.serviceLevelConfigs(serviceLevelConfigRepository.getByShipperId(shipperId))
				.jsonConverter(jsonConverter)
				.shipmentOrderLogRepository(shipmentOrderLogRepository)
				.shipmentDispatchService(shipmentDispatchService)
				.shipAdvisorService(shipAdvisorService)
				.shipmentScheduleRepository(shipmentScheduleRepository)
				.carrierProductAllocationService(carrierProductAllocationService)
				.build();
	}
}
