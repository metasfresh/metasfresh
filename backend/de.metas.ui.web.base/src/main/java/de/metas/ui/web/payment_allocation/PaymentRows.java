package de.metas.ui.web.payment_allocation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.payment.PaymentId;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.ImmutableRowsIndex;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_Payment;

import java.time.ZonedDateTime;
import java.util.List;

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

/**
 * @implNote Problem: the right table (invoices) is an includedView of the main table row (payments - left table).
 * <p>
 * If there are no payments, the invoices table is not shown even if we have invoices.
 * Solution (workaround): this dummy row is needed because we want to display the right table (invoices) at all times
 */
public class PaymentRows implements IRowsData<PaymentRow>
{
	public static PaymentRows cast(final IRowsData<PaymentRow> rows)
	{
		return (PaymentRows)rows;
	}

	private final PaymentAndInvoiceRowsRepo repository;
	private final ZonedDateTime evaluationDate;
	private final SynchronizedRowsIndexHolder<PaymentRow> rowsHolder;

	@Builder
	private PaymentRows(
			@NonNull final PaymentAndInvoiceRowsRepo repository,
			@NonNull final List<PaymentRow> initialRows,
			@NonNull final ZonedDateTime evaluationDate)
	{
		this.repository = repository;
		this.evaluationDate = evaluationDate;
		rowsHolder = SynchronizedRowsIndexHolder.of(ImmutableRowsIndex.of(initialRows)
				.removingRowId(PaymentRow.DEFAULT_PAYMENT_ROW.getId())
				.addingRowIfEmpty(PaymentRow.DEFAULT_PAYMENT_ROW));
	}

	@Override
	public ImmutableMap<DocumentId, PaymentRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return recordRefs.streamIds(I_C_Payment.Table_Name, PaymentId::ofRepoId)
				.map(PaymentRow::convertPaymentIdToDocumentId)
				.filter(rowsHolder.isRelevantForRefreshingByDocumentId())
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}

	@Override
	public void invalidateAll()
	{
		invalidate(DocumentIdsSelection.ALL);
		// nothing
	}

	@Override
	public void invalidate(final DocumentIdsSelection rowIds)
	{
		final ImmutableSet<PaymentId> paymentIds = rowsHolder
				.getRecordIdsToRefresh(rowIds, PaymentRow::convertDocumentIdToPaymentId);

		final List<PaymentRow> newRows = repository.getPaymentRowsListByPaymentId(paymentIds, evaluationDate);
		rowsHolder.compute(rows -> rows
				.replacingRows(rowIds, newRows)
				.removingRowId(PaymentRow.DEFAULT_PAYMENT_ROW.getId())
				.addingRowIfEmpty(PaymentRow.DEFAULT_PAYMENT_ROW));
	}

	public void addPayment(@NonNull final PaymentId paymentId)
	{
		final PaymentRow row = repository.getPaymentRowByPaymentId(paymentId, evaluationDate).orElse(null);
		if (row == null)
		{
			throw new AdempiereException("@PaymentNotOpen@");
		}

		rowsHolder.compute(rows -> rows
				.addingRow(row)
				.removingRowId(PaymentRow.DEFAULT_PAYMENT_ROW.getId())
				.addingRowIfEmpty(PaymentRow.DEFAULT_PAYMENT_ROW));
	}
}
