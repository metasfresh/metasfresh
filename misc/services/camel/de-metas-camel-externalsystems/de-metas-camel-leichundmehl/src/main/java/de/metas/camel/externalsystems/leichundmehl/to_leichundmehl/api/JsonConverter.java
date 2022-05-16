/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api;

import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonBPartner;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPPOrder;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonPrice;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model.JsonProductInfo;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.pricing.v2.productprice.JsonResponsePrice;
import de.metas.common.product.v2.response.JsonProduct;
import de.metas.common.product.v2.response.JsonProductBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class JsonConverter
{
	@NonNull
	public JsonPrice mapToJsonPrice(@NonNull final JsonResponsePrice json)
	{
		return JsonPrice.builder()
				.price(json.getPrice())
				.currencyCode(json.getCurrencyCode())
				.isSOTrx(json.getIsSOTrx())
				.productCode(json.getProductCode())
				.productId(json.getProductId().getValue())
				.taxCategoryId(json.getTaxCategoryId().getValue())
				.countryCode(json.getCountryCode())
				.build();
	}

	@NonNull
	public JsonPPOrder mapToJsonPPOrder(
			@NonNull final JsonResponseManufacturingOrder ppOrder,
			@NonNull final JsonProductInfo jsonProductInfo,
			@Nullable final JsonBPartner jsonBPartner)
	{
		return JsonPPOrder.builder()
				.orderId(String.valueOf(ppOrder.getOrderId().getValue()))
				.dateOrdered(ppOrder.getDateOrdered().toInstant())
				.dateStartSchedule(ppOrder.getDateStartSchedule().toInstant())

				.documentNo(ppOrder.getDocumentNo())
				.orgCode(ppOrder.getOrgCode())
				.description(ppOrder.getDescription())

				.finishGoodProduct(jsonProductInfo)
				.qtyToProduce(ppOrder.getQtyToProduce())
				.bPartner(jsonBPartner)

				.components(ppOrder.getComponents())
				.build();
	}

	@NonNull
	public JsonProductInfo.JsonProductInfoBuilder initProductInfo(
			@NonNull final JsonResponseManufacturingOrder jsonResponseManufacturingOrder,
			@NonNull final JsonProduct extendedProductInfo)
	{
		final de.metas.common.shipping.v2.JsonProduct finishedGood = jsonResponseManufacturingOrder.getFinishGoodProduct();

		final JsonProductInfo.JsonProductInfoBuilder builder = JsonProductInfo.builder()
				.id(extendedProductInfo.getId().getValue())
				.externalId(extendedProductInfo.getExternalId())
				.manufacturerName(extendedProductInfo.getManufacturerName())
				.productCategoryId(extendedProductInfo.getProductCategoryId().getValue())
				.productNo(extendedProductInfo.getProductNo())
				.uomCode(extendedProductInfo.getUom())
				.name(extendedProductInfo.getName())
				.manufacturerNumber(extendedProductInfo.getManufacturerNumber())
				.ean(extendedProductInfo.getEan())
				.description(extendedProductInfo.getDescription())
				.packageSize(finishedGood.getPackageSize())
				.weight(finishedGood.getWeight())
				.commodityNumberValue(finishedGood.getCommodityNumberValue())
				.documentNote(finishedGood.getDocumentNote())
				.stocked(finishedGood.isStocked())
				.manufacturerId(JsonMetasfreshId.toValue(extendedProductInfo.getManufacturerId()));

		final Integer bpartnerId = JsonMetasfreshId.toValue(jsonResponseManufacturingOrder.getBpartnerId());
		if (bpartnerId != null)
		{
			extendedProductInfo.getBpartners()
					.stream()
					.filter(bpartner -> bpartner.getBpartnerId().getValue() == bpartnerId)
					.findFirst()
					.map(JsonConverter::mapToJsonProductBPartner)
					.ifPresent(builder::bPartnerProduct);
		}

		return builder;
	}

	@NonNull
	private JsonBPartnerProduct mapToJsonProductBPartner(@NonNull final JsonProductBPartner json)
	{
		return JsonBPartnerProduct.builder()
				.bpartnerId(String.valueOf(json.getBpartnerId().getValue()))

				.currentVendor(json.isCurrentVendor())
				.customer(json.isCustomer())

				.ean(json.getEan())
				.productNo(json.getProductNo())
				.productCategory(json.getProductCategory())

				.excludedFromPurchase(json.isExcludedFromPurchase())
				.excludedFromSale(json.isExcludedFromSale())
				.exclusionFromPurchaseReason(json.getExclusionFromPurchaseReason())
				.exclusionFromSaleReason(json.getExclusionFromSaleReason())
				.productName(json.getProductName())

				.productDescription(json.getProductDescription())
				.leadTimeInDays(json.getLeadTimeInDays())
				.vendor(json.isVendor())
				.build();
	}
}
