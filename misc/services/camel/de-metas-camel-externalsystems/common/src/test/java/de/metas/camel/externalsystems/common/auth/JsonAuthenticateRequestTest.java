/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class JsonAuthenticateRequestTest
{
	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonAuthenticateRequest request = JsonAuthenticateRequest.builder()
				.grantedAuthority("grantedAuthority")
				.authKey("authKey")
				.orgCode("123")
				.pInstance(JsonMetasfreshId.of(1))
				.build();

		final String string = mapper.writeValueAsString(request);

		final JsonAuthenticateRequest result = mapper.readValue(string, JsonAuthenticateRequest.class);

		assertThat(result).isEqualTo(request);
	}
}
