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

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.scriptedadapter.sftp.EmbeddedSftpServer;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-to-end integration test for inbound SFTP polling via the scripted adapter.
 * <p>
 * Starts an embedded SFTP server, drops a test file into /inbound, enables the SFTP polling route,
 * waits for the file to be picked up and processed, then verifies the remote file was CONSUMED
 * (deleted — no remote mkdir, no remote {@code .done}/{@code .error} move) and the payload archived to a
 * LOCAL processed/error folder. Finally disables the route and verifies clean shutdown.
 * <p>
 * Covers three scenarios:
 * <ul>
 *     <li>{@link #sftpFilePolledConsumedAndArchivedLocally()} — a trivial (no-op) JavaScript transform
 *     that returns an empty array, focused on verifying the plain SFTP file lifecycle (consume by
 *     delete + local archive).</li>
 *     <li>{@link #sftpFileWithOlCandTransformConsumedArchivedLocallyAndDispatchedToOlCandRoute()} — a real
 *     JavaScript transform that parses the input file and emits an order-line-candidate item, verifying
 *     the item is actually dispatched to the OLCand route and the source file is consumed + archived
 *     locally.</li>
 *     <li>{@link #malformedSftpFileConsumedAndArchivedToLocalErrorDir()} — a malformed input file that
 *     makes the same real transform throw, verifying the remote file is still consumed (deleted) and the
 *     payload archived to the LOCAL error folder instead of being silently lost, and that nothing is
 *     dispatched to the OLCand route.</li>
 * </ul>
 * The {@link ProducerTemplate} used by the routes under test is a real one (bound to this test's
 * {@link #context}), so that a dispatch to the OLCand route id ({@value ExternalSystemCamelConstants#MF_PUSH_OL_CANDIDATES_ROUTE_ID})
 * actually reaches the dummy destination route registered in {@link #registerOlCandMockRoute()}.
 */
public class InboundSftpIntegrationTest extends CamelTestSupport
{
	private static final String SFTP_USERNAME = "testuser";
	private static final String SFTP_PASSWORD = "testpass";
	private static final String ENDPOINT_NAME = "sftpIntegrationTestEndpoint";
	/**
	 * Stable per-child route key — the dynamic SFTP poll route is keyed on THIS (the child config id),
	 * NOT on {@link #ENDPOINT_NAME} (the endpoint Value), so that changing the endpoint does not leak the
	 * old poller. This is the value the backend puts under {@code PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY}.
	 */
	private static final String ROUTE_KEY = "ScriptedImportConversion-100";
	private static final String SCRIPT_IDENTIFIER = "inbound_sftp_test_noop";
	private static final String SCRIPT_IDENTIFIER_OLCAND = "inbound_sftp_test_olcand";
	private static final String TEST_FILE_NAME = "test_order.json";
	private static final String TEST_FILE_CONTENT = "{\"orderId\": \"12345\", \"items\": [{\"sku\": \"ABC\", \"qty\": 10}]}";
	private static final String MALFORMED_TEST_FILE_NAME = "malformed_order.json";
	private static final String MALFORMED_TEST_FILE_CONTENT = "{ \"orderId\": \"12345\", this is not valid json !!";

	private static final String OLCAND_MOCK_ROUTE_URI = "mock:olCandRoute";

	/**
	 * The requestBody the {@code inbound_sftp_test_olcand.js} script produces for {@link #TEST_FILE_CONTENT}.
	 */
	private static final String OLCAND_REQUEST_BODY_JSON = "{\"requests\": [{\"orgCode\": \"001\", \"externalHeaderId\": \"12345\", "
			+ "\"externalLineId\": \"12345\", \"externalSystemCode\": \"Other\", \"dataSource\": \"int-Shopware\", "
			+ "\"bpartner\": {\"bpartnerIdentifier\": \"2156425\", \"bpartnerLocationIdentifier\": \"2205175\"}, "
			+ "\"dateRequired\": \"2022-12-12\", \"dateOrdered\": \"2022-12-12\", \"orderDocType\": \"SalesOrder\", "
			+ "\"paymentTerm\": \"val-1000002\", \"productIdentifier\": \"2005577\", \"qty\": 1, \"currencyCode\": \"EUR\", "
			+ "\"discount\": 0, \"poReference\": \"ref_12301\", \"deliveryViaRule\": \"S\", \"deliveryRule\": \"F\", "
			+ "\"bpartnerName\": \"testName\"}]}";

	@TempDir
	Path sftpRootDir;

	@TempDir
	Path scriptRepoDir;

	/** LOCAL processed-folder archive target (never a remote dir — see AC5 runtime-fixes refinement). */
	@TempDir
	Path localProcessedDir;

	/** LOCAL error-folder archive target (never a remote dir — see AC5 runtime-fixes refinement). */
	@TempDir
	Path localErrorDir;

	private EmbeddedSftpServer sftpServer;

	@BeforeEach
	void setUpSftpEnvironment() throws Exception
	{
		// Set the scriptRepoDir property now that @TempDir has been injected
		context.getPropertiesComponent().addOverrideProperty(
				"metasfresh.scriptedadapter.repo.baseDir",
				scriptRepoDir.toAbsolutePath().toString());

		// Create the SFTP inbound directory. No remote .done/.error subdirs — the remote file is
		// consumed by delete, never moved to a remote folder (see class javadoc).
		Files.createDirectories(sftpRootDir.resolve("inbound"));

		// Create a trivial JS script that returns an empty array (no API calls to dispatch)
		final String noopScript = "function transform(messageToMetasfresh) {\n"
				+ "    return JSON.stringify([]);\n"
				+ "}\n";
		Files.writeString(scriptRepoDir.resolve(SCRIPT_IDENTIFIER + ".js"), noopScript, StandardCharsets.UTF_8);

		// Create a real JS script that parses the incoming file and emits one OLCand create-bulk item.
		// Used to genuinely exercise the OLCand dispatch (as opposed to the no-op script above),
		// and to genuinely fail (JSON.parse throws) on malformed input.
		final String olCandScript = "function transform(messageFromMetasfresh) {\n"
				+ "    var order = JSON.parse(messageFromMetasfresh);\n"
				+ "    var requestBody = JSON.stringify({\n"
				+ "        requests: [{\n"
				+ "            orgCode: \"001\",\n"
				+ "            externalHeaderId: String(order.orderId),\n"
				+ "            externalLineId: String(order.orderId),\n"
				+ "            externalSystemCode: \"Other\",\n"
				+ "            dataSource: \"int-Shopware\",\n"
				+ "            bpartner: { bpartnerIdentifier: \"2156425\", bpartnerLocationIdentifier: \"2205175\" },\n"
				+ "            dateRequired: \"2022-12-12\",\n"
				+ "            dateOrdered: \"2022-12-12\",\n"
				+ "            orderDocType: \"SalesOrder\",\n"
				+ "            paymentTerm: \"val-1000002\",\n"
				+ "            productIdentifier: \"2005577\",\n"
				+ "            qty: 1,\n"
				+ "            currencyCode: \"EUR\",\n"
				+ "            discount: 0,\n"
				+ "            poReference: \"ref_12301\",\n"
				+ "            deliveryViaRule: \"S\",\n"
				+ "            deliveryRule: \"F\",\n"
				+ "            bpartnerName: \"testName\"\n"
				+ "        }]\n"
				+ "    });\n"
				+ "    return JSON.stringify([{ camelServiceRouteID: \"" + MF_PUSH_OL_CANDIDATES_ROUTE_ID + "\", requestBody: requestBody }]);\n"
				+ "}\n";
		Files.writeString(scriptRepoDir.resolve(SCRIPT_IDENTIFIER_OLCAND + ".js"), olCandScript, StandardCharsets.UTF_8);

		// Start embedded SFTP server
		sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USERNAME, SFTP_PASSWORD);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		// Stop the dynamic route if still running (keyed on the stable route key, not the endpoint name)
		if (context.getRoute(ROUTE_KEY) != null)
		{
			try
			{
				context.getRouteController().stopRoute(ROUTE_KEY);
				context.removeRoute(ROUTE_KEY);
			}
			catch (final Exception ignored)
			{
				// best-effort cleanup
			}
		}

		if (sftpServer != null)
		{
			sftpServer.close();
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		// A real (context-bound) producer template so that a dispatch to the OLCand route id actually
		// reaches whatever destination route is registered for it in this test's context (see registerOlCandMockRoute()).
		final ProducerTemplate producerTemplate = context.createProducerTemplate();
		return new ScriptedImportConversionSftpRouteBuilder(producerTemplate);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		// Load base properties; scriptRepoDir is not available yet (@TempDir injected after construction),
		// so the baseDir property is set later in setUpSftpEnvironment()
		final Properties properties = new Properties();
		try
		{
			properties.load(InboundSftpIntegrationTest.class.getClassLoader().getResourceAsStream("application.properties"));
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
		return properties;
	}

	@Test
	void sftpFilePolledConsumedAndArchivedLocally() throws Exception
	{
		// Arrange: intercept the external status endpoint and the error route so they don't fail
		interceptExternalStatusEndpoints();
		registerDummyErrorRoute();

		context.start();

		// Place the test file in /inbound on the SFTP server
		final Path inboundFile = sftpRootDir.resolve("inbound").resolve(TEST_FILE_NAME);
		Files.writeString(inboundFile, TEST_FILE_CONTENT, StandardCharsets.UTF_8);
		assertThat(inboundFile).exists();

		// Build the enable request
		final JsonExternalSystemRequest enableRequest = buildEnableRequest(SCRIPT_IDENTIFIER);

		// Act: fire the enable SFTP polling route
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID, enableRequest);

		// The dynamic route should now be registered
		assertThat(context.getRouteController().getRouteStatus(ROUTE_KEY)).isNotNull();

		// Wait for the payload to be archived to the LOCAL processed folder (up to 10 seconds)
		final Path localDoneFile = localProcessedDir.resolve(TEST_FILE_NAME);
		final boolean archivedLocally = waitForCondition(() -> Files.exists(localDoneFile), 10_000, 250);
		assertThat(archivedLocally)
				.as("Payload should be archived to the LOCAL processed folder within 10 seconds")
				.isTrue();

		// The remote file must be CONSUMED (deleted) — never moved to a remote .done folder
		assertThat(inboundFile).doesNotExist();
		assertThat(sftpRootDir.resolve("inbound/.done")).doesNotExist();

		// The locally-archived file content should match what we placed
		assertThat(Files.readString(localDoneFile, StandardCharsets.UTF_8)).isEqualTo(TEST_FILE_CONTENT);

		// Nothing should have landed in the local error folder
		try (var files = Files.list(localErrorDir))
		{
			assertThat(files.findAny()).isEmpty();
		}

		// Act: disable the polling route
		final JsonExternalSystemRequest disableRequest = buildDisableRequest();
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID, disableRequest);

		// Then: route should be removed
		assertThat(context.getRoute(ROUTE_KEY)).isNull();
	}

	@Test
	void sftpFileWithOlCandTransformConsumedArchivedLocallyAndDispatchedToOlCandRoute() throws Exception
	{
		// Arrange: intercept the external status endpoint, the error route, and register a dummy
		// destination for the OLCand route id so the dispatch has somewhere real to land.
		interceptExternalStatusEndpoints();
		registerDummyErrorRoute();
		registerOlCandMockRoute();

		context.start();

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(OLCAND_MOCK_ROUTE_URI);
		olCandMockEndpoint.expectedMessageCount(1);

		// Place the test file in /inbound on the SFTP server
		final Path inboundFile = sftpRootDir.resolve("inbound").resolve(TEST_FILE_NAME);
		Files.writeString(inboundFile, TEST_FILE_CONTENT, StandardCharsets.UTF_8);
		assertThat(inboundFile).exists();

		// Act: fire the enable SFTP polling route, using the real (non-no-op) OLCand-producing transform
		final JsonExternalSystemRequest enableRequest = buildEnableRequest(SCRIPT_IDENTIFIER_OLCAND);
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID, enableRequest);
		assertThat(context.getRouteController().getRouteStatus(ROUTE_KEY)).isNotNull();

		// Wait for the payload to be archived to the LOCAL processed folder (up to 10 seconds)
		final Path localDoneFile = localProcessedDir.resolve(TEST_FILE_NAME);
		final boolean archivedLocally = waitForCondition(() -> Files.exists(localDoneFile), 10_000, 250);
		assertThat(archivedLocally)
				.as("Payload should be archived to the LOCAL processed folder within 10 seconds")
				.isTrue();

		// The remote file must be CONSUMED (deleted) — never moved to a remote .done folder
		assertThat(inboundFile).doesNotExist();
		assertThat(sftpRootDir.resolve("inbound/.done")).doesNotExist();

		// Then: the item produced by the real transform was actually dispatched to the OLCand route
		olCandMockEndpoint.assertIsSatisfied(10_000);
		final JsonOLCandCreateBulkRequest actualDispatchedBody = olCandMockEndpoint.getExchanges().get(0).getIn().getBody(JsonOLCandCreateBulkRequest.class);
		final JsonOLCandCreateBulkRequest expectedDispatchedBody = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(OLCAND_REQUEST_BODY_JSON, JsonOLCandCreateBulkRequest.class);
		assertThat(actualDispatchedBody).isEqualTo(expectedDispatchedBody);

		// Act: disable the polling route
		final JsonExternalSystemRequest disableRequest = buildDisableRequest();
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID, disableRequest);
		assertThat(context.getRoute(ROUTE_KEY)).isNull();
	}

	@Test
	void malformedSftpFileConsumedAndArchivedToLocalErrorDir() throws Exception
	{
		// Arrange: same setup as the successful OLCand-dispatch test, but the input file will make the
		// real transform throw (JSON.parse on invalid JSON), so nothing should ever reach the OLCand route.
		interceptExternalStatusEndpoints();
		registerDummyErrorRoute();
		registerOlCandMockRoute();

		context.start();

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(OLCAND_MOCK_ROUTE_URI);
		olCandMockEndpoint.expectedMessageCount(0);

		// Place a malformed test file in /inbound on the SFTP server
		final Path inboundFile = sftpRootDir.resolve("inbound").resolve(MALFORMED_TEST_FILE_NAME);
		Files.writeString(inboundFile, MALFORMED_TEST_FILE_CONTENT, StandardCharsets.UTF_8);
		assertThat(inboundFile).exists();

		// Act: fire the enable SFTP polling route
		final JsonExternalSystemRequest enableRequest = buildEnableRequest(SCRIPT_IDENTIFIER_OLCAND);
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID, enableRequest);
		assertThat(context.getRouteController().getRouteStatus(ROUTE_KEY)).isNotNull();

		// Wait for the payload to be archived to the LOCAL error folder (up to 10 seconds) — it must
		// not be silently lost
		final Path localErrorFile = localErrorDir.resolve(MALFORMED_TEST_FILE_NAME);
		final boolean archivedLocally = waitForCondition(() -> Files.exists(localErrorFile), 10_000, 250);
		assertThat(archivedLocally)
				.as("Malformed payload should be archived to the LOCAL error folder within 10 seconds")
				.isTrue();
		assertThat(Files.readString(localErrorFile, StandardCharsets.UTF_8)).isEqualTo(MALFORMED_TEST_FILE_CONTENT);

		// The remote file must still be CONSUMED (deleted) — never left in place, never moved remotely
		assertThat(inboundFile).doesNotExist();
		assertThat(sftpRootDir.resolve("inbound/.done")).doesNotExist();
		assertThat(sftpRootDir.resolve("inbound/.error")).doesNotExist();

		// It must not have ended up in the local processed folder either
		final Path localDoneFile = localProcessedDir.resolve(MALFORMED_TEST_FILE_NAME);
		assertThat(localDoneFile).doesNotExist();

		// Nothing should have been dispatched to the OLCand route
		olCandMockEndpoint.assertIsSatisfied(2_000);

		// Act: disable the polling route
		final JsonExternalSystemRequest disableRequest = buildDisableRequest();
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID, disableRequest);
		assertThat(context.getRoute(ROUTE_KEY)).isNull();
	}

	/**
	 * Reproduces the production leak: a child's SFTP endpoint is changed (pointed at a different endpoint
	 * record — different host / {@code Value}) and the poller is re-started. The previously-started route
	 * must be torn down; only ONE poller — the new one — may remain.
	 * <p>
	 * The dynamic route is keyed on the STABLE per-child route key ({@link #ROUTE_KEY}), not on the endpoint
	 * name/host, so the second enable idempotently REPLACES the first. On the pre-fix code (route keyed on
	 * the endpoint name) the two enables produced two distinct routes and the old host kept being polled.
	 */
	@Test
	void endpointChanged_previousPollerTornDown_onlyNewPollerRemains() throws Exception
	{
		interceptExternalStatusEndpoints();
		registerDummyErrorRoute();

		context.start();

		// Two remote locations on the SFTP server — "before" and "after" the endpoint change.
		Files.createDirectories(sftpRootDir.resolve("inboundA"));
		Files.createDirectories(sftpRootDir.resolve("inboundB"));

		// Enable pointing at endpoint A (Value "endpointA", remote path inboundA).
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID,
				buildEnableRequest(SCRIPT_IDENTIFIER, "endpointA", ROUTE_KEY, "inboundA"));

		// The child's endpoint is changed to a DIFFERENT endpoint record (Value "endpointB", remote path
		// inboundB) and Start is pressed again — SAME child, SAME stable route key, different host/path.
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID,
				buildEnableRequest(SCRIPT_IDENTIFIER, "endpointB", ROUTE_KEY, "inboundB"));

		// Exactly ONE SFTP poll route must remain — the old endpoint-A poller must have been removed.
		assertThat(countSftpPollRoutes())
				.as("Changing the child's endpoint must not leak the old SFTP poller")
				.isEqualTo(1);

		// And the surviving route must poll the NEW location, never the old one.
		final String survivingUri = sftpPollRouteUris().stream().findFirst().orElse("");
		assertThat(survivingUri).contains("inboundB");
		assertThat(survivingUri).doesNotContain("inboundA");

		// Disable via the stable key removes the poller entirely.
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID,
				buildDisableRequest("endpointB", ROUTE_KEY));
		assertThat(countSftpPollRoutes())
				.as("Disable must remove the SFTP poller")
				.isZero();
		assertThat(context.getRoute(ROUTE_KEY)).isNull();
	}

	private long countSftpPollRoutes()
	{
		return sftpPollRouteUris().size();
	}

	private List<String> sftpPollRouteUris()
	{
		return context.getRoutes().stream()
				.map(route -> route.getEndpoint().getEndpointUri())
				.filter(uri -> uri.startsWith("sftp://"))
				.collect(Collectors.toList());
	}

	private void interceptExternalStatusEndpoints() throws Exception
	{
		AdviceWith.adviceWith(context, ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID,
				advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
						.skipSendToOriginalEndpoint()
						.process(exchange -> { /* no-op */ }));

		AdviceWith.adviceWith(context, ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID,
				advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
						.skipSendToOriginalEndpoint()
						.process(exchange -> { /* no-op */ }));
	}

	private void registerDummyErrorRoute() throws Exception
	{
		// Register a dummy error route so onException doesn't fail
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId(MF_ERROR_ROUTE_ID)
						.log("Error route invoked (test): ${body}");
			}
		});
	}

	/**
	 * Registers a dummy destination route for the OLCand route id, ending in a mock endpoint, so that a
	 * dispatch to {@value ExternalSystemCamelConstants#MF_PUSH_OL_CANDIDATES_ROUTE_ID} (the production route
	 * lives outside this module's Camel context) has somewhere to land and can be asserted on.
	 */
	private void registerOlCandMockRoute() throws Exception
	{
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_PUSH_OL_CANDIDATES_ROUTE_ID)
						.routeId(MF_PUSH_OL_CANDIDATES_ROUTE_ID)
						.to(OLCAND_MOCK_ROUTE_URI)
						.setBody(constant("{}"));
			}
		});
	}

	private JsonExternalSystemRequest buildEnableRequest(@NonNull final String scriptIdentifier)
	{
		return buildEnableRequest(scriptIdentifier, ENDPOINT_NAME, ROUTE_KEY, "inbound");
	}

	private JsonExternalSystemRequest buildEnableRequest(
			@NonNull final String scriptIdentifier,
			@NonNull final String endpointName,
			@NonNull final String routeKey,
			@NonNull final String remotePath)
	{
		final Map<String, String> params = new HashMap<>();
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, endpointName);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY, routeKey);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, scriptIdentifier);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST, "localhost");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PORT, String.valueOf(sftpServer.getPort()));
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME, SFTP_USERNAME);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PASSWORD, SFTP_PASSWORD);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE, "PASSWORD");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_REMOTE_PATH, remotePath);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_INTERVAL_MS, "500");
		// LOCAL, transport-agnostic archive folders (never remote — see class javadoc)
		params.put(ExternalSystemConstants.PARAM_PROCESSED_DIR, localProcessedDir.toAbsolutePath().toString());
		params.put(ExternalSystemConstants.PARAM_ERROR_DIR, localErrorDir.toAbsolutePath().toString());

		return JsonExternalSystemRequest.builder()
				.externalSystemName(JsonExternalSystemName.of("ScriptedImportConversion"))
				.externalSystemConfigId(JsonMetasfreshId.of(100))
				.externalSystemChildConfigValue("testChild")
				.command("ScriptedImportConversion-enableSftpPolling")
				.orgCode("testOrg")
				.adPInstanceId(JsonMetasfreshId.of(999))
				.traceId("integrationTest-trace")
				.parameters(params)
				.build();
	}

	private JsonExternalSystemRequest buildDisableRequest()
	{
		return buildDisableRequest(ENDPOINT_NAME, ROUTE_KEY);
	}

	private JsonExternalSystemRequest buildDisableRequest(
			@NonNull final String endpointName,
			@NonNull final String routeKey)
	{
		final Map<String, String> params = new HashMap<>();
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, endpointName);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY, routeKey);

		return JsonExternalSystemRequest.builder()
				.externalSystemName(JsonExternalSystemName.of("ScriptedImportConversion"))
				.externalSystemConfigId(JsonMetasfreshId.of(100))
				.externalSystemChildConfigValue("testChild")
				.command("ScriptedImportConversion-disableSftpPolling")
				.orgCode("testOrg")
				.adPInstanceId(JsonMetasfreshId.of(999))
				.traceId("integrationTest-trace")
				.parameters(params)
				.build();
	}

	/**
	 * Polls a condition at the given interval until it returns true or the timeout elapses.
	 *
	 * @return true if the condition was met within the timeout, false otherwise
	 */
	@SuppressWarnings("BusyWait")
	private static boolean waitForCondition(final BooleanSupplier condition, final long timeoutMs, final long pollIntervalMs)
	{
		final long deadline = System.currentTimeMillis() + timeoutMs;
		while (System.currentTimeMillis() < deadline)
		{
			if (condition.getAsBoolean())
			{
				return true;
			}
			try
			{
				Thread.sleep(pollIntervalMs);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				return false;
			}
		}
		return condition.getAsBoolean();
	}

	@FunctionalInterface
	private interface BooleanSupplier
	{
		boolean getAsBoolean();
	}
}
