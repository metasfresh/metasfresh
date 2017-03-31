package de.metas.ui.web.window.datatypes.json;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JsonMapAsValuesListSerializerTest
{
	private static class TestPOJO
	{
		@JsonProperty("values")
		@JsonSerialize(using = JsonMapAsValuesListSerializer.class)
		private Map<String, String> map;
	}

	private static class TestPOJO_IncludeNonEmpty
	{
		@JsonProperty("values")
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		@JsonSerialize(using = JsonMapAsValuesListSerializer.class)
		private Map<String, String> map;
	}

	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
	}

	@Test
	public void test_nullMap() throws Exception
	{
		final TestPOJO pojo = new TestPOJO();
		pojo.map = null;

		final String jsonExpected = "{\"values\":null}";
		final String json = jsonObjectMapper.writeValueAsString(pojo);
		Assert.assertEquals(jsonExpected, json);
	}

	@Test
	public void test_emptyMap() throws Exception
	{
		final TestPOJO pojo = new TestPOJO();
		pojo.map = new LinkedHashMap<>();

		final String jsonExpected = "{\"values\":[]}";
		final String json = jsonObjectMapper.writeValueAsString(pojo);
		Assert.assertEquals(jsonExpected, json);
	}

	@Test
	public void test_regularMap() throws Exception
	{
		final TestPOJO pojo = new TestPOJO();
		pojo.map = new LinkedHashMap<>();
		pojo.map.put("1", "one");
		pojo.map.put("2", "two");
		pojo.map.put("3", "three");

		final String jsonExpected = "{\"values\":[\"one\",\"two\",\"three\"]}";
		final String json = jsonObjectMapper.writeValueAsString(pojo);
		Assert.assertEquals(jsonExpected, json);
	}

	@Test
	public void test_includeNonEmpty_emptyMap() throws Exception
	{
		test_includeNonEmpty_assertNotInJsonString(null);
		test_includeNonEmpty_assertNotInJsonString(new LinkedHashMap<>());
		test_includeNonEmpty_assertNotInJsonString(ImmutableMap.of());
		test_includeNonEmpty_assertNotInJsonString(Maps.<String, String> uniqueIndex(ImmutableList.of(), value -> value));
	}

	private void test_includeNonEmpty_assertNotInJsonString(final Map<String, String> map) throws Exception
	{
		final TestPOJO_IncludeNonEmpty pojo = new TestPOJO_IncludeNonEmpty();
		pojo.map = map;

		final String jsonExpected = "{}";
		final String json = jsonObjectMapper.writeValueAsString(pojo);
		Assert.assertEquals("Failed for map=" + map, jsonExpected, json);
	}

}
