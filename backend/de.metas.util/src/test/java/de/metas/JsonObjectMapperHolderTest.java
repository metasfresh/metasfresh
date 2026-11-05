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
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonObjectMapperHolderTest
{
	// plain public-field POJO so Jackson binds by field access, no lombok @Jacksonized
	// (which is ambiguous while both Jackson 2 and 3 are on the test classpath)
	static class SimpleDto
	{
		public String name;
		public int value;
	}

	private static final String JSON_WITH_UNKNOWN_PROPERTIES = "{\"name\":\"abc\",\"value\":7,\"currencyCode\":\"EUR\",\"title\":\"some step label\"}";

	/**
	 * The general-purpose mapper must stay strict: {@code JsonMapperUtil.tryDeserializeToType} (e.g. the bpartner data-export audit)
	 * and the cucumber REST harness tell JSON types apart by an unknown property failing. A lenient shared mapper makes every JSON
	 * "successfully" deserialize into any type with null fields (NPE in {@code BPartnerCompositeAuditService.auditAdUser}, null REST response bodies).
	 */
	@Test
	void newJsonObjectMapper_rejects_unknown_properties()
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		assertThatThrownBy(() -> mapper.readValue(JSON_WITH_UNKNOWN_PROPERTIES, SimpleDto.class))
				.isInstanceOf(UnrecognizedPropertyException.class);
	}

	/**
	 * The REST (Spring MVC message converter) mapper ignores unknown request properties, like the pre-JDK25 REST layer did
	 * (e.g. a price {@code currencyCode}, a mobile {@code title} test-step label, an OLCand {@code dataDest}).
	 */
	@Test
	void newHttpMessageConverterJsonObjectMapper_ignores_unknown_properties()
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newHttpMessageConverterJsonObjectMapper();

		assertThatCode(() -> mapper.readValue(JSON_WITH_UNKNOWN_PROPERTIES, SimpleDto.class))
				.as("unknown JSON properties must be ignored, not rejected")
				.doesNotThrowAnyException();
	}

	@Test
	void newHttpMessageConverterJsonObjectMapper_binds_known_properties_even_with_an_unknown_one() throws Exception
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newHttpMessageConverterJsonObjectMapper();
		final SimpleDto dto = mapper.readValue("{\"name\":\"abc\",\"value\":7,\"extra\":true}", SimpleDto.class);
		assertThat(dto.name).isEqualTo("abc");
		assertThat(dto.value).isEqualTo(7);
	}
}
