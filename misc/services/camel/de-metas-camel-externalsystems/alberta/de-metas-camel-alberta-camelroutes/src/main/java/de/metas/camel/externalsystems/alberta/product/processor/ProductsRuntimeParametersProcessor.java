/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.externalsystems.alberta.product.processor;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.alberta.ProcessorHelper;
import de.metas.camel.externalsystems.alberta.ordercandidate.NextImportSinceTimestamp;
import de.metas.camel.externalsystems.alberta.product.PushProductsRouteConstants;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonRuntimeParameterUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER;

public class ProductsRuntimeParametersProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final NextImportSinceTimestamp updatedAfter =
				ProcessorHelper.getPropertyOrThrowError(exchange, PushProductsRouteConstants.ROUTE_PROPERTY_UPDATED_AFTER, NextImportSinceTimestamp.class);
		final String command =
				ProcessorHelper.getPropertyOrThrowError(exchange, PushProductsRouteConstants.ROUTE_PROPERTY_COMMAND, String.class);
		final JsonMetasfreshId externalSystemConfigId =
				ProcessorHelper.getPropertyOrThrowError(exchange, PushProductsRouteConstants.ROUTE_PROPERTY_EXTERNAL_SYSTEM_CONFIG_ID, JsonMetasfreshId.class);

		final JsonRuntimeParameterUpsertItem runtimeParameterUpsertItem = JsonRuntimeParameterUpsertItem.builder()
				.externalSystemParentConfigId(externalSystemConfigId)
				.request(command)
				.name(PARAM_UPDATED_AFTER)
				.value(String.valueOf(updatedAfter.getDate()))
				.build();

		final JsonESRuntimeParameterUpsertRequest request = JsonESRuntimeParameterUpsertRequest.builder()
				.runtimeParameterUpsertItems(ImmutableList.of(runtimeParameterUpsertItem))
				.build();

		exchange.getIn().setBody(request);
	}
}
