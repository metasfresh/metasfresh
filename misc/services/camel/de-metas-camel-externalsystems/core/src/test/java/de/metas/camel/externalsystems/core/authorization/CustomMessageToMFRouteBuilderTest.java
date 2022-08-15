/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.authorization;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.camel.externalsystems.core.CustomRouteController;
import de.metas.camel.externalsystems.core.authorization.provider.MetasfreshAuthProvider;
import de.metas.camel.externalsystems.core.restapi.ExternalSystemRestAPIHandler;
import de.metas.camel.externalsystems.core.to_mf.ErrorReportRouteBuilder;
import de.metas.camel.externalsystems.grssignum.from_grs.hu.ClearHURouteBuilder;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonHUClear;
import de.metas.camel.externalsystems.grssignum.to_grs.client.GRSSignumDispatcherRouteBuilder;
import de.metas.camel.externalsystems.woocommerce.restapi.RestAPIRouteBuilder;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.core.CoreConstants.CUSTOM_TO_MF_ROUTE;
import static de.metas.camel.externalsystems.core.authorization.CustomMessageFromMFRouteBuilder.CUSTOM_MESSAGE_FROM_MF_ROUTE_ID;
import static de.metas.camel.externalsystems.core.authorization.CustomMessageToMFRouteBuilder.CUSTOM_TO_MF_ROUTE_ID;
import static de.metas.camel.externalsystems.core.restapi.ExternalSystemRestAPIHandler.HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.from_grs.hu.ClearHURouteBuilder.CLEAR_HU_ROUTE_ID;
import static de.metas.camel.externalsystems.grssignum.to_grs.client.GRSSignumDispatcherRouteBuilder.GRS_DISPATCHER_ROUTE_ID;
import static de.metas.camel.externalsystems.woocommerce.restapi.RestAPIRouteBuilder.REST_API_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;

public class CustomMessageToMFRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_CLEAR_HU = "mock:clearHURoute";

	private static final String JSON_EXTERNAL_SYSTEM_MESSAGE_RESPONSE = "10_JsonExternalSystemMessage.json";
	private static final String JSON_HU_CLEAR_REQUEST = "20_JsonHUClear.json";
	private static final String JSON_CLEARANCE_STATUS_REQUEST = "30_ClearanceStatusRequest.json";

	private MetasfreshAuthProvider metasfreshAuthProvider;
	private CustomRouteController customRouteController;

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final Properties properties = new Properties();
		try
		{
			properties.load(CustomMessageToMFRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected CamelContext createCamelContext() throws Exception
	{
		final CamelContext camelContext = super.createCamelContext();

		//init
		metasfreshAuthProvider = new MetasfreshAuthProvider();
		customRouteController = new CustomRouteController(camelContext);

		//dev-note: in production code this is covered by org.apache.camel.spring.boot.CamelContextConfiguration.beforeApplicationStart
		camelContext.setAutoStartup(false);

		return camelContext;
	}

	@Override
	protected RoutesBuilder[] createRouteBuilders() throws Exception
	{
		//always running
		final RouteBuilder customMessageToMFRouteBuilder = new CustomMessageToMFRouteBuilder();
		final RouteBuilder customMessageFromMFRouteBuilder = new CustomMessageFromMFRouteBuilder(metasfreshAuthProvider, customRouteController);
		final RouteBuilder errorReportRouteBuilder = new ErrorReportRouteBuilder();

		//normal
		final RouteBuilder clearHURouteBuilder = new ClearHURouteBuilder();
		final RouteBuilder grsSignumDispatcherRouteBuilder = new GRSSignumDispatcherRouteBuilder();

		//start on demand
		final RouteBuilder restAPIRouteBuilder = new RestAPIRouteBuilder();

		//with a start-up order set
		final RouteBuilder externalSystemRestAPIHandler = new ExternalSystemRestAPIHandler(ImmutableList.of((IExternalSystemService)restAPIRouteBuilder));

		return new RoutesBuilder[] {
				customMessageToMFRouteBuilder,
				customMessageFromMFRouteBuilder,
				errorReportRouteBuilder,
				clearHURouteBuilder,
				grsSignumDispatcherRouteBuilder,
				restAPIRouteBuilder,
				externalSystemRestAPIHandler
		};
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	public void happyFlow() throws Exception
	{
		//when
		context.start();

		//dev-note: in production code this is covered by org.apache.camel.spring.boot.CamelContextConfiguration.beforeApplicationStart
		customRouteController.startAlwaysRunningRoutes();

		//given initial state
		// then validate routes have the appropriate "running" status
		validateRoutesWhenNoAuthPresent();

		//given an auth token is received from MF
		// then validate appropriate routes have started in the proper order
		validateMFResponseWithAuthToken();
	}

	@Test
	public void givenAnExchange_whenCallingMFAPI_thenAuthNotifierProvidesAnAuthToken() throws Exception
	{
		//given
		final MockCustomMessageToMFProcessor mockCustomMessageToMFProcessor = new MockCustomMessageToMFProcessor();
		final MockClearHUProcessor mockClearHUProcessor = new MockClearHUProcessor();
		prepareClearHURouteForTesting(mockCustomMessageToMFProcessor, mockClearHUProcessor);

		final MetasfreshAuthorizationTokenNotifier metasfreshAuthorizationTokenNotifier = new MetasfreshAuthorizationTokenNotifier(metasfreshAuthProvider,
																																   "mock://clearHURoute",
																																   customRouteController,
																																   context.createProducerTemplate());
		context.getManagementStrategy().addEventNotifier(metasfreshAuthorizationTokenNotifier);

		context.start();

		//dev-note: in production code this is covered by org.apache.camel.spring.boot.CamelContextConfiguration.beforeApplicationStart
		customRouteController.startAlwaysRunningRoutes();

		validateRoutesWhenNoAuthPresent();
		validateMFResponseWithAuthToken();

		//validate ClearanceStatusRequest call to MF has the appropriate auth token set
		//dev-note: this is 100% dependent on MetasfreshAuthorizationTokenNotifier.metasfreshAPIURL
		final MockEndpoint clearHUMockEP = getMockEndpoint(MOCK_CLEAR_HU);
		final InputStream clearanceStatusReq = this.getClass().getResourceAsStream(JSON_CLEARANCE_STATUS_REQUEST);
		clearHUMockEP.expectedBodiesReceived(JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(clearanceStatusReq, JsonSetClearanceStatusRequest.class));
		clearHUMockEP.expectedHeaderReceived(CoreConstants.AUTHORIZATION, metasfreshAuthProvider.getAuthToken());

		//send JsonHUClear request
		final InputStream requestBodyAsStringIS = this.getClass().getResourceAsStream(JSON_HU_CLEAR_REQUEST);
		final JsonHUClear requestBodyAsJsonHUClear = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(requestBodyAsStringIS, JsonHUClear.class);
		final String requestBodyAsString = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requestBodyAsJsonHUClear);

		//when
		template.sendBody("direct:" + CLEAR_HU_ROUTE_ID, requestBodyAsString);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockCustomMessageToMFProcessor.called).isEqualTo(0);
		assertThat(mockClearHUProcessor.called).isEqualTo(1);
	}

	@Test
	public void givenAnExchange_whenCallingAnythingButMFAPI_thenAuthNotifierDoesntProvideAnAuthToken() throws Exception
	{
		//given
		final MockCustomMessageToMFProcessor mockCustomMessageToMFProcessor = new MockCustomMessageToMFProcessor();
		final MockClearHUProcessor mockClearHUProcessor = new MockClearHUProcessor();
		prepareClearHURouteForTesting(mockCustomMessageToMFProcessor, mockClearHUProcessor);

		final MetasfreshAuthorizationTokenNotifier metasfreshAuthorizationTokenNotifier = new MetasfreshAuthorizationTokenNotifier(metasfreshAuthProvider,
																																   "mock://doesntmatter",
																																   customRouteController,
																																   context.createProducerTemplate());
		context.getManagementStrategy().addEventNotifier(metasfreshAuthorizationTokenNotifier);

		context.start();

		//dev-note: in production code this is covered by org.apache.camel.spring.boot.CamelContextConfiguration.beforeApplicationStart
		customRouteController.startAlwaysRunningRoutes();

		validateRoutesWhenNoAuthPresent();
		validateMFResponseWithAuthToken();

		//validate ClearanceStatusRequest
		final MockEndpoint clearHUMockEP = getMockEndpoint(MOCK_CLEAR_HU);
		clearHUMockEP.expectedHeaderReceived(CoreConstants.AUTHORIZATION, metasfreshAuthProvider.getAuthToken());

		//send JsonHUClear request
		final InputStream requestBodyAsStringIS = this.getClass().getResourceAsStream(JSON_HU_CLEAR_REQUEST);
		final JsonHUClear requestBodyAsJsonHUClear = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(requestBodyAsStringIS, JsonHUClear.class);
		final String requestBodyAsString = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requestBodyAsJsonHUClear);

		//when
		template.sendBody("direct:" + CLEAR_HU_ROUTE_ID, requestBodyAsString);

		//then
		clearHUMockEP.assertIsNotSatisfied();
		assertThat(mockCustomMessageToMFProcessor.called).isEqualTo(0);
		assertThat(mockClearHUProcessor.called).isEqualTo(1);
	}

	@Test
	public void givenAnExchange_whenCallingMFAPI_withNoAuthTokenAvailable_thenShutdownAllRoutesAndRequestAuthToken() throws Exception
	{
		//given
		final MockCustomMessageToMFProcessor mockCustomMessageToMFProcessor = new MockCustomMessageToMFProcessor();
		final MockClearHUProcessor mockClearHUProcessor = new MockClearHUProcessor(true);
		prepareClearHURouteForTesting(mockCustomMessageToMFProcessor, mockClearHUProcessor);

		final MetasfreshAuthorizationTokenNotifier metasfreshAuthorizationTokenNotifier = new MetasfreshAuthorizationTokenNotifier(metasfreshAuthProvider,
																																   "mock://clearHURoute",
																																   customRouteController,
																																   context.createProducerTemplate());
		context.getManagementStrategy().addEventNotifier(metasfreshAuthorizationTokenNotifier);

		context.start();

		//dev-note: org.apache.camel.spring.boot.CamelContextConfiguration.afterApplicationStart
		customRouteController.startAlwaysRunningRoutes();

		validateRoutesWhenNoAuthPresent();
		validateMFResponseWithAuthToken();

		//send JsonHUClear request
		final InputStream requestBodyAsStringIS = this.getClass().getResourceAsStream(JSON_HU_CLEAR_REQUEST);
		final JsonHUClear requestBodyAsJsonHUClear = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(requestBodyAsStringIS, JsonHUClear.class);
		final String requestBodyAsString = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requestBodyAsJsonHUClear);

		//when
		template.sendBody("direct:" + CLEAR_HU_ROUTE_ID, requestBodyAsString);

		//then
		assertThat(mockCustomMessageToMFProcessor.called).isEqualTo(1);
		assertThat(mockClearHUProcessor.called).isEqualTo(1);
		validateRoutesAreStopped();
	}

	private void validateRoutesWhenNoAuthPresent()
	{
		assertThat(metasfreshAuthProvider.getAuthToken()).isNull();

		validateRoutesAreStopped();
	}

	private void validateRoutesAreStopped()
	{
		//always running
		assertThat(context.getRouteController().getRouteStatus(CUSTOM_TO_MF_ROUTE_ID).isStarted()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(CUSTOM_MESSAGE_FROM_MF_ROUTE_ID).isStarted()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(MF_ERROR_ROUTE_ID).isStarted()).isTrue();

		//normal routes
		assertThat(context.getRouteController().getRouteStatus(CLEAR_HU_ROUTE_ID).isStopped()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(GRS_DISPATCHER_ROUTE_ID).isStopped()).isTrue();

		//on demand
		assertThat(context.getRouteController().getRouteStatus(REST_API_ROUTE_ID).isStopped()).isTrue();

		//route order
		assertThat(context.getRouteController().getRouteStatus(HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID).isStopped()).isTrue();
	}

	private void validateMFResponseWithAuthToken() throws Exception
	{
		//given
		final InputStream jsonExternalSystemMessageResponseIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_MESSAGE_RESPONSE);
		final JsonExternalSystemMessage jsonExternalSystemMessageResponse = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(jsonExternalSystemMessageResponseIS, JsonExternalSystemMessage.class);
		final String jsonExternalSystemMessageResponseString = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.writeValueAsString(jsonExternalSystemMessageResponse);

		//when
		template.sendBody("direct:" + CUSTOM_MESSAGE_FROM_MF_ROUTE_ID, jsonExternalSystemMessageResponseString);

		//then
		final JsonExternalSystemMessagePayload payload = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(jsonExternalSystemMessageResponse.getPayload(), JsonExternalSystemMessagePayload.class);

		assertThat(metasfreshAuthProvider.getAuthToken()).isEqualTo(payload.getAuthToken());

		validateRoutesAreStarted();
	}

	private void validateRoutesAreStarted()
	{
		//always running
		assertThat(context.getRouteController().getRouteStatus(CUSTOM_TO_MF_ROUTE_ID).isStarted()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(CUSTOM_MESSAGE_FROM_MF_ROUTE_ID).isStarted()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(MF_ERROR_ROUTE_ID).isStarted()).isTrue();

		//normal routes
		assertThat(context.getRouteController().getRouteStatus(CLEAR_HU_ROUTE_ID).isStarted()).isTrue();
		assertThat(context.getRouteController().getRouteStatus(GRS_DISPATCHER_ROUTE_ID).isStarted()).isTrue();

		//on demand
		assertThat(context.getRouteController().getRouteStatus(REST_API_ROUTE_ID).isStopped()).isTrue();

		//route order
		assertThat(context.getRouteController().getRouteStatus(HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID).isStarted()).isTrue();
	}

	private void prepareClearHURouteForTesting(
			@NonNull final MockCustomMessageToMFProcessor mockCustomMessageToMFRouteProcessor,
			@NonNull final MockClearHUProcessor mockClearHUProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, CUSTOM_TO_MF_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint(CUSTOM_TO_MF_ROUTE)
									  .skipSendToOriginalEndpoint()
									  .process(mockCustomMessageToMFRouteProcessor));

		AdviceWith.adviceWith(context, ClearHURouteBuilder.CLEAR_HU_ROUTE_ID,
							  advice ->
									  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_CLEAR_HU_V2_CAMEL_ROUTE_ID)
											  .skipSendToOriginalEndpoint()
											  .doTry()
											  .process(mockClearHUProcessor)
											  .endDoTry()
											  .doCatch(Throwable.class)
											  .log("prepareClearHURouteForTesting")
											  .end()
											  .to(MOCK_CLEAR_HU));
	}

	private static class MockClearHUProcessor implements Processor
	{
		private boolean throwError;
		private int called = 0;

		public MockClearHUProcessor()
		{
			this(false);
		}

		public MockClearHUProcessor(final boolean throwError)
		{
			this.throwError = throwError;
		}

		@Override
		public void process(final Exchange exchange)
		{
			if (throwError)
			{
				exchange.setException(new HttpOperationFailedException("uri", 401, null, null, null, null));
			}

			called++;
		}
	}

	private static class MockCustomMessageToMFProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
