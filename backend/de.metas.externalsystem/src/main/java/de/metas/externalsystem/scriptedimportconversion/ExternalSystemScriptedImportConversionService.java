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

import de.metas.externalsystem.endpoint.ExternalSystemEndpoint;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository;
import de.metas.externalsystem.endpoint.TransportType;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import org.adempiere.exceptions.AdempiereException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_AUTH_TYPE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_HOST;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PASSWORD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PORT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_PRIVATE_KEY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_REMOTE_PATH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ENDPOINT_USERNAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_ERROR_DIR;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_INTERVAL_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_PROCESSED_DIR;

@Service
@RequiredArgsConstructor
public class ExternalSystemScriptedImportConversionService
{
	@NonNull
	private final UserAuthTokenRepository userAuthTokenRepository;

	@NonNull
	private final ExternalSystemEndpointRepository externalSystemEndpointRepository;

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

		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_ENDPOINT_NAME, endpoint.getValue());
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_SCRIPT_IDENTIFIER, config.getScriptIdentifier());
		parameters.put(PARAM_SCRIPTEDADAPTER_TO_MF_TOKEN, token.getAuthToken());

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
			if (config.getSftpPollingIntervalMs() != null)
			{
				parameters.put(PARAM_SFTP_POLLING_INTERVAL_MS, String.valueOf(config.getSftpPollingIntervalMs()));
			}
			if (config.getSftpProcessedDirectory() != null)
			{
				parameters.put(PARAM_SFTP_POLLING_PROCESSED_DIR, config.getSftpProcessedDirectory());
			}
			if (config.getSftpErrorDirectory() != null)
			{
				parameters.put(PARAM_SFTP_POLLING_ERROR_DIR, config.getSftpErrorDirectory());
			}
		}

		return parameters;
	}
}
