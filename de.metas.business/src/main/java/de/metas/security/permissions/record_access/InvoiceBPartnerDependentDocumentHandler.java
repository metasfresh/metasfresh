package de.metas.security.permissions.record_access;

import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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

@Component
class InvoiceBPartnerDependentDocumentHandler implements BPartnerDependentDocumentHandler
{

	@Override
	public String getDocumentTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public Optional<BPartnerId> extractBPartnerIdFromDependentDocument(final TableRecordReference documentRef)
	{
		final IInvoiceDAO invoicesRepo = Services.get(IInvoiceDAO.class);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(documentRef.getRecord_ID());
		final I_C_Invoice invoice = invoicesRepo.getByIdInTrx(invoiceId);
		return BPartnerId.optionalOfRepoId(invoice.getC_BPartner_ID());
	}

	@Override
	public Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(BPartnerId bpartnerId)
	{
		final IInvoiceDAO invoicesRepo = Services.get(IInvoiceDAO.class);

		return invoicesRepo.streamInvoiceIdsByBPartnerId(bpartnerId)
				.map(invoiceId -> toTableRecordReference(invoiceId));
	}

	private static final TableRecordReference toTableRecordReference(final InvoiceId invoiceId)
	{
		return TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId.getRepoId());
	}
}
