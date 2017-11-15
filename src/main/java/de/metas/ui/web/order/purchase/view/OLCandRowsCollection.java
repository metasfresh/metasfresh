package de.metas.ui.web.order.purchase.view;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;

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

class OLCandRowsCollection
{
	private static final int DEFAULT_PAGE_LENGTH = 30;

	public static final OLCandRowsCollection ofSupplier(final OLCandRowsSupplier rowsSupplier)
	{
		return new OLCandRowsCollection(rowsSupplier);
	}

	private final ConcurrentMap<OLCandRowId, OLCandRow> rowsById;

	private OLCandRowsCollection(@NonNull final OLCandRowsSupplier rowsSupplier)
	{
		rowsById = rowsSupplier.retrieveRows()
				.stream()
				.collect(Collectors.toConcurrentMap(OLCandRow::getRowId, Function.identity()));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(rowsById).toString();
	}

	public long size()
	{
		return rowsById.size();
	}

	public List<OLCandRow> getPage(final int firstRow, final int pageLength)
	{
		return rowsById.values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : DEFAULT_PAGE_LENGTH)
				.collect(ImmutableList.toImmutableList());
	}

	public List<OLCandRow> getAll()
	{
		// there are not so many, so we can afford to return all of them
		return ImmutableList.copyOf(rowsById.values());
	}

	public OLCandRow getById(@NonNull final OLCandRowId rowId) throws EntityNotFoundException
	{
		final OLCandRow row = rowsById.get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("rowId", rowId);
		}
		return row;
	}

	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return rowsById.values().stream();
		}
		else
		{
			return rowIds.stream()
					.map(OLCandRowId::fromDocumentId)
					.map(this::getById);
		}
	}

	private void updateRow(@NonNull final OLCandRowId rowId, @NonNull final OLCandViewRowEditor editor)
	{
		rowsById.compute(rowId.toGroupRowId(), (groupRowId, groupRow) -> {
			if (groupRow == null)
			{
				throw new EntityNotFoundException("Row not found").setParameter("rowId", groupRowId);
			}

			final OLCandRow newGroupRow = groupRow.copy();
			if (rowId.isGroupRowId())
			{
				final OLCandRowId includedRowId = null;
				editor.edit(newGroupRow, includedRowId);
			}
			else
			{
				final OLCandRowId includedRowId = rowId;
				editor.edit(newGroupRow, includedRowId);
			}

			return newGroupRow;
		});
	}

	public void patchRow(final OLCandRowId rowId, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		updateRow(rowId, (groupRow, includedRowId) -> applyFieldChangeRequests(groupRow, includedRowId, fieldChangeRequests));
	}

	private void applyFieldChangeRequests(final OLCandRow editableGroupRow, final OLCandRowId includedRowId, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		if (includedRowId == null)
		{
			throw new AdempiereException("Only included rows are editable");
		}

		for (final JSONDocumentChangedEvent fieldChangeRequest : fieldChangeRequests)
		{
			final String fieldName = fieldChangeRequest.getPath();
			if (OLCandRow.FIELDNAME_QtyToPurchase.equals(fieldName))
			{
				final BigDecimal qtyToPurchase = fieldChangeRequest.getValueAsBigDecimal();
				editableGroupRow.changeQtyToPurchase(includedRowId, qtyToPurchase);
			}
			else if (OLCandRow.FIELDNAME_DatePromised.equals(fieldName))
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
	private static interface OLCandViewRowEditor
	{
		void edit(final OLCandRow editableGroupRow, final OLCandRowId includedRowId);
	}
}
