package de.metas.rest_api.ordercandidates.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.JsonObjectMapperHolder;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateBulkRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
class JsonOLCandUtil
{
	/**
	 * Sends the given request's JSON to std-out in a pretty-printed way
	 */
	public static void printJsonString(@NonNull final JsonOLCandCreateBulkRequest object)
	{
		System.out.println(writeValueAsString(object));
	}

	private static String writeValueAsString(@NonNull final JsonOLCandCreateBulkRequest object)
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper()
				.enable(SerializationFeature.INDENT_OUTPUT);

		try
		{
			final String json = jsonObjectMapper.writeValueAsString(object);
			return json;
		}
		catch (final JsonProcessingException e)
		{
			throw new JsonOLCandUtilException("JsonProcessingException", e);
		}
	}

	public static class JsonOLCandUtilException extends RuntimeException
	{
		private static final long serialVersionUID = -626001461757553239L;

		public JsonOLCandUtilException(final String msg, final Throwable cause)
		{
			super(msg, cause);
		}
	}

	public static JsonOLCandCreateBulkRequest loadJsonOLCandCreateBulkRequest(@NonNull final String resourceName)
	{
		return fromRessource(resourceName, JsonOLCandCreateBulkRequest.class);
	}

	public static JsonOLCandCreateRequest loadJsonOLCandCreateRequest(@NonNull final String resourceName)
	{
		return fromRessource(resourceName, JsonOLCandCreateRequest.class);
	}

	private static <T> T fromRessource(@NonNull final String resourceName, @NonNull final Class<T> clazz)
	{
		final InputStream inputStream = Check.assumeNotNull(
				JsonOLCandUtil.class.getResourceAsStream(resourceName),
				"There needs to be a loadable resource with name={}", resourceName);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		try
		{
			return jsonObjectMapper.readValue(inputStream, clazz);
		}
		catch (final JsonParseException e)
		{
			throw new JsonOLCandUtilException("JsonParseException", e);
		}
		catch (final JsonMappingException e)
		{
			throw new JsonOLCandUtilException("JsonMappingException", e);
		}
		catch (final IOException e)
		{
			throw new JsonOLCandUtilException("IOException", e);
		}
	}
}
