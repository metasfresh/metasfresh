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

package de.metas.externalsystem.outboundendpoint;

import de.metas.common.externalsystem.endpoint.JsonExternalSystemOutboundEndpoint;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ExternalSystemOutboundEndpointTest
{

	@Test
	void toJson_withAllFields()
	{
		// given
		final ExternalSystemOutboundEndpoint endpoint = ExternalSystemOutboundEndpoint.builder()
				.id(ExternalSystemOutboundEndpointId.ofRepoId(1))
				.value("TestEndpoint")
				.endpointUrl("https://example.com/api")
				.method("POST")
				.authType(OutboundEndpointAuthType.OAuth)
				.clientId("test-client-id")
				.clientSecret("test-client-secret")
				.token("test-token")
				.user("test@example.com")
				.password("test-password")
				.sasSignature("test-signature")
				.build();

		// when
		final JsonExternalSystemOutboundEndpoint json = endpoint.toJson();

		// then
		assertThat(json.getValue()).isEqualTo("TestEndpoint");
		assertThat(json.getEndpointUrl()).isEqualTo("https://example.com/api");
		assertThat(json.getMethod()).isEqualTo("POST");
		assertThat(json.getAuthType()).isEqualTo(OutboundEndpointAuthType.OAuth.toJson());
		assertThat(json.getClientId()).isEqualTo("test-client-id");
		assertThat(json.getClientSecret()).isEqualTo("test-client-secret");
		assertThat(json.getToken()).isEqualTo("test-token");
		assertThat(json.getUser()).isEqualTo("test@example.com");
		assertThat(json.getPassword()).isEqualTo("test-password");
	}
}