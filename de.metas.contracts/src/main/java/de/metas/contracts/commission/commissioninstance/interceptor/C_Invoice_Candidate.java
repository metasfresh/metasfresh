package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.commission.commissioninstance.services.SalesInvoiceCandidateService;
import de.metas.contracts.commission.commissioninstance.services.SettlementInvoiceCandidateService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

@Interceptor(I_C_Invoice_Candidate.class)
@Component
public class C_Invoice_Candidate
{
	private final SalesInvoiceCandidateService invoiceCandidateService;
	private final SettlementInvoiceCandidateService settlementInvoiceCandidateService;

	public C_Invoice_Candidate(
			@NonNull final SalesInvoiceCandidateService invoiceCandidateService,
			@NonNull final SettlementInvoiceCandidateService settlementInvoiceCandidateService)
	{
		this.invoiceCandidateService = invoiceCandidateService;
		this.settlementInvoiceCandidateService = settlementInvoiceCandidateService;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, // we aren't interested in "after-new", because prior to the first revalidation, the ICs isn't in a valid state anyways
			ifColumnsChanged = { /* keep in sync with the columns from InvoiceCandidateRecordHelper */
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtInvoiced,
					I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice,
					I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoiceInUOM,
					I_C_Invoice_Candidate.COLUMNNAME_QtyInvoicedInUOM,
					I_C_Invoice_Candidate.COLUMNNAME_PriceActual })
	public void createOrUpdateCommissionInstance(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		if (icRecord.getM_Product_ID() <= 0 /* ic can't belong to a commission contract */ )
		{
			return; // nothing to do
		}

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());

		if (CommissionConstants.COMMISSION_PRODUCT_ID.getRepoId() == icRecord.getM_Product_ID())
		{
			settlementInvoiceCandidateService.syncSettlementICToCommissionInstance(invoiceCandidateId, false/*candidateDeleted*/);
		}
		else
		{
			invoiceCandidateService.syncSalesICToCommissionInstance(invoiceCandidateId, false/*candidateDeleted*/);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteCommissionInstance(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());

		if (CommissionConstants.COMMISSION_PRODUCT_ID.getRepoId() == icRecord.getM_Product_ID())
		{
			settlementInvoiceCandidateService.syncSettlementICToCommissionInstance(invoiceCandidateId, true/*candidateDeleted*/);
		}
		else
		{
			invoiceCandidateService.syncSalesICToCommissionInstance(invoiceCandidateId, true/*candidateDeleted*/);
		}
	}
}
