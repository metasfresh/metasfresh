package de.metas.ui.web.window.datatypes.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class JSONLookupValueTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void init()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void testSerializeDeserialize() throws Exception
	{
		testSerializeDeserialize(
				JSONLookupValue.ofLookupValue(
						StringLookupValue.of("Y", "Yes"),
						"en_US"));
	}

	private void testSerializeDeserialize(final JSONLookupValue obj) throws IOException
	{
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("JSON: " + json);

		final JSONLookupValue objDeserialized = jsonObjectMapper.readValue(json, JSONLookupValue.class);
		assertThat(objDeserialized.toStringLookupValue()).isEqualTo(obj.toStringLookupValue());
	}

}
