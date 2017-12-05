package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.collections.PagedIterator.PageFetcher;
import org.compiere.util.CCache;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsFilterData;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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
	private static final int HIGHVOLUME_THRESHOLD = 100;
	private static final int STREAM_ALL_MAX_SIZE_ALLOWED = 200;

	private final HUEditorViewRepository huEditorRepo;
	private final ImmutableList<DocumentFilter> stickyFilters;
	private final AtomicReference<ViewRowIdsOrderedSelection> defaultSelectionRef;
	private final CCache<DocumentId, HUEditorRow> cache_huRowsById = CCache.newLRUCache(I_M_HU.Table_Name + "#HUEditorRows#by#Id", 100, 2);

	HUEditorViewBuffer_HighVolume(
			final ViewId viewId,
			final HUEditorViewRepository huEditorRepo,
			final List<DocumentFilter> stickyFilters,
			final List<DocumentFilter> filters,
			final List<DocumentQueryOrderBy> orderBys)
	{
		this.huEditorRepo = huEditorRepo;
		this.stickyFilters = ImmutableList.copyOf(stickyFilters);

		final List<DocumentFilter> filtersAll = ImmutableList.copyOf(Iterables.concat(stickyFilters, filters));
		final ViewRowIdsOrderedSelection defaultSelection = huEditorRepo.createSelection(viewId, filtersAll, orderBys);
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
		huEditorRepo.invalidateCache();
		cache_huRowsById.clear();
	}

	@Override
	public boolean addHUIds(final Collection<Integer> huIdsToAdd)
	{
		if (huIdsToAdd == null || huIdsToAdd.isEmpty())
		{
			return false;
		}

		final DocumentIdsSelection rowIdsToAdd = HUEditorRowId.rowIdsFromTopLevelM_HU_IDs(huIdsToAdd);
		if (rowIdsToAdd.isEmpty())
		{
			return false;
		}

		return changeDefaultSelection(defaultSelection -> huEditorRepo.addRowIdsToSelection(defaultSelection, rowIdsToAdd));
	}

	@Override
	public boolean removeHUIds(final Collection<Integer> huIdsToRemove)
	{
		if (huIdsToRemove == null || huIdsToRemove.isEmpty())
		{
			return false;
		}

		final DocumentIdsSelection rowIdsToRemove = HUEditorRowId.rowIdsFromTopLevelM_HU_IDs(huIdsToRemove);

		rowIdsToRemove.forEach(rowId -> cache_huRowsById.remove(rowId));

		return changeDefaultSelection(defaultSelection -> huEditorRepo.removeRowIdsFromSelection(defaultSelection, rowIdsToRemove));
	}

	@Override
	public boolean containsAnyOfHUIds(final Collection<Integer> huIdsToCheck)
	{
		final DocumentIdsSelection rowIds = HUEditorRowId.rowIdsFromTopLevelM_HU_IDs(huIdsToCheck);
		return huEditorRepo.containsAnyOfRowIds(getDefaultSelection(), rowIds);
	}

	@Override
	public Stream<HUEditorRow> streamAllRecursive(@NonNull final HUEditorRowFilter filter) throws UnsupportedOperationException
	{
		final ViewRowIdsOrderedSelection defaultSelection = getDefaultSelection();
		if (defaultSelection.getSize() > STREAM_ALL_MAX_SIZE_ALLOWED)
		{
			throw new UnsupportedOperationException("Streaming all rows when selection is bigger than " + STREAM_ALL_MAX_SIZE_ALLOWED + " is not allowed");
		}

		return streamPage(0, STREAM_ALL_MAX_SIZE_ALLOWED, filter, defaultSelection.getOrderBys())
				.flatMap(row -> row.streamRecursive());
	}

	@Override
	public Stream<HUEditorRow> streamByIdsExcludingIncludedRows(@NonNull final HUEditorRowFilter filter)
	{
		return streamByIds(filter);
	}

	private Stream<HUEditorRow> streamByIds(@NonNull final HUEditorRowFilter filter)
	{
		final Stream<HUEditorRowId> huEditorRowIds;
		final ImmutableSet<HUEditorRowId> onlyRowIds = filter.getOnlyRowIds();
		if (onlyRowIds.isEmpty())
		{
			final List<DocumentQueryOrderBy> defaultOrderBys = getDefaultSelection().getOrderBys();
			huEditorRowIds = streamHUIdsByPage(0, Integer.MAX_VALUE, defaultOrderBys)
					.map(HUEditorRowId::ofTopLevelHU);
		}
		else
		{
			huEditorRowIds = onlyRowIds.stream();
		}

		return HUEditorRowsPagedLoadingIterator.builder()
				.huEditorRepo(huEditorRepo)
				.cache(cache_huRowsById)
				.rowIds(huEditorRowIds.iterator())
				.filter(filter)
				.build()
				.stream();
	}

	@Override
	public Stream<HUEditorRow> streamPage(
			final int firstRow,
			final int pageLength,
			@NonNull final HUEditorRowFilter filter,
			final List<DocumentQueryOrderBy> orderBys)
	{
		final Iterator<HUEditorRowId> rowIds = streamHUIdsByPage(firstRow, pageLength, orderBys)
				.map(HUEditorRowId::ofTopLevelHU)
				.iterator();

		return HUEditorRowsPagedLoadingIterator.builder()
				.huEditorRepo(huEditorRepo)
				.cache(cache_huRowsById)
				.rowIds(rowIds)
				.filter(filter)
				.build()
				.stream();
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

	private PageFetcher<Integer> huIdsPageFetcher(final List<DocumentQueryOrderBy> orderBys)
	{
		final ViewRowIdsOrderedSelection selection = getSelection(orderBys);
		return (firstRow, maxRows) -> huEditorRepo.retrieveHUIdsPage(selection, firstRow, maxRows);
	}

	private Stream<Integer> streamHUIdsByPage(final int firstRow, final int maxRows, final List<DocumentQueryOrderBy> orderBys)
	{
		return IteratorUtils.<Integer> newPagedIterator()
				.firstRow(firstRow)
				.maxRows(maxRows)
				.pageSize(100) // fetch 100items/chunk
				.pageFetcher(huIdsPageFetcher(orderBys))
				.build()
				.stream();
	}

	@Override
	public HUEditorRow getById(@NonNull final DocumentId rowId) throws EntityNotFoundException
	{
		final HUEditorRowId huRowId = HUEditorRowId.ofDocumentId(rowId);
		final HUEditorRowId topLevelRowId = huRowId.toTopLevelRowId();

		final HUEditorRow topLevelRow = cache_huRowsById.getOrLoad(topLevelRowId.toDocumentId(), () -> huEditorRepo.retrieveForHUId(topLevelRowId.getTopLevelHUId()));
		if (topLevelRowId.equals(huRowId))
		{
			return topLevelRow;
		}
		else
		{
			return topLevelRow.getIncludedRowById(rowId)
					.orElseThrow(() -> new EntityNotFoundException("No row found for " + rowId).setParameter("topLevelRowId", topLevelRowId));
		}
	}

	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		return huEditorRepo.buildSqlWhereClause(getDefaultSelection(), rowIds);
	}

	public static boolean isHighVolume(final List<DocumentFilter> stickyFilters)
	{
		final HUIdsFilterData huIdsFilterData = HUIdsFilterHelper.extractFilterDataOrNull(stickyFilters);
		if (huIdsFilterData == null)
		{
			return true;
		}

		final Set<Integer> huIds = huIdsFilterData.getInitialHUIds();
		if (huIds == null)
		{
			// null means no restrictions, so we might have a lot of HUs
			return true; // high volume
		}
		else if (huIds.isEmpty())
		{
			// no HUs will be allowed
			return false; // not high volume
		}
		else
		{
			// consider high volume if it's above give threshold
			return huIds.size() >= HIGHVOLUME_THRESHOLD;
		}
	}

}
