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

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.ExternalStatusCreateCamelRequest;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptExecutorService;
import de.metas.camel.externalsystems.scriptedadapter.JavaScriptRepo;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.IExternalSystemService;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.status.JsonExternalStatus;
import de.metas.common.externalsystem.status.JsonStatusRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.DEFAULT_LOCAL_ERROR_DIR;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.DEFAULT_LOCAL_PROCESSED_DIR;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * The addressable enable/disable service for a {@code LOCAL_FILE} scripted-import endpoint — the
 * local-file counterpart of {@link ScriptedImportConversionSftpRouteBuilder}, and the piece that
 * instantiates {@link ScriptedImportConversionLocalFileDynamicRouteBuilder}.
 * <p>
 * Two joints make this reachable from the backend, and both are literal string matches:
 * {@link #getServiceValue()} must equal the {@code ExternalSystem_Service.Value} registered for this
 * transport, and {@link #ENABLE_LOCAL_FILE_POLLING} / {@link #DISABLE_LOCAL_FILE_POLLING} must equal
 * that row's {@code EnableCommand}/{@code DisableCommand} and the wire values of
 * {@code ScriptedImportConversionCommand.EnableLocalFilePolling}/{@code DisableLocalFilePolling} —
 * the call dispatcher addresses this route as {@code <externalSystemName>-<command>}.
 */
@Component
@RequiredArgsConstructor
public class ScriptedImportConversionLocalFileRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	private static final String ENABLE_LOCAL_FILE_POLLING = "enableLocalFilePolling";
	private static final String DISABLE_LOCAL_FILE_POLLING = "disableLocalFilePolling";

	public static final String ENABLE_LOCAL_FILE_POLLING_ROUTE_ID = SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME + "-" + ENABLE_LOCAL_FILE_POLLING;
	public static final String DISABLE_LOCAL_FILE_POLLING_ROUTE_ID = SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME + "-" + DISABLE_LOCAL_FILE_POLLING;

	public static final String ENABLE_LOCAL_FILE_POLLING_PROCESSOR_ID = "ScriptedImportConversion-enableLocalFilePollingProcessor";
	public static final String DISABLE_LOCAL_FILE_POLLING_PROCESSOR_ID = "ScriptedImportConversion-disableLocalFilePollingProcessor";

	public static final String ENABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-EnableLocalFile-PrepareStatusReqProcessorId";
	public static final String DISABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-DisableLocalFile-PrepareStatusReqProcessorId";

	/**
	 * Polling interval used when the endpoint carries none. {@code ExternalSystem_Endpoint.Frequency} is
	 * mandatory for a LOCAL_FILE endpoint and defaults to this same value, so the key is absent only for
	 * a row that predates those two rules — poll at the transport-wide default rather than refuse it.
	 * Same value and same reason as the SFTP sibling's polling-interval default.
	 */
	private static final String DEFAULT_POLLING_INTERVAL_MS = "60000";

	@NonNull private final ProducerTemplate producerTemplate;

	private JavaScriptExecutorService javaScriptExecutorService;

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		javaScriptExecutorService = new JavaScriptExecutorService();

		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		// enable local-file polling
		from(direct(ENABLE_LOCAL_FILE_POLLING_ROUTE_ID))
				.routeId(ENABLE_LOCAL_FILE_POLLING_ROUTE_ID)
				.log("Route invoked!")
				.process(this::enableLocalFilePollingProcessor).id(ENABLE_LOCAL_FILE_POLLING_PROCESSOR_ID)
				.process(exchange -> prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Active)).id(ENABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();

		// disable local-file polling
		from(direct(DISABLE_LOCAL_FILE_POLLING_ROUTE_ID))
				.routeId(DISABLE_LOCAL_FILE_POLLING_ROUTE_ID)
				.log("Route invoked!")
				.process(this::disableLocalFilePollingProcessor).id(DISABLE_LOCAL_FILE_POLLING_PROCESSOR_ID)
				.process(exchange -> prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Inactive)).id(DISABLE_LOCAL_FILE_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();
		//@formatter:on
	}

	private void enableLocalFilePollingProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> params = request.getParameters();

		// Stable per-child route id (child config id based). The route MUST be keyed on this, not on the
		// endpoint name/polled directory — otherwise a later endpoint change orphans this poller (disable
		// recomputes a different key and stops nothing). endpointName is kept only for display / the
		// archive-file fallback.
		final String routeKey = requireRouteKey(params);

		// Tear down any poller already running under this stable key BEFORE (re)creating it.
		// addRoutes() below would replace a same-id route on its own (ModelCamelContext.addRouteDefinitions
		// removes the existing definitions first), but only once the new route BUILDS: a re-enable whose
		// endpoint no longer yields a usable file:// URI fails inside addRoutes and would otherwise leave
		// the PREVIOUS poller running on a directory the endpoint no longer names, while the operator is
		// told the start failed. Stopping first makes the failure honest.
		removePollingRoute(routeKey);

		final String endpointName = params.get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);
		final String scriptIdentifier = params.get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER);

		// LOCAL, transport-agnostic archive folders. Default to a container path when the endpoint's dir
		// fields are unset — resolved exactly as the SFTP transport resolves them.
		final String processedDir = params.getOrDefault(ExternalSystemConstants.PARAM_PROCESSED_DIR, DEFAULT_LOCAL_PROCESSED_DIR);
		final String errorDir = params.getOrDefault(ExternalSystemConstants.PARAM_ERROR_DIR, DEFAULT_LOCAL_ERROR_DIR);

		final String localRootLocation = requireLocalRootLocation(params);
		final String importFileNamePattern = params.get(ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FILE_NAME_PATTERN);
		final long frequencyMs = resolveFrequencyMs(params);

		// Resolve the script repo path lazily at enable time, not at configure time.
		final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(
				getCamelContext().resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));

		getCamelContext().addRoutes(new ScriptedImportConversionLocalFileDynamicRouteBuilder(
				routeKey, endpointName, localRootLocation, importFileNamePattern, frequencyMs, scriptIdentifier,
				javaScriptRepo, javaScriptExecutorService, producerTemplate, processedDir, errorDir));

		getCamelContext().getRouteController().startRoute(routeKey);
		log.info("Dynamic local-file polling route '{}' started successfully.", routeKey);
	}

	private void disableLocalFilePollingProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		// Stop by the SAME stable key the route was started with — matches the running poller regardless of
		// whether the child's endpoint (polled directory / Value) has since changed.
		final String routeKey = requireRouteKey(request.getParameters());

		removePollingRoute(routeKey);

		log.info("Dynamic local-file polling route '{}' stopped and removed.", routeKey);
	}

	/**
	 * The stable per-child route key ({@link ExternalSystemConstants#PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY})
	 * is mandatory — the backend always supplies it for both enable and disable. A missing key would mean
	 * the poller could not be reliably torn down, so fail loudly rather than leak a route.
	 */
	@NonNull
	private static String requireRouteKey(@NonNull final Map<String, String> params)
	{
		final String routeKey = params.get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY);
		if (routeKey == null || routeKey.isBlank())
		{
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY + "' is required!");
		}
		return routeKey;
	}

	/**
	 * The polled directory is the whole transport — without it there is nothing to poll. The dynamic route
	 * builder takes it {@code @NonNull}, so checking it here turns a missing endpoint value into a message
	 * naming the parameter instead of a bare {@code NullPointerException}, the same way the SFTP sibling
	 * validates host/username/authType before building its URI.
	 */
	@NonNull
	private static String requireLocalRootLocation(@NonNull final Map<String, String> params)
	{
		final String localRootLocation = params.get(ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION);
		if (localRootLocation == null || localRootLocation.isBlank())
		{
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_ROOT_LOCATION + "' is required!");
		}
		return localRootLocation;
	}

	/**
	 * Never parses the raw parameter blind: it is omitted entirely when the endpoint carries no frequency
	 * (see {@link #DEFAULT_POLLING_INTERVAL_MS}), and a non-numeric value would otherwise surface as a bare
	 * {@code NumberFormatException} with no hint of which field produced it.
	 */
	private static long resolveFrequencyMs(@NonNull final Map<String, String> params)
	{
		final String frequencyMs = params.getOrDefault(
				ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS, DEFAULT_POLLING_INTERVAL_MS);
		try
		{
			return Long.parseLong(frequencyMs.trim());
		}
		catch (final NumberFormatException e)
		{
			throw new RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_ENDPOINT_FREQUENCY_MS
					+ "' must be a number of milliseconds, but was: " + frequencyMs, e);
		}
	}

	/**
	 * Tears down the dynamic local-file poll route for {@code routeKey}: stops and removes the route if
	 * present. Idempotent — safe to call when nothing is running (used both by disable and by enable's
	 * idempotent-replace pre-clean).
	 */
	private void removePollingRoute(@NonNull final String routeKey) throws Exception
	{
		if (getCamelContext().getRoute(routeKey) != null)
		{
			getCamelContext().getRouteController().stopRoute(routeKey);
			getCamelContext().removeRoute(routeKey);
		}
	}

	private void prepareExternalStatusCreateRequest(@NonNull final Exchange exchange, @NonNull final JsonExternalStatus externalStatus)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final JsonStatusRequest jsonStatusRequest = JsonStatusRequest.builder()
				.status(externalStatus)
				.pInstanceId(request.getAdPInstanceId())
				.build();

		final ExternalStatusCreateCamelRequest camelRequest = ExternalStatusCreateCamelRequest.builder()
				.jsonStatusRequest(jsonStatusRequest)
				.externalSystemConfigType(getExternalSystemTypeCode())
				.externalSystemChildConfigValue(request.getExternalSystemChildConfigValue())
				.serviceValue(getServiceValue())
				.build();

		exchange.getIn().setBody(camelRequest, JsonExternalSystemRequest.class);
	}

	@Override
	public String getServiceValue()
	{
		return "defaultLocalFilePollingScriptedImportConversion";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME;
	}

	@Override
	public String getEnableCommand()
	{
		return ENABLE_LOCAL_FILE_POLLING;
	}

	@Override
	public String getDisableCommand()
	{
		return DISABLE_LOCAL_FILE_POLLING;
	}
}
