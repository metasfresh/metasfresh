/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.order.processor;

import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.DATA_SOURCE_INT_SHOPWARE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;

public class ProcessOLCandProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportOrdersRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		if (context.getImportedExternalHeaderIds().isEmpty())
		{
			//nothing to do
			exchange.getIn().setBody(null);
			return;
		}

		final List<JsonOLCandProcessRequest> olCandClearRequests = context.getImportedExternalHeaderIds().stream()
				.map(externalHeaderId -> JsonOLCandProcessRequest.builder()
						.externalHeaderId(externalHeaderId)
						.inputDataSourceName(DATA_SOURCE_INT_SHOPWARE)
						.ship(true)
						.invoice(true)
						.closeOrder(true)
						.build())
				.collect(Collectors.toList());

		exchange.getIn().setBody(olCandClearRequests);
	}
}
