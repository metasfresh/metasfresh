package de.metas.ui.web.handlingunits;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.SqlViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.NonNull;

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

public class HUEditorViewBuffer_HighVolume implements HUEditorViewBuffer
{
	public static final int HIGHVOLUME_THRESHOLD = 100;

	private static final int STREAM_ALL_MAX_SIZE = 100;

	private final HUEditorViewRepository huEditorRepo;
	private final IStringExpression sqlSelectHUIdsByPage;

	private final ImmutableList<DocumentFilter> stickyFilters;

	private final SqlViewRowIdsOrderedSelectionFactory viewSelectionFactory;
	private final AtomicReference<ViewRowIdsOrderedSelection> defaultSelectionRef;

	private final CCache<DocumentId, HUEditorRow> cache_huRowsById = CCache.newLRUCache(I_M_HU.Table_Name + "#HUEditorRows#by#Id", 100, 2);

	HUEditorViewBuffer_HighVolume(
			final WindowId windowId,
			final HUEditorViewRepository huEditorRepo,
			final List<DocumentFilter> stickyFilters,
			final List<DocumentFilter> filters)
	{
		this.huEditorRepo = huEditorRepo;

		final SqlViewBinding viewBinding = huEditorRepo.getSqlViewBinding();
		viewSelectionFactory = SqlViewRowIdsOrderedSelectionFactory.of(viewBinding);
		sqlSelectHUIdsByPage = viewBinding.getSqlSelectByPage();

		this.stickyFilters = ImmutableList.copyOf(stickyFilters);

		final ViewEvaluationCtx viewEvalCtx = ViewEvaluationCtx.of(Env.getCtx());
		final List<DocumentFilter> filtersAll = ImmutableList.copyOf(Iterables.concat(stickyFilters, filters));
		final ViewRowIdsOrderedSelection defaultSelection = viewSelectionFactory.createOrderedSelection(viewEvalCtx, windowId, filtersAll, viewBinding.getDefaultOrderBys());
		defaultSelectionRef = new AtomicReference<>(defaultSelection);

	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return stickyFilters;
	}

	private ViewRowIdsOrderedSelection getDefaultSelection()
	{
		return defaultSelectionRef.get();
	}

	/** @return true if selection was really changed */
	private boolean changeDefaultSelection(final UnaryOperator<ViewRowIdsOrderedSelection> mapper)
	{
		final ViewRowIdsOrderedSelection defaultSelectionOld = defaultSelectionRef.get();
		final ViewRowIdsOrderedSelection defaultSelectionNew = defaultSelectionRef.updateAndGet(mapper);
		return !Objects.equals(defaultSelectionOld, defaultSelectionNew);
	}

	@Override
	public ViewId getViewId()
	{
		return getDefaultSelection().getViewId();
	}

	@Override
	public long size()
	{
		return getDefaultSelection().getSize();
	}

	@Override
	public void invalidateAll()
	{
		cache_huRowsById.clear();
	}

	@Override
	public boolean addHUIds(final Collection<Integer> huIdsToAdd)
	{
		if (huIdsToAdd == null || huIdsToAdd.isEmpty())
		{
			return false;
		}

		final DocumentIdsSelection rowIdsToAdd = HUEditorRow.rowIdsFromM_HU_IDs(huIdsToAdd);
		if (rowIdsToAdd.isEmpty())
		{
			return false;
		}

		return changeDefaultSelection(defaultSelection -> viewSelectionFactory.addRowIdsToSelection(defaultSelection, rowIdsToAdd));
	}

	@Override
	public boolean removeHUIds(final Collection<Integer> huIdsToRemove)
	{
		if (huIdsToRemove == null || huIdsToRemove.isEmpty())
		{
			return false;
		}

		final DocumentIdsSelection rowIdsToRemove = HUEditorRow.rowIdsFromM_HU_IDs(huIdsToRemove);

		rowIdsToRemove.forEach(rowId -> cache_huRowsById.remove(rowId));

		return changeDefaultSelection(defaultSelection -> viewSelectionFactory.removeRowIdsFromSelection(defaultSelection, rowIdsToRemove));
	}

	@Override
	public boolean containsAnyOfHUIds(final Collection<Integer> huIdsToCheck)
	{
		final DocumentIdsSelection rowIds = HUEditorRow.rowIdsFromM_HU_IDs(huIdsToCheck);
		return viewSelectionFactory.containsAnyOfRowIds(getDefaultSelection().getSelectionId(), rowIds);
	}

	@Override
	public Stream<HUEditorRow> streamAllRecursive() throws UnsupportedOperationException
	{
		final ViewRowIdsOrderedSelection defaultSelection = getDefaultSelection();
		if (defaultSelection.getSize() > STREAM_ALL_MAX_SIZE)
		{
			throw new UnsupportedOperationException("Streaming all rows when selection is bigger than " + STREAM_ALL_MAX_SIZE + " is not allowed");
		}

		return streamPage(0, STREAM_ALL_MAX_SIZE, defaultSelection.getOrderBys())
				.flatMap(row -> row.streamRecursive());
	}

	@Override
	public Stream<HUEditorRow> streamByIdsExcludingIncludedRows(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds == null || rowIds.isEmpty())
		{
			return Stream.empty();
		}

		return streamByIds(rowIds);
	}

	private Stream<HUEditorRow> streamByIds(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return Stream.empty();
		}

		// TODO: implement support for ALL rowIds
		if (rowIds.isAll())
		{
			throw new UnsupportedOperationException("streaming all rowIds is not supported");
		}

		final HUEditorRow[] rows = new HUEditorRow[rowIds.size()];
		final Map<DocumentId, Integer> rowIdToLoad2index = new HashMap<>();
		{
			int idx = 0;
			for (final DocumentId rowId : rowIds.toSet())
			{
				final HUEditorRow row = cache_huRowsById.get(rowId);
				if (row == null)
				{
					// to be loaded
					rowIdToLoad2index.put(rowId, idx);
				}
				else
				{
					rows[idx] = row;
				}

				idx++;
			}
		}

		if (!rowIdToLoad2index.isEmpty())
		{
			final Set<Integer> huIds = HUEditorRow.rowIdsToM_HU_IDs(rowIdToLoad2index.keySet());
			huEditorRepo.retrieveHUEditorRows(huIds)
					.forEach(row -> {
							final DocumentId rowId = row.getId();
							final Integer idx = rowIdToLoad2index.remove(rowId);
							if (idx == null)
							{
								// wtf?! we got some more then we requested?!?
								return;
							}

							rows[idx] = row;

							cache_huRowsById.put(rowId, row);
						});
		}

		return Stream.of(rows)
				.filter(row -> row != null); // just to make sure we won't stream some empty gaps (e.g. missing rows because HU was not a top level one)
	}

	@Override
	public Stream<HUEditorRow> streamPage(
			final int firstRow,
			final int pageLength,
			final List<DocumentQueryOrderBy> orderBys)
	{
		final Set<Integer> huIds = retrieveHUIdsByPage(firstRow, pageLength, orderBys);
		final DocumentIdsSelection rowIds = HUEditorRow.rowIdsFromM_HU_IDs(huIds);
		return streamByIds(rowIds);
	}

	private ViewRowIdsOrderedSelection getSelection(final List<DocumentQueryOrderBy> orderBys)
	{
		final ViewRowIdsOrderedSelection defaultSelection = getDefaultSelection();

		if (orderBys == null || orderBys.isEmpty())
		{
			return defaultSelection;
		}

		if (!Objects.equals(orderBys, defaultSelection.getOrderBys()))
		{
			throw new UnsupportedOperationException("Sorting is not supported");
		}

		return defaultSelection;
	}

	private Set<Integer> retrieveHUIdsByPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		final ViewRowIdsOrderedSelection selection = getSelection(orderBys);
		final String viewSelectionId = selection.getSelectionId();
		final int firstSeqNo = firstRow + 1; // NOTE: firstRow is 0-based while SeqNo are 1-based
		final int lastSeqNo = firstRow + pageLength;

		final ViewEvaluationCtx viewEvalCtx = ViewEvaluationCtx.of(Env.getCtx());
		final String sql = sqlSelectHUIdsByPage.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		final Object[] sqlParams = new Object[] { viewSelectionId, firstSeqNo, lastSeqNo };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			pstmt.setMaxRows(pageLength);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final Set<Integer> huIds = new LinkedHashSet<>();
			while (rs.next())
			{
				final int huId = rs.getInt(I_M_HU.COLUMNNAME_M_HU_ID);
				if (huId <= 0)
				{
					continue;
				}
				huIds.add(huId);
			}

			return huIds;

		}
		catch (final SQLException ex)
		{
			throw DBException.wrapIfNeeded(ex)
					.setSqlIfAbsent(sql, sqlParams);
		}
	}

	@Override
	public HUEditorRow getById(@NonNull final DocumentId rowId) throws EntityNotFoundException
	{
		final int huId = rowId.toInt();
		// FIXME: fails if not top level ...
		return huEditorRepo.retrieveForHUId(huId);
	}

	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		final String sqlKeyColumnNameFK = I_M_HU.Table_Name + "." + I_M_HU.COLUMNNAME_M_HU_ID;
		final String selectionId = getViewId().getViewId();
		return SqlViewSelectionQueryBuilder.buildSqlWhereClause(sqlKeyColumnNameFK, selectionId, rowIds.toDocumentIdsSelectionWithOnlyIntegerDocumentIds());
	}
}
