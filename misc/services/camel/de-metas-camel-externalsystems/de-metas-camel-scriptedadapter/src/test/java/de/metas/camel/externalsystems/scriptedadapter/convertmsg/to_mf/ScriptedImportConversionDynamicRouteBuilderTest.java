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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.CamelServiceRouteIdWithRequestType;
import de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf.model.ScriptedImportedConversionToMfRequest;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.FIELD_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class ScriptedImportConversionDynamicRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_ENDPOINT_NAME = "mock:endpointName";
	private static final String MOCK_SCRIPT_IDENTIFIER = "mock:scriptIdentifier";
	private static final String MOCK_SCRIPT = "mock:script.js";

	private static final String JSON_ONE_VALID_ITEM_SCRIPT_RESPONSE = "1_OneValidItem_ScriptResponse.json";
	private static final String JSON_ONE_VALID_ITEM_ENDPOINT_RESPONSE = "1_OneValidItem_EndpointResponse.json";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	private final JavaScriptRepo javaScriptRepo = Mockito.mock(JavaScriptRepo.class);
	private final JavaScriptExecutorService javaScriptExecutorService = Mockito.mock(JavaScriptExecutorService.class);
	private final ProducerTemplate producerTemplate = Mockito.spy(ProducerTemplate.class);

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ScriptedImportConversionDynamicRouteBuilder(
				MOCK_ENDPOINT_NAME,
				MOCK_SCRIPT_IDENTIFIER,
				javaScriptRepo,
				javaScriptExecutorService,
				producerTemplate);
	}

	@Test
	void handleOneSuccessfulItem() throws Exception
	{
		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER))
				.thenReturn(MOCK_SCRIPT);

		final InputStream jsonOneValidItemScriptResponse = this.getClass().getResourceAsStream(JSON_ONE_VALID_ITEM_SCRIPT_RESPONSE);
		assertThat(jsonOneValidItemScriptResponse).isNotNull();
		final String jsonOneValidItemScriptResponseAsString = new String(jsonOneValidItemScriptResponse.readAllBytes(), StandardCharsets.UTF_8);

		final List<ScriptedImportedConversionToMfRequest> requests = objectMapper.readValue(jsonOneValidItemScriptResponseAsString, new TypeReference<>() {});

		Mockito.when(javaScriptExecutorService.executeScript(MOCK_SCRIPT_IDENTIFIER, MOCK_SCRIPT, jsonOneValidItemScriptResponseAsString))
				.thenReturn(jsonOneValidItemScriptResponseAsString);

		final InputStream jsonOneValidItemEndpointResponse = this.getClass().getResourceAsStream(JSON_ONE_VALID_ITEM_ENDPOINT_RESPONSE);
		assertThat(jsonOneValidItemEndpointResponse).isNotNull();
		final String jsonOneValidItemEndpointResponseAsString = new String(jsonOneValidItemEndpointResponse.readAllBytes(), StandardCharsets.UTF_8);

		Mockito.when(producerTemplate.requestBody(anyString(), any(), any()))
				.thenReturn(jsonOneValidItemEndpointResponseAsString);

		// when fire the route
		template.sendBody("direct:" + MOCK_ENDPOINT_NAME, jsonOneValidItemScriptResponseAsString);

		// then
		MockEndpoint.assertIsSatisfied(context);

		final ArgumentCaptor<Object> bodyCaptor = ArgumentCaptor.forClass(Object.class);
		verify(producerTemplate, times(1))
				.requestBody(anyString(), bodyCaptor.capture(), any());

		final Object capturedBody = bodyCaptor.getValue();
		final CamelServiceRouteIdWithRequestType camelRouteIdWithRequestType =
				CamelServiceRouteIdWithRequestType.ofRouteId(requests.get(0).getCamelServiceRouteID());

		assertThat(capturedBody).isInstanceOf(camelRouteIdWithRequestType.getRequestType());
	}

	@Test
	void handleMultipleItems()
	{
		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER))
				.thenReturn(MOCK_SCRIPT);

		final String multiJson = "["
				+ "{ \"camelServiceRouteID\": \"Route1\", \"requestBody\": \"{}\"},"
				+ "{ \"camelServiceRouteID\": \"Route1\", \"requestBody\": \"{}\"}"
				+ "]";

		Mockito.when(javaScriptExecutorService.executeScript(any(), any(), any()))
				.thenReturn(multiJson);

		Mockito.when(producerTemplate.requestBody(anyString(), any(), any()))
				.thenReturn("{\"result\":1}");

		@SuppressWarnings("unchecked") final List<Object> aggregatedResult =
				template.requestBody("direct:" + MOCK_ENDPOINT_NAME, "ignored", List.class);

		assertThat(aggregatedResult).hasSize(2);
	}

	@Test
	void whenExceptionThrown_exceptionIsReturnedAsBody() throws IOException
	{
		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER))
				.thenReturn(MOCK_SCRIPT);

		final InputStream jsonOneValidItemScriptResponse = this.getClass().getResourceAsStream(JSON_ONE_VALID_ITEM_SCRIPT_RESPONSE);
		assertThat(jsonOneValidItemScriptResponse).isNotNull();
		final String jsonOneValidItemScriptResponseAsString = new String(jsonOneValidItemScriptResponse.readAllBytes(), StandardCharsets.UTF_8);

		Mockito.when(javaScriptExecutorService.executeScript(any(), any(), any()))
				.thenReturn(jsonOneValidItemScriptResponseAsString);

		Mockito.when(producerTemplate.requestBody(anyString(), any(), any()))
				.thenThrow(new RuntimeCamelException("exception"));

		@SuppressWarnings("unchecked") final List<Object> result =
				template.requestBody("direct:" + MOCK_ENDPOINT_NAME, JSON_ONE_VALID_ITEM_SCRIPT_RESPONSE, List.class);

		assertThat(result.get(0)).isEqualTo("Exception - exception");
	}

	@Test
	void aggregationStrategy_handlesNonJsonResponse()
	{
		final ScriptedImportConversionDynamicRouteBuilder.ResponseAggregationStrategy s =
				new ScriptedImportConversionDynamicRouteBuilder.ResponseAggregationStrategy();

		final Exchange e1 = new DefaultExchange(context);
		e1.getIn().setBody("{\"valid\":true}");

		final Exchange e2 = new DefaultExchange(context);
		e2.getIn().setBody("not_json");

		final Exchange result = s.aggregate(s.aggregate(null, e1), e2);

		@SuppressWarnings("unchecked") final List<Object> list = result.getIn().getBody(List.class);

		assertThat(list).hasSize(2);
		assertThat(list.get(0)).isEqualTo(Map.of("valid", true));
		assertThat(list.get(1)).isEqualTo(Map.of(FIELD_ERROR_MESSAGE, "Unrecognized token 'not_json': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\n"
				+ " at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 9]"));
	}

	@Test
	void whenBodyIsNull_nothingIsProcessed()
	{
		context.start();

		template.sendBody("direct:" + MOCK_ENDPOINT_NAME, null);

		verifyNoInteractions(javaScriptRepo, javaScriptExecutorService, producerTemplate);
	}
}
