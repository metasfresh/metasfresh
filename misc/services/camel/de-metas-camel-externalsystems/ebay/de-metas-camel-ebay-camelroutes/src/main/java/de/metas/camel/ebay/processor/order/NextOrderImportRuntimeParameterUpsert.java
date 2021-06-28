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

package de.metas.camel.ebay.processor.order;

import static de.metas.camel.ebay.EbayConstants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.ebay.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER;

import java.time.Instant;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.google.common.collect.ImmutableList;

import de.metas.camel.ebay.EbayImportOrdersRouteContext;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonRuntimeParameterUpsertItem;

public class NextOrderImportRuntimeParameterUpsert implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final EbayImportOrdersRouteContext importOrdersRouteContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, EbayImportOrdersRouteContext.class);

		final Optional<Instant> nextImportStartingTimestamp = importOrdersRouteContext.getNextImportStartingTimestamp();

		if (nextImportStartingTimestamp.isEmpty())
		{
			//nothing to do
			exchange.getIn().setBody(null);
			return;
		}

		final JsonRuntimeParameterUpsertItem runtimeParameterUpsertItem = JsonRuntimeParameterUpsertItem.builder()
				.externalSystemParentConfigId(importOrdersRouteContext.getExternalSystemRequest().getExternalSystemConfigId())
				.name(PARAM_UPDATED_AFTER)
				.request(importOrdersRouteContext.getExternalSystemRequest().getCommand())
				.value(nextImportStartingTimestamp.get().toString())
				.build();

		final JsonESRuntimeParameterUpsertRequest request = JsonESRuntimeParameterUpsertRequest.builder()
				.runtimeParameterUpsertItems(ImmutableList.of(runtimeParameterUpsertItem))
				.build();

		exchange.getIn().setBody(request);
	}

}
