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

class JsonExternalSystemOutboundEndpointTest
{
	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonExternalSystemOutboundEndpoint request = JsonExternalSystemOutboundEndpoint.builder()
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

		final String string = mapper.writeValueAsString(request);

		final JsonExternalSystemOutboundEndpoint result = mapper.readValue(string, JsonExternalSystemOutboundEndpoint.class);

		assertThat(result).isEqualTo(request);
	}
}