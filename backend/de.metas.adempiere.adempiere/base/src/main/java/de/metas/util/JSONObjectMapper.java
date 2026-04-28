package de.metas.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
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
 * Thx to https://stackoverflow.com/questions/10004241/jackson-objectmapper-with-utf-8-encoding for a note about string encoding.
 *
 * @param <T> the class to serialize/deserialize
 */
public class JSONObjectMapper<T>
{
	public static <T> JSONObjectMapper<T> forClass(@NonNull final Class<T> clazz)
	{
		return new JSONObjectMapper<>(clazz);
	}

	private final static ObjectMapper jsonObjectMapper= JsonObjectMapperHolder.sharedJsonObjectMapper();

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

	public T readValue(final String objectString)
	{
		try
		{
			return jsonObjectMapper.readValue(objectString, clazz);
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed converting json to class= " + clazz + "; object=" + objectString, ex);
		}
	}

	public T readValue(final InputStream objectStream)
	{
		try
		{
			return jsonObjectMapper.readValue(objectStream, clazz);
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed converting json to class= " + clazz + "; object=" + objectStream, ex);
		}
	}

	public T convertValue(final Object object)
	{
		return jsonObjectMapper.convertValue(object, clazz);
	}
}
