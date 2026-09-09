/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.camel.externalsystems.common.LogMessageRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.http.common.HttpMethods;
import org.apache.camel.test.junit5.CamelContextConfiguration;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.ErrorReportRouteBuilder.ERROR_SEND_LOG_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Covers the "Error-Route-sendLogMessage" and "Error-Route-writeToADIssue" sub-routes of {@link ErrorReportRouteBuilder}.
 * <p>
 * A per-file exchange failure that carries no {@code HEADER_PINSTANCE_ID} (e.g. a continuous polling
 * consumer that never attaches a PInstance to the exchange) must degrade gracefully instead of throwing
 * inside the error-reporting route itself, which would mask the original error.
 */
class ErrorReportRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_LOG_MESSAGE_ROUTE = "mock:LogMessageRoute";
	private static final String MOCK_AD_ISSUE_ROUTE = "mock:ADIssueEndpoint";

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ErrorReportRouteBuilder();
	}

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration);

		testConfiguration().withUseAdviceWith(true);

		final Properties properties = new Properties();
		try
		{
			properties.load(ErrorReportRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		camelContextConfiguration.withUseOverridePropertiesWithPropertiesComponent(properties);
	}

	private void prepareRouteForTesting() throws Exception
	{
		AdviceWith.adviceWith(context, ERROR_SEND_LOG_MESSAGE,
				advice -> advice.interceptSendToEndpoint("direct:" + MF_LOG_MESSAGE_ROUTE_ID)
						.skipSendToOriginalEndpoint()
						.to(MOCK_LOG_MESSAGE_ROUTE));
	}

	private void prepareADIssueRouteForTesting() throws Exception
	{
		// the toD endpoint is resolved (property placeholders + header) before being matched by
		// interceptSendToEndpoint, so the pattern must use the already-resolved base URL
		AdviceWith.adviceWith(context, ERROR_WRITE_TO_ADISSUE,
				advice -> advice.interceptSendToEndpoint("http://localhost:8282/*")
						.skipSendToOriginalEndpoint()
						.to(MOCK_AD_ISSUE_ROUTE));
	}

	@Test
	void givenNoPInstanceId_whenSendLogMessage_thenNoExceptionThrown_andNothingForwardedToLogMessageRoute() throws Exception
	{
		prepareRouteForTesting();
		context.start();

		final MockEndpoint logMessageEP = getMockEndpoint(MOCK_LOG_MESSAGE_ROUTE);
		logMessageEP.expectedMessageCount(0);

		// no HEADER_PINSTANCE_ID set on the exchange -> must degrade gracefully instead of throwing
		assertThatCode(() -> template.sendBody("direct:" + ERROR_SEND_LOG_MESSAGE, "some error body"))
				.doesNotThrowAnyException();

		logMessageEP.assertIsSatisfied();
	}

	@Test
	void givenPInstanceId_whenSendLogMessage_thenLogMessageForwardedAsBefore() throws Exception
	{
		prepareRouteForTesting();
		context.start();

		final MockEndpoint logMessageEP = getMockEndpoint(MOCK_LOG_MESSAGE_ROUTE);
		logMessageEP.expectedMessageCount(1);

		final Map<String, Object> headers = new HashMap<>();
		headers.put(HEADER_PINSTANCE_ID, 42);

		template.sendBodyAndHeaders("direct:" + ERROR_SEND_LOG_MESSAGE, "some error body", headers);

		logMessageEP.assertIsSatisfied();

		final LogMessageRequest logMessageRequest = logMessageEP.getExchanges().get(0).getIn().getBody(LogMessageRequest.class);
		assertThat(logMessageRequest.getPInstanceId().getValue()).isEqualTo(42);
	}

	@Test
	void givenNoPInstanceId_whenWriteToADIssue_thenNoExceptionThrown_andNothingForwardedToADIssueEndpoint() throws Exception
	{
		prepareADIssueRouteForTesting();
		context.start();

		final MockEndpoint adIssueEP = getMockEndpoint(MOCK_AD_ISSUE_ROUTE);
		adIssueEP.expectedMessageCount(0);

		// no HEADER_PINSTANCE_ID set on the exchange -> must degrade gracefully instead of throwing
		assertThatCode(() -> template.sendBody("direct:" + ERROR_WRITE_TO_ADISSUE, "some error body"))
				.doesNotThrowAnyException();

		adIssueEP.assertIsSatisfied();
	}

	@Test
	void givenPInstanceId_whenWriteToADIssue_thenRequestForwardedAsBefore() throws Exception
	{
		prepareADIssueRouteForTesting();
		context.start();

		final MockEndpoint adIssueEP = getMockEndpoint(MOCK_AD_ISSUE_ROUTE);
		adIssueEP.expectedMessageCount(1);

		final Map<String, Object> headers = new HashMap<>();
		headers.put(HEADER_PINSTANCE_ID, 42);

		template.sendBodyAndHeaders("direct:" + ERROR_WRITE_TO_ADISSUE, "some error body", headers);

		adIssueEP.assertIsSatisfied();

		final Exchange forwardedExchange = adIssueEP.getExchanges().get(0);
		assertThat(forwardedExchange.getIn().getHeader(Exchange.HTTP_METHOD)).isEqualTo(HttpMethods.POST);
		assertThat(forwardedExchange.getIn().getBody(String.class)).contains("No error message available!");
	}
}
