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

	// HTTP transport fields (null when transportType == SFTP)
	@Nullable String endpointUrl;

	@Nullable HttpMethod method;

	@Nullable MediaType contentType;

	// HTTP authentication fields
	@NonNull EndpointAuthType authType;

	@Nullable String clientId;

	@Nullable String clientSecret;

	@Nullable String token;

	@Nullable String user;

	@Nullable String password;

	@Nullable String sasSignature;

	// SFTP transport fields (null when transportType == HTTP)
	@Nullable String sftpHost;

	int sftpPort;

	@Nullable String sftpUsername;

	@Nullable SftpAuthType sftpAuthType;

	@Nullable String sshPrivateKey;

	@Nullable String sftpRemotePath;

	@Nullable String sftpFilenamePattern;

	/**
	 * Converts this endpoint to a JSON DTO.
	 * Supports both HTTP and SFTP transport types.
	 */
	@NonNull
	public JsonExternalSystemEndpoint toJson()
	{
		return JsonExternalSystemEndpoint.builder()
				.value(value)
				.transportType(transportType.getCode())
				.endpointUrl(endpointUrl)
				.method(method != null ? method.getCode() : null)
				.authType(authType.toJson())
				.clientId(clientId)
				.clientSecret(clientSecret)
				.token(token)
				.user(user)
				.password(password)
				.sasSignature(sasSignature)
				.contentType(contentType != null ? contentType.toString() : null)
				.sftpHost(sftpHost)
				.sftpPort(sftpPort > 0 ? sftpPort : null)
				.sftpUsername(sftpUsername)
				.sftpAuthType(sftpAuthType != null ? sftpAuthType.getCode() : null)
				.sshPrivateKey(sshPrivateKey)
				.sftpRemotePath(sftpRemotePath)
				.sftpFilenamePattern(sftpFilenamePattern)
				.build();
	}
}
