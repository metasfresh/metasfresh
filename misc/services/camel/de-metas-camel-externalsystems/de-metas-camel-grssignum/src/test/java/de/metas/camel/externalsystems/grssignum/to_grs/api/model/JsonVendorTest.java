/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class JsonVendorTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    \"FLAG\": 100,\n"
				+ "    \"MID\": \"HOM\",\n"
				+ "    \"DUMMYVALUE\": \"TEST\",\n"
				+ "    \"MKREDID\": \"value\",\n"
				+ "    \"KURZBEZEICHNUNG\": \"test company name\",\n"
				+ "    \"NAMENSZUSATZ\": \"test name2\",\n"
				+ "    \"ADRESSE 1\": \"test address1\",\n"
				+ "    \"ADRESSE 2\": \"test address2\",\n"
				+ "    \"ADRESSE 3\": \"test address3\",\n"
				+ "    \"ADRESSE 4\": \"test address4\",\n"
				+ "    \"PLZ\": \"test postal\",\n"
				+ "    \"ORT\": \"test city\",\n"
				+ "    \"LANDESCODE\": \"test country code\",\n"
				+ "    \"GLN\": \"test gln\",\n"
				+ "    \"INAKTIV\": 0,\n"
				+ "    \"METASFRESHID\":\"12345\",\n"
				+ "    \"METASFRESHURL\": \"metasfreshUrl\",\n"
				+ "    \"KREDITORENNR\":1,\n"
				+ "    \"DEBITORENNR\":2,\n"
				+ "    \"KONTAKTE\":[\n "
				+ "        {\n"
				+ "   				 \"METASFRESHID\": 12345,\n"
				+ "   				 \"NACHNAME\": \"LASTNAME\",\n"
				+ "   				 \"DUMMYVALUE\": \"TEST\",\n"
				+ "    				 \"VORNAME\": \"FIRSTNAME\",\n"
				+ "    				 \"ANREDE\": \"GREETING\",\n"
				+ "   				 \"TITEL\": \"TITLE\",\n"
				+ "    				 \"POSITION\": \"POSITION\",\n"
				+ "    				 \"EMAIL\": \"EMAIL\",\n"
				+ "   				 \"TELEFON\": \"TELEFON\",\n"
				+ "   				 \"MOBIL\": \"TELEFON2\",\n"
				+ "   				 \"FAX\": \"FAX\",\n"
				+ "    				 \"ROLLEN\":[\n "
				+ "									\"ROLE1\",\n"
				+ "									\"ROLE2\"\n"
				+ "    					]\n"
				+ "			}\n"
				+ "    	]\n"
				+ "}";

		//when
		final JsonVendor partner = objectMapper.readValue(candidate, JsonVendor.class);

		//then
		final JsonVendorContact contact = JsonVendorContactTest.createJsonVendorContact();

		final JsonVendor expectedVendor = JsonVendor.builder()
				.flag(100)
				.bpartnerValue("value")
				.name("test company name")
				.name2("test name2")
				.address1("test address1")
				.address2("test address2")
				.address3("test address3")
				.address4("test address4")
				.postal("test postal")
				.city("test city")
				.countryCode("test country code")
				.gln("test gln")
				.inactive(0)
				.tenantId("HOM")
				.contact(contact)
				.metasfreshId("12345")
				.metasfreshURL("metasfreshUrl")
				.creditorId(1)
				.debtorId(2)
				.build();

		assertThat(partner).isEqualTo(expectedVendor);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final List<String> roles = ImmutableList.of("ROLE1", "ROLE2");

		final JsonVendorContact contact = JsonVendorContact.builder()
				.metasfreshId(JsonMetasfreshId.of(12345))
				.lastName("LASTNAME")
				.firstName("FIRSTNAME")
				.greeting("GREETING")
				.title("TITLE")
				.position("POSITION")
				.email("EMAIL")
				.phone("TELEFON")
				.fax("FAX")
				.phone2("TELEFON2")
				.contactRoles(roles)
				.build();

		final JsonVendor vendor = JsonVendor.builder()
				.flag(100)
				.bpartnerValue("value")
				.name("test company name")
				.name2("test name2")
				.address1("test address1")
				.address2("test address2")
				.address3("test address3")
				.address4("test address4")
				.postal("test postal")
				.city("test city")
				.countryCode("test country code")
				.gln("test gln")
				.inactive(0)
				.tenantId("HOM")
				.contact(contact)
				.metasfreshId("12345")
				.metasfreshURL("metasfreshUrl")
				.creditorId(1)
				.debtorId(2)
				.build();

		//when
		final String serialized = objectMapper.writeValueAsString(vendor);

		final JsonVendor deserialized = objectMapper.readValue(serialized, JsonVendor.class);

		//then
		assertThat(deserialized).isEqualTo(vendor);
	}
}
