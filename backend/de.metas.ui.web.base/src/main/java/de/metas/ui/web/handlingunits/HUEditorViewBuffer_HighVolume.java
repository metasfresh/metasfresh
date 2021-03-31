package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.view.ViewRowsOrderBy;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.util.collections.IteratorUtils;
import de.metas.util.collections.PagedIterator.PageFetcher;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.Mutables;
import org.adempiere.util.lang.SynchronizedMutable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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

	private final ViewEvaluationCtx viewEvaluationCtx;

	private final HUEditorViewRepository huEditorRepo;
	private final DocumentFilterList stickyFilters;

	private final HUIdsFilterData huIdsFilterData;
	private final DefaultSelectionHolder defaultSelectionHolder;
	private final ConcurrentHashMap<DocumentQueryOrderByList, ViewRowIdsOrderedSelection> selectionsByOrderBys = new ConcurrentHashMap<>();

	private final CCache<DocumentId, HUEditorRow> cache_huRowsById = CCache.newLRUCache(I_M_HU.Table_Name + "#HUEditorRows#by#Id", 100, 2);

	HUEditorViewBuffer_HighVolume(
			final ViewId viewId,
			final HUEditorViewRepository huEditorRepo,
			@NonNull final DocumentFilterList stickyFilters,
			@NonNull final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		this.viewEvaluationCtx = ViewEvaluationCtx.newInstanceFromCurrentContext();
		this.huEditorRepo = huEditorRepo;

		this.huIdsFilterData = HUIdsFilterHelper.extractFilterData(stickyFilters)
				.map(HUIdsFilterData::copy)
				.orElseGet(HUIdsFilterData::acceptAll);

		final DocumentFilterList stickyFiltersWithoutHUIdsFilter = stickyFilters.stream()
				.filter(HUIdsFilterHelper::isNotHUIdsFilter)
				.collect(DocumentFilterList.toDocumentFilterList());
		this.stickyFilters = stickyFiltersWithoutHUIdsFilter.mergeWith(HUIdsFilterHelper.createFilter(huIdsFilterData));

		defaultSelectionHolder = DefaultSelectionHolder.builder()
				.huEditorViewRepository(huEditorRepo)
				.viewEvaluationCtx(viewEvaluationCtx)
				.viewId(viewId)
				.filters(this.stickyFilters.mergeWith(filters))
				.orderBys(orderBys)
				.filterConverterCtx(filterConverterCtx)
				.build();
	}

	@Override
	public DocumentFilterList getStickyFilters()
	{
		return stickyFilters;
	}

	private ViewRowIdsOrderedSelection getDefaultSelection()
	{
		return defaultSelectionHolder.get();
	}

	private ViewRowIdsOrderedSelection getSelection(final DocumentQueryOrderByList orderBys)
	{
		final ViewRowIdsOrderedSelection defaultSelection = getDefaultSelection();

		if (orderBys == null || orderBys.isEmpty())
		{
			return defaultSelection;
		}

		if (DocumentQueryOrderByList.equals(defaultSelection.getOrderBys(), orderBys))
		{
			return defaultSelection;
		}

		return selectionsByOrderBys.computeIfAbsent(
				orderBys,
				k -> huEditorRepo.createSelectionFromSelection(getViewEvaluationCtx(), defaultSelection, orderBys));
	}

	/**
	 * @return true if selection was really changed
	 */
	private boolean changeSelection(@NonNull final UnaryOperator<ViewRowIdsOrderedSelection> mapper)
	{
		final boolean changed = defaultSelectionHolder.change(mapper);
		selectionsByOrderBys.clear(); // invalidate all the other selections, let it recompute when needed
		return changed;
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
		defaultSelectionHolder.delete();
		huEditorRepo.invalidateCache();
		cache_huRowsById.reset();
	}

	@Override
	public boolean addHUIds(final Collection<HuId> huIdsToAdd)
	{
		if (huIdsToAdd == null || huIdsToAdd.isEmpty())
		{
			return false;
		}

		final DocumentIdsSelection rowIdsToAdd = HUEditorRowId.rowIdsFromTopLevelHuIds(huIdsToAdd);
		if (rowIdsToAdd.isEmpty())
		{
			return false;
		}

		huIdsFilterData.mustHUIds(rowIdsToAdd.toIds(HuId::ofRepoId));
		return changeSelection(defaultSelection -> huEditorRepo.addRowIdsToSelection(defaultSelection, rowIdsToAdd));
	}

	@Override
	public boolean removeHUIds(final Collection<HuId> huIdsToRemove)
	{
		if (huIdsToRemove == null || huIdsToRemove.isEmpty())
		{
			return false;
		}

		final DocumentIdsSelection rowIdsToRemove = HUEditorRowId.rowIdsFromTopLevelHuIds(huIdsToRemove);

		cache_huRowsById.removeAll(rowIdsToRemove.toSet());
		huIdsFilterData.shallNotHUIds(rowIdsToRemove.toIds(HuId::ofRepoId));
		return changeSelection(defaultSelection -> huEditorRepo.removeRowIdsFromSelection(defaultSelection, rowIdsToRemove));
	}

	@Override
	public boolean containsAnyOfHUIds(final Collection<HuId> huIdsToCheck)
	{
		final DocumentIdsSelection rowIds = HUEditorRowId.rowIdsFromTopLevelHuIds(huIdsToCheck);
		return huEditorRepo.containsAnyOfRowIds(getDefaultSelection(), rowIds);
	}

	private ViewEvaluationCtx getViewEvaluationCtx()
	{
		return viewEvaluationCtx;
	}

	@Override
	public Stream<HUEditorRow> streamAllRecursive(@NonNull final HUEditorRowFilter filter) throws UnsupportedOperationException
	{
		final ViewRowIdsOrderedSelection defaultSelection = getDefaultSelection();
		if (defaultSelection.getSize() > STREAM_ALL_MAX_SIZE_ALLOWED)
		{
			throw new UnsupportedOperationException("Streaming all rows when selection is bigger than " + STREAM_ALL_MAX_SIZE_ALLOWED + " is not allowed");
		}

		final JSONOptions jsonOpts = JSONOptions.newInstance();

		final ViewRowsOrderBy orderBys = ViewRowsOrderBy.of(defaultSelection.getOrderBys(), jsonOpts);
		return streamPage(0, STREAM_ALL_MAX_SIZE_ALLOWED, filter, orderBys)
				.flatMap(HUEditorRow::streamRecursive)
				.map(HUEditorRow::cast)
				.filter(HUEditorRowFilters.toPredicate(filter));
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
			final DocumentQueryOrderByList defaultOrderBys = getDefaultSelection().getOrderBys();
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
			@NonNull final ViewRowsOrderBy orderBys)
	{
		final Iterator<HUEditorRowId> rowIds = streamHUIdsByPage(firstRow, pageLength, orderBys.toDocumentQueryOrderByList())
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

	private PageFetcher<HuId> huIdsPageFetcher(final DocumentQueryOrderByList orderBys)
	{
		final ViewEvaluationCtx viewEvalCtx = getViewEvaluationCtx();
		final ViewRowIdsOrderedSelection selection = getSelection(orderBys);
		return (firstRow, maxRows) -> huEditorRepo.retrieveHUIdsPage(viewEvalCtx, selection, firstRow, maxRows);
	}

	private Stream<HuId> streamHUIdsByPage(final int firstRow, final int maxRows, final DocumentQueryOrderByList orderBys)
	{
		return IteratorUtils.<HuId>newPagedIterator()
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
		final HuId topLevelHUId = topLevelRowId.getTopLevelHUId();

		final HUEditorRow topLevelRow = cache_huRowsById.getOrLoad(topLevelRowId.toDocumentId(), () -> huEditorRepo.retrieveForHUId(topLevelHUId));
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
	public SqlViewRowsWhereClause getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		return huEditorRepo.buildSqlWhereClause(getDefaultSelection(), rowIds);
	}

	public static boolean isHighVolume(final DocumentFilterList stickyFilters)
	{
		final HUIdsFilterData huIdsFilterData = HUIdsFilterHelper.extractFilterData(stickyFilters).orElse(null);
		return huIdsFilterData == null || huIdsFilterData.isPossibleHighVolume(HIGHVOLUME_THRESHOLD);
	}

	private static class DefaultSelectionHolder
	{
		private final HUEditorViewRepository huEditorViewRepository;
		private final ViewEvaluationCtx viewEvaluationCtx;
		private final ViewId viewId;
		private final DocumentFilterList filters;
		private final DocumentQueryOrderByList orderBys;
		private final SqlDocumentFilterConverterContext filterConverterCtx;

		private final SynchronizedMutable<ViewRowIdsOrderedSelection> defaultSelectionRef;

		@Builder
		private DefaultSelectionHolder(
				@NonNull final HUEditorViewRepository huEditorViewRepository,
				final ViewEvaluationCtx viewEvaluationCtx,
				final ViewId viewId,
				@NonNull final DocumentFilterList filters,
				@Nullable final DocumentQueryOrderByList orderBys,
				final SqlDocumentFilterConverterContext filterConverterCtx)
		{
			this.huEditorViewRepository = huEditorViewRepository;
			this.viewEvaluationCtx = viewEvaluationCtx;
			this.viewId = viewId;
			this.filters = filters;
			this.orderBys = orderBys;
			this.filterConverterCtx = filterConverterCtx;

			defaultSelectionRef = Mutables.synchronizedMutable(create());

		}

		private ViewRowIdsOrderedSelection create()
		{
			return huEditorViewRepository.createSelection(viewEvaluationCtx, viewId, filters, orderBys, filterConverterCtx);
		}

		public ViewRowIdsOrderedSelection get()
		{
			return defaultSelectionRef.computeIfNull(this::create);
		}

		/**
		 * @return true if selection was really changed
		 */
		public boolean change(@NonNull final UnaryOperator<ViewRowIdsOrderedSelection> mapper)
		{
			final ViewRowIdsOrderedSelection defaultSelectionOld = defaultSelectionRef.getValue();

			defaultSelectionRef.computeIfNull(this::create); // make sure it's not null (might be null if it was invalidated)
			final ViewRowIdsOrderedSelection defaultSelectionNew = defaultSelectionRef.compute(mapper);

			return !ViewRowIdsOrderedSelection.equals(defaultSelectionOld, defaultSelectionNew);
		}

		public void delete()
		{
			defaultSelectionRef.computeIfNotNull(defaultSelection -> {
				huEditorViewRepository.deleteSelection(defaultSelection);
				return null;
			});
		}

	}
}
