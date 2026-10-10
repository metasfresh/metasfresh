package de.metas.contracts.refund;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Flags the invoice candidates of refund contracts for the update run, which then assigns them (or recomputes them).
 */
@Service
@RequiredArgsConstructor
public class RefundInvoiceCandidateInvalidator
{
	private static final Logger logger = LogManager.getLogger(RefundInvoiceCandidateInvalidator.class);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	@NonNull private final RefundContractRepository refundContractRepository;

	/** For a contract that was just completed: flags the sales of its partner in the current open period, after the commit. */
	public void invalidateCandidatesOfContractAfterCommit(@NonNull final RefundContract refundContract)
	{
		final IQuery<I_C_Invoice_Candidate> query = refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContract, SystemTime.asLocalDate());

		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> flagInvoiceCandidates(query));
	}

	/**
	 * After the commit, flags the open refund candidates of every completed contract with these conditions, and the sales of each contract's current period.
	 * Each conditions is handled once per transaction.
	 */
	public void invalidateCandidatesOfConditionsAfterCommit(@NonNull final ConditionsId conditionsId)
	{
		trxManager.accumulateAndProcessAfterCommit(
				RefundInvoiceCandidateInvalidator.class.getName() + "#conditionsIds",
				ImmutableList.of(conditionsId),
				conditionsIds -> conditionsIds.stream().distinct().forEach(this::flagCandidatesOfConditions));
	}

	private void flagCandidatesOfConditions(@NonNull final ConditionsId conditionsId)
	{
		// the changed configs are committed now; the contracts that were cached before are stale
		refundContractRepository.resetCaches();

		final LocalDate today = SystemTime.asLocalDate();
		for (final FlatrateTermId contractId : refundContractRepository.getCompletedIdsByConditions(conditionsId))
		{
			invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, contractId));

			final RefundContract refundContract = loadOrNull(contractId);
			if (refundContract != null && !refundContract.getEndDate().isBefore(today)) // an ended contract has no current period
			{
				invoiceCandDAO.invalidateCandsFor(refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContract, today));
			}
		}
	}

	@Nullable
	private RefundContract loadOrNull(@NonNull final FlatrateTermId contractId)
	{
		try
		{
			return refundContractRepository.getById(contractId);
		}
		catch (final RuntimeException e)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Skipping the sales of C_Flatrate_Term_ID={}, which cannot be loaded as refund contract; e={}", contractId.getRepoId(), e.toString());
			return null;
		}
	}

	@VisibleForTesting
	void flagInvoiceCandidates(@NonNull final IQuery<I_C_Invoice_Candidate> query)
	{
		// the contract is committed now; the contracts that were cached before are stale
		refundContractRepository.resetCaches();
		invoiceCandDAO.invalidateCandsFor(query);
	}
}
