package de.metas.rest_api.invoice.impl;

import java.util.List;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import lombok.NonNull;

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
public class InvoicePDFService
{
	final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	public byte[] getInvoicePDF(@NonNull final InvoiceId invoiceId)
	{
		I_C_Invoice invoiceRecord = invoiceDAO.getByIdInTrx(invoiceId);

		List<I_AD_Archive> lastArchive = archiveDAO.retrieveLastArchives(Env.getCtx(), TableRecordReference.of(invoiceRecord), 1);

		return lastArchive.isEmpty()? null : archiveBL.getBinaryData(lastArchive.get(0));
	}

}
