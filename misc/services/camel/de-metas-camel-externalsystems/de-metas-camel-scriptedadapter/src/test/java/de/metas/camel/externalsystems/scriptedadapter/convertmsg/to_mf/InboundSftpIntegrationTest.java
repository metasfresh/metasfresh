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
import de.metas.camel.externalsystems.scriptedadapter.sftp.EmbeddedSftpServer;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * End-to-end integration test for inbound SFTP polling via the scripted adapter.
 * <p>
 * Starts an embedded SFTP server, drops a test file into /inbound, enables the SFTP polling route,
 * waits for the file to be picked up and processed, then verifies it was moved to .done.
 * Finally disables the route and verifies clean shutdown.
 * <p>
 * Uses a real (trivial) JavaScript transform that returns an empty array, so no metasfresh API
 * dispatch is needed — the test focuses on verifying the SFTP file lifecycle (pick up + move to .done).
 */
public class InboundSftpIntegrationTest extends CamelTestSupport
{
	private static final String SFTP_USERNAME = "testuser";
	private static final String SFTP_PASSWORD = "testpass";
	private static final String ENDPOINT_NAME = "sftpIntegrationTestEndpoint";
	private static final String SCRIPT_IDENTIFIER = "inbound_sftp_test_noop";
	private static final String TEST_FILE_NAME = "test_order.json";
	private static final String TEST_FILE_CONTENT = "{\"orderId\": \"12345\", \"items\": [{\"sku\": \"ABC\", \"qty\": 10}]}";

	@TempDir
	Path sftpRootDir;

	@TempDir
	Path scriptRepoDir;

	private EmbeddedSftpServer sftpServer;

	@BeforeEach
	void setUpSftpEnvironment() throws Exception
	{
		// Set the scriptRepoDir property now that @TempDir has been injected
		context.getPropertiesComponent().addOverrideProperty(
				"metasfresh.scriptedadapter.repo.baseDir",
				scriptRepoDir.toAbsolutePath().toString());

		// Create SFTP directories
		Files.createDirectories(sftpRootDir.resolve("inbound"));
		Files.createDirectories(sftpRootDir.resolve("inbound/.done"));
		Files.createDirectories(sftpRootDir.resolve("inbound/.error"));

		// Create a trivial JS script that returns an empty array (no API calls to dispatch)
		final String noopScript = "function transform(messageToMetasfresh) {\n"
				+ "    return JSON.stringify([]);\n"
				+ "}\n";
		Files.writeString(scriptRepoDir.resolve(SCRIPT_IDENTIFIER + ".js"), noopScript, StandardCharsets.UTF_8);

		// Start embedded SFTP server
		sftpServer = new EmbeddedSftpServer(sftpRootDir, SFTP_USERNAME, SFTP_PASSWORD);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		// Stop the dynamic route if still running
		if (context.getRoute(ENDPOINT_NAME) != null)
		{
			try
			{
				context.getRouteController().stopRoute(ENDPOINT_NAME);
				context.removeRoute(ENDPOINT_NAME);
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
		final ProducerTemplate producerTemplate = Mockito.mock(ProducerTemplate.class);
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
	void sftpFilePolledAndMovedToDone() throws Exception
	{
		// Arrange: intercept the external status endpoint and the error route so they don't fail
		AdviceWith.adviceWith(context, ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID,
				advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
						.skipSendToOriginalEndpoint()
						.process(exchange -> { /* no-op */ }));

		AdviceWith.adviceWith(context, ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID,
				advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
						.skipSendToOriginalEndpoint()
						.process(exchange -> { /* no-op */ }));

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

		context.start();

		// Place the test file in /inbound on the SFTP server
		final Path inboundFile = sftpRootDir.resolve("inbound").resolve(TEST_FILE_NAME);
		Files.writeString(inboundFile, TEST_FILE_CONTENT, StandardCharsets.UTF_8);
		assertThat(inboundFile).exists();

		// Build the enable request
		final JsonExternalSystemRequest enableRequest = buildEnableRequest();

		// Act: fire the enable SFTP polling route
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.ENABLE_SFTP_POLLING_ROUTE_ID, enableRequest);

		// The dynamic route should now be registered
		assertThat(context.getRouteController().getRouteStatus(ENDPOINT_NAME)).isNotNull();

		// Wait for the file to be polled and moved to .done (up to 10 seconds)
		final Path doneFile = sftpRootDir.resolve("inbound/.done").resolve(TEST_FILE_NAME);
		final boolean fileMovedToDone = waitForCondition(() -> Files.exists(doneFile), 10_000, 250);
		assertThat(fileMovedToDone)
				.as("File should be moved from /inbound to /inbound/.done within 10 seconds")
				.isTrue();

		// The original file should no longer be in /inbound
		assertThat(inboundFile).doesNotExist();

		// The .done file content should match what we placed
		assertThat(Files.readString(doneFile, StandardCharsets.UTF_8)).isEqualTo(TEST_FILE_CONTENT);

		// Act: disable the polling route
		final JsonExternalSystemRequest disableRequest = buildDisableRequest();
		template.sendBody("direct:" + ScriptedImportConversionSftpRouteBuilder.DISABLE_SFTP_POLLING_ROUTE_ID, disableRequest);

		// Then: route should be removed
		assertThat(context.getRoute(ENDPOINT_NAME)).isNull();
	}

	private JsonExternalSystemRequest buildEnableRequest()
	{
		final Map<String, String> params = new HashMap<>();
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, ENDPOINT_NAME);
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, SCRIPT_IDENTIFIER);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST, "localhost");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PORT, String.valueOf(sftpServer.getPort()));
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME, SFTP_USERNAME);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PASSWORD, SFTP_PASSWORD);
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE, "PASSWORD");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_REMOTE_PATH, "inbound");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_INTERVAL_MS, "500");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_PROCESSED_DIR, ".done");
		params.put(ExternalSystemConstants.PARAM_SFTP_POLLING_ERROR_DIR, ".error");

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
		final Map<String, String> params = new HashMap<>();
		params.put(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, ENDPOINT_NAME);

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
