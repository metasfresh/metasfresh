/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.to_mf.v2;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.InvokeExternalSystemActionCamelRequest;
import de.metas.camel.externalsystems.common.v2.RetrieveExternalSystemInfoCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_REQUEST;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_EXTERNAL_SYSTEM_V2_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ExternalSystemRouteBuilder extends RouteBuilder
{

	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID))
				.routeId(ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(exchange -> {
					final Object request = exchange.getIn().getBody();
					if (!(request instanceof JsonESRuntimeParameterUpsertRequest))
					{
						throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID + " requires the body to be instanceof JsonESRuntimeParameterUpsertRequest. "
																+ "However, it is " + (request == null ? "null" : request.getClass().getName()));
					}
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonESRuntimeParameterUpsertRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.externalsystem.v2.api.uri}}/runtimeParameter/bulk")

				.to(direct(UNPACK_V2_API_RESPONSE));

		from("{{" + ExternalSystemCamelConstants.MF_INVOKE_EXTERNAL_SYSTEM_ACTION_V2_CAMEL_URI + "}}")
				.routeId(ExternalSystemCamelConstants.MF_INVOKE_EXTERNAL_SYSTEM_ACTION_V2_CAMEL_URI)
				.streamCaching()
				.log("Route invoked!")
				.process(this::processInvokeRequest)
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_EXTERNAL_SYSTEM_V2_URI + "}}/invoke/${header." + HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE + "}/${header." + HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE + "}/${header." + HEADER_EXTERNAL_SYSTEM_REQUEST + "}")

				.to(direct(UNPACK_V2_API_RESPONSE));

		from("{{" + ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO + "}}")
				.routeId(ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO)
				.streamCaching()
				.log("Route invoked!")
				.process(this::processRetrieveInfoRequest)
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("{{" + MF_EXTERNAL_SYSTEM_V2_URI + "}}/${header." + HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE + "}/${header." + HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE + "}/info")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}

	private void processInvokeRequest(@NonNull final Exchange exchange)
	{
		final Object request = exchange.getIn().getBody();
		if (!(request instanceof InvokeExternalSystemActionCamelRequest))
		{
			throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_INVOKE_EXTERNAL_SYSTEM_ACTION_V2_CAMEL_URI
													+ " requires the body to be instanceof " + InvokeExternalSystemActionCamelRequest.class.getName()
													+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
		}

		final InvokeExternalSystemActionCamelRequest invokeExternalSystemActionCamelRequest = (InvokeExternalSystemActionCamelRequest)request;

		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE,invokeExternalSystemActionCamelRequest.getExternalSystemConfigType());
		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE, invokeExternalSystemActionCamelRequest.getExternalSystemChildValue());
		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_REQUEST, invokeExternalSystemActionCamelRequest.getCommand());

		exchange.getIn().setBody(null);
	}

	private void processRetrieveInfoRequest(@NonNull final Exchange exchange)
	{
		final Object request = exchange.getIn().getBody();
		if (!(request instanceof RetrieveExternalSystemInfoCamelRequest))
		{
			throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO
													+ " requires the body to be instanceof " + RetrieveExternalSystemInfoCamelRequest.class.getName()
													+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
		}

		final RetrieveExternalSystemInfoCamelRequest retrieveExternalSystemInfoCamelRequest = (RetrieveExternalSystemInfoCamelRequest)request;

		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE,retrieveExternalSystemInfoCamelRequest.getExternalSystemConfigType());
		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE, retrieveExternalSystemInfoCamelRequest.getExternalSystemChildValue());

		exchange.getIn().setBody(null);
	}
}
