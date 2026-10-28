package de.metas.contracts.refund.interceptor;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateInvalidator;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
	@NonNull private final RefundContractRepository refundContractRepository;
	@NonNull private final RefundInvoiceCandidateInvalidator refundInvoiceCandidateInvalidator;

	/* package */ C_Flatrate_Term(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final RefundInvoiceCandidateInvalidator refundInvoiceCandidateInvalidator)
	{
		this.refundContractRepository = refundContractRepository;
		this.refundInvoiceCandidateInvalidator = refundInvoiceCandidateInvalidator;
	}

	/**
	 * Note: this method corresponds with the sysconfig setting {@code de.metas.contracts.C_Flatrate_Term.allow_reactivate_Refund = 'Y'}
	 * <p>
	 * The reactivated term must also leave the refund contract caches, like a completed term enters them (see {@link RefundInvoiceCandidateInvalidator}).
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

		refundInvoiceCandidateInvalidator.invalidateCandidatesOfContractAfterCommit(refundContractRepository.ofRecord(flatrateTerm));
	}

	private boolean isNoRefundTerm(@NonNull final I_C_Flatrate_Term flatrateTerm)
	{
		return !X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(flatrateTerm.getType_Conditions());
	}
}
