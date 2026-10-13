package de.metas.contracts.refund.interceptor;

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final RefundInvoiceCandidateRepository invoiceCandidateRepository;
	private final RefundContractRepository refundContractRepository;

	/* package */ C_Flatrate_Term(
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final RefundContractRepository refundContractRepository)
	{
		this.invoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.refundContractRepository = refundContractRepository;
	}

	/**
	 * Note: this method corresponds with the sysconfig setting {@code de.metas.contracts.C_Flatrate_Term.allow_reactivate_Refund = 'Y'}
	 * <p>
	 * The reactivated term must also leave the refund contract caches, see {@link #flagInvoiceCandidates(IQuery)}.
	 * (Voiding and closing are prohibited for all terms, see {@code de.metas.contracts.interceptor.C_Flatrate_Term#prohibitVoidingAndClosing}.)
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void deleteRefundInvoiceCandidates(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		if (isNoRefundTerm(flatrateTerm))
		{
			return; // this MI only deals with "refund" terms
		}
		invoiceCandDAO.deleteAllReferencingInvoiceCandidates(flatrateTerm);

		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> refundContractRepository.resetCaches());
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void invalidateMatchingInvoiceCandidatesAfterCommit(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		if (isNoRefundTerm(flatrateTerm))
		{
			return; // this MI only deals with "refund" terms
		}

		final IQuery<I_C_Invoice_Candidate> query = createInvoiceCandidatesToInvalidQuery(flatrateTerm, SystemTime.asLocalDate());

		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> flagInvoiceCandidates(query));
	}

	@VisibleForTesting
	/* package */ void flagInvoiceCandidates(@NonNull final IQuery<I_C_Invoice_Candidate> query)
	{
		// the term is committed now; the contracts that were cached before are stale
		refundContractRepository.resetCaches();
		invoiceCandDAO.invalidateCandsFor(query);
	}

	/**
	 * The invoice candidates (also the already invoiced ones) that might belong to the term: those invoiced to the term's partner,
	 * from the start of the current open period (but not before the term's start) to the term's end. They are only flagged here; the invoice candidate update run decides which of them really match, and assigns them.
	 */
	@VisibleForTesting
	/* package */ IQuery<I_C_Invoice_Candidate> createInvoiceCandidatesToInvalidQuery(
			@NonNull final I_C_Flatrate_Term flatrateTerm,
			@NonNull final LocalDate today)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// only the current open period is picked up retroactively; the periods before it get no refund
		final RefundContract refundContract = refundContractRepository.ofRecord(flatrateTerm);
		final LocalDate firstDayToFlag = refundContract.computeCurrentPeriodStart(today);

		final IQueryFilter<I_C_Invoice_Candidate> dateToInvoiceEffectiveFilter = invoiceCandidateRepository
				.createDateToInvoiceEffectiveFilter(
						TimeUtil.asTimestamp(firstDayToFlag),
						flatrateTerm.getEndDate());

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				// not the refund candidates themselves
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class))
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, flatrateTerm.getBill_BPartner_ID())
				.filter(dateToInvoiceEffectiveFilter)
				.create();
	}

	private boolean isNoRefundTerm(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		return !X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(flatrateTerm.getType_Conditions());
	}
}
