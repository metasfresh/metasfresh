/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.product.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.UpsertProductPriceList;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProduct;
import de.metas.camel.externalsystems.shopware6.product.ImportProductsRouteContext;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPrice;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsert;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsertItem;
import de.metas.common.pricing.v2.productprice.TaxCategory;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.math.BigDecimal;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat.formatExternalId;

public class ProductPriceProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public ProductPriceProcessor(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange)
	{
		final ImportProductsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT, ImportProductsRouteContext.class);

		final String targetPriceListId = context.getTargetPriceListId();

		final JsonProduct product = context.getJsonProduct();

		if (targetPriceListId == null)
		{
			exchange.getIn().setBody(null);
			processLogger.logMessage("Target price list id param is missing! productId: " + product.getId(), context.getPInstanceId());
			return;
		}

		if (product.getJsonTax() == null)
		{
			exchange.getIn().setBody(null);
			processLogger.logMessage("No tax info present for product: " + product.getId(), context.getPInstanceId());
			return;
		}

		final TaxCategory taxCategory = context.getTaxCategoryProvider().getTaxCategory(product.getJsonTax().getTaxRate());

		if (taxCategory == null)
		{
			exchange.getIn().setBody(null);
			processLogger.logMessage("No tax category found for taxRate: " + product.getJsonTax().getTaxRate()
											 + ", product: " + product, context.getPInstanceId());
			return;
		}

		exchange.getIn().setBody(buildUpsertProductPriceRequest(taxCategory, targetPriceListId, context));
	}

	@NonNull
	private UpsertProductPriceList buildUpsertProductPriceRequest(
			@NonNull final TaxCategory taxCategory,
			@NonNull final String targetPriceListId,
			@NonNull final ImportProductsRouteContext context)
	{
		final String productIdentifier = formatExternalId(context.getJsonProduct().getId());

		final JsonRequestProductPrice jsonRequestProductPrice = new JsonRequestProductPrice();
		jsonRequestProductPrice.setProductIdentifier(productIdentifier);
		jsonRequestProductPrice.setOrgCode(context.getOrgCode());
		jsonRequestProductPrice.setTaxCategory(taxCategory);
		jsonRequestProductPrice.setPriceStd(BigDecimal.ZERO);

		final JsonRequestProductPriceUpsertItem jsonRequestProductPriceUpsertItem = JsonRequestProductPriceUpsertItem.builder()
				.productPriceIdentifier(productIdentifier)
				.jsonRequestProductPrice(jsonRequestProductPrice)
				.build();

		final JsonRequestProductPriceUpsert jsonRequestProductPriceUpsert = JsonRequestProductPriceUpsert.builder()
				.requestItems(ImmutableList.of(jsonRequestProductPriceUpsertItem))
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return UpsertProductPriceList.builder()
				.priceListIdentifier(targetPriceListId)
				.jsonRequestProductPriceUpsert(jsonRequestProductPriceUpsert)
				.build();
	}
}
