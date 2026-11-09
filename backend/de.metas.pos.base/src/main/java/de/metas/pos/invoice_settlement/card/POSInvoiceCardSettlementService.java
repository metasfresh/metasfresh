package de.metas.pos.invoice_settlement.card;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.sumup.SumUpConfig;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpContext;
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
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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
	private static final AdMessageKey MSG_ReleaseNotYetAllowed = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.ReleaseNotYetAllowed");
	private static final AdMessageKey MSG_ReleaseRefusedApproved = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.ReleaseRefusedApproved");
	private static final AdMessageKey MSG_ReleasedAsAbandoned = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.ReleasedAsAbandoned");

	private static final String SYSCONFIG_ReleaseAfterSeconds = "de.metas.pos.InvoiceCardSettlement.ReleaseAfterSeconds";
	private static final int DEFAULT_ReleaseAfterSeconds = 180;
	private static final int MIN_ReleaseAfterSeconds = 150;

	/**
	 * A PENDING settlement without a SumUp transaction that is older than this is an orphan: its checkout got no answer
	 * (the system stopped between saving and sending), since a send takes at most 2 × 60 s.
	 */
	private static final Duration ORPHAN_AGE = Duration.ofSeconds(150);

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

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
	 * The till's check, also run by the release: refreshes the settlement's SumUp transaction from SumUp, if it has one, and then
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

	/**
	 * The cashier's release of a settlement whose result is not known, after the configured wait, in two steps:
	 * <ol>
	 * <li>without confirmation: allowed for PENDING / UNKNOWN settlements older than the configured wait
	 * ({@link #getReleaseAfter}); cancels the checkout on the terminal's default card reader (best effort), then re-checks
	 * the settlement ({@link #check}, outside the settlement lock). If it is still PENDING / UNKNOWN, the cashier must
	 * confirm that the card was not charged ({@code confirmationRequired}); otherwise the booked, failed or cancelled
	 * settlement is returned;</li>
	 * <li>with confirmation: under the settlement lock, sets ABANDONED (and {@code IsAbandoned}, so the invoice is no longer
	 * blocked) only if the status is still {@code expectedStatus}; otherwise the current state is returned. The release
	 * is recorded as an AD_Issue of the settlement for back office ({@link #recordReleasedAsAbandoned}). If the
	 * settlement lock is not got within the booking lock timeout, nothing is released and the current state is
	 * returned.</li>
	 * </ol>
	 * Refused (nothing changed): before the configured wait ({@code ReleaseNotYetAllowed}); once SumUp approved the
	 * payment, i.e. the settlement is APPROVED / BOOKING_ERROR, or its SumUp transaction is approved
	 * ({@code ReleaseRefusedApproved}).
	 */
	@NonNull
	public POSInvoiceCardSettlementReleaseResult release(@NonNull final POSInvoiceCardSettlementReleaseRequest request)
	{
		// check() and the release run their own transactions, and check() must not run under the settlement lock
		trxManager.assertThreadInheritedTrxNotExists();

		return request.isConfirmNotCharged()
				? confirmRelease(request)
				: checkBeforeRelease(request.getId());
	}

	@NonNull
	private POSInvoiceCardSettlementReleaseResult checkBeforeRelease(@NonNull final POSInvoiceCardSettlementId id)
	{
		final POSInvoiceCardSettlement settlement = repository.getById(id);
		if (!isReleasableStatus(settlement.getStatus()))
		{
			return toReleaseResult(settlement, false);
		}

		final POSInvoiceCardSettlementReleaseResult notYetAllowed = refuseIfTooEarly(settlement);
		if (notYetAllowed != null)
		{
			return notYetAllowed;
		}

		terminateCheckoutOnReader(settlement);

		final POSInvoiceCardSettlementCheckResult checkResult = check(id);
		return toReleaseResult(checkResult.getSettlement(), checkResult.isCheckFailed());
	}

	@NonNull
	private POSInvoiceCardSettlementReleaseResult confirmRelease(@NonNull final POSInvoiceCardSettlementReleaseRequest request)
	{
		final POSInvoiceCardSettlementId id = request.getId();
		final POSInvoiceCardSettlementStatus expectedStatus = request.getExpectedStatus();
		if (expectedStatus == null || !isReleasableStatus(expectedStatus))
		{
			throw new AdempiereException("The confirmation of a card settlement release needs the expected status PENDING or UNKNOWN")
					.setParameter("expectedStatus", expectedStatus)
					.setRecord(id.toRecordRef());
		}

		final POSInvoiceCardSettlement settlementBefore = repository.getById(id);
		if (settlementBefore.getStatus() != expectedStatus)
		{
			return toReleaseResult(settlementBefore, false);
		}

		// the first step checked the age already; repeated here so that no request can release earlier
		final POSInvoiceCardSettlementReleaseResult notYetAllowed = refuseIfTooEarly(settlementBefore);
		if (notYetAllowed != null)
		{
			return notYetAllowed;
		}

		final POSInvoiceCardSettlement released;
		try
		{
			released = trxManager.callInNewTrx(() -> {
				booking.setLockTimeout();
				final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
				if (settlement.getStatus() != expectedStatus || isApprovedAtSumUp(id))
				{
					return null;
				}
				final POSInvoiceCardSettlement abandoned = repository.save(settlement.toBuilder()
						.status(POSInvoiceCardSettlementStatus.ABANDONED)
						.isAbandoned(true)
						.build());
				recordReleasedAsAbandoned(abandoned, request.getCashierId());
				return abandoned;
			});
		}
		catch (final Exception ex)
		{
			if (DBException.findSQLExceptionInCauseChainOrNull(ex, DBException::isLockNotAvailable) == null)
			{
				throw ex;
			}
			// e.g. a booking holds the lock: nothing released; the till gets the current state (and asks again if unchanged)
			logger.info("Card settlement {} is locked by another transaction; not released", id, ex);
			return toReleaseResult(repository.getById(id), false);
		}

		if (released == null)
		{
			// changed meanwhile, or approved at SumUp (then refused)
			return toReleaseResult(repository.getById(id), false);
		}

		return toReleaseResult(released, false);
	}

	/**
	 * @return the release result for the settlement as it is now: refused if SumUp approved its payment, a confirmation
	 * prompt if it is still PENDING / UNKNOWN, else the settlement (nothing to release)
	 */
	@NonNull
	private POSInvoiceCardSettlementReleaseResult toReleaseResult(@NonNull final POSInvoiceCardSettlement settlement, final boolean checkFailed)
	{
		final POSInvoiceCardSettlementStatus status = settlement.getStatus();
		if (status == POSInvoiceCardSettlementStatus.APPROVED
				|| status == POSInvoiceCardSettlementStatus.BOOKING_ERROR
				|| (isReleasableStatus(status) && isApprovedAtSumUp(settlement.getId())))
		{
			return POSInvoiceCardSettlementReleaseResult.builder()
					.settlement(settlement)
					.checkFailed(checkFailed)
					.refusedMessageKey(MSG_ReleaseRefusedApproved)
					.refusedMessage(TranslatableStrings.adMessage(MSG_ReleaseRefusedApproved))
					.build();
		}

		return POSInvoiceCardSettlementReleaseResult.builder()
				.settlement(settlement)
				.confirmationRequired(isReleasableStatus(status))
				.checkFailed(checkFailed)
				.build();
	}

	private static boolean isReleasableStatus(@NonNull final POSInvoiceCardSettlementStatus status)
	{
		return status == POSInvoiceCardSettlementStatus.PENDING || status == POSInvoiceCardSettlementStatus.UNKNOWN;
	}

	/**
	 * @return whether the settlement's SumUp transaction, as stored locally, is approved and not refunded (a refunded
	 * charge is never booked, see {@link POSInvoiceCardSettlementBooking})
	 */
	private boolean isApprovedAtSumUp(@NonNull final POSInvoiceCardSettlementId id)
	{
		return booking.findSumUpTransaction(id)
				.map(SumUpTransaction::isSuccessfulAndNotRefunded)
				.orElse(false);
	}

	@Nullable
	private POSInvoiceCardSettlementReleaseResult refuseIfTooEarly(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final Duration releaseAfter = getReleaseAfter(settlement.getClientAndOrgId());
		if (settlement.getCreated().plus(releaseAfter).isBefore(SystemTime.asInstant()))
		{
			return null;
		}

		return POSInvoiceCardSettlementReleaseResult.builder()
				.settlement(settlement)
				.refusedMessageKey(MSG_ReleaseNotYetAllowed)
				.refusedMessage(TranslatableStrings.adMessage(MSG_ReleaseNotYetAllowed, String.valueOf(releaseAfter.getSeconds())))
				.build();
	}

	/**
	 * @return the wait after which the cashier may release a settlement: AD_SysConfig {@link #SYSCONFIG_ReleaseAfterSeconds},
	 * default {@link #DEFAULT_ReleaseAfterSeconds}; a value below {@link #MIN_ReleaseAfterSeconds} (above the worst-case
	 * send of 2 × 60 s) is raised to it
	 */
	@NonNull
	private Duration getReleaseAfter(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final int seconds = sysConfigBL.getIntValue(SYSCONFIG_ReleaseAfterSeconds, DEFAULT_ReleaseAfterSeconds, clientAndOrgId);
		return Duration.ofSeconds(Math.max(seconds, MIN_ReleaseAfterSeconds));
	}

	/**
	 * Asks the terminal's default card reader to abort its checkout. Best effort: SumUp does not confirm it, and a
	 * failure (e.g. a 4xx because the reader has no checkout running) is only logged; the re-check decides.
	 */
	private void terminateCheckoutOnReader(@NonNull final POSInvoiceCardSettlement settlement)
	{
		try
		{
			final POSTerminalPaymentProcessorConfig paymentProcessorConfig = posTerminalService.getPOSTerminalById(settlement.getPosTerminalId()).getPaymentProcessorConfigNotNull();
			final SumUpConfigId sumUpConfigId = Check.assumeNotNull(paymentProcessorConfig.getSumUpConfigId(), "sumUpConfigId is set for {}", paymentProcessorConfig);
			final SumUpConfig sumUpConfig = sumUpService.getConfig(sumUpConfigId);
			sumUpService.terminateCardReaderCheckout(sumUpConfigId, sumUpConfig.getDefaultCardReaderExternalIdNotNull());
		}
		catch (final Exception ex)
		{
			logger.warn("Could not terminate the card reader checkout of card settlement {}; releasing anyway", settlement.getId(), ex);
		}
	}

	/**
	 * Records the release as an AD_Issue of the settlement, in the settlement's client / org (not the cashier's login
	 * context), so that back office reconciles it with SumUp. Called inside the release transaction, under the settlement
	 * lock.
	 * <p>
	 * The AD_Issue is committed in its own transaction, before the release commits ({@link IErrorManager#createIssue}).
	 * If writing it fails, the release is rolled back and the cashier can try again. If the release commit fails after
	 * the issue was written, a stray issue remains for a settlement that is not ABANDONED, and a repeated release records
	 * a second one. This is deliberate, as for the booking's issues ({@link POSInvoiceCardSettlementBooking}): a duplicate
	 * issue is better than a release that back office never hears of.
	 */
	private void recordReleasedAsAbandoned(@NonNull final POSInvoiceCardSettlement settlement, @NonNull final UserId cashierId)
	{
		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(settlement.getClientAndOrgId()))
		{
			final String invoiceDocumentNo = invoiceBL.getById(settlement.getInvoiceId()).getDocumentNo();
			errorManager.createIssue(new AdempiereException(MSG_ReleasedAsAbandoned, String.valueOf(settlement.getId().getRepoId()), invoiceDocumentNo)
					.setParameter("ReleasedBy_User_ID", cashierId.getRepoId())
					.setRecord(settlement.getId().toRecordRef()));
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
