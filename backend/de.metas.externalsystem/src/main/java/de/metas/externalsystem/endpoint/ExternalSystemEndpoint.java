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

package de.metas.externalsystem.endpoint;

import de.metas.audit.apirequest.HttpMethod;
import de.metas.common.externalsystem.endpoint.JsonExternalSystemEndpoint;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.springframework.http.MediaType;

import javax.annotation.Nullable;

@Builder
@Value
public class ExternalSystemEndpoint
{
	@NonNull ExternalSystemEndpointId id;

	@NonNull String value;

	@NonNull TransportType transportType;

	// HTTP transport fields
	@Nullable String endpointUrl;

	@Nullable HttpMethod method;

	@Nullable MediaType contentType;

	// HTTP authentication fields
	@Nullable EndpointAuthType authType;

	@Nullable String clientId;

	@Nullable String clientSecret;

	@Nullable String token;

	@Nullable String user;

	@Nullable String password;

	@Nullable String sasSignature;

	// SFTP transport fields
	@Nullable String sftpHost;

	// null when no port is configured. Boxed, like sftpPollingIntervalMs and frequency below, so that
	// "not configured" survives the trip out of PO.get_ValueAsInt, which collapses SQL NULL onto 0.
	@Nullable Integer sftpPort;

	@Nullable String sftpUsername;

	@Nullable SftpAuthType sftpAuthType;

	@Nullable String sshPrivateKey;

	@Nullable String sftpRemotePath;

	@Nullable String sftpFilenamePattern;

	// SFTP inbound-polling settings (poll interval -- SFTP-only).
	@Nullable Integer sftpPollingIntervalMs;

	// LOCAL_FILE transport fields
	@Nullable String localRootLocation;

	@Nullable Integer frequency;

	@Nullable String importFileNamePattern;

	// Local, transport-agnostic archive folders (used across all three transports: HTTP, SFTP, and LOCAL_FILE).
	@Nullable String processedDirectory;

	@Nullable String errorDirectory;

	/**
	 * If TRUE and the upstream scripted-adapter conversion returns a JSON array, the downstream
	 * Camel route dispatches one HTTP/SFTP request per array element. FALSE: one request carries the
	 * whole payload.
	 */
	@Default boolean isArrayFanOut = false;

	/** OAuth2 token endpoint URL the password-grant request is POSTed to. */
	@Nullable String oauthTokenUrl;

	/** Optional OAuth2 scope, e.g. "docuware.platform". */
	@Nullable String oauthScope;

	/** If TRUE the payload is uploaded as multipart/form-data. */
	@Default boolean isFileUpload = false;

	/**
	 * Converts this endpoint to a JSON DTO. The transport type (HTTP, SFTP, or LOCAL_FILE) is always
	 * included; only the HTTP and SFTP outbound-dispatch fields are carried, as LOCAL_FILE endpoints
	 * have no outbound-relevant fields to serialize.
	 */
	@NonNull
	public JsonExternalSystemEndpoint toJson()
	{
		return JsonExternalSystemEndpoint.builder()
				.value(value)
				.transportType(transportType.getCode())
				.endpointUrl(endpointUrl)
				.method(method != null ? method.getCode() : null)
				.authType(authType != null ? authType.toJson() : null)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.token(token)
				.user(user)
				.password(password)
				.sasSignature(sasSignature)
				.contentType(contentType != null ? contentType.toString() : null)
				.sftpHost(sftpHost)
				// boundary guard: JsonExternalSystemEndpoint.sftpPort is @JsonInclude(NON_NULL), so a 0 would go out
				// as "sftpPort": 0 and the SFTP delivery would dial port 0 instead of falling back to the default 22.
				.sftpPort(sftpPort != null && sftpPort > 0 ? sftpPort : null)
				.sftpUsername(sftpUsername)
				.sftpAuthType(sftpAuthType != null ? sftpAuthType.getCode() : null)
				.sshPrivateKey(sshPrivateKey)
				.sftpRemotePath(sftpRemotePath)
				.sftpFilenamePattern(sftpFilenamePattern)
				.arrayFanOut(isArrayFanOut ? Boolean.TRUE : null)
				.oauthTokenUrl(oauthTokenUrl)
				.oauthScope(oauthScope)
				.isFileUpload(isFileUpload ? Boolean.TRUE : null)
				.build();
	}
}
