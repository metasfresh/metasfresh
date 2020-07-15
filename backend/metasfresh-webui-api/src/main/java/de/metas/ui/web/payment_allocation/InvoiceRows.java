package de.metas.ui.web.payment_allocation;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_Invoice;

import com.google.common.collect.ImmutableSet;

import de.metas.invoice.InvoiceId;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.Builder;
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

public class InvoiceRows implements IEditableRowsData<InvoiceRow>
{
	public static InvoiceRows cast(final IRowsData<InvoiceRow> rows)
	{
		return (InvoiceRows)rows;
	}

	private final PaymentAndInvoiceRowsRepo repository;
	private final ZonedDateTime evaluationDate;
	private final SynchronizedRowsIndexHolder<InvoiceRow> rowsHolder;

	@Builder
	private InvoiceRows(
			@NonNull final PaymentAndInvoiceRowsRepo repository,
			@NonNull final List<InvoiceRow> initialRows,
			@NonNull final ZonedDateTime evaluationDate)
	{
		this.repository = repository;
		this.evaluationDate = evaluationDate;
		rowsHolder = SynchronizedRowsIndexHolder.of(initialRows);
	}

	@Override
	public Map<DocumentId, InvoiceRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return recordRefs.streamIds(I_C_Invoice.Table_Name, InvoiceId::ofRepoId)
				.map(InvoiceRow::convertInvoiceIdToDocumentId)
				.filter(rowsHolder.isRelevantForRefreshingByDocumentId())
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}

	@Override
	public void invalidateAll()
	{
		invalidate(DocumentIdsSelection.ALL);
	}

	@Override
	public void invalidate(final DocumentIdsSelection rowIds)
	{
		final ImmutableSet<InvoiceId> invoiceIds = rowsHolder
				.getRecordIdsToRefresh(rowIds, InvoiceRow::convertDocumentIdToInvoiceId);

		final List<InvoiceRow> newRows = repository.getInvoiceRowsListByInvoiceId(invoiceIds, evaluationDate);
		rowsHolder.compute(rows -> rows.replacingRows(rowIds, newRows));
	}

	public void addInvoice(@NonNull final InvoiceId invoiceId)
	{
		final InvoiceRow row = repository.getInvoiceRowByInvoiceId(invoiceId, evaluationDate).orElse(null);
		if (row == null)
		{
			throw new AdempiereException("@InvoiceNotOpen@");
		}

		rowsHolder.compute(rows -> rows.addingRow(row));
	}

	@Override
	public void patchRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final DocumentId rowIdToChange = ctx.getRowId();
		rowsHolder.compute(rows -> rows.changingRow(rowIdToChange, row -> InvoiceRowReducers.reduce(row, fieldChangeRequests)));
	}
}
