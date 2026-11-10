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
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
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
import de.metas.pos.repository.model.I_C_POS_InvoiceCardSettlement;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

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
	private static final AdMessageKey MSG_MarkResolvedRefused = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.MarkResolvedRefused");
	private static final AdMessageKey MSG_RetryRefusedSumUpNotReachable = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.RetryRefusedSumUpNotReachable");
	public static final AdMessageKey MSG_NotPossibleForStatus = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.NotPossibleForStatus");

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
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

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

		return new StartedSettlement(settlement, paymentProcessorConfig);
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
					.clientAndOrgId(settlement.getClientAndOrgId())
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
					errorManager.createIssue(metasfreshException),
					null);
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
	 * <p>
	 * Every status event follows a SumUp lookup attempt that has just stored the transaction
	 * ({@code SumUpService.updateTransactionFromRemote}: the poller, the SumUp callback, the till's check, back office's
	 * retry; {@code SumUpService.refundTransaction}; the back-office "update from SumUp" processes); the one event
	 * without a lookup is a new transaction's, at checkout, which is PENDING and books nothing. A failed lookup changes
	 * neither status nor refund, so it fires no event, except for the back-office processes, which force the event: then
	 * the transaction carries the failed lookup ({@code LastSync} = error). So the booking is told "refreshed", and it
	 * relies on that only if the stored lookup succeeded; otherwise it asks SumUp itself and allocates nothing while
	 * SumUp cannot be asked ({@link POSInvoiceCardSettlementBooking#completeBooking}).
	 */
	public void onSumUpTransactionChanged(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		try
		{
			reconcileLocally(id, true);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed applying the SumUp transaction status to card settlement {}", id, ex);
			errorManager.createIssue(AdempiereException.wrapIfNeeded(ex).setRecord(id.toRecordRef()));
		}
	}

	/**
	 * The till's check, also run by the release: refreshes the settlement's SumUp transaction from SumUp, if it has one
	 * ({@link #refreshSumUpTransactionFromRemote}), and then reconciles the settlement with it ({@link #reconcileLocally}).
	 * If that lookup succeeded, the booking does not ask SumUp again. A failed SumUp lookup leaves the transaction as it
	 * was ({@link SumUpService#updateTransactionFromRemote} records the error on it); the settlement is then reconciled
	 * with the last known state, whose booking asks SumUp itself before it allocates, and {@code checkFailed} is set.
	 * <p>
	 * The till checks whenever the cashier selects an invoice that the settlement blocks, so while SumUp is unreachable it
	 * checks again and again: back office gets one AD_Issue per outage, not one per check (see
	 * {@link POSInvoiceCardSettlementBooking#isCreateIssueOnFailedLookup}).
	 */
	@NonNull
	public POSInvoiceCardSettlementCheckResult check(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final POSInvoiceCardSettlement settlement = repository.getById(id);
		final boolean checkFailed = !refreshSumUpTransactionFromRemote(id, sumUpTrx -> POSInvoiceCardSettlementBooking.isCreateIssueOnFailedLookup(settlement, sumUpTrx));
		// a failed lookup must not count as a refresh: the booking then asks SumUp itself (and allocates nothing while it cannot)
		reconcileLocally(id, !checkFailed);

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
		return refreshSumUpTransactionFromRemote(id, sumUpTrx -> true);
	}

	/**
	 * @param isCreateIssueOnError whether a failed lookup of the given (stored) transaction is recorded as AD_Issue
	 * @see #refreshSumUpTransactionFromRemote(POSInvoiceCardSettlementId)
	 */
	private boolean refreshSumUpTransactionFromRemote(
			@NonNull final POSInvoiceCardSettlementId id,
			@NonNull final Predicate<SumUpTransaction> isCreateIssueOnError)
	{
		final SumUpTransaction sumUpTrx = booking.findSumUpTransaction(id).orElse(null);
		if (sumUpTrx == null)
		{
			return true;
		}

		try
		{
			sumUpService.updateTransactionFromRemote(sumUpTrx.getClientTransactionId(), isCreateIssueOnError.test(sumUpTrx));
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
		return POSInvoiceCardSettlementBooking.isLastSyncOk(sumUpTrxRefreshed);
	}

	/**
	 * Reconciles the settlement with its SumUp transaction as stored locally; used by the SumUp status events, the till's
	 * check and the background sweep. It decides on the local state; only the booking's step B asks SumUp once more,
	 * whether the charge was refunded meanwhile, right before it allocates (one lookup, outside any transaction and
	 * without a lock held, only when a cash journal is open; see {@link POSInvoiceCardSettlementBooking#completeBooking}),
	 * unless the caller has just asked SumUp successfully (the status events, see {@link #onSumUpTransactionChanged}; the
	 * till's check, if its lookup succeeded):
	 * <ul>
	 * <li>charged at SumUp (SUCCESSFUL, or REFUNDED since), or the booking was not finished (APPROVED) → booking
	 * ({@link POSInvoiceCardSettlementBooking#book}, which handles its own booking errors and never books a refunded
	 * charge);</li>
	 * <li>declined / cancelled at SumUp → FAILED / CANCELLED, only from PENDING / UNKNOWN;</li>
	 * <li>no SumUp transaction → the orphan rule ({@link #markUnknownIfOrphan}).</li>
	 * </ul>
	 * Each status change runs in its own transaction, so this must be called outside any transaction (asserted).
	 */
	public void reconcileLocally(@NonNull final POSInvoiceCardSettlementId id)
	{
		reconcileLocally(id, false);
	}

	/**
	 * @param isSumUpTransactionRefreshed the SumUp transaction was just refreshed from SumUp (by the caller, or by the
	 *                                    lookup that fired the status event), so the booking does not ask SumUp again if
	 *                                    that lookup succeeded
	 */
	private void reconcileLocally(@NonNull final POSInvoiceCardSettlementId id, final boolean isSumUpTransactionRefreshed)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final SumUpTransaction sumUpTrx = booking.findSumUpTransaction(id).orElse(null);
		final SumUpTransactionStatus sumUpStatus = sumUpTrx != null ? sumUpTrx.getStatus() : null;
		if ((sumUpTrx != null && sumUpTrx.isCharged())
				|| repository.getById(id).getStatus() == POSInvoiceCardSettlementStatus.APPROVED)
		{
			booking.book(id, isSumUpTransactionRefreshed);
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
	 * ({@link #getReleaseAfter}); cancels the checkout on the terminal's default card reader unless the settlement's own
	 * SumUp transaction is known and no longer PENDING (best effort, see {@link #terminateCheckoutOnReaderUnlessFinished}), then re-checks
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

		terminateCheckoutOnReaderUnlessFinished(settlement);

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

	/**
	 * @return the instant from which the cashier may release the settlement, see {@link #getReleaseAfter}
	 */
	@NonNull
	public Instant getReleasableAt(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return settlement.getCreated().plus(getReleaseAfter(settlement.getClientAndOrgId()));
	}

	/**
	 * @return the document number of the settlement's invoice
	 */
	@NonNull
	public String getInvoiceDocumentNo(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return invoiceBL.getById(settlement.getInvoiceId()).getDocumentNo();
	}

	@Nullable
	private POSInvoiceCardSettlementReleaseResult refuseIfTooEarly(@NonNull final POSInvoiceCardSettlement settlement)
	{
		if (getReleasableAt(settlement).isBefore(SystemTime.asInstant()))
		{
			return null;
		}

		final Duration releaseAfter = getReleaseAfter(settlement.getClientAndOrgId());

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
	 * Asks the terminal's default card reader to abort its checkout, unless the settlement's own SumUp transaction is
	 * known and SumUp reports it as no longer PENDING: SumUp's terminate aborts whatever checkout the reader runs (it
	 * takes no checkout id), which then can only be another one (e.g. another till's, on a shared reader). So:
	 * <ul>
	 * <li>no SumUp transaction (an orphan, or UNKNOWN because metasfresh failed right after SumUp accepted the checkout)
	 * → terminate: the reader may still run this settlement's checkout;</li>
	 * <li>otherwise the transaction is refreshed from SumUp first (a failed lookup leaves the last known status, see
	 * {@link #refreshSumUpTransactionFromRemote}) → terminate only if it is still PENDING.</li>
	 * </ul>
	 * This lookup is not the one of the re-check that follows ({@link #check}): that one, after the terminate, sees what
	 * the reader did meanwhile. Residual limit: while this settlement's transaction is PENDING, a shared reader may already
	 * run another checkout; SumUp offers no way to tell.
	 */
	private void terminateCheckoutOnReaderUnlessFinished(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final POSInvoiceCardSettlementId id = settlement.getId();
		if (booking.findSumUpTransaction(id).isPresent())
		{
			refreshSumUpTransactionFromRemote(id);
			final boolean isFinishedAtSumUp = booking.findSumUpTransaction(id)
					.map(sumUpTrx -> !sumUpTrx.getStatus().isPending())
					.orElse(false);
			if (isFinishedAtSumUp)
			{
				return;
			}
		}

		terminateCheckoutOnReader(settlement);
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
			final String invoiceDocumentNo = getInvoiceDocumentNo(settlement);
			errorManager.createIssue(new AdempiereException(MSG_ReleasedAsAbandoned, String.valueOf(settlement.getId().getRepoId()), invoiceDocumentNo)
					.setParameter("ReleasedBy_User_ID", cashierId.getRepoId())
					.setRecord(settlement.getId().toRecordRef()));
		}
	}

	/**
	 * Back office's "retry booking" of a BOOKING_ERROR settlement, or of an APPROVED one that waits for a journal:
	 * under the settlement lock, prepares the settlement so that {@link POSInvoiceCardSettlementBooking#book} books it
	 * again with a fresh attempt budget ({@code BookingAttempts} = 0), then books it right away:
	 * <ul>
	 * <li>BOOKING_ERROR with a payment (step B failed) → APPROVED, so that step B runs again on the refreshed transaction
	 * (a charge refunded meanwhile → BOOKING_ERROR "refunded at SumUp", nothing allocated);</li>
	 * <li>BOOKING_ERROR without a payment (step A failed) → UNKNOWN (ABANDONED if it was released as abandoned), so
	 * that step A creates the payment first. Its SumUp transaction is refreshed from SumUp before, so that step A sees
	 * a charge refunded meanwhile and books nothing (UNKNOWN → FAILED "refunded at SumUp"; an abandoned one stays
	 * ABANDONED);</li>
	 * <li>APPROVED stays APPROVED (its {@code ErrorMsg} is kept, so a still missing journal is not reported again).</li>
	 * </ul>
	 * In all these cases the SumUp transaction is refreshed from SumUp first: neither a payment is created nor one
	 * allocated for a charge that may have been refunded.
	 * The SumUp lookup and the booking run in the settlement's client / org, so their AD_Issues land there, not in back
	 * office's login context. The booking handles its own errors ({@link POSInvoiceCardSettlementBooking#book}): the
	 * caller reads the outcome from the returned settlement.
	 *
	 * @return the settlement after the retry
	 * @throws AdempiereException {@code RetryRefusedSumUpNotReachable} — SumUp could not be asked whether the charge was
	 * refunded; {@code NotPossibleForStatus} — the settlement is in another
	 * status. In both cases nothing is changed.
	 */
	@NonNull
	public POSInvoiceCardSettlement retryBooking(@NonNull final POSInvoiceCardSettlementId id)
	{
		// the SumUp lookup and the booking run their own transactions
		trxManager.assertThreadInheritedTrxNotExists();

		// the SumUp lookup and the booking write their AD_Issues and SumUp API logs in the settlement's client / org
		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(repository.getById(id).getClientAndOrgId()))
		{
			// back office may have refunded the charge in the SumUp dashboard; the poller does not refresh a successful transaction
			final POSInvoiceCardSettlement settlementBefore = repository.getById(id);
			// only for the statuses that are retried at all (others are refused below, without asking SumUp)
			final boolean isRefreshBeforeBooking = settlementBefore.getStatus() == POSInvoiceCardSettlementStatus.BOOKING_ERROR
					|| settlementBefore.getStatus() == POSInvoiceCardSettlementStatus.APPROVED;
			boolean isSumUpTransactionRefreshed = false;
			if (isRefreshBeforeBooking)
			{
				if (!refreshSumUpTransactionFromRemote(id))
				{
					// a payment must not be created, nor allocated, for a charge that may have been refunded
					throw new AdempiereException(MSG_RetryRefusedSumUpNotReachable)
							.markAsUserValidationError()
							.setRecord(id.toRecordRef());
				}
				isSumUpTransactionRefreshed = true;
			}

			trxManager.runInNewTrx(() -> {
				booking.setLockTimeout();
				final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
				assertStatusIn(settlement, POSInvoiceCardSettlementStatus.BOOKING_ERROR, POSInvoiceCardSettlementStatus.APPROVED);
				repository.save(toRetry(settlement));
			});

			// the transaction was refreshed just before: step B does not ask SumUp again
			booking.book(id, isSumUpTransactionRefreshed);
		}

		return repository.getById(id);
	}

	/**
	 * @return whether the settlement waits for the background sweep ({@link POSInvoiceCardSettlementRepository#streamSweepCandidateIds})
	 * to book it, i.e. a booking attempt failed with a transient error and is retried without back office: UNKNOWN (the
	 * payment is not created yet), APPROVED without an error or with
	 * {@link POSInvoiceCardSettlementBooking#ERRORMSG_SumUpNotReachable} (the sweep asks SumUp again in every cycle; any
	 * other error, e.g. a missing journal, is one back office must see), or ABANDONED while SumUp reports its charge as
	 * approved and not refunded (an abandoned one whose charge was refunded is final)
	 */
	public boolean isAwaitingAutomaticBooking(@NonNull final POSInvoiceCardSettlement settlement)
	{
		switch (settlement.getStatus())
		{
			case UNKNOWN:
				return true;
			case APPROVED:
				return settlement.getErrorMsg() == null
						|| POSInvoiceCardSettlementBooking.ERRORMSG_SumUpNotReachable.equals(settlement.getErrorMsg());
			case ABANDONED:
				return isApprovedAtSumUp(settlement.getId());
			default:
				return false;
		}
	}

	/**
	 * @return the settlement as {@link POSInvoiceCardSettlementBooking#book} must find it to book it again, with a fresh
	 * attempt budget
	 */
	@NonNull
	private static POSInvoiceCardSettlement toRetry(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final POSInvoiceCardSettlement.POSInvoiceCardSettlementBuilder retry = settlement.toBuilder().bookingAttempts(0);
		if (settlement.getStatus() != POSInvoiceCardSettlementStatus.BOOKING_ERROR)
		{
			return retry.build(); // APPROVED keeps its ErrorMsg, so that a still missing journal is not reported again
		}

		if (settlement.getPaymentId() != null)
		{
			// step B failed: run it again
			return retry.status(POSInvoiceCardSettlementStatus.APPROVED).errorMsg(null).build();
		}

		// step A failed: run it again; it also handles a charge refunded at SumUp meanwhile
		return retry.status(settlement.isAbandoned() ? POSInvoiceCardSettlementStatus.ABANDONED : POSInvoiceCardSettlementStatus.UNKNOWN)
				.errorMsg(null)
				.build();
	}

	/**
	 * Back office's "mark resolved manually" of a BOOKING_ERROR settlement, or of an APPROVED one that waits for a
	 * journal, after it fixed the booking by hand: under the invoice lock and then the settlement lock, sets SUCCESSFUL
	 * and stores the note; nothing is booked. The invoice is no longer blocked.
	 *
	 * @throws AdempiereException {@code MarkResolvedRefused} — the invoice is still open while the card payment is not
	 * (fully) allocated or was not even created: unblocking the invoice would let it be paid a second time;
	 * {@link FillMandatoryException} — the note is empty; {@code NotPossibleForStatus} — the settlement is in another
	 * status
	 */
	@NonNull
	public POSInvoiceCardSettlement markResolvedManually(@NonNull final POSInvoiceCardSettlementId id, @Nullable final String note)
	{
		final String noteNorm = assertNoteNotBlank(note);

		return trxManager.callInNewTrx(() -> {
			booking.setLockTimeout();
			// lock order: invoice before settlement; the settlement's invoice never changes
			final I_C_Invoice invoice = invoiceSettlementService.lockInvoice(repository.getById(id).getInvoiceId());
			final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
			assertStatusIn(settlement, POSInvoiceCardSettlementStatus.BOOKING_ERROR, POSInvoiceCardSettlementStatus.APPROVED);

			if (invoiceSettlementService.isStillOpen(invoice) && !isPaymentFullyAllocated(settlement))
			{
				throw new AdempiereException(MSG_MarkResolvedRefused)
						.markAsUserValidationError()
						.setRecord(id.toRecordRef());
			}

			return repository.save(settlement.toBuilder()
					.status(POSInvoiceCardSettlementStatus.SUCCESSFUL)
					.note(noteNorm)
					.build());
		});
	}

	private boolean isPaymentFullyAllocated(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final PaymentId paymentId = settlement.getPaymentId();
		return paymentId != null && paymentDAO.getAvailableAmount(paymentId).signum() == 0;
	}

	/**
	 * Back office's "mark refunded" of a CREDIT_REFUND_PENDING settlement, after it refunded or reallocated the customer
	 * credit, whatever part of the card payment is allocated: under the settlement lock, sets REFUNDED and stores the
	 * note.
	 *
	 * @throws AdempiereException {@link FillMandatoryException} — the note is empty; {@code NotPossibleForStatus} — the settlement is in
	 * another status
	 */
	@NonNull
	public POSInvoiceCardSettlement markRefunded(@NonNull final POSInvoiceCardSettlementId id, @Nullable final String note)
	{
		final String noteNorm = assertNoteNotBlank(note);

		return trxManager.callInNewTrx(() -> {
			booking.setLockTimeout();
			final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
			assertStatusIn(settlement, POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING);

			return repository.save(settlement.toBuilder()
					.status(POSInvoiceCardSettlementStatus.REFUNDED)
					.note(noteNorm)
					.build());
		});
	}

	@NonNull
	private static String assertNoteNotBlank(@Nullable final String note)
	{
		final String noteNorm = StringUtils.trimBlankToNull(note);
		if (noteNorm == null)
		{
			throw new FillMandatoryException(I_C_POS_InvoiceCardSettlement.COLUMNNAME_Note);
		}
		return noteNorm;
	}

	private static void assertStatusIn(@NonNull final POSInvoiceCardSettlement settlement, @NonNull final POSInvoiceCardSettlementStatus... expectedStatuses)
	{
		if (!Arrays.asList(expectedStatuses).contains(settlement.getStatus()))
		{
			throw new AdempiereException(MSG_NotPossibleForStatus)
					.markAsUserValidationError()
					.setParameter("status", settlement.getStatus())
					.setParameter("expectedStatuses", Arrays.toString(expectedStatuses))
					.setRecord(settlement.getId().toRecordRef());
		}
	}

	@Value
	private static class StartedSettlement
	{
		@NonNull POSInvoiceCardSettlement settlement;
		@NonNull POSTerminalPaymentProcessorConfig paymentProcessorConfig;
	}
}
