/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.impex.InputDataSourceId;
import de.metas.invoice.request.CreateManualInvoiceLineRequest;
import de.metas.invoice.request.CreateManualInvoiceRequest;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.function.Consumer;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
class ManualInvoiceRepository
{
	@NonNull
	public ManualInvoice save(@NonNull final CreateManualInvoiceRequest createManualInvoiceRequest)
	{
		final I_C_Invoice invoiceRecord = saveInvoiceHeader(createManualInvoiceRequest);

		final ImmutableList.Builder<ManualInvoiceLine> lines = ImmutableList.builder();

		for (final CreateManualInvoiceLineRequest createManualInvoiceLineRequest : createManualInvoiceRequest.getLines())
		{
			final ManualInvoiceLine manualInvoiceLine = saveLine(invoiceRecord, createManualInvoiceLineRequest);

			lines.add(manualInvoiceLine);
		}

		return ofRecord(invoiceRecord)
				.lines(lines.build())
				.build();
	}

	public void applyAndSave(
			@NonNull final InvoiceId invoiceId,
			@NonNull final Consumer<I_C_Invoice> updateInvoice)
	{
		final I_C_Invoice invoiceRecord = getRecordByIdNotNull(invoiceId);

		updateInvoice.accept(invoiceRecord);

		InterfaceWrapperHelper.save(invoiceRecord);
	}

	public void applyAndSave(
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@NonNull final Consumer<I_C_InvoiceLine> updateInvoiceLine)
	{
		final I_C_InvoiceLine invoiceLineRecord = getLineRecordByIdNotNull(invoiceAndLineId);

		updateInvoiceLine.accept(invoiceLineRecord);

		InterfaceWrapperHelper.save(invoiceLineRecord);
	}

	@NonNull
	public I_C_Invoice getRecordByIdNotNull(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoiceRecord = InterfaceWrapperHelper.load(invoiceId, I_C_Invoice.class);

		if (invoiceRecord == null)
		{
			throw new AdempiereException("No C_Invoice record found for id: " + invoiceId);
		}

		return invoiceRecord;
	}

	@NonNull
	private I_C_InvoiceLine getLineRecordByIdNotNull(final @NonNull InvoiceAndLineId invoiceAndLineId)
	{
		final I_C_InvoiceLine invoiceLineRecord = InterfaceWrapperHelper.load(invoiceAndLineId, I_C_InvoiceLine.class);

		if (invoiceLineRecord == null)
		{
			throw new AdempiereException("No C_InvoiceLine record found for id: " + invoiceAndLineId);
		}

		return invoiceLineRecord;
	}

	@NonNull
	private ManualInvoiceLine saveLine(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final CreateManualInvoiceLineRequest request)
	{
		final I_C_InvoiceLine invoiceLineRecord = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);

		invoiceLineRecord.setAD_Org_ID(invoiceRecord.getAD_Org_ID());
		invoiceLineRecord.setC_Invoice_ID(invoiceRecord.getC_Invoice_ID());
		invoiceLineRecord.setExternalIds(request.getExternalLineId());

		if (request.getLine() != null)
		{
			invoiceLineRecord.setLine(request.getLine());
		}

		final Quantity qtyToInvoice = request.getQtyToInvoice();

		invoiceLineRecord.setQtyInvoiced(qtyToInvoice.toBigDecimal());
		invoiceLineRecord.setQtyEntered(qtyToInvoice.toBigDecimal());
		invoiceLineRecord.setC_UOM_ID(qtyToInvoice.getUomId().getRepoId());

		invoiceLineRecord.setDescription(request.getLineDescription());
		invoiceLineRecord.setPriceEntered(request.getPriceEntered().toBigDecimal());
		invoiceLineRecord.setPriceActual(request.getPriceEntered().toBigDecimal());
		invoiceLineRecord.setPrice_UOM_ID(request.getPriceUomId().getRepoId());
		invoiceLineRecord.setIsManualPrice(request.getIsManualPrice());
		invoiceLineRecord.setM_Product_ID(request.getProductId().getRepoId());
		invoiceLineRecord.setC_Tax_ID(request.getTaxId().getRepoId());

		saveRecord(invoiceLineRecord);

		return ofRecord(invoiceLineRecord, invoiceRecord);
	}

	@NonNull
	private I_C_Invoice saveInvoiceHeader(@NonNull final CreateManualInvoiceRequest createManualInvoiceRequest)
	{
		final I_C_Invoice invoiceRecord = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);

		invoiceRecord.setAD_Org_ID(createManualInvoiceRequest.getOrgId().getRepoId());
		invoiceRecord.setC_BPartner_ID(createManualInvoiceRequest.getBillBPartnerId().getRepoId());
		invoiceRecord.setC_BPartner_Location_ID(createManualInvoiceRequest.getBillBPartnerLocationId().getRepoId());
		invoiceRecord.setAD_User_ID(BPartnerContactId.toRepoId(createManualInvoiceRequest.getBillContactId()));
		invoiceRecord.setDateInvoiced(TimeUtil.asTimestamp(createManualInvoiceRequest.getDateInvoiced()));
		invoiceRecord.setDateAcct(TimeUtil.asTimestamp(createManualInvoiceRequest.getDateAcct()));
		invoiceRecord.setDateOrdered(TimeUtil.asTimestamp(createManualInvoiceRequest.getDateOrdered()));
		invoiceRecord.setExternalId(createManualInvoiceRequest.getExternalHeaderId());
		invoiceRecord.setC_DocType_ID(createManualInvoiceRequest.getDocTypeId().getRepoId());
		invoiceRecord.setC_DocTypeTarget_ID(createManualInvoiceRequest.getDocTypeId().getRepoId());
		invoiceRecord.setPOReference(createManualInvoiceRequest.getPoReference());
		invoiceRecord.setIsSOTrx(createManualInvoiceRequest.getSoTrx().toBoolean());
		invoiceRecord.setC_Currency_ID(createManualInvoiceRequest.getCurrencyId().getRepoId());
		invoiceRecord.setM_PriceList_ID(createManualInvoiceRequest.getPriceListId().getRepoId());
		invoiceRecord.setAD_InputDataSource_ID(InputDataSourceId.toRepoId(createManualInvoiceRequest.getDataSourceId()));
		saveRecord(invoiceRecord);

		return invoiceRecord;
	}

	@NonNull
	private static ManualInvoiceLine ofRecord(
			@NonNull final I_C_InvoiceLine invoiceLineRecord,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		return ManualInvoiceLine.builder()
				.id(InvoiceAndLineId.ofRepoId(invoiceRecord.getC_Invoice_ID(), invoiceLineRecord.getC_InvoiceLine_ID()))
				.externalLineId(invoiceLineRecord.getExternalIds())
				.line(invoiceLineRecord.getLine())
				.lineDescription(invoiceLineRecord.getDescription())
				.priceEntered(invoiceLineRecord.getPriceEntered())
				.priceUomId(UomId.ofRepoIdOrNull(invoiceLineRecord.getPrice_UOM_ID()))
				.isActive(invoiceLineRecord.isActive())
				.isManualPrice(invoiceLineRecord.isManualPrice())
				.productId(ProductId.ofRepoIdOrNull(invoiceLineRecord.getM_Product_ID()))
				.qtyInvoiced(invoiceLineRecord.getQtyInvoiced())
				.uomId(UomId.ofRepoIdOrNull(invoiceLineRecord.getC_UOM_ID()))
				.taxId(TaxId.ofRepoId(invoiceLineRecord.getC_Tax_ID()))
				.build();
	}

	@NonNull
	private static ManualInvoice.ManualInvoiceBuilder ofRecord(@NonNull final I_C_Invoice invoiceRecord)
	{
		InterfaceWrapperHelper.refresh(invoiceRecord);

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID());

		return ManualInvoice.builder()
				.invoiceId(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()))
				.orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()))
				.docNumber(invoiceRecord.getDocumentNo())
				.billBPartnerLocationId(BPartnerLocationId.ofRepoId(bPartnerId, invoiceRecord.getC_BPartner_Location_ID()))
				.priceListId(PriceListId.ofRepoId(invoiceRecord.getM_PriceList_ID()))
				.billContactId(BPartnerContactId.ofRepoIdOrNull(bPartnerId, invoiceRecord.getAD_User_ID()))
				.dateInvoiced(TimeUtil.asInstantNonNull(invoiceRecord.getDateInvoiced()))
				.dateAcct(TimeUtil.asInstantNonNull(invoiceRecord.getDateAcct()))
				.dateOrdered(TimeUtil.asInstantNonNull(invoiceRecord.getDateAcct()))
				.externalHeaderId(invoiceRecord.getExternalId())
				.docTypeId(DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID()))
				.targetDocTypeId(DocTypeId.ofRepoId(invoiceRecord.getC_DocTypeTarget_ID()))
				.poReference(invoiceRecord.getPOReference())
				.soTrx(SOTrx.ofBoolean(invoiceRecord.isSOTrx()))
				.currencyId(CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID()))
				.grandTotal(invoiceRecord.getGrandTotal())
				.totalLines(invoiceRecord.getTotalLines());
	}
}
