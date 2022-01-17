/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.hu.processor;

import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.ExportGRSRouteContext;
import de.metas.camel.externalsystems.grssignum.to_grs.client.model.DispatchRequest;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.util.EmptyUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

public class ExportHUProcessor implements Processor
{
	@Override
	public void process(@NonNull final Exchange exchange) throws Exception
	{
		final ExportGRSRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_GRS_CONTEXT, ExportGRSRouteContext.class);

		final JsonGetSingleHUResponse jsonGetSingleHUResponse = exchange.getIn().getBody(JsonGetSingleHUResponse.class);

		if (jsonGetSingleHUResponse.getError() != null || !EmptyUtil.isEmpty(jsonGetSingleHUResponse.getError()))
		{
			throw new RuntimeCamelException("Retrieve HU by id returned error: " + jsonGetSingleHUResponse.getError());
		}

		final JsonHU jsonHUToExport = jsonGetSingleHUResponse.getResult();

		final DispatchRequest dispatchRequest = DispatchRequest.builder()
				.url(routeContext.getRemoteUrl())
				.authToken(routeContext.getAuthToken())
				.request(JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(jsonHUToExport))
				.build();

		exchange.getIn().setBody(dispatchRequest);
	}
}
