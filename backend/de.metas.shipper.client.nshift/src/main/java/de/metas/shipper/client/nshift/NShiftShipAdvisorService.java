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

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.JsonShipmentData;
import de.metas.shipper.client.nshift.json.JsonShipmentOptions;
import de.metas.shipper.client.nshift.json.request.JsonShipAdvisorRequest;
import de.metas.shipper.client.nshift.json.response.JsonShipAdvisorResponse;
import de.metas.shipper.client.nshift.json.response.JsonShipAdvisorResponseGoodsType;
import de.metas.shipper.client.nshift.json.response.JsonShipAdvisorResponseProduct;
import de.metas.shipper.client.nshift.json.response.JsonShipAdvisorResponseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class NShiftShipAdvisorService
{
	private static final Logger logger = LogManager.getLogger(NShiftShipAdvisorService.class);
	private static final String SHIP_ADVISES_ENDPOINT = "/ShipServer/{ID}/shipAdvises";

	@NonNull private final NShiftRestClient restClient;

	public JsonDeliveryAdvisorResponse getShipAdvises(@NonNull final JsonDeliveryAdvisorRequest deliveryAdvisorRequest)
	{
		try
		{
			logger.debug("Getting Ship Advises for request: {}", deliveryAdvisorRequest);
			final JsonShipAdvisorRequest requestBody = buildRequest(deliveryAdvisorRequest);
			final JsonShipAdvisorResponse response = restClient.post(SHIP_ADVISES_ENDPOINT, requestBody, deliveryAdvisorRequest.getShipperConfig(), JsonShipAdvisorResponse.class);

			logger.debug("Successfully received nShift response: {}", response);
			return buildJsonDeliveryResponse(response, deliveryAdvisorRequest.getId());
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

	private static JsonShipAdvisorRequest buildRequest(@NonNull final JsonDeliveryAdvisorRequest deliveryAdvisorRequest)
	{
		final JsonShipmentOptions options = JsonShipmentOptions.builder()
				.serviceLevel(deliveryAdvisorRequest.getShipperConfig().getAdditionalPropertyNotNull(NShiftConstants.SERVICE_LEVEL))
				.build();

		final JsonShipmentData.JsonShipmentDataBuilder dataBuilder = JsonShipmentData.builder();

		// Add Addresses
		dataBuilder.address(NShiftUtil.buildNShiftAddressBuilder(deliveryAdvisorRequest.getPickupAddress(), JsonAddressKind.SENDER)
				.attention(deliveryAdvisorRequest.getPickupAddress().getCompanyName1())
				.build());

		final JsonAddress.JsonAddressBuilder receiverAddressBuilder = NShiftUtil.buildNShiftReceiverAddress(
				deliveryAdvisorRequest.getDeliveryAddress(),
				deliveryAdvisorRequest.getDeliveryContact());
		dataBuilder.address(receiverAddressBuilder.build());

		dataBuilder.line(buildNShiftLine(deliveryAdvisorRequest.getItem()));

		return JsonShipAdvisorRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private static JsonLine buildNShiftLine(@NonNull final JsonDeliveryAdvisorRequestItem item)
	{
		// nShift expects weight in grams and dimensions in millimeters.
		final int weightGrams = item.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final JsonLine.JsonLineBuilder lineBuilder = JsonLine.builder()
				.lineWeight(weightGrams);
		if (item.getPackageDimensions() != null)
		{
			final int lengthMM = item.getPackageDimensions().getLengthInCM() * 10;
			final int widthMM = item.getPackageDimensions().getWidthInCM() * 10;
			final int heightMM = item.getPackageDimensions().getHeightInCM() * 10;
			lineBuilder.number(item.getNumberOfItems());
			lineBuilder.length(lengthMM);
			lineBuilder.width(widthMM);
			lineBuilder.height(heightMM);
		}

		return lineBuilder.build();
	}

	private static JsonDeliveryAdvisorResponse buildJsonDeliveryResponse(@NonNull final JsonShipAdvisorResponse response, @NonNull final String requestId)
	{
		Check.assumeEquals(response.getProducts().size(), 1, "response should only contain 1 shipperProduct, pls check defined shipment rules");
		final JsonShipAdvisorResponseProduct product = response.getProducts().get(0);
		final JsonDeliveryAdvisorResponse.JsonDeliveryAdvisorResponseBuilder responseBuilder = JsonDeliveryAdvisorResponse.builder()
				.requestId(requestId)
				.shipperProduct(product.getProdName())
				.responseItem(NShiftConstants.PROD_CONCEPT_ID, String.valueOf(product.getProdConceptID()));

		final JsonShipAdvisorResponseGoodsType productGoodsType = product.getProductGoodsType();
		if (productGoodsType != null)
		{
			responseBuilder.responseItem(NShiftConstants.GOODS_TYPE_ID, String.valueOf(productGoodsType.getGoodsTypeId()));
			responseBuilder.responseItem(NShiftConstants.GOODS_TYPE_NAME, productGoodsType.getGoodsTypeName());
		}

		responseBuilder.shipperProductServices(product.getServices().stream()
				.map(JsonShipAdvisorResponseService::getServiceId)
				.map(String::valueOf)
				.collect(ImmutableList.toImmutableList())
		);

		return responseBuilder.build();
	}
}