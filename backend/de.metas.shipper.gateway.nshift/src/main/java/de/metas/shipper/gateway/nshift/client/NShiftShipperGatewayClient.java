/*
 * #%L
 * de.metas.shipper.gateway.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.shipper.gateway.nshift.client;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponseItem;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.inoutcandidate.CarrierAdviseStatus;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.commons.CarrierProductAllocationService;
import de.metas.shipper.gateway.commons.converters.v1.JsonShipperConverter;
import de.metas.shipper.gateway.commons.mapping.ShipperMappingConfigList;
import de.metas.shipper.gateway.commons.model.ShipmentOrderLogCreateRequest;
import de.metas.shipper.gateway.commons.model.ShipmentOrderLogRepository;
import de.metas.shipper.gateway.commons.model.ShipperConfig;
import de.metas.shipper.gateway.commons.servicelevel.ShipperServiceLevelConfigList;
import de.metas.shipper.gateway.nshift.NShiftConstants;
import de.metas.shipper.gateway.spi.ShipperConfigRequest;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcel;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabelType;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipping.ShipperGatewayId;
import de.metas.shipping.ShipperRepository;
import de.metas.shipping.mpackage.PackageId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_Carrier_Config;
import org.slf4j.Logger;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder

public class NShiftShipperGatewayClient implements ShipperGatewayClient
{
	private static final Logger logger = LogManager.getLogger(NShiftShipperGatewayClient.class);

	@NonNull private final ShipmentDispatchService shipmentDispatchService;
	@NonNull private final ShipAdvisorService shipAdvisorService;
	//TODO Adrian to be removed in next iteration(s), once the API changes so that we pass a JsonDeliveryRequest and we get a JsonDeliveryResponse
	@NonNull private final JsonShipperConverter jsonConverter;
	@NonNull private final ShipmentOrderLogRepository shipmentOrderLogRepository;
	//TODO implement as provided by carrier
	private final static PackageLabelType DEFAULT_LABEL_TYPE = new PackageLabelType() {};
	@NonNull private final ShipperConfig shipperConfig;
	@NonNull private final ShipperMappingConfigList mappingConfigs;
	@NonNull private final ShipperServiceLevelConfigList serviceLevelConfigs;
	@NonNull private final ShipmentScheduleRepository shipmentScheduleRepository;
	@NonNull private final ShipperRepository shipperRepository;
	@NonNull private final CarrierProductAllocationService carrierProductAllocationService;

	@Override
	@NonNull
	public ShipperGatewayId getShipperGatewayId()
	{
		return NShiftConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	@NonNull
	public DeliveryOrder completeDeliveryOrder(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final List<ShipmentSchedule> schedules = loadSchedules(deliveryOrder);
		final JsonDeliveryRequest deliveryRequestJson = applyShippingRuleOptions(
				jsonConverter.toJson(shipperConfig, deliveryOrder, mappingConfigs),
				deliveryOrder,
				schedules);
		final Stopwatch stopwatch = Stopwatch.createStarted();
		JsonDeliveryResponse response;
		try
		{
			response = shipmentDispatchService.createShipment(deliveryRequestJson);
			logger.debug("Received nShift response: {}", response);
		}
		catch (final AdempiereException ex)
		{
			response = JsonDeliveryResponse.builder()
					.requestId(deliveryRequestJson.getId())
					.errorMessage(ex.getLocalizedMessage())
					.build();
		}

		shipmentOrderLogRepository.save(ShipmentOrderLogCreateRequest.builder()
				.request(deliveryRequestJson)
				.response(response)
				.durationMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
				.build());

		if (response.isError())
		{
			throw new ShipperGatewayException("nShift request failed pls check ShipmentOrderLog");
		}

		// When shipping rules were active, nShift re-resolved the carrier at ship time; persist what was actually
		// shipped into the carrier-product allocations (only if missing) so it becomes selectable in manual advise.
		if (areShippingRulesActive(deliveryOrder, schedules))
		{
			carrierProductAllocationService.persistResolvedAllocations(
					deliveryOrder.getShipperId(),
					response.getShipperProduct(),
					response.getResolvedGoodsTypes(),
					response.getResolvedServices());
		}

		return updateDeliveryOrder(deliveryOrder, response);
	}

	private List<ShipmentSchedule> loadSchedules(@NonNull final DeliveryOrder deliveryOrder)
	{
		return deliveryOrder.getDeliveryOrderParcels()
				.stream()
				.map(DeliveryOrderParcel::getPackageId)
				.distinct()
				.flatMap(packageId -> shipmentScheduleRepository.loadByPackageId(packageId).stream())
				.collect(Collectors.toList());
	}

	/**
	 * Patches the shipper config of the given request with UseShippingRules and ServiceLevel
	 * when the shipper is configured for API carrier advising and not all schedules are Manual.
	 */
	private JsonDeliveryRequest applyShippingRuleOptions(
			@NonNull final JsonDeliveryRequest request,
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final List<ShipmentSchedule> schedules)
	{
		if (!areShippingRulesActive(deliveryOrder, schedules))
		{
			return request;
		}

		final ExternalSystemId externalSystemId = schedules.stream()
				.map(ShipmentSchedule::getExternalSystemId)
				.filter(id -> id != null)
				.findFirst()
				.orElse(null);

		final String serviceLevel = serviceLevelConfigs.getEffectiveServiceLevel(
				ShipperConfigRequest.builder().externalSystemId(externalSystemId).build()).orElse(null);

		JsonShipperConfig patchedConfig = request.getShipperConfig()
				.withAdditionalProperty(NShiftConstants.USE_SHIPPING_RULES, Boolean.TRUE.toString());
		if (serviceLevel != null)
		{
			patchedConfig = patchedConfig.withAdditionalProperty(I_Carrier_Config.COLUMNNAME_ServiceLevel, serviceLevel);
		}

		return request.toBuilder().shipperConfig(patchedConfig).build();
	}

	/**
	 * Shipping rules are active (so nShift re-resolves the carrier at ship time) when the shipper is configured
	 * for API carrier advising and not all schedules are Manual.
	 */
	private boolean areShippingRulesActive(
			@NonNull final DeliveryOrder deliveryOrder,
			@NonNull final List<ShipmentSchedule> schedules)
	{
		if (!shipperRepository.isApiCarrierAdvise(deliveryOrder.getShipperId()))
		{
			return false;
		}
		if (schedules.isEmpty())
		{
			return false;
		}
		final boolean allManual = schedules.stream()
				.allMatch(s -> CarrierAdviseStatus.Manual.equals(s.getCarrierAdvisingStatus()));
		return !allManual;
	}

	/**
	 * This method has 2 concerns that will be separated in the future:
	 * <ol>
	 * <li>replace the tracking url in the JsonDeliveryResponse - should stay in nShift</li>
	 * <li>update the deliveryOrderParcels based on JsonDeliveryResponse - should be moved to the common module in the next iteration</li>
	 * </ol>
	 */
	private DeliveryOrder updateDeliveryOrder(final @NonNull DeliveryOrder deliveryOrder, @NonNull final JsonDeliveryResponse response)
	{
		final ImmutableMap<String, JsonDeliveryResponseItem> lineIdToResponseMap = response.getItems().stream()
				.collect(ImmutableMap.toImmutableMap(JsonDeliveryResponseItem::getLineId, Function.identity()));
		final ImmutableList<DeliveryOrderParcel> updatedDeliveryOrderParcels = deliveryOrder.getDeliveryOrderParcels()
				.stream()
				.map(line -> updateDeliveryOrderLine(line, lineIdToResponseMap.get(String.valueOf(line.getId().getRepoId()))))
				.collect(ImmutableList.toImmutableList());
		return deliveryOrder.withDeliveryOrderParcels(updatedDeliveryOrderParcels);
	}

	private DeliveryOrderParcel updateDeliveryOrderLine(@NonNull final DeliveryOrderParcel line, @NonNull final JsonDeliveryResponseItem jsonDeliveryResponseItem)
	{
		final String awb = jsonDeliveryResponseItem.getAwb();
		final byte[] labelPdfBase64 = jsonDeliveryResponseItem.getLabelPdfBase64();
		final byte[] labelData = labelPdfBase64 != null ? Base64.getDecoder().decode(labelPdfBase64) : null;
		final String trackingUrl = jsonDeliveryResponseItem.getTrackingUrl();

		return line.toBuilder()
				.awb(awb)
				.trackingUrl(trackingUrl)
				.labelPdfBase64(labelData)
				.build();
	}

	@NonNull
	@Override
	public List<PackageLabels> getPackageLabelsList(@NonNull final DeliveryOrder deliveryOrder) throws ShipperGatewayException
	{
		final String orderIdAsString = String.valueOf(deliveryOrder.getId());
		return deliveryOrder.getDeliveryOrderParcels()
				.stream()
				.map(line -> createPackageLabel(line.getLabelPdfBase64(), line.getAwb(), orderIdAsString))
				.collect(Collectors.toList());
	}

	@Override
	public @NonNull JsonDeliveryAdvisorResponse adviseShipment(final @NonNull JsonDeliveryAdvisorRequest request)
	{
		return shipAdvisorService.advise(request);
	}

	@NonNull
	private static PackageLabels createPackageLabel(final byte[] labelData, @NonNull final String awb, @NonNull final String deliveryOrderIdAsString)
	{
		return PackageLabels.builder()
				.orderId(OrderId.of(NShiftConstants.SHIPPER_GATEWAY_ID, deliveryOrderIdAsString))
				.defaultLabelType(NShiftPackageLabelType.DEFAULT)
				.label(PackageLabel.builder()
						.type(NShiftPackageLabelType.DEFAULT)
						.labelData(labelData)
						.contentType(PackageLabel.CONTENTTYPE_PDF)
						.fileName(awb)
						.build())
				.build();
	}

	@Override
	public JsonShipperConfig getJsonShipperConfigEffective(@NonNull final ShipperConfigRequest request)
	{
		final JsonShipperConfig baseConfig = jsonConverter.toJsonShipperConfig(shipperConfig);
		return serviceLevelConfigs.getEffectiveServiceLevel(request)
				.map(effectiveLevel -> baseConfig.withAdditionalProperty(I_Carrier_Config.COLUMNNAME_ServiceLevel, effectiveLevel))
				.orElse(baseConfig);
	}

}