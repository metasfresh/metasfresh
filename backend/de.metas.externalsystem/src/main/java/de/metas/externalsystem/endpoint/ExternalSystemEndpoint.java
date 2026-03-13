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
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
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
	 * Converts this endpoint to a JSON DTO for HTTP-transport endpoints.
	 * <p>
	 * NOTE: {@link JsonExternalSystemEndpoint} currently only supports HTTP transport.
	 * SFTP fields will be added to the DTO in Task 9.
	 * Calling this method on an SFTP endpoint will throw.
	 */
	@NonNull
	public JsonExternalSystemEndpoint toJson()
	{
		if (transportType != TransportType.HTTP)
		{
			throw new AdempiereException("toJson() is only supported for HTTP transport type; got " + transportType + ". Awaiting DTO extension.");
		}

		return JsonExternalSystemEndpoint.builder()
				.value(value)
				.endpointUrl(Check.assumeNotNull(endpointUrl, "endpointUrl must be set for HTTP transport endpoints"))
				.method(Check.assumeNotNull(method, "method must be set for HTTP transport endpoints").getCode())
				.authType(authType.toJson())
				.clientId(clientId)
				.clientSecret(clientSecret)
				.token(token)
				.user(user)
				.password(password)
				.sasSignature(sasSignature)
				.contentType(contentType != null ? contentType.toString() : null)
				.build();
	}
}
