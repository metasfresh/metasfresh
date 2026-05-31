/*
 * #%L
 * de-metas-camel-scriptedadapter
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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorException;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthAccessToken;
import de.metas.camel.externalsystems.scriptedadapter.oauth.OAuthTokenManager;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentRequest;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelContextConfiguration;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ROUTE_MSG_FROM_MF_CONTEXT;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class ScriptedAdapterConvertMsgFromMFRouteBuilderTests extends CamelTestSupport
{
	private static final String MOCK_ATTACHMENT_ENDPOINT = "mock:AttachmentEndpoint";

	/**
	 * Used to parse and verify the results.
	 */
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	@Override
	public void configureContext(@NonNull final CamelContextConfiguration camelContextConfiguration)
	{
		super.configureContext(camelContextConfiguration);
		testConfiguration().withUseAdviceWith(true);

		final Properties properties = new Properties();
		try
		{
			properties.load(ScriptedAdapterConvertMsgFromMFRouteBuilderTests.class.getClassLoader().getResourceAsStream("application.properties"));
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		camelContextConfiguration.withUseOverridePropertiesWithPropertiesComponent(properties);
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final OAuthTokenManager oauthTokenManager = Mockito.mock(OAuthTokenManager.class);
		Mockito.when(oauthTokenManager.getAccessToken(Mockito.any()))
				.thenReturn(OAuthAccessToken.of("dummy access token", SystemTime.asInstant().plus(24, ChronoUnit.HOURS)));

		return new ScriptedAdapterConvertMsgFromMFRouteBuilder(oauthTokenManager, new SftpDeliveryProcessor());
	}

	@Test
	void executeJavaScriptWithJsonInput() throws Exception
	{
		// Given: A JSON input object
		final String messageFromMetasfresh = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

		// JavaScript that processes the JSON and returns a modified object
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					// Parse the JSON input
					var inputData = JSON.parse(messageFromMetasfresh);
				
					// Process the data
					var result = {
						processed: true,
						originalName: inputData.name,
						ageInMonths: inputData.age * 12,
						location: inputData.city,
						timestamp: new Date().toISOString().substring(0, 10)
					};
				
					// Return as JSON string
					return JSON.stringify(result);
				}
				""";

		final Exchange exchange = prepareScriptAndExchange(jsScript, messageFromMetasfresh);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		mockHttpEndpoint.expectedMessageCount(1);

		context.start();

		// When: Send message to the scripting route
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		// Then: Verify the result
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);

		MockEndpoint.assertIsSatisfied(context);
		final String result = exchange.getIn().getBody(String.class);
		assertThat(result).isNotNull();

		// Parse the result to verify it's valid JSON
		final var resultObject = objectMapper.readTree(result);
		assertThat(resultObject.get("processed").asBoolean()).isTrue();
		assertThat(resultObject.get("originalName").asText()).isEqualTo("John");
		assertThat(resultObject.get("ageInMonths").asInt()).isEqualTo(360);
		assertThat(resultObject.get("location").asText()).isEqualTo("New York");
		assertThat(resultObject.get("timestamp").asText()).matches("\\d{4}-\\d{2}-\\d{2}");
	}

	@Test
	void executeJavaScriptWithSimpleTransformation() throws Exception
	{
		// Given: A simple JSON object
		final String messageFromMetasfresh = "{\"value\":42}";

		// JavaScript that doubles the value
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					var inputData = JSON.parse(messageFromMetasfresh);
					var result = {
						originalValue: inputData.value,
						doubledValue: inputData.value * 2
					};
					return JSON.stringify(result);
				}
				""";

		final Exchange exchange = prepareScriptAndExchange(jsScript, messageFromMetasfresh);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		mockHttpEndpoint.expectedMessageCount(1);

		context.start();

		// When: Send message to the scripting route
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		// Then: Verify the result
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);

		MockEndpoint.assertIsSatisfied(context);
		final String result = exchange.getIn().getBody(String.class);
		final var resultObject = objectMapper.readTree(result);
		assertThat(resultObject.get("originalValue").asInt()).isEqualTo(42);
		assertThat(resultObject.get("doubledValue").asInt()).isEqualTo(84);
	}

	@Test
	void executeJavaScriptWithArrayProcessing() throws Exception
	{
		// Given: JSON with an array
		final String messageFromMetasfresh = "{\"numbers\":[1,2,3,4,5]}";

		// JavaScript that processes the array
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					var inputData = JSON.parse(messageFromMetasfresh);
					var sum = inputData.numbers.reduce(function(acc, num) { return acc + num; }, 0);
					var result = {
						originalArray: inputData.numbers,
						sum: sum,
						count: inputData.numbers.length,
						average: sum / inputData.numbers.length
					};
					return JSON.stringify(result);
				}
				""";

		// When: Send message to the scripting route
		final Exchange exchange = prepareScriptAndExchange(jsScript, messageFromMetasfresh);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		mockHttpEndpoint.expectedMessageCount(1);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);
		MockEndpoint.assertIsSatisfied(context);

		// Then: Verify the result
		final String result = exchange.getIn().getBody(String.class);
		final var resultObject = objectMapper.readTree(result);
		assertThat(resultObject.get("sum").asInt()).isEqualTo(15);
		assertThat(resultObject.get("count").asInt()).isEqualTo(5);
		assertThat(resultObject.get("average").asDouble()).isEqualTo(3.0);
	}

	private MockEndpoint createAndInjectMockHttpEndpoint(@NonNull final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor) throws Exception
	{
		final MockEndpoint mockHttpEndpoint = getMockEndpoint("mock:httpEndPoint");
		AdviceWith.adviceWith(context,
				ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID)
							.replace()
							.to(mockHttpEndpoint);

					advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(MOCK_ATTACHMENT_ENDPOINT)
							.process(mockJsonAttachmentRequestProcessor);
				});

		return mockHttpEndpoint;
	}

	@Test
	void testFaultyJavaScriptInvokesErrorRoute() throws Exception
	{
		// Given: A faulty JavaScript that will throw an error
		final String messageFromMetasfresh = "{\"value\":10}";
		final String scriptWithFaultyMethodName = """
				function transfoorm(messageFromMetasfresh) {
					var result = messageFromMetasfresh;
					return result;
				}
				""";

		final Exchange exchange = prepareScriptAndExchange(scriptWithFaultyMethodName, messageFromMetasfresh);

		final MockEndpoint mockErrorRoute = creatAndInjectMockErrorRoute();
		mockErrorRoute.expectedMessageCount(1); // Expect one message to reach the error route

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		mockHttpEndpoint.expectedMessageCount(0);

		context.start();

		// When: Send message to the scripting route with faulty JavaScript
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		// Then: Verify that the error route was invoked
		MockEndpoint.assertIsSatisfied(context);

		// assert the original exchange has the exception
		final Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		assertThat(exception).isNotNull();
		assertThat(exception).isInstanceOf(JavaScriptExecutorException.class).hasMessageContaining("JavaScript script must define a 'transform' function that is executable.");
	}

	// ========================================================================================
	// ARRAY-MODE C6: fan-out unit-test matrix per PLAN_ARRAY_MODE.md §4.2.
	// The 7 tests below cover the splitOnArrayIfRequested + per-element split branch:
	//   - arrayFanOut=null (disabled) — payload is single object OR array (backward-compat path)
	//   - arrayFanOut=true  — single-object/empty-array (no-op + WARN log) / multi-element happy path
	//                       / multi-element with partial downstream failure / all-failed (aggregate throws)
	// ========================================================================================

	@Test
	void arrayFanOut_disabled_singleObject_payload() throws Exception
	{
		// JS returns a single JSON object. Endpoint has no arrayFanOut flag -> single-request path.
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify({foo: 1});
				}
				""";

		final Exchange exchange = prepareScriptAndExchangeForFanOut(jsScript, "{}", /*arrayFanOut*/ null);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		mockHttpEndpoint.expectedMessageCount(1);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);
	}

	@Test
	void arrayFanOut_disabled_arrayPayload() throws Exception
	{
		// Backward-compat: even when JS returns a JSON array, fan-out is OFF -> single request
		// with the whole array as body. Pre-existing customer scripts that happen to return
		// arrays must keep working unchanged.
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify([{foo: 1}, {foo: 2}]);
				}
				""";

		final Exchange exchange = prepareScriptAndExchangeForFanOut(jsScript, "{}", /*arrayFanOut*/ null);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		mockHttpEndpoint.expectedMessageCount(1);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);
	}

	@Test
	void arrayFanOut_true_singleObject_payload() throws Exception
	{
		// arrayFanOut=true but the JS returns a single object (not an array).
		// splitOnArrayIfRequested logs a WARN and falls through to the single-request branch.
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify({foo: 1});
				}
				""";

		final Exchange exchange = prepareScriptAndExchangeForFanOut(jsScript, "{}", /*arrayFanOut*/ Boolean.TRUE);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		mockHttpEndpoint.expectedMessageCount(1);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);

		// Fan-out exchange properties must NOT be populated (single-request branch).
		assertThat(exchange.getProperty(ScriptedAdapterConvertMsgFromMFRouteBuilder.EXCHANGE_PROPERTY_FAN_OUT_ARRAY)).isNull();
	}

	@Test
	void arrayFanOut_true_emptyArray() throws Exception
	{
		// arrayFanOut=true but the JS returns []. splitOnArrayIfRequested logs a WARN and falls
		// through to the single-request branch -- but since the body is "[]" the downstream call
		// still happens once with the empty array as body (it's the single-request fall-through).
		// We assert zero per-element fan-out exchange properties.
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify([]);
				}
				""";

		final Exchange exchange = prepareScriptAndExchangeForFanOut(jsScript, "{}", /*arrayFanOut*/ Boolean.TRUE);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpoint(mockJsonAttachmentRequestProcessor);
		// Empty array falls through to the single-request branch (we don't dispatch zero requests
		// because the existing route shape preserves the legacy single-call contract on no-op).
		mockHttpEndpoint.expectedMessageCount(1);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);

		// Fan-out exchange properties must NOT be populated -- empty array triggers WARN + no-op.
		assertThat(exchange.getProperty(ScriptedAdapterConvertMsgFromMFRouteBuilder.EXCHANGE_PROPERTY_FAN_OUT_ARRAY)).isNull();
		assertThat(exchange.getProperty(ScriptedAdapterConvertMsgFromMFRouteBuilder.EXCHANGE_PROPERTY_FAN_OUT_TOTAL)).isNull();
	}

	@Test
	void arrayFanOut_true_twoElementArray_allSucceed() throws Exception
	{
		// arrayFanOut=true and JS returns a 2-element array -> TWO downstream HTTP requests +
		// TWO attachment posts back to metasfresh, each with a per-element file name.
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify([{a: 1}, {b: 2}]);
				}
				""";

		final Exchange exchange = prepareScriptAndExchangeForFanOut(jsScript, "{}", /*arrayFanOut*/ Boolean.TRUE);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpointForFanOut(mockJsonAttachmentRequestProcessor, /*failOnIndex*/ -1);
		mockHttpEndpoint.expectedMessageCount(2);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);
		// TWO attachments posted, one per element.
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(2);

		// Attachment file names carry the per-element suffix "-1-of-2", "-2-of-2".
		final List<String> fileNames = mockJsonAttachmentRequestProcessor.attachmentFileNames;
		assertThat(fileNames).hasSize(2);
		assertThat(fileNames.get(0)).endsWith("-1-of-2.txt");
		assertThat(fileNames.get(1)).endsWith("-2-of-2.txt");

		// Aggregate result must record 2 successes / 0 failures.
		final FanOutResult result = exchange.getProperty(
				ScriptedAdapterConvertMsgFromMFRouteBuilder.EXCHANGE_PROPERTY_FAN_OUT_RESULT,
				FanOutResult.class);
		assertThat(result).isNotNull();
		assertThat(result.getSuccessCount()).isEqualTo(2);
		assertThat(result.getFailureCount()).isEqualTo(0);
	}

	@Test
	void arrayFanOut_true_twoElementArray_secondFails() throws Exception
	{
		// arrayFanOut=true with 2 elements; the second downstream call throws. The split must
		// NOT abort -- the first element's success and the second's failure are both recorded
		// on the FanOutResult aggregator. Overall the fan-out is considered successful because
		// at least one element succeeded (only the all-failed case throws).
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify([{a: 1}, {b: 2}]);
				}
				""";

		final Exchange exchange = prepareScriptAndExchangeForFanOut(jsScript, "{}", /*arrayFanOut*/ Boolean.TRUE);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		// Fail on the 2nd HTTP call (0-based index 1).
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpointForFanOut(mockJsonAttachmentRequestProcessor, /*failOnIndex*/ 1);
		mockHttpEndpoint.expectedMessageCount(2);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);
		// Only the first element produces an attachment (the second throws before reaching the
		// attachment processor inside the FanOutIteration sub-route).
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(1);

		// Aggregate must record 1 success + 1 failure; the outer route must NOT have thrown.
		final FanOutResult result = exchange.getProperty(
				ScriptedAdapterConvertMsgFromMFRouteBuilder.EXCHANGE_PROPERTY_FAN_OUT_RESULT,
				FanOutResult.class);
		assertThat(result).isNotNull();
		assertThat(result.getSuccessCount()).isEqualTo(1);
		assertThat(result.getFailureCount()).isEqualTo(1);
		assertThat(result.isAllFailed()).isFalse();
		// No exception escaped to the caller.
		assertThat(exchange.getException()).isNull();
	}

	@Test
	void arrayFanOut_true_allFailed() throws Exception
	{
		// arrayFanOut=true with 3 elements; ALL downstream calls throw. The split continues
		// through every element (continue-on-failure), then finalizeFanOut throws a
		// RuntimeCamelException because every element failed -> caller sees the aggregate error.
		final String jsScript = """
				function transform(messageFromMetasfresh) {
					return JSON.stringify([{a: 1}, {b: 2}, {c: 3}]);
				}
				""";

		final Exchange exchange = prepareScriptAndExchangeForFanOut(jsScript, "{}", /*arrayFanOut*/ Boolean.TRUE);

		final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor = new MockJsonAttachmentRequestProcessor();
		// failOnIndex = Integer.MIN_VALUE -> sentinel meaning "always fail".
		final MockEndpoint mockHttpEndpoint = createAndInjectMockHttpEndpointForFanOut(mockJsonAttachmentRequestProcessor, /*failOnIndex*/ Integer.MIN_VALUE);
		// All 3 elements ARE dispatched to the HTTP endpoint before they throw.
		mockHttpEndpoint.expectedMessageCount(3);

		// Install the mock error route so the onException handler has a consumer for the final
		// aggregate RuntimeCamelException (avoids the secondary "No consumers" exception).
		final MockEndpoint mockErrorRoute = creatAndInjectMockErrorRoute();
		mockErrorRoute.expectedMessageCount(1);

		context.start();
		template.send("direct:" + ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID, exchange);

		MockEndpoint.assertIsSatisfied(context);
		// Zero successful attachments.
		AssertionsForClassTypes.assertThat(mockJsonAttachmentRequestProcessor.called).isEqualTo(0);

		// Aggregate records 0 successes / 3 failures.
		final FanOutResult result = exchange.getProperty(
				ScriptedAdapterConvertMsgFromMFRouteBuilder.EXCHANGE_PROPERTY_FAN_OUT_RESULT,
				FanOutResult.class);
		assertThat(result).isNotNull();
		assertThat(result.getSuccessCount()).isEqualTo(0);
		assertThat(result.getFailureCount()).isEqualTo(3);
		assertThat(result.isAllFailed()).isTrue();

		// The aggregate RuntimeCamelException thrown by finalizeFanOut() must reach the
		// onException handler. Walk the exception chain because the handler may wrap it.
		final Exception caught = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		assertThat(caught).isNotNull();
		assertThat(collectMessages(caught)).anyMatch(msg -> msg.contains("all 3 element(s) failed"));
	}

	/** Walks the cause + suppressed chain so callers can assert on a message anywhere in it. */
	@NonNull
	private static List<String> collectMessages(@NonNull final Throwable root)
	{
		final List<String> messages = new ArrayList<>();
		Throwable current = root;
		while (current != null)
		{
			if (current.getMessage() != null)
			{
				messages.add(current.getMessage());
			}
			for (final Throwable suppressed : current.getSuppressed())
			{
				if (suppressed != null && suppressed.getMessage() != null)
				{
					messages.add(suppressed.getMessage());
				}
			}
			current = current.getCause();
		}
		return messages;
	}

	/**
	 * Like {@link #createAndInjectMockHttpEndpoint} but also routes the FanOutIteration sub-route's
	 * HTTP endpoint to the mock, and optionally makes a specific 0-based call index throw
	 * (to simulate a downstream failure in the middle of the fan-out).
	 *
	 * @param failOnIndex {@code -1} -> never fail; {@code Integer.MIN_VALUE} -> always fail;
	 *                    otherwise the (0-based) call index that should throw.
	 */
	private MockEndpoint createAndInjectMockHttpEndpointForFanOut(
			@NonNull final MockJsonAttachmentRequestProcessor mockJsonAttachmentRequestProcessor,
			final int failOnIndex) throws Exception
	{
		final MockEndpoint mockHttpEndpoint = getMockEndpoint("mock:httpEndPoint");
		final AtomicInteger callIndex = new AtomicInteger(0);

		final Processor httpResponseProcessor = exchange -> {
			final int currentIndex = callIndex.getAndIncrement();
			final boolean shouldFail = failOnIndex == Integer.MIN_VALUE
					|| (failOnIndex >= 0 && currentIndex == failOnIndex);
			if (shouldFail)
			{
				throw new RuntimeException("Simulated downstream failure on call " + currentIndex);
			}
			// Set a 200 response code + a small body so prepareJsonAttachmentRequest has something to encode.
			exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
			exchange.getIn().setBody("OK-" + currentIndex);
		};

		AdviceWith.adviceWith(context,
				ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID,
				advice -> {
					// Single-request path's HTTP endpoint.
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID)
							.replace()
							.to(mockHttpEndpoint)
							.process(httpResponseProcessor);

					advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(MOCK_ATTACHMENT_ENDPOINT)
							.process(mockJsonAttachmentRequestProcessor);
				});

		// The FanOutIteration sub-route is a separate routeId -- advise it independently so the
		// per-element HTTP call also goes to the mock and can fail on demand.
		AdviceWith.adviceWith(context,
				ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_FanOutIteration_ROUTE_ID,
				advice -> {
					advice.weaveById(ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID + "_FANOUT")
							.replace()
							.to(mockHttpEndpoint)
							.process(httpResponseProcessor);

					advice.interceptSendToEndpoint("direct:" + MF_ATTACHMENT_ROUTE_ID)
							.skipSendToOriginalEndpoint()
							.to(MOCK_ATTACHMENT_ENDPOINT)
							.process(mockJsonAttachmentRequestProcessor);
				});

		return mockHttpEndpoint;
	}

	/**
	 * Builds an exchange whose endpoint parameters include the {@code arrayFanOut} flag.
	 * When {@code arrayFanOut} is {@code null} the field is omitted from the JSON (matches the
	 * @JsonInclude(NON_NULL) on the DTO -> wire-format backward-compat).
	 */
	@NonNull
	private Exchange prepareScriptAndExchangeForFanOut(
			@NonNull final String jsScript,
			@NonNull final String messageFromMetasfresh,
			final Boolean arrayFanOut)
	{
		final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
		javaScriptRepo.save("testScript", jsScript);

		// Manually embed the optional flag to keep the JSON close to the existing test fixtures.
		final String arrayFanOutLine = arrayFanOut == null ? "" : ",\n  \"arrayFanOut\" : " + arrayFanOut;

		final Exchange exchange = new DefaultExchange(template.getCamelContext());
		exchange.getIn().setBody(
				JsonExternalSystemRequest.builder()
						.orgCode("orgCode")
						.externalSystemName(JsonExternalSystemName.of("externalSystemName"))
						.command("command")
						.externalSystemConfigId(JsonMetasfreshId.of(1))
						.traceId("traceId")
						.externalSystemChildConfigValue("externalSystemChildConfigValue")
						.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh)
						.parameter(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, "testScript")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, """
								{
								  "value" : "value",
								  "endpointUrl" : "http://localhost:8080/test",
								  "method" : "POST",
								  "authType" : "Token",
								  "clientId" : "clientId",
								  "clientSecret" : "clientSecret",
								  "token" : "API_TOKEN",
								  "user" : "user",
								  "password" : "password"%s
								}""".formatted(arrayFanOutLine))
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "TableName")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "123")
						.build());

		return exchange;
	}

	private MockEndpoint creatAndInjectMockErrorRoute() throws Exception
	{
		// Define a mock endpoint to assert whether the error route was invoked
		final MockEndpoint mockErrorRoute = getMockEndpoint("mock:errorRoute");

		// The onException handler is tricky to advise directly.
		// Instead, we add a new route in our test that consumes from the error endpoint
		// and redirects any incoming messages to our mock.
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId("mock-error-handler-for-test") // Unique ID for clarity
						.to(mockErrorRoute);
			}
		});
		return mockErrorRoute;
	}

	@NonNull
	private Exchange prepareScriptAndExchange(
			@NonNull final String jsScript,
			@NonNull final String messageFromMetasfresh)
	{
		final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
		javaScriptRepo.save("testScript", jsScript);

		final Exchange exchange = new DefaultExchange(template.getCamelContext());
		exchange.getIn().setBody(
				JsonExternalSystemRequest.builder()
						.orgCode("orgCode")
						.externalSystemName(JsonExternalSystemName.of("externalSystemName"))
						.command("command")
						.externalSystemConfigId(JsonMetasfreshId.of(1))
						.traceId("traceId")
						.externalSystemChildConfigValue("externalSystemChildConfigValue")
						.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT, messageFromMetasfresh)
						.parameter(PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER, "testScript")

						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_ENDPOINT_PARAMETERS, """
								{
								  "value" : "value",
								  "endpointUrl" : "http://localhost:8080/test",
								  "method" : "POST",
								  "authType" : "OAuth",
								  "clientId" : "clientId",
								  "clientSecret" : "clientSecret",
								  "token" : "API_TOKEN",
								  "user" : "user",
								  "password" : "password"
								}""")

						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "TableName")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "123")
						.build());

		return exchange;
	}

	private static class MockJsonAttachmentRequestProcessor implements Processor
	{
		private int called = 0;
		private final List<String> attachmentFileNames = new ArrayList<>();

		@Override
		public void process(@NonNull final Exchange exchange)
		{
			called++;
			// Capture the attachment file name before we overwrite the body -- the fan-out tests
			// assert on the "-{i}-of-{n}.txt" suffix added by buildAttachmentFileName().
			final JsonAttachmentRequest jsonAttachmentRequest = exchange.getIn().getBody(JsonAttachmentRequest.class);
			if (jsonAttachmentRequest != null && jsonAttachmentRequest.getAttachment() != null)
			{
				attachmentFileNames.add(jsonAttachmentRequest.getAttachment().getFileName());
			}

			final MsgFromMfContext msgFromMfContext = ProcessorHelper.getPropertyOrThrowError(exchange,
					ROUTE_MSG_FROM_MF_CONTEXT,
					MsgFromMfContext.class);
			exchange.getIn().setBody(msgFromMfContext.getScriptReturnValue());
		}
	}
}