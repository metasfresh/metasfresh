package de.metas.i18n;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AdMessageKeyTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void init()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void test_equals()
	{
		assertThat(AdMessageKey.of("msg1")).isEqualTo(AdMessageKey.of("msg1"));
		assertThat(AdMessageKey.of("msg1")).isNotEqualTo(AdMessageKey.of("msg2"));
	}

	@Test
	public void testSerializeDeserialize() throws Exception
	{
		testSerializeDeserialize(AdMessageKey.of("msg1"));
	}

	private void testSerializeDeserialize(@NonNull final Object obj) throws IOException
	{
		System.out.println("object: " + obj);
		final String json = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("object as json: " + json);

		final Object objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		System.out.println("object deserialized: " + objDeserialized);
		assertThat(objDeserialized).isEqualTo(obj);

		final String json2 = jsonObjectMapper.writeValueAsString(objDeserialized);
		System.out.println("object deserialized as json: " + json2);
		assertThat(json2).isEqualTo(json);
	}

}
