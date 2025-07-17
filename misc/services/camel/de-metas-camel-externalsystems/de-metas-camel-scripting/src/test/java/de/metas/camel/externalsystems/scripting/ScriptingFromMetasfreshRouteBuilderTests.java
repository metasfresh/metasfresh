/*
 * #%L
 * de-metas-camel-scripting
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

package de.metas.camel.externalsystems.scripting;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.common.ProcessLogger;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelContextConfiguration;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scripting.ScriptingFromMetasfreshRouteBuilder.PROPERTY_JAVASCRIPT_IDENTIFIER;
import static de.metas.camel.externalsystems.scripting.ScriptingFromMetasfreshRouteBuilder.PROPERTY_METASFRESH_INPUT;
import static de.metas.camel.externalsystems.scripting.ScriptingFromMetasfreshRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static de.metas.camel.externalsystems.scripting.ScriptingFromMetasfreshRouteBuilder.Scripting_ROUTE_ID;
import static org.assertj.core.api.Assertions.assertThat;

public class ScriptingFromMetasfreshRouteBuilderTests extends CamelTestSupport
{
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
			properties.load(ScriptingFromMetasfreshRouteBuilderTests.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new ScriptingFromMetasfreshRouteBuilder(Mockito.mock(ProcessLogger.class));
	}

	@Test
	void executeJavaScriptWithJsonInput() throws Exception
	{
		context.start();

		// Given: A JSON input object
		final String metasfreshInput = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

		// JavaScript that processes the JSON and returns a modified object
		final String jsScript = """
				// Parse the JSON input
				var inputData = JSON.parse(metasfreshInput);
				
				// Process the data
				var result = {
					processed: true,
					originalName: inputData.name,
					ageInMonths: inputData.age * 12,
					location: inputData.city,
					timestamp: new Date().toISOString().substring(0, 10)
				};
				
				// Return as JSON string
				JSON.stringify(result);
				""";

		// When: Send message to the scripting route
		final Exchange exchange = prepareScriptAndExchange(jsScript, metasfreshInput);

		template.send("direct:" + Scripting_ROUTE_ID, exchange);

		// Then: Verify the result
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
		context.start();

		// Given: A simple JSON object
		final String metasfreshInput = "{\"value\":42}";

		// JavaScript that doubles the value
		final String jsScript = """
				var inputData = JSON.parse(metasfreshInput);
				var result = {
					originalValue: inputData.value,
					doubledValue: inputData.value * 2
				};
				JSON.stringify(result);
				""";

		// When: Send message to the scripting route
		final Exchange exchange = prepareScriptAndExchange(jsScript, metasfreshInput);

		template.send("direct:" + Scripting_ROUTE_ID, exchange);

		// Then: Verify the result
		final String result = exchange.getIn().getBody(String.class);
		final var resultObject = objectMapper.readTree(result);
		assertThat(resultObject.get("originalValue").asInt()).isEqualTo(42);
		assertThat(resultObject.get("doubledValue").asInt()).isEqualTo(84);
	}

	@Test
	void executeJavaScriptWithArrayProcessing() throws Exception
	{
		context.start();

		// Given: JSON with an array
		final String metasfreshInput = "{\"numbers\":[1,2,3,4,5]}";

		// JavaScript that processes the array
		final String jsScript = """
				var inputData = JSON.parse(metasfreshInput);
				var sum = inputData.numbers.reduce(function(acc, num) { return acc + num; }, 0);
				var result = {
					originalArray: inputData.numbers,
					sum: sum,
					count: inputData.numbers.length,
					average: sum / inputData.numbers.length
				};
				JSON.stringify(result);
				""";

		// When: Send message to the scripting route
		final Exchange exchange = prepareScriptAndExchange(jsScript, metasfreshInput);

		template.send("direct:" + Scripting_ROUTE_ID, exchange);

		// Then: Verify the result
		final String result = exchange.getIn().getBody(String.class);
		final var resultObject = objectMapper.readTree(result);
		assertThat(resultObject.get("sum").asInt()).isEqualTo(15);
		assertThat(resultObject.get("count").asInt()).isEqualTo(5);
		assertThat(resultObject.get("average").asDouble()).isEqualTo(3.0);
	}

	@Test
	void executeJavaScriptReturnsPrimitiveValue() throws Exception
	{
		// Given: JSON input
		final String jsonInput = "{\"count\":5}";

		// JavaScript that returns a primitive value
		final String jsScript = """
				var inputData = JSON.parse(metasfreshInput);
				inputData.count * 10;
				""";

		context.start();

		// When: Send message to the scripting route
		final Exchange exchange = prepareScriptAndExchange(jsScript, jsonInput);

		template.send("direct:" + Scripting_ROUTE_ID, exchange);

		// Then: Verify the result
		final Object result = exchange.getIn().getBody();
		assertThat(result).isEqualTo(50);
	}

	@Test
	void testFaultyJavaScriptInvokesErrorRoute() throws Exception
	{
		// Given: A faulty JavaScript that will throw an error
		final String metasfreshInput = "{\"value\":10}";
		final String faultyJsScript = """
				// This will cause a ReferenceError because 'undefinedVariable' is not defined
				var result = undefinedVariable;
				""";

		// Define a mock endpoint to assert that the error route was invoked
		final MockEndpoint mockErrorRoute = getMockEndpoint("mock:errorRoute");
		mockErrorRoute.expectedMessageCount(1); // Expect one message to reach the error route

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

		context.start();

		final Exchange exchange = prepareScriptAndExchange(faultyJsScript, metasfreshInput);

		// When: Send message to the scripting route with faulty JavaScript
		template.send("direct:" + Scripting_ROUTE_ID, exchange);

		// Then: Verify that the error route was invoked
		MockEndpoint.assertIsSatisfied(context);

		// assert the original exchange has the exception
		final Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		assertThat(exception).isNotNull();
		assertThat(exception).isInstanceOf(JavaScriptExecutorException.class).hasMessageContaining("scpriptIdentifier=testScript; errorMsg=ReferenceError: undefinedVariable is not defined");
	}

	@NonNull
	private Exchange prepareScriptAndExchange(
			@NonNull final String jsScript,
			@NonNull final String metasfreshInput)
	{
		final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(context.resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
		javaScriptRepo.save("testScript", jsScript);

		final Exchange exchange = new DefaultExchange(template.getCamelContext());
		exchange.setProperty(PROPERTY_METASFRESH_INPUT, metasfreshInput);
		exchange.setProperty(PROPERTY_JAVASCRIPT_IDENTIFIER, "testScript");

		return exchange;
	}
}