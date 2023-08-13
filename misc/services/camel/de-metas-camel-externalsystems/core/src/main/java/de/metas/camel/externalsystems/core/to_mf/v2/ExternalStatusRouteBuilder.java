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
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.common.v2.RetreiveServiceStatusCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.externalsystem.status.JsonExternalStatusResponse;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SERVICE_VALUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_EXTERNAL_SYSTEM_V2_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ExternalStatusRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.routeId(ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI)
				.streamCaching()
				.log("Route invoked!")
				.process(this::processCreateRequest)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonStatusRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_EXTERNAL_SYSTEM_V2_URI + "}}/service/${header." + HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE + "}/${header." + HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE + "}/${header." + HEADER_EXTERNAL_SERVICE_VALUE + "}/status");


		from("{{" + ExternalSystemCamelConstants.MF_GET_SERVICE_STATUS_V2_CAMEL_URI + "}}")
				.routeId(ExternalSystemCamelConstants.MF_GET_SERVICE_STATUS_V2_CAMEL_URI)
				.streamCaching()
				.log("Route invoked!")
				.process(this::processRetrieveRequest)
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("{{" + MF_EXTERNAL_SYSTEM_V2_URI + "}}/service/${header." + HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE + "}/status")

				.to(direct(UNPACK_V2_API_RESPONSE))
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalStatusResponse.class));
	}

	private void processCreateRequest(@NonNull final Exchange exchange)
	{
		final Object request = exchange.getIn().getBody();
		if (!(request instanceof ExternalStatusCreateCamelRequest))
		{
			throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI
													+ " requires the body to be instanceof " + ExternalStatusCreateCamelRequest.class.getName()
													+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
		}

		final ExternalStatusCreateCamelRequest externalStatusRequest = (ExternalStatusCreateCamelRequest)request;

		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE, externalStatusRequest.getExternalSystemConfigType());
		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE, externalStatusRequest.getExternalSystemChildConfigValue());
		exchange.getIn().setHeader(HEADER_EXTERNAL_SERVICE_VALUE, externalStatusRequest.getServiceValue());
		final JsonStatusRequest jsonStatusRequest = externalStatusRequest.getJsonStatusRequest();

		exchange.getIn().setBody(jsonStatusRequest);
	}


	private void processRetrieveRequest(@NonNull final Exchange exchange)
	{
		final Object request = exchange.getIn().getBody();
		if (!(request instanceof RetreiveServiceStatusCamelRequest))
		{
			throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_GET_SERVICE_STATUS_V2_CAMEL_URI + " requires the body to be instanceof "
													+ RetreiveServiceStatusCamelRequest.class.getName()
													+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
		}

		final RetreiveServiceStatusCamelRequest externalTypeCamelRequest = (RetreiveServiceStatusCamelRequest)request;

		exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_CONFIG_TYPE, externalTypeCamelRequest.getType());
	}
}
