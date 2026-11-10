package de.metas.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpContext;
import de.metas.payment.sumup.SumUpPollerCycleListener;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Background check of the till's card payments whose SumUp result has not reached them, run in every cycle of the SumUp
 * poller (after the poller refreshed the PENDING SumUp transactions):
 * <ul>
 * <li>(a) a card payment whose result is unknown and that names a SumUp transaction metasfresh does not have (SumUp accepted
 * the checkout, but saving its transaction failed) → the transaction is imported from SumUp
 * ({@link POSOrdersService#importCardPaymentSumUpTransaction}); its status event then resolves the payment. One SumUp lookup
 * per such payment per cycle, outside any transaction and without a lock held; while SumUp is unreachable the payment stays
 * unknown, and back office gets one AD_Issue per payment per outage;</li>
 * <li>(b) the orphan rule: a PENDING card payment whose checkout started more than
 * {@link POSOrdersService#CARD_CHECKOUT_ORPHAN_AGE} ago and stopped before SumUp's answer was stored → UNKNOWN
 * ({@link POSOrdersService#markCardPaymentUnknownIfOrphan}).</li>
 * </ul>
 * Each card payment is swept on its own, oldest first, in its own transactions, in its order's client/org context (the
 * poller thread has no login context), and with its own error capture: a failure is logged, recorded once as an AD_Issue of
 * the payment (see {@link #recordFailureOnce}), and the next payment is swept; the failed one is retried by the next cycle.
 *
 * @see de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementSweeper the twin for the card settlements of invoices
 */
@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class POSCardPaymentSweeper implements SumUpPollerCycleListener
{
	private static final Logger logger = LogManager.getLogger(POSCardPaymentSweeper.class);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@NonNull private final POSOrdersRepository ordersRepository;
	@NonNull private final POSOrdersService ordersService;

	/**
	 * Per card payment, the signatures of the sweep failures already recorded as AD_Issue, so that a failure that repeats
	 * in every cycle is recorded once; cleared when the payment is swept without an error.
	 */
	private final Map<POSOrderAndPaymentId, Set<String>> recordedFailureSignatures = new ConcurrentHashMap<>();

	@Override
	public void afterPollCycle()
	{
		// each payment runs its own transactions; an ambient one would join them, so one failure would roll back all
		trxManager.assertThreadInheritedTrxNotExists();

		final ImmutableList<POSOrderAndPaymentId> idsToImport;
		try (final Stream<POSOrderAndPaymentId> stream = ordersRepository.streamCardPaymentsToImport())
		{
			idsToImport = stream.collect(ImmutableList.toImmutableList());
		}
		final ImmutableList<POSOrderAndPaymentId> orphanIds;
		try (final Stream<POSOrderAndPaymentId> stream = ordersRepository.streamOrphanCardPaymentIds(SystemTime.asInstant().minus(POSOrdersService.CARD_CHECKOUT_ORPHAN_AGE)))
		{
			orphanIds = stream.collect(ImmutableList.toImmutableList());
		}

		// forget payments that are no longer swept
		recordedFailureSignatures.keySet().retainAll(ImmutableSet.<POSOrderAndPaymentId>builder().addAll(idsToImport).addAll(orphanIds).build());

		for (final POSOrderAndPaymentId id : idsToImport)
		{
			sweep(id, ordersService::importCardPaymentSumUpTransaction);
		}
		for (final POSOrderAndPaymentId id : orphanIds)
		{
			sweep(id, ordersService::markCardPaymentUnknownIfOrphan);
		}
	}

	private void sweep(@NonNull final POSOrderAndPaymentId id, @NonNull final Consumer<POSOrderAndPaymentId> sweeper)
	{
		final ClientAndOrgId clientAndOrgId;
		try
		{
			clientAndOrgId = ordersRepository.getById(id.getOrderId()).getClientAndOrgId();
		}
		catch (final Exception ex)
		{
			// the order's client is unknown, so the issue is written in the current context
			onSweepFailure(id, ex);
			return;
		}

		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(clientAndOrgId))
		{
			try
			{
				sweeper.accept(id);
				recordedFailureSignatures.remove(id);
			}
			catch (final Exception ex)
			{
				// still in the order's context, so that the AD_Issue belongs to its client / org
				onSweepFailure(id, ex);
			}
		}
	}

	/**
	 * Logs the failure and records it once as AD_Issue, so that the next payment is still swept. A failure whose cause
	 * chain is cyclic is replaced by a copy without causes (see {@link #toReportable}).
	 */
	private void onSweepFailure(@NonNull final POSOrderAndPaymentId id, @NonNull final Exception failure)
	{
		final Throwable rootCause = ExceptionUtils.getRootCause(failure); // cycle-safe: stops at the first repeated cause
		final Exception reportable = toReportable(failure, rootCause);

		logger.warn("Card payment sweep failed for {}; continuing with the next payment", id, reportable);
		recordFailureOnce(id, toSignature(rootCause), reportable);
	}

	/**
	 * @return the failure itself, or, if its cause chain is cyclic (the root cause still has a cause), a copy without
	 * causes: same text and stack trace, plus the root cause's text (the logger and the AD_Issue writer would not end on it)
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
	 * Records the failure as an AD_Issue of the card payment, unless the same failure (same signature, see
	 * {@link #toSignature}) was already recorded since the payment was last swept without an error.
	 */
	private void recordFailureOnce(
			@NonNull final POSOrderAndPaymentId id,
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
			errorManager.createIssue(AdempiereException.wrapIfNeeded(failure)
					.setRecord(TableRecordReference.of(I_C_POS_Payment.Table_Name, id.getPaymentId())));
		}
		catch (final Exception issueEx)
		{
			// e.g. the DB is down: the next cycle tries again
			signatures.remove(signature);
			logger.warn("Could not record the sweep failure of card payment {} as AD_Issue", id, issueEx);
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
