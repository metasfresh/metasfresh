package de.metas.ui.web.window.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

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

public class DocumentFieldChangedEventCollector implements IDocumentFieldChangedEventCollector
{
	public static final DocumentFieldChangedEventCollector newInstance()
	{
		return new DocumentFieldChangedEventCollector();
	}

	private static final Logger logger = LogManager.getLogger(DocumentFieldChangedEventCollector.class);

	private final Map<String, DocumentFieldChangedEvent> fieldName2event = new LinkedHashMap<>();

	private DocumentFieldChangedEventCollector()
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

	private DocumentFieldChangedEvent getFieldChangedEvent(final DocumentField documentField)
	{
		return getFieldChangedEvent(documentField.getFieldName(), documentField.isKey());
	}

	private DocumentFieldChangedEvent getFieldChangedEvent(final String fieldName, final boolean key)
	{
		return fieldName2event.computeIfAbsent(fieldName, (newFieldName) -> DocumentFieldChangedEvent.of(newFieldName, key));
	}

	@Override
	public List<DocumentFieldChangedEvent> toEventsList()
	{
		return ImmutableList.copyOf(fieldName2event.values());
	}

	private final String extractReason(final Supplier<String> reasonSupplier)
	{
		if (reasonSupplier == null)
		{
			return null;
		}

		// Extract the reason only if debugging is enabled
		if (!logger.isDebugEnabled())
		{
			return null;
		}

		return reasonSupplier.get();
	}

	@Override
	public void collectValueChanged(final DocumentField documentField, final Supplier<String> reason)
	{
		getFieldChangedEvent(documentField).setValue(documentField.getValueAsJsonObject(), extractReason(reason));
	}

	@Override
	public void collectReadonlyChanged(final DocumentField documentField, final Supplier<String> reason)
	{
		getFieldChangedEvent(documentField).setReadonly(documentField.isReadonly(), extractReason(reason));
	}

	@Override
	public void collectMandatoryChanged(final DocumentField documentField, final Supplier<String> reason)
	{
		getFieldChangedEvent(documentField).setMandatory(documentField.isMandatory(), extractReason(reason));
	}

	@Override
	public void collectDisplayedChanged(final DocumentField documentField, final Supplier<String> reason)
	{
		getFieldChangedEvent(documentField).setDisplayed(documentField.isDisplayed(), extractReason(reason));
	}

	@Override
	public void collectLookupValuesStaled(final DocumentField documentField, final Supplier<String> reason)
	{
		getFieldChangedEvent(documentField).setLookupValuesStale(true, extractReason(reason));
	}

	@Override
	public void collectFrom(final IDocumentFieldChangedEventCollector fromCollector)
	{
		for (final DocumentFieldChangedEvent fromEvent : fromCollector.toEventsList())
		{
			final DocumentFieldChangedEvent toEvent = getFieldChangedEvent(fromEvent.getFieldName(), fromEvent.isKey());
			toEvent.mergeFrom(fromEvent);
		}
	}

	@Override
	public void collectFrom(final Document document)
	{
		for (final DocumentField documentField : document.getFields())
		{
			final DocumentFieldChangedEvent toEvent = getFieldChangedEvent(documentField.getFieldName(), documentField.isKey());
			final String reason = "direct push";

			final Object value = documentField.getValue();
			if (!Objects.equals(value, toEvent.getValue()))
			{
				toEvent.setValue(value, reason);
			}

			final boolean readonly = documentField.isReadonly();
			if (!Objects.equals(readonly, toEvent.getReadonly()))
			{
				toEvent.setReadonly(readonly, reason);
			}

			final boolean mandatory = documentField.isMandatory();
			if (!Objects.equals(mandatory, toEvent.getMandatory()))
			{
				toEvent.setMandatory(mandatory, reason);
			}

			final boolean displayed = documentField.isDisplayed();
			if (!Objects.equals(displayed, toEvent.getDisplayed()))
			{
				toEvent.setDisplayed(displayed, reason);
			}
		}
	}
}
