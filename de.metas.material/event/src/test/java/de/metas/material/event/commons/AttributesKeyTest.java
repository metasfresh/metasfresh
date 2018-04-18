package de.metas.material.event.commons;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class AttributesKeyTest
{
	@Test
	public void testSerializeDeserialize() throws IOException
	{
		ObjectMapper jsonObjectMapper = new ObjectMapper();

		final AttributesKey attributesKey = AttributesKey.ofAttributeValueIds(1, 2, 3, 4);
		final String attributesKeyStr = attributesKey.getAsString();

		// serialize
		final String json = jsonObjectMapper.writeValueAsString(attributesKey);
		// System.out.println(json);
		assertThat(json).isEqualTo("\"" + attributesKeyStr + "\"");

		// deserialize
		final AttributesKey attributesKeyDeserialized = jsonObjectMapper.readValue(json, AttributesKey.class);
		assertThat(attributesKeyDeserialized).isEqualTo(attributesKey);
		assertThat(attributesKeyDeserialized.getAsString()).isEqualTo(attributesKeyStr);
		assertThat(attributesKeyDeserialized.getAttributeValueIds()).isEqualTo(ImmutableSet.of(1, 2, 3, 4));
	}

	@Test
	public void test_ofString()
	{
		final String keyStr = "3" + AttributesKey.ATTRIBUTES_KEY_DELIMITER + "1" + AttributesKey.ATTRIBUTES_KEY_DELIMITER + "2";
		final String keyNormStr = "1" + AttributesKey.ATTRIBUTES_KEY_DELIMITER + "2" + AttributesKey.ATTRIBUTES_KEY_DELIMITER + "3";
		assertThat(AttributesKey.ofString(keyStr).getAsString()).isEqualTo(keyNormStr);
	}

	@Test
	public void test_ofString_NONE()
	{
		assertThat(AttributesKey.ofString("   ")).isSameAs(AttributesKey.NONE);
	}

}
