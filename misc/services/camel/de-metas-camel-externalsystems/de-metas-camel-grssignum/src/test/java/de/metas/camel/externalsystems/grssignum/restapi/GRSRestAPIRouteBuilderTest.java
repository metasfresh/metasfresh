/*
 * #%L
 * de-metas-camel-externalsystems
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

package de.metas.camel.externalsystems.grssignum.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;

import static de.metas.camel.externalsystems.grssignum.restapi.GRSRestAPIRouteBuilder.DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.grssignum.restapi.GRSRestAPIRouteBuilder.DISABLE_RESOURCE_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.restapi.GRSRestAPIRouteBuilder.ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.grssignum.restapi.GRSRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.restapi.GRSRestAPIRouteBuilder.REST_API_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GRSRestAPIRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID = "mock:Core-registerTokenRoute";
	private static final String MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID = "mock:Core-expireTokenRoute";

	private static final String EXTERNAL_SYSTEM_REQUEST = "1_ExternalSystemRequest.json";
	private static final String JSON_AUTHENTICATE_REQUEST = "10_JsonAuthenticateRequest.json";
	private static final String JSON_EXPIRE_TOKEN_RESPONSE = "20_JsonExpireTokenResponse.json";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final ProducerTemplate producerTemplate = Mockito.mock(ProducerTemplate.class);
		return new GRSRestAPIRouteBuilder(producerTemplate);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void enableRestAPI() throws Exception
	{

		final MockAuthenticateTokenEP mockAuthenticateTokenEP = new MockAuthenticateTokenEP();

		prepareEnableRouteForTesting(mockAuthenticateTokenEP);

		context.start();

		final MockEndpoint registerRouteMockEP = getMockEndpoint(MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID);
		final InputStream jsonAuthenticateReqIS = this.getClass().getResourceAsStream(JSON_AUTHENTICATE_REQUEST);
		final JsonAuthenticateRequest jsonAuthenticateReq = objectMapper.readValue(jsonAuthenticateReqIS, JsonAuthenticateRequest.class);
		registerRouteMockEP.expectedBodiesReceived(jsonAuthenticateReq);

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		// when fire the route
		template.sendBody("direct:" + ENABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		assertMockEndpointsSatisfied();
		assertThat(mockAuthenticateTokenEP.called).isEqualTo(1);

		Assertions.assertThat(context.getRouteController().getRouteStatus(REST_API_ROUTE_ID).isStarted()).isTrue();

	}

	@Test
	void disableRestAPI() throws Exception
	{

		final MockExpireTokenEP mockExpireTokenEP = new MockExpireTokenEP();

		prepareDisableRouteForTesting(mockExpireTokenEP);

		context.start();

		final MockEndpoint expireRouteMockEP = getMockEndpoint(MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID);

		final InputStream jsonAuthenticateReqIS = this.getClass().getResourceAsStream(JSON_AUTHENTICATE_REQUEST);
		final JsonAuthenticateRequest jsonAuthenticateReq = objectMapper.readValue(jsonAuthenticateReqIS, JsonAuthenticateRequest.class);
		expireRouteMockEP.expectedBodiesReceived(jsonAuthenticateReq);

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when fire the route
		template.sendBody("direct:" + DISABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		assertMockEndpointsSatisfied();
		assertThat(mockExpireTokenEP.called).isEqualTo(1);

		assertThat(context.getRouteController().getRouteStatus(REST_API_ROUTE_ID).isSuspended()).isTrue();

	}

	private void prepareEnableRouteForTesting(@NonNull final GRSRestAPIRouteBuilderTest.MockAuthenticateTokenEP mockAuthenticateTokenEP) throws Exception
	{
		AdviceWith.adviceWith(context, ENABLE_RESOURCE_ROUTE_ID,
							  advice -> {
								  advice.weaveById(ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN)
										  .skipSendToOriginalEndpoint()
										  .process(mockAuthenticateTokenEP);
							  });
	}

	private void prepareDisableRouteForTesting(@NonNull final GRSRestAPIRouteBuilderTest.MockExpireTokenEP mockExpireTokenEP) throws Exception
	{
		AdviceWith.adviceWith(context, DISABLE_RESOURCE_ROUTE_ID,
							  advice -> {
								  advice.weaveById(DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN)
										  .skipSendToOriginalEndpoint()
										  .process(mockExpireTokenEP);
							  });
	}

	private static class MockAuthenticateTokenEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockExpireTokenEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			final InputStream jsonExpireTokenRes = this.getClass().getResourceAsStream(JSON_EXPIRE_TOKEN_RESPONSE);
			final JsonExpireTokenResponse jsonExpireTokenResponse = objectMapper.readValue(jsonExpireTokenRes
					, JsonExpireTokenResponse.class);

			exchange.getIn().setBody(jsonExpireTokenResponse);

			called++;
		}
	}
}
