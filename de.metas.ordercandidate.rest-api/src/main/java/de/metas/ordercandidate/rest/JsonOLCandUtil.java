package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import de.metas.util.Check;
import lombok.NonNull;

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

public class JsonOLCandUtil
{
	/**
	 * Sends the given request's JSON to std-out in a pretty-printed way
	 */
	public static void printJsonString(@NonNull final JsonOLCandCreateBulkRequest object)
	{
		System.out.println(writeValueAsString(object));
	}

	public static String writeValueAsString(@NonNull final JsonOLCandCreateBulkRequest object)
	{
		final ObjectMapper jsonObjectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.enable(SerializationFeature.INDENT_OUTPUT);

		try
		{
			final String json = jsonObjectMapper.writeValueAsString(object);
			return json;
		}
		catch (JsonProcessingException e)
		{
			throw new RuntimeException("JsonProcessingException", e);
		}
	}

	public static JsonOLCandCreateBulkRequest fromResource(@NonNull final String resourceName)
	{
		final InputStream inputStream = Check.assumeNotNull(
				JsonOLCandUtil.class.getResourceAsStream(resourceName),
				"There needs to be a loadable resource with name={}", resourceName);

		final ObjectMapper jsonObjectMapper = new ObjectMapper()
				.findAndRegisterModules();

		try
		{
			return jsonObjectMapper.readValue(inputStream, JsonOLCandCreateBulkRequest.class);
		}
		catch (JsonParseException e)
		{
			throw new RuntimeException("JsonParseException", e);
		}
		catch (JsonMappingException e)
		{
			throw new RuntimeException("JsonMappingException", e);
		}
		catch (IOException e)
		{
			throw new RuntimeException("IOException", e);
		}
	}
}
