package de.metas.rest_api.invoice.impl;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.springframework.http.ResponseEntity;
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
	final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

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

	public ResponseEntity<Object> reverseInvoice(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice documentRecord = Services.get(IInvoiceDAO.class).getByIdInTrx(invoiceId);
		if (documentRecord == null)
		{
			return ResponseEntity.notFound().build();
		}

		try
		{
			Services.get(IDocumentBL.class).processEx(documentRecord, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
		}
		catch (final Exception e)
		{
			return ResponseEntity.unprocessableEntity().body(e.getLocalizedMessage());
		}

		return ResponseEntity.ok().build();
	}

}
