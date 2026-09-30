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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
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
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.ErrorReportRouteBuilder.ERROR_SEND_LOG_MESSAGE;
import static de.metas.camel.externalsystems.core.to_mf.ErrorReportRouteBuilder.ERROR_WRITE_TO_FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorReportBuilderTest extends CamelTestSupport
{
	private final static String MOCK_LOG_MESSAGE = "mock:logMessage";
	private final static String MOCK_ERROR_FILE = "mock:errorFile";
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

	@Test
	void errorWithoutPInstanceId_isWrittenToFile_andSkipsLogMessage() throws Exception
	{
		this.prepareRouteForTesting();
		this.prepareErrorFileRouteForTesting();

		context.start();

		final MockEndpoint fileMock = getMockEndpoint(MOCK_ERROR_FILE);
		fileMock.expectedMessageCount(1);
		final MockEndpoint logMessageMock = getMockEndpoint(MOCK_LOG_MESSAGE);
		logMessageMock.expectedMessageCount(0);

		final Exchange exchange = newErrorExchange(null);

		template.send("direct:" + MF_ERROR_ROUTE_ID, exchange);

		assertThat(exchange.getException()).isNull();
		assertThat(fileMock.getReceivedExchanges().get(0).getIn().getBody(String.class)).contains("startup failure");
		assertMockEndpointsSatisfied();
	}

	@Test
	void logMessageLegHandlesMissingPInstanceIdWithoutThrowing() throws Exception
	{
		this.prepareRouteForTesting();

		context.start();

		final MockEndpoint logMessageMock = getMockEndpoint(MOCK_LOG_MESSAGE);
		logMessageMock.expectedMessageCount(0);

		final Exchange exchange = newErrorExchange(null);

		template.send("direct:" + ERROR_SEND_LOG_MESSAGE, exchange);

		assertThat(exchange.getException()).isNull();
		assertMockEndpointsSatisfied();
	}

	@Test
	void errorWithoutPInstanceId_isLoggedAsWarning() throws Exception
	{
		this.prepareRouteForTesting();

		context.start();

		final Logger rootLogger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		final ListAppender<ILoggingEvent> appender = new ListAppender<>();
		appender.start();
		rootLogger.addAppender(appender);
		try
		{
			template.send("direct:" + ERROR_SEND_LOG_MESSAGE, newErrorExchange(null));
		}
		finally
		{
			rootLogger.detachAppender(appender);
		}

		assertThat(appender.list)
				.filteredOn(event -> event.getLevel() == Level.WARN)
				.filteredOn(event -> event.getFormattedMessage().contains("No PInstanceId available; reporting error without pInstance linkage"))
				.singleElement()
				.satisfies(event -> assertThat(event.getFormattedMessage()).contains("startup failure"));
	}

	@Test
	void errorWithPInstanceId_isSentAsLogMessage() throws Exception
	{
		this.prepareRouteForTesting();
		this.prepareErrorFileRouteForTesting();

		context.start();

		final MockEndpoint fileMock = getMockEndpoint(MOCK_ERROR_FILE);
		fileMock.expectedMessageCount(1);
		final MockEndpoint logMessageMock = getMockEndpoint(MOCK_LOG_MESSAGE);
		logMessageMock.expectedMessageCount(1);

		final Exchange exchange = newErrorExchange(1);

		template.send("direct:" + MF_ERROR_ROUTE_ID, exchange);

		assertMockEndpointsSatisfied();
	}

	private Exchange newErrorExchange(final Integer pInstanceId)
	{
		final Exchange exchange = new DefaultExchange(context);
		exchange.setProperty(Exchange.EXCEPTION_CAUGHT, new RuntimeException("startup failure"));
		if (pInstanceId != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, pInstanceId);
		}
		return exchange;
	}

	private void prepareErrorFileRouteForTesting() throws Exception
	{
		AdviceWith.adviceWith(context, ERROR_WRITE_TO_FILE,
							  advice -> advice.interceptSendToEndpoint("file:*")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_ERROR_FILE));
	}

	private void prepareRouteForTesting() throws Exception
	{
		AdviceWith.adviceWith(context, ERROR_SEND_LOG_MESSAGE,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_LOG_MESSAGE));
	}

}
