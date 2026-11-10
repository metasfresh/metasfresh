package de.metas.pos.invoice_settlement.card;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpContext;
import de.metas.payment.sumup.SumUpPollerCycleListener;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Background check of the card settlements, run in every cycle of the SumUp poller (after the poller refreshed the
 * PENDING SumUp transactions): reconciles each sweep candidate ({@link POSInvoiceCardSettlementRepository#streamSweepCandidateIds})
 * with its SumUp transaction as stored locally ({@link POSInvoiceCardSettlementService#reconcileLocally}). So an
 * approval is booked even if its status event was lost or nobody reopens the till dialog, an interrupted booking is
 * completed, and an orphan is set UNKNOWN.
 * <p>
 * The sweep itself does not refresh transactions from SumUp, but a booking it runs asks SumUp once whether the charge
 * was refunded, right before step B allocates ({@link POSInvoiceCardSettlementBooking#completeBooking}): outside any
 * transaction and without a lock held, only while the terminal's cash journal is open (none while a settlement waits
 * for a journal). So a cycle makes at most one SumUp lookup per settlement whose step B runs in it: a newly approved one,
 * or an APPROVED one retried after a retryable step-B error. Retries after such an error end after
 * {@code MAX_BOOKING_ATTEMPTS} counted attempts; an attempt whose error cannot be recorded because the settlement row is
 * locked elsewhere is not counted, so while such a lock is held the lookup repeats in every cycle. While SumUp is
 * unreachable, an APPROVED settlement with an open journal waits with
 * {@link POSInvoiceCardSettlementBooking#ERRORMSG_SumUpNotReachable}, and its lookup repeats in every cycle (once per
 * cycle per settlement) for as long as the outage lasts: such attempts are not counted, so they are not bounded by
 * {@code MAX_BOOKING_ATTEMPTS} and an outage never ends as a booking error. Back office gets one AD_Issue per settlement
 * per outage (for the failed lookup that starts it); the repeated failures are only logged. Each lookup is bounded by
 * the SumUp client's timeouts, which therefore also delay the poller's next cycle while SumUp hangs.
 * <p>
 * Each settlement is reconciled on its own, in its own transactions, in the settlement's client/org context (the
 * poller thread has no login context), and with its own error capture: a failure is logged, recorded once as an
 * AD_Issue of the settlement (see {@link #recordFailureOnce}), and the next settlement is reconciled; the failed one is
 * retried by the next cycle. Booking errors themselves are handled (counted, AD_Issue) by the booking.
 */
@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class POSInvoiceCardSettlementSweeper implements SumUpPollerCycleListener
{
	private static final Logger logger = LogManager.getLogger(POSInvoiceCardSettlementSweeper.class);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final POSInvoiceCardSettlementService service;

	/**
	 * Per settlement, the signatures of the sweep failures already recorded as AD_Issue, so that a failure that repeats
	 * in every cycle is recorded once; cleared when the settlement is swept without an error.
	 */
	private final Map<POSInvoiceCardSettlementId, Set<String>> recordedFailureSignatures = new ConcurrentHashMap<>();

	@Override
	public void afterPollCycle()
	{
		// each settlement runs its own transactions; an ambient one would join them, so one failure would roll back all
		trxManager.assertThreadInheritedTrxNotExists();

		final ImmutableList<POSInvoiceCardSettlementId> ids;
		try (final Stream<POSInvoiceCardSettlementId> stream = repository.streamSweepCandidateIds())
		{
			ids = stream.collect(ImmutableList.toImmutableList());
		}

		// forget settlements that are no longer swept
		recordedFailureSignatures.keySet().retainAll(ids);

		for (final POSInvoiceCardSettlementId id : ids)
		{
			sweep(id);
		}
	}

	private void sweep(@NonNull final POSInvoiceCardSettlementId id)
	{
		final ClientAndOrgId clientAndOrgId;
		try
		{
			clientAndOrgId = repository.getById(id).getClientAndOrgId();
		}
		catch (final Exception ex)
		{
			// the settlement's client is unknown, so the issue is written in the current context
			onSweepFailure(id, ex);
			return;
		}

		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(clientAndOrgId))
		{
			try
			{
				service.reconcileLocally(id);
				recordedFailureSignatures.remove(id);
			}
			catch (final Exception ex)
			{
				// still in the settlement's context, so that the AD_Issue belongs to its client / org
				onSweepFailure(id, ex);
			}
		}
	}

	/**
	 * Logs the failure and records it once as AD_Issue, so that the next settlement is still swept.
	 * <p>
	 * A failure whose cause chain is cyclic is replaced by a copy without causes (see {@link #toReportable}): the logger
	 * would fail on it with a {@link StackOverflowError}, and the AD_Issue writer would loop forever.
	 */
	private void onSweepFailure(@NonNull final POSInvoiceCardSettlementId id, @NonNull final Exception failure)
	{
		final Throwable rootCause = ExceptionUtils.getRootCause(failure); // cycle-safe: stops at the first repeated cause
		final Exception reportable = toReportable(failure, rootCause);

		logger.warn("Card settlement sweep failed for {}; continuing with the next settlement", id, reportable);
		recordFailureOnce(id, toSignature(rootCause), reportable);
	}

	/**
	 * @return the failure itself, or, if its cause chain is cyclic (the root cause still has a cause), a copy without
	 * causes: same text and stack trace, plus the root cause's text
	 */
	@NonNull
	private static Exception toReportable(@NonNull final Exception failure, @NonNull final Throwable rootCause)
	{
		if (rootCause.getCause() == null)
		{
			return failure;
		}

		final AdempiereException copy = new AdempiereException(failure + " (cyclic cause chain, root cause: " + rootCause + ")");
		copy.setStackTrace(failure.getStackTrace());
		return copy;
	}

	/**
	 * Records the failure as an AD_Issue of the settlement, unless the same failure (same signature, see
	 * {@link #toSignature}) was already recorded since the settlement was last swept without an error.
	 */
	private void recordFailureOnce(
			@NonNull final POSInvoiceCardSettlementId id,
			@NonNull final String signature,
			@NonNull final Exception failure)
	{
		final Set<String> signatures = recordedFailureSignatures.computeIfAbsent(id, k -> ConcurrentHashMap.newKeySet());
		if (!signatures.add(signature))
		{
			return;
		}

		try
		{
			errorManager.createIssue(AdempiereException.wrapIfNeeded(failure).setRecord(id.toRecordRef()));
		}
		catch (final Exception issueEx)
		{
			// e.g. the DB is down: the next cycle tries again
			signatures.remove(signature);
			logger.warn("Could not record the sweep failure of card settlement {} as AD_Issue", id, issueEx);
		}
	}

	/**
	 * @return the root cause's class and the place it was thrown ({@code class#method:line}); not the message, which can
	 * vary (ids, timestamps) for the same failure
	 */
	@NonNull
	private static String toSignature(@NonNull final Throwable rootCause)
	{
		final StackTraceElement[] stackTrace = rootCause.getStackTrace();
		final String place = stackTrace.length > 0
				? stackTrace[0].getClassName() + "#" + stackTrace[0].getMethodName() + ":" + stackTrace[0].getLineNumber()
				: "?";
		return rootCause.getClass().getName() + "@" + place;
	}
}
