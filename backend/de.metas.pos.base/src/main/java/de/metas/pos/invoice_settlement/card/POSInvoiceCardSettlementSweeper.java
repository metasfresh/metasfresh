package de.metas.pos.invoice_settlement.card;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpContext;
import de.metas.payment.sumup.SumUpPollerCycleListener;
import de.metas.pos.SweepFailureRecorder;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
 * AD_Issue of the settlement (see {@link SweepFailureRecorder}), and the next settlement is reconciled; the failed one is
 * retried by the next cycle. Booking errors themselves are handled (counted, AD_Issue) by the booking.
 */
@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class POSInvoiceCardSettlementSweeper implements SumUpPollerCycleListener
{
	private static final Logger logger = LogManager.getLogger(POSInvoiceCardSettlementSweeper.class);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final POSInvoiceCardSettlementService service;

	/**
	 * Records each sweep failure once as AD_Issue of its settlement, for as long as the settlement keeps failing.
	 */
	private final SweepFailureRecorder<POSInvoiceCardSettlementId> failureRecorder = new SweepFailureRecorder<>(logger, "card settlement");

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
		failureRecorder.retainOnly(ids);

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
				failureRecorder.cleared(id);
			}
			catch (final Exception ex)
			{
				// still in the settlement's context, so that the AD_Issue belongs to its client / org
				onSweepFailure(id, ex);
			}
		}
	}

	private void onSweepFailure(@NonNull final POSInvoiceCardSettlementId id, @NonNull final Exception failure)
	{
		failureRecorder.onFailure(id, failure, id.toRecordRef());
	}
}
