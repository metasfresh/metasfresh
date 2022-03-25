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

public class JsonVendorContactTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    \"METASFRESHID\": 12345,\n"
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
				+ "    	\"ROLLEN\":[\n "
				+ "						\"ROLE1\",\n"
				+ "						\"ROLE2\"\n"
				+ "    		]\n"
				+ "}";

		//when
		final JsonVendorContact vendorContact = objectMapper.readValue(candidate, JsonVendorContact.class);

		//then
		final JsonVendorContact expectedVendorContact = createJsonVendorContact();

		assertThat(vendorContact).isEqualTo(expectedVendorContact);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonVendorContact vendorContact = createJsonVendorContact();

		//when
		final String serialized = objectMapper.writeValueAsString(vendorContact);

		final JsonVendorContact deserialized = objectMapper.readValue(serialized, JsonVendorContact.class);

		//then
		assertThat(deserialized).isEqualTo(vendorContact);
	}

	@NonNull
	@Builder(builderMethodName = "createJsonVendorContactBuilder", builderClassName = "JsonVendorContactBuilder")
	public static JsonVendorContact createJsonVendorContact()
	{
		return JsonVendorContact.builder()
				.lastName("LASTNAME")
				.firstName("FIRSTNAME")
				.greeting("GREETING")
				.title("TITLE")
				.position("POSITION")
				.email("EMAIL")
				.phone("TELEFON")
				.fax("FAX")
				.phone2("TELEFON2")
				.contactRoles(ImmutableList.of("ROLE1", "ROLE2"))
				.metasfreshId(JsonMetasfreshId.of(12345))
				.build();
	}
}
