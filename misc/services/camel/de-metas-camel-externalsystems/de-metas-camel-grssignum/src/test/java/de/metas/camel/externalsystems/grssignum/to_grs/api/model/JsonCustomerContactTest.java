/*
 * #%L
 * de-metas-camel-grssignum
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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonCustomerContactTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "   		\"METASFRESHID\": 2205180,\n"
				+ "   		\"FULLNAME\": \"contact firstname contact lastname\",\n"
				+ "    		\"NACHNAME\": \"contact lastname\",\n"
				+ "    		\"VORNAME\": \"contact firstname\",\n"
				+ "    		\"ANREDE\": \"test\",\n"
				+ "    		\"TITEL\": \"title\",\n"
				+ "    		\"POSITION\": \"position\",\n"
				+ "    		\"EMAIL\": \"email\",\n"
				+ "    		\"TELEFON\": \"phone\",\n"
				+ "    		\"MOBIL\": \"phone2\",\n"
				+ "    		\"FAX\": \"fax\",\n"
				+ "    		\"INAKTIV\": 0\n"
				+ "}";

		//when
		final JsonCustomerContact partnerContact = objectMapper.readValue(candidate, JsonCustomerContact.class);

		//then
		final JsonCustomerContact expectedCustomerContact = createJsonCustomerContact();

		assertThat(partnerContact).isEqualTo(expectedCustomerContact);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonCustomerContact bPartnerContact = createJsonCustomerContact();

		//when
		final String serialized = objectMapper.writeValueAsString(bPartnerContact);

		final JsonCustomerContact deserialized = objectMapper.readValue(serialized, JsonCustomerContact.class);

		//then
		assertThat(deserialized).isEqualTo(bPartnerContact);
	}

	@NonNull
	@Builder(builderMethodName = "createJsonCustomerContactBuilder", builderClassName = "JsonCustomerContactBuilder")
	public static JsonCustomerContact createJsonCustomerContact()
	{
		return JsonCustomerContact.builder()
				.metasfreshId(JsonMetasfreshId.of(2205180))
				.fullName("contact firstname contact lastname")
				.lastName("contact lastname")
				.firstName("contact firstname")
				.greeting("test")
				.title("title")
				.position("position")
				.email("email")
				.phone("phone")
				.phone2("phone2")
				.fax("fax")
				.inactive(0)
				.build();
	}
}
