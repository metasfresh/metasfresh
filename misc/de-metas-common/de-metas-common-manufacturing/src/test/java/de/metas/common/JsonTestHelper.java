/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import static org.assertj.core.api.Assertions.assertThat;

@UtilityClass
public class JsonTestHelper
{
	public static ObjectMapper newJsonObjectMapper()
	{
		return new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);
	}

	public static void testSerializeDeserialize(@NonNull final Object obj)
	{
		final ObjectMapper jsonObjectMapper = newJsonObjectMapper();
		System.out.println("testSerializeDeserialize: Object: " + obj);

		final String json;
		try
		{
			json = jsonObjectMapper.writeValueAsString(obj);
		}
		catch (final JsonProcessingException ex)
		{
			throw new RuntimeException("Failed converting object to JSON: " + obj, ex);
		}
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized;
		try
		{
			objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		}
		catch (final JsonProcessingException ex)
		{
			throw new RuntimeException("Failed converting JSON to " + obj.getClass() + ": " + json);
		}
		System.out.println("Object deserialized: " + objDeserialized);

		assertThat(objDeserialized)
				.usingRecursiveComparison()
				.isEqualTo(obj);
	}

}
