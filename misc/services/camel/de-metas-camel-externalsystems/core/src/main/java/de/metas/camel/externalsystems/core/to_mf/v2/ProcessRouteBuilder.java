/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.to_mf.v2;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.InvokeProcessCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.rest_api.v2.process.request.RunProcessRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_AD_Process_Value;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_AD_PROCESS_V2_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_INVOKE_AD_PROCESS;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ProcessRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_INVOKE_AD_PROCESS))
				.routeId(MF_INVOKE_AD_PROCESS)
				.log("Route invoked!")
				.process(exchange -> {
					final Object request = exchange.getIn().getBody();
					if (!(request instanceof InvokeProcessCamelRequest))
					{
						throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_INVOKE_AD_PROCESS + " requires the body to be instanceof InvokeProcessCamelRequest. "
																+ "However, it is " + (request == null ? "null" : request.getClass().getName()));
					}

					final InvokeProcessCamelRequest invokeProcessReq = (InvokeProcessCamelRequest)request;

					exchange.getIn().setHeader(HEADER_AD_Process_Value, invokeProcessReq.getProcessValue());
					exchange.getIn().setBody(invokeProcessReq.getRunProcessRequest());
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), RunProcessRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_AD_PROCESS_V2_URI + "}}/${header." + HEADER_AD_Process_Value + "}/invoke")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
