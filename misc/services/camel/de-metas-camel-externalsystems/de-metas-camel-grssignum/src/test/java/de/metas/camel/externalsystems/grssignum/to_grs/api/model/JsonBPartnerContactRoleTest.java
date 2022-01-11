/*
 * #%L
 * metas
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Builder;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonBPartnerContactRoleTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    		\"ROLLE\": \"ROLE1\"\n"
				+ "			}";

		//when
		final JsonBPartnerContactRole partnerContactRole = objectMapper.readValue(candidate, JsonBPartnerContactRole.class);

		//then
		final JsonBPartnerContactRole expectedBPartnerContactRole = JsonBPartnerContactRole.builder()
				.role("ROLE1")
				.build();

		assertThat(partnerContactRole).isEqualTo(expectedBPartnerContactRole);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonBPartnerContactRole bPartnerContactRole = createJsonBPartnerContactRole("ROLE1");

		//when
		final String serialized = objectMapper.writeValueAsString(bPartnerContactRole);

		final JsonBPartnerContactRole deserialized = objectMapper.readValue(serialized, JsonBPartnerContactRole.class);

		//then
		assertThat(deserialized).isEqualTo(bPartnerContactRole);
	}

	@NonNull
	@Builder(builderMethodName = "createJsonBPartnerContactRoleBuilder", builderClassName = "JsonBPartnerContactRoleBuilder")
	public static JsonBPartnerContactRole createJsonBPartnerContactRole(
			@NonNull final String roleName)
	{
		return JsonBPartnerContactRole.builder()
				.role(roleName)
				.build();
	}
}
