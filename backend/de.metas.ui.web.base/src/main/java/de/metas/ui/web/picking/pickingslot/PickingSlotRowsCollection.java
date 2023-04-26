package de.metas.ui.web.picking.pickingslot;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
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

public final class PickingSlotRowsCollection
{
	private static final int DEFAULT_PAGE_LENGTH = 30;

	public static final PickingSlotRowsCollection ofSupplier(final Supplier<List<PickingSlotRow>> rowsSupplier)
	{
		return new PickingSlotRowsCollection(rowsSupplier);
	}

	private final ExtendedMemorizingSupplier<PickingSlotRowsIndex> rowsSupplier;

	private PickingSlotRowsCollection(@NonNull final Supplier<List<PickingSlotRow>> rowsSupplier)
	{
		this.rowsSupplier = ExtendedMemorizingSupplier.of(() -> new PickingSlotRowsIndex(rowsSupplier.get()));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(rowsSupplier).toString();
	}

	public void invalidateAll()
	{
		rowsSupplier.forget();
	}

	private final PickingSlotRowsIndex getRowsIndex()
	{
		return rowsSupplier.get();
	}

	public long size()
	{
		return getRowsIndex().size();
	}

	public List<PickingSlotRow> getPage(final int firstRow, final int pageLength)
	{
		return getRowsIndex().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : DEFAULT_PAGE_LENGTH)
				.collect(ImmutableList.toImmutableList());
	}

	public PickingSlotRow getById(@NonNull final DocumentId id) throws EntityNotFoundException
	{
		final PickingSlotRowId rowId = PickingSlotRowId.fromDocumentId(id);
		return getById(rowId);
	}

	public PickingSlotRow getById(@NonNull final PickingSlotRowId rowId) throws EntityNotFoundException
	{
		if (rowId.getPickingSlotId() == null)
		{
			return assertRowNotNull(rowId, getRowsIndex().getRow(rowId));
		}

		final PickingSlotRowId pickingSlotRowId = PickingSlotRowId.ofPickingSlotId(rowId.getPickingSlotId());
		final PickingSlotRow pickingSlotRow = getRowsIndex().getRow(pickingSlotRowId);

		if (java.util.Objects.equals(rowId, pickingSlotRowId))
		{
			return assertRowNotNull(pickingSlotRowId, pickingSlotRow);
		}

		return pickingSlotRow.findIncludedRowById(rowId)
				.orElseThrow(() -> new EntityNotFoundException("Row not found").setParameter("pickingSlotRow", pickingSlotRow).setParameter("rowId", rowId));
	}

	public PickingSlotRow getRootRowWhichIncludes(@NonNull final PickingSlotRowId rowId)
	{
		final PickingSlotRow rootRow = getRowsIndex().getRootRow(rowId);
		if (rootRow == null)
		{
			throw new AdempiereException("No root row found which includes " + rowId);
		}
		return rootRow;
	}

	public PickingSlotRowId getRootRowIdWhichIncludes(@NonNull final PickingSlotRowId rowId)
	{
		return getRowsIndex().getRootRowId(rowId);
	}

	private static final PickingSlotRow assertRowNotNull(final PickingSlotRowId pickingSlotRowId, final PickingSlotRow pickingSlotRow)
	{
		if (pickingSlotRow == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("pickingSlotRowId", pickingSlotRowId);
		}
		return pickingSlotRow;
	}

	public Stream<PickingSlotRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getRowsIndex().stream();
		}
		else
		{
			return rowIds.stream().map(this::getById);
		}
	}

	@ToString
	private static final class PickingSlotRowsIndex
	{
		private final ImmutableMap<PickingSlotRowId, PickingSlotRow> rowsById;
		private final ImmutableMap<PickingSlotRowId, PickingSlotRowId> rowId2rootRowId;

		private PickingSlotRowsIndex(final List<PickingSlotRow> rows)
		{
			rowsById = Maps.uniqueIndex(rows, PickingSlotRow::getPickingSlotRowId);

			rowId2rootRowId = rows.stream()
					.flatMap(rootRow -> streamChild2RootRowIdsRecursivelly(rootRow))
					.collect(GuavaCollectors.toImmutableMap());
		}

		private static Stream<Map.Entry<PickingSlotRowId, PickingSlotRowId>> streamChild2RootRowIdsRecursivelly(final PickingSlotRow row)
		{
			final PickingSlotRowId rootRowId = row.getPickingSlotRowId();
			return row.streamThisRowAndIncludedRowsRecursivelly()
					.map(PickingSlotRow::getPickingSlotRowId)
					.map(includedRowId -> GuavaCollectors.entry(includedRowId, rootRowId));
		}

		public PickingSlotRow getRow(final PickingSlotRowId rowId)
		{
			return rowsById.get(rowId);
		}

		@Nullable
		public PickingSlotRow getRootRow(final PickingSlotRowId rowId)
		{
			final PickingSlotRowId rootRowId = getRootRowId(rowId);
			if (rootRowId == null)
			{
				return null;
			}
			return getRow(rootRowId);
		}
		
		public PickingSlotRowId getRootRowId(final PickingSlotRowId rowId)
		{
			return rowId2rootRowId.get(rowId);
		}


		public long size()
		{
			return rowsById.size();
		}

		public Stream<PickingSlotRow> stream()
		{
			return rowsById.values().stream();
		}
	}
}
