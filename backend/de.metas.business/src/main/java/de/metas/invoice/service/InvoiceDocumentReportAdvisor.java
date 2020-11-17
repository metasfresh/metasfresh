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
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.Language;
import de.metas.invoice.InvoiceId;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportInfo;
import de.metas.report.StandardDocumentReportType;
import de.metas.util.Check;
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

import static de.metas.report.DocumentReportAdvisorUtil.getDocumentCopies;
import static de.metas.report.DocumentReportAdvisorUtil.getReportProcessIdByPrintFormatId;

@Component
public class InvoiceDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IBPartnerBL bpartnerBL;
	private final DefaultPrintFormatsRepository defaultPrintFormatsRepository;

	public InvoiceDocumentReportAdvisor(
			@NonNull final IBPartnerBL bpartnerBL,
			@NonNull final DefaultPrintFormatsRepository defaultPrintFormatsRepository)
	{
		this.bpartnerBL = bpartnerBL;
		this.defaultPrintFormatsRepository = defaultPrintFormatsRepository;
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
	public @NonNull StandardDocumentReportInfo getDocumentReportInfo(
			final @NonNull StandardDocumentReportType type,
			final int recordId,
			@Nullable final PrintFormatId adPrintFormatToUseId)
	{
		Check.assumeEquals(type, StandardDocumentReportType.INVOICE, "type");

		final InvoiceId invoiceId = InvoiceId.ofRepoId(recordId);
		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final I_C_BPartner bpartner = bpartnerBL.getById(bpartnerId);

		final DocTypeId docTypeId = extractDocTypeId(invoice);
		final I_C_DocType docType = docTypeDAO.getById(docTypeId);

		final ClientId clientId = ClientId.ofRepoId(invoice.getAD_Client_ID());

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> bpartnerBL.getPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(bpartner.getInvoice_PrintFormat_ID()),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()),
				() -> defaultPrintFormatsRepository.getByClientId(clientId).getInvoicePrintFormatId());
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final int documentCopies = getDocumentCopies(bpartner, docType);

		final String adLanguage = bpartner.getAD_Language();
		final Language language = Check.isNotBlank(adLanguage) ? Language.getLanguage(adLanguage) : null;

		return StandardDocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId))
				.printFormatId(printFormatId)
				.reportProcessId(getReportProcessIdByPrintFormatId(printFormatId))
				.copies(documentCopies)
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
