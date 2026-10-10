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
 * Flags the invoice candidates that might belong to a refund contract, so that the invoice candidate update run assigns them to the contract (or recomputes them).
 * The flagging is done after the current transaction is committed, so that the update run sees the committed contract and configs.
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

	/**
	 * For a contract that was just completed: flags the invoice candidates of its partner in the current open period.
	 */
	public void invalidateCandidatesOfContractAfterCommit(@NonNull final RefundContract refundContract)
	{
		final IQuery<I_C_Invoice_Candidate> query = refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContract, SystemTime.asLocalDate());

		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> flagInvoiceCandidates(query));
	}

	/**
	 * For a refund config whose amount was changed (e.g. its currency was corrected): flags the refund candidates of every completed contract with these conditions that has not ended,
	 * so that they are checked again (e.g. their currency error is gone), and the invoice candidates of each contract's partner in the current open period,
	 * so that those that are not assigned yet get assigned. A contract that cannot be loaded is skipped (and logged), so that it does not keep the others from being flagged.
	 * Only a change of the amount is handled like this, not e.g. a new or deactivated config line.
	 */
	public void invalidateCandidatesOfConditionsAfterCommit(@NonNull final ConditionsId conditionsId)
	{
		final LocalDate today = SystemTime.asLocalDate();
		final ImmutableList.Builder<FlatrateTermId> contractIds = ImmutableList.builder();
		final ImmutableList.Builder<IQuery<I_C_Invoice_Candidate>> queries = ImmutableList.builder();
		for (final FlatrateTermId contractId : refundContractRepository.getCompletedIdsByConditions(conditionsId, today))
		{
			try
			{
				queries.add(refundInvoiceCandidateRepository.createInvoiceCandidatesOfCurrentPeriodQuery(refundContractRepository.getById(contractId), today));
				contractIds.add(contractId);
			}
			catch (final RuntimeException e)
			{
				Loggables.withLogger(logger, Level.WARN).addLog("Skipping C_Flatrate_Term_ID={}, which cannot be loaded as refund contract; e={}", contractId.getRepoId(), e.toString());
			}
		}

		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> {
					contractIds.build().forEach(contractId -> invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, contractId)));
					queries.build().forEach(this::flagInvoiceCandidates);
				});
	}

	@VisibleForTesting
	void flagInvoiceCandidates(@NonNull final IQuery<I_C_Invoice_Candidate> query)
	{
		// the contract is committed now; the contracts that were cached before are stale
		refundContractRepository.resetCaches();
		invoiceCandDAO.invalidateCandsFor(query);
	}
}
