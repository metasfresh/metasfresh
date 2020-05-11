package de.metas.ui.web.payment_allocation;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.i18n.ITranslatableString;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
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
	private final Amount serviceFeeAmt;

	@ViewColumn(seqNo = 90, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "C_Currency_ID")
	private final String currencyCode;

	//
	//
	//

	private final DocumentId rowId;
	@Getter
	private final InvoiceId invoiceId;
	private final ClientAndOrgId clientAndOrgId;
	@Getter
	private final InvoiceDocBaseType docBaseType;
	@Getter
	private final InvoiceProcessingFeeCalculation serviceFeeCalculation;

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
			@Nullable final InvoiceProcessingFeeCalculation serviceFeeCalculation)
	{
		this.docTypeName = docTypeName;
		this.documentNo = documentNo;
		this.dateInvoiced = dateInvoiced;
		this.bpartner = bpartner;
		this.docBaseType = docBaseType;

		this.grandTotal = grandTotal;
		this.openAmt = openAmt;
		this.discountAmt = discountAmt;
		this.serviceFeeAmt = serviceFeeCalculation != null ? serviceFeeCalculation.getFeeAmountIncludingTax() : null;
		this.currencyCode = Amount.getCommonCurrencyCodeOfAll(grandTotal, openAmt, discountAmt, this.serviceFeeAmt)
				.toThreeLetterCode();

		rowId = convertInvoiceIdToDocumentId(invoiceId);
		this.invoiceId = invoiceId;
		this.clientAndOrgId = clientAndOrgId;
		this.serviceFeeCalculation = serviceFeeCalculation;

		this.values = buildViewRowFieldNameAndJsonValuesHolder(serviceFeeCalculation);
	}

	private static ViewRowFieldNameAndJsonValuesHolder<InvoiceRow> buildViewRowFieldNameAndJsonValuesHolder(
			@Nullable final InvoiceProcessingFeeCalculation serviceFeeCalculation)
	{
		final ImmutableMap.Builder<String, ViewEditorRenderMode> viewEditorRenderModes = ImmutableMap.<String, ViewEditorRenderMode> builder()
				.put(FIELD_DiscountAmt, ViewEditorRenderMode.ALWAYS);

		if (serviceFeeCalculation != null)
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
