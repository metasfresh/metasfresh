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

package de.metas.ui.web.payment_allocation;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.i18n.ITranslatableString;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class InvoiceRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "C_DocType_ID")
	private final ITranslatableString docTypeName;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "DocumentNo")
	@Getter
	private final String documentNo;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.LocalDate, widgetSize = WidgetSize.Small, captionKey = "DateInvoiced")
	private final LocalDate dateInvoiced;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Lookup, widgetSize = WidgetSize.Small, captionKey = "C_BPartner_ID")
	private final LookupValue bpartner;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "GrandTotal")
	private final Amount grandTotal;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "OpenAmt")
	@Getter
	private final Amount openAmt;

	public static final String FIELD_DiscountAmt = "discountAmt";
	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "Discount", fieldName = FIELD_DiscountAmt)
	@Getter
	private final Amount discountAmt;

	public static final String FIELD_ServiceFeeAmt = "serviceFeeAmt";
	@ViewColumn(seqNo = 80, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "ServiceFeeAmt", fieldName = FIELD_ServiceFeeAmt)
	@Getter
	// @Nullable // if you uncomment this, the field will no longer be shown in the modal :^)
	private final Amount serviceFeeAmt;

	public static final String FIELD_BankFeeAmt = "bankFeeAmt";
	@ViewColumn(seqNo = 90, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "BankFeeAmt", fieldName = FIELD_BankFeeAmt)
	@Getter
	private final Amount bankFeeAmt;

	@ViewColumn(seqNo = 100, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "C_Currency_ID")
	private final String currencyCodeString;
	@Getter
	private final CurrencyCode currencyCode;

	//
	//
	//

	private final DocumentId rowId;
	@Getter
	private final InvoiceId invoiceId;
	private final ClientAndOrgId clientAndOrgId;
	@Getter
	private final InvoiceDocBaseType docBaseType;

	private final ViewRowFieldNameAndJsonValuesHolder<InvoiceRow> values;

	@Builder(toBuilder = true)
	private InvoiceRow(
			@NonNull final InvoiceId invoiceId,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final ITranslatableString docTypeName,
			@NonNull final String documentNo,
			@NonNull final LocalDate dateInvoiced,
			@NonNull final LookupValue bpartner,
			@NonNull final InvoiceDocBaseType docBaseType,
			@NonNull final Amount grandTotal,
			@NonNull final Amount openAmt,
			@NonNull final Amount discountAmt,
			@Nullable final Amount bankFeeAmt,
			@Nullable final Amount serviceFeeAmt)
	{
		this.docTypeName = docTypeName;
		this.documentNo = documentNo;
		this.dateInvoiced = dateInvoiced;
		this.bpartner = bpartner;
		this.docBaseType = docBaseType;

		this.grandTotal = grandTotal;
		this.openAmt = openAmt;
		this.discountAmt = discountAmt;
		this.serviceFeeAmt = serviceFeeAmt;
		this.bankFeeAmt = bankFeeAmt;
		//noinspection ConstantConditions
		this.currencyCode = Amount.getCommonCurrencyCodeOfAll(grandTotal, openAmt, discountAmt, this.serviceFeeAmt, this.bankFeeAmt);
		this.currencyCodeString = currencyCode.toThreeLetterCode();

		rowId = convertInvoiceIdToDocumentId(invoiceId);
		this.invoiceId = invoiceId;
		this.clientAndOrgId = clientAndOrgId;

		this.values = buildViewRowFieldNameAndJsonValuesHolder(docBaseType.getSoTrx());
	}

	private static ViewRowFieldNameAndJsonValuesHolder<InvoiceRow> buildViewRowFieldNameAndJsonValuesHolder(
			@NonNull final SOTrx soTrx)
	{
		final ImmutableMap.Builder<String, ViewEditorRenderMode> viewEditorRenderModes = ImmutableMap.<String, ViewEditorRenderMode>builder()
				.put(FIELD_DiscountAmt, ViewEditorRenderMode.ALWAYS)
				.put(FIELD_BankFeeAmt, ViewEditorRenderMode.ALWAYS);

		if (soTrx.isSales())
		{
			viewEditorRenderModes.put(FIELD_ServiceFeeAmt, ViewEditorRenderMode.ALWAYS);
		}

		return ViewRowFieldNameAndJsonValuesHolder.builder(InvoiceRow.class)
				.viewEditorRenderModeByFieldName(viewEditorRenderModes.build())
				.build();
	}

	static DocumentId convertInvoiceIdToDocumentId(@NonNull final InvoiceId invoiceId)
	{
		return DocumentId.of(invoiceId);
	}

	static InvoiceId convertDocumentIdToInvoiceId(@NonNull final DocumentId rowId)
	{
		return rowId.toId(InvoiceId::ofRepoId);
	}

	public static DocumentId convertRecordRefToDocumentId(@NonNull final TableRecordReference recordRef)
	{
		final InvoiceId invoiceId = recordRef.getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		return convertInvoiceIdToDocumentId(invoiceId);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(values.get(this))
				.toString();
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	public OrgId getOrgId()
	{
		return clientAndOrgId.getOrgId();
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

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return values.getViewEditorRenderModeByFieldName();
	}

	public BPartnerId getBPartnerId()
	{
		return bpartner.getIdAs(BPartnerId::ofRepoId);
	}
}
