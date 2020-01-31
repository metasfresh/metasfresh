package de.metas.contracts.refund.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.IQuery;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
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

@Interceptor(I_C_Flatrate_Term.class)
@Component
public class C_Flatrate_Term
{
	private final RefundInvoiceCandidateRepository invoiceCandidateRepository;

	private C_Flatrate_Term(@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository)
	{
		this.invoiceCandidateRepository = refundInvoiceCandidateRepository;
	}

	/**
	 * Note: this method corresponds with the sysconfig setting {@code de.metas.contracts.C_Flatrate_Term.allow_reactivate_Refund = 'Y'}
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void deleteRefundInvoiceCandidates(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		if (isNoRefundTerm(flatrateTerm))
		{
			return; // this MI only deals with "refund" terms
		}
		Services.get(IInvoiceCandDAO.class).deleteAllReferencingInvoiceCandidates(flatrateTerm);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void invalidateMatchingInvoiceCandidatesAfterCommit(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		if (isNoRefundTerm(flatrateTerm))
		{
			return; // this MI only deals with "refund" terms
		}

		final IQuery<I_C_Invoice_Candidate> query = createInvoiceCandidatesToInvalidQuery(flatrateTerm);

		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> Services.get(IInvoiceCandDAO.class).invalidateCandsFor(query));
	}

	private IQuery<I_C_Invoice_Candidate> createInvoiceCandidatesToInvalidQuery(
			@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		final IQueryFilter<I_C_Invoice_Candidate> dateToInvoiceEffectiveFilter = invoiceCandidateRepository
				.createDateToInvoiceEffectiveFilter(
						flatrateTerm.getStartDate(),
						flatrateTerm.getEndDate());

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID, flatrateTerm.getM_Product_ID())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, flatrateTerm.getBill_BPartner_ID())
				.filter(dateToInvoiceEffectiveFilter)
				.create();
	}

	private boolean isNoRefundTerm(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		return !X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(flatrateTerm.getType_Conditions());
	}
}
