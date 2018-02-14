package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.util.DB;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsFilterData;
import de.metas.ui.web.view.ViewId;
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

/**
 * {@link HUEditorViewBuffer} implementation which fully caches the {@link HUEditorRow}s.
 *
 * This implementation shall be used when dealing with small amount of HUs.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class HUEditorViewBuffer_FullyCached implements HUEditorViewBuffer
{
	private final ViewId viewId;
	private final HUEditorViewRepository huEditorRepo;

	private final ImmutableList<DocumentFilter> stickyFiltersWithoutHUIdsFilter;

	private final HUIdsFilterData huIdsFilterData;
	private final ExtendedMemorizingSupplier<CopyOnWriteArraySet<Integer>> huIdsSupplier;
	private final ExtendedMemorizingSupplier<IndexedHUEditorRows> rowsSupplier = ExtendedMemorizingSupplier.of(() -> retrieveHUEditorRows());

	private final ImmutableList<DocumentQueryOrderBy> defaultOrderBys;

	HUEditorViewBuffer_FullyCached(
			@NonNull final ViewId viewId,
			@NonNull final HUEditorViewRepository huEditorRepo,
			final List<DocumentFilter> stickyFilters,
			final List<DocumentFilter> filters,
			final List<DocumentQueryOrderBy> orderBys)
	{
		this.viewId = viewId;
		this.huEditorRepo = huEditorRepo;

		HUIdsFilterData huIdsFilterData = HUIdsFilterHelper.extractFilterDataOrNull(stickyFilters);
		if (huIdsFilterData == null)
		{
			huIdsFilterData = HUIdsFilterData.newEmpty();
		}
		else
		{
			huIdsFilterData = huIdsFilterData.copy();
		}
		this.huIdsFilterData = huIdsFilterData;

		stickyFiltersWithoutHUIdsFilter = stickyFilters.stream()
				.filter(HUIdsFilterHelper::isNotHUIdsFilter)
				.collect(ImmutableList.toImmutableList());

		final List<DocumentFilter> filtersAll = ImmutableList.copyOf(Iterables.concat(stickyFiltersWithoutHUIdsFilter, filters));

		huIdsSupplier = ExtendedMemorizingSupplier.of(() -> new CopyOnWriteArraySet<>(huEditorRepo.retrieveHUIdsEffective(this.huIdsFilterData, filtersAll)));

		this.defaultOrderBys = orderBys != null ? ImmutableList.copyOf(orderBys) : ImmutableList.of();
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public List<DocumentFilter> getStickyFilters()
	{
		return ImmutableList.<DocumentFilter> builder()
				.add(HUIdsFilterHelper.createFilter(huIdsFilterData.copy()))
				.addAll(stickyFiltersWithoutHUIdsFilter)
				.build();
	}

	private CopyOnWriteArraySet<Integer> getHUIds()
	{
		return huIdsSupplier.get();
	}

	private HUIdsFilterData getHUIdsFilterData()
	{
		return huIdsFilterData;
	}

	private IndexedHUEditorRows getRows()
	{
		return rowsSupplier.get();
	}

	private IndexedHUEditorRows retrieveHUEditorRows()
	{
		final List<HUEditorRow> rows = huEditorRepo.retrieveHUEditorRows(getHUIds(), HUEditorRowFilter.ALL);
		return new IndexedHUEditorRows(rows);
	}

	@Override
	public long size()
	{
		return getRows().size();
	}

	@Override
	public Stream<HUEditorRow> streamAllRecursive(@NonNull final HUEditorRowFilter filter)
	{
		return getRows().streamRecursive().filter(HUEditorRowFilters.toPredicate(filter));
	}

	@Override
	public Stream<HUEditorRow> streamPage(final int firstRow, final int pageLength, @NonNull final HUEditorRowFilter filter, final List<DocumentQueryOrderBy> orderBys)
	{
		final List<DocumentQueryOrderBy> orderBysEffective = !orderBys.isEmpty() ? orderBys : defaultOrderBys;
		Stream<HUEditorRow> stream = getRows().stream()
				.skip(firstRow)
				.limit(pageLength)
				.filter(HUEditorRowFilters.toPredicate(filter));

		final Comparator<HUEditorRow> comparator = createComparatorOrNull(orderBysEffective);
		if (comparator != null)
		{
			stream = stream.sorted(comparator);
		}

		return stream;
	}

	private static final Comparator<HUEditorRow> createComparatorOrNull(final List<DocumentQueryOrderBy> orderBys)
	{
		if (orderBys == null || orderBys.isEmpty())
		{
			return null;
		}

		return orderBys.stream()
				.map(orderBy -> orderBy.asComparator(HUEditorRow::getFieldValueAsJson))
				.reduce((cmp1, cmp2) -> cmp1.thenComparing(cmp2))
				.orElse(null);
	}

	@Override
	public Stream<HUEditorRow> streamByIdsExcludingIncludedRows(@NonNull final HUEditorRowFilter filter)
	{
		return getRows().streamByIdsExcludingIncludedRows(filter);
	}

	@Override
	public HUEditorRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return getRows().getById(rowId);
	}

	@Override
	public void invalidateAll()
	{
		huIdsSupplier.forget();
		huEditorRepo.invalidateCache();
		rowsSupplier.forget();
	}

	@Override
	public boolean addHUIds(final Collection<Integer> huIdsToAdd)
	{
		if (huIdsToAdd.isEmpty())
		{
			return false;
		}

		getHUIdsFilterData().mustHUIds(huIdsToAdd);
		return getHUIds().addAll(huIdsToAdd);
	}

	@Override
	public boolean removeHUIds(final Collection<Integer> huIdsToRemove)
	{
		if (huIdsToRemove == null || huIdsToRemove.isEmpty())
		{
			return false;
		}

		getHUIdsFilterData().shallNotHUIds(huIdsToRemove);
		return getHUIds().removeAll(huIdsToRemove);
	}

	@Override
	public boolean containsAnyOfHUIds(final Collection<Integer> huIdsToCheck)
	{
		if (huIdsToCheck == null || huIdsToCheck.isEmpty())
		{
			return false;
		}

		return !Collections.disjoint(getHUIds(), huIdsToCheck);
	}

	@Override
	public String getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		final DocumentIdsSelection rowIdsEffective = getRows().streamByIdsExcludingIncludedRows(HUEditorRowFilter.onlyRowIds(rowIds))
				.map(HUEditorRow::getId)
				.collect(DocumentIdsSelection.toDocumentIdsSelection());

		final Set<Integer> huIds = huEditorRepo.convertToRecordIds(rowIdsEffective);
		// NOTE: accept it even if is empty. In case it's empty, we will return something like M_HU_ID in (-1)
		// same this is happening for the others HUEditorViewBuffer implementation
		// see https://github.com/metasfresh/metasfresh-webui-api/issues/764

		final String sqlKeyColumnNameFK = I_M_HU.Table_Name + "." + I_M_HU.COLUMNNAME_M_HU_ID;
		return sqlKeyColumnNameFK + " IN " + DB.buildSqlList(huIds);
	}

	//
	//
	//
	private static final class IndexedHUEditorRows
	{
		/** Top level rows list */
		private final ImmutableList<HUEditorRow> rows;
		/** All rows (included ones too) indexed by row Id */
		private final ImmutableMap<DocumentId, HUEditorRow> allRowsById;

		/** "rowId" to "parent's rowId" mapping */
		private final ImmutableMap<HUEditorRowId, HUEditorRowId> rowId2parentId;

		public IndexedHUEditorRows(final List<HUEditorRow> rows)
		{
			this.rows = ImmutableList.copyOf(rows);

			allRowsById = buildRowsByIdMap(this.rows);
			rowId2parentId = buildRowId2ParentIdMap(this.rows);
		}

		public HUEditorRow getById(final DocumentId rowId)
		{
			final HUEditorRow record = allRowsById.get(rowId);
			if (record == null)
			{
				throw new EntityNotFoundException("No document found for rowId=" + rowId);
			}
			return record;
		}

		public Stream<HUEditorRow> streamByIdsExcludingIncludedRows(final HUEditorRowFilter filter)
		{
			final ImmutableSet<HUEditorRowId> onlyRowIds = filter.getOnlyRowIds();
			if (onlyRowIds.isEmpty())
			{
				return allRowsById.values().stream()
						.filter(HUEditorRowFilters.toPredicate(filter));
			}
			else
			{
				return onlyRowIds.stream()
						.distinct()
						.filter(rowId -> !isRowIdIncluded(onlyRowIds, rowId))
						.map(rowId -> allRowsById.get(rowId.toDocumentId()))
						.filter(Predicates.notNull())
						.filter(HUEditorRowFilters.toPredicate(filter));
			}
		}

		/** @return true if given <code>childRowId</code> is a direct or indirect child of any of <code>parentRowIds</code> */
		private final boolean isRowIdIncluded(final Set<HUEditorRowId> parentRowIds, final HUEditorRowId childRowId)
		{
			if (parentRowIds == null || parentRowIds.isEmpty())
			{
				return false;
			}

			// Iterate child row's up-stream (up to the root row)
			HUEditorRowId currentChildId = childRowId;
			while (currentChildId != null)
			{
				final HUEditorRowId currentParentRowId = rowId2parentId.get(currentChildId);
				if (currentParentRowId == null)
				{
					// => not included (currentChildId is a top level row)
					return false;
				}

				if (parentRowIds.contains(currentParentRowId))
				{
					// => included (current child is included in our parents list)
					return true;
				}

				currentChildId = currentParentRowId;
			}

			return false;
		}

		public Stream<HUEditorRow> stream()
		{
			return rows.stream();
		}

		public Stream<HUEditorRow> streamRecursive()
		{
			return stream()
					.flatMap(HUEditorRow::streamRecursive)
					.map(HUEditorRow::cast);
		}

		public long size()
		{
			return rows.size();
		}

		private static ImmutableMap<DocumentId, HUEditorRow> buildRowsByIdMap(final List<HUEditorRow> rows)
		{
			if (rows.isEmpty())
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<DocumentId, HUEditorRow> rowsById = ImmutableMap.builder();
			rows.forEach(row -> indexByIdRecursively(rowsById, row));
			return rowsById.build();
		}

		private static final void indexByIdRecursively(final ImmutableMap.Builder<DocumentId, HUEditorRow> collector, final HUEditorRow row)
		{
			collector.put(row.getId(), row);
			row.getIncludedRows()
					.forEach(includedRow -> indexByIdRecursively(collector, includedRow));
		}

		private static final ImmutableMap<HUEditorRowId, HUEditorRowId> buildRowId2ParentIdMap(final List<HUEditorRow> rows)
		{
			if (rows.isEmpty())
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<HUEditorRowId, HUEditorRowId> rowId2parentId = ImmutableMap.builder();
			rows.forEach(row -> buildRowId2ParentIdMap(rowId2parentId, row));
			return rowId2parentId.build();
		}

		private static final void buildRowId2ParentIdMap(final ImmutableMap.Builder<HUEditorRowId, HUEditorRowId> rowId2parentId, final HUEditorRow parentRow)
		{
			final HUEditorRowId parentId = parentRow.getHURowId();
			parentRow.getIncludedRows()
					.forEach(includedRow -> rowId2parentId.put(includedRow.getHURowId(), parentId));
		}

	}

}
