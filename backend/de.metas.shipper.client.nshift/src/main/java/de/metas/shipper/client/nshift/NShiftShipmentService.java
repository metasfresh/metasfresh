/*
 * #%L
 * de.metas.shipper.client.nshift
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

package de.metas.shipper.client.nshift;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponseItem;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.shipper.client.nshift.json.JsonDetail;
import de.metas.shipper.client.nshift.json.JsonDetailGroup;
import de.metas.shipper.client.nshift.json.JsonDetailRow;
import de.metas.shipper.client.nshift.json.JsonLabelType;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonReference;
import de.metas.shipper.client.nshift.json.JsonPackage;
import de.metas.shipper.client.nshift.json.JsonShipmentData;
import de.metas.shipper.client.nshift.json.JsonShipmentOptions;
import de.metas.shipper.client.nshift.json.request.JsonShipmentRequest;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponse;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponseLabel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NShiftShipmentService
{
	private static final Logger logger = LogManager.getLogger(NShiftShipmentService.class);
	private static final String CREATE_SHIPMENT_ENDPOINT = "/ShipServer/{ID}/Shipments";
	private static final int LINE_REFERENCE_KIND_TRACKING_URL = 147;

	@NonNull private final NShiftRestClient restClient;

	public JsonDeliveryResponse createShipment(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		try
		{
			logger.debug("Creating shipment for request: {}", deliveryRequest);
			final JsonShipmentRequest requestBody = buildShipmentRequest(deliveryRequest);
			final JsonShipmentResponse response = restClient.post(CREATE_SHIPMENT_ENDPOINT, requestBody, deliveryRequest.getShipperConfig(), JsonShipmentResponse.class);

			logger.debug("Successfully received nShift response: {}", response);
			return buildJsonDeliveryResponse(response, deliveryRequest);
		}
		catch (final Throwable throwable)
		{
			logger.error("Got error", throwable);
			return JsonDeliveryResponse.builder()
					.requestId(deliveryRequest.getId())
					.errorMessage(throwable.getMessage())
					.build();
		}
	}

	@VisibleForTesting
	public static JsonShipmentRequest buildShipmentRequest(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				.labelType(JsonLabelType.PDF)
				//.requiredDeliveryDate()
				.validatePostCode(true)
				.build();

		final JsonShipperConfig config = deliveryRequest.getShipperConfig();
		final String actorId = config.getAdditionalPropertyNotNull(NShiftConstants.ACTOR_ID);

		final int prodConceptId = Integer.parseInt(deliveryRequest.getShipAdviceNotNull(NShiftConstants.PROD_CONCEPT_ID));

		final JsonShipmentData.JsonShipmentDataBuilder dataBuilder = JsonShipmentData.builder()
				.actorCSID(Integer.valueOf(actorId))
				.orderNo(String.valueOf(deliveryRequest.getDeliveryOrderId()))
				.prodConceptID(prodConceptId)
				.pickupDt(LocalDate.parse(deliveryRequest.getPickupDate()));

		deliveryRequest.getShipperProductServices().forEach(service -> dataBuilder.service(Integer.parseInt(service)));

		final NShiftMappingConfigs mappingConfigs = NShiftMappingConfigs.ofJson(deliveryRequest.getMappingConfigs());

		// Add Addresses
		dataBuilder.address(NShiftUtil.buildNShiftAddressBuilder(deliveryRequest.getPickupAddress(), JsonAddressKind.SENDER)
				.attention(mappingConfigs.getSingleValue(DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION, deliveryRequest::getValue))
				.build());

		final JsonAddress.JsonAddressBuilder receiverAddressBuilder = NShiftUtil.buildNShiftReceiverAddress(
				deliveryRequest.getDeliveryAddress(),
				deliveryRequest.getDeliveryContact());
		receiverAddressBuilder.attention(mappingConfigs.getSingleValue(DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION, deliveryRequest::getValue));
		dataBuilder.address(receiverAddressBuilder.build());

		dataBuilder.references(mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_REFERENCE, deliveryRequest::getValue));

		// 1. Add shipment-level detail groups (processed once)
		final List<JsonDetailGroup> allDetailGroups = new ArrayList<>(buildShipmentDetailGroups(mappingConfigs, deliveryRequest::getValue));

		// 2. Add line-level detail groups (processed for each parcel)
		int lineNoCounter = 1;
		for (final JsonDeliveryOrderParcel deliveryLine : deliveryRequest.getDeliveryOrderParcels())
		{
			dataBuilder.line(buildNShiftLine(deliveryLine, deliveryRequest, mappingConfigs));
			allDetailGroups.addAll(buildLineLevelDetailGroups(deliveryLine, lineNoCounter, mappingConfigs, deliveryRequest));
			lineNoCounter++;
		}

		dataBuilder.detailGroups(allDetailGroups);

		return JsonShipmentRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private static List<JsonDetailGroup> buildShipmentDetailGroups(
			@NonNull final NShiftMappingConfigs mappingConfigs,
			@NonNull final Function<String, String> valueProvider)
	{
		final List<String> shipmentLevelGroupKeys = mappingConfigs.getDetailGroupKeysForType(DeliveryMappingConstants.ATTRIBUTE_TYPE_DETAIL_GROUP, valueProvider);

		if (shipmentLevelGroupKeys.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<JsonDetailGroup> resultGroups = new ArrayList<>();
		for (final String groupKey : shipmentLevelGroupKeys)
		{
			final List<JsonDetail> details = mappingConfigs.getDetailsForGroupAndType(groupKey, DeliveryMappingConstants.ATTRIBUTE_TYPE_DETAIL_GROUP, valueProvider);
			if (details.isEmpty())
			{
				continue;
			}

			// For shipment-level groups, we create one row without a line number.
			final JsonDetailRow detailRow = JsonDetailRow.builder()
					.details(details)
					.build();

			resultGroups.add(JsonDetailGroup.builder()
					.groupID(groupKey)
					.row(detailRow)
					.build());
		}
		return resultGroups;
	}

	private static JsonLine buildNShiftLine(@NonNull final JsonDeliveryOrderParcel deliveryLine,
											@NonNull final JsonDeliveryRequest deliveryRequest,
											@NonNull final NShiftMappingConfigs mappingConfigs
	)
	{
		// nShift expects weight in grams and dimensions in millimeters.
		final int weightGrams = deliveryLine.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final int lengthMM = deliveryLine.getPackageDimensions().getLengthInCM() * 10;
		final int widthMM = deliveryLine.getPackageDimensions().getWidthInCM() * 10;
		final int heightMM = deliveryLine.getPackageDimensions().getHeightInCM() * 10;

		final Function<String, Optional<String>> valueProvider =
				withFallback(deliveryLine::getValue, attributeValue -> Optional.ofNullable(deliveryRequest.getValue(attributeValue)));
		final Function<String, String> finalValueProvider = attributeValue -> valueProvider.apply(attributeValue).orElse(null);

		return JsonLine.builder()
				.number(1)
				.pkgWeight(weightGrams)
				.lineWeight(weightGrams)
				.length(lengthMM)
				.width(widthMM)
				.height(heightMM)
				.goodsTypeID(Integer.parseInt(deliveryRequest.getShipAdviceNotNull(NShiftConstants.GOODS_TYPE_ID)))
				.goodsTypeName(deliveryRequest.getShipAdviceNotNull(NShiftConstants.GOODS_TYPE_NAME))
				.references(mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE, finalValueProvider))
				.build();
	}

	private static List<JsonDetailGroup> buildLineLevelDetailGroups(
			@NonNull final JsonDeliveryOrderParcel deliveryLine,
			final int lineNo,
			@NonNull final NShiftMappingConfigs mappingConfigs,
			@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final Map<String, JsonDetailGroup.JsonDetailGroupBuilder> groupBuilders = new LinkedHashMap<>();

		// This provider is for evaluating mapping rules, which might depend on parcel or request data.
		final Function<String, Optional<String>> parcelAndRequestProvider =
				withFallback(deliveryLine::getValue, attributeValue -> Optional.ofNullable(deliveryRequest.getValue(attributeValue)));

		for (final de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents content : deliveryLine.getContents())
		{
			// This full valueProviderChain is for resolving the detail values, which can come from content, parcel, or request.
			final Function<String, Optional<String>> valueProviderChain =
					withFallback(content::getValue, parcelAndRequestProvider);

			final Function<String, String> finalValueProvider = attributeValue -> valueProviderChain.apply(attributeValue).orElse(null);

			final List<String> groupKeys = mappingConfigs.getDetailGroupKeysForType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP, finalValueProvider);
			if (groupKeys.isEmpty())
			{
				continue;
			}

			for (final String groupKey : groupKeys)
			{
				final List<JsonDetail> details = mappingConfigs.getDetailsForGroupAndType(groupKey, DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP, finalValueProvider);

				if (!details.isEmpty())
				{
					groupBuilders.computeIfAbsent(groupKey, k -> JsonDetailGroup.builder().groupID(k))
							.row(JsonDetailRow.builder().lineNo(lineNo).details(details).build());
				}
			}
		}

		return groupBuilders.values().stream()
				.map(JsonDetailGroup.JsonDetailGroupBuilder::build)
				.collect(Collectors.toList());
	}

	private static <T, R> Function<T, Optional<R>> withFallback(
			@NonNull final Function<T, Optional<R>> primary,
			@NonNull final Function<T, Optional<R>> fallback)
	{
		return t -> {
			final Optional<R> value = primary.apply(t);
			return value.isPresent() ? value : fallback.apply(t);
		};
	}

	private static JsonDeliveryResponse buildJsonDeliveryResponse(@NonNull final JsonShipmentResponse response, @NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final Map<String, JsonShipmentResponseLabel> labelsByPkgNo = response.getLabels() != null
				? response.getLabels().stream()
				.filter(label -> label.getPkgNo() != null)
				.collect(Collectors.toMap(
						JsonShipmentResponseLabel::getPkgNo,
						Function.identity(),
						(first, second) -> first)) // In case of duplicate pkgNo, take the first.
				: Collections.emptyMap();

		final List<JsonDeliveryOrderParcel> requestParcels = deliveryRequest.getDeliveryOrderParcels();
		final List<JsonLine> responseLines = response.getLines() != null ? response.getLines() : Collections.emptyList();
		Check.assume(requestParcels.size() == responseLines.size(), "Request and response line counts do not match. Request: %s, Response: %s", requestParcels.size(), responseLines.size());

		final List<JsonDeliveryResponseItem> items = Streams.zip(
						requestParcels.stream(),
						responseLines.stream(),
						(requestParcel, responseLine) -> {
							final JsonPackage pkg = responseLine.getPkgs().get(0);
							final JsonShipmentResponseLabel label = labelsByPkgNo.get(pkg.getPkgNo());

							final byte[] labelPdf = (label != null && label.getContent() != null)
									? label.getContent().getBytes()
									: null;

							return JsonDeliveryResponseItem.builder()
									.lineId(requestParcel.getId())
									.awb(pkg.getPkgNo())
									.trackingUrl(responseLine.getReferences().stream()
											.filter(ref -> ref.getKind() == LINE_REFERENCE_KIND_TRACKING_URL)
											.map(JsonReference::getValue)
											.findFirst()
											.orElse(null))
									.labelPdfBase64(labelPdf)
									.build();
						})
				.collect(Collectors.toList());

		return JsonDeliveryResponse.builder()
				.requestId(deliveryRequest.getId())
				.items(items)
				.build();
	}
}