package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class FieldChangedEventCollector
{
	public static final FieldChangedEventCollector newInstance()
	{
		return new FieldChangedEventCollector();
	}

	private final Map<String, FieldChangedEvent> fieldName2event = new LinkedHashMap<>();

	private FieldChangedEventCollector()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(fieldName2event)
				.toString();
	}

	public Set<String> getFieldNames()
	{
		return ImmutableSet.copyOf(fieldName2event.keySet());
	}
	
	public boolean isEmpty()
	{
		return fieldName2event.isEmpty();
	}

	private FieldChangedEvent getFieldChangedEvent(final String fieldName)
	{
		FieldChangedEvent changedEvent = fieldName2event.get(fieldName);
		if (changedEvent == null)
		{
			changedEvent = FieldChangedEvent.of(fieldName);
			fieldName2event.put(fieldName, changedEvent);
		}
		return changedEvent;
	}
	
	public List<FieldChangedEvent> toEventsList()
	{
		return new ArrayList<>(fieldName2event.values());
	}

	public void collectValueChanged(final String fieldName, final Object value)
	{
		getFieldChangedEvent(fieldName).setValue(value);
	}

	public void collectReadonlyChanged(final String fieldName, final boolean value)
	{
		getFieldChangedEvent(fieldName).setReadonly(value);
	}

	public void collectMandatoryChanged(final String fieldName, final boolean value)
	{
		getFieldChangedEvent(fieldName).setMandatory(value);
	}

	public void collectDisplayedChanged(final String fieldName, final boolean value)
	{
		getFieldChangedEvent(fieldName).setDisplayed(value);
	}

	public void collectLookupValuesStaled(final String fieldName)
	{
		getFieldChangedEvent(fieldName).setLookupValuesStale(true);
	}

	public void collectFrom(final FieldChangedEventCollector fromCollector)
	{
		for (final FieldChangedEvent fromEvent : fromCollector.fieldName2event.values())
		{
			final String fieldName = fromEvent.getFieldName();
			final FieldChangedEvent toEvent = getFieldChangedEvent(fieldName);
			mergeEvent(toEvent, fromEvent);
		}
	}

	private void mergeEvent(final FieldChangedEvent toEvent, final FieldChangedEvent fromEvent)
	{
		{
			final Boolean readonly = fromEvent.getReadonly();
			if (readonly != null)
			{
				toEvent.setReadonly(readonly);
			}
		}
		//
		{
			final Boolean mandatory = fromEvent.getMandatory();
			if (mandatory != null)
			{
				toEvent.setMandatory(mandatory);
			}
		}
		//
		{
			final Boolean displayed = fromEvent.getDisplayed();
			if (displayed != null)
			{
				toEvent.setDisplayed(displayed);
			}
		}
		//
		{
			final Boolean lookupValuesStale = fromEvent.getLookupValuesStale();
			if (lookupValuesStale != null)
			{
				toEvent.setLookupValuesStale(lookupValuesStale);
			}
		}
	}

}
