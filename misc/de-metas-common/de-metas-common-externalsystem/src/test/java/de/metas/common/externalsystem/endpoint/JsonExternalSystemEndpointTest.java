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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JsonExternalSystemEndpointTest
{
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonExternalSystemEndpoint request = JsonExternalSystemEndpoint.builder()
				.value("value")
				.authType(JsonEndpointAuthType.OAuth)
				.token("token")
				.user("user")
				.endpointUrl("endpointUrl")
				.method("method")
				.password("password")
				.sasSignature("sasSignature")
				.clientSecret("clientSecret")
				.clientId("clientId")
				.build();

		final String string = objectMapper.writeValueAsString(request);

		final JsonExternalSystemEndpoint result = objectMapper.readValue(string, JsonExternalSystemEndpoint.class);

		assertThat(result).isEqualTo(request);
	}

	@Test
	void sftpEndpoint_serializeDeserialize() throws Exception
	{
		final JsonExternalSystemEndpoint endpoint = JsonExternalSystemEndpoint.builder()
				.value("sftp-test")
				.transportType("SFTP")
				.sftpHost("sftp.example.com")
				.sftpPort(22)
				.sftpUsername("user")
				.sftpAuthType("PASSWORD")
				.password("secret")
				.sftpRemotePath("/outbound")
				.sftpFilenamePattern("DESADV_{documentno}.json")
				.build();

		final String json = objectMapper.writeValueAsString(endpoint);
		final JsonExternalSystemEndpoint deserialized = objectMapper.readValue(json, JsonExternalSystemEndpoint.class);
		assertThat(deserialized).isEqualTo(endpoint);
	}
}
