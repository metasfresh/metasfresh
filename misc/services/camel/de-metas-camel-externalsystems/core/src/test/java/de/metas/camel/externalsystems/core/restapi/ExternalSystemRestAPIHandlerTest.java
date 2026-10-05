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

package de.metas.camel.externalsystems.core.restapi;

import com.google.common.collect.ImmutableList;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonExternalStatusResponse;
import de.metas.common.externalsystem.status.JsonExternalStatusResponseItem;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalSystemRestAPIHandlerTest extends CamelTestSupport
{
	private static final String STATUS_URI = "direct:test-service-status";
	private static final String INVOKE_URI = "mock:test-invoke-action";

	private Object statusResponse;
	private final List<Exception> errors = new ArrayList<>();

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		properties.setProperty("metasfresh.service-status-v2.camel.uri", STATUS_URI);
		properties.setProperty("metasfresh.invoke-external-system-action-v2.camel.uri", INVOKE_URI);
		return properties;
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ExternalSystemRestAPIHandler(ImmutableList.of(new TestExternalSystemService()));
	}

	@Override
	protected RouteBuilder[] createRouteBuilders()
	{
		return new RouteBuilder[] { createRouteBuilder(), new StubRoutes() };
	}

	private class StubRoutes extends RouteBuilder
	{
		@Override
		public void configure()
		{
			from(STATUS_URI).process(exchange -> exchange.getIn().setBody(statusResponse));
			from("direct:Error-Route").process(exchange -> errors.add(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class)));
		}
	}

	@Test
	void nullServiceStatusResponse_noServiceEnabled_noError() throws Exception
	{
		statusResponse = null;

		runRouteAndExpectInvocations(0);

		assertThat(errors).isEmpty();
	}

	@Test
	void jsonNullServiceStatusResponse_noServiceEnabled_noError() throws Exception
	{
		// what UnpackV2 passes on for a JsonApiResponse without endpointResponse
		statusResponse = "null";

		runRouteAndExpectInvocations(0);

		assertThat(errors).isEmpty();
	}

	@Test
	void onlyActiveServicesAreEnabled() throws Exception
	{
		statusResponse = JsonExternalStatusResponse.builder()
				.externalStatusResponses(ImmutableList.of(
						item("activeChild", JsonExternalStatus.Active),
						item("inactiveChild", JsonExternalStatus.Inactive)))
				.build();

		final MockEndpoint invoke = runRouteAndExpectInvocations(1);

		assertThat(errors).isEmpty();
		assertThat(invoke.getReceivedExchanges()).hasSize(1);
	}

	private MockEndpoint runRouteAndExpectInvocations(final int expectedCount) throws Exception
	{
		AdviceWith.adviceWith(context, ExternalSystemRestAPIHandler.HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID,
							  a -> a.replaceFromWith("direct:start"));
		final MockEndpoint invoke = getMockEndpoint(INVOKE_URI);
		invoke.expectedMessageCount(expectedCount);
		context.start();

		template.sendBody("direct:start", "start");

		invoke.assertIsSatisfied();
		return invoke;
	}

	private static JsonExternalStatusResponseItem item(final String childValue, final JsonExternalStatus status)
	{
		return JsonExternalStatusResponseItem.builder()
				.externalSystemChildValue(childValue)
				.externalSystemConfigType("configType")
				.serviceValue("testService")
				.expectedStatus(status)
				.build();
	}

	private static class TestExternalSystemService implements IExternalSystemService
	{
		@Override
		public String getServiceValue() {return "testService";}

		@Override
		public String getExternalSystemTypeCode() {return "TestType";}

		@Override
		public String getEnableCommand() {return "enableTest";}

		@Override
		public String getDisableCommand() {return "disableTest";}
	}
}
