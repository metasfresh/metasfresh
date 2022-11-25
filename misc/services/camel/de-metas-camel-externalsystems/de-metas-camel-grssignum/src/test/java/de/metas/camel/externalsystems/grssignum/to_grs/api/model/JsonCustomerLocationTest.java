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

public class JsonCustomerLocationTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
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
				+ "}";

		//when
		final JsonCustomerLocation customerLocation = objectMapper.readValue(candidate, JsonCustomerLocation.class);

		//then
		final JsonCustomerLocation expectedCustomerLocation = createJsonCustomerLocation();

		assertThat(customerLocation).isEqualTo(expectedCustomerLocation);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonCustomerLocation customerLocation = createJsonCustomerLocation();

		//when
		final String serialized = objectMapper.writeValueAsString(customerLocation);

		final JsonCustomerLocation deserialized = objectMapper.readValue(serialized, JsonCustomerLocation.class);

		//then
		assertThat(deserialized).isEqualTo(customerLocation);
	}

	@NonNull
	@Builder(builderMethodName = "createJsonCustomerLocationBuilder", builderClassName = "JsonCustomerLocationBuilder")
	public static JsonCustomerLocation createJsonCustomerLocation()
	{
		return JsonCustomerLocation.builder()
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
	}
}