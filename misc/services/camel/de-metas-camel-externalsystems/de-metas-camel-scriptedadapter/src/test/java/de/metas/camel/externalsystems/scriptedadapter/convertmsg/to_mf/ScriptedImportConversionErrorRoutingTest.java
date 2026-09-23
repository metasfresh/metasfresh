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

package de.metas.camel.externalsystems.scriptedadapter.convertmsg.to_mf;

import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Regression test for the defect where a rejected dispatch of an item to its resolved Camel endpoint
 * (the downstream endpoint throws — e.g. an {@code HttpOperationFailedException}) was silently absorbed
 * by {@link AbstractScriptedImportConversionArchivingRouteBuilder#handleItemInList}: the exception was
 * logged and turned into an error-message BODY, but never rethrown, so the route's trailing
 * {@code .process(this::archiveLocallyOnSuccess)} filed the payload under PROCESSED — a rejected request
 * was reported to the operator as a success, and the remote original was already consumed.
 * <p>
 * Exercises the shared {@code handleItemInList}/archiving behaviour via
 * {@link ScriptedImportConversionDynamicRouteBuilder} (the transport-agnostic base the SFTP and REST
 * transports both extend). Registers a dummy destination route for
 * {@code onException(...).to(direct(MF_ERROR_ROUTE_ID))} — the same pattern
 * {@code InboundSftpIntegrationTest#registerDummyErrorRoute()} and
 * {@code ScriptedImportConversionRestAPIRouteBuilderTest#registerDummyErrorRoute()} already use for the
 * same reason: Camel's {@code interceptSendToEndpoint} does not intercept a send that originates from
 * inside an {@code onException(...)} clause, only ones on the route's main flow, so a real consumer is
 * needed for {@code direct:Error-Route} or the send fails with {@code DirectConsumerNotAvailableException}.
 */
public class ScriptedImportConversionErrorRoutingTest extends CamelTestSupport
{
	private static final String MOCK_ENDPOINT_NAME = "errorRoutingTestEndpoint";
	private static final String MOCK_SCRIPT_IDENTIFIER = "mock:scriptIdentifier";
	private static final String MOCK_SCRIPT = "mock:script.js";
	private static final String MOCK_ERROR_ROUTE_URI = "mock:mfErrorRoute";

	/** Same fixture {@link ScriptedImportConversionDynamicRouteBuilderTest} uses for one valid item —
	 * a real, resolvable {@code camelServiceRouteID} ({@code To-MF_PushOLCandidates-Route}), so the
	 * failure this test injects (below) is unambiguously the DISPATCH to the resolved endpoint, not an
	 * unrelated route-id-resolution failure. */
	private static final String JSON_ONE_VALID_ITEM_SCRIPT_RESPONSE = "1_OneValidItem_ScriptResponse.json";

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
	void rejectedDispatch_landsInErrorArchive_notProcessed() throws Exception
	{
		// A real consumer for the route's onException(...).to(direct(MF_ERROR_ROUTE_ID)) send, forwarding
		// to a mock endpoint for assertion (see class javadoc for why interceptSendToEndpoint won't do here).
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId(MF_ERROR_ROUTE_ID)
						.to(MOCK_ERROR_ROUTE_URI);
			}
		});

		context.start();

		Mockito.when(javaScriptRepo.get(MOCK_SCRIPT_IDENTIFIER)).thenReturn(MOCK_SCRIPT);

		final String inputPayload = "{\"orderId\":\"error-routing-test\"}";

		final InputStream jsonOneValidItemScriptResponse = this.getClass().getResourceAsStream(JSON_ONE_VALID_ITEM_SCRIPT_RESPONSE);
		assertThat(jsonOneValidItemScriptResponse).isNotNull();
		final String scriptResponse = new String(jsonOneValidItemScriptResponse.readAllBytes(), StandardCharsets.UTF_8);

		Mockito.when(javaScriptExecutorService.executeScript(MOCK_SCRIPT_IDENTIFIER, MOCK_SCRIPT, inputPayload))
				.thenReturn(scriptResponse);

		// The downstream endpoint rejects the request (e.g. an HttpOperationFailedException wrapped by Camel)
		Mockito.when(producerTemplate.requestBody(anyString(), any(), any()))
				.thenThrow(new RuntimeCamelException("rejected by endpoint"));

		final MockEndpoint mockErrorRoute = getMockEndpoint(MOCK_ERROR_ROUTE_URI);
		mockErrorRoute.expectedMessageCount(1);

		// The route's onException is not marked handled(), so the failure also propagates back to this
		// synchronous send — that propagation is not what this test is about, only the archiving outcome is.
		try
		{
			template.sendBody("direct:" + MOCK_ENDPOINT_NAME, inputPayload);
		}
		catch (final RuntimeException ignored)
		{
			// expected: the rejected dispatch fails the exchange all the way back to the caller
		}

		mockErrorRoute.assertIsSatisfied();

		// The rejected request must be archived to the LOCAL error folder, carrying the original payload...
		final List<Path> errorFiles;
		try (var files = Files.list(localErrorDir))
		{
			errorFiles = files.toList();
		}
		assertThat(errorFiles).hasSize(1);
		assertThat(Files.readString(errorFiles.get(0), StandardCharsets.UTF_8)).isEqualTo(inputPayload);

		// ... and must NOT be filed as a success under processed.
		try (var files = Files.list(localProcessedDir))
		{
			assertThat(files.findAny()).isEmpty();
		}
	}
}
