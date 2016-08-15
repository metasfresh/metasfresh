package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Set;

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

public final class NullDocumentFieldChangedEventCollector implements IDocumentFieldChangedEventCollector
{
	public static final transient NullDocumentFieldChangedEventCollector instance = new NullDocumentFieldChangedEventCollector();

	private NullDocumentFieldChangedEventCollector()
	{
		super();
	}

	@Override
	public Set<String> getFieldNames()
	{
		return ImmutableSet.of();
	}

	@Override
	public boolean isEmpty()
	{
		return true;
	}

	@Override
	public List<DocumentFieldChangedEvent> toEventsList()
	{
		return ImmutableList.of();
	}

	@Override
	public void collectValueChanged(final DocumentField documentField, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectReadonlyChanged(final DocumentField documentField, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectMandatoryChanged(final DocumentField documentField, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectDisplayedChanged(final DocumentField documentField, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectLookupValuesStaled(final DocumentField documentField, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectFrom(final IDocumentFieldChangedEventCollector fromCollector)
	{
		// do nothing
	}

	@Override
	public void collectFrom(final Document document, final ReasonSupplier reason)
	{
		// do nothing
	}
}
