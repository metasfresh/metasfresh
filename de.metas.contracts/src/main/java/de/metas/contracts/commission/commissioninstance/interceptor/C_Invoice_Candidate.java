package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.TableRecordMDC;
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
	private final C_Invoice_CandidateFacadeService invoiceCandidateFacadeService;

	public C_Invoice_Candidate(@NonNull final C_Invoice_CandidateFacadeService invoiceCandidateFacadeService)
	{
		this.invoiceCandidateFacadeService = invoiceCandidateFacadeService;
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
		try (final MDCCloseable icRecordMDC = TableRecordMDC.putTableRecordReference(icRecord))
		{
			invoiceCandidateFacadeService.syncICToCommissionInstance(icRecord, false/* candidateDeleted */);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteCommissionInstance(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		try (final MDCCloseable icRecordMDC = TableRecordMDC.putTableRecordReference(icRecord))
		{
			invoiceCandidateFacadeService.syncICToCommissionInstance(icRecord, true/* candidateDeleted */);
		}
	}

}
