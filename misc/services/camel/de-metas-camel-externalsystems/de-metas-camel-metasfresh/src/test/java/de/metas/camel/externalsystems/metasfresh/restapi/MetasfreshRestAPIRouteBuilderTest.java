/*
 * #%L
 * de-metas-camel-metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.metasfresh.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.metasfresh.restapi.feedback.FeedbackConfigProvider;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.metasfresh.restapi.MetasfreshRestAPIRouteBuilder.DISABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.metasfresh.restapi.MetasfreshRestAPIRouteBuilder.DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.metasfresh.restapi.MetasfreshRestAPIRouteBuilder.DISABLE_RESOURCE_ROUTE_ID;
import static de.metas.camel.externalsystems.metasfresh.restapi.MetasfreshRestAPIRouteBuilder.ENABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.metasfresh.restapi.MetasfreshRestAPIRouteBuilder.ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.metasfresh.restapi.MetasfreshRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID;
import static de.metas.camel.externalsystems.metasfresh.restapi.MetasfreshRestAPIRouteBuilder.REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MetasfreshRestAPIRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID = "mock:Core-registerTokenRoute";
	private static final String MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID = "mock:Core-expireTokenRoute";
	private static final String MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID = "mock:Core-storeExternalStatus";

	private static final String EXTERNAL_SYSTEM_REQUEST = "1_ExternalSystemRequest.json";
	private static final String JSON_AUTHENTICATE_REQUEST = "10_JsonAuthenticateRequest.json";
	private static final String EXTERNAL_STATUS_ACTIVE_CAMEL_REQUEST = "20_ExternalStatusCreateCamelRequestActive.json";
	private static final String EXTERNAL_STATUS_INACTIVE_CAMEL_REQUEST = "20_ExternalStatusCreateCamelRequestInactive.json";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final FeedbackConfigProvider feedbackConfigProvider = Mockito.mock(FeedbackConfigProvider.class);

		return new MetasfreshRestAPIRouteBuilder(feedbackConfigProvider);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(MetasfreshRestAPIRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	@Test
	void enableRestAPI() throws Exception
	{
		final MockEnableTokenEP mockEnableTokenEP = new MockEnableTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareEnableRouteForTesting(mockEnableTokenEP, mockStoreExternalStatusEP);

		context.start();

		final MockEndpoint registerRouteMockEP = getMockEndpoint(MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID);
		final InputStream jsonAuthenticateReqIS = this.getClass().getResourceAsStream(JSON_AUTHENTICATE_REQUEST);
		final JsonAuthenticateRequest jsonAuthenticateReq = objectMapper.readValue(jsonAuthenticateReqIS, JsonAuthenticateRequest.class);
		registerRouteMockEP.expectedBodiesReceived(jsonAuthenticateReq);

		final MockEndpoint storeStatusMockEP = getMockEndpoint(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);
		final InputStream jsonExternalStatusReqIS = this.getClass().getResourceAsStream(EXTERNAL_STATUS_ACTIVE_CAMEL_REQUEST);
		final ExternalStatusCreateCamelRequest jsonExternalStatusReq = objectMapper.readValue(jsonExternalStatusReqIS, ExternalStatusCreateCamelRequest.class);
		storeStatusMockEP.expectedBodiesReceived(jsonExternalStatusReq);
		
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		// when fire the route
		template.sendBody("direct:" + ENABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		assertMockEndpointsSatisfied();
		assertThat(mockEnableTokenEP.called).isEqualTo(1);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);

		Assertions.assertThat(context.getRouteController().getRouteStatus(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID).isStarted()).isTrue();
	}

	@Test
	void disableRestAPI() throws Exception
	{
		// given
		final MockExpireTokenEP mockExpireTokenEP = new MockExpireTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareDisableRouteForTesting(mockExpireTokenEP, mockStoreExternalStatusEP);

		context.start();
		context.getRouteController().resumeRoute(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID);

		Assertions.assertThat(context.getRouteController().getRouteStatus(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID).isStarted()).isTrue();

		final MockEndpoint expireRouteMockEP = getMockEndpoint(MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID);
		final InputStream jsonAuthenticateReqIS = this.getClass().getResourceAsStream(JSON_AUTHENTICATE_REQUEST);
		final JsonAuthenticateRequest jsonAuthenticateReq = objectMapper.readValue(jsonAuthenticateReqIS, JsonAuthenticateRequest.class);
		expireRouteMockEP.expectedBodiesReceived(jsonAuthenticateReq);

		final MockEndpoint storeStatusMockEP = getMockEndpoint(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);
		final InputStream jsonExternalStatusReqIS = this.getClass().getResourceAsStream(EXTERNAL_STATUS_INACTIVE_CAMEL_REQUEST);
		final ExternalStatusCreateCamelRequest jsonExternalStatusReq = objectMapper.readValue(jsonExternalStatusReqIS, ExternalStatusCreateCamelRequest.class);
		storeStatusMockEP.expectedBodiesReceived(jsonExternalStatusReq);
		
		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = objectMapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when fire the route
		template.sendBody("direct:" + DISABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		assertMockEndpointsSatisfied();
		assertThat(mockExpireTokenEP.called).isEqualTo(1);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);

		assertThat(context.getRouteController().getRouteStatus(REST_API_WRITE_REQUEST_BODY_TO_FILE_ROUTE_ID).isSuspended()).isTrue();
	}

	private void prepareEnableRouteForTesting(
			@NonNull final MetasfreshRestAPIRouteBuilderTest.MockEnableTokenEP mockEnableTokenEP,
			@NonNull final MetasfreshRestAPIRouteBuilderTest.MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, ENABLE_RESOURCE_ROUTE_ID,
							  advice -> {
								  advice.weaveById(ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN)
										  .skipSendToOriginalEndpoint()
										  .process(mockEnableTokenEP);

								  advice.weaveById(ENABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockStoreExternalStatusEP);
							  });
	}

	private void prepareDisableRouteForTesting(
			@NonNull final MetasfreshRestAPIRouteBuilderTest.MockExpireTokenEP mockExpireTokenEP,
			@NonNull final MetasfreshRestAPIRouteBuilderTest.MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, DISABLE_RESOURCE_ROUTE_ID,
							  advice -> {
								  advice.weaveById(DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN)
										  .skipSendToOriginalEndpoint()
										  .process(mockExpireTokenEP);

								  advice.weaveById(DISABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(mockStoreExternalStatusEP);
							  });
	}

	private static class MockEnableTokenEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockStoreExternalStatusEP implements Processor
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
			exchange.getIn().setBody(JsonExpireTokenResponse.builder().numberOfAuthenticatedTokens(0).build());
			called++;
		}
	}
}
