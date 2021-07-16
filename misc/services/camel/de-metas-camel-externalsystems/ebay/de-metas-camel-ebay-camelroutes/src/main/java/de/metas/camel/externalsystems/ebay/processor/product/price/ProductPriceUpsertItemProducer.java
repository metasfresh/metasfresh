/*
 * #%L
 * de-metas-camel-ebay-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.ebay.processor.product.price;

import de.metas.camel.externalsystems.ebay.EbayConstants;
import de.metas.camel.externalsystems.ebay.EbayUtils;
import de.metas.camel.externalsystems.ebay.api.model.LineItem;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPrice;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsertItem;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;


public class ProductPriceUpsertItemProducer
{
	@NonNull
	String orgCode;

	@NonNull
	LineItem lineItem;

	@NonNull
	String productIdentifier;

	@NonNull
	ProductPriceRequestProducerResult.ProductPriceRequestProducerResultBuilder resultBuilder;

	@Builder
	public ProductPriceUpsertItemProducer(
			@NonNull final String orgCode,
			@NonNull final LineItem lineItem,
			@NonNull final String productId)
	{
		this.orgCode = orgCode;
		this.lineItem = lineItem;
		this.productIdentifier = EbayUtils.formatExternalId(productId);
		this.resultBuilder = ProductPriceRequestProducerResult.builder();
	}

	@NonNull
	public ProductPriceRequestProducerResult run()
	{
		final String productPriceIdentifier = productIdentifier + "_" + "PRICE"; 

		final JsonRequestProductPriceUpsertItem requestProductPriceUpsertItem = JsonRequestProductPriceUpsertItem.builder()
				.productPriceIdentifier(productPriceIdentifier)
				.jsonRequestProductPrice(getJsonRequestProductPrice())
				.build();

		resultBuilder
				.jsonRequestProductPriceUpsertItem(requestProductPriceUpsertItem)
				.priceListVersionIdentifier(EbayConstants.DEFAULT_PRICELIST_ID);

		return resultBuilder.build();
	}


	@NonNull
	private JsonRequestProductPrice getJsonRequestProductPrice()
	{
		final JsonRequestProductPrice jsonRequestProductPrice = new JsonRequestProductPrice();

		jsonRequestProductPrice.setOrgCode(orgCode);
		jsonRequestProductPrice.setPriceStd(BigDecimal.ZERO);
		jsonRequestProductPrice.setTaxCategory(EbayConstants.DEFAULT_TAX_CATEGORY);
		jsonRequestProductPrice.setProductIdentifier(productIdentifier);

		return jsonRequestProductPrice;
	}

}

