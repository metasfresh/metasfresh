package de.metas.ui.web.bankstatement_reconciliation;

import java.util.List;
import java.util.Map;

import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_C_Payment;

import com.google.common.collect.ImmutableSet;

import de.metas.payment.PaymentId;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class PaymentToReconcileRows implements IRowsData<PaymentToReconcileRow>
{
	public static PaymentToReconcileRows cast(final IRowsData<PaymentToReconcileRow> rowsData)
	{
		return (PaymentToReconcileRows)rowsData;
	}

	private final BankStatementLineAndPaymentsToReconcileRepository repository;
	private final SynchronizedRowsIndexHolder<PaymentToReconcileRow> rowsHolder;

	@Builder
	private PaymentToReconcileRows(
			@NonNull final BankStatementLineAndPaymentsToReconcileRepository repository,
			@NonNull final List<PaymentToReconcileRow> rows)
	{
		this.repository = repository;
		rowsHolder = SynchronizedRowsIndexHolder.of(rows);
	}

	@Override
	public Map<DocumentId, PaymentToReconcileRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(TableRecordReferenceSet recordRefs)
	{
		return recordRefs.streamIds(I_C_Payment.Table_Name, PaymentId::ofRepoId)
				.map(PaymentToReconcileRow::convertPaymentIdToDocumentId)
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
		final ImmutableSet<PaymentId> paymentIds = rowsHolder
				.getRecordIdsToRefresh(rowIds, PaymentToReconcileRow::convertDocumentIdToPaymentId);

		final List<PaymentToReconcileRow> newRows = repository.getPaymentToReconcileRowsByIds(paymentIds);
		rowsHolder.compute(rows -> rows.replacingRows(rowIds, newRows));
	}
}
