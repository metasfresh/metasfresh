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

import static org.assertj.core.api.Assertions.*;

public class JsonCustomerTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    \"FLAG\": 500,\n"
				+ "    \"METASFRESHID\": 12345,\n"
				+ "    \"MKDID\": \"TEST\",\n"
				+ "    \"METASFRESHURL\": \"TEST\",\n"
				+ "    \"MATCHCODE\": \"NAME\",\n"
				+ "    \"MID\": \"HOM\",\n"
				+ "    \"KREDITORENNR\":1,\n"
				+ "    \"DEBITORENNR\":2,\n"
				+ "    \"INAKTIV\": 0,\n"
				+ "    \"KDDATA\":[\n "
				+ "        {\n"
				+ "   				\"METASFRESHID\": 12345,\n"
				+ "   				\"NAME\": \"NAME\",\n"
				+ "    				\"ADRESSE 1\": \"test address1\",\n"
				+ "    				\"ADRESSE 2\": \"test address2\",\n"
				+ "    				\"ADRESSE 3\": \"test address3\",\n"
				+ "    				\"ADRESSE 4\": \"test address4\",\n"
				+ "    				\"PLZ\": \"test postal\",\n"
				+ "    				\"ORT\": \"test city\",\n"
				+ "    				\"LANDESCODE\": \"test country code\",\n"
				+ "    				\"GLN\": \"test gln\",\n"
				+ "    				\"INAKTIV\": 0,\n"
				+ "    				\"LIEFERADRESSE\": true,\n"
				+ "    				\"RECHNUNGSADDRESSE\": true,\n"
				+ "    				\"HAUPTADDRESSE\": 1\n"
				+ "			}\n"
				+ "    	],\n"
				+ "    \"PERSDATA\":[\n "
				+ "        {\n"
				+ "   				\"METASFRESHID\": 2205180,\n"
				+ "   				\"FULLNAME\": \"contact firstname contact lastname\",\n"
				+ "    				\"NACHNAME\": \"contact lastname\",\n"
				+ "    				\"VORNAME\": \"contact firstname\",\n"
				+ "    				\"ANREDE\": \"test\",\n"
				+ "    				\"TITEL\": \"title\",\n"
				+ "    				\"POSITION\": \"position\",\n"
				+ "    				\"EMAIL\": \"email\",\n"
				+ "    				\"TELEFON\": \"phone\",\n"
				+ "    				\"MOBIL\": \"phone2\",\n"
				+ "    				\"FAX\": \"fax\",\n"
				+ "    				\"INAKTIV\": 0\n"
				+ "			}\n"
				+ "    	],\n"
				+ "    \"REZDET\":[\n "
				+ "					\"someExternalRef\"\n"
				+ "    			  ]\n"
				+ "}";

		//when
		final JsonCustomer partner = objectMapper.readValue(candidate, JsonCustomer.class);

		//then
		final JsonCustomerLocation location = JsonCustomerLocationTest.createJsonCustomerLocation();
		final JsonCustomerContact contact = JsonCustomerContactTest.createJsonCustomerContact();

		final JsonCustomer expectedCustomer = JsonCustomer.builder()
				.flag(500)
				.id(JsonMetasfreshId.of(12345))
				.bpartnerValue("TEST")
				.url("TEST")
				.name("NAME")
				.tenantId("HOM")
				.creditorId(1)
				.debtorId(2)
				.inactive(0)
				.locations(ImmutableList.of(location))
				.contacts(ImmutableList.of(contact))
				.bpartnerProductExternalReferences(ImmutableList.of("someExternalRef"))
				.build();

		assertThat(partner).isEqualTo(expectedCustomer);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given

		final JsonCustomerLocation location = JsonCustomerLocation.builder()
				.metasfreshId(JsonMetasfreshId.of(12345))
				.name("NAME")
				.address1("test address1")
				.address2("test address2")
				.address3("test address3")
				.address4("test address4")
				.postal("test postal")
				.city("test city")
				.countryCode("test country code")
				.gln("test gln")
				.inactive(0)
				.shipTo(true)
				.billTo(true)
				.mainAddress(1)
				.build();

		final JsonCustomerContact contact = JsonCustomerContact.builder()
				.metasfreshId(JsonMetasfreshId.of(2205180))
				.fullName("contact firstname contact lastname")
				.lastName("contact lastname")
				.firstName("contact firstname")
				.greeting("test")
				.title("title")
				.position("position")
				.phone("phone")
				.phone2("phone2")
				.fax("fax")
				.inactive(0)
				.build();

		final JsonCustomer customer = JsonCustomer.builder()
				.flag(500)
				.id(JsonMetasfreshId.of(12345))
				.bpartnerValue("TEST")
				.url("TEST")
				.name("NAME")
				.tenantId("HOM")
				.creditorId(1)
				.debtorId(2)
				.inactive(0)
				.contacts(ImmutableList.of(contact))
				.locations(ImmutableList.of(location))
				.bpartnerProductExternalReferences(ImmutableList.of("someExternalReference"))
				.build();

		//when
		final String serialized = objectMapper.writeValueAsString(customer);

		final JsonCustomer deserialized = objectMapper.readValue(serialized, JsonCustomer.class);

		//then
		assertThat(deserialized).isEqualTo(customer);
	}
}
