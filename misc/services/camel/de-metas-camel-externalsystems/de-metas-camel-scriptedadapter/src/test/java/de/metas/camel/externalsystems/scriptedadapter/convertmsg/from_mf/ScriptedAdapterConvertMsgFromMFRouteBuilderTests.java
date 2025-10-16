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
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorException;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
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
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.ROUTE_MSG_FROM_MF_CONTEXT;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_OUTBOUND_HTTP_EP_ID;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.ScriptedExportConversion_ConvertMsgFromMF_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_EP;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_METHOD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_TOKEN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_FROM_MF_METASFRESH_INPUT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_JAVASCRIPT_IDENTIFIER;
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
		return new ScriptedAdapterConvertMsgFromMFRouteBuilder(Mockito.mock(ProcessLogger.class));
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
						.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_EP, "http://localhost:8080/test")
						.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_TOKEN, "API_TOKEN")
						.parameter(PARAM_SCRIPTEDADAPTER_FROM_MF_HTTP_METHOD, "POST")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_TABLE_NAME, "TableName")
						.parameter(PARAM_SCRIPTEDADAPTER_OUTBOUND_RECORD_ID, "123")
						.build());

		return exchange;
	}

	private static class MockJsonAttachmentRequestProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(@NonNull final Exchange exchange)
		{
			called++;
			final MsgFromMfContext msgFromMfContext = ProcessorHelper.getPropertyOrThrowError(exchange,
					ROUTE_MSG_FROM_MF_CONTEXT,
					MsgFromMfContext.class);
			exchange.getIn().setBody(msgFromMfContext.getScriptReturnValue());
		}
	}
}