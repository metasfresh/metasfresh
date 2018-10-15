package de.metas.invoice.export;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import lombok.NonNull;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.invoice_gateway.spi.model.BPartner;
import de.metas.invoice_gateway.spi.model.EAN;
import de.metas.invoice_gateway.spi.model.Invoice;
import de.metas.invoice_gateway.spi.model.InvoiceAttachment;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.invoice_gateway.spi.model.InvoiceLine;
import de.metas.invoice_gateway.spi.model.MetasfreshVersion;
import de.metas.invoice_gateway.spi.model.Money;
import de.metas.invoice_gateway.spi.model.ProductId;
import de.metas.lang.SoftwareVersion;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class ExportInvoiceRepository
{
	private final AttachmentEntryService attachmentEntryservice;

	public ExportInvoiceRepository(@NonNull final AttachmentEntryService attachmentEntryservice)
	{
		this.attachmentEntryservice = attachmentEntryservice;

	}

	public Invoice getById(@NonNull final InvoiceId id)
	{
		final I_C_Invoice invoiceRecord = load(id, I_C_Invoice.class);
		final String currentyStr = invoiceRecord.getC_Currency().getISO_Code();
		final Money grandTotal = Money.of(invoiceRecord.getGrandTotal(), currentyStr);

		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
		final BigDecimal allocatedAmt = allocationDAO.retrieveAllocatedAmt(invoiceRecord);
		final Money allocatedMoney = Money.of(allocatedAmt, currentyStr);

		ImmutableList.Builder<InvoiceLine> invoiceLines = ImmutableList.builder();
		final List<I_C_InvoiceLine> invoiceLineRecords = Services.get(IInvoiceDAO.class).retrieveLines(invoiceRecord);
		for (final I_C_InvoiceLine lineRecord : invoiceLineRecords)
		{
			final InvoiceLine invoiceLine = InvoiceLine
					.builder()
					.lineAmount(Money.of(lineRecord.getLineNetAmt(),currentyStr))
					.productId(ProductId.ofId(lineRecord.getM_Product_ID()))
					//.externalId(lineRecord.gete)
					.build();
			invoiceLines.add(invoiceLine);
		}

		return Invoice
				.builder()
				.amount(grandTotal)
				.alreadyPaidAmount(allocatedMoney)
				.biller(createBiller(invoiceRecord))
				.documentNumber(invoiceRecord.getDocumentNo())
				// .customInvoicePayloads(customInvoicePayloads)
				.metasfreshVersion(createMetasfreshVersion())
				.invoiceAttachments(createInvoiceAttachments(invoiceRecord))
				.invoiceDate(createInvoiceDate(invoiceRecord))
				.invoiceLines(invoiceLines.build())
				.build();
	}

	private GregorianCalendar createInvoiceDate(final I_C_Invoice invoiceRecord)
	{
		final GregorianCalendar invoiceDate = new GregorianCalendar();
		invoiceDate.setTime(invoiceRecord.getDateInvoiced());
		return invoiceDate;
	}

	private ImmutableList<InvoiceAttachment> createInvoiceAttachments(final I_C_Invoice invoiceRecord)
	{
		final List<AttachmentEntry> attachments = attachmentEntryservice.getByReferencedRecord(invoiceRecord);
		final ImmutableList.Builder<InvoiceAttachment> invoiceAttachments = ImmutableList.builder();
		for (final AttachmentEntry attachment : attachments)
		{
			if (!attachment.hasLabel("ForInvoiceExport"))
			{
				continue;
			}
			final InvoiceAttachment invoiceAttachment = InvoiceAttachment.builder()
					.fileName(attachment.getFilename())
					.data(new ByteArrayInputStream(attachmentEntryservice.retrieveData(attachment.getId())))
					.typeName(attachment.getLabelValue("InvoiceAttachmentType"))
					.build();
			invoiceAttachments.add(invoiceAttachment);
		}
		return invoiceAttachments.build();

	}

	private BPartner createBiller(final I_C_Invoice invoiceRecord)
	{
		final String gln = invoiceRecord.getC_BPartner_Location().getGLN();

		final String vatTaxId = invoiceRecord.getC_BPartner().getVATaxID();

		final BPartner biller = BPartner.builder()
				.ean(EAN.of(gln))
				.vatNumber(vatTaxId)
				.build();
		return biller;
	}

	private MetasfreshVersion createMetasfreshVersion()
	{
		final SoftwareVersion metasfreshBuildVersion = Adempiere.getBuildVersion();
		final MetasfreshVersion metasfreshVersion = MetasfreshVersion.builder()
				.major(metasfreshBuildVersion.getMajor())
				.minor(metasfreshBuildVersion.getMinor())
				.fullVersion(Adempiere.getBuildAndDateVersion())
				.build();
		return metasfreshVersion;
	}
}
