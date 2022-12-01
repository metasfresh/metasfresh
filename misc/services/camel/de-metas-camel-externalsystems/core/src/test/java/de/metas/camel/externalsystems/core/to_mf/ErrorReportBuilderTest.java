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

package de.metas.camel.externalsystems.core.to_mf;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.LogMessageRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.core.to_mf.ErrorReportRouteBuilder.ERROR_SEND_LOG_MESSAGE;

public class ErrorReportBuilderTest extends CamelTestSupport
{
	private final static String MOCK_LOG_MESSAGE = "mock:logMessage";
	private final static String JSON_LOG_MESSAGE_REQUEST = "0_LogMessageRequest.json";

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ErrorReportRouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(ErrorReportBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void errorHandlerTest() throws Exception
	{
		this.prepareRouteForTesting();

		context.start();

		//given
		final JsonApiResponse jsonApiResponse = JsonApiResponse.builder()
				.requestId(JsonMetasfreshId.of(100001))
				.build();

		final String jsonApiResponseAsString = JsonObjectMapperHolder
				.sharedJsonObjectMapper()
				.writeValueAsString(jsonApiResponse);

		final HttpOperationFailedException exception = new HttpOperationFailedException("www.does-not-matter.com",
																						500,
																						"TestLogMessage",
																						"Location",
																						null,
																						jsonApiResponseAsString);
		exception.setStackTrace(new StackTraceElement[0]);

		final Exchange exchange = new DefaultExchange(template.getCamelContext());
		exchange.setProperty(Exchange.EXCEPTION_CAUGHT, exception);
		exchange.getIn().setHeader(HEADER_PINSTANCE_ID, 1);

		//expect
		final MockEndpoint logMessageMockEndpoint = getMockEndpoint(MOCK_LOG_MESSAGE);
		final InputStream logRequest = ErrorReportBuilderTest.class.getResourceAsStream(JSON_LOG_MESSAGE_REQUEST);
		final LogMessageRequest logMessageRequest = JsonObjectMapperHolder
				.sharedJsonObjectMapper()
				.readValue(logRequest, LogMessageRequest.class);
		logMessageMockEndpoint.expectedBodiesReceived(logMessageRequest);

		//when
		template.send("direct:" + ERROR_SEND_LOG_MESSAGE, exchange);

		//then
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting() throws Exception
	{
		AdviceWith.adviceWith(context, ERROR_SEND_LOG_MESSAGE,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_LOG_MESSAGE));
	}

}
