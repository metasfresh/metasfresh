package de.metas.ui.web.payment_allocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class ImmutableRowsIndex<T extends IViewRow>
{
	private final ImmutableList<DocumentId> rowIds; // used to preserve the order
	private final ImmutableMap<DocumentId, T> rowsById;

	ImmutableRowsIndex(@NonNull final List<T> rows)
	{
		rowIds = rows.stream()
				.map(IViewRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = Maps.uniqueIndex(rows, IViewRow::getId);
	}

	private Stream<T> streamAllRows()
	{
		return rowIds.stream().map(rowsById::get);
	}

	public ImmutableMap<DocumentId, T> getDocumentId2TopLevelRows()
	{
		return streamAllRows()
				.collect(GuavaCollectors.toImmutableMapByKey(IViewRow::getId));
	}

	public boolean containsRowId(final DocumentId rowId)
	{
		return rowIds.contains(rowId);
	}

	public ImmutableRowsIndex<T> replacingRows(
			@NonNull final DocumentIdsSelection oldRowIds,
			@NonNull final List<T> newRows)
	{
		final LinkedHashMap<DocumentId, T> newRowsToAdd = newRows.stream()
				.collect(GuavaCollectors.toMapByKey(LinkedHashMap::new, IViewRow::getId));

		final ArrayList<T> resultRows = new ArrayList<>(rowIds.size());
		for (final DocumentId rowId : this.rowIds)
		{
			if (oldRowIds.contains(rowId))
			{
				final T newRowToAdd = newRowsToAdd.remove(rowId);
				if (newRowToAdd != null)
				{
					resultRows.add(newRowToAdd);
				}
			}
			else
			{
				resultRows.add(rowsById.get(rowId));
			}
		}

		resultRows.addAll(newRowsToAdd.values());

		return new ImmutableRowsIndex<>(resultRows);
	}

	public List<T> getRows(@NonNull final DocumentIdsSelection rowIds)
	{
		return streamRows(rowIds).collect(ImmutableList.toImmutableList());
	}

	public Stream<T> streamRows(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return Stream.empty();
		}
		else if (rowIds.isAll())
		{
			return streamAllRows();
		}
		else
		{
			return rowIds.stream()
					.map(rowsById::get)
					.filter(Predicates.notNull());
		}
	}
}
