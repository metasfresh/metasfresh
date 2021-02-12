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

package de.metas.camel.externalsystems.core.from_mf;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class CallDispatcherRouteBuilderTest extends CamelTestSupport
{
	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new CallDispatcherRouteBuilder();
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			final int appServerPort = SocketUtils.findAvailableTcpPort(8080);

			properties.load(CallDispatcherRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));

			final String port = Integer.toString(appServerPort);
			properties.setProperty("server.port", port);
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Verifies that a request is touted to a dinamic endpoint that is according to the request's externalSystem and command.
	 */
	@Test
	void callEndpoint() throws Exception
	{
		final var postEndpoint = new ExternalSystemEndpoint();
		final var command = "myCommand";
		final var externalSystem = "myExternalSystem";
		
		AdviceWith.adviceWith(context,
				CallDispatcherRouteBuilder.ROUTE_ID,
				a -> a.interceptSendToEndpoint("direct:" + externalSystem + "-" + command)
						.skipSendToOriginalEndpoint()
						.process(postEndpoint));

		final NotifyBuilder notify = new NotifyBuilder(context)
				.wereSentTo("direct:" + externalSystem + "-" + command)
				.whenDone(1).create();

		final String jsonRequest = "{\"orgCode\":\"orgCode\",\"externalSystemName\":\"" + externalSystem + "\",\"command\":\"" + command + "\",\"parameters\":{\"parameterName1\":\"parameterValue1\",\"parameterName2\":\"parameterValue2\"}}";

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