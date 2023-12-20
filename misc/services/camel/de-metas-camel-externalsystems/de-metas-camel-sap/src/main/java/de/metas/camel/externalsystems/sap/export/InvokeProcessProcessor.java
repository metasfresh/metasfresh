/*
 * #%L
 * de-metas-camel-sap
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.sap.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.InvokeProcessCamelRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.process.request.JSONProcessParam;
import de.metas.common.rest_api.v2.process.request.RunProcessRequest;
import de.metas.common.util.Check;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.sap.export.ExportAcctDetailsRouteBuilder.ROUTE_PROPERTY_EXPORT_ACCT_ROUTE_CONTEXT;

public class InvokeProcessProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws JsonProcessingException
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String processValue = request.getParameters().get(ExternalSystemConstants.PARAM_PostGREST_AD_Process_Value);

		if (Check.isBlank(processValue))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PostGREST_AD_Process_Value);
		}

		final String invokeProcessPrams = request.getParameters().get(ExternalSystemConstants.PARAM_PostGREST_JSONParamList);

		if (Check.isBlank(invokeProcessPrams))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PostGREST_JSONParamList);
		}

		final ImmutableList<JSONProcessParam> processParams = ImmutableList.copyOf(JsonObjectMapperHolder.sharedJsonObjectMapper()
																						   .readValue(invokeProcessPrams, JSONProcessParam[].class));

		final InvokeProcessCamelRequest invokeProcessRequest = InvokeProcessCamelRequest.builder()
				.processValue(processValue)
				.runProcessRequest(RunProcessRequest.builder()
										   .processParameters(processParams)
										   .build())
				.build();

		final ExportAcctDetailsRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange,
																								   ROUTE_PROPERTY_EXPORT_ACCT_ROUTE_CONTEXT,
																								   ExportAcctDetailsRouteContext.class);

		routeContext.setInvokePostgRESTRequest(invokeProcessRequest);

		exchange.getIn().setBody(invokeProcessRequest);
	}
}
