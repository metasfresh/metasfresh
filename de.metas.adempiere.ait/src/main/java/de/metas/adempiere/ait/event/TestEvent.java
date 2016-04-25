package  de.metas.adempiere.ait.event;

/*
 * #%L
 * de.metas.adempiere.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public final class TestEvent
{
	private final EventType eventType;
	private final AIntegrationTestDriver source;
	private final Object obj;

	public TestEvent(
			final AIntegrationTestDriver source,
			final EventType eventType,
			final Object obj)
	{
		this.source = source;
		this.eventType = eventType;
		this.obj = obj;
	}

	public EventType getEventType()
	{
		return eventType;
	}

	public AIntegrationTestDriver getSource()
	{
		return source;
	}

	public Object getObj()
	{
		return obj;
	}

	@Override
	public String toString()
	{
		return eventType.toString();
	}

}
