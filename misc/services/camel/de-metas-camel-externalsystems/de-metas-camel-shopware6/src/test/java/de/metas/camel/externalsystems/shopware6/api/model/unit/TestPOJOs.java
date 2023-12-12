/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model.unit;

import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class TestPOJOs
{
	@Test
	public void givenJsonUnits_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonUnits());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(value);
		final Object value2 = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(json, valueClass);

		assertThat(value2).isEqualTo(value);
	}

	private JsonUnits getMockJsonUnits()
	{
		final List<JsonUOM> unitList = Arrays.asList(
				JsonUOM.builder()
						.id("1")
						.shortCode("shortCode1")
						.build(),
				JsonUOM.builder()
						.id("2")
						.shortCode("shortCode2")
						.build()
		);

		return JsonUnits.builder()
				.unitList(unitList)
				.build();
	}
}
