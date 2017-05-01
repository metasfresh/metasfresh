package de.metas.ui.web.handlingunits;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
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
	private final HUEditorViewRepository huEditorRepo;

	private final CopyOnWriteArraySet<Integer> huIds;
	private final ExtendedMemorizingSupplier<IndexedHUEditorRows> rowsSupplier = ExtendedMemorizingSupplier.of(() -> retrieveHUEditorRows());

	HUEditorViewBuffer_FullyCached(@NonNull final HUEditorViewRepository huEditorRepo, final Collection<Integer> huIds)
	{
		this.huEditorRepo = huEditorRepo;
		this.huIds = new CopyOnWriteArraySet<>(huIds);
	}

	private IndexedHUEditorRows getRows()
	{
		return rowsSupplier.get();
	}

	private IndexedHUEditorRows retrieveHUEditorRows()
	{
		final List<HUEditorRow> rows = huEditorRepo.retrieveHUEditorRows(huIds);
		return new IndexedHUEditorRows(rows);
	}

	@Override
	public long size()
	{
		return getRows().size();
	}

	@Override
	public Stream<HUEditorRow> streamAllRecursive()
	{
		return getRows().streamRecursive();
	}

	@Override
	public Stream<HUEditorRow> streamPage(final int firstRow, final int pageLength, final List<DocumentQueryOrderBy> orderBys)
	{
		Stream<HUEditorRow> stream = getRows().stream()
				.skip(firstRow)
				.limit(pageLength);

		final Comparator<HUEditorRow> comparator = createComparatorOrNull(orderBys);
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

		Comparator<HUEditorRow> comparator = null;
		for (final DocumentQueryOrderBy orderBy : orderBys)
		{
			final Comparator<HUEditorRow> orderByComparator = orderBy.<HUEditorRow> asComparator((row, fieldName) -> row.getFieldValueAsJson(fieldName));
			if (comparator == null)
			{
				comparator = orderByComparator;
			}
			else
			{
				comparator = comparator.thenComparing(orderByComparator);
			}
		}

		return comparator;
	}

	@Override
	public Stream<HUEditorRow> streamByIdsExcludingIncludedRows(final Collection<DocumentId> rowIds)
	{
		if (rowIds == null || rowIds.isEmpty())
		{
			return Stream.empty();
		}

		return getRows().streamByIdsExcludingIncludedRows(rowIds);
	}

	@Override
	public HUEditorRow getById(final DocumentId rowId) throws EntityNotFoundException
	{
		return getRows().getById(rowId);
	}

	@Override
	public Set<DocumentId> getRowIdsMatchingBarcode(@NonNull final String barcode)
	{
		if (Check.isEmpty(barcode, true))
		{
			throw new IllegalArgumentException("Invalid barcode");
		}

		return streamAllRecursive()
				.filter(row -> row.matchesBarcode(barcode))
				.map(HUEditorRow::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public void invalidateAll()
	{
		rowsSupplier.forget();
	}

	@Override
	public boolean addHUIds(final Collection<Integer> huIdsToAdd)
	{
		if (huIdsToAdd.isEmpty())
		{
			return false;
		}

		return huIds.addAll(huIdsToAdd);
	}

	@Override
	public boolean removeHUIds(final Collection<Integer> huIdsToRemove)
	{
		if (huIdsToRemove == null || huIdsToRemove.isEmpty())
		{
			return false;
		}

		return huIds.removeAll(huIdsToRemove);
	}

	@Override
	public boolean containsAnyOfHUIds(final Collection<Integer> huIdsToCheck)
	{
		if (huIdsToCheck == null || huIdsToCheck.isEmpty())
		{
			return false;
		}

		return !Collections.disjoint(huIds, huIdsToCheck);
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
		private final ImmutableMap<DocumentId, DocumentId> rowId2parentId;

		public IndexedHUEditorRows(final List<HUEditorRow> rows)
		{
			super();
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

		public Stream<HUEditorRow> streamByIdsExcludingIncludedRows(final Collection<DocumentId> rowIds)
		{
			if (rowIds == null || rowIds.isEmpty())
			{
				return Stream.empty();
			}

			return rowIds.stream()
					.distinct()
					.filter(rowId -> !isRowIdIncluded(rowIds, rowId))
					.map(rowId -> allRowsById.get(rowId))
					.filter(document -> document != null);
		}

		/** @return true if given <code>childRowId</code> is a direct or indirect child of any of <code>parentRowIds</code> */
		private final boolean isRowIdIncluded(final Collection<DocumentId> parentRowIds, final DocumentId childRowId)
		{
			if (parentRowIds == null || parentRowIds.isEmpty())
			{
				return false;
			}

			// Iterate child row's up-stream (up to the root row)
			DocumentId currentChildId = childRowId;
			while (currentChildId != null)
			{
				final DocumentId currentParentRowId = rowId2parentId.get(currentChildId);
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
			return rows.stream()
					.map(row -> streamRecursive(row))
					.reduce(Stream::concat)
					.orElse(Stream.of());
		}

		private Stream<HUEditorRow> streamRecursive(final HUEditorRow row)
		{
			return row.getIncludedRows()
					.stream()
					.map(includedRow -> streamRecursive(includedRow))
					.reduce(Stream.of(row), Stream::concat);
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

		private static final ImmutableMap<DocumentId, DocumentId> buildRowId2ParentIdMap(final List<HUEditorRow> rows)
		{
			if (rows.isEmpty())
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<DocumentId, DocumentId> rowId2parentId = ImmutableMap.builder();
			rows.forEach(row -> buildRowId2ParentIdMap(rowId2parentId, row));
			return rowId2parentId.build();
		}

		private static final void buildRowId2ParentIdMap(final ImmutableMap.Builder<DocumentId, DocumentId> rowId2parentId, final HUEditorRow parentRow)
		{
			final DocumentId parentId = parentRow.getId();
			parentRow.getIncludedRows()
					.forEach(includedRow -> rowId2parentId.put(includedRow.getId(), parentId));
		}

	}

}
