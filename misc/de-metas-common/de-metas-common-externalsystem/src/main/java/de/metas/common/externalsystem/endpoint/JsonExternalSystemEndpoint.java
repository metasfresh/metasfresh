/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem.endpoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Value
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonExternalSystemEndpoint
{
	@NonNull String value;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String transportType;  // "HTTP" or "SFTP"

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String endpointUrl;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String method;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonEndpointAuthType authType;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String clientId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String clientSecret;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String token;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String user;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String password;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String sasSignature;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String contentType;

	// SFTP transport fields

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String sftpHost;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	Integer sftpPort;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String sftpUsername;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String sftpAuthType;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String sshPrivateKey;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String sftpRemotePath;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String sftpFilenamePattern;
}
