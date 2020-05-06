package de.metas.security.permissions.bpartner_hierarchy.handlers.impl;

import java.util.stream.Stream;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocument;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.user.UserId;
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
@VisibleForTesting
public class InvoiceBPartnerDependentDocumentHandler implements BPartnerDependentDocumentHandler
{

	@Override
	public String getDocumentTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	private IInvoiceDAO getInvoicesRepo()
	{
		final IInvoiceDAO invoicesRepo = Services.get(IInvoiceDAO.class);
		return invoicesRepo;
	}

	@Override
	public BPartnerDependentDocument extractBPartnerDependentDocumentFromDocumentObj(final Object documentObj)
	{
		final I_C_Invoice invoiceRecord = InterfaceWrapperHelper.create(documentObj, I_C_Invoice.class);
		final I_C_Invoice invoiceRecordOld = InterfaceWrapperHelper.createOld(documentObj, I_C_Invoice.class);

		return BPartnerDependentDocument.builder()
				.documentRef(TableRecordReference.of(documentObj))
				.newBPartnerId(BPartnerId.ofRepoIdOrNull(invoiceRecord.getC_BPartner_ID()))
				.oldBPartnerId(BPartnerId.ofRepoIdOrNull(invoiceRecordOld.getC_BPartner_ID()))
				.updatedBy(UserId.ofRepoIdOrSystem(invoiceRecord.getUpdatedBy()))
				.build();
	}

	@Override
	public Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(final BPartnerId bpartnerId)
	{
		final IInvoiceDAO invoicesRepo = getInvoicesRepo();

		return invoicesRepo.streamInvoiceIdsByBPartnerId(bpartnerId)
				.map(invoiceId -> toTableRecordReference(invoiceId));
	}

	private static final TableRecordReference toTableRecordReference(final InvoiceId invoiceId)
	{
		return TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId);
	}
}
