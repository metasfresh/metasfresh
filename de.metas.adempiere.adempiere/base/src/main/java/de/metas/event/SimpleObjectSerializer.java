package de.metas.event;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/*
 * #%L
 * metasfresh-manufacturing-event-api
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
 * Simple json serializer/deserializer that can be used when a whole object is to be send as an {@link Event}'s property.<br>
 * See the {@code metasfresh-material-event} module for usage examples.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class SimpleObjectSerializer
{
	private static final SimpleObjectSerializer INSTANCE = new SimpleObjectSerializer();

	private final ObjectMapper objectMapper;

	private SimpleObjectSerializer()
	{
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

	public static SimpleObjectSerializer get()
	{
		return INSTANCE;
	}

	public String serialize(final Object event)
	{
		try
		{
			return objectMapper.writeValueAsString(event);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}

	public <T> T deserialize(final String eventJson, final Class<T> clazz)
	{
		try
		{
			return objectMapper.readValue(eventJson, clazz);
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
