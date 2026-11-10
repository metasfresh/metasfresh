package de.metas.pos.invoice_settlement.card;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.notification.INotificationBL;
import de.metas.notification.NotificationGroupName;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.sumup.SumUpContext;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.payment.sumup.repository.SumUpTransactionRepository;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalService;
import de.metas.pos.invoice_settlement.POSInvoiceSettlementService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * Books an approved card settlement in two steps, each in its own transaction: step A creates the payment
 * ({@link #bookPaymentIfApproved}), step B journals and allocates it ({@link #completeBooking}). Each step re-reads
 * the settlement under its row lock and acts only on the status it expects, so a repeated or concurrent delivery of
 * the same approval books exactly once.
 * <p>
 * {@link #book} runs both steps and handles their errors: a retryable one (serialization failure, lock timeout,
 * connection error) only counts the attempt, up to {@value #MAX_BOOKING_ATTEMPTS} attempts; any other one, and a
 * retryable one on the last attempt, ends the settlement as BOOKING_ERROR with one AD_Issue for back office. No open
 * cash journal is no error: the settlement waits as APPROVED until a journal is open (one AD_Issue, however often it
 * is retried).
 */
@Component
@RequiredArgsConstructor
public class POSInvoiceCardSettlementBooking
{
	/**
	 * {@code ErrorMsg} of a settlement whose charge SumUp reports as refunded (in full or in part) before it was booked:
	 * FAILED if the payment was not created yet (step A), BOOKING_ERROR if it was (step B).
	 */
	public static final String ERRORMSG_RefundedAtSumUp = "refunded at SumUp";

	/**
	 * {@code ErrorMsg} of an APPROVED settlement whose booking waits for the terminal's next open cash journal.
	 */
	public static final String ERRORMSG_NoOpenCashJournal = "no open cash journal";

	/**
	 * {@code ErrorMsg} of an APPROVED settlement whose step B waits because SumUp could not be asked whether the charge
	 * was refunded meanwhile: nothing is allocated or journaled until a later lookup succeeds (next sweep, till's check,
	 * back office's retry).
	 */
	public static final String ERRORMSG_SumUpNotReachable = "SumUp not reachable";

	/**
	 * {@code ErrorMsg} of a FAILED / CANCELLED settlement whose charge SumUp reports as approved after all.
	 */
	private static final String ERRORMSG_ApprovedAfterRejected = "SumUp reports the card charge as successful, but the card settlement was already rejected; nothing was booked";

	/**
	 * Booking attempts after which a retryable error is no longer retried.
	 */
	static final int MAX_BOOKING_ATTEMPTS = 5;

	/**
	 * PostgreSQL {@code lock_timeout} of each booking transaction: a booking never waits long for a row (invoice,
	 * settlement) locked by another transaction; it fails retryably and the next attempt tries again.
	 */
	private static final String SYSCONFIG_LockTimeoutMillis = "de.metas.pos.InvoiceCardSettlement.BookingLockTimeoutMillis";
	private static final int DEFAULT_LockTimeoutMillis = 5000;

	private static final ImmutableSet<POSInvoiceCardSettlementStatus> STATUSES_BeforePayment = ImmutableSet.of(
			POSInvoiceCardSettlementStatus.PENDING,
			POSInvoiceCardSettlementStatus.UNKNOWN,
			POSInvoiceCardSettlementStatus.ABANDONED);
	private static final ImmutableSet<POSInvoiceCardSettlementStatus> STATUSES_PaymentCreated = ImmutableSet.of(POSInvoiceCardSettlementStatus.APPROVED);

	private static final Logger logger = LogManager.getLogger(POSInvoiceCardSettlementBooking.class);

	private static final NotificationGroupName NOTIFICATION_GROUP_NAME = NotificationGroupName.of("de.metas.pos.InvoiceCardSettlement");
	private static final AdMessageKey MSG_CreditRefundPending = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.CreditRefundPending");
	private static final AdMessageKey MSG_JournalDescription = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.JournalDescription");
	private static final AdMessageKey MSG_CashJournalNotOpen = AdMessageKey.of("de.metas.pos.CashJournalNotOpen");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final POSInvoiceSettlementService invoiceSettlementService;
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posCashJournalService;
	@NonNull private final SumUpTransactionRepository sumUpTransactionRepository;
	@NonNull private final SumUpService sumUpService;

	/**
	 * Runs step A and then step B, each in its own transaction, and handles their errors (see the class comment).
	 * A booking error is handled here and not thrown; only a failure while recording that error (e.g. the connection
	 * is lost) is thrown, so callers that must not fail (the SumUp event dispatcher) still need their own catch.
	 * <p>
	 * Step B may ask SumUp once (see {@link #completeBooking}); so this must run outside any transaction.
	 *
	 * @param isSumUpTransactionRefreshed the caller has just refreshed the settlement's SumUp transaction from SumUp, so
	 *                                    step B does not ask SumUp again
	 */
	public void book(@NonNull final POSInvoiceCardSettlementId id, final boolean isSumUpTransactionRefreshed)
	{
		try
		{
			bookPaymentIfApproved(id);
		}
		catch (final Exception ex)
		{
			handleBookingError(id, ex, STATUSES_BeforePayment);
			return;
		}

		try
		{
			completeBooking(id, isSumUpTransactionRefreshed);
		}
		catch (final Exception ex)
		{
			handleBookingError(id, ex, STATUSES_PaymentCreated);
		}
	}

	/**
	 * Under the settlement lock, if the failed step still applies to the settlement's status (otherwise a concurrent
	 * booking moved it on): counts the attempt; a non-retryable error, or a retryable one on the last attempt, sets
	 * BOOKING_ERROR and records one AD_Issue (the status change happens once).
	 * <p>
	 * The settlement lock is waited for at most the booking lock timeout; if it is not got in time, the settlement is
	 * left unchanged (the attempt is not counted), so that the next delivery or sweep retries the booking.
	 * <p>
	 * The AD_Issue is committed in its own transaction, before the transaction that sets BOOKING_ERROR commits
	 * ({@link IErrorManager#createIssue}). If that commit fails, the next attempt records a second issue. This is
	 * deliberate: a duplicate issue is better than a booking error that back office never hears of, which an issue
	 * written only after the commit would risk. The same holds for the other once-only issues of this class.
	 */
	@VisibleForTesting
	void handleBookingError(
			@NonNull final POSInvoiceCardSettlementId id,
			@NonNull final Exception bookingError,
			@NonNull final ImmutableSet<POSInvoiceCardSettlementStatus> statusesOfFailedStep)
	{
		try
		{
			// own trx: the failed step's trx was rolled back, and the error must be recorded even so
			trxManager.runInNewTrx(() -> handleBookingErrorInTrx(id, bookingError, statusesOfFailedStep));
		}
		catch (final Exception ex)
		{
			if (DBException.findSQLExceptionInCauseChainOrNull(ex, DBException::isLockNotAvailable) == null)
			{
				throw ex;
			}
			logger.warn("Could not lock card settlement {} to record its booking error in time; leaving it as it is, the next attempt retries the booking", id, bookingError);
		}
	}

	private void handleBookingErrorInTrx(
			@NonNull final POSInvoiceCardSettlementId id,
			@NonNull final Exception bookingError,
			@NonNull final ImmutableSet<POSInvoiceCardSettlementStatus> statusesOfFailedStep)
	{
		final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(bookingError).setRecord(id.toRecordRef());
		final boolean isRetryable = isRetryable(bookingError);

		setLockTimeout();
		final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
		if (!statusesOfFailedStep.contains(settlement.getStatus()))
		{
			logger.warn("Booking card settlement {} failed, but its status is already {}; leaving it as it is", id, settlement.getStatus(), bookingError);
			return;
		}

		final int bookingAttempts = settlement.getBookingAttempts() + 1;
		if (isRetryable && bookingAttempts < MAX_BOOKING_ATTEMPTS)
		{
			logger.info("Booking card settlement {} failed with a retryable error (attempt {} of {}); the next attempt retries it", id, bookingAttempts, MAX_BOOKING_ATTEMPTS, bookingError);
			repository.save(settlement.toBuilder().bookingAttempts(bookingAttempts).build());
			return;
		}

		repository.save(settlement.toBuilder()
				.status(POSInvoiceCardSettlementStatus.BOOKING_ERROR)
				.bookingAttempts(bookingAttempts)
				.sumUpTransactionId(getLinkedOrResolvedSumUpTransactionId(settlement))
				.errorMsg(POSInvoiceCardSettlement.truncateErrorMsg(AdempiereException.extractMessage(metasfreshException)))
				.build());
		errorManager.createIssue(metasfreshException);
	}

	/**
	 * @return whether a booking error is transient, so that a later attempt can succeed: a serialization failure or
	 * deadlock, a lock timeout, or a connection error
	 */
	static boolean isRetryable(@NonNull final Throwable bookingError)
	{
		return DBException.findSQLExceptionInCauseChainOrNull(bookingError, POSInvoiceCardSettlementBooking::isRetryableSQLException) != null;
	}

	private static boolean isRetryableSQLException(@NonNull final SQLException sqlException)
	{
		final String sqlState = sqlException.getSQLState();
		return DBException.isLockNotAvailable(sqlException) // lock_timeout expired
				|| "40001".equals(sqlState) // serialization_failure
				|| "40P01".equals(sqlState) // deadlock_detected
				|| (sqlState != null && sqlState.startsWith("08")); // connection exception
	}

	/**
	 * Lets the current transaction wait at most {@link #SYSCONFIG_LockTimeoutMillis} for a row lock. Used by the booking
	 * transactions and by the other status changes of the background sweep, so that a lock held long by another
	 * transaction never stalls the SumUp poller.
	 */
	void setLockTimeout()
	{
		final int lockTimeoutMillis = sysConfigBL.getIntValue(SYSCONFIG_LockTimeoutMillis, DEFAULT_LockTimeoutMillis);
		if (lockTimeoutMillis > 0)
		{
			repository.setLockTimeoutForCurrentTrx(Duration.ofMillis(lockTimeoutMillis));
		}
	}

	/**
	 * Step A, under the settlement lock: if the settlement's SumUp transaction is approved, creates the credit-card
	 * payment and sets APPROVED; a charge already refunded at SumUp is never booked.
	 */
	public void bookPaymentIfApproved(@NonNull final POSInvoiceCardSettlementId id)
	{
		trxManager.runInNewTrx(() -> bookPaymentIfApprovedInTrx(id));
	}

	private void bookPaymentIfApprovedInTrx(@NonNull final POSInvoiceCardSettlementId id)
	{
		setLockTimeout();
		final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
		final SumUpTransaction sumUpTrx = findSumUpTransaction(id).orElse(null);
		if (sumUpTrx == null || !sumUpTrx.isCharged())
		{
			return;
		}

		final POSInvoiceCardSettlementStatus status = settlement.getStatus();
		if (sumUpTrx.isRefunded())
		{
			// status REFUNDED, or a refund event on a SUCCESSFUL one (a refund also fires a status event that still says
			// SUCCESSFUL); an abandoned settlement stays as it is
			if (status == POSInvoiceCardSettlementStatus.PENDING || status == POSInvoiceCardSettlementStatus.UNKNOWN)
			{
				repository.save(settlement.toBuilder()
						.status(POSInvoiceCardSettlementStatus.FAILED)
						.errorMsg(ERRORMSG_RefundedAtSumUp)
						.sumUpTransactionId(getLinkedOrResolvedSumUpTransactionId(settlement))
						.build());
			}
			return;
		}

		if (STATUSES_BeforePayment.contains(status))
		{
			final PaymentId paymentId = createPayment(settlement);
			repository.save(settlement.toBuilder()
					.status(POSInvoiceCardSettlementStatus.APPROVED) // IsAbandoned is kept
					.paymentId(paymentId)
					.sumUpTransactionId(getLinkedOrResolvedSumUpTransactionId(settlement))
					.build());
		}
		else if ((status == POSInvoiceCardSettlementStatus.FAILED || status == POSInvoiceCardSettlementStatus.CANCELLED)
				&& !ERRORMSG_ApprovedAfterRejected.equals(settlement.getErrorMsg()))
		{
			// the reader took money for a settlement metasfresh had already given up on: back office must reconcile it;
			// recorded once, however often the approval is reported
			repository.save(settlement.toBuilder().errorMsg(ERRORMSG_ApprovedAfterRejected).build());
			errorManager.createIssue(new AdempiereException(ERRORMSG_ApprovedAfterRejected)
					.setParameter("status", status)
					.setParameter("previousErrorMsg", settlement.getErrorMsg())
					.setParameter("SumUpClientTransactionId", sumUpTrx.getClientTransactionId())
					.setParameter("amount", sumUpTrx.getAmount())
					.setRecord(id.toRecordRef()));
		}
	}

	@NonNull
	private PaymentId createPayment(@NonNull final POSInvoiceCardSettlement settlement)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(settlement.getPosTerminalId());
		final Money amount = settlement.getAmount();
		final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
				.adOrgId(terminal.getOrgId())
				.orgBankAccountId(terminal.getCashbookId())
				.bpartnerId(settlement.getBpartnerId())
				.payAmt(amount.toBigDecimal())
				.currencyId(amount.getCurrencyId())
				.tenderType(TenderType.CreditCard)
				.dateTrx(SystemTime.asInstant())
				.createAndProcess();
		return PaymentId.ofRepoId(payment.getC_Payment_ID());
	}

	/**
	 * Step B, under the invoice lock and then the settlement lock, only while APPROVED, all in one transaction:
	 * allocates the payment to the invoice up to its open amount (nothing if the invoice is no longer open), adds the
	 * card line to the terminal's open cash journal, and sets SUCCESSFUL, or CREDIT_REFUND_PENDING (plus a
	 * notification to the back office) if part of the payment stays unallocated. Without an open cash journal it only
	 * records that (see {@link #recordNoOpenCashJournal}) and the settlement stays APPROVED. Throws, rolling back, if
	 * the invoice is still open but the payment could not be allocated.
	 * <p>
	 * The charge may have been refunded at SumUp since step A created the payment (e.g. while the settlement waited for a
	 * journal), and the poller does not refresh a successful transaction. So step B allocates only on a refund state that
	 * a successful SumUp lookup confirmed ({@link #checkRefundStateBeforeAllocating}):
	 * <ul>
	 * <li>if the caller just refreshed the transaction ({@code isSumUpTransactionRefreshed}: a status event, the till's
	 * check, back office's retry) and that lookup succeeded, step B does not ask SumUp again;</li>
	 * <li>otherwise (also after a forced status event whose lookup failed) it asks SumUp itself: one lookup, outside any
	 * transaction and without holding a lock, and only when a cash journal is open, i.e. when the booking can complete
	 * now (never on each retry while the settlement waits for a journal). The SumUp client's timeouts apply to it.</li>
	 * </ul>
	 * Under the locks: a refund known locally → BOOKING_ERROR, nothing allocated or journaled
	 * ({@link #recordRefundedAfterPaymentCreated}), checked before the journal, so a refund known already is reported also
	 * while no journal is open; no open journal → waits ({@link #recordNoOpenCashJournal}); the refund state not
	 * confirmed because SumUp could not be asked → nothing allocated or journaled, the settlement stays APPROVED with
	 * {@link #ERRORMSG_SumUpNotReachable} ({@link #recordSumUpNotReachable}); the next sweep cycle asks again.
	 */
	public void completeBooking(@NonNull final POSInvoiceCardSettlementId id, final boolean isSumUpTransactionRefreshed)
	{
		// the SumUp lookup below must not run inside a transaction (it would hold its locks while waiting for SumUp)
		trxManager.assertThreadInheritedTrxNotExists();

		// cheap pre-check without locks; re-checked below under the locks
		final POSInvoiceCardSettlement settlement = repository.getById(id);
		if (settlement.getStatus() != POSInvoiceCardSettlementStatus.APPROVED)
		{
			return;
		}

		final RefundState refundState = checkRefundStateBeforeAllocating(settlement, isSumUpTransactionRefreshed);

		trxManager.runInNewTrx(() -> completeBookingInTrx(id, refundState));
	}

	/**
	 * What step B knows about a refund of the charge, before it takes its locks.
	 */
	private enum RefundState
	{
		/**
		 * a successful SumUp lookup gave the transaction as stored (or there is no SumUp transaction to ask about)
		 */
		CONFIRMED,
		/**
		 * SumUp could not be asked: the stored state may miss a refund
		 */
		SUMUP_NOT_REACHABLE,
		/**
		 * not asked, because no journal was open (the booking would only wait); if a journal was opened meanwhile, the
		 * next booking attempt asks
		 */
		NOT_ASKED
	}

	@NonNull
	private RefundState checkRefundStateBeforeAllocating(@NonNull final POSInvoiceCardSettlement settlement, final boolean isSumUpTransactionRefreshed)
	{
		final SumUpTransaction sumUpTrx = findSumUpTransaction(settlement.getId()).orElse(null);
		if (sumUpTrx == null)
		{
			return RefundState.CONFIRMED;
		}
		if (isSumUpTransactionRefreshed && isLastSyncOk(sumUpTrx))
		{
			return RefundState.CONFIRMED;
		}
		if (!posTerminalService.getPOSTerminalById(settlement.getPosTerminalId()).isCashJournalOpen())
		{
			return RefundState.NOT_ASKED;
		}

		return refreshSumUpTransactionBeforeAllocating(settlement, sumUpTrx)
				? RefundState.CONFIRMED
				: RefundState.SUMUP_NOT_REACHABLE;
	}

	/**
	 * Step B's refund check: refreshes the settlement's SumUp transaction from SumUp, in the settlement's client / org
	 * (so that the AD_Issue and API log of the lookup land there). A failed lookup is recorded on the transaction by
	 * {@link SumUpService#updateTransactionFromRemote}; a failure around it is only logged. (The till's check and the
	 * release use {@code POSInvoiceCardSettlementService.refreshSumUpTransactionFromRemote}.)
	 * <p>
	 * While SumUp is unreachable the sweep repeats this lookup in every cycle. So back office gets one AD_Issue per
	 * outage: a failed lookup is recorded as AD_Issue only when the settlement does not wait with
	 * {@link #ERRORMSG_SumUpNotReachable} yet (the failure that starts the outage); later consecutive failures are only
	 * logged.
	 *
	 * @return whether SumUp was asked successfully
	 */
	private boolean refreshSumUpTransactionBeforeAllocating(@NonNull final POSInvoiceCardSettlement settlement, @NonNull final SumUpTransaction sumUpTrx)
	{
		final boolean isCreateIssueOnError = !Objects.equals(settlement.getErrorMsg(), ERRORMSG_SumUpNotReachable);
		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(settlement.getClientAndOrgId()))
		{
			sumUpService.updateTransactionFromRemote(sumUpTrx.getClientTransactionId(), isCreateIssueOnError);
		}
		catch (final Exception ex)
		{
			logger.warn("Could not refresh the SumUp transaction of card settlement {} before completing its booking; nothing is allocated", settlement.getId(), ex);
			return false;
		}

		return findSumUpTransaction(settlement.getId())
				.map(POSInvoiceCardSettlementBooking::isLastSyncOk)
				.orElse(false);
	}

	/**
	 * @return whether the transaction's last SumUp lookup succeeded (or it was never looked up after the checkout)
	 */
	static boolean isLastSyncOk(@NonNull final SumUpTransaction sumUpTrx)
	{
		final SumUpTransaction.LastSync lastSync = sumUpTrx.getLastSync();
		return lastSync == null || lastSync.getStatus() != SumUpTransaction.LastSyncStatus.Error;
	}

	private void completeBookingInTrx(@NonNull final POSInvoiceCardSettlementId id, @NonNull final RefundState refundState)
	{
		setLockTimeout();
		// the settlement's invoice never changes, so it can be read before taking the locks
		final I_C_Invoice invoice = invoiceSettlementService.lockInvoice(repository.getById(id).getInvoiceId());
		final POSInvoiceCardSettlement settlement = repository.getByIdForUpdate(id);
		if (settlement.getStatus() != POSInvoiceCardSettlementStatus.APPROVED)
		{
			return;
		}

		final PaymentId paymentId = settlement.getPaymentId();
		if (paymentId == null)
		{
			throw new AdempiereException("APPROVED card settlement without C_Payment_ID").setRecord(id.toRecordRef());
		}

		if (findSumUpTransaction(id).map(SumUpTransaction::isRefunded).orElse(false))
		{
			recordRefundedAfterPaymentCreated(settlement);
			return;
		}

		final POSTerminal terminal = posTerminalService.getPOSTerminalById(settlement.getPosTerminalId());
		if (!terminal.isCashJournalOpen())
		{
			recordNoOpenCashJournal(settlement);
			return;
		}

		if (refundState == RefundState.SUMUP_NOT_REACHABLE)
		{
			recordSumUpNotReachable(settlement);
			return;
		}
		if (refundState == RefundState.NOT_ASKED)
		{
			// a journal was opened after the pre-check: the next booking attempt asks SumUp first
			return;
		}

		final Money amount = settlement.getAmount();

		// an invoice that is no longer open (paid or reversed meanwhile) takes nothing; one that is still open takes the
		// payment up to its open amount. Whatever stays unallocated is customer credit that back office has to refund.
		final boolean isInvoiceOpen = invoiceSettlementService.isStillOpen(invoice);
		if (isInvoiceOpen)
		{
			invoiceSettlementService.allocateUpToOpenAmt(invoice, paymentBL.getById(paymentId));
		}
		final boolean isCreditRefundPending = amount.subtract(getAllocatedAmount(settlement)).signum() > 0;

		if (isInvoiceOpen && isCreditRefundPending && invoiceSettlementService.isStillOpen(invoice))
		{
			// the allocation silently did nothing (e.g. the payment was allocated elsewhere meanwhile): the invoice is
			// still open and the payment is not used up, so this is no customer credit but a booking that did not work
			throw new AdempiereException("Card payment could not be allocated to the invoice that is still open")
					.setParameter("openAmt", invoiceSettlementService.getOpenAmt(invoice))
					.setParameter("amount", amount)
					.setParameter("allocatedAmount", getAllocatedAmount(settlement))
					.setRecord(id.toRecordRef());
		}

		final String description = invoiceSettlementService.getJournalDescription(MSG_JournalDescription, invoice);
		posCashJournalService.changeJournalById(
				terminal.getCashJournalIdNotNull(),
				cashJournal -> cashJournal.addCardSettlementPayment(amount, settlement.getCashierId(), id, description));

		repository.save(settlement.toBuilder()
				.status(isCreditRefundPending ? POSInvoiceCardSettlementStatus.CREDIT_REFUND_PENDING : POSInvoiceCardSettlementStatus.SUCCESSFUL)
				.errorMsg(null)
				.build());

		if (isCreditRefundPending)
		{
			notifyCreditRefundPending(settlement, paymentId, invoice);
		}
	}

	/**
	 * The terminal has no open cash journal (closed after the card was charged): the settlement stays APPROVED, and
	 * the next booking attempt after a journal was opened completes it. Back office gets one AD_Issue: it is recorded
	 * only when the settlement's {@code ErrorMsg} changes to {@link #ERRORMSG_NoOpenCashJournal}, not on every retry.
	 */
	private void recordNoOpenCashJournal(@NonNull final POSInvoiceCardSettlement settlement)
	{
		if (Objects.equals(settlement.getErrorMsg(), ERRORMSG_NoOpenCashJournal))
		{
			return;
		}

		repository.save(settlement.toBuilder().errorMsg(ERRORMSG_NoOpenCashJournal).build());
		errorManager.createIssue(new AdempiereException(MSG_CashJournalNotOpen)
				.setParameter("C_POS_ID", settlement.getPosTerminalId().getRepoId())
				.setRecord(settlement.getId().toRecordRef()));
	}

	/**
	 * SumUp could not be asked whether the charge was refunded since the payment was created: nothing is allocated or
	 * journaled, the settlement stays APPROVED (still blocking the invoice) with {@link #ERRORMSG_SumUpNotReachable};
	 * not counted as a booking attempt (an outage must not end as a booking error), and no AD_Issue here: the failed
	 * lookup is recorded on the SumUp transaction, and the lookup that started the outage also as AD_Issue (see
	 * {@link #refreshSumUpTransactionBeforeAllocating}). The next sweep cycle, the till's check or back office's retry
	 * asks again.
	 */
	private void recordSumUpNotReachable(@NonNull final POSInvoiceCardSettlement settlement)
	{
		if (Objects.equals(settlement.getErrorMsg(), ERRORMSG_SumUpNotReachable))
		{
			return;
		}
		repository.save(settlement.toBuilder().errorMsg(ERRORMSG_SumUpNotReachable).build());
	}

	/**
	 * SumUp reports the charge as refunded after step A created the payment: nothing is allocated or journaled, the
	 * settlement is a BOOKING_ERROR ({@link #ERRORMSG_RefundedAtSumUp}, still blocking the invoice unless released as
	 * abandoned) with one AD_Issue for back office. The payment is kept as it is, unallocated: whether to reverse it or to
	 * record the refund against it is back office's decision (it may also have refunded only a part).
	 */
	private void recordRefundedAfterPaymentCreated(@NonNull final POSInvoiceCardSettlement settlement)
	{
		repository.save(settlement.toBuilder()
				.status(POSInvoiceCardSettlementStatus.BOOKING_ERROR)
				.errorMsg(ERRORMSG_RefundedAtSumUp)
				.sumUpTransactionId(getLinkedOrResolvedSumUpTransactionId(settlement))
				.build());
		errorManager.createIssue(new AdempiereException("SumUp reports the card charge as refunded, but its card payment was already created; nothing was allocated or journaled, the card payment is kept unallocated. To close this: reverse the card payment, then mark the card settlement resolved")
				.setParameter("C_Payment_ID", settlement.getPaymentId())
				.setRecord(settlement.getId().toRecordRef()));
	}

	/**
	 * Tells the back office, after the booking transaction committed, that a card payment waits to be refunded.
	 */
	private void notifyCreditRefundPending(
			@NonNull final POSInvoiceCardSettlement settlement,
			@NonNull final PaymentId paymentId,
			@NonNull final I_C_Invoice invoice)
	{
		notificationBL.sendAfterCommit(UserNotificationRequest.builder()
				.notificationGroupName(NOTIFICATION_GROUP_NAME)
				.recipient(Recipient.allRolesContainingGroup(NOTIFICATION_GROUP_NAME))
				.contentADMessage(MSG_CreditRefundPending)
				.contentADMessageParam(TableRecordReference.of(I_C_Payment.Table_Name, paymentId.getRepoId()))
				.contentADMessageParam(TableRecordReference.of(I_C_Invoice.Table_Name, invoice.getC_Invoice_ID()))
				.targetAction(UserNotificationRequest.TargetRecordAction.of(settlement.getId().toRecordRef()))
				.build());
	}

	/**
	 * @return the part of the settlement's card payment that is allocated to the settlement's invoice; zero while the
	 * settlement has no payment
	 */
	@NonNull
	Money getAllocatedAmount(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return invoiceSettlementService.getAllocatedAmt(settlement.getInvoiceId(), settlement.getPaymentId(), settlement.getAmount().getCurrencyId());
	}

	/**
	 * @return the settlement's SumUp transaction as stored locally, if the checkout created one
	 */
	@NonNull
	Optional<SumUpTransaction> findSumUpTransaction(@NonNull final POSInvoiceCardSettlementId id)
	{
		final SumUpTransactionId sumUpTransactionId = resolveSumUpTransactionId(id);
		if (sumUpTransactionId == null)
		{
			return Optional.empty();
		}
		return sumUpTransactionRepository.stream(SumUpTransactionQuery.ofLocalId(sumUpTransactionId)).findFirst();
	}

	/**
	 * @return the SumUp transaction the settlement is already linked to, else the one that references it (if any)
	 */
	@Nullable
	SumUpTransactionId getLinkedOrResolvedSumUpTransactionId(@NonNull final POSInvoiceCardSettlement settlement)
	{
		return settlement.getSumUpTransactionId() != null
				? settlement.getSumUpTransactionId()
				: resolveSumUpTransactionId(settlement.getId());
	}

	/**
	 * Resolves the settlement's SumUp transaction via the back-reference {@code SUMUP_Transaction.C_POS_InvoiceCardSettlement_ID},
	 * which the checkout sets when it saves the transaction.
	 */
	@Nullable
	private SumUpTransactionId resolveSumUpTransactionId(@NonNull final POSInvoiceCardSettlementId id)
	{
		return sumUpTransactionRepository.findIdByPOSRef(SumUpPOSRef.builder()
				.posInvoiceCardSettlementId(id.getRepoId())
				.build());
	}
}
