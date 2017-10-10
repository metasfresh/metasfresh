package de.metas.ui.web.picking.pickingslot;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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

public final class PickingSlotRowsCollection
{
	private static final int DEFAULT_PAGE_LENGTH = 30;

	public static final PickingSlotRowsCollection ofSupplier(final Supplier<List<PickingSlotRow>> rowsSupplier)
	{
		return new PickingSlotRowsCollection(rowsSupplier);
	}

	private final ExtendedMemorizingSupplier<Map<PickingSlotRowId, PickingSlotRow>> rowsSupplier;

	private PickingSlotRowsCollection(@NonNull final Supplier<List<PickingSlotRow>> rowsSupplier)
	{
		this.rowsSupplier = ExtendedMemorizingSupplier.of(() -> Maps.uniqueIndex(rowsSupplier.get(), PickingSlotRow::getPickingSlotRowId));
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

	private final Map<PickingSlotRowId, PickingSlotRow> getRowsMap()
	{
		return rowsSupplier.get();
	}

	public long size()
	{
		return getRowsMap().size();
	}

	public List<PickingSlotRow> getPage(final int firstRow, final int pageLength)
	{
		return getRowsMap().values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : DEFAULT_PAGE_LENGTH)
				.collect(ImmutableList.toImmutableList());
	}

	public PickingSlotRow getById(@NonNull final DocumentId id) throws EntityNotFoundException
	{
		final PickingSlotRowId rowId = PickingSlotRowId.fromDocumentId(id);

		if (rowId.getPickingSlotId() <= 0)
		{
			return assertRowNotNull(rowId, getRowsMap().get(rowId));
		}

		final PickingSlotRowId pickingSlotRowId = PickingSlotRowId.ofPickingSlotId(rowId.getPickingSlotId());
		final PickingSlotRow pickingSlotRow = getRowsMap().get(pickingSlotRowId);

		if (java.util.Objects.equals(rowId, pickingSlotRowId))
		{
			return assertRowNotNull(pickingSlotRowId, pickingSlotRow);
		}

		return pickingSlotRow.findIncludedRowById(rowId)
				.orElseThrow(() -> new EntityNotFoundException("Row not found").setParameter("pickingSlotRow", pickingSlotRow).setParameter("rowId", rowId));
	}

	private static final PickingSlotRow assertRowNotNull(final PickingSlotRowId pickingSlotRowId, final PickingSlotRow pickingSlotRow)
	{
		if (pickingSlotRow == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("pickingSlotRowId", pickingSlotRowId);
		}
		return pickingSlotRow;
	}

	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getRowsMap().values().stream();
		}
		else
		{
			return rowIds.stream().map(this::getById);
		}
	}
}
