package de.metas.ui.web.payment_allocation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;

import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
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

public class InvoiceRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "DocumentNo")
	private final String documentNo;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.LocalDate, widgetSize = WidgetSize.Small, captionKey = "DateInvoiced")
	private final LocalDate dateInvoiced;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "C_BPartner_ID")
	private final LookupValue bpartner;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "GrandTotal")
	private final BigDecimal grandTotal;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "OpenAmt")
	private final BigDecimal openAmt;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "Discount")
	private final BigDecimal discountAmt;

	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "C_Currency_ID")
	private final String currencyCode;

	private final DocumentId rowId;
	private final ViewRowFieldNameAndJsonValuesHolder<InvoiceRow> values;

	@Builder
	private InvoiceRow(
			@NonNull final InvoiceId invoiceId,
			@NonNull final String documentNo,
			@NonNull final LocalDate dateInvoiced,
			@NonNull final LookupValue bpartner,
			@NonNull final Amount grandTotal,
			@NonNull final Amount openAmt,
			@NonNull final Amount discountAmt)
	{
		rowId = convertInvoiceIdToDocumentId(invoiceId);
		this.documentNo = documentNo;
		this.dateInvoiced = dateInvoiced;
		this.bpartner = bpartner;
		this.grandTotal = grandTotal.getAsBigDecimal();
		this.openAmt = openAmt.getAsBigDecimal();
		this.discountAmt = discountAmt.getAsBigDecimal();
		this.currencyCode = Amount.getCommonCurrencyCodeOfAll(grandTotal, openAmt, discountAmt)
				.toThreeLetterCode();

		values = ViewRowFieldNameAndJsonValuesHolder.newInstance(InvoiceRow.class);
	}

	private static DocumentId convertInvoiceIdToDocumentId(@NonNull final InvoiceId invoiceId)
	{
		return DocumentId.of(invoiceId);
	}

	public static DocumentId convertRecordRefToDocumentId(@NonNull final TableRecordReference recordRef)
	{
		final InvoiceId invoiceId = recordRef.getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		return convertInvoiceIdToDocumentId(invoiceId);
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
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
}
