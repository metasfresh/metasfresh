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

package de.metas.camel.externalsystems.grssignum.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonBPartnerTest
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
				+ "    \"MKREDID\": \"102\",\n"
				+ "    \"KURZBEZEICHNUNG\": \"test company name\",\n"
				+ "    \"INAKTIV\": 0\n"
				+ "}";

		//when
		final JsonBPartner partner = objectMapper.readValue(candidate, JsonBPartner.class);

		//then
		final JsonBPartner expectedBPartner = JsonBPartner.builder()
				.flag(100)
				.id("102")
				.name("test company name")
				.inactive(0)
				.tenantId("HOM")
				.build();

		assertThat(partner).isEqualTo(expectedBPartner);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonBPartner bPartner = JsonBPartner.builder()
				.flag(100)
				.id("102")
				.name("test company name")
				.inactive(0)
				.tenantId("HOM")
				.build();

		//when
		final String serialized = objectMapper.writeValueAsString(bPartner);

		final JsonBPartner deserialized = objectMapper.readValue(serialized, JsonBPartner.class);

		//then
		assertThat(deserialized).isEqualTo(bPartner);
	}
}
