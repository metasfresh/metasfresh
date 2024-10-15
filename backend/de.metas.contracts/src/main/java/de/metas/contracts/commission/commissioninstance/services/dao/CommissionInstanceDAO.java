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

package de.metas.contracts.commission.commissioninstance.services.dao;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceLineDocumentId;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CommissionInstanceDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * @return true if any of the {@code salesCandDocIds}'s {@link InvoiceCandidateId}s is referenced by a commission instance.
	 */
	public boolean isICsReferencedByCommissionInstances(@NonNull final Set<SalesInvoiceCandidateDocumentId> salesCandDocIds)
	{
		final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds = salesCandDocIds.stream()
				.map(SalesInvoiceCandidateDocumentId::getInvoiceCandidateId)
				.collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_C_Commission_Instance.class)
				.addInArrayFilter(I_C_Commission_Instance.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateIds)
				.create()
				.anyMatch();
	}

	/**
	 * @return true if any of the {@code salesCandDocIds}'s {@link InvoiceLineId}s is referenced by a commission instance.
	 */
	public boolean isILsReferencedByCommissionInstances(@NonNull final Set<SalesInvoiceLineDocumentId> salesInvoiceLineDocumentIds)
	{
		final ImmutableSet<InvoiceLineId> invoiceLineIds = salesInvoiceLineDocumentIds.stream()
				.map(SalesInvoiceLineDocumentId::getInvoiceLineId)
				.collect(ImmutableSet.toImmutableSet());

		return queryBL.createQueryBuilder(I_C_Commission_Instance.class)
				.addInArrayFilter(I_C_Commission_Instance.COLUMNNAME_C_InvoiceLine_ID, invoiceLineIds)
				.create()
				.anyMatch();
	}
}
