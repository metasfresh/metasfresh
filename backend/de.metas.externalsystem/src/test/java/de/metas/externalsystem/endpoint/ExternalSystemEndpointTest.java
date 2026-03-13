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
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ExternalSystemEndpointTest
{

	@Test
	void toJson_withAllFields()
	{
		// given
		final ExternalSystemEndpoint endpoint = ExternalSystemEndpoint.builder()
				.id(ExternalSystemEndpointId.ofRepoId(1))
				.value("TestEndpoint")
				.transportType(TransportType.HTTP)
				.endpointUrl("https://example.com/api")
				.method(HttpMethod.POST)
				.authType(EndpointAuthType.OAuth)
				.clientId("test-client-id")
				.clientSecret("test-client-secret")
				.token("test-token")
				.user("test@example.com")
				.password("test-password")
				.sasSignature("test-signature")
				.contentType(MediaType.parseMediaType("application/json"))
				.build();

		// when
		final JsonExternalSystemEndpoint json = endpoint.toJson();

		// then
		assertThat(json.getValue()).isEqualTo("TestEndpoint");
		assertThat(json.getEndpointUrl()).isEqualTo("https://example.com/api");
		assertThat(json.getMethod()).isEqualTo("POST");
		assertThat(json.getAuthType()).isEqualTo(EndpointAuthType.OAuth.toJson());
		assertThat(json.getClientId()).isEqualTo("test-client-id");
		assertThat(json.getClientSecret()).isEqualTo("test-client-secret");
		assertThat(json.getToken()).isEqualTo("test-token");
		assertThat(json.getUser()).isEqualTo("test@example.com");
		assertThat(json.getPassword()).isEqualTo("test-password");
	}

	@Test
	void build_sftpEndpoint_withAllSftpFields()
	{
		// given / when
		final ExternalSystemEndpoint endpoint = ExternalSystemEndpoint.builder()
				.id(ExternalSystemEndpointId.ofRepoId(2))
				.value("SftpEndpoint")
				.transportType(TransportType.SFTP)
				.authType(EndpointAuthType.Basic)
				.sftpHost("sftp.example.com")
				.sftpPort(22)
				.sftpUsername("sftpuser")
				.sftpAuthType(SftpAuthType.PASSWORD)
				.sftpRemotePath("/upload/edi")
				.sftpFilenamePattern("order_{date}.edi")
				.build();

		// then
		assertThat(endpoint.getTransportType()).isEqualTo(TransportType.SFTP);
		assertThat(endpoint.getSftpHost()).isEqualTo("sftp.example.com");
		assertThat(endpoint.getSftpPort()).isEqualTo(22);
		assertThat(endpoint.getSftpUsername()).isEqualTo("sftpuser");
		assertThat(endpoint.getSftpAuthType()).isEqualTo(SftpAuthType.PASSWORD);
		assertThat(endpoint.getSftpRemotePath()).isEqualTo("/upload/edi");
		assertThat(endpoint.getSftpFilenamePattern()).isEqualTo("order_{date}.edi");
		// HTTP fields are null for SFTP endpoints
		assertThat(endpoint.getEndpointUrl()).isNull();
		assertThat(endpoint.getMethod()).isNull();
		assertThat(endpoint.getContentType()).isNull();
	}

	@Test
	void toJson_throwsForSftpTransport()
	{
		// given
		final ExternalSystemEndpoint endpoint = ExternalSystemEndpoint.builder()
				.id(ExternalSystemEndpointId.ofRepoId(3))
				.value("SftpEndpoint")
				.transportType(TransportType.SFTP)
				.authType(EndpointAuthType.Basic)
				.sftpHost("sftp.example.com")
				.sftpPort(22)
				.build();

		// when / then
		assertThatThrownBy(endpoint::toJson)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("HTTP transport");
	}
}
