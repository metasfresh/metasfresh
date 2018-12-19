package de.metas.contracts.invoice;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoice.InvoiceId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

@Service
public class ContractInvoiceService
{
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public boolean isContractSalesInvoice(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		if (!invoice.isSOTrx())
		{
			// only sales invoices allowed
			return false;
		}

		final List<I_C_InvoiceLine> invoiceLines = invoiceDAO.retrieveLines(invoice);

		for (final I_C_InvoiceLine invoiceLine : invoiceLines)
		{
			final boolean isContractSalesInvoiceLine = invoiceCandDAO.retrieveIcForIl(invoiceLine)
					.stream()
					.anyMatch(invoiceCandidate -> isSubscriptionInvoiceCandidate(invoiceCandidate));

			if (isContractSalesInvoiceLine)
			{
				return true;
			}
		}

		return false;
	}

	public InvoiceId retrievePredecessorSalesContractInvoiceId(final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);

		final Optional<InvoiceId> predecessorInvoice = queryBL.createQueryBuilder(I_C_Invoice.class)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_BPartner_ID, invoice.getC_BPartner_ID())
				.addNotEqualsFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID())
				.orderByDescending(I_C_Invoice.COLUMNNAME_DateInvoiced)
				.create()
				.listIds(InvoiceId::ofRepoId)
				.stream()
				.filter(olderInvoiceId -> isContractSalesInvoice(olderInvoiceId))
				.findFirst();

		return predecessorInvoice.isPresent() ? predecessorInvoice.get() : null;
	}

	public InvoiceId retrieveLastSalesContractInvoiceId(final BPartnerId bPartnerId)
	{
		final Optional<InvoiceId> predecessorInvoice = queryBL.createQueryBuilder(I_C_Invoice.class)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_BPartner_ID, bPartnerId.getRepoId())
				.orderByDescending(I_C_Invoice.COLUMNNAME_DateInvoiced)
				.create()
				.listIds(InvoiceId::ofRepoId)
				.stream()
				.filter(olderInvoiceId -> isContractSalesInvoice(olderInvoiceId))
				.findFirst();

		return predecessorInvoice.isPresent() ? predecessorInvoice.get() : null;
	}

	private boolean isSubscriptionInvoiceCandidate(final I_C_Invoice_Candidate invoiceCandidate)
	{
		final int tableId = invoiceCandidate.getAD_Table_ID();

		return getTableId(I_C_Flatrate_Term.class) == tableId;
	}
}
