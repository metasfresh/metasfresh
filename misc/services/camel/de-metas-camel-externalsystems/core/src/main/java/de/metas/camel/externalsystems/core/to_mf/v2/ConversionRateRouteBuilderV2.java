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

import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.rest_api.v2.conversionRate.JsonCurrencyRateCreateRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_TARGET_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CREATE_CONVERSION_RATE_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CREATE_CONVERSION_RATE_V2_URI;

@Component
public class ConversionRateRouteBuilderV2 extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from("{{" + MF_CREATE_CONVERSION_RATE_CAMEL_URI + "}}")
				.routeId(MF_CREATE_CONVERSION_RATE_CAMEL_URI)
				.streamCaching()

				.process(this::processJsonCurrencyRateCreateRequest)

				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonCurrencyRateCreateRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))

				.toD("${header." + HEADER_TARGET_URI + "}");
	}

	private void processJsonCurrencyRateCreateRequest(@NonNull final Exchange exchange)
	{
		final Object request = exchange.getIn().getBody();
		if (!(request instanceof JsonCurrencyRateCreateRequest))
		{
			throw new RuntimeCamelException("The route " + MF_CREATE_CONVERSION_RATE_CAMEL_URI
													+ " requires the body to be instanceof " + JsonCurrencyRateCreateRequest.class.getName()
													+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
		}

		final String conversionRateBaseURL = exchange.getContext().getPropertiesComponent().resolveProperty(MF_CREATE_CONVERSION_RATE_V2_URI)
				.orElseThrow(() -> new RuntimeCamelException("Missing mandatory property: " + MF_CREATE_CONVERSION_RATE_V2_URI));

		exchange.getIn().setHeader(HEADER_TARGET_URI, conversionRateBaseURL + "/rates");
	}
}
