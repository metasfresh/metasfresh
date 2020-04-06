package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

class PurchaseRowsCollection
{
	private static final int DEFAULT_PAGE_LENGTH = 30;

	public static final PurchaseRowsCollection ofSupplier(final PurchaseRowsSupplier rowsSupplier)
	{
		return new PurchaseRowsCollection(rowsSupplier);
	}

	private final ConcurrentMap<PurchaseRowId, PurchaseRow> topLevelRowsById;

	private PurchaseRowsCollection(@NonNull final PurchaseRowsSupplier rowsSupplier)
	{
		topLevelRowsById = rowsSupplier.retrieveRows()
				.stream()
				.collect(Collectors.toConcurrentMap(PurchaseRow::getRowId, Function.identity()));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(topLevelRowsById).toString();
	}

	public long size()
	{
		return topLevelRowsById.size();
	}

	public List<PurchaseRow> getPage(final int firstRow, final int pageLength)
	{
		return topLevelRowsById.values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : DEFAULT_PAGE_LENGTH)
				.collect(ImmutableList.toImmutableList());
	}

	public List<PurchaseRow> getAll()
	{
		// there are not so many, so we can afford to return all of them
		return ImmutableList.copyOf(topLevelRowsById.values());
	}

	public PurchaseRow getById(@NonNull final DocumentId rowId) throws EntityNotFoundException
	{
		return getById(PurchaseRowId.fromDocumentId(rowId));
	}

	public PurchaseRow getById(@NonNull final PurchaseRowId rowId) throws EntityNotFoundException
	{
		if (rowId.isGroupRowId())
		{
			return getToplevelRowById(rowId);
		}
		else if (rowId.isLineRowId())
		{
			return getToplevelRowById(rowId.toGroupRowId())
					.getIncludedRowById(rowId);
		}
		else
		{
			return getToplevelRowById(rowId.toGroupRowId())
					.getIncludedRowById(rowId.toLineRowId())
					.getIncludedRowById(rowId);
		}
	}

	private PurchaseRow getToplevelRowById(@NonNull final PurchaseRowId topLevelRowId)
	{
		final PurchaseRow topLevelRow = topLevelRowsById.get(topLevelRowId);
		if (topLevelRow != null)
		{
			return topLevelRow;
		}

		throw new EntityNotFoundException("topLevelRow not found")
				.appendParametersToMessage()
				.setParameter("topLevelRowId", topLevelRowId);
	}

	public Stream<? extends IViewRow> streamTopLevelRowsByIds(@NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return topLevelRowsById.values().stream();
		}

		return rowIds.stream().map(this::getById);
	}

	void patchRow(
			@NonNull final PurchaseRowId idOfRowToPatch,
			@NonNull final PurchaseRowChangeRequest request)
	{
		final PurchaseGroupRowEditor editorToUse = PurchaseRow::changeIncludedRow;
		updateRow(idOfRowToPatch, request, editorToUse);
	}

	private void updateRow(
			@NonNull final PurchaseRowId idOfRowToPatch,
			@NonNull final PurchaseRowChangeRequest request,
			@NonNull final PurchaseGroupRowEditor editor)
	{
		topLevelRowsById.compute(idOfRowToPatch.toGroupRowId(), (groupRowId, groupRow) -> {
			if (groupRow == null)
			{
				throw new EntityNotFoundException("Row not found").appendParametersToMessage().setParameter("rowId", groupRowId);
			}

			final PurchaseRow newGroupRow = groupRow.copy();
			if (idOfRowToPatch.isGroupRowId())
			{
				final PurchaseRowId includedRowId = null;
				editor.edit(newGroupRow, includedRowId, request);
			}
			else
			{
				final PurchaseRowId includedRowId = idOfRowToPatch;
				editor.edit(newGroupRow, includedRowId, request);
			}

			return newGroupRow;
		});
	}

	@FunctionalInterface
	private static interface PurchaseGroupRowEditor
	{
		void edit(final PurchaseRow editableGroupRow, final PurchaseRowId includedRowId, final PurchaseRowChangeRequest request);
	}
}
