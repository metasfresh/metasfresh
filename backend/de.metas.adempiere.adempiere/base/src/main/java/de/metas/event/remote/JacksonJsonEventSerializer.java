package de.metas.event.remote;

import de.metas.event.Event;
import de.metas.util.JSONObjectMapper;

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

	private final JSONObjectMapper<Event> delegate;

	private JacksonJsonEventSerializer()
	{
		delegate = JSONObjectMapper.forClass(Event.class);
	}

	@Override
	public String toString(final Event event)
	{
		return delegate.writeValueAsString(event);
	}

	@Override
	public Event fromString(final String eventStr)
	{
		return delegate.readValue(eventStr);
	}
}
