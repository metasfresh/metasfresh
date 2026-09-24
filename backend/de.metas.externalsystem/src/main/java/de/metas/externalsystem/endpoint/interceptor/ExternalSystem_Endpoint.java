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

package de.metas.externalsystem.endpoint.interceptor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.endpoint.EndpointAuthType;
import de.metas.externalsystem.endpoint.SftpAuthType;
import de.metas.externalsystem.endpoint.TransportType;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Interceptor(I_ExternalSystem_Endpoint.class)
@Component
@RequiredArgsConstructor
public class ExternalSystem_Endpoint
{
	/**
	 * Every column this interceptor knows how to clear, together with how to clear it. This is the single
	 * source of truth for "what does transport X own" — {@link #resetTransportSpecificFields(I_ExternalSystem_Endpoint)}
	 * derives each transport's owned columns from {@link #OWNED_COLUMN_NAMES_BY_TRANSPORT_CODE} and clears
	 * every column here that the new transport does NOT own. Ownership only ever means "do not clear on
	 * switch to this transport", so a column may be owned by more than one transport (e.g.
	 * {@code IsArrayFanOut}, read by both the HTTP and SFTP outbound dispatch) — it is simply listed in
	 * every owning transport's set below. A newly added transport-specific column only needs to be added
	 * HERE and to each set that owns it — never to N per-transport clearing lists.
	 */
	private static final ImmutableMap<String, Consumer<I_ExternalSystem_Endpoint>> CLEAR_ACTIONS_BY_COLUMN_NAME =
			ImmutableMap.<String, Consumer<I_ExternalSystem_Endpoint>>builder()
					// HTTP transport + HTTP authentication (incl. OAuth2)
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_HttpEndPoint, endpoint -> endpoint.setHttpEndPoint(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_OutboundHttpMethod, endpoint -> endpoint.setOutboundHttpMethod(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_ContentType, endpoint -> endpoint.setContentType(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_AuthType, endpoint -> endpoint.setAuthType(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_AuthToken, endpoint -> endpoint.setAuthToken(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_LoginUsername, endpoint -> endpoint.setLoginUsername(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_Password, endpoint -> endpoint.setPassword(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_ClientId, endpoint -> endpoint.setClientId(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_ClientSecret, endpoint -> endpoint.setClientSecret(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SasSignature, endpoint -> endpoint.setSasSignature(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_OAuthTokenUrl, endpoint -> endpoint.setOAuthTokenUrl(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_OAuthScope, endpoint -> endpoint.setOAuthScope(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_IsFileUpload, endpoint -> endpoint.setIsFileUpload(false))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_IsArrayFanOut, endpoint -> endpoint.setIsArrayFanOut(false))
					// SFTP transport
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SftpHost, endpoint -> endpoint.setSftpHost(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort, endpoint -> endpoint.setSftpPort(0))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SftpUsername, endpoint -> endpoint.setSftpUsername(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType, endpoint -> endpoint.setSftpAuthType(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SshPrivateKey, endpoint -> endpoint.setSshPrivateKey(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SftpRemotePath, endpoint -> endpoint.setSftpRemotePath(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SftpFilenamePattern, endpoint -> endpoint.setSftpFilenamePattern(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPollingIntervalMs, endpoint -> endpoint.setSftpPollingIntervalMs(0))
					// LOCAL_FILE transport
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_LocalRootLocation, endpoint -> endpoint.setLocalRootLocation(null))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_Frequency, endpoint -> endpoint.setFrequency(0))
					.put(I_ExternalSystem_Endpoint.COLUMNNAME_ImportFileNamePattern, endpoint -> endpoint.setImportFileNamePattern(null))
					.build();

	private static final ImmutableSet<String> HTTP_OWNED_COLUMN_NAMES = ImmutableSet.of(
			I_ExternalSystem_Endpoint.COLUMNNAME_HttpEndPoint,
			I_ExternalSystem_Endpoint.COLUMNNAME_OutboundHttpMethod,
			I_ExternalSystem_Endpoint.COLUMNNAME_ContentType,
			I_ExternalSystem_Endpoint.COLUMNNAME_AuthType,
			I_ExternalSystem_Endpoint.COLUMNNAME_AuthToken,
			I_ExternalSystem_Endpoint.COLUMNNAME_LoginUsername,
			I_ExternalSystem_Endpoint.COLUMNNAME_Password,
			I_ExternalSystem_Endpoint.COLUMNNAME_ClientId,
			I_ExternalSystem_Endpoint.COLUMNNAME_ClientSecret,
			I_ExternalSystem_Endpoint.COLUMNNAME_SasSignature,
			I_ExternalSystem_Endpoint.COLUMNNAME_OAuthTokenUrl,
			I_ExternalSystem_Endpoint.COLUMNNAME_OAuthScope,
			I_ExternalSystem_Endpoint.COLUMNNAME_IsFileUpload,
			I_ExternalSystem_Endpoint.COLUMNNAME_IsArrayFanOut);

	private static final ImmutableSet<String> SFTP_OWNED_COLUMN_NAMES = ImmutableSet.of(
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpHost,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpUsername,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType,
			I_ExternalSystem_Endpoint.COLUMNNAME_SshPrivateKey,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpRemotePath,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpFilenamePattern,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpPollingIntervalMs,
			// transport-agnostic, also HTTP-owned below: the SFTP outbound dispatch reads it too (see
			// ScriptedAdapterConvertMsgFromMFRouteBuilder#isFanOutEnabled), so switching to SFTP must not
			// clear it
			I_ExternalSystem_Endpoint.COLUMNNAME_IsArrayFanOut);

	private static final ImmutableSet<String> LOCAL_FILE_OWNED_COLUMN_NAMES = ImmutableSet.of(
			I_ExternalSystem_Endpoint.COLUMNNAME_LocalRootLocation,
			I_ExternalSystem_Endpoint.COLUMNNAME_Frequency,
			I_ExternalSystem_Endpoint.COLUMNNAME_ImportFileNamePattern);

	/** Maps each transport's DB code to the set of columns it owns (a column may appear in more than one set). */
	private static final ImmutableMap<String, ImmutableSet<String>> OWNED_COLUMN_NAMES_BY_TRANSPORT_CODE = ImmutableMap.of(
			TransportType.HTTP.getCode(), HTTP_OWNED_COLUMN_NAMES,
			TransportType.SFTP.getCode(), SFTP_OWNED_COLUMN_NAMES,
			TransportType.LOCAL_FILE.getCode(), LOCAL_FILE_OWNED_COLUMN_NAMES);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_ExternalSystem_Endpoint.COLUMNNAME_AuthType)
	public void resetHttpCredentials(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		final EndpointAuthType newAuthType = EndpointAuthType.ofNullableCode(endpoint.getAuthType());
		if (newAuthType == null)
		{
			return;
		}

		switch (newAuthType)
		{
			case Basic:
				endpoint.setAuthToken(null);
				endpoint.setClientId(null);
				endpoint.setClientSecret(null);
				break;
			case Token:
				endpoint.setLoginUsername(null);
				endpoint.setPassword(null);
				endpoint.setClientId(null);
				endpoint.setClientSecret(null);
				break;
			case OAuth:
				endpoint.setLoginUsername(null);
				endpoint.setPassword(null);
				endpoint.setAuthToken(null);
				break;
			case SAS:
				endpoint.setLoginUsername(null);
				endpoint.setPassword(null);
				endpoint.setAuthToken(null);
				endpoint.setClientId(null);
				endpoint.setClientSecret(null);
				break;
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_ExternalSystem_Endpoint.COLUMNNAME_TransportType)
	public void resetTransportSpecificFields(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		final String newTransportType = endpoint.getTransportType();
		final ImmutableSet<String> ownedColumnNames = OWNED_COLUMN_NAMES_BY_TRANSPORT_CODE.get(newTransportType);
		if (ownedColumnNames == null)
		{
			// unset/unrecognized transport type: nothing to clear, mirrors the previous no-op behaviour
			return;
		}

		// clear every OTHER transport's fields: the complement of this transport's own columns within
		// CLEAR_ACTIONS_BY_COLUMN_NAME. Transport-agnostic columns (e.g. ProcessedDirectory/ErrorDirectory)
		// never appear in CLEAR_ACTIONS_BY_COLUMN_NAME, so they are never touched here.
		CLEAR_ACTIONS_BY_COLUMN_NAME.forEach((columnName, clearAction) -> {
			if (!ownedColumnNames.contains(columnName))
			{
				clearAction.accept(endpoint);
			}
		});
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType)
	public void resetSftpCredentials(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		final String newSftpAuthType = endpoint.getSftpAuthType();
		if (SftpAuthType.PASSWORD.getCode().equals(newSftpAuthType))
		{
			endpoint.setSshPrivateKey(null);
		}
		else if (SftpAuthType.SSH_KEY.getCode().equals(newSftpAuthType))
		{
			endpoint.setPassword(null);
		}
	}
}
