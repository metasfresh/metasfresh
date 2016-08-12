package de.metas.ui.web.window.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
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

public class FieldChangedEventCollector implements IDocumentFieldChangedEventCollector
{
	public static final FieldChangedEventCollector newInstance()
	{
		return new FieldChangedEventCollector();
	}

	private final Map<String, DocumentFieldChangedEvent> fieldName2event = new LinkedHashMap<>();

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

	@Override
	public Set<String> getFieldNames()
	{
		return ImmutableSet.copyOf(fieldName2event.keySet());
	}

	@Override
	public boolean isEmpty()
	{
		return fieldName2event.isEmpty();
	}

	private DocumentFieldChangedEvent getFieldChangedEvent(final String fieldName)
	{
		return fieldName2event.computeIfAbsent(fieldName, (key) -> DocumentFieldChangedEvent.of(key));
	}

	@Override
	public List<DocumentFieldChangedEvent> toEventsList()
	{
		return ImmutableList.copyOf(fieldName2event.values());
	}

	private final String extractReason(final Supplier<String> reasonSupplier)
	{
		// TODO: do this only when debugging/tracing
		return reasonSupplier == null ? null : reasonSupplier.get();
	}

	@Override
	public void collectValueChanged(final String fieldName, final Object value, final Supplier<String> reason)
	{
		getFieldChangedEvent(fieldName).setValue(value, extractReason(reason));
	}

	@Override
	public void collectReadonlyChanged(final String fieldName, final boolean value, final Supplier<String> reason)
	{
		getFieldChangedEvent(fieldName).setReadonly(value, extractReason(reason));
	}

	@Override
	public void collectMandatoryChanged(final String fieldName, final boolean value, final Supplier<String> reason)
	{
		getFieldChangedEvent(fieldName).setMandatory(value, extractReason(reason));
	}

	@Override
	public void collectDisplayedChanged(final String fieldName, final boolean value, final Supplier<String> reason)
	{
		getFieldChangedEvent(fieldName).setDisplayed(value, extractReason(reason));
	}

	@Override
	public void collectLookupValuesStaled(final String fieldName, final Supplier<String> reason)
	{
		getFieldChangedEvent(fieldName).setLookupValuesStale(true, extractReason(reason));
	}

	@Override
	public void collectFrom(final IDocumentFieldChangedEventCollector fromCollector)
	{
		for (final DocumentFieldChangedEvent fromEvent : fromCollector.toEventsList())
		{
			final String fieldName = fromEvent.getFieldName();
			final DocumentFieldChangedEvent toEvent = getFieldChangedEvent(fieldName);
			toEvent.mergeFrom(fromEvent);
		}
	}
}
