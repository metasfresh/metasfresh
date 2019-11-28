package de.metas.ui.web.payment_allocation;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.adempiere.util.lang.SynchronizedMutable;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_Invoice;

import com.google.common.collect.ImmutableSet;

import de.metas.invoice.InvoiceId;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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

public class InvoiceRows implements IRowsData<InvoiceRow>
{
	private final PaymentAndInvoiceRowsRepo repository;
	private final ZonedDateTime evaluationDate;
	private final SynchronizedMutable<ImmutableRowsIndex<InvoiceRow>> rowsHolder;

	@Builder
	private InvoiceRows(
			@NonNull PaymentAndInvoiceRowsRepo repository,
			@NonNull final List<InvoiceRow> initialRows,
			@NonNull final ZonedDateTime evaluationDate)
	{
		this.repository = repository;
		this.evaluationDate = evaluationDate;
		rowsHolder = SynchronizedMutable.of(new ImmutableRowsIndex<>(initialRows));
	}

	@Override
	public Map<DocumentId, InvoiceRow> getDocumentId2TopLevelRows()
	{
		final ImmutableRowsIndex<InvoiceRow> rows = rowsHolder.getValue();
		return rows.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		final ImmutableRowsIndex<InvoiceRow> rows = rowsHolder.getValue();
		return recordRefs.streamIds(I_C_Invoice.Table_Name, InvoiceId::ofRepoId)
				.map(InvoiceRow::convertInvoiceIdToDocumentId)
				.filter(rows::containsRowId)
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
		final ImmutableSet<InvoiceId> invoiceIds = rowsHolder.getValue().streamRows(rowIds)
				.map(InvoiceRow::getInvoiceId)
				.collect(ImmutableSet.toImmutableSet());

		final List<InvoiceRow> newRows = repository.getInvoiceRowsListByInvoiceId(invoiceIds, evaluationDate);
		rowsHolder.compute(rows -> rows.replacingRows(rowIds, newRows));
	}
}
