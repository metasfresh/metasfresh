package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsFilterData;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowsOrderBy;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
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

	private final DocumentFilterList stickyFiltersWithoutHUIdsFilter;

	private final HUIdsFilterData huIdsFilterData;
	private final ExtendedMemorizingSupplier<CopyOnWriteArraySet<HuId>> huIdsSupplier;
	private final ExtendedMemorizingSupplier<IndexedHUEditorRows> rowsSupplier = ExtendedMemorizingSupplier.of(() -> retrieveHUEditorRows());

	private final DocumentQueryOrderByList defaultOrderBys;

	HUEditorViewBuffer_FullyCached(
			@NonNull final ViewId viewId,
			@NonNull final HUEditorViewRepository huEditorRepo,
			final DocumentFilterList stickyFilters,
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			@NonNull final SqlDocumentFilterConverterContext context)
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
				.collect(DocumentFilterList.toDocumentFilterList());

		final DocumentFilterList filtersAll = stickyFiltersWithoutHUIdsFilter.mergeWith(filters);

		huIdsSupplier = ExtendedMemorizingSupplier.of(() -> new CopyOnWriteArraySet<>(huEditorRepo.retrieveHUIdsEffective(this.huIdsFilterData, filtersAll, context)));

		this.defaultOrderBys = orderBys != null ? orderBys : DocumentQueryOrderByList.EMPTY;
	}

	@Override
	public ViewId getViewId()
	{
		return viewId;
	}

	@Override
	public DocumentFilterList getStickyFilters()
	{
		final DocumentFilter huIdsFilter = HUIdsFilterHelper.createFilter(huIdsFilterData.copy());
		return stickyFiltersWithoutHUIdsFilter.mergeWith(huIdsFilter);
	}

	private CopyOnWriteArraySet<HuId> getHUIds()
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
		final Set<HuId> huIds = getHUIds();
		final List<HUEditorRow> rows = huEditorRepo.retrieveHUEditorRows(huIds, HUEditorRowFilter.ALL);
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
	public Stream<HUEditorRow> streamPage(
			final int firstRow,
			final int pageLength,
			@NonNull final HUEditorRowFilter filter,
			@NonNull final ViewRowsOrderBy orderBys)
	{
		final ViewRowsOrderBy orderBysEffective = !orderBys.isEmpty()
				? orderBys
				: orderBys.withOrderBys(defaultOrderBys);

		Stream<HUEditorRow> stream = getRows().stream()
				.skip(firstRow)
				.limit(pageLength)
				.filter(HUEditorRowFilters.toPredicate(filter));

		final Comparator<HUEditorRow> comparator = orderBysEffective.toComparatorOrNull();
		if (comparator != null)
		{
			stream = stream.sorted(comparator);
		}

		return stream;
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
	public boolean addHUIds(final Collection<HuId> huIdsToAdd)
	{
		if (huIdsToAdd.isEmpty())
		{
			return false;
		}

		getHUIdsFilterData().mustHUIds(huIdsToAdd);
		return getHUIds().addAll(huIdsToAdd);
	}

	@Override
	public boolean removeHUIds(final Collection<HuId> huIdsToRemove)
	{
		if (huIdsToRemove == null || huIdsToRemove.isEmpty())
		{
			return false;
		}

		getHUIdsFilterData().shallNotHUIds(huIdsToRemove);
		return getHUIds().removeAll(huIdsToRemove);
	}

	@Override
	public boolean containsAnyOfHUIds(final Collection<HuId> huIdsToCheck)
	{
		if (huIdsToCheck == null || huIdsToCheck.isEmpty())
		{
			return false;
		}

		return !Collections.disjoint(getHUIds(), huIdsToCheck);
	}

	@Override
	public SqlViewRowsWhereClause getSqlWhereClause(final DocumentIdsSelection rowIds)
	{
		final DocumentIdsSelection rowIdsEffective = getRows().streamByIdsExcludingIncludedRows(HUEditorRowFilter.onlyRowIds(rowIds))
				.map(HUEditorRow::getId)
				.collect(DocumentIdsSelection.toDocumentIdsSelection());

		final Set<Integer> huIds = huEditorRepo.getRowIdsConverter().convertToRecordIds(rowIdsEffective);

		// NOTE: accept it even if is empty.
		// Same this is happening for the others HUEditorViewBuffer implementation.
		// see https://github.com/metasfresh/metasfresh-webui-api/issues/764
		if (huIds.isEmpty())
		{
			return SqlViewRowsWhereClause.noRecords();
		}

		final String sqlKeyColumnNameFK = I_M_HU.Table_Name + "." + I_M_HU.COLUMNNAME_M_HU_ID;
		return SqlViewRowsWhereClause.builder()
				.rowsPresentInTable(SqlAndParams.of(sqlKeyColumnNameFK + " IN " + DB.buildSqlList(huIds)))
				.build();
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

		public IndexedHUEditorRows(@NonNull final List<HUEditorRow> rows)
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
						.filter(Objects::nonNull)
						.filter(HUEditorRowFilters.toPredicate(filter));
			}
		}

		/** @return true if given <code>childRowId</code> is a direct or indirect child of any of <code>parentRowIds</code> */
		private boolean isRowIdIncluded(final Set<HUEditorRowId> parentRowIds, final HUEditorRowId childRowId)
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

		private static void indexByIdRecursively(final ImmutableMap.Builder<DocumentId, HUEditorRow> collector, final HUEditorRow row)
		{
			collector.put(row.getId(), row);
			row.getIncludedRows()
					.forEach(includedRow -> indexByIdRecursively(collector, includedRow));
		}

		private static ImmutableMap<HUEditorRowId, HUEditorRowId> buildRowId2ParentIdMap(final List<HUEditorRow> rows)
		{
			if (rows.isEmpty())
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<HUEditorRowId, HUEditorRowId> rowId2parentId = ImmutableMap.builder();
			rows.forEach(row -> buildRowId2ParentIdMap(rowId2parentId, row));
			return rowId2parentId.build();
		}

		private static void buildRowId2ParentIdMap(final ImmutableMap.Builder<HUEditorRowId, HUEditorRowId> rowId2parentId, final HUEditorRow parentRow)
		{
			final HUEditorRowId parentId = parentRow.getHURowId();
			parentRow.getIncludedRows()
					.forEach(includedRow -> rowId2parentId.put(includedRow.getHURowId(), parentId));
		}
	}
}
