/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.i18n.Language;
import de.metas.invoice.InvoiceId;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class InvoiceDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final DocumentReportAdvisorUtil util;

	public InvoiceDocumentReportAdvisor(@NonNull final DocumentReportAdvisorUtil util)
	{
		this.util = util;
	}

	@Override
	public @NonNull String getHandledTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.INVOICE;
	}

	@Override
	public @NonNull DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId, final AdProcessId reportProcessIdToUse)
	{
		final InvoiceId invoiceId = recordRef.getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final I_C_BPartner bpartner = util.getBPartnerById(bpartnerId);

		final DocTypeId docTypeId = extractDocTypeId(invoice);
		final I_C_DocType docType = util.getDocTypeById(docTypeId);

		final ClientId clientId = ClientId.ofRepoId(invoice.getAD_Client_ID());

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> util.getBPartnerPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(bpartner.getInvoice_PrintFormat_ID()),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()),
				() -> util.getDefaultPrintFormats(clientId).getInvoicePrintFormatId());
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final Language language = util.getBPartnerLanguage(bpartner).orElse(null);

		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.adTableId(recordRef.getAdTableId())
				.bpartnerId(bpartnerId)
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, invoice.getC_BPartner_Location_ID()))
				.docTypeId(docTypeId)
				.onlyCopiesGreaterZero(true)
				.build();

		return DocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId))
				.reportProcessId(util.getReportProcessIdByPrintFormatId(printFormatId))
				.copies(util.getDocumentCopies(docType, bpPrintFormatQuery))
				.documentNo(invoice.getDocumentNo())
				.bpartnerId(bpartnerId)
				.docTypeId(docTypeId)
				.language(language)
				.build();
	}

	private DocTypeId extractDocTypeId(@NonNull final I_C_Invoice invoice)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(invoice.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeId;
		}

		final DocTypeId docTypeTargetId = DocTypeId.ofRepoIdOrNull(invoice.getC_DocTypeTarget_ID());
		if (docTypeTargetId != null)
		{
			return docTypeTargetId;
		}

		throw new AdempiereException("No document type set");
	}
}
