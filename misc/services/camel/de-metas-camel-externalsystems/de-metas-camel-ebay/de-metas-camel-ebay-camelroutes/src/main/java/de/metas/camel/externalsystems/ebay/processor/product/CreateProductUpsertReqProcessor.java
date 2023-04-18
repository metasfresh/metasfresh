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

package de.metas.camel.externalsystems.ebay.processor.product;

import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.ebay.EbayImportOrdersRouteContext;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static de.metas.camel.externalsystems.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.ebay.ProcessorHelper.getPropertyOrThrowError;

public class CreateProductUpsertReqProcessor implements Processor
{
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);
		
		final Order ebayOrder = importOrdersRouteContext.getOrderNotNull();

		final ProductUpsertRequestProducer productUpsertRequestProducer = ProductUpsertRequestProducer.builder()
				.orgCode(importOrdersRouteContext.getOrgCode())
				.articles(ebayOrder.getLineItems())
				.build();

		final Optional<ProductRequestProducerResult> productRequestProducerResult = productUpsertRequestProducer.run();

		if (productRequestProducerResult.isPresent())
		{
			final ProductUpsertCamelRequest productUpsertCamelRequest = ProductUpsertCamelRequest.builder()
					.jsonRequestProductUpsert(productRequestProducerResult.get().getJsonRequestProductUpsert())
					.orgCode(importOrdersRouteContext.getOrgCode())
					.build();

			exchange.getIn().setBody(productUpsertCamelRequest);
		}
		else
		{
			exchange.getIn().setBody(null);
		}

	}

}
