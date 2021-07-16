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

import de.metas.camel.externalsystems.common.v2.ProductPriceUpsertCamelRequest;
import de.metas.camel.externalsystems.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.externalsystems.ebay.api.model.LineItem;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsert;
import de.metas.common.pricing.v2.productprice.JsonRequestProductPriceUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.ebay.ProcessorHelper.getPropertyOrThrowError;

public class CreateProductPriceUpsertReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);

		final Order ebayOrder = importOrdersRouteContext.getOrderNotNull();


		final List<ProductPriceRequestProducerResult> productPriceRequestProducerResults = new ArrayList<>();

		for (final LineItem lineItem : ebayOrder.getLineItems())
		{
			productPriceRequestProducerResults.add(ProductPriceUpsertItemProducer.builder()
													.orgCode(importOrdersRouteContext.getOrgCode())
													.lineItem(lineItem)
													.productId(lineItem.getSku())
													.build()
													.run());
		}

		final List<ProductPriceUpsertCamelRequest> camelRequests = groupRequestItemsByPriceListVersionIdentifier(productPriceRequestProducerResults);

		exchange.getIn().setBody(camelRequests);
	}

	@NonNull
	private List<ProductPriceUpsertCamelRequest> groupRequestItemsByPriceListVersionIdentifier(
			@NonNull final List<ProductPriceRequestProducerResult> producerResults)
	{
		final Map<String, List<JsonRequestProductPriceUpsertItem>> productPriceRequestByPLV = new HashMap<>();

		producerResults
				.forEach(result -> {
					final ArrayList<JsonRequestProductPriceUpsertItem> productPriceUpsertItems = new ArrayList<>();
					productPriceUpsertItems.add(result.getJsonRequestProductPriceUpsertItem());

					productPriceRequestByPLV.merge(result.getPriceListVersionIdentifier(),
							productPriceUpsertItems,
							(oldList, newList) -> {
								oldList.addAll(newList);
								return oldList;
							});
				});

		return productPriceRequestByPLV.keySet()
				.stream()
				.map(key -> buildCamelRequest(key, productPriceRequestByPLV.get(key)))
				.collect(Collectors.toList());
	}

	@NonNull
	private ProductPriceUpsertCamelRequest buildCamelRequest(
			@NonNull final String priceListVersionIdentifier,
			@NonNull final List<JsonRequestProductPriceUpsertItem> requestItems)
	{
		final JsonRequestProductPriceUpsert requestProductPriceUpsert = JsonRequestProductPriceUpsert.builder()
				.requestItems(requestItems)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return ProductPriceUpsertCamelRequest.builder()
				.priceListVersionIdentifier(priceListVersionIdentifier)
				.jsonRequestProductPriceUpsert(requestProductPriceUpsert)
				.build();
	}
}
