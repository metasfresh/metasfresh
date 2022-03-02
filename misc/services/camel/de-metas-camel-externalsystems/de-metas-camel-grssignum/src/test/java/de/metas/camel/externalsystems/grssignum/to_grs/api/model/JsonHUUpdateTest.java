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
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonHUUpdateTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    \"FLAG\": 999,\n"
				+ "    \"ID\": \"huId\",\n"
				+ "    \"dummyValue\": \"TEST\",\n"
				+ "    \"ATTRIBUTES\": \n"
				+ "	    {\n"
				+ "			\"key\": \"value\" \n"
				+ "		}\n"
				+ "}";
		//when
		final JsonHUUpdate jsonHUUpdate = objectMapper.readValue(candidate, JsonHUUpdate.class);

		//then
		final JsonHUUpdate expectedJsonHuUpdate = JsonHUUpdate.builder()
				.id("huId")
				.flag(999)
				.attributes(ImmutableMap.of("key", "value"))
				.build();

		assertThat(jsonHUUpdate).isEqualTo(expectedJsonHuUpdate);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonHUUpdate jsonHUUpdate = JsonHUUpdate.builder()
				.flag(999)
				.id("huId")
				.attributes(ImmutableMap.of("key", "value"))
				.build();

		//when
		final String serialized = objectMapper.writeValueAsString(jsonHUUpdate);

		final JsonHUUpdate deserialized = objectMapper.readValue(serialized, JsonHUUpdate.class);

		//then
		assertThat(deserialized).isEqualTo(jsonHUUpdate);
	}
}
