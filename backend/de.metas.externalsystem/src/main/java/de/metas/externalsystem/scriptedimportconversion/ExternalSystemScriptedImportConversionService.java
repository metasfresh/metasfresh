/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.scriptedimportconversion;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.externalsystem.ExternalSystemConfigRepository;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpoint;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointId;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository;
import de.metas.externalsystem.endpoint.TransportType;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PASSWORD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PORT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PRIVATE_KEY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_REMOTE_PATH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ERROR_DIR;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_INTERVAL_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PROCESSED_DIR;

@Service
@RequiredArgsConstructor
public class ExternalSystemScriptedImportConversionService
{
	@NonNull
	private final UserAuthTokenRepository userAuthTokenRepository;

	@NonNull
	private final ExternalSystemEndpointRepository externalSystemEndpointRepository;

	@NonNull
	private final ExternalSystemConfigRepository externalSystemConfigRepo;

	/**
	 * Resolve the concrete camel command(s) for a Start/Stop run, per child, deriving REST vs SFTP
	 * from each child's own endpoint transport.
	 *
	 * @param parentId    the parent config whose active children to iterate (used when {@code childConfigId} is null)
	 * @param childConfigId when non-null, resolve only this single child (parentId is ignored)
	 */
	@NonNull
	public ImmutableList<ResolvedChildCommand> resolveCommands(
			@Nullable final ExternalSystemParentConfigId parentId,
			@Nullable final ExternalSystemScriptedImportConversionConfigId childConfigId,
			@NonNull final ScriptedImportConversionIntent intent)
	{
		final ImmutableList<ExternalSystemScriptedImportConversionConfig> children;
		if (childConfigId != null)
		{
			children = ImmutableList.of(externalSystemConfigRepo.getScriptedImportConversionChildById(childConfigId));
		}
		else if (parentId != null)
		{
			children = externalSystemConfigRepo.getScriptedImportConversionChildrenByParentId(parentId);
		}
		else
		{
			throw new AdempiereException("resolveCommands requires either parentId or childConfigId");
		}

		final ImmutableMap<ExternalSystemEndpointId, ExternalSystemEndpoint> endpointsById = externalSystemEndpointRepository.getByIds(
				children.stream()
						.map(ExternalSystemScriptedImportConversionConfig::getExternalSystemEndpointId)
						.collect(ImmutableList.toImmutableList()));

		return children.stream()
				.map(child -> {
					final ExternalSystemEndpointId endpointId = child.getExternalSystemEndpointId();
					final ExternalSystemEndpoint endpoint = endpointsById.get(endpointId);
					if (endpoint == null)
					{
						throw new AdempiereException("No Endpoint found for " + endpointId);
					}
					final ScriptedImportConversionCommand command = ScriptedImportConversionCommand.ofIntentAndTransport(intent, endpoint.getTransportType());
					return new ResolvedChildCommand(child, command);
				})
				.collect(ImmutableList.toImmutableList());
	}

	@Value
	public static class ResolvedChildCommand
	{
		@NonNull ExternalSystemScriptedImportConversionConfig config;
		@NonNull ScriptedImportConversionCommand command;
	}

	@NonNull
	public Map<String, String> getParameters(@NonNull final ExternalSystemScriptedImportConversionConfig config)
	{
		final Map<String, String> parameters = new HashMap<>();

		final UserAuthToken token = userAuthTokenRepository.retrieveOptionalByUserAndRoleId(config.getUserImportId(), RoleId.WEBUI)
				.orElseThrow(() -> new AdempiereException("The scripted-import Importeur has no WEBUI API auth token; the created order candidates cannot be authorised. Assign a valid WEBUI auth token to this user.")
						.appendParametersToMessage()
						.setParameter("AD_User_ID (Importeur)", config.getUserImportId().getRepoId())
						.setParameter("AD_Role_ID", RoleId.WEBUI.getRepoId()));

		final ExternalSystemEndpoint endpoint = externalSystemEndpointRepository.getById(config.getExternalSystemEndpointId());

		// Stable per-child identity for the camel SFTP poll-route id. Keyed on the child config id (never on
		// the endpoint Value/host), so changing this child's endpoint later still lets Stop/disable find and
		// tear down the previously-started poller instead of orphaning it. endpointName stays for display.
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_ROUTE_KEY, "ScriptedImportConversion-" + config.getId().getRepoId());
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, endpoint.getValue());
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, config.getScriptIdentifier());
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN, token.getAuthToken());

		// LOCAL, transport-agnostic archive dirs — used by BOTH the SFTP and the REST import flow for
		// local done/error archiving, so these are not gated on TransportType.SFTP.
		if (endpoint.getProcessedDirectory() != null)
		{
			parameters.put(PARAM_PROCESSED_DIR, endpoint.getProcessedDirectory());
		}
		if (endpoint.getErrorDirectory() != null)
		{
			parameters.put(PARAM_ERROR_DIR, endpoint.getErrorDirectory());
		}

		// Add SFTP endpoint parameters if endpoint uses SFTP transport
		if (endpoint.getTransportType() == TransportType.SFTP)
		{
			parameters.put(PARAM_SFTP_POLLING_ENDPOINT_HOST, endpoint.getSftpHost());
			parameters.put(PARAM_SFTP_POLLING_ENDPOINT_PORT, String.valueOf(endpoint.getSftpPort()));
			parameters.put(PARAM_SFTP_POLLING_ENDPOINT_USERNAME, endpoint.getSftpUsername());
			parameters.put(PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE, endpoint.getSftpAuthType() != null ? endpoint.getSftpAuthType().getCode() : null);
			if (endpoint.getPassword() != null)
			{
				parameters.put(PARAM_SFTP_POLLING_ENDPOINT_PASSWORD, endpoint.getPassword());
			}
			if (endpoint.getSshPrivateKey() != null)
			{
				parameters.put(PARAM_SFTP_POLLING_ENDPOINT_PRIVATE_KEY, endpoint.getSshPrivateKey());
			}
			if (endpoint.getSftpRemotePath() != null)
			{
				parameters.put(PARAM_SFTP_POLLING_ENDPOINT_REMOTE_PATH, endpoint.getSftpRemotePath());
			}
			if (endpoint.getSftpPollingIntervalMs() != null)
			{
				parameters.put(PARAM_SFTP_POLLING_INTERVAL_MS, String.valueOf(endpoint.getSftpPollingIntervalMs()));
			}
		}

		return parameters;
	}
}
