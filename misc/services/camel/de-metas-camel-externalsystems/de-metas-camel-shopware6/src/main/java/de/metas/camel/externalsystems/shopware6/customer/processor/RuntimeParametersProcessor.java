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

package de.metas.camel.externalsystems.shopware6.customer.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.shopware6.customer.ImportCustomersRouteContext;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonRuntimeParameterUpsertItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.Instant;
import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER;

public class RuntimeParametersProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportCustomersRouteContext context =
				ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT, ImportCustomersRouteContext.class);

		if (context.isSkipNextImportTimestamp())
		{
			//nothing to do
			exchange.getIn().setBody(null);
			return;
		}

		final Instant updatedAt = Optional
				.ofNullable(context.getOldestFailingCustomer())
				.orElse(Instant.now());

		final JsonRuntimeParameterUpsertItem runtimeParameterUpsertItem = JsonRuntimeParameterUpsertItem.builder()
				.externalSystemParentConfigId(context.getExternalSystemRequest().getExternalSystemConfigId())
				.name(PARAM_UPDATED_AFTER)
				.request(context.getExternalSystemRequest().getCommand())
				.value(updatedAt.toString())
				.build();

		final JsonESRuntimeParameterUpsertRequest request = JsonESRuntimeParameterUpsertRequest.builder()
				.runtimeParameterUpsertItems(ImmutableList.of(runtimeParameterUpsertItem))
				.build();

		exchange.getIn().setBody(request);
	}
}
