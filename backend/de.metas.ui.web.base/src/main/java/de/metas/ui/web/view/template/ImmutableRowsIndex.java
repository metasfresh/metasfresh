package de.metas.ui.web.view.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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

public final class ImmutableRowsIndex<T extends IViewRow>
{
	public static <T extends IViewRow> ImmutableRowsIndex<T> of(@NonNull final List<T> rows)
	{
		return of(rows, row -> true);
	}

	public static <T extends IViewRow> ImmutableRowsIndex<T> of(@NonNull final List<T> rows, @NonNull final Predicate<T> filter)
	{
		final ImmutableList<DocumentId> initialRowIds = rows.stream()
				.map(IViewRow::getId)
				.collect(ImmutableList.toImmutableList());

		return new ImmutableRowsIndex<>(initialRowIds, rows, filter);
	}

	private final ImmutableList<DocumentId> initialRowIds;
	private final ImmutableList<DocumentId> allRowIdsInOrder;
	private final ImmutableMap<DocumentId, T> allRowsById;
	private final Predicate<T> filter;
	private final ImmutableList<DocumentId> filteredRowIdsInOrder;

	private ImmutableRowsIndex(
			@NonNull final ImmutableList<DocumentId> initialRowIds,
			@NonNull final List<T> rowsInOrder,
			@NonNull final Predicate<T> filter)
	{
		this.initialRowIds = initialRowIds;

		this.allRowIdsInOrder = rowsInOrder.stream()
				.map(IViewRow::getId)
				.collect(ImmutableList.toImmutableList());
		this.allRowsById = Maps.uniqueIndex(rowsInOrder, IViewRow::getId);

		this.filter = filter;
		this.filteredRowIdsInOrder = rowsInOrder.stream()
				.filter(filter)
				.map(IViewRow::getId)
				.collect(ImmutableList.toImmutableList());

	}

	private ImmutableRowsIndex(
			@NonNull final ImmutableRowsIndex<T> from,
			@NonNull final Predicate<T> filter)
	{
		this.initialRowIds = from.initialRowIds;

		this.allRowIdsInOrder = from.allRowIdsInOrder;
		this.allRowsById = from.allRowsById;

		this.filter = filter;
		this.filteredRowIdsInOrder = allRowIdsInOrder.stream()
				.map(allRowsById::get)
				.filter(filter)
				.map(IViewRow::getId)
				.collect(ImmutableList.toImmutableList());
	}

	private Stream<T> streamAllRows()
	{
		return filteredRowIdsInOrder.stream().map(allRowsById::get);
	}

	public ImmutableMap<DocumentId, T> getDocumentId2TopLevelRows()
	{
		return streamAllRows()
				.collect(GuavaCollectors.toImmutableMapByKey(IViewRow::getId));
	}

	public boolean isRelevantForRefreshing(final DocumentId rowId)
	{
		return filteredRowIdsInOrder.contains(rowId)
				|| initialRowIds.contains(rowId);
	}

	public ImmutableRowsIndex<T> replacingRows(
			@NonNull final DocumentIdsSelection oldRowIds,
			@NonNull final List<T> newRows)
	{
		final LinkedHashMap<DocumentId, T> newRowsToAdd = newRows.stream()
				.collect(GuavaCollectors.toMapByKey(LinkedHashMap::new, IViewRow::getId));

		final ArrayList<T> resultRows = new ArrayList<>(filteredRowIdsInOrder.size());
		for (final DocumentId rowId : this.filteredRowIdsInOrder)
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
				resultRows.add(allRowsById.get(rowId));
			}
		}

		resultRows.addAll(newRowsToAdd.values());

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows, filter);
	}

	public ImmutableRowsIndex<T> addingRowIfEmpty(@NonNull final T rowToAdd)
	{
		if (allRowsById.isEmpty())
		{
			return addingRow(rowToAdd);
		}
		else
		{
			return this;
		}
	}

	public ImmutableRowsIndex<T> addingRow(@NonNull final T rowToAdd)
	{
		final ArrayList<T> resultRows = new ArrayList<>(filteredRowIdsInOrder.size());
		boolean added = false;
		for (final DocumentId rowId : this.filteredRowIdsInOrder)
		{
			if (rowId.equals(rowToAdd.getId()))
			{
				resultRows.add(rowToAdd);
				added = true;
			}
			else
			{
				resultRows.add(allRowsById.get(rowId));
			}
		}

		if (!added)
		{
			resultRows.add(rowToAdd);
		}

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows, filter);
	}

	public ImmutableRowsIndex<T> changingRows(
			@NonNull final DocumentIdsSelection rowIdsToChange,
			@NonNull final UnaryOperator<T> rowMapper)
	{
		if (rowIdsToChange.isEmpty())
		{
			return this;
		}

		boolean changed = false;
		final ArrayList<T> resultRows = new ArrayList<>(filteredRowIdsInOrder.size());
		for (final DocumentId rowId : this.filteredRowIdsInOrder)
		{
			final T row = allRowsById.get(rowId);
			if (rowIdsToChange.contains(rowId))
			{
				final T rowChanged = rowMapper.apply(row);
				if (Objects.equals(row, rowChanged))
				{
					resultRows.add(row);
				}
				else
				{
					resultRows.add(rowChanged);
					changed = true;
				}
			}
			else
			{
				resultRows.add(row);
			}
		}

		return changed
				? new ImmutableRowsIndex<>(this.initialRowIds, resultRows, filter)
				: this;
	}

	public ImmutableRowsIndex<T> changingRow(
			@NonNull final DocumentId rowIdToChange,
			@NonNull final UnaryOperator<T> rowMapper)
	{
		final ArrayList<T> resultRows = new ArrayList<>(filteredRowIdsInOrder.size());
		boolean changed = false;
		for (final DocumentId rowId : this.filteredRowIdsInOrder)
		{
			if (rowId.equals(rowIdToChange))
			{
				final T row = allRowsById.get(rowId);
				final T rowChanged = rowMapper.apply(row);
				if (Objects.equals(row, rowChanged))
				{
					// no change
					return this;
				}

				resultRows.add(rowChanged);
				changed = true;
			}
			else
			{
				resultRows.add(allRowsById.get(rowId));
			}
		}

		if (!changed)
		{
			return this;
		}

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows, filter);
	}

	public ImmutableRowsIndex<T> removingRowId(@NonNull final DocumentId rowIdToRemove)
	{
		if (!allRowsById.containsKey(rowIdToRemove))
		{
			return this;
		}

		final ArrayList<T> resultRows = new ArrayList<>(filteredRowIdsInOrder.size());
		for (final DocumentId rowId : this.filteredRowIdsInOrder)
		{
			if (!rowId.equals(rowIdToRemove))
			{
				resultRows.add(allRowsById.get(rowId));
			}
		}

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows, filter);
	}

	public <ID extends RepoIdAware> ImmutableSet<ID> getRecordIdsToRefresh(
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final Function<DocumentId, ID> idMapper)
	{
		if (rowIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else if (rowIds.isAll())
		{
			return Stream.concat(this.filteredRowIdsInOrder.stream(), this.initialRowIds.stream())
					.distinct()
					.map(idMapper)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			return rowIds.stream()
					.filter(this::isRelevantForRefreshing)
					.map(idMapper)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	public ImmutableRowsIndex<T> withFilter(@NonNull final Predicate<T> filter)
	{
		return Objects.equals(this.filter, filter)
				? this
				: new ImmutableRowsIndex<>(this, filter);
	}
}
