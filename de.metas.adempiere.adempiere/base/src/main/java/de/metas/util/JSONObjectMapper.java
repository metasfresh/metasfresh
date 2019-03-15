package de.metas.util;

import java.io.IOException;
import java.time.ZonedDateTime;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Wraps an {@link ObjectMapper} that is configured with special tweaks for {@link ZonedDateTime}.
 *
 * @param <T> the class to serialize/deserialize
 */
public class JSONObjectMapper<T>
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> JSONObjectMapper<T> forClass(@NonNull final Class<T> clazz)
	{
		return new JSONObjectMapper(clazz);
	}

	private final static ObjectMapper jsonObjectMapper;

	static
	{
		jsonObjectMapper = new ObjectMapper();

		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant

		jsonObjectMapper.registerModule(new JavaTimeModule());
		jsonObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		jsonObjectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	}

	private final Class<T> clazz;

	private JSONObjectMapper(@NonNull final Class<T> clazz)
	{
		this.clazz = clazz;
	}

	public String writeValueAsString(final T object)
	{
		try
		{
			return jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		}
		catch (final JsonProcessingException ex)
		{
			throw new AdempiereException("Failed converting object to json: " + object, ex);
		}
	}

	public T readValue(final String objectStr)
	{
		try
		{
			return jsonObjectMapper.readValue(objectStr, clazz);
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed converting json to class= " + clazz + "; object=" + objectStr, ex);
		}
	}
}
