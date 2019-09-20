package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.contracts.commission.commissioninstance.services.InvoiceCandidateService;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
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
	private final InvoiceCandidateService invoiceCandidateService;
	private CommissionInstanceRepository commissionInstanceRepository;

	public C_Invoice_Candidate(
			@NonNull final InvoiceCandidateService invoiceCandidateService,
			@NonNull final CommissionInstanceRepository commissionInstanceRepository)
	{
		this.invoiceCandidateService = invoiceCandidateService;
		this.commissionInstanceRepository = commissionInstanceRepository;
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
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> invoiceCandidateService.createOrUpdateCommissionInstance(invoiceCandidateId));
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteCommissionInstance(I_C_Invoice_Candidate icRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID());
		commissionInstanceRepository.deleteCommissionRecordsFor(invoiceCandidateId);
	}
}
