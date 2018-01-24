package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
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

	public PurchaseRow getById(@NonNull final PurchaseRowId rowId) throws EntityNotFoundException
	{
		final PurchaseRow topLevelRow = topLevelRowsById.get(rowId);
		if (topLevelRow != null)
		{
			return topLevelRow;
		}

		final Optional<PurchaseRow> recursivelyFoundRow = topLevelRowsById.values().stream()
				.flatMap(IViewRow::streamRecursive)
				.filter(row -> row.getId().equals(rowId.toDocumentId()))
				.map(row -> (PurchaseRow)row)
				.findAny();

		return recursivelyFoundRow
				.orElseThrow(() -> new EntityNotFoundException("Row not found")
						.appendParametersToMessage()
						.setParameter("rowId", rowId));
	}

	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return topLevelRowsById.values().stream();
		}
		else
		{
			return rowIds.stream()
					.map(PurchaseRowId::fromDocumentId)
					.map(this::getById);
		}
	}

	private void updateRow(@NonNull final PurchaseRowId rowId, @NonNull final PurchaseGroupRowEditor editor)
	{
		topLevelRowsById.compute(rowId.toGroupRowId(), (groupRowId, groupRow) -> {
			if (groupRow == null)
			{
				throw new EntityNotFoundException("Row not found").setParameter("rowId", groupRowId);
			}

			final PurchaseRow newGroupRow = groupRow.copy();
			if (rowId.isGroupRowId())
			{
				final PurchaseRowId includedRowId = null;
				editor.edit(newGroupRow, includedRowId);
			}
			else
			{
				final PurchaseRowId includedRowId = rowId;
				editor.edit(newGroupRow, includedRowId);
			}

			return newGroupRow;
		});
	}

	public void patchRow(
			final PurchaseRowId rowId,
			final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		updateRow(
				rowId,
				(groupRow, includedRowId) -> applyFieldChangeRequests(groupRow, includedRowId, fieldChangeRequests));
	}

	private void applyFieldChangeRequests(
			@NonNull final PurchaseRow editableGroupRow,
			final PurchaseRowId includedRowId,
			@NonNull final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		Check.errorIf(includedRowId == null,
				"Only group rows with an includedRowId may be edited, but includedRowId=null; fieldChangeRequests={}; editableGroupRow={}",
				fieldChangeRequests, editableGroupRow);

		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (PurchaseRow.FIELDNAME_QtyToPurchase.equals(fieldName))
			{
				final BigDecimal qtyToPurchase = fieldChangeRequest.getValueAsBigDecimal();
				editableGroupRow.changeQtyToPurchase(includedRowId, qtyToPurchase);
			}
			else if (PurchaseRow.FIELDNAME_DatePromised.equals(fieldName))
			{
				final Date datePromised = fieldChangeRequest.getValueAsDateTime();
				editableGroupRow.changeDatePromised(includedRowId, datePromised);
			}
			else
			{
				throw new AdempiereException("Field " + fieldName + " is not editable").setParameter("row", editableGroupRow);
			}
		}
	}

	@FunctionalInterface
	private static interface PurchaseGroupRowEditor
	{
		void edit(final PurchaseRow editableGroupRow, final PurchaseRowId includedRowId);
	}
}
