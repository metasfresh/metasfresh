/*
 * #%L
 * de-metas-camel-edi
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

package de.metas.edi.esb.remadvimport.ecosio;

import at.erpel.schemas._1p0.messaging.message.ErpelMessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceRequest;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceResponse;
import de.metas.edi.esb.commons.Util;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.AUTHORIZATION;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.CREATE_REMADV_MF_URL;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.ECOSIO_AUTH_TOKEN;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.ECOSIO_REMADV_XML_TO_JSON_ROUTE;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.INPUT_REMADV_LOCAL;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.INPUT_REMADV_REMOTE;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.NUMBER_OF_ITEMS;
import static de.metas.edi.esb.remadvimport.ecosio.EcosioRemadvConstants.REMADV_XML_TO_JSON_PROCESSOR;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.http;

@Component
public class EcosioRemadvRoute extends RouteBuilder
{
	@Override
	public final void configure()
	{
		final JacksonDataFormat requestJacksonDataFormat = setupMetasfreshJSONFormat(getContext(), JsonCreateRemittanceAdviceRequest.class);
		final JacksonDataFormat responseJacksonDataFormat = setupMetasfreshJSONFormat(getContext(), JsonCreateRemittanceAdviceResponse.class);
		final JaxbDataFormat jacksonXMLDataFormat = setupECOSIOXMLFormat();

		final String remoteEndpoint = Util.resolveProperty(getContext(), INPUT_REMADV_REMOTE, "");
		if (!Util.isEmpty(remoteEndpoint))
		{
			from(remoteEndpoint)
					.routeId("ecosio-Remote-XML-Remadv-To-Local")
					.log(LoggingLevel.INFO, "Getting remote file")
					.to("{{" + INPUT_REMADV_LOCAL + "}}");
		}

		// @formatter:off
		from("{{" +INPUT_REMADV_LOCAL + "}}")
				.routeId(ECOSIO_REMADV_XML_TO_JSON_ROUTE)
				.streamCaching()
				.unmarshal(jacksonXMLDataFormat)
				.process(new RemadvXmlToJsonProcessor()).id(REMADV_XML_TO_JSON_PROCESSOR)
				.choice()
				.when(header(NUMBER_OF_ITEMS).isLessThanOrEqualTo(0))
					.log(LoggingLevel.INFO, "Nothing to do! no remittance advices found in file: ${header." + Exchange.FILE_NAME + "}")
				.otherwise()
					.log(LoggingLevel.INFO, "Posting ${header." + NUMBER_OF_ITEMS + "} remittance advices to metasfresh.")
					.marshal(requestJacksonDataFormat)
					.removeHeaders("*", "Authorization") // we don't want so send all headers as HTTP-headers; might be too much and we'd get an error back
					.setHeader(AUTHORIZATION, simple(ECOSIO_AUTH_TOKEN))
					.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
					.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
					.to(http(CREATE_REMADV_MF_URL))
					.unmarshal(responseJacksonDataFormat)
					.process(new RemadvResponseProcessor())
				.end();
		// @formatter:on
	}

	@NonNull
	public JacksonDataFormat setupMetasfreshJSONFormat(
			@NonNull final CamelContext context,
			@NonNull final Class<?> unmarshalType)
	{
		final ObjectMapper objectMapper = new ObjectMapper();

		final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
		jacksonDataFormat.setCamelContext(context);
		jacksonDataFormat.setObjectMapper(objectMapper);
		jacksonDataFormat.setUnmarshalType(unmarshalType);
		return jacksonDataFormat;
	}

	@NonNull
	public JaxbDataFormat setupECOSIOXMLFormat()
	{
		final JaxbDataFormat dataFormat = new JaxbDataFormat(ErpelMessageType.class.getPackage().getName());
		dataFormat.setCamelContext(getContext());
		dataFormat.setEncoding(StandardCharsets.UTF_8.name());

		return dataFormat;
	}
}
