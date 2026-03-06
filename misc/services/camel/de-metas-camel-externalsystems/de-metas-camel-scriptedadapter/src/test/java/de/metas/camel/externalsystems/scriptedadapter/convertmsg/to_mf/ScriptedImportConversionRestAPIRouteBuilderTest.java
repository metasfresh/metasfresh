/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.auth.JsonAuthenticateRequest;
import de.metas.camel.externalsystems.common.auth.JsonExpireTokenResponse;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
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
import java.util.Properties;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ScriptedImportConversionRestAPIRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID = "mock:Core-registerTokenRoute";
	private static final String MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID = "mock:Core-expireTokenRoute";
	private static final String MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID = "mock:Core-storeExternalStatus";

	private static final String EXTERNAL_SYSTEM_REQUEST = "1_ExternalSystemRequest.json";
	private static final String JSON_AUTHENTICATE_REQUEST = "10_JsonAuthenticateRequest.json";
	private static final String JSON_EXPIRE_TOKEN_RESPONSE = "20_JsonExpireTokenResponse.json";
	private static final String EXTERNAL_STATUS_ACTIVE_CAMEL_REQUEST = "30_ExternalStatusCreateCamelRequestActive.json";
	private static final String EXTERNAL_STATUS_INACTIVE_CAMEL_REQUEST = "30_ExternalStatusCreateCamelRequestInactive.json";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final ProducerTemplate producerTemplate = Mockito.mock(ProducerTemplate.class);
		return new ScriptedImportConversionRestAPIRouteBuilder(producerTemplate);
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
			properties.load(ScriptedImportConversionRestAPIRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
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

		final MockAuthenticateTokenEP mockAuthenticateTokenEP = new MockAuthenticateTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareEnableRouteForTesting(mockAuthenticateTokenEP, mockStoreExternalStatusEP);

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

		final String endpointName = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);

		// when fire the route
		template.sendBody("direct:" + ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		MockEndpoint.assertIsSatisfied(context);
		assertThat(mockAuthenticateTokenEP.called).isEqualTo(1);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);

		Assertions.assertThat(context.getRouteController().getRouteStatus(endpointName).isStarted()).isTrue();

	}

	@Test
	void disableRestAPI() throws Exception
	{

		final MockExpireTokenEP mockExpireTokenEP = new MockExpireTokenEP();
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		prepareDisableRouteForTesting(mockExpireTokenEP, mockStoreExternalStatusEP);

		context.start();

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

		final String endpointName = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);
		final String scriptIdentifier = invokeExternalSystemRequest.getParameters().get(PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER);

		context.addRoutes(new ScriptedImportConversionDynamicRouteBuilder(endpointName, scriptIdentifier, new JavaScriptRepo("baseDir"), new JavaScriptExecutorService(), template));
		context.getRouteController().startRoute(endpointName);

		//when fire the route
		template.sendBody("direct:" + ScriptedImportConversionRestAPIRouteBuilder.DISABLE_RESOURCE_ROUTE_ID, invokeExternalSystemRequest);

		// then
		MockEndpoint.assertIsSatisfied(context);
		assertThat(mockExpireTokenEP.called).isEqualTo(1);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);

		assertThat(context.getRouteController().getRouteStatus(endpointName).isSuspended()).isTrue();

	}

	private void prepareEnableRouteForTesting(@NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockAuthenticateTokenEP mockAuthenticateTokenEP,
											  @NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.ENABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_REST_API_AUTHENTICATE_TOKEN_ROUTE_ID);

					advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_AUTHENTICATE_TOKEN)
							.skipSendToOriginalEndpoint()
							.process(mockAuthenticateTokenEP);

					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.ENABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockStoreExternalStatusEP);
				});
	}

	private void prepareDisableRouteForTesting(@NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockExpireTokenEP mockExpireTokenEP,
											   @NonNull final ScriptedImportConversionRestAPIRouteBuilderTest.MockStoreExternalStatusEP mockStoreExternalStatusEP) throws Exception
	{
		AdviceWith.adviceWith(context, ScriptedImportConversionRestAPIRouteBuilder.DISABLE_RESOURCE_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.DISABLE_RESOURCE_ATTACH_AUTHENTICATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_REST_API_EXPIRE_TOKEN_ROUTE_ID);

					advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.REST_API_EXPIRE_TOKEN)
							.skipSendToOriginalEndpoint()
							.process(mockExpireTokenEP);

					advice.weaveById(ScriptedImportConversionRestAPIRouteBuilder.DISABLE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
							.after()
							.to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockStoreExternalStatusEP);
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

	private static class MockStoreExternalStatusEP implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
