package de.metas.event.jms;

import java.io.IOException;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.event.Event;

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

public class JacksonJsonEventSerializer implements IEventSerializer
{
	public static final transient JacksonJsonEventSerializer instance = new JacksonJsonEventSerializer();

	private final ObjectMapper jsonObjectMapper;

	private JacksonJsonEventSerializer()
	{
		jsonObjectMapper = new ObjectMapper();

		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		jsonObjectMapper.findAndRegisterModules();
	}

	@Override
	public String toString(final Event event)
	{
		try
		{
			return jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
		}
		catch (final JsonProcessingException ex)
		{
			throw new AdempiereException("Failed converting event to json: " + event, ex);
		}
	}

	@Override
	public Event fromString(final String eventStr)
	{
		try
		{
			return jsonObjectMapper.readValue(eventStr, Event.class);
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed converting json to Event: " + eventStr, ex);
		}
	}

}
