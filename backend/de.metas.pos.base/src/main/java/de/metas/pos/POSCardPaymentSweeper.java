package de.metas.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.common.util.time.SystemTime;
import de.metas.payment.sumup.SumUpContext;
import de.metas.payment.sumup.SumUpPollSchedule;
import de.metas.payment.sumup.SumUpPollerCycleListener;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Background check of the till's card payments whose SumUp result has not reached them, run in every cycle of the SumUp
 * poller (after the poller refreshed the PENDING SumUp transactions):
 * <ul>
 * <li>(a) a card payment whose result is unknown and that names a SumUp transaction metasfresh does not have (SumUp accepted
 * the checkout, but saving its transaction failed) → the transaction is imported from SumUp
 * ({@link POSOrdersService#importCardPaymentSumUpTransaction}); its status event then resolves the payment. SumUp is asked
 * for such a payment when it is due by {@link SumUpPollSchedule}, counted from its checkout start (every cycle in its first
 * 5 min, then less often as it ages), outside any transaction and without a lock held; while SumUp is unreachable the
 * payment stays unknown, and back office gets one AD_Issue per payment per outage;</li>
 * <li>(b) the orphan rule: a PENDING card payment whose checkout started more than
 * 150 s ago ({@link POSOrdersService#getOrphanCardPaymentIds}) and stopped before SumUp's answer was stored → UNKNOWN
 * ({@link POSOrdersService#markCardPaymentUnknownIfOrphan});</li>
 * <li>(c) a PENDING card payment naming a SumUp transaction that metasfresh stores as final (its status event did not resolve
 * the payment: lost, or arrived before the payment named the transaction) → the stored result is applied as its status event
 * does ({@link POSOrdersService#reconcileCardPaymentWithItsSumUpTransaction}). No SumUp lookup.</li>
 * </ul>
 * Each card payment is swept on its own, oldest first, in its own transactions, in its order's client/org context (the
 * poller thread has no login context), and with its own error capture: a failure is logged, recorded once as an AD_Issue of
 * the payment (see {@link SweepFailureRecorder}), and the next payment is swept; the failed one is retried by the next cycle.
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

	@NonNull private final POSOrdersService ordersService;

	/**
	 * Records each sweep failure once as AD_Issue of its card payment, for as long as the payment keeps failing.
	 */
	private final SweepFailureRecorder<POSOrderAndPaymentId> failureRecorder = new SweepFailureRecorder<>(logger, "card payment");

	/**
	 * When case (a) last asked SumUp for each payment to import, for {@link SumUpPollSchedule}. Kept in memory only: after a
	 * restart each payment is asked for at once, and then on the schedule again.
	 */
	private final Map<POSOrderAndPaymentId, Instant> lastImportLookupAt = new ConcurrentHashMap<>();

	@Override
	public void afterPollCycle()
	{
		// each payment runs its own transactions; an ambient one would join them, so one failure would roll back all
		trxManager.assertThreadInheritedTrxNotExists();

		final Instant now = SystemTime.asInstant();
		final ImmutableList<POSCardPaymentToImport> paymentsToImport = ordersService.getCardPaymentsToImport();
		final ImmutableSet<POSOrderAndPaymentId> idsToImport = paymentsToImport.stream()
				.map(POSCardPaymentToImport::getId)
				.collect(ImmutableSet.toImmutableSet());
		final ImmutableList<POSOrderAndPaymentId> orphanIds = ordersService.getOrphanCardPaymentIds();
		final ImmutableList<POSOrderAndPaymentId> idsToReconcile = ordersService.getCardPaymentIdsToReconcile();

		// forget payments that are no longer swept
		failureRecorder.retainOnly(ImmutableSet.<POSOrderAndPaymentId>builder().addAll(idsToImport).addAll(orphanIds).addAll(idsToReconcile).build());
		lastImportLookupAt.keySet().retainAll(idsToImport);

		for (final POSCardPaymentToImport toImport : paymentsToImport)
		{
			final POSOrderAndPaymentId id = toImport.getId();
			if (!SumUpPollSchedule.isDue(toImport.getCheckoutStartedAt(), lastImportLookupAt.get(id), now))
			{
				continue;
			}
			lastImportLookupAt.put(id, now);
			sweep(id, ordersService::importCardPaymentSumUpTransaction);
		}
		for (final POSOrderAndPaymentId id : orphanIds)
		{
			sweep(id, ordersService::markCardPaymentUnknownIfOrphan);
		}
		for (final POSOrderAndPaymentId id : idsToReconcile)
		{
			sweep(id, ordersService::reconcileCardPaymentWithItsSumUpTransaction);
		}
	}

	private void sweep(@NonNull final POSOrderAndPaymentId id, @NonNull final Consumer<POSOrderAndPaymentId> sweeper)
	{
		final ClientAndOrgId clientAndOrgId;
		try
		{
			clientAndOrgId = ordersService.getClientAndOrgId(id.getOrderId());
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
				failureRecorder.cleared(id);
			}
			catch (final Exception ex)
			{
				// still in the order's context, so that the AD_Issue belongs to its client / org
				onSweepFailure(id, ex);
			}
		}
	}

	private void onSweepFailure(@NonNull final POSOrderAndPaymentId id, @NonNull final Exception failure)
	{
		failureRecorder.onFailure(id, failure, TableRecordReference.of(I_C_POS_Payment.Table_Name, id.getPaymentId()));
	}
}
