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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonHUClearTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    \"FLAG\": 998,\n"
				+ "    \"METASFRESHID\": \"100\",\n"
				+ "    \"dummyValue\": \"TEST\",\n"
				+ "    \"CLEARANCE_STATUS\": \"ClearanceStatus\",\n"
				+ "    \"CLEARANCE_NOTE\": \"ClearanceNote\"\n"
				+ "}";
		//when
		final JsonHUClear jsonHUClear = objectMapper.readValue(candidate, JsonHUClear.class);

		//then
		final JsonHUClear expectedJsonHuClear = JsonHUClear.builder()
				.metasfreshId("100")
				.flag(998)
				.clearanceNote("ClearanceNote")
				.clearanceStatus("ClearanceStatus")
				.build();

		assertThat(jsonHUClear).isEqualTo(expectedJsonHuClear);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonHUClear jsonHUClear = JsonHUClear.builder()
				.metasfreshId("100")
				.flag(998)
				.clearanceNote("ClearanceNote")
				.clearanceStatus("ClearanceStatus")
				.build();

		//when
		final String serialized = objectMapper.writeValueAsString(jsonHUClear);

		final JsonHUClear deserialized = objectMapper.readValue(serialized, JsonHUClear.class);

		//then
		assertThat(deserialized).isEqualTo(jsonHUClear);
	}
}
