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
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.common.util.Check;
import de.metas.common.util.StringUtils;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonShipmentData;
import de.metas.shipper.client.nshift.json.JsonShipmentOptions;
import de.metas.shipper.client.nshift.json.request.JsonShipAdvisorRequest;
import de.metas.shipper.client.nshift.json.response.JsonOrderAdviceResponse;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class NShiftOrderAdvisorService
{
	private static final Logger logger = LogManager.getLogger(NShiftOrderAdvisorService.class);

	@NonNull private final NShiftRestClient restClient;

	public JsonDeliveryAdvisorResponse advise(@NonNull final JsonDeliveryAdvisorRequest deliveryAdvisorRequest)
	{
		try
		{
			logger.debug("Getting Order Advises for request: {}", deliveryAdvisorRequest);
			final JsonShipAdvisorRequest requestBody = buildRequest(deliveryAdvisorRequest);
			final JsonOrderAdviceResponse response = restClient.post(NShiftConstants.ORDER_ADVICE_ENDPOINT, requestBody, deliveryAdvisorRequest.getShipperConfig(), JsonOrderAdviceResponse.class);

			logger.debug("Successfully received nShift response: {}", response);
			return buildJsonDeliveryAdvisorResponse(response, deliveryAdvisorRequest.getId());
		}
		catch (final Throwable throwable)
		{
			logger.error("Got error", throwable);
			return JsonDeliveryAdvisorResponse.builder()
					.requestId(deliveryAdvisorRequest.getId())
					.errorMessage(throwable.toString())
					.build();
		}
	}

	@VisibleForTesting
	public static JsonShipAdvisorRequest buildRequest(@NonNull final JsonDeliveryAdvisorRequest deliveryAdvisorRequest)
	{
		final NShiftMappingConfigs mappingConfigs = NShiftMappingConfigs.ofJson(deliveryAdvisorRequest.getMappingConfigs());
		final Function<String, String> valueProvider = deliveryAdvisorRequest::getValue;

		final boolean useSelectionRules = StringUtils.toBoolean(deliveryAdvisorRequest.getShipperConfig().getAdditionalProperty(NShiftConstants.SELECTION_RULES), false);
		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				// with selection rules active nShift resolves the product from the rules, so ServiceLevel must not be sent (omitted via NON_NULL)
				.serviceLevel(useSelectionRules ? null : deliveryAdvisorRequest.getShipperConfig().getAdditionalPropertyNotNull(NShiftConstants.SERVICE_LEVEL))
				.useShippingRules(useSelectionRules)
				.submit(false) // advise only: do not book the shipment (serialized as 0)
				.visibility("extended")
				.build();

		final JsonShipmentData.JsonShipmentDataBuilder dataBuilder = JsonShipmentData.builder()
				.orderNo(deliveryAdvisorRequest.getId().replace("-", "")); //  Order Number is limited to 35 characters. fld_RefOrderNumber

		dataBuilder.address(NShiftUtil.buildAddressWithAttentionFromMappings(
				deliveryAdvisorRequest.getPickupAddress(),
				deliveryAdvisorRequest.getPickupContact(),
				JsonAddressKind.SENDER,
				mappingConfigs,
				valueProvider));

		dataBuilder.address(NShiftUtil.buildAddressWithAttentionFromMappings(
				deliveryAdvisorRequest.getDeliveryAddress(),
				deliveryAdvisorRequest.getDeliveryContact(),
				JsonAddressKind.RECEIVER,
				mappingConfigs,
				valueProvider));

		dataBuilder.references(mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_REFERENCE, valueProvider));

		final JsonDeliveryAdvisorRequestItem item = deliveryAdvisorRequest.getItem();
		final Function<String, Optional<String>> lineValueProvider = NShiftUtil.withFallback(
				item::getValue,
				attributeValue -> Optional.ofNullable(deliveryAdvisorRequest.getValue(attributeValue)));
		final Function<String, String> finalLineValueProvider = attributeValue -> lineValueProvider.apply(attributeValue).orElse(null);

		dataBuilder.line(NShiftUtil.buildAdvisorLine(item, mappingConfigs, finalLineValueProvider));

		return JsonShipAdvisorRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private static JsonDeliveryAdvisorResponse buildJsonDeliveryAdvisorResponse(@NonNull final JsonOrderAdviceResponse response, @NonNull final String requestId)
	{
		// OrderAdvice wraps the advised shipment under "Shipment"; the carrier product is identified by ProdCSID.
		final JsonShipmentResponse shipment = Check.assumeNotNull(response.getShipment(), "OrderAdvice response should contain a Shipment, pls check defined shipment rules. Status={}", response.getStatus());
		final Integer prodCSID = Check.assumeNotNull(shipment.getProdCSID(), "OrderAdvice Shipment should contain a ProdCSID, pls check defined shipment rules");

		final JsonDeliveryAdvisorResponse.JsonDeliveryAdvisorResponseBuilder responseBuilder = JsonDeliveryAdvisorResponse.builder()
				.requestId(requestId)
				.shipperProduct(JsonShipperProduct.builder()
						.code(String.valueOf(prodCSID))
						.name(shipment.getCarrierFullName())
						.build());

		// GoodsType is reported per line (no shipment-level GoodsType / Services in the OrderAdvice response);
		// the advise carries a single line, so take the GoodsType from the first.
		final List<JsonLine> lines = shipment.getLines();
		if (lines != null && !lines.isEmpty() && lines.get(0).getGoodsTypeID() != null)
		{
			final JsonLine line = lines.get(0);
			responseBuilder.goodsType(JsonGoodsType.builder()
					.id(String.valueOf(line.getGoodsTypeID()))
					.name(line.getGoodsTypeName())
					.build());
		}

		return responseBuilder.build();
	}
}
