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
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonBPartnerContactTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    \"METASFRESHID\": 100,\n"
				+ "    \"NACHNAME\": \"LASTNAME\",\n"
				+ "    \"DUMMYVALUE\": \"TEST\",\n"
				+ "    \"VORNAME\": \"FIRSTNAME\",\n"
				+ "    \"ANREDE\": \"GREETING\",\n"
				+ "    \"TITEL\": \"TITLE\",\n"
				+ "    \"POSITION\": \"POSITION\",\n"
				+ "    \"EMAIL\": \"EMAIL\",\n"
				+ "    \"TELEFON\": \"TELEFON\",\n"
				+ "    \"MOBIL\": \"TELEFON2\",\n"
				+ "    \"FAX\": \"FAX\",\n"
				+ "    \"ROLLEN\":[\n "
				+ "        {\n"
				+ "				\"ROLLE\": \"ROLE1\"\n"
				+ "        },\n"
				+ "        {\n"
				+ "				\"ROLLE\": \"ROLE2\" \n"
				+ "        }\n"
				+ "    ]\n"

				+ "}";

		//when
		final JsonBPartnerContact partnerContact = objectMapper.readValue(candidate, JsonBPartnerContact.class);

		//then
		final JsonBPartnerContact expectedBPartnerContact = createJsonBPartnerContact();

		assertThat(partnerContact).isEqualTo(expectedBPartnerContact);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonBPartnerContact bPartnerContact = createJsonBPartnerContact();

		//when
		final String serialized = objectMapper.writeValueAsString(bPartnerContact);

		final JsonBPartnerContact deserialized = objectMapper.readValue(serialized, JsonBPartnerContact.class);

		//then
		assertThat(deserialized).isEqualTo(bPartnerContact);
	}

	@NonNull
	@Builder(builderMethodName = "createJsonBPartnerContactBuilder", builderClassName = "JsonBPartnerContactBuilder")
	public static JsonBPartnerContact createJsonBPartnerContact()
	{
		final JsonBPartnerContactRole role1 = JsonBPartnerContactRoleTest.createJsonBPartnerContactRole("ROLE1");

		final JsonBPartnerContactRole role2 = JsonBPartnerContactRoleTest.createJsonBPartnerContactRole("ROLE2");

		return JsonBPartnerContact.builder()
				.metasfreshId(JsonMetasfreshId.of(100))
				.lastName("LASTNAME")
				.firstName("FIRSTNAME")
				.greeting("GREETING")
				.title("TITLE")
				.position("POSITION")
				.email("EMAIL")
				.phone("TELEFON")
				.fax("FAX")
				.phone2("TELEFON2")
				.contactRoles(ImmutableList.of(role1, role2))
				.build();
	}
}
