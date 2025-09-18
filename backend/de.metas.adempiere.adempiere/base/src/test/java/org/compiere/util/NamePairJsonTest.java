package org.compiere.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class NamePairJsonTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void init()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		jsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty
	}

	@Test
	public void test_KeyNamePair() throws Exception
	{
		testJsonIdentical(KeyNamePair.EMPTY, KeyNamePair.class);
		testJsonEquals(KeyNamePair.of(1, "test-name-1", "test-description-1"), KeyNamePair.class);
	}

	@Test
	public void test_ValueNamePair() throws Exception
	{
		testJsonIdentical(ValueNamePair.EMPTY, ValueNamePair.class);
		testJsonEquals(ValueNamePair.of("1", "test-name-1", "test-description-1", null), ValueNamePair.class);
	}

	private <T> void testJsonEquals(final T value, final Class<T> type) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value, type);
		Assertions.assertEquals(value, valueDeserialized);
	}

	private <T> void testJsonIdentical(final T value, final Class<T> type) throws Exception
	{
		final T valueDeserialized = testSerializeDeserialize(value, type);
		Assertions.assertSame(value, valueDeserialized);
	}

	private <T> T testSerializeDeserialize(final T value, final Class<T> type) throws Exception
	{
		final String jsonStr = jsonObjectMapper.writeValueAsString(value);
		System.out.println("\n\n-------------------------------------------------------------------------------------------");
		System.out.println("Value: " + value);
		System.out.println("Type: " + type);
		System.out.println("JSON: " + jsonStr);

		final T valueDeserialized = jsonObjectMapper.readValue(jsonStr, type);
		System.out.println("Value deserialized: " + valueDeserialized);
		System.out.println("-------------------------------------------------------------------------------------------");
		return valueDeserialized;
	}

}
