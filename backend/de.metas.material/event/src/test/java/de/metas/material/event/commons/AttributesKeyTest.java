package de.metas.material.event.commons;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;

import de.metas.JsonObjectMapperHolder;

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
	private static final String delim = AttributesKey.ATTRIBUTES_KEY_DELIMITER;

	@Test
	public void testSerializeDeserialize() throws IOException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final AttributesKey attributesKey = AttributesKey.ofParts(
				AttributesKeyPart.OTHER,
				AttributesKeyPart.ofAttributeValueId(AttributeValueId.ofRepoId(1)),
				AttributesKeyPart.ofStringAttribute(AttributeId.ofRepoId(2), "stringValue"),
				AttributesKeyPart.ofNumberAttribute(AttributeId.ofRepoId(3), new BigDecimal("12.345")),
				AttributesKeyPart.ofDateAttribute(AttributeId.ofRepoId(4), LocalDate.of(2019, Month.SEPTEMBER, 21)));
		final String attributesKeyStr = attributesKey.getAsString();

		// serialize
		final String json = jsonObjectMapper.writeValueAsString(attributesKey);
		// System.out.println(json);
		assertThat(json).isEqualTo("\"" + attributesKeyStr + "\"");

		// deserialize
		final AttributesKey attributesKeyDeserialized = jsonObjectMapper.readValue(json, AttributesKey.class);
		assertThat(attributesKeyDeserialized).isEqualTo(attributesKey);
		assertThat(attributesKeyDeserialized.getAsString()).isEqualTo(attributesKeyStr);
	}

	@Test
	public void ofString()
	{
		final String keyStr = "3" + delim + "1" + delim + "2";
		final String keyNormStr = "1" + delim + "2" + delim + "3";
		assertThat(AttributesKey.ofString(keyStr).getAsString()).isEqualTo(keyNormStr);
	}

	@Test
	public void ofString_NONE()
	{
		assertThat(AttributesKey.ofString("   ")).isSameAs(AttributesKey.NONE);
	}

	@Test
	public void ofAttributeValueIds()
	{
		final AttributesKey attributesKey = AttributesKey.ofString("2" + delim + "4" + delim + "3" + delim + "1");
		final AttributesKey attributesKey2 = AttributesKey.ofString("1" + delim + "4" + delim + "3" + delim + "2");

		assertThat(attributesKey).isEqualTo(attributesKey2);
		assertThat(attributesKey.getParts()).isEqualTo(ImmutableSet.of(
				AttributesKeyPart.ofInteger(1),
				AttributesKeyPart.ofInteger(2),
				AttributesKeyPart.ofInteger(3),
				AttributesKeyPart.ofInteger(4)));
	}

	@Test
	public void testNormalization()
	{
		AttributeId attributeId1 = AttributeId.ofRepoId(10);
		AttributeId attributeId2 = AttributeId.ofRepoId(20);
		AttributeValueId attributeValueId1 = AttributeValueId.ofRepoId(1);
		AttributeValueId attributeValueId2 = AttributeValueId.ofRepoId(2);

		final AttributesKey key = AttributesKey.ofParts(
				AttributesKeyPart.ofStringAttribute(attributeId1, "value1"),
				AttributesKeyPart.ofAttributeValueId(attributeValueId1),
				AttributesKeyPart.ofStringAttribute(attributeId2, "value2"),
				AttributesKeyPart.ofAttributeValueId(attributeValueId2),
				AttributesKeyPart.OTHER);

		assertThat(key.getAsString()).isEqualTo("-1001" + delim + "1" + delim + "2" + delim + "10=value1" + delim + "20=value2");
	}
}
