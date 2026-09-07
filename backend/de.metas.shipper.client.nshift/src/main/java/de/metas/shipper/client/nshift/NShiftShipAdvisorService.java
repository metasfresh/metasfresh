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
import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import de.metas.common.delivery.v1.json.response.JsonDeliveryAdvisorResponse;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.StringUtils;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
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

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class NShiftShipAdvisorService
{
	private static final Logger logger = LogManager.getLogger(NShiftShipAdvisorService.class);
	private static final String SHIP_ADVISES_ENDPOINT = "/ShipServer/{ID}/shipAdvises";

	@NonNull private final NShiftRestClient restClient;

	public JsonDeliveryAdvisorResponse advise(@NonNull final JsonDeliveryAdvisorRequest deliveryAdvisorRequest)
	{
		try
		{
			logger.debug("Getting Ship Advises for request: {}", deliveryAdvisorRequest);
			final JsonShipAdvisorRequest requestBody = buildRequest(deliveryAdvisorRequest);
			final JsonShipAdvisorResponse response = restClient.post(SHIP_ADVISES_ENDPOINT, requestBody, deliveryAdvisorRequest.getShipperConfig(), JsonShipAdvisorResponse.class);

			logger.debug("Successfully received nShift response: {}", response);
			return buildJsonDeliveryAdvisorResponse(response, deliveryAdvisorRequest.getId());
		}
		catch (final Throwable throwable)
		{
			logger.error("Got error", throwable);
			return JsonDeliveryAdvisorResponse.builder()
					.requestId(deliveryAdvisorRequest.getId())
					.errorMessage(CoalesceUtil.coalesceNotNull(throwable.getMessage(), throwable.toString()))
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

		dataBuilder.line(NShiftUtil.buildAdvisorLine(deliveryAdvisorRequest, mappingConfigs));

		dataBuilder.detailGroups(NShiftUtil.buildAdvisorDetailGroups(deliveryAdvisorRequest, mappingConfigs));

		return JsonShipAdvisorRequest.builder()
				.options(options)
				.data(dataBuilder.build())
				.build();
	}

	private static JsonDeliveryAdvisorResponse buildJsonDeliveryAdvisorResponse(@NonNull final JsonShipAdvisorResponse response, @NonNull final String requestId)
	{
		Check.assumeNotEmpty(response.getProducts(), "response should contain at least 1 shipperProduct, pls check defined shipment rules");
		final JsonShipAdvisorResponseProduct product = response.getProducts().get(0);
		final JsonDeliveryAdvisorResponse.JsonDeliveryAdvisorResponseBuilder responseBuilder = JsonDeliveryAdvisorResponse.builder()
				.requestId(requestId)
				.shipperProduct(JsonShipperProduct.builder()
						.name(product.getProdName())
						.code(String.valueOf(product.getProdConceptID()))
						.build());

		final JsonShipAdvisorResponseGoodsType productGoodsType = Check.assumeNotNull(product.getProductGoodsType(), "response should contain a GoodsType, pls check defined shipment rules");
		responseBuilder.goodsType(JsonGoodsType.builder()
				.id(String.valueOf(productGoodsType.getGoodsTypeId()))
				.name(productGoodsType.getGoodsTypeName())
				.build());

		responseBuilder.shipperProductServices(product.getServices()
				.stream()
				.map(NShiftShipAdvisorService::toJsonCarrierService)
				.collect(ImmutableList.toImmutableList())
		);

		return responseBuilder.build();
	}

	private static JsonCarrierService toJsonCarrierService(@NonNull final JsonShipAdvisorResponseService jsonShipAdvisorResponseService)
	{
		return JsonCarrierService.builder()
				.name(jsonShipAdvisorResponseService.getName())
				.id(String.valueOf(jsonShipAdvisorResponseService.getServiceId()))
				.build();
	}
}