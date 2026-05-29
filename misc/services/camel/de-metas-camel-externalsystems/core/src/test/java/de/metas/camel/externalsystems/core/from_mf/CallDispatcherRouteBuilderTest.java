/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.core.from_mf;

import de.metas.camel.externalsystems.common.ProcessLogger;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelContextConfiguration;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.TestSocketUtils;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class CallDispatcherRouteBuilderTest extends CamelTestSupport
{
	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new CallDispatcherRouteBuilder(Mockito.mock(ProcessLogger.class));
	}

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration);

		testConfiguration().withUseAdviceWith(true);

		final Properties properties = new Properties();
		try
		{
			final int appServerPort = TestSocketUtils.findAvailableTcpPort();

			properties.load(CallDispatcherRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));

			final String port = Integer.toString(appServerPort);
			properties.setProperty("server.port", port);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		camelContextConfiguration.withUseOverridePropertiesWithPropertiesComponent(properties);
	}

	/**
	 * Verifies that a request is touted to a dynamic endpoint that is according to the request's externalSystem and command.
	 */
	@Test
	void callEndpoint() throws Exception
	{
		final var postEndpoint = new ExternalSystemEndpoint();
		final var command = "myCommand";
		final var externalSystem = "myExternalSystem";

		// Mock the RabbitMQ route by replacing it with a direct endpoint
		AdviceWith.adviceWith(context,
				CallDispatcherRouteBuilder.FROM_MF_ROUTE_ID, // The route ID from CallDispatcherRouteBuilder
				a -> a.replaceFromWith("direct:test-RabbitMQ_from_MF_ID"));

		AdviceWith.adviceWith(context,
				CallDispatcherRouteBuilder.DISPATCH_ROUTE_ID,
				a -> a.interceptSendToEndpoint("direct:" + externalSystem + "-" + command)
						.skipSendToOriginalEndpoint()
						.process(postEndpoint));

		context.start();

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("direct:" + externalSystem + "-" + command)
				.whenDone(1).create();

		final String jsonRequest = "{\"externalSystemConfigId\":1, \"orgCode\":\"orgCode\",\"traceId\":\"traceId\",\"externalSystemName\":\"" + externalSystem + "\",\"command\":\"" + command + "\",\"externalSystemChildConfigValue\":\"childValue\",\"parameters\":{\"parameterName1\":\"parameterValue1\",\"parameterName2\":\"parameterValue2\"}}";

		template.requestBody("direct:dispatch", jsonRequest);

		assertThat(notify.matchesWaitTime()).isTrue();
		assertThat(postEndpoint.called).isEqualTo(1);
	}

	private static class ExternalSystemEndpoint implements Processor
	{
		private int called = 0;
		private String messageBody;

		@Override
		public void process(final Exchange exchange)
		{
			messageBody = exchange.getIn().getBody(String.class);
			called++;
		}
	}
}