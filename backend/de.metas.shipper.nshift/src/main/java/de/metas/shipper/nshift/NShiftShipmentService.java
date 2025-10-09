/*
 * #%L
 * de.metas.shipper.nshift
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

package de.metas.shipper.nshift;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponse;
import de.metas.common.delivery.v1.json.response.JsonDeliveryResponseItem;

import de.metas.shipper.nshift.json.JsonAddress;
import de.metas.shipper.nshift.json.JsonAddressKind;
import de.metas.shipper.nshift.json.JsonCustomsArticleDetail;
import de.metas.shipper.nshift.json.JsonCustomsArticleDetailKind;
import de.metas.shipper.nshift.json.JsonCustomsArticleInfo;
import de.metas.shipper.nshift.json.JsonDetailGroup;
import de.metas.shipper.nshift.json.JsonDetailRow;
import de.metas.shipper.nshift.json.JsonLabelType;
import de.metas.shipper.nshift.json.JsonLine;
import de.metas.shipper.nshift.json.JsonLineReference;
import de.metas.shipper.nshift.json.JsonLineReferenceKind;
import de.metas.shipper.nshift.json.JsonPackage;
import de.metas.shipper.nshift.json.JsonShipmentData;
import de.metas.shipper.nshift.json.JsonShipmentOptions;
import de.metas.shipper.nshift.json.JsonShipmentReference;
import de.metas.shipper.nshift.json.JsonShipmentReferenceKind;
import de.metas.shipper.nshift.json.request.JsonShipmentRequest;
import de.metas.shipper.nshift.json.response.JsonShipmentResponse;
import de.metas.shipper.nshift.json.response.JsonShipmentResponseLabel;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import java.util.stream.Collectors;

@Service
public class NShiftShipmentService extends AbstractNShiftApiClient
{
	private static final Logger logger = LogManager.getLogger(NShiftShipmentService.class);
	private static final String CREATE_SHIPMENT_ENDPOINT = "/ShipServer/{ID}/Shipments";

	public NShiftShipmentService(
			@NonNull final RestTemplate restTemplate,
			@NonNull final ObjectMapper objectMapper)
	{
		super(restTemplate, objectMapper);
	}

	public JsonDeliveryResponse createShipment(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		try
		{
			logger.debug("Creating shipment for request: {}", deliveryRequest);
			final JsonShipmentRequest requestBody = buildShipmentRequest(deliveryRequest);
			final JsonShipmentResponse response = post(CREATE_SHIPMENT_ENDPOINT, requestBody, deliveryRequest.getShipperConfig(), JsonShipmentResponse.class);

			logger.debug("Successfully received nShift response: {}", response);
			return buildJsonDeliveryResponse(response, deliveryRequest.getId());
		}
		catch (final Throwable throwable)
		{
			logger.error(throwable.toString());
			return JsonDeliveryResponse.builder()
					.requestId(deliveryRequest.getId())
					.errorMessage(throwable.getMessage())
					.build();
		}
	}

	private JsonShipmentRequest buildShipmentRequest(@NonNull final JsonDeliveryRequest deliveryRequest)
	{
		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				.submit(false)
				.labelType(JsonLabelType.PDF)
				//.requiredDeliveryDate()
				.validatePostCode(true)
				.build();

		final JsonShipperConfig config = deliveryRequest.getShipperConfig();
		final String actorId = config.getAdditionalPropertyNotNull(NShiftConstants.ACTOR_ID);
		final int prodConceptId = Integer.parseInt(config.getAdditionalPropertyNotNull(NShiftConstants.PROD_CONCEPT_ID));

		final JsonShipmentData.JsonShipmentDataBuilder dataBuilder = JsonShipmentData.builder()
				.actorCSID(Integer.valueOf(actorId))
				.orderNo(String.valueOf(deliveryRequest.getDeliveryOrderId()))
				.prodConceptID(prodConceptId)
				.pickupDt(LocalDate.parse(deliveryRequest.getPickupDate()));

		// Add Addresses
		dataBuilder.address(NShiftUtil.buildNShiftAddressBuilder(deliveryRequest.getPickupAddress(), JsonAddressKind.SENDER)
				.attention(deliveryRequest.getPickupAddress().getCompanyName1())
				.build());

		final JsonAddress.JsonAddressBuilder receiverAddressBuilder = NShiftUtil.buildNShiftReceiverAddress(
				deliveryRequest.getDeliveryAddress(),
				deliveryRequest.getDeliveryContact());
		dataBuilder.address(receiverAddressBuilder.build());

		// Add References
		if (deliveryRequest.getDeliveryDate() != null)
		{
			dataBuilder.reference(JsonShipmentReference.builder()
					.kind(JsonShipmentReferenceKind.WANTED_DELIVERY_TIME)
					.value(deliveryRequest.getDeliveryDate())
					.build());
		}
		if (Check.isNotBlank(deliveryRequest.getCustomerReference()))
		{
			dataBuilder.reference(JsonShipmentReference.builder()
					.kind(JsonShipmentReferenceKind.SENDER_REFERENCE)
					.value(deliveryRequest.getCustomerReference())
					.build());
		}

		int lineNoCounter = 1;
		for (final JsonDeliveryOrderParcel deliveryLine : deliveryRequest.getDeliveryOrderParcels())
		{
			dataBuilder.line(buildNShiftLine(deliveryLine, config));
			dataBuilder.detailGroup(buildCustomsArticleGroup(deliveryLine, lineNoCounter, deliveryRequest.getPickupAddress().getCountry()));
			lineNoCounter++;
		}

		return JsonShipmentRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private JsonLine buildNShiftLine(@NonNull final JsonDeliveryOrderParcel deliveryLine, @NonNull final JsonShipperConfig config)
	{
		// nShift expects weight in grams and dimensions in millimeters.
		final int weightGrams = deliveryLine.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final int lengthMM = deliveryLine.getPackageDimensions().getLengthInCM() * 10;
		final int widthMM = deliveryLine.getPackageDimensions().getWidthInCM() * 10;
		final int heightMM = deliveryLine.getPackageDimensions().getHeightInCM() * 10;

		return JsonLine.builder()
				.number(1)
				.pkgWeight(weightGrams)
				.lineWeight(weightGrams)
				.length(lengthMM)
				.width(widthMM)
				.height(heightMM)
				.goodsTypeID(Integer.parseInt(config.getAdditionalPropertyNotNull(NShiftConstants.GOODS_TYPE_ID)))
				.goodsTypeName(config.getAdditionalPropertyNotNull(NShiftConstants.GOODS_TYPE_NAME))
				.reference(JsonLineReference.builder()
						.kind(JsonLineReferenceKind.ESRK_CUSTOM_LINE_FIELD_1)
						.value(deliveryLine.getId())
						.build())
				.build();
	}

	private JsonDetailGroup buildCustomsArticleGroup(@NonNull final JsonDeliveryOrderParcel deliveryLine, final int lineNo, @NonNull final String countryOfOrigin)
	{
		final JsonCustomsArticleInfo.JsonCustomsArticleInfoBuilder<?, ?> customsArticleInfoBuilder = JsonCustomsArticleInfo.builder();

		for (final de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents content : deliveryLine.getContents())
		{
			final JsonDetailRow.JsonDetailRowBuilder<JsonCustomsArticleDetail> detailRowBuilder = JsonDetailRow.<JsonCustomsArticleDetail>builder()
					.lineNo(lineNo);

			detailRowBuilder
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.QUANTITY).value(content.getShippedQuantity().getValue().toPlainString()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.UNIT_OF_MEASURE).value(content.getShippedQuantity().getUomCode()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.DESCRIPTION_OF_GOODS).value(content.getProductName()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.ARTICLE_NO).value(content.getShipmentOrderItemId()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.UNIT_VALUE).value(content.getUnitPrice().getAmount().toPlainString()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.TOTAL_VALUE).value(content.getTotalValue().getAmount().toPlainString()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.CUSTOMS_ARTICLE_CURRENCY).value(content.getTotalValue().getCurrencyCode()).build())
					.detail(JsonCustomsArticleDetail.builder().kindId(JsonCustomsArticleDetailKind.COUNTRY_OF_ORIGIN).value(countryOfOrigin).build());

			customsArticleInfoBuilder.row(detailRowBuilder.build());
		}

		return customsArticleInfoBuilder.build();
	}

	private JsonDeliveryResponse buildJsonDeliveryResponse(@NonNull final JsonShipmentResponse response, @NonNull final String requestId)
	{
		final Map<String, JsonShipmentResponseLabel> labelsByPkgNo = response.getLabels() != null
				? response.getLabels().stream()
				.filter(label -> label.getPkgNo() != null)
				.collect(Collectors.toMap(
						JsonShipmentResponseLabel::getPkgNo,
						Function.identity(),
						(first, second) -> first)) // In case of duplicate pkgNo, take the first.
				: Collections.emptyMap();

		final List<JsonDeliveryResponseItem> items = response.getLines() != null
				? response.getLines().stream()
				.map(parcel -> {
					final JsonPackage pkg = parcel.getPkgs().get(0);
					final JsonShipmentResponseLabel label = labelsByPkgNo.get(pkg.getPkgNo());

					final byte[] labelPdf = (label != null && label.getContent() != null)
							? label.getContent().getBytes()
							: null;

					final String lineId = parcel.getReferences().stream()
							.filter(ref -> ref.getKind().isLineId())
							.map(JsonLineReference::getValue)
							.findFirst()
							.orElse(null);
					Check.assumeNotNull(lineId, "The lineId in the response shall not be null for parcel {}", parcel);

					return JsonDeliveryResponseItem.builder()
							.lineId(lineId)
							.awb(pkg.getPkgNo())
							.trackingUrl(pkg.getReferences().stream()
									.filter(ref -> ref.getKind().isTrackingUrl())
									.map(JsonLineReference::getValue)
									.findFirst()
									.orElse(null))
							.labelPdfBase64(labelPdf)
							.build();
				})
				.collect(Collectors.toList())
				: Collections.emptyList();

		// 3. Construct the final response object with the correlated items.
		return JsonDeliveryResponse.builder()
				.requestId(requestId)
				.items(items)
				.build();

	}
}