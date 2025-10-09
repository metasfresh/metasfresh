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
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponseItem;
import de.metas.logging.LogManager;
import de.metas.shipper.gateway.commons.converters.v1.JsonShipperConverter;
import de.metas.shipper.gateway.commons.model.ShipmentOrderLogCreateRequest;
import de.metas.shipper.gateway.commons.model.ShipmentOrderLogRepository;
import de.metas.shipper.gateway.commons.model.ShipperConfig;
import de.metas.shipper.gateway.nshift.NShiftConstants;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.exceptions.ShipperGatewayException;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderParcel;
import de.metas.shipper.gateway.spi.model.OrderId;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabelType;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.nshift.NShiftShipmentService;
import de.metas.shipping.ShipperGatewayId;
import lombok.Builder;
import lombok.NonNull;
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

	@NonNull private final NShiftShipmentService shipmentService;
	//TODO Adrian to be removed in next iteration(s), once the API changes so that we pass a JsonDeliveryRequest and we get a JsonDeliveryResponse
	@NonNull private final JsonShipperConverter jsonConverter;
	@NonNull private final ShipmentOrderLogRepository shipmentOrderLogRepository;
	//TODO implement as provided by carrier
	private final static PackageLabelType DEFAULT_LABEL_TYPE = new PackageLabelType() {};
	@NonNull private final ShipperConfig shipperConfig;

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
		final JsonDeliveryRequest deliveryRequestJson = jsonConverter.toJson(shipperConfig, deliveryOrder);
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final JsonDeliveryResponse response = shipmentService.createShipment(deliveryRequestJson);
		logger.debug("Received nShift response: {}", response);

		shipmentOrderLogRepository.save(ShipmentOrderLogCreateRequest.builder()
				.request(deliveryRequestJson)
				.response(response)
				.durationMillis(stopwatch.elapsed(TimeUnit.MILLISECONDS))
				.build());

		return updateDeliveryOrder(deliveryOrder, response);
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
		final String language = deliveryOrder.getDeliveryContact().getLanguageCode();

		final ImmutableMap<String, JsonDeliveryResponseItem> lineIdToResponseMap = response.getItems().stream()
				.collect(ImmutableMap.toImmutableMap(JsonDeliveryResponseItem::getLineId, Function.identity()));
		final ImmutableList<DeliveryOrderParcel> updatedDeliveryOrderParcels = deliveryOrder.getDeliveryOrderParcels()
				.stream()
				.map(line -> updateDeliveryOrderLine(line, lineIdToResponseMap.get(String.valueOf(line.getId().getRepoId())), language))
				.collect(ImmutableList.toImmutableList());
		return deliveryOrder.withDeliveryOrderParcels(updatedDeliveryOrderParcels);
	}

	private DeliveryOrderParcel updateDeliveryOrderLine(@NonNull final DeliveryOrderParcel line, @NonNull final JsonDeliveryResponseItem jsonDeliveryResponseItem, @NonNull final String language)
	{
		final String awb = jsonDeliveryResponseItem.getAwb();
		final byte[] labelData = Base64.getDecoder().decode(jsonDeliveryResponseItem.getLabelPdfBase64());
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

}