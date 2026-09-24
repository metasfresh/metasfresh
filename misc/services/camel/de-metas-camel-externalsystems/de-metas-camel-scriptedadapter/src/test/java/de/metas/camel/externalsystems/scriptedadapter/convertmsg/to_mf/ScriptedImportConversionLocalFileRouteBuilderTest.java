/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2026 metas GmbH
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
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERROR_DIR;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PROCESSED_DIR;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Wiring coverage for the addressable enable/disable service of the LOCAL_FILE transport.
 * <p>
 * Unlike the SFTP sibling's test — which replaces its enable processor with a no-op, because a real
 * SFTP poller needs a server — this test runs the real enable processor: a {@code file://} consumer
 * over a {@code @TempDir} costs nothing, so every assertion here is on the dynamic route the
 * processor actually created. That is the point: the route builder existed for a while with nothing
 * constructing it, and a test that only checked the status notification would have stayed green
 * through exactly that gap.
 */
public class ScriptedImportConversionLocalFileRouteBuilderTest extends CamelTestSupport
{
	private static final String MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID = "mock:Core-storeExternalStatus";

	private static final String ROUTE_KEY = "ScriptedImportConversion-540123";
	private static final String ENDPOINT_NAME = "packzettelEndpoint";
	private static final String SCRIPT_IDENTIFIER = "packzettelScript";
	private static final String CHILD_CONFIG_VALUE = "packzettelChild";
	private static final String ORG_CODE = "001";
	private static final String ENABLE_COMMAND = "enableLocalFilePolling";
	private static final String DISABLE_COMMAND = "disableLocalFilePolling";

	@TempDir
	Path localInputDir;

	@TempDir
	Path movedInputDir;

	@TempDir
	Path localProcessedDir;

	@TempDir
	Path localErrorDir;

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final ProducerTemplate producerTemplate = Mockito.mock(ProducerTemplate.class);
		return new ScriptedImportConversionLocalFileRouteBuilder(producerTemplate);
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
			properties.load(ScriptedImportConversionLocalFileRouteBuilderTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	void enable_createsAndStartsTheDynamicPollRouteOnTheEndpointsDirectory() throws Exception
	{
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = interceptStatusEndpoint(
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID,
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID);

		context.start();
		assertThat(context.getRoute(ROUTE_KEY)).as("no poller before enable").isNull();

		template.sendBody("direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, params()));

		// The poller exists, is running, and polls THIS endpoint's directory at THIS endpoint's frequency
		// — i.e. the endpoint's settings reached the route, not just "some route was created".
		assertThat(context.getRoute(ROUTE_KEY)).as("poller for the child's stable route key").isNotNull();
		assertThat(context.getRouteController().getRouteStatus(ROUTE_KEY).isStarted()).isTrue();
		assertThat(context.getRoute(ROUTE_KEY).getEndpoint().getEndpointUri())
				.contains(localInputDir.toAbsolutePath().toString())
				.contains("delay=250");

		assertThat(mockStoreExternalStatusEP.lastStatus).isEqualTo(JsonExternalStatus.Active);
		assertThat(mockStoreExternalStatusEP.called).isEqualTo(1);
	}

	@Test
	void reEnable_afterTheEndpointChanged_pollsTheNewDirectory() throws Exception
	{
		interceptStatusEndpoint(
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID,
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID);

		context.start();

		template.sendBody("direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, params()));

		// The operator edits the endpoint and presses Start again. The route key is the child's, so it is
		// unchanged — the second enable must therefore land on the NEW directory rather than be swallowed
		// as a duplicate route id.
		final Map<String, String> movedEndpoint = params();
		movedEndpoint.put(PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION, movedInputDir.toAbsolutePath().toString());
		template.sendBody("direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, movedEndpoint));

		assertThat(context.getRoutes().stream().filter(route -> ROUTE_KEY.equals(route.getRouteId())).count())
				.as("exactly one poller per child — a second one on the same input would double-import")
				.isEqualTo(1);
		assertThat(context.getRouteController().getRouteStatus(ROUTE_KEY).isStarted()).isTrue();
		assertThat(context.getRoute(ROUTE_KEY).getEndpoint().getEndpointUri())
				.contains(movedInputDir.toAbsolutePath().toString());
	}

	@Test
	void reEnable_ontoAnUnusableDirectory_leavesNothingPollingTheOldOne() throws Exception
	{
		interceptStatusEndpoint(
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID,
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID);
		captureErrorRoute();
		context.start();

		template.sendBody("direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, params()));
		assertThat(context.getRoute(ROUTE_KEY)).isNotNull();

		// A directory the dynamic route builder rejects while building its file:// URI — non-blank, so it
		// passes this service's own parameter check and only blows up inside addRoutes().
		final Map<String, String> unusableEndpoint = params();
		unusableEndpoint.put(PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION, localInputDir.toAbsolutePath() + "&inbox");

		assertThatThrownBy(() -> template.sendBody(
				"direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, unusableEndpoint)))
				.isInstanceOf(CamelExecutionException.class);

		// This is what the pre-clean in enable buys, and the only thing it buys: Camel replaces a same-id
		// route by itself, but only once the new one BUILDS. A re-enable that fails mid-build would
		// otherwise leave the previous poller running on a directory the endpoint no longer names, while
		// the operator is told the start failed.
		assertThat(context.getRoute(ROUTE_KEY))
				.as("stale poller left running by a re-enable that failed to build")
				.isNull();
	}

	@Test
	void disable_stopsAndRemovesThePollerStartedByEnable() throws Exception
	{
		interceptStatusEndpoint(
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID,
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID);
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = interceptStatusEndpoint(
				ScriptedImportConversionLocalFileRouteBuilder.DISABLE_LOCAL_FILE_POLLING_ROUTE_ID,
				ScriptedImportConversionLocalFileRouteBuilder.DISABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID);

		context.start();

		// Started by the real enable processor, not by a hand-registered stand-in: this pins that the two
		// sides agree on the key, which is what makes Stop able to find a running poller at all.
		template.sendBody("direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, params()));
		assertThat(context.getRoute(ROUTE_KEY)).isNotNull();

		template.sendBody("direct:" + ScriptedImportConversionLocalFileRouteBuilder.DISABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(DISABLE_COMMAND, params()));

		assertThat(context.getRoute(ROUTE_KEY)).isNull();
		assertThat(mockStoreExternalStatusEP.lastStatus).isEqualTo(JsonExternalStatus.Inactive);
	}

	@Test
	void enable_withoutFrequency_fallsBackToTheTransportWideDefault() throws Exception
	{
		interceptStatusEndpoint(
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID,
				ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID);

		context.start();

		// getParameters() omits the key entirely when the endpoint carries no frequency, so this is the
		// map a pre-mandatory-Frequency endpoint produces — it must poll, not NPE on a blind parseLong.
		final Map<String, String> params = params();
		params.remove(PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS);

		template.sendBody("direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, params));

		assertThat(context.getRoute(ROUTE_KEY).getEndpoint().getEndpointUri()).contains("delay=60000");
	}

	@Test
	void enable_withoutRootLocation_failsNamingTheParameter() throws Exception
	{
		captureErrorRoute();
		context.start();

		final Map<String, String> params = params();
		params.remove(PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION);

		assertThatThrownBy(() -> template.sendBody(
				"direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, params)))
				.isInstanceOf(CamelExecutionException.class)
				.rootCause()
				.hasMessageContaining(PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION);

		assertThat(context.getRoute(ROUTE_KEY)).as("no half-built poller left behind").isNull();
	}

	@Test
	void enable_withoutRouteKey_failsNamingTheParameter() throws Exception
	{
		captureErrorRoute();
		context.start();

		final Map<String, String> params = params();
		params.remove(PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY);

		assertThatThrownBy(() -> template.sendBody(
				"direct:" + ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID, newRequest(ENABLE_COMMAND, params)))
				.isInstanceOf(CamelExecutionException.class)
				.rootCause()
				.hasMessageContaining(PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY);
	}

	/**
	 * The three strings the backend addresses this service by. They are literal matches against the
	 * {@code ExternalSystem_Service} row (Value / EnableCommand / DisableCommand) and against
	 * {@code ScriptedImportConversionCommand}'s wire values; a typo in any one of them reproduces the
	 * silent unreachability this service was added to close, so they are pinned rather than derived.
	 */
	@Test
	void serviceValueAndCommands_matchTheRegisteredExternalSystemService()
	{
		final ScriptedImportConversionLocalFileRouteBuilder routeBuilder =
				new ScriptedImportConversionLocalFileRouteBuilder(Mockito.mock(ProducerTemplate.class));

		assertThat(routeBuilder.getServiceValue()).isEqualTo("defaultLocalFilePollingScriptedImportConversion");
		assertThat(routeBuilder.getEnableCommand()).isEqualTo("enableLocalFilePolling");
		assertThat(routeBuilder.getDisableCommand()).isEqualTo("disableLocalFilePolling");
		assertThat(routeBuilder.getExternalSystemTypeCode()).isEqualTo("ScriptedImportConversion");

		// The call dispatcher addresses a command as "<externalSystemName>-<command>", so these two route
		// ids are what the backend's enable/disable message actually lands on.
		assertThat(ScriptedImportConversionLocalFileRouteBuilder.ENABLE_LOCAL_FILE_POLLING_ROUTE_ID)
				.isEqualTo("ScriptedImportConversion-enableLocalFilePolling");
		assertThat(ScriptedImportConversionLocalFileRouteBuilder.DISABLE_LOCAL_FILE_POLLING_ROUTE_ID)
				.isEqualTo("ScriptedImportConversion-disableLocalFilePolling");
	}

	@NonNull
	private Map<String, String> params()
	{
		final Map<String, String> params = new HashMap<>();
		params.put(PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY, ROUTE_KEY);
		params.put(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, ENDPOINT_NAME);
		params.put(PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, SCRIPT_IDENTIFIER);
		params.put(PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION, localInputDir.toAbsolutePath().toString());
		params.put(PARAM_LOCAL_FILE_POLLING_ENDPOINT_FILE_NAME_PATTERN, "packzettel_{timestamp}");
		params.put(PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS, "250");
		params.put(PARAM_PROCESSED_DIR, localProcessedDir.toAbsolutePath().toString());
		params.put(PARAM_ERROR_DIR, localErrorDir.toAbsolutePath().toString());
		return params;
	}

	/**
	 * Shaped like what the backend's "call" process sends: the same command string the dispatcher
	 * addressed the route with, plus the child's parameter map.
	 */
	@NonNull
	private static JsonExternalSystemRequest newRequest(@NonNull final String command, @NonNull final Map<String, String> params)
	{
		return JsonExternalSystemRequest.builder()
				.externalSystemName(JsonExternalSystemName.of("ScriptedImportConversion"))
				.externalSystemChildConfigValue(CHILD_CONFIG_VALUE)
				.orgCode(ORG_CODE)
				.command(command)
				.externalSystemConfigId(JsonMetasfreshId.of(540020))
				.traceId("trace-" + command)
				.parameters(params)
				.build();
	}

	/**
	 * Redirects the status-notification send (whose configured URI has no consumer in the test context)
	 * into a recording processor, leaving the route's own enable/disable processor untouched.
	 */
	@NonNull
	private MockStoreExternalStatusEP interceptStatusEndpoint(
			@NonNull final String routeId,
			@NonNull final String prepareStatusProcessorId) throws Exception
	{
		final MockStoreExternalStatusEP mockStoreExternalStatusEP = new MockStoreExternalStatusEP();

		AdviceWith.adviceWith(context, routeId,
				advice -> {
					advice.weaveById(prepareStatusProcessorId)
							.after()
							.to(MOCK_STORE_EXTERNAL_STATUS_ROUTE_ID);

					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(mockStoreExternalStatusEP);
				});

		return mockStoreExternalStatusEP;
	}

	/**
	 * The shared error route has no consumer in this test context; the failure paths below route to it via
	 * onException, so give it one — otherwise the assertion would be on "no consumers available for
	 * Error-Route" rather than on the message the service actually raised.
	 */
	private void captureErrorRoute() throws Exception
	{
		context.addRoutes(new RouteBuilder()
		{
			@Override
			public void configure()
			{
				from("direct:" + MF_ERROR_ROUTE_ID)
						.routeId("mock-" + MF_ERROR_ROUTE_ID)
						.log("mock error route");
			}
		});
	}

	private static class MockStoreExternalStatusEP implements Processor
	{
		private int called = 0;
		private JsonExternalStatus lastStatus = null;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			lastStatus = exchange.getIn().getBody(ExternalStatusCreateCamelRequest.class).getJsonStatusRequest().getStatus();
		}
	}
}
