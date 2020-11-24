package de.metas.rest_api.invoice.impl;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.invoicecandidates.response.JsonInvoiceCandidatesResponseItem;
import de.metas.rest_api.invoicecandidates.response.JsonReverseInvoiceResponse;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@Service
public class InvoiceService
{
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	public Optional<byte[]> getInvoicePDF(@NonNull final InvoiceId invoiceId)
	{
		final Optional<I_AD_Archive> lastArchive = getLastArchive(invoiceId);
		return lastArchive.isPresent() ? Optional.of(archiveBL.getBinaryData(lastArchive.get())) : Optional.empty();
	}

	public boolean hasArchive(@NonNull final InvoiceId invoiceId)
	{
		return getLastArchive(invoiceId).isPresent();
	}

	private Optional<I_AD_Archive> getLastArchive(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoiceRecord = invoiceDAO.getByIdInTrx(invoiceId);

		if (invoiceRecord == null)
		{
			return Optional.empty();
		}

		final List<I_AD_Archive> lastArchive = archiveDAO.retrieveLastArchives(Env.getCtx(), TableRecordReference.of(invoiceRecord), 1);
		if (Check.isEmpty(lastArchive))
		{
			return Optional.empty();
		}
		return Optional.of(lastArchive.get(0));
	}

	@NonNull
	public Optional<JsonReverseInvoiceResponse> reverseInvoice(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice documentRecord = Services.get(IInvoiceDAO.class).getByIdInTrx(invoiceId);
		if (documentRecord == null)
		{
			return Optional.empty();
		}

		documentBL.processEx(documentRecord, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

		final JsonReverseInvoiceResponse.JsonReverseInvoiceResponseBuilder responseBuilder = JsonReverseInvoiceResponse.builder();

		invoiceCandDAO
				.retrieveInvoiceCandidates(invoiceId)
				.stream()
				.map(this::buildJSONItem)
				.forEach(responseBuilder::affectedInvoiceCandidate);

		return Optional.of(responseBuilder.build());
	}

	private JsonInvoiceCandidatesResponseItem buildJSONItem(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return JsonInvoiceCandidatesResponseItem
				.builder()
				.externalHeaderId(JsonExternalId.ofOrNull(invoiceCandidate.getExternalHeaderId()))
				.externalLineId(JsonExternalId.ofOrNull(invoiceCandidate.getExternalLineId()))
				.metasfreshId(MetasfreshId.of(invoiceCandidate.getC_Invoice_Candidate_ID()))
				.build();
	}

}
