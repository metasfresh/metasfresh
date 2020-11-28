package de.metas.ui.web.window.model;

import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class NullDocumentChangesCollector implements IDocumentChangesCollector
{
	public static final boolean isNull(final IDocumentChangesCollector changesCollector)
	{
		return changesCollector == null || changesCollector instanceof NullDocumentChangesCollector;
	}

	public static final transient NullDocumentChangesCollector instance = new NullDocumentChangesCollector();

	private NullDocumentChangesCollector()
	{
		super();
	}

	@Override
	public void setPrimaryChange(final DocumentPath documentPath)
	{
		// do nothing
	}

	@Override
	public Stream<DocumentChanges> streamOrderedDocumentChanges()
	{
		return Stream.empty();
	}

	@Override
	public void collectValueChanged(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectValueIfChanged(final IDocumentFieldView documentField, final Object valueOld, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectReadonlyIfChanged(final IDocumentFieldView documentField, final LogicExpressionResult valueOld, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectMandatoryIfChanged(final IDocumentFieldView documentField, final LogicExpressionResult valueOld, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectDisplayedIfChanged(final IDocumentFieldView documentField, final LogicExpressionResult valueOld, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectLookupValuesStaled(final IDocumentFieldView documentField, final ReasonSupplier reason)
	{
		// do nothing
	}

	@Override
	public void collectFieldWarning(final IDocumentFieldView documentField, final DocumentFieldWarning fieldWarning)
	{
		// do nothing
	}

	@Override
	public void collectFrom(final IDocumentChangesCollector fromCollector)
	{
		// do nothing
	}

	@Override
	public Set<String> collectFrom(final Document document, final ReasonSupplier reason)
	{
		return ImmutableSet.of(); // nothing collected
	}

	@Override
	public void collectDocumentValidStatusChanged(final DocumentPath documentPath, final DocumentValidStatus documentValidStatus)
	{
		// do nothing
	}

	@Override
	public void collectValidStatus(final IDocumentFieldView documentField)
	{
		// do nothing
	}

	@Override
	public void collectDocumentSaveStatusChanged(final DocumentPath documentPath, final DocumentSaveStatus documentSaveStatus)
	{
		// do nothing
	}

	@Override
	public void collectDeleted(final DocumentPath documentPath)
	{
		// do nothing
	}

	@Override
	public void collectStaleDetailId(final DocumentPath rootDocumentPath, final DetailId detailId)
	{
		// do nothing
	}

	@Override
	public void collectAllowNew(final DocumentPath rootDocumentPath, final DetailId detailId, final LogicExpressionResult allowNew)
	{
		// do nothing
	}

	@Override
	public void collectAllowDelete(final DocumentPath rootDocumentPath, final DetailId detailId, final LogicExpressionResult allowDelete)
	{
		// do nothing
	}

	@Override
	public void collectEvent(final IDocumentFieldChangedEvent event)
	{
		// do nothing
	}
}
