/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.services;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.ICommissionTriggerService;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceLineDocumentId;
import de.metas.contracts.commission.commissioninstance.services.dao.CommissionInstanceDAO;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CommissionTriggerService implements ICommissionTriggerService
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final CommissionInstanceDAO commissionInstanceDAO;

	public CommissionTriggerService(final CommissionInstanceDAO commissionInstanceDAO)
	{
		this.commissionInstanceDAO = commissionInstanceDAO;
	}

	@Override
	public boolean isContainsCommissionTriggers(@NonNull final InvoiceId invoiceId)
	{
		final Set<SalesInvoiceLineDocumentId> invoiceLineIds = invoiceDAO.retrieveLines(invoiceId)
				.stream()
				.map(I_C_InvoiceLine::getC_InvoiceLine_ID)
				.map(invoiceLineId -> InvoiceAndLineId.ofRepoId(invoiceId, invoiceLineId))
				.map(SalesInvoiceLineDocumentId::new)
				.collect(ImmutableSet.toImmutableSet());

		final boolean linesSubjectToCommission = commissionInstanceDAO.isILsReferencedByCommissionInstances(invoiceLineIds);
		if (linesSubjectToCommission)
		{
			return true;
		}

		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveInvoiceCandidates(invoiceId);
		if (invoiceCandidates.isEmpty())
		{
			return false;
		}

		final Set<SalesInvoiceCandidateDocumentId> invoiceCandIdSet = invoiceCandidates.stream()
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId)
				.map(SalesInvoiceCandidateDocumentId::new)
				.collect(ImmutableSet.toImmutableSet());

		return commissionInstanceDAO.isICsReferencedByCommissionInstances(invoiceCandIdSet);
	}
}
