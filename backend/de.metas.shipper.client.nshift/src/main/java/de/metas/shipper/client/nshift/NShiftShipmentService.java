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
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponseItem;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.StringUtils;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.shipper.client.nshift.json.JsonDetailGroup;
import de.metas.shipper.client.nshift.json.JsonLabelType;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonPackage;
import de.metas.shipper.client.nshift.json.JsonShipmentData;
import de.metas.shipper.client.nshift.json.JsonShipmentOptions;
import de.metas.shipper.client.nshift.json.request.JsonShipmentRequest;
import de.metas.shipper.client.nshift.json.response.JsonOrderAdviceResponse;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponse;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponseLabel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NShiftShipmentService
{
	private static final Logger logger = LogManager.getLogger(NShiftShipmentService.class);
	private static final String CREATE_SHIPMENT_ENDPOINT = "/ShipServer/{ID}/Shipments";
	private static final String DRAFT_SHIPMENT_ENDPOINT = "/ShipServer/{ID}/SaveShipment";

	@NonNull private final NShiftRestClient restClient;

	public JsonDeliveryResponse createShipment(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		try
		{
			logger.debug("Creating shipment for request: {}", deliveryRequest);
			final boolean isDraftShipmentOnly = StringUtils.toBoolean(deliveryRequest.getShipperConfig().getAdditionalPropertyNotNull(NShiftConstants.IS_CREATE_DRAFT_SHIPMENT_ONLY));
			final String endpoint = isDraftShipmentOnly ? DRAFT_SHIPMENT_ENDPOINT : CREATE_SHIPMENT_ENDPOINT;
			final JsonShipmentRequest requestBody = buildShipmentRequest(deliveryRequest);
			final JsonShipmentResponse response = restClient.post(endpoint, requestBody, deliveryRequest.getShipperConfig(), JsonShipmentResponse.class);

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

	/**
	 * Books a shipment via the OrderAdvice endpoint (Submit=1).
	 * Keep response handling in sync with {@link #createShipment}.
	 */
	public JsonDeliveryResponse createShipmentViaOrderAdvice(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		try
		{
			logger.debug("Creating shipment via OrderAdvice for request: {}", deliveryRequest);
			final JsonShipmentRequest requestBody = buildOrderAdviceShipmentRequest(deliveryRequest);
			final JsonOrderAdviceResponse orderAdviceResponse = restClient.post(NShiftConstants.ORDER_ADVICE_ENDPOINT, requestBody, deliveryRequest.getShipperConfig(), JsonOrderAdviceResponse.class);

			logger.debug("Successfully received nShift OrderAdvice response: {}", orderAdviceResponse);
			final JsonShipmentResponse shipment = orderAdviceResponse.getShipment();
			if (shipment == null)
			{
				// No booked (forward) Shipment. Surface nShift's own reason (ErrorMessages/Status) plus the request
				// JSON so the offending request is visible.
				throw new RuntimeException("OrderAdvice(Submit=1) response contains no booked Shipment; " + orderAdviceResponse.failureReason() + "\n"
						+ "nShiftRequest: " + restClient.requestBodyAsJsonForError(requestBody));
			}
			return buildJsonDeliveryResponse(shipment, deliveryRequest);
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
	public static JsonShipmentRequest buildOrderAdviceShipmentRequest(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final JsonShipmentRequest baseRequest = buildShipmentRequest(deliveryRequest);
		// Rebuild options with submit=true to book the shipment; other options are preserved from the base request.
		final JsonShipmentOptions baseOptions = baseRequest.getOptions();
		final JsonShipmentOptions bookingOptions = JsonShipmentOptions.builder()
				.labelType(baseOptions.getLabelType())
				.trackingURL(baseOptions.getTrackingURL())
				//.useShippingRules(baseOptions.getUseShippingRules()) always active on this Endpoint
				.serviceLevel(baseOptions.getServiceLevel())
				.submit(true)
				// OrderAdvice returns product/carrier (+ goods type) detail only with Visibility=extended — same as the advise (Submit=0) path
				.visibility(NShiftConstants.VISIBILITY_EXTENDED)
				.build();
		return JsonShipmentRequest.builder()
				.data(baseRequest.getData())
				.options(bookingOptions)
				.build();
	}

	@VisibleForTesting
	public static JsonShipmentRequest buildShipmentRequest(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final JsonShipperConfig config = deliveryRequest.getShipperConfig();
		final boolean isSelectionRules = StringUtils.toBoolean(config.getAdditionalProperty(NShiftConstants.SELECTION_RULES), false);
		// with shipping/selection rules active nShift resolves the product from the rules, so ServiceLevel must not be sent (omitted via NON_NULL)
		final String serviceLevel = isSelectionRules ? null : config.getAdditionalProperty(NShiftConstants.SERVICE_LEVEL);

		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				.labelType(JsonLabelType.PDF)
				.trackingURL(true)
				.useShippingRules(isSelectionRules)
				.serviceLevel(serviceLevel)
				.build();

		final String actorId = config.getAdditionalPropertyNotNull(NShiftConstants.ACTOR_ID);

		final JsonShipmentData.JsonShipmentDataBuilder dataBuilder = JsonShipmentData.builder()
				.actorCSID(Integer.valueOf(actorId))
				// nShift requires OrderNo (its refNo) to be 8..35 chars long
				.orderNo(String.format("%08d", deliveryRequest.getDeliveryOrderId()))
				.pickupDt(LocalDate.parse(deliveryRequest.getPickupDate()));

		// With shipping rules active (non-manual) nShift re-resolves product / goods type / services from the rules,
		// so they must NOT be pre-sent on the request; only send them when rules are off (manual / fixed product).
		if (!isSelectionRules)
		{
			dataBuilder.prodConceptID(Integer.parseInt(deliveryRequest.getShipperProduct().getCode()));
			deliveryRequest.getServices().forEach(service -> dataBuilder.service(Long.valueOf(service.getId()).intValue()));
		}

		final NShiftMappingConfigs mappingConfigs = NShiftMappingConfigs.ofJson(deliveryRequest.getMappingConfigs());

		// Add Addresses
		dataBuilder.address(NShiftUtil.buildAddressWithAttentionFromMappings(
				deliveryRequest.getPickupAddress(), deliveryRequest.getPickupContact(), JsonAddressKind.SENDER, mappingConfigs, deliveryRequest::getValue));

		dataBuilder.address(NShiftUtil.buildAddressWithAttentionFromMappings(
				deliveryRequest.getDeliveryAddress(), deliveryRequest.getDeliveryContact(), JsonAddressKind.RECEIVER, mappingConfigs, deliveryRequest::getValue));

		dataBuilder.references(mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_REFERENCE, deliveryRequest::getValue));

		// 1. Add shipment-level detail groups (processed once)
		final List<JsonDetailGroup> allDetailGroups = new ArrayList<>(NShiftUtil.buildShipmentDetailGroups(mappingConfigs, deliveryRequest::getValue));

		// 2. Add line-level detail groups (processed for each parcel)
		int lineNoCounter = 1;
		for (final JsonDeliveryOrderParcel deliveryLine : deliveryRequest.getDeliveryOrderParcels())
		{
			dataBuilder.line(buildNShiftLine(deliveryLine, deliveryRequest, mappingConfigs, isSelectionRules));
			allDetailGroups.addAll(NShiftUtil.buildLineLevelDetailGroups(buildContentValueProviders(deliveryLine, deliveryRequest), lineNoCounter, mappingConfigs));
			lineNoCounter++;
		}

		dataBuilder.detailGroups(allDetailGroups);

		return JsonShipmentRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private static JsonLine buildNShiftLine(@NonNull final JsonDeliveryOrderParcel deliveryLine,
											@NonNull final JsonDeliveryRequest deliveryRequest,
											@NonNull final NShiftMappingConfigs mappingConfigs,
											final boolean useShippingRules
	)
	{
		// nShift expects weight in grams and dimensions in millimeters.
		final int weightGrams = deliveryLine.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final JsonPackageDimensions dims = deliveryLine.getPackageDimensions();
		if (dims.getLengthInCM() <= 0 && dims.getWidthInCM() <= 0 && dims.getHeightInCM() <= 0)
		{
			throw new IllegalStateException("Package dimensions are mandatory but were not specified (all dimensions are zero or unspecified).");
		}
		final int lengthMM = dims.getLengthInCM() * 10;
		final int widthMM = dims.getWidthInCM() * 10;
		final int heightMM = dims.getHeightInCM() * 10;

		final Function<String, Optional<String>> valueProvider =
				NShiftUtil.withFallback(deliveryLine::getValue, attributeValue -> Optional.ofNullable(deliveryRequest.getValue(attributeValue)));
		final Function<String, String> finalValueProvider = attributeValue -> valueProvider.apply(attributeValue).orElse(null);

		final JsonLine.JsonLineBuilder lineBuilder = JsonLine.builder()
				.number(1)
				.pkgWeight(weightGrams)
				.lineWeight(weightGrams)
				.length(lengthMM)
				.width(widthMM)
				.height(heightMM)
				.references(mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE, finalValueProvider));

		// Goods type is part of the product resolution — with rules active nShift resolves it, so don't pre-send it.
		if (!useShippingRules)
		{
			final JsonGoodsType goodsType = Check.assumeNotNull(deliveryRequest.getGoodsType(), "No Goods Type found for %s", deliveryRequest);
			lineBuilder.goodsTypeID(Long.valueOf(goodsType.getId()).intValue())
					.goodsTypeName(goodsType.getName());
		}

		return lineBuilder.build();
	}

	/**
	 * Builds the per-content value-provider chains for one parcel, to feed
	 * {@link NShiftUtil#buildLineLevelDetailGroups(List, int, NShiftMappingConfigs)}. Each chain resolves a detail
	 * value from content first, then parcel, then request — identical to the previous inline ship-path logic.
	 */
	private static List<Function<String, String>> buildContentValueProviders(
			@NonNull final JsonDeliveryOrderParcel deliveryLine,
			@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		// This provider is for evaluating mapping rules, which might depend on parcel or request data.
		final Function<String, Optional<String>> parcelAndRequestProvider =
				NShiftUtil.withFallback(deliveryLine::getValue, attributeValue -> Optional.ofNullable(deliveryRequest.getValue(attributeValue)));

		final List<Function<String, String>> perContentProviders = new ArrayList<>();
		for (final de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents content : deliveryLine.getContents())
		{
			// This full valueProviderChain is for resolving the detail values, which can come from content, parcel, or request.
			final Function<String, Optional<String>> valueProviderChain =
					NShiftUtil.withFallback(content::getValue, parcelAndRequestProvider);

			perContentProviders.add(attributeValue -> valueProviderChain.apply(attributeValue).orElse(null));
		}
		return perContentProviders;
	}

	@VisibleForTesting
	static JsonDeliveryResponse buildJsonDeliveryResponse(@NonNull final JsonShipmentResponse response, @NonNull final JsonDeliveryRequest deliveryRequest)
	{
		// The label's PkgNo (the AWB) is what we want, but a package's PkgNo can be blank; match by the stable
		// PkgCSID (fallback PkgTag) so the label is found regardless.
		final Map<String, JsonShipmentResponseLabel> labelsByPkgKey = response.getLabels() != null
				? response.getLabels().stream()
				.filter(label -> labelPackageKey(label) != null)
				.collect(Collectors.toMap(
						NShiftShipmentService::labelPackageKey,
						Function.identity(),
						(first, second) -> first)) // In case of a duplicate key, take the first.
				: Collections.emptyMap();

		final List<JsonDeliveryOrderParcel> requestParcels = deliveryRequest.getDeliveryOrderParcels();
		final List<JsonLine> responseLines = response.getLines() != null ? response.getLines() : Collections.emptyList();
		Check.assume(requestParcels.size() == responseLines.size(), "Request and response line counts do not match. Request: %s, Response: %s", requestParcels.size(), responseLines.size());

		final List<JsonDeliveryResponseItem> items = Streams.zip(
						requestParcels.stream(),
						responseLines.stream(),
						(requestParcel, responseLine) -> {
							Check.assumeNotEmpty(responseLine.getPkgs(), "No packages found for line: {}", responseLine);
							final JsonPackage pkg = responseLine.getPkgs().get(0);
							final String pkgKey = packageKey(pkg);
							final JsonShipmentResponseLabel label = pkgKey != null ? labelsByPkgKey.get(pkgKey) : null;

							// Prefer the label's AWB/TrackingURL (blank treated as absent); fall back to the per-line package.
							final String awb = CoalesceUtil.firstNotBlank(
									label != null ? label.getPkgNo() : null,
									pkg.getPkgNo());
							final String trackingUrl = CoalesceUtil.firstNotBlank(
									label != null ? label.getTrackingURL() : null,
									JsonPackage.extractTrackingUrl(pkg));

							return JsonDeliveryResponseItem.builder()
									.lineId(requestParcel.getId())
									.awb(awb)
									.trackingUrl(trackingUrl)
									.labelPdfBase64(JsonShipmentResponseLabel.extractLabel(label))
									.build();
						})
				.collect(Collectors.toList());
		Check.assume(items.size() == requestParcels.size(), "Request and response parcel counts do not match. Request: %s, Response: %s", requestParcels.size(), items.size());

		final JsonDeliveryResponse.JsonDeliveryResponseBuilder responseBuilder = JsonDeliveryResponse.builder()
				.requestId(deliveryRequest.getId())
				.items(items)
				.shipperProduct(extractResolvedShipperProduct(response));

		extractResolvedGoodsTypes(responseLines).forEach(responseBuilder::resolvedGoodsType);
		NShiftUtil.extractResolvedServices(response).forEach(responseBuilder::resolvedService);

		return responseBuilder.build();
	}

	@Nullable
	private static String labelPackageKey(@NonNull final JsonShipmentResponseLabel label)
	{
		if (label.getPkgCSID() != null)
		{
			return "csid:" + label.getPkgCSID();
		}
		final String pkgTag = StringUtils.trimBlankToNull(label.getPkgTag());
		return pkgTag != null ? "tag:" + pkgTag : null;
	}

	@Nullable
	private static String packageKey(@NonNull final JsonPackage pkg)
	{
		if (pkg.getPkgCSID() != null)
		{
			return "csid:" + pkg.getPkgCSID();
		}
		final String pkgTag = StringUtils.trimBlankToNull(pkg.getPkgTag());
		return pkgTag != null ? "tag:" + pkgTag : null;
	}

	@Nullable
	private static JsonShipperProduct extractResolvedShipperProduct(@NonNull final JsonShipmentResponse response)
	{
		if (response.getProdConceptID() == null)
		{
			return null;
		}
		final String code = String.valueOf(response.getProdConceptID());
		return JsonShipperProduct.builder()
				.code(code)
				.name(NShiftUtil.buildCarrierProductName(response.getCarrierFullName(), response.getProdName(), code))
				.build();
	}

	private static Set<JsonGoodsType> extractResolvedGoodsTypes(@NonNull final List<JsonLine> responseLines)
	{
		return responseLines.stream()
				.filter(line -> line.getGoodsTypeID() != null)
				.map(line -> {
					final String id = String.valueOf(line.getGoodsTypeID());
					return JsonGoodsType.builder()
							.id(id)
							.name(line.getGoodsTypeName() != null ? line.getGoodsTypeName() : id)
							.build();
				})
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
}
