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
	private static final ImmutableRowsIndex<IViewRow> EMPTY = new ImmutableRowsIndex<>(ImmutableList.of(), ImmutableList.of());

	public static <T extends IViewRow> ImmutableRowsIndex<T> empty()
	{
		//noinspection unchecked
		return (ImmutableRowsIndex<T>)EMPTY;
	}

	public static <T extends IViewRow> ImmutableRowsIndex<T> of(@NonNull final List<T> rows)
	{
		final ImmutableList<DocumentId> initialRowIds = rows.stream()
				.map(IViewRow::getId)
				.collect(ImmutableList.toImmutableList());

		return new ImmutableRowsIndex<>(initialRowIds, rows);
	}

	private final ImmutableList<DocumentId> initialRowIds;
	private final ImmutableList<DocumentId> rowIds; // used to preserve the order
	private final ImmutableMap<DocumentId, T> rowsById;

	private ImmutableRowsIndex(
			@NonNull final ImmutableList<DocumentId> initialRowIds,
			@NonNull final List<T> rows)
	{
		this.initialRowIds = initialRowIds;

		rowIds = rows.stream()
				.map(IViewRow::getId)
				.collect(ImmutableList.toImmutableList());

		rowsById = Maps.uniqueIndex(rows, IViewRow::getId);
	}

	private Stream<T> streamInOrder()
	{
		return rowIds.stream().map(rowsById::get);
	}

	public ImmutableMap<DocumentId, T> getDocumentId2TopLevelRows()
	{
		return getDocumentId2TopLevelRows(row -> true);
	}

	public ImmutableMap<DocumentId, T> getDocumentId2TopLevelRows(@NonNull final Predicate<T> filter)
	{
		return streamInOrder()
				.filter(filter)
				.collect(GuavaCollectors.toImmutableMapByKey(IViewRow::getId));
	}

	public boolean isRelevantForRefreshing(final DocumentId rowId)
	{
		return rowIds.contains(rowId)
				|| initialRowIds.contains(rowId);
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

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows);
	}

	public ImmutableRowsIndex<T> addingRowIfEmpty(@NonNull final T rowToAdd)
	{
		if (rowsById.isEmpty())
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
		final ArrayList<T> resultRows = new ArrayList<>(rowIds.size());
		boolean added = false;
		for (final DocumentId rowId : this.rowIds)
		{
			if (rowId.equals(rowToAdd.getId()))
			{
				resultRows.add(rowToAdd);
				added = true;
			}
			else
			{
				resultRows.add(rowsById.get(rowId));
			}
		}

		if (!added)
		{
			resultRows.add(rowToAdd);
		}

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows);
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
		final ArrayList<T> resultRows = new ArrayList<>(rowIds.size());
		for (final DocumentId rowId : this.rowIds)
		{
			final T row = rowsById.get(rowId);
			if (rowIdsToChange.contains(rowId))
			{
				final T rowChanged = rowMapper.apply(row);
				if (Objects.equals(row, rowChanged))
				{
					resultRows.add(row);
				}
				else
				{
					if (rowChanged != null)
					{
						resultRows.add(rowChanged);
					}
					changed = true;
				}
			}
			else
			{
				resultRows.add(row);
			}
		}

		return changed
				? new ImmutableRowsIndex<>(this.initialRowIds, resultRows)
				: this;
	}

	public ImmutableRowsIndex<T> changingRow(
			@NonNull final DocumentId rowIdToChange,
			@NonNull final UnaryOperator<T> rowMapper)
	{
		final ArrayList<T> resultRows = new ArrayList<>(rowIds.size());
		boolean changed = false;
		for (final DocumentId rowId : this.rowIds)
		{
			if (rowId.equals(rowIdToChange))
			{
				final T row = rowsById.get(rowId);
				final T rowChanged = rowMapper.apply(row);
				if (Objects.equals(row, rowChanged))
				{
					// no change
					return this;
				}

				if (rowChanged != null)
				{
					resultRows.add(rowChanged);
				}
				changed = true;
			}
			else
			{
				resultRows.add(rowsById.get(rowId));
			}
		}

		if (!changed)
		{
			return this;
		}

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows);
	}

	public ImmutableRowsIndex<T> removingRowId(@NonNull final DocumentId rowIdToRemove)
	{
		if (!rowsById.containsKey(rowIdToRemove))
		{
			return this;
		}

		final ArrayList<T> resultRows = new ArrayList<>(rowIds.size());
		for (final DocumentId rowId : this.rowIds)
		{
			if (!rowId.equals(rowIdToRemove))
			{
				resultRows.add(rowsById.get(rowId));
			}
		}

		return new ImmutableRowsIndex<>(this.initialRowIds, resultRows);
	}

	public ImmutableRowsIndex<T> removingRowIds(@NonNull final DocumentIdsSelection rowIdsToRemove)
	{
		if (rowIdsToRemove.isEmpty())
		{
			return this;
		}
		else if (rowIdsToRemove.isAll())
		{
			if (rowIds.isEmpty())
			{
				return this; // already empty, nothing to remove
			}
			return new ImmutableRowsIndex<>(this.initialRowIds, ImmutableList.of());
		}
		else
		{
			final int sizeBeforeRemove = rowIds.size();
			final ArrayList<T> rowsAfterRemove = new ArrayList<>(sizeBeforeRemove);
			for (final DocumentId rowId : this.rowIds)
			{
				if (!rowIdsToRemove.contains(rowId))
				{
					rowsAfterRemove.add(rowsById.get(rowId));
				}
			}

			if (rowsAfterRemove.size() == sizeBeforeRemove)
			{
				return this; // nothing was deleted
			}

			return new ImmutableRowsIndex<>(this.initialRowIds, rowsAfterRemove);
		}
	}

	public ImmutableRowsIndex<T> removingIf(@NonNull final Predicate<T> predicate)
	{
		final int sizeBeforeRemove = rowIds.size();
		final ArrayList<T> rowsAfterRemove = new ArrayList<>(sizeBeforeRemove);
		for (final DocumentId rowId : this.rowIds)
		{
			final T row = rowsById.get(rowId);
			final boolean remove = predicate.test(row);
			if (!remove)
			{
				rowsAfterRemove.add(row);
			}
		}

		if (rowsAfterRemove.size() == sizeBeforeRemove)
		{
			return this; // nothing was deleted
		}

		return new ImmutableRowsIndex<>(this.initialRowIds, rowsAfterRemove);
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
			return Stream.concat(this.rowIds.stream(), this.initialRowIds.stream())
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

	public Stream<T> stream() {return rowsById.values().stream();}

	public Stream<T> stream(final Predicate<T> predicate) {return rowsById.values().stream().filter(predicate);}

	public long count(final Predicate<T> predicate) {return rowsById.values().stream().filter(predicate).count();}

	public boolean anyMatch(final Predicate<T> predicate) {return rowsById.values().stream().anyMatch(predicate);}

	public List<T> list() {return rowIds.stream().map(rowsById::get).collect(ImmutableList.toImmutableList());}
}
