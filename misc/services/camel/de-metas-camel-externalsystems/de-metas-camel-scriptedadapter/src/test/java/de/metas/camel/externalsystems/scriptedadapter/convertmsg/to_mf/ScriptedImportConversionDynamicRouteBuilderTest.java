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
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.FIELD_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

	/**
	 * A two-item transform result using a real, resolvable {@code camelServiceRouteID} (see
	 * {@link CamelServiceRouteIdWithRequestType}) — so a failure a test injects is unambiguously the
	 * DISPATCH to the resolved endpoint, not an unrelated route-id-resolution failure.
	 */
	private static final String TWO_VALID_ITEMS_SCRIPT_RESPONSE = "["
			+ "{ \"camelServiceRouteID\": \"To-MF_PushOLCandidates-Route\", \"requestBody\": \"{\\\"requests\\\":[]}\"},"
			+ "{ \"camelServiceRouteID\": \"To-MF_PushOLCandidates-Route\", \"requestBody\": \"{\\\"requests\\\":[]}\"}"
			+ "]";

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	private final JavaScriptRepo javaScriptRepo = Mockito.mock(JavaScriptRepo.class);
	private final JavaScriptExecutorService javaScriptExecutorService = Mockito.mock(JavaScriptExecutorService.class);
	private final ProducerTemplate producerTemplate = Mockito.spy(ProducerTemplate.class);

	@TempDir
	Path localProcessedDir;

	@TempDir
	Path localErrorDir;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		return new ScriptedImportConversionDynamicRouteBuilder(
				MOCK_ENDPOINT_NAME,
				MOCK_SCRIPT_IDENTIFIER,
				javaScriptRepo,
				javaScriptExecutorService,
				producerTemplate,
				localProcessedDir.toAbsolutePath().toString(),
				localErrorDir.toAbsolutePath().toString());
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

		Mockito.when(javaScriptExecutorService.executeScript(any(), any(), any()))
				.thenReturn(TWO_VALID_ITEMS_SCRIPT_RESPONSE);

		Mockito.when(producerTemplate.requestBody(anyString(), any(), any()))
				.thenReturn("{\"result\":1}");

		@SuppressWarnings("unchecked") final List<Object> aggregatedResult =
				template.requestBody("direct:" + MOCK_ENDPOINT_NAME, "ignored", List.class);

		assertThat(aggregatedResult).hasSize(2);
	}

	/**
	 * A rejected dispatch is reported to the caller as that item's extracted error message — the per-item
	 * outcome the callers of this route (the REST endpoint in particular) classify to decide their own
	 * answer. That error message reaching the response is therefore a contract, NOT the item silently
	 * passing as a success: the run's own verdict is the archive folder, asserted here too.
	 */
	@Test
	void whenDispatchIsRejected_errorMessageIsReportedPerItemAndPayloadLandsInErrorDir() throws Exception
	{
		registerDummyErrorRoute();

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
				template.requestBody("direct:" + MOCK_ENDPOINT_NAME, jsonOneValidItemScriptResponseAsString, List.class);

		assertThat(result).containsExactly("Exception - exception");

		final List<Path> errorFiles;
		try (var files = java.nio.file.Files.list(localErrorDir))
		{
			errorFiles = files.toList();
		}
		assertThat(errorFiles).hasSize(1);

		try (var processedFiles = java.nio.file.Files.list(localProcessedDir))
		{
			assertThat(processedFiles.findAny()).isEmpty();
		}
	}

	/**
	 * A script emitting several items must have EVERY item attempted, even when an earlier item's
	 * dispatch is rejected: the per-item outcome is what the caller is told about, so item 1 failing may
	 * not silently cancel items 2..N. The split is configured {@code stopOnException()}, so this only
	 * holds as long as a rejected dispatch is recorded rather than thrown out of the per-item step.
	 */
	@Test
	void whenFirstItemFails_remainingItemsAreStillDispatched() throws Exception
	{
		registerDummyErrorRoute();

		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER))
				.thenReturn(MOCK_SCRIPT);

		Mockito.when(javaScriptExecutorService.executeScript(any(), any(), any()))
				.thenReturn(TWO_VALID_ITEMS_SCRIPT_RESPONSE);

		Mockito.when(producerTemplate.requestBody(anyString(), any(), any()))
				.thenThrow(new RuntimeCamelException("first item rejected"))
				.thenReturn("{\"result\":1}");

		@SuppressWarnings("unchecked") final List<Object> aggregatedResult =
				template.requestBody("direct:" + MOCK_ENDPOINT_NAME, "ignored", List.class);

		// the second item was dispatched although the first one had already failed ...
		verify(producerTemplate, times(2)).requestBody(anyString(), any(), any());

		// ... and both outcomes are reported per item: the failure as its extracted error message, the
		// success as its parsed response.
		assertThat(aggregatedResult).containsExactly("Exception - first item rejected", Map.of("result", 1));
	}

	/**
	 * The flip side of {@link #whenFirstItemFails_remainingItemsAreStillDispatched()}: continuing with the
	 * remaining items must not make the run count as a success. A run in which ANY item was rejected
	 * belongs in the error folder — filing it under processed would tell the operator the payload was
	 * imported when part of it was not, while the source has already been consumed.
	 */
	@Test
	void whenOneOfSeveralItemsFails_payloadIsArchivedToErrorDirNotProcessed() throws Exception
	{
		registerDummyErrorRoute();

		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER))
				.thenReturn(MOCK_SCRIPT);

		final String inputPayload = "{\"orderId\":\"partial-failure-test\"}";

		Mockito.when(javaScriptExecutorService.executeScript(MOCK_SCRIPT_IDENTIFIER, MOCK_SCRIPT, inputPayload))
				.thenReturn(TWO_VALID_ITEMS_SCRIPT_RESPONSE);

		Mockito.when(producerTemplate.requestBody(anyString(), any(), any()))
				.thenThrow(new RuntimeCamelException("first item rejected"))
				.thenReturn("{\"result\":1}");

		template.sendBody("direct:" + MOCK_ENDPOINT_NAME, inputPayload);

		final List<Path> errorFiles;
		try (var files = java.nio.file.Files.list(localErrorDir))
		{
			errorFiles = files.toList();
		}
		assertThat(errorFiles).hasSize(1);
		assertThat(java.nio.file.Files.readString(errorFiles.get(0), StandardCharsets.UTF_8)).isEqualTo(inputPayload);

		try (var processedFiles = java.nio.file.Files.list(localProcessedDir))
		{
			assertThat(processedFiles.findAny()).isEmpty();
		}
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

	@Test
	void successfulProcessing_archivesOriginalPayloadToLocalProcessedDir() throws Exception
	{
		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER)).thenReturn(MOCK_SCRIPT);

		final String inputPayload = "{\"orderId\":\"local-archive-test\"}";

		Mockito.when(javaScriptExecutorService.executeScript(MOCK_SCRIPT_IDENTIFIER, MOCK_SCRIPT, inputPayload))
				.thenReturn("[]");

		template.sendBody("direct:" + MOCK_ENDPOINT_NAME, inputPayload);

		final List<Path> archivedFiles;
		try (var files = java.nio.file.Files.list(localProcessedDir))
		{
			archivedFiles = files.toList();
		}
		assertThat(archivedFiles).hasSize(1);
		assertThat(java.nio.file.Files.readString(archivedFiles.get(0), StandardCharsets.UTF_8)).isEqualTo(inputPayload);

		try (var errorFiles = java.nio.file.Files.list(localErrorDir))
		{
			assertThat(errorFiles.findAny()).isEmpty();
		}
	}

	@Test
	void successfulProcessing_archivesNonAsciiPayloadByteIdenticalAsUtf8() throws Exception
	{
		// regression guard: this route now carries the archived payload as explicit UTF-8 bytes
		// (rather than an implicitly-converted String); a non-ASCII payload proves the archived
		// file is byte-identical to the old Files.writeString(..., UTF_8) behaviour, not merely
		// String-equal after a round-trip decode.
		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER)).thenReturn(MOCK_SCRIPT);

		final String inputPayload = "{\"customer\":\"Müller & Söhne\",\"note\":\"日本語 café €\"}";

		Mockito.when(javaScriptExecutorService.executeScript(MOCK_SCRIPT_IDENTIFIER, MOCK_SCRIPT, inputPayload))
				.thenReturn("[]");

		template.sendBody("direct:" + MOCK_ENDPOINT_NAME, inputPayload);

		final List<Path> archivedFiles;
		try (var files = java.nio.file.Files.list(localProcessedDir))
		{
			archivedFiles = files.toList();
		}
		assertThat(archivedFiles).hasSize(1);
		assertThat(java.nio.file.Files.readAllBytes(archivedFiles.get(0)))
				.isEqualTo(inputPayload.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * A real consumer for the route's {@code onException(...).to(direct(MF_ERROR_ROUTE_ID))} send.
	 * Camel's {@code interceptSendToEndpoint} does not intercept a send originating inside an
	 * {@code onException(...)} clause, only ones on the route's main flow, so without a real consumer
	 * such a send fails with {@code DirectConsumerNotAvailableException} — which would mask whatever
	 * failure the test actually injected.
	 */
	private void registerDummyErrorRoute() throws Exception
	{
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId(MF_ERROR_ROUTE_ID)
						.log("Error route invoked (test)");
			}
		});
	}
}
