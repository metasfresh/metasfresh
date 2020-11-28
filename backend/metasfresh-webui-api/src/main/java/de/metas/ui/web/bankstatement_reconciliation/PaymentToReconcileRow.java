package de.metas.ui.web.bankstatement_reconciliation;

import java.time.LocalDate;
import java.util.Set;

import de.metas.currency.Amount;
import de.metas.payment.PaymentId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.Getter;
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

public class PaymentToReconcileRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.YesNo, widgetSize = WidgetSize.Small, captionKey = "IsReceipt")
	@Getter
	private final boolean inboundPayment;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "DocumentNo")
	@Getter
	private final String documentNo;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.LocalDate, widgetSize = WidgetSize.Small, captionKey = "DateTrx")
	private final LocalDate dateTrx;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "C_BPartner_ID")
	private final LookupValue bpartner;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "InvoiceDocumentNo")
	@Getter
	private final String invoiceDocumentNos;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "PayAmt")
	@Getter
	private final Amount payAmt;

	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "C_Currency_ID")
	private final String currencyCode;

	//
	@Getter
	private final PaymentId paymentId;
	private final DocumentId rowId;
	@Getter
	private final boolean reconciled;
	private final ViewRowFieldNameAndJsonValuesHolder<PaymentToReconcileRow> values;

	@Builder
	private PaymentToReconcileRow(
			@NonNull final PaymentId paymentId,
			final boolean inboundPayment,
			@NonNull final String documentNo,
			@NonNull final LocalDate dateTrx,
			@NonNull final LookupValue bpartner,
			final String invoiceDocumentNos,
			@NonNull final Amount payAmt,
			final boolean reconciled)
	{
		this.inboundPayment = inboundPayment;
		this.documentNo = documentNo;
		this.dateTrx = dateTrx;
		this.bpartner = bpartner;
		this.invoiceDocumentNos = invoiceDocumentNos;
		this.payAmt = payAmt;
		this.currencyCode = payAmt.getCurrencyCode().toThreeLetterCode();

		this.paymentId = paymentId;
		this.rowId = convertPaymentIdToDocumentId(paymentId);
		this.reconciled = reconciled;
		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(PaymentToReconcileRow.class);
	}

	public static PaymentToReconcileRow cast(final IViewRow row)
	{
		return (PaymentToReconcileRow)row;
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		return isReconciled();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	static DocumentId convertPaymentIdToDocumentId(@NonNull final PaymentId paymentId)
	{
		return DocumentId.of(paymentId);
	}

	static PaymentId convertDocumentIdToPaymentId(@NonNull final DocumentId rowId)
	{
		return rowId.toId(PaymentId::ofRepoId);
	}

	public Amount getPayAmtNegateIfOutbound()
	{
		return getPayAmt().negateIf(!inboundPayment);
	}
}
