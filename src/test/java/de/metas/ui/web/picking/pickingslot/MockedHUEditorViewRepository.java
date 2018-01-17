package de.metas.ui.web.picking.pickingslot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.adempiere.util.collections.PagedIterator.Page;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorRowFilters;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsFilterData;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;

/*
 * #%L
 * metasfresh-webui-api
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

public class MockedHUEditorViewRepository implements HUEditorViewRepository
{
	private final LinkedHashMap<Integer, HUEditorRow> rowsByHUId = new LinkedHashMap<>();

	public void addRow(final HUEditorRow row)
	{
		rowsByHUId.put(row.getM_HU_ID(), row);
	}

	@Override
	public void invalidateCache()
	{
		// nothing
	}

	@Override
	public List<HUEditorRow> retrieveHUEditorRows(final Set<Integer> huIds, final HUEditorRowFilter filter)
	{
		return huIds.stream()
				.map(rowsByHUId::get)
				.filter(Predicates.notNull())
				.filter(HUEditorRowFilters.toPredicate(filter))
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public HUEditorRow retrieveForHUId(final int huId)
	{
		final HUEditorRow row = rowsByHUId.get(huId);
		if (row == null)
		{
			throw new EntityNotFoundException("no row found for huId=" + huId);
		}
		return row;
	}

	@Override
	public List<Integer> retrieveHUIdsEffective(final HUIdsFilterData huIdsFilter, final List<DocumentFilter> filters)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Page<Integer> retrieveHUIdsPage(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection selection, final int firstRow, final int maxRows)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public ViewRowIdsOrderedSelection createSelection(final ViewEvaluationCtx viewEvalCtx, final ViewId viewId, final List<DocumentFilter> filters, final List<DocumentQueryOrderBy> orderBys)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public ViewRowIdsOrderedSelection createSelectionFromSelection(final ViewEvaluationCtx viewEvalCtx, final ViewRowIdsOrderedSelection fromSelection, final List<DocumentQueryOrderBy> orderBys)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public ViewRowIdsOrderedSelection removeRowIdsFromSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIdsToRemove)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public ViewRowIdsOrderedSelection addRowIdsToSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIdsToAdd)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public boolean containsAnyOfRowIds(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void deleteSelection(final ViewRowIdsOrderedSelection selection)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Set<Integer> convertToRecordIds(final DocumentIdsSelection rowIds)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String buildSqlWhereClause(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		throw new UnsupportedOperationException("not implemented");
	}
}
