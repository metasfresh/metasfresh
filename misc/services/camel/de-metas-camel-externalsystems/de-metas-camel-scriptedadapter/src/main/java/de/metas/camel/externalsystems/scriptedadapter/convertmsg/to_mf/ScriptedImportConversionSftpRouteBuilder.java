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
import jakarta.annotation.PreDestroy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.scriptedadapter.ScriptedAdapterConstants.SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME;
import static de.metas.camel.externalsystems.scriptedadapter.convertmsg.from_mf.ScriptedAdapterConvertMsgFromMFRouteBuilder.PROPERTY_SCRIPTING_REPO_BASE_DIR;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
@RequiredArgsConstructor
public class ScriptedImportConversionSftpRouteBuilder extends RouteBuilder implements IExternalSystemService
{
	private static final String ENABLE_SFTP_POLLING = "enableSftpPolling";
	private static final String DISABLE_SFTP_POLLING = "disableSftpPolling";

	public static final String ENABLE_SFTP_POLLING_ROUTE_ID = SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME + "-" + ENABLE_SFTP_POLLING;
	public static final String DISABLE_SFTP_POLLING_ROUTE_ID = SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME + "-" + DISABLE_SFTP_POLLING;

	public static final String ENABLE_SFTP_POLLING_PROCESSOR_ID = "ScriptedImportConversion-enableSftpPollingProcessor";
	public static final String DISABLE_SFTP_POLLING_PROCESSOR_ID = "ScriptedImportConversion-disableSftpPollingProcessor";

	public static final String ENABLE_SFTP_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-EnableSftp-PrepareStatusReqProcessorId";
	public static final String DISABLE_SFTP_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID = "ScriptedImportConversion-DisableSftp-PrepareStatusReqProcessorId";

	@NonNull private final ProducerTemplate producerTemplate;

	// Track SSH key temp files for cleanup on disable
	private final ConcurrentHashMap<String, Path> sshKeyTempFiles = new ConcurrentHashMap<>();

	private JavaScriptExecutorService javaScriptExecutorService;

	@PreDestroy
	public void cleanupSshKeyTempFiles()
	{
		sshKeyTempFiles.forEach((name, path) -> {
			try
			{
				Files.deleteIfExists(path);
				log.info("Cleaned up SSH key temp file for route '{}': {}", name, path);
			}
			catch (final java.io.IOException e)
			{
				log.warn("Failed to clean up SSH key temp file for route '{}': {}", name, path, e);
			}
		});
		sshKeyTempFiles.clear();
	}

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		javaScriptExecutorService = new JavaScriptExecutorService();

		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		// enable SFTP polling
		from(direct(ENABLE_SFTP_POLLING_ROUTE_ID))
				.routeId(ENABLE_SFTP_POLLING_ROUTE_ID)
				.log("Route invoked!")
				.process(this::enableSftpPollingProcessor).id(ENABLE_SFTP_POLLING_PROCESSOR_ID)
				.process(exchange -> prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Active)).id(ENABLE_SFTP_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();

		// disable SFTP polling
		from(direct(DISABLE_SFTP_POLLING_ROUTE_ID))
				.routeId(DISABLE_SFTP_POLLING_ROUTE_ID)
				.log("Route invoked!")
				.process(this::disableSftpPollingProcessor).id(DISABLE_SFTP_POLLING_PROCESSOR_ID)
				.process(exchange -> prepareExternalStatusCreateRequest(exchange, JsonExternalStatus.Inactive)).id(DISABLE_SFTP_PREPARE_EXTERNAL_STATUS_CREATE_REQ_PROCESSOR_ID)
				.to("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNAL_SYSTEM_STATUS_V2_CAMEL_URI + "}}")
				.end();
		//@formatter:on
	}

	private void enableSftpPollingProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final Map<String, String> params = request.getParameters();

		final String endpointName = params.get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);
		final String scriptIdentifier = params.get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER);

		// SFTP connection parameters
		final String sftpHost = params.get(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST);
		final String sftpPort = params.get(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PORT);
		final String sftpUsername = params.get(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME);
		final String sftpAuthType = params.get(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE);

		// Validate mandatory parameters
		if (sftpHost == null || sftpHost.isBlank())
		{
			throw new org.apache.camel.RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST + "' is required!");
		}
		if (sftpUsername == null || sftpUsername.isBlank())
		{
			throw new org.apache.camel.RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME + "' is required!");
		}
		if (sftpAuthType == null || sftpAuthType.isBlank())
		{
			throw new org.apache.camel.RuntimeCamelException("Parameter '" + ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE + "' is required!");
		}
		final String sftpRemotePath = params.getOrDefault(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_REMOTE_PATH, "/");
		final String pollingIntervalMs = params.getOrDefault(ExternalSystemConstants.PARAM_SFTP_POLLING_INTERVAL_MS, "60000");
		final String processedDir = params.getOrDefault(ExternalSystemConstants.PARAM_SFTP_POLLING_PROCESSED_DIR, ".done");
		final String errorDir = params.getOrDefault(ExternalSystemConstants.PARAM_SFTP_POLLING_ERROR_DIR, ".error");

		// Build SFTP URI
		final StringBuilder sftpUri = new StringBuilder();
		sftpUri.append("sftp://").append(sftpHost);
		if (sftpPort != null && !sftpPort.isEmpty())
		{
			sftpUri.append(":").append(sftpPort);
		}
		sftpUri.append("/").append(sftpRemotePath);
		sftpUri.append("?username=").append(sftpUsername);

		// Auth: password or SSH key
		if (ExternalSystemConstants.SFTP_AUTH_TYPE_SSH_KEY.equals(sftpAuthType))
		{
			final String sshPrivateKey = params.get(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PRIVATE_KEY);
			final Set<PosixFilePermission> ownerOnly = PosixFilePermissions.fromString("rw-------");
			final FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(ownerOnly);
			final Path tempKeyFile = Files.createTempFile("sftp_key_" + endpointName + "_", ".pem", attr);
			Files.writeString(tempKeyFile, sshPrivateKey);
			sshKeyTempFiles.put(endpointName, tempKeyFile);
			sftpUri.append("&privateKeyFile=").append(tempKeyFile.toAbsolutePath());
		}
		else
		{
			final String sftpPassword = params.get(ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PASSWORD);
			sftpUri.append("&password=RAW(").append(sftpPassword).append(")");
		}

		sftpUri.append("&delay=").append(pollingIntervalMs);
		sftpUri.append("&move=").append(processedDir);
		sftpUri.append("&moveFailed=").append(errorDir);
		sftpUri.append("&stepwise=false");
		sftpUri.append("&disconnect=true");
		// NOTE: Host key verification is disabled for convenience. Consider making this configurable for production use.
		sftpUri.append("&strictHostKeyChecking=no");
		sftpUri.append("&useUserKnownHostsFile=false");

		// Add the dynamic route (resolve script repo path lazily at enable time, not at configure time)
		final String finalSftpUri = sftpUri.toString();
		final JavaScriptRepo javaScriptRepo = new JavaScriptRepo(
				getCamelContext().resolvePropertyPlaceholders("{{" + PROPERTY_SCRIPTING_REPO_BASE_DIR + "}}"));
		getCamelContext().addRoutes(new ScriptedImportConversionSftpDynamicRouteBuilder(
				endpointName, finalSftpUri, scriptIdentifier, javaScriptRepo, javaScriptExecutorService, producerTemplate));

		getCamelContext().getRouteController().startRoute(endpointName);
		log.info("Dynamic SFTP polling route '{}' started successfully.", endpointName);
	}

	private void disableSftpPollingProcessor(@NonNull final Exchange exchange) throws Exception
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		final String endpointName = request.getParameters().get(ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME);

		getCamelContext().getRouteController().stopRoute(endpointName);
		getCamelContext().removeRoute(endpointName);

		// Cleanup SSH key temp file if any
		final Path tempKeyFile = sshKeyTempFiles.remove(endpointName);
		if (tempKeyFile != null)
		{
			Files.deleteIfExists(tempKeyFile);
		}

		log.info("Dynamic SFTP polling route '{}' stopped and removed.", endpointName);
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
		return "defaultSftpPollingScriptedImportConversion";
	}

	@Override
	public String getExternalSystemTypeCode()
	{
		return SCRIPTED_IMPORT_CONVERSION_SYSTEM_NAME;
	}

	@Override
	public String getEnableCommand()
	{
		return ENABLE_SFTP_POLLING;
	}

	@Override
	public String getDisableCommand()
	{
		return DISABLE_SFTP_POLLING;
	}
}
