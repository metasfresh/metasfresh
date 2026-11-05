/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class JsonObjectMapperHolderTest
{
	// plain public-field POJO so Jackson binds by field access, no lombok @Jacksonized
	// (which is ambiguous while both Jackson 2 and 3 are on the test classpath)
	static class SimpleDto
	{
		public String name;
		public int value;
	}

	/**
	 * Behaviour restored for the JDK25 / Spring-Boot-3 upgrade: the shared ObjectMapper must IGNORE
	 * unknown JSON properties, matching the Spring-Boot-2 / jackson-2.11 base behaviour the e2e seed
	 * relied on (a hard-coded price {@code currencyCode}, a mobile {@code title} test-step label, …).
	 * Under SB3 Jackson defaults FAIL_ON_UNKNOWN_PROPERTIES to true; the holder disables it again.
	 */
	@Test
	void deserialize_ignores_unknown_properties()
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String jsonWithExtras = "{\"name\":\"abc\",\"value\":7,\"currencyCode\":\"EUR\",\"title\":\"some step label\"}";

		assertThatCode(() -> mapper.readValue(jsonWithExtras, SimpleDto.class))
				.as("unknown JSON properties must be ignored, not rejected")
				.doesNotThrowAnyException();
	}

	@Test
	void deserialize_binds_known_properties_even_with_an_unknown_one() throws Exception
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final SimpleDto dto = mapper.readValue("{\"name\":\"abc\",\"value\":7,\"extra\":true}", SimpleDto.class);
		assertThat(dto.name).isEqualTo("abc");
		assertThat(dto.value).isEqualTo(7);
	}
}
