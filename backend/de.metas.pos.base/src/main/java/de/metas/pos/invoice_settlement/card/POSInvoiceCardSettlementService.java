package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.pos.POSPaymentProcessingStatus;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import de.metas.pos.POSTerminalService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService.SettleableInvoice;
import de.metas.pos.payment_gateway.POSPaymentProcessRequest;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.payment_gateway.POSPaymentProcessorService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

/**
 * Settles an existing, still-open sales invoice by card on the till's SumUp reader. Every attempt is a persisted
 * {@link POSInvoiceCardSettlement} with a small state machine; every status change is made under the settlement's
 * row lock and conditional on the status it expects.
 * <p>
 * Lock order: invoice before settlement, wherever both are needed.
 */
@Service
@RequiredArgsConstructor
public class POSInvoiceCardSettlementService
{
	private static final Logger logger = LogManager.getLogger(POSInvoiceCardSettlementService.class);
	private static final String BLOCKING_INDEX_NAME = "c_pos_invoicecardsettlement_blocking_uq";
	private static final AdMessageKey MSG_CardPaymentStillOpen = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen");

	/**
	 * A PENDING settlement without a SumUp transaction that is older than this is an orphan: its checkout got no answer
	 * (the system stopped between saving and sending), since a send takes at most 2 × 60 s.
	 */
	private static final Duration ORPHAN_AGE = Duration.ofSeconds(150);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final POSInvoiceSettlementService invoiceSettlementService;
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSPaymentProcessorService paymentProcessorService;
	@NonNull private final MoneyService moneyService;
	@NonNull private final POSInvoiceCardSettlementBooking booking;
	@NonNull private final SumUpService sumUpService;

	@NonNull
	public POSInvoiceCardSettlement getById(@NonNull final POSInvoiceCardSettlementId id)
	{
		return repository.getById(id);
	}

	/**
	 * @return the part of the settlement's card payment that is allocated to the settlement's invoice; zero while the
	 * settlement has no payment
	 */
	@NonNull
	public Money getAllocatedAmount(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return invoiceSettlementService.getAllocatedAmt(settlement.getInvoiceId(), settlement.getPaymentId(), settlement.getAmount().getCurrencyId());
	}

	/**
	 * @return the unfinished settlement of the invoice that blocks any further payment of it, if any
	 */
	@NonNull
	public Optional<POSInvoiceCardSettlement> getBlockingByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return repository.findBlockingByInvoiceId(invoiceId);
	}

	/**
	 * Starts a card settlement of the invoice's full open amount as follows:
	 * <ol>
	 * <li>in one transaction: lock the invoice, apply the cash guards, check that no blocking settlement exists and
	 * that a journal is open, insert the settlement as PENDING, commit;</li>
	 * <li>send the checkout to the card reader outside any transaction;</li>
	 * <li>apply the result under the settlement lock ({@link #applySendResult}).</li>
	 * </ol>
	 *
	 * @throws AdempiereException {@code CardPaymentStillOpen} — a card settlement of the invoice is unfinished;
	 * {@code NoLongerOpen} / {@code NotEligibleForSettlement} / {@code WrongOrg} / {@code CurrencyMismatch} — as for
	 * cash, see {@link POSInvoiceSettlementService#lockAndAssertSettleable}; {@code CashJournalNotOpen} — the terminal
	 * has no open cash journal. In all these cases nothing is persisted and nothing is sent.
	 */
	@NonNull
	public POSInvoiceCardSettlement start(@NonNull final POSInvoiceCardSettlementStartRequest request)
	{
		// fail before anything is persisted: the checkout must be sent outside any transaction
		trxManager.assertThreadInheritedTrxNotExists();

		final StartedSettlement started = callStartInTrx(request);

		final POSPaymentProcessResponse response = send(started);

		return applySendResult(started.getSettlement().getId(), response);
	}

	@NonNull
	private StartedSettlement callStartInTrx(@NonNull final POSInvoiceCardSettlementStartRequest request)
	{
		try
		{
			return trxManager.callInNewTrx(() -> startInTrx(request));
		}
		catch (final DBUniqueConstraintException ex)
		{
			// defense in depth: the invoice lock and the blocking check normally refuse first; if the blocking index
			// still fires, report it like the check does (the index's own error code would be a generic one)
			if (isBlockingIndexViolation(ex))
			{
				throw new AdempiereException(MSG_CardPaymentStillOpen)
						.setParameter("C_Invoice_ID", request.getInvoiceId());
			}
			throw ex;
		}
	}

	private static boolean isBlockingIndexViolation(@NonNull final DBUniqueConstraintException ex)
	{
		final Throwable cause = ex.getCause();
		final String msg = (cause != null ? cause.getMessage() : ex.getMessage());
		return msg != null && msg.toLowerCase().contains(BLOCKING_INDEX_NAME);
	}

	@NonNull
	private StartedSettlement startInTrx(@NonNull final POSInvoiceCardSettlementStartRequest request)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());

		// invoice lock first, then the guards (cash parity + the blocking check)
		final SettleableInvoice settleableInvoice = invoiceSettlementService.lockAndAssertSettleable(terminal, request.getInvoiceId());

		final POSTerminalPaymentProcessorConfig paymentProcessorConfig = terminal.getPaymentProcessorConfigNotNull();
		terminal.getCashJournalIdNotNull(); // refuse without an open journal, as the cash path does (the journal line is booked later)

		final OrgId orgId = terminal.getOrgId();
		final POSInvoiceCardSettlement settlement = repository.create(
				orgId,
				request.getPosTerminalId(),
				request.getInvoiceId(),
				BPartnerId.ofRepoId(settleableInvoice.getInvoice().getC_BPartner_ID()),
				request.getCashierId(),
				settleableInvoice.getOpen());

		return new StartedSettlement(settlement, paymentProcessorConfig, ClientAndOrgId.ofClientAndOrg(Env.getClientId(), orgId));
	}

	/**
	 * Sends the checkout, with the settlement id as description (to match it manually in the SumUp dashboard).
	 * Runs outside any transaction: the call can take long and must not hold locks.
	 */
	@NonNull
	private POSPaymentProcessResponse send(@NonNull final StartedSettlement started)
	{
		final POSInvoiceCardSettlement settlement = started.getSettlement();
		try
		{
			return paymentProcessorService.processPayment(POSPaymentProcessRequest.builder()
					.paymentProcessorConfig(started.getPaymentProcessorConfig())
					.clientAndOrgId(started.getClientAndOrgId())
					.posInvoiceCardSettlementId(settlement.getId())
					.description(String.valueOf(settlement.getId().getRepoId()))
					.amount(settlement.getAmount().toAmount(moneyService::getCurrencyCodeByCurrencyId))
					.build());
		}
		catch (final Exception ex)
		{
			// the processors report their failures in the response; this is something unexpected around the call,
			// so whether the reader got the request is unknown
			final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex)
					.setRecord(settlement.getId().toRecordRef());
			return POSPaymentProcessResponse.errorIndeterminate(
					started.getPaymentProcessorConfig(),
					AdempiereException.extractMessage(metasfreshException),
					errorManager.createIssue(metasfreshException));
		}
	}

	/**
	 * Applies the send result under the settlement lock:
	 * <ul>
	 * <li>a definite rejection (an error response from SumUp, or a configuration error before any call) → FAILED, if
	 * the status is still PENDING, or UNKNOWN without a SumUp transaction (an orphan flip);</li>
	 * <li>an indeterminate failure (timeout / IO / 5xx) → UNKNOWN, if the status is still PENDING;</li>
	 * <li>the SumUp transaction, if there is one, is linked in any status.</li>
	 * </ul>
	 */
	@NonNull
	private POSInvoiceCardSettlement applySendResult(
			@NonNull final POSInvoiceCardSettlementId settlementId,
			@NonNull final POSPaymentProcessResponse response)
	{
		return trxManager.callInNewTrx(() -> {
			final POSInvoiceCardSettlement settlementBefore = repository.getByIdForUpdate(settlementId);
			POSInvoiceCardSettlement settlement = settlementBefore;

			settlement = settlement.toBuilder().sumUpTransactionId(booking.getLinkedOrResolvedSumUpTransactionId(settlement)).build();

			if (response.getStatus() == POSPaymentProcessingStatus.FAILED)
			{
				final boolean isLinked = settlement.getSumUpTransactionId() != null;
				if (response.isIndeterminate())
				{
					if (settlement.getStatus() == POSInvoiceCardSettlementStatus.PENDING)
					{
						settlement = settlement.toBuilder()
								.status(POSInvoiceCardSettlementStatus.UNKNOWN)
								.errorMsg(POSInvoiceCardSettlement.truncateErrorMsg(response.getErrorMessage()))
								.build();
					}
				}
				else if (settlement.getStatus() == POSInvoiceCardSettlementStatus.PENDING
						|| (settlement.getStatus() == POSInvoiceCardSettlementStatus.UNKNOWN && !isLinked))
				{
					settlement = settlement.toBuilder()
							.status(POSInvoiceCardSettlementStatus.FAILED)
							.errorMsg(POSInvoiceCardSettlement.truncateErrorMsg(response.getErrorMessage()))
							.build();
				}
			}

			return settlement.equals(settlementBefore) ? settlementBefore : repository.save(settlement);
		});
	}

	/**
	 * Applies a status change of the settlement's SumUp transaction (as stored locally), see {@link #reconcileLocally}.
	 * Safe to call any number of times, also concurrently.
	 * <p>
	 * Never throws: any failure is logged and recorded as an AD_Issue of the settlement, whose status stays as it was, so
	 * the caller (the SumUp event dispatcher) still notifies its other listeners.
	 */
	public void onSumUpTransactionChanged(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		try
		{
			reconcileLocally(id);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed applying the SumUp transaction status to card settlement {}", id, ex);
			errorManager.createIssue(AdempiereException.wrapIfNeeded(ex).setRecord(id.toRecordRef()));
		}
	}

	/**
	 * The till's check (AC12): refreshes the settlement's SumUp transaction from SumUp, if it has one, and then
	 * reconciles the settlement with it ({@link #reconcileLocally}). A failed SumUp lookup leaves the transaction as it
	 * was ({@link SumUpService#updateTransactionFromRemote} records the error on it); the settlement is then reconciled
	 * with the last known state and {@code checkFailed} is set.
	 */
	@NonNull
	public POSInvoiceCardSettlementCheckResult check(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final boolean checkFailed = !refreshSumUpTransactionFromRemote(id);
		reconcileLocally(id);

		return POSInvoiceCardSettlementCheckResult.builder()
				.settlement(repository.getById(id))
				.checkFailed(checkFailed)
				.build();
	}

	/**
	 * @return false if SumUp could not be asked (the lookup failed, or the refresh failed before it); true if the
	 * transaction was refreshed, or if the settlement has no SumUp transaction (nothing to ask SumUp about)
	 */
	private boolean refreshSumUpTransactionFromRemote(@NonNull final POSInvoiceCardSettlementId id)
	{
		final SumUpTransaction sumUpTrx = booking.findSumUpTransaction(id).orElse(null);
		if (sumUpTrx == null)
		{
			return true;
		}

		try
		{
			sumUpService.updateTransactionFromRemote(sumUpTrx.getClientTransactionId());
		}
		catch (final Exception ex)
		{
			// the SumUp lookup errors are recorded on the transaction by the call itself; this is a failure around it
			// (e.g. no active SumUp config, so no client could be built): the transaction stays as it was
			// no AD_Issue: the till may check again and again while the config stays broken; it is told checkFailed
			logger.warn("Could not refresh the SumUp transaction of card settlement {}", id, ex);
			return false;
		}

		final SumUpTransaction sumUpTrxRefreshed = booking.findSumUpTransaction(id).orElseThrow(() -> new AdempiereException("SumUp transaction disappeared")
				.setParameter("SumUpClientTransactionId", sumUpTrx.getClientTransactionId())
				.setRecord(id.toRecordRef()));
		final SumUpTransaction.LastSync lastSync = sumUpTrxRefreshed.getLastSync();
		return lastSync == null || lastSync.getStatus() != SumUpTransaction.LastSyncStatus.Error;
	}

	/**
	 * Reconciles the settlement with its SumUp transaction as stored locally (no SumUp call); used by the SumUp status
	 * events, the till's check and the background sweep:
	 * <ul>
	 * <li>approved at SumUp, or the booking was not finished (APPROVED) → booking ({@link POSInvoiceCardSettlementBooking#book},
	 * which handles its own booking errors);</li>
	 * <li>declined / cancelled at SumUp → FAILED / CANCELLED, only from PENDING / UNKNOWN;</li>
	 * <li>no SumUp transaction → the orphan rule ({@link #markUnknownIfOrphan}).</li>
	 * </ul>
	 * Each status change runs in its own transaction, so this must be called outside any transaction (asserted).
	 */
	public void reconcileLocally(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final SumUpTransaction sumUpTrx = booking.findSumUpTransaction(id).orElse(null);
		final SumUpTransactionStatus sumUpStatus = sumUpTrx != null ? sumUpTrx.getStatus() : null;
		if (SumUpTransactionStatus.SUCCESSFUL.equals(sumUpStatus)
				|| repository.getById(id).getStatus() == POSInvoiceCardSettlementStatus.APPROVED)
		{
			booking.book(id);
		}
		else if (SumUpTransactionStatus.FAILED.equals(sumUpStatus))
		{
			applyRejectedBySumUp(id, POSInvoiceCardSettlementStatus.FAILED);
		}
		else if (SumUpTransactionStatus.CANCELLED.equals(sumUpStatus))
		{
			applyRejectedBySumUp(id, POSInvoiceCardSettlementStatus.CANCELLED);
		}
		else if (sumUpTrx == null)
		{
			markUnknownIfOrphan(id);
		}
	}

	/**
	 * Orphan rule, under the settlement lock: a PENDING settlement without a SumUp transaction that was created more than
	 * {@link #ORPHAN_AGE} ago → UNKNOWN (still blocking, and releasable). A definite rejection of its checkout that still
	 * arrives later moves it to FAILED ({@link #applySendResult}).
	 */
	private void markUnknownIfOrphan(@NonNull final POSInvoiceCardSettlementId id)
	{
		runInNewTrxUnlessLocked(id, () -> {
			final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
			if (settlement.getStatus() != POSInvoiceCardSettlementStatus.PENDING
					|| booking.getLinkedOrResolvedSumUpTransactionId(settlement) != null
					|| !isOlderThanOrphanAge(settlement))
			{
				return;
			}

			repository.save(settlement.toBuilder().status(POSInvoiceCardSettlementStatus.UNKNOWN).build());
		});
	}

	private static boolean isOlderThanOrphanAge(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return settlement.getCreated().plus(ORPHAN_AGE).isBefore(SystemTime.asInstant());
	}

	/**
	 * Declined / cancelled on the reader: changes the status under the settlement lock, only from PENDING / UNKNOWN.
	 */
	private void applyRejectedBySumUp(
			@NonNull final POSInvoiceCardSettlementId id,
			@NonNull final POSInvoiceCardSettlementStatus newStatus)
	{
		runInNewTrxUnlessLocked(id, () -> {
			final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
			if (settlement.getStatus() != POSInvoiceCardSettlementStatus.PENDING
					&& settlement.getStatus() != POSInvoiceCardSettlementStatus.UNKNOWN)
			{
				return;
			}

			repository.save(settlement.toBuilder()
					.status(newStatus)
					.sumUpTransactionId(booking.getLinkedOrResolvedSumUpTransactionId(settlement))
					.build());
		});
	}

	/**
	 * Runs a status change in a new transaction with the booking lock timeout. If a needed row lock is not got in time
	 * (another transaction holds it), the settlement is left as it is and the next reconcile (the next sweep cycle)
	 * tries again; any other error is thrown.
	 */
	private void runInNewTrxUnlessLocked(@NonNull final POSInvoiceCardSettlementId id, @NonNull final Runnable statusChange)
	{
		try
		{
			trxManager.runInNewTrx(() -> {
				booking.setLockTimeout();
				statusChange.run();
			});
		}
		catch (final Exception ex)
		{
			if (DBException.findSQLExceptionInCauseChainOrNull(ex, DBException::isLockNotAvailable) == null)
			{
				throw ex;
			}
			logger.info("Card settlement {} is locked by another transaction; leaving it for the next check", id, ex);
		}
	}

	@Value
	private static class StartedSettlement
	{
		@NonNull POSInvoiceCardSettlement settlement;
		@NonNull POSTerminalPaymentProcessorConfig paymentProcessorConfig;
		@NonNull ClientAndOrgId clientAndOrgId;
	}
}
