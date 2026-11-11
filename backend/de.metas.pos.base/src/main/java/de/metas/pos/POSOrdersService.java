package de.metas.pos;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpContext;
import de.metas.payment.sumup.SumUpImportResult;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.tax.api.ITaxDAO;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class POSOrdersService
{
	@NonNull private static final Logger logger = LogManager.getLogger(POSOrdersService.class);
	private static final AdMessageKey MSG_LateApprovalNotBooked = AdMessageKey.of("de.metas.pos.POSPayment.LateApprovalNotBooked");
	private static final AdMessageKey MSG_CardResultNotStored = AdMessageKey.of("de.metas.pos.POSPayment.CardResultNotStored");
	private static final AdMessageKey MSG_SumUpLookupFailed = AdMessageKey.of("de.metas.pos.POSPayment.SumUpLookupFailed");
	private static final AdMessageKey MSG_ReleaseOnlyIfResultUnknown = AdMessageKey.of("de.metas.pos.POSPayment.ReleaseOnlyIfResultUnknown");
	private static final AdMessageKey MSG_ReleaseNotYetAllowed = AdMessageKey.of("de.metas.pos.POSPayment.ReleaseNotYetAllowed");
	private static final AdMessageKey MSG_ReleaseRefusedApproved = AdMessageKey.of("de.metas.pos.POSPayment.ReleaseRefusedApproved");
	private static final AdMessageKey MSG_ReleasedAsNotCharged = AdMessageKey.of("de.metas.pos.POSPayment.ReleasedAsNotCharged");
	/**
	 * A PENDING card payment whose checkout started longer ago than this without SumUp's answer stored is an orphan: the
	 * send is bounded by the SumUp client's timeouts (2 x 60 s), so its checkout stopped (see {@link #markCardPaymentUnknownIfOrphan}).
	 */
	private static final Duration CARD_CHECKOUT_ORPHAN_AGE = Duration.ofSeconds(150);
	/** waits between the attempts of storing a card checkout's answer; one attempt more than waits */
	private static final ImmutableList<Duration> CARD_SEND_RESULT_RETRY_BACKOFFS = ImmutableList.of(Duration.ofMillis(200), Duration.ofSeconds(1));
	private static final int CARD_SEND_RESULT_MAX_ATTEMPTS = CARD_SEND_RESULT_RETRY_BACKOFFS.size() + 1;
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSOrdersRepository ordersRepository;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final POSOrderProcessingServices posOrderProcessingServices;
	@NonNull private final SumUpService sumUpService;
	@NonNull private final POSCardReleaseWait cardReleaseWait;
	@NonNull private final POSCardReaderCheckoutTerminator cardReaderCheckoutTerminator;
	@NonNull private final Sleeper sleeper;

	/**
	 * Waits between the attempts of a retried write; a test can record the waits instead.
	 */
	@FunctionalInterface
	interface Sleeper
	{
		void sleep(@NonNull Duration duration);
	}

	@Autowired
	public POSOrdersService(
			@NonNull final POSTerminalService posTerminalService,
			@NonNull final POSOrdersRepository ordersRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final POSOrderProcessingServices posOrderProcessingServices,
			@NonNull final SumUpService sumUpService,
			@NonNull final POSCardReleaseWait cardReleaseWait,
			@NonNull final POSCardReaderCheckoutTerminator cardReaderCheckoutTerminator)
	{
		this(posTerminalService, ordersRepository, currencyRepository, posOrderProcessingServices, sumUpService, cardReleaseWait, cardReaderCheckoutTerminator, POSOrdersService::sleepUninterruptibly);
	}

	@VisibleForTesting
	POSOrdersService(
			@NonNull final POSTerminalService posTerminalService,
			@NonNull final POSOrdersRepository ordersRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final POSOrderProcessingServices posOrderProcessingServices,
			@NonNull final SumUpService sumUpService,
			@NonNull final POSCardReleaseWait cardReleaseWait,
			@NonNull final POSCardReaderCheckoutTerminator cardReaderCheckoutTerminator,
			@NonNull final Sleeper sleeper)
	{
		this.posTerminalService = posTerminalService;
		this.ordersRepository = ordersRepository;
		this.currencyRepository = currencyRepository;
		this.posOrderProcessingServices = posOrderProcessingServices;
		this.sumUpService = sumUpService;
		this.cardReleaseWait = cardReleaseWait;
		this.cardReaderCheckoutTerminator = cardReaderCheckoutTerminator;
		this.sleeper = sleeper;
	}

	private static void sleepUninterruptibly(@NonNull final Duration duration)
	{
		try
		{
			Thread.sleep(duration.toMillis());
		}
		catch (final InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}

	public List<POSOrder> list(@NonNull final POSOrderQuery query)
	{
		return ordersRepository.list(query);
	}

	public Optional<POSOrder> firstOnly(@NonNull final POSOrderQuery query)
	{
		return ordersRepository.firstOnly(query);
	}

	public POSOrder changeStatusTo(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId externalId,
			@NonNull final POSOrderStatus nextStatus,
			@NonNull final UserId userId)
	{
		// order lock first (a completion then updates the cash journal), as every completer does, see tryCompleteNoFail
		return ordersRepository.updateByExternalIdForUpdate(externalId, order -> {
			assertCanEdit(posTerminalId, order, userId);
			order.changeStatusTo(nextStatus, posOrderProcessingServices);
		});
	}

	public void updatePaymentStatusFromRemoteAndTryCompleteOrder(
			@NonNull final POSOrderAndPaymentId posOrderAndPaymentId,
			@NonNull final POSPaymentProcessResponse response)
	{
		// NOTE: 
		// Make sure we are running out of transaction. 
		// Keep [POS payment update] and [POS order completing] in 2 separate transactions
		// so it might be that updating can succeed (and it's saved) but order processing not.
		trxManager.assertThreadInheritedTrxNotExists();

		final AtomicReference<POSOrder> orderRef = new AtomicReference<>();
		final AtomicBoolean lateApprovalNotBooked = new AtomicBoolean(false);
		ordersRepository.updatePaymentByIdForUpdate(posOrderAndPaymentId, (order, payment) -> {
			final POSPaymentRemoteUpdateResult result = payment.changingStatusFromRemote(response);
			orderRef.set(order);
			lateApprovalNotBooked.set(result.isLateApprovalNotBooked());
			return result.getPayment();
		});

		if (lateApprovalNotBooked.get())
		{
			recordLateApprovalNotBooked(orderRef.get(), posOrderAndPaymentId, response);
		}

		tryCompleteNoFail(posOrderAndPaymentId.getOrderId());
	}

	/**
	 * Written after the payment update committed, in its own transaction: a late SumUp approval of a deleted or released card payment
	 * is not booked; the issue (on the payment) tells the back office to review it. A failure here is only logged.
	 */
	private void recordLateApprovalNotBooked(
			@NonNull final POSOrder order,
			@NonNull final POSOrderAndPaymentId posOrderAndPaymentId,
			@NonNull final POSPaymentProcessResponse response)
	{
		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(order.getClientAndOrgId()))
		{
			errorManager.createIssue(new AdempiereException(MSG_LateApprovalNotBooked)
					.setParameter("posOrderExternalId", order.getExternalId().getAsString())
					.setParameter("sumUpClientTransactionId", response.getClientTransactionId())
					.setRecord(TableRecordReference.of(I_C_POS_Payment.Table_Name, posOrderAndPaymentId.getPaymentId())));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed recording the late approval of {} as AD_Issue. Ignored.", posOrderAndPaymentId, ex);
		}
	}

	private void tryCompleteNoFail(@NonNull final POSOrderId posOrderId)
	{
		try
		{
			// under the order lock: the card checkout and a SumUp status event may both try to complete the order at the same time.
			// Every completer locks the order first, then (completing) the cash journal: changeStatusTo and the cash checkout, too.
			ordersRepository.updateByIdForUpdate(posOrderId, this::tryCompleteNoFail);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed completing order {}. Ignored.", posOrderId, ex);
		}
	}

	private void tryCompleteNoFail(@NonNull final POSOrder order)
	{
		final BooleanWithReason canComplete = order.checkCanTryTransitionToCompleted();
		if (canComplete.isTrue())
		{
			order.changeStatusTo(POSOrderStatus.Completed, posOrderProcessingServices);
		}
		else
		{
			logger.debug("Skip completing {} because {}", order.getLocalId(), canComplete.getReason());
		}
	}

	private void assertCanEdit(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrder order,
			@NonNull final UserId userId)
	{
		if (!POSTerminalId.equals(order.getPosTerminalId(), posTerminalId))
		{
			throw new AdempiereException("Edit not allowed")
					.setParameter("detail", "POS Order is not owned by " + posTerminalId);
		}
		if (!UserId.equals(order.getCashierId(), userId))
		{
			throw new AdempiereException("Edit not allowed")
					.setParameter("detail", "Not owned by cashier " + userId);
		}
	}

	public POSOrder updateOrderFromRemote(
			@NonNull final RemotePOSOrder remoteOrder,
			@NonNull final UserId userId)
	{
		final POSTerminalId posTerminalId = remoteOrder.getPosTerminalId();
		return ordersRepository.createOrUpdateByExternalId(
				remoteOrder.getUuid(),
				externalId -> newPOSOrder(posTerminalId, externalId, userId),
				order -> {
					assertCanEdit(posTerminalId, order, userId);
					updateOrderFromRemote(order, remoteOrder);
				});
	}

	private void updateOrderFromRemote(final POSOrder order, final @NonNull RemotePOSOrder remoteOrder)
	{
		POSOrderUpdateFromRemoteCommand.builder()
				.taxDAO(taxDAO)
				.order(order)
				.remoteOrder(remoteOrder)
				.currencyPrecision(currencyRepository.getStdPrecision(order.getCurrencyId()))
				.build()
				.execute();
	}

	private POSOrder newPOSOrder(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId externalId,
			@NonNull final UserId userId)
	{
		final POSTerminal posTerminal = posTerminalService.getPOSTerminalById(posTerminalId);

		return POSOrder.builder()
				.externalId(externalId)
				.status(POSOrderStatus.Drafted)
				.salesOrderDocTypeId(posTerminal.getSalesOrderDocTypeId())
				.pricingSystemAndListId(posTerminal.getPricingSystemAndListId())
				.cashbookId(posTerminal.getCashbookId())
				.cashierId(userId)
				.date(SystemTime.asInstant())
				.shipToCustomerAndLocationId(posTerminal.getWalkInCustomerShipToLocationId())
				.shipFrom(posTerminal.getShipFrom())
				.isTaxIncluded(posTerminal.isTaxIncluded())
				.currencyId(posTerminal.getCurrencyId())
				.posTerminalId(posTerminal.getId())
				.build();
	}

	/**
	 * Checks out the given payment of the order. A cash payment is checked out in one transaction. A card payment is
	 * checked out in three steps, so that the card charge is sent outside any transaction and without holding any lock:
	 * <ol>
	 * <li>{@link #startCardCheckout}: the payment is saved as PENDING (own transaction);</li>
	 * <li>the charge is sent to the card reader (no transaction);</li>
	 * <li>SumUp's answer is applied to the payment under the order lock (own transaction, retried while the database
	 * fails), see {@link #applyCardSendResult}; then the order is completed if it can be.</li>
	 * </ol>
	 *
	 * @return the order as saved; for a card payment whose answer could not be stored, the order as saved by step 1
	 */
	public POSOrder checkoutPayment(@NonNull final POSPaymentCheckoutRequest request)
	{
		if (isCardPayment(request))
		{
			return checkoutCardPayment(request);
		}

		final POSTerminalId posTerminalId = request.getPosTerminalId();
		final POSOrderExternalId posOrderExternalId = request.getPosOrderExternalId();
		final POSPaymentExternalId posPaymentExternalId = request.getPosPaymentExternalId();
		final UserId userId = request.getUserId();

		// order lock first (before the completion updates the cash journal), as every completer does, see tryCompleteNoFail
		return ordersRepository.updateByExternalIdForUpdate(posOrderExternalId, posOrder -> {
			assertCanEdit(posTerminalId, posOrder, userId);
			posOrder.assertWaitingForPayment();

			posOrder.updatePaymentByExternalId(posPaymentExternalId, posPayment -> checkoutCashPayment(request, posPayment));
			tryCompleteNoFail(posOrder);
		});
	}

	private POSPayment checkoutCashPayment(@NonNull final POSPaymentCheckoutRequest request, @NonNull final POSPayment posPaymentToProcess)
	{
		POSPayment posPayment = posPaymentToProcess;
		if (posPayment.getPaymentMethod().isCash() && request.getCashTenderedAmount() != null)
		{
			posPayment = posPayment.withCashTenderedAmount(request.getCashTenderedAmount());
		}

		return posOrderProcessingServices.processPOSPayment(posPayment);
	}

	/**
	 * @return true if the request is for an existing card payment of an order of the request's terminal and cashier; otherwise
	 * the one-transaction checkout runs and reports what is wrong with the request, as before
	 */
	private boolean isCardPayment(@NonNull final POSPaymentCheckoutRequest request)
	{
		return ordersRepository.firstOnly(POSOrderQuery.builder()
						.posTerminalId(request.getPosTerminalId())
						.cashierId(request.getUserId())
						.onlyOrderExternalIds(ImmutableSet.of(request.getPosOrderExternalId()))
						.build())
				.flatMap(order -> findPaymentByExternalId(order, request.getPosPaymentExternalId()))
				.map(payment -> payment.getPaymentMethod().isCard())
				.orElse(false);
	}

	private static Optional<POSPayment> findPaymentByExternalId(@NonNull final POSOrder order, @NonNull final POSPaymentExternalId externalId)
	{
		return order.getAllPayments()
				.stream()
				.filter(payment -> POSPaymentExternalId.equals(payment.getExternalId(), externalId))
				.findFirst();
	}

	private POSOrder checkoutCardPayment(@NonNull final POSPaymentCheckoutRequest request)
	{
		// the charge is sent outside any transaction, see the method doc
		trxManager.assertThreadInheritedTrxNotExists();

		// step 1
		final POSOrder startedOrder = startCardCheckout(request);
		final POSPayment startedPayment = findPaymentByExternalId(startedOrder, request.getPosPaymentExternalId())
				.orElseThrow(() -> new AdempiereException("No payment found for " + request.getPosPaymentExternalId()));
		final POSOrderAndPaymentId orderAndPaymentId = POSOrderAndPaymentId.of(startedOrder.getLocalIdNotNull(), startedPayment.getLocalIdNotNull());

		// step 2
		final POSPaymentProcessResponse response = posOrderProcessingServices.sendCardPayment(startedPayment, startedOrder);

		// step 3
		final boolean resultStored = applyCardSendResult(startedOrder, orderAndPaymentId, response);
		if (resultStored)
		{
			tryCompleteNoFail(orderAndPaymentId.getOrderId());
		}

		// the order as it is now; if step 3 could not be stored, its card payment is still PENDING, as saved by step 1
		return trxManager.callInNewTrx(() -> ordersRepository.getById(orderAndPaymentId.getOrderId()));
	}

	/**
	 * Step 1 of a card checkout: in its own transaction, locks the order, checks that the cashier may edit it, that it waits
	 * for payment and that the card payment can be checked out, then saves the payment as PENDING for a new attempt
	 * (checkout started now, no SumUp transaction yet), and commits. Nothing is sent.
	 */
	public POSOrder startCardCheckout(@NonNull final POSPaymentCheckoutRequest request)
	{
		final POSTerminalId posTerminalId = request.getPosTerminalId();
		final UserId userId = request.getUserId();

		return trxManager.callInNewTrx(() -> ordersRepository.updateByExternalIdForUpdate(request.getPosOrderExternalId(), posOrder -> {
			assertCanEdit(posTerminalId, posOrder, userId);
			posOrder.assertWaitingForPayment();
			posOrderProcessingServices.assertCardPaymentCanBeSent(posOrder);

			posOrder.updatePaymentByExternalId(request.getPosPaymentExternalId(), posPayment -> {
				POSPayment payment = posPayment;
				if (request.getCardPayAmount() != null)
				{
					payment = payment.withCardPayAmount(request.getCardPayAmount());
				}
				return payment.changingStatusToCardCheckoutStarted(SystemTime.asInstant());
			});
		}));
	}

	/**
	 * Step 3 of a card checkout: applies SumUp's answer to the card payment, in a new transaction under the order lock. While
	 * the database fails, it is tried {@link #CARD_SEND_RESULT_MAX_ATTEMPTS} times in all, each time re-locking and re-reading the
	 * order, waiting {@link #CARD_SEND_RESULT_RETRY_BACKOFFS} in between. If all attempts fail, the answer is logged at ERROR
	 * and recorded as an AD_Issue on the payment (best effort): the payment stays PENDING, without SumUp's id, as saved by step 1.
	 *
	 * @return true if the answer was stored
	 */
	private boolean applyCardSendResult(
			@NonNull final POSOrder order,
			@NonNull final POSOrderAndPaymentId orderAndPaymentId,
			@NonNull final POSPaymentProcessResponse response)
	{
		Exception lastFailure = null;
		for (int attempt = 1; attempt <= CARD_SEND_RESULT_MAX_ATTEMPTS; attempt++)
		{
			if (attempt > 1)
			{
				sleeper.sleep(CARD_SEND_RESULT_RETRY_BACKOFFS.get(attempt - 2));
			}

			try
			{
				trxManager.runInNewTrx(() -> ordersRepository.updatePaymentByIdForUpdate(
						orderAndPaymentId,
						(lockedOrder, payment) -> payment.changingStatusFromSendResult(response)));
				return true;
			}
			catch (final Exception ex)
			{
				if (!isDatabaseFailure(ex))
				{
					throw AdempiereException.wrapIfNeeded(ex);
				}
				logger.warn("Storing the card checkout result of {} failed (attempt {} of {})", orderAndPaymentId, attempt, CARD_SEND_RESULT_MAX_ATTEMPTS, ex);
				lastFailure = ex;
			}
		}

		recordCardResultNotStored(order, orderAndPaymentId, response, lastFailure);
		return false;
	}

	private static boolean isDatabaseFailure(@NonNull final Throwable ex)
	{
		for (Throwable cause = ex; cause != null; cause = cause.getCause())
		{
			if (cause instanceof DBException || cause instanceof SQLException)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * The card checkout's answer could not be stored: logs it at ERROR, then records it as an AD_Issue on the payment, in its own
	 * transaction (it fails too if the database is still down; that is only logged). SumUp's client transaction id, if known,
	 * lets the back office find the charge in the SumUp dashboard.
	 */
	private void recordCardResultNotStored(
			@NonNull final POSOrder order,
			@NonNull final POSOrderAndPaymentId orderAndPaymentId,
			@NonNull final POSPaymentProcessResponse response,
			@Nullable final Exception cause)
	{
		final String clientTransactionId = response.getClientTransactionId();
		logger.error("The card checkout result could not be stored: posOrderExternalId={}, {}, sumUpClientTransactionId={}, status={}, indeterminate={}",
				order.getExternalId().getAsString(), orderAndPaymentId, clientTransactionId, response.getStatus(), response.isIndeterminate(), cause);

		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(order.getClientAndOrgId()))
		{
			trxManager.runInNewTrx(() -> errorManager.createIssue(new AdempiereException(cause, MSG_CardResultNotStored, clientTransactionId != null ? clientTransactionId : "")
					.setParameter("posOrderExternalId", order.getExternalId().getAsString())
					.setParameter("sumUpClientTransactionId", clientTransactionId)
					.setRecord(TableRecordReference.of(I_C_POS_Payment.Table_Name, orderAndPaymentId.getPaymentId()))));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed recording the unstored card checkout result of {} as AD_Issue. Ignored.", orderAndPaymentId, ex);
		}
	}

	/**
	 * The till's check of a card payment whose result is unknown: asks SumUp for the payment's own SumUp transaction (the
	 * current attempt's, named by the payment's SumUp client transaction id; never looked up by the payment's POS reference),
	 * outside any transaction and without a lock held:
	 * <ul>
	 * <li>metasfresh has that transaction → it is refreshed from SumUp ({@link SumUpService#updateTransactionFromRemote}); its
	 * status event, if the status changed, updates the payment asynchronously;</li>
	 * <li>metasfresh does not have it yet (saving it failed after SumUp accepted the checkout) → it is imported from SumUp
	 * ({@link SumUpService#importAcceptedCheckout}), which fires its status event;</li>
	 * <li>the payment names no SumUp transaction (timeout, network error, SumUp 5xx, or a stopped checkout) → nothing to ask.</li>
	 * </ul>
	 * Any other payment (not a card payment, or its result is known) is not looked up. A failed lookup leaves the payment as
	 * it was, sets {@code checkFailed}, and is reported once per outage (see {@link #recordCardLookupOutcome}). Any other failure
	 * of the check (e.g. SumUp's answer cannot be read) also sets {@code checkFailed}, but is only logged.
	 *
	 * @return the order as it is after the check (a status event of the lookup may still be under way)
	 */
	public POSCardPaymentCheckResult checkCardPayment(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		// the SumUp lookup must run outside any transaction, see the method doc
		trxManager.assertThreadInheritedTrxNotExists();

		final POSOrderId orderId = ordersRepository.getIdByExternalId(posOrderExternalId);
		final POSOrder order = ordersRepository.getById(orderId);
		assertCanEdit(posTerminalId, order, userId);
		final POSPayment payment = findPaymentByExternalId(order, posPaymentExternalId)
				.orElseThrow(() -> new AdempiereException("No payment found for " + posPaymentExternalId));

		if (!isUnknownCardPaymentNamingSumUpTransaction(payment))
		{
			return POSCardPaymentCheckResult.builder().order(order).checkFailed(false).build();
		}

		final POSOrderAndPaymentId orderAndPaymentId = POSOrderAndPaymentId.of(orderId, payment.getLocalIdNotNull());
		final Exception lookupFailure;
		try
		{
			lookupFailure = lookUpOwnSumUpTransaction(order, orderAndPaymentId, payment).orElse(null);
		}
		catch (final Exception ex)
		{
			// not a failed SumUp lookup (e.g. SumUp's answer could not be read, or no usable SumUp config): for the cashier,
			// SumUp could not be asked; it is no SumUp outage, so the outage state is left as it is. Only logged, not recorded
			// as AD_Issue: the till may check again and again while the cause persists (the sweep reports it once).
			logger.warn("Checking the card payment {} at SumUp failed", orderAndPaymentId, ex);
			return POSCardPaymentCheckResult.builder()
					.order(ordersRepository.getById(orderId))
					.checkFailed(true)
					.build();
		}
		recordCardLookupOutcome(order, orderAndPaymentId, payment, lookupFailure);

		return POSCardPaymentCheckResult.builder()
				.order(ordersRepository.getById(orderId))
				.checkFailed(lookupFailure != null)
				.build();
	}

	/**
	 * @return the card payments whose SumUp transaction is to be imported by the background sweep, case (a), oldest first:
	 * their result is unknown and they name a SumUp transaction metasfresh does not have (see {@link #importCardPaymentSumUpTransaction})
	 */
	public ImmutableList<POSOrderAndPaymentId> getCardPaymentIdsToImport()
	{
		try (final Stream<POSOrderAndPaymentId> stream = ordersRepository.streamCardPaymentsToImport())
		{
			return stream.collect(ImmutableList.toImmutableList());
		}
	}

	/**
	 * @return the candidates of the background sweep's orphan rule, case (b), oldest first: PENDING card payments naming no
	 * SumUp transaction whose checkout started more than {@link #CARD_CHECKOUT_ORPHAN_AGE} ago (by {@link SystemTime}) and has
	 * no SumUp transaction of its own (see {@link #markCardPaymentUnknownIfOrphan}, which checks again under the order lock)
	 */
	public ImmutableList<POSOrderAndPaymentId> getOrphanCardPaymentIds()
	{
		try (final Stream<POSOrderAndPaymentId> stream = ordersRepository.streamOrphanCardPaymentIds(SystemTime.asInstant().minus(CARD_CHECKOUT_ORPHAN_AGE)))
		{
			return stream.collect(ImmutableList.toImmutableList());
		}
	}

	/**
	 * @return the client and org of the given POS order, as stored (read without a lock)
	 */
	public ClientAndOrgId getClientAndOrgId(@NonNull final POSOrderId posOrderId)
	{
		return ordersRepository.getById(posOrderId).getClientAndOrgId();
	}

	/**
	 * Background sweep, case (a): imports the SumUp transaction of a card payment whose result is unknown and that names a
	 * SumUp transaction metasfresh does not have (SumUp accepted the checkout, but saving its transaction failed). The SumUp
	 * lookup runs outside any transaction and without a lock held; the import fires the transaction's status event, which
	 * updates the payment. A failed lookup is reported once per outage (see {@link #recordCardLookupOutcome}). Does nothing
	 * if the payment no longer qualifies.
	 *
	 * @throws RuntimeException if the import fails other than by a failed SumUp lookup (e.g. SumUp's answer cannot be read)
	 */
	public void importCardPaymentSumUpTransaction(@NonNull final POSOrderAndPaymentId orderAndPaymentId)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final POSOrder order = ordersRepository.getById(orderAndPaymentId.getOrderId());
		final POSPayment payment = order.getPaymentById(orderAndPaymentId.getPaymentId());
		if (!isUnknownCardPaymentNamingSumUpTransaction(payment))
		{
			return;
		}

		final Exception lookupFailure = lookUpOwnSumUpTransaction(order, orderAndPaymentId, payment).orElse(null);
		recordCardLookupOutcome(order, orderAndPaymentId, payment, lookupFailure);
	}

	/**
	 * Background sweep, case (b), the orphan rule, keyed on the payment's current attempt: in its own transaction under the
	 * order lock, a PENDING card payment that names no SumUp transaction, whose checkout started more than
	 * {@link #CARD_CHECKOUT_ORPHAN_AGE} ago (by {@link SystemTime}), and for which no SumUp transaction with its POS reference
	 * was created since that checkout started, becomes UNKNOWN: its checkout stopped (or its answer could not be stored)
	 * before SumUp's result reached it. A SumUp transaction of an earlier attempt of the same payment does not count. Nothing
	 * is sent to SumUp.
	 */
	public void markCardPaymentUnknownIfOrphan(@NonNull final POSOrderAndPaymentId orderAndPaymentId)
	{
		final Instant now = SystemTime.asInstant();
		trxManager.runInNewTrx(() -> ordersRepository.updatePaymentByIdForUpdate(orderAndPaymentId, (order, payment) -> {
			final Instant startedAt = payment.getCardCheckoutStartedAt();
			if (startedAt == null || !startedAt.plus(CARD_CHECKOUT_ORPHAN_AGE).isBefore(now))
			{
				return payment;
			}
			if (sumUpService.existsTransactionByPOSRefCreatedSince(toSumUpPOSRef(orderAndPaymentId), startedAt))
			{
				return payment;
			}
			return payment.changingStatusToUnknownIfOrphan();
		}));
	}

	private static boolean isUnknownCardPaymentNamingSumUpTransaction(@NonNull final POSPayment payment)
	{
		return payment.isUnknownCardPayment() && payment.getSumUpClientTransactionId() != null;
	}

	/**
	 * Asks SumUp for the payment's own SumUp transaction: refreshes it if metasfresh has it, else imports it. Must run outside
	 * any transaction and without a lock held.
	 *
	 * @return the failure of the SumUp lookup, if it failed; empty if SumUp answered (also if SumUp does not know the transaction)
	 */
	private Optional<Exception> lookUpOwnSumUpTransaction(
			@NonNull final POSOrder order,
			@NonNull final POSOrderAndPaymentId orderAndPaymentId,
			@NonNull final POSPayment payment)
	{
		final SumUpClientTransactionId clientTransactionId = SumUpClientTransactionId.ofString(Check.assumeNotNull(payment.getSumUpClientTransactionId(), "sumUpClientTransactionId is set: {}", payment));

		if (sumUpService.findTransactionByClientTransactionId(clientTransactionId).isPresent())
		{
			// a failed lookup is recorded on the transaction (LastSync), not as AD_Issue: this caller reports an outage once
			sumUpService.updateTransactionFromRemote(clientTransactionId, false);
			final SumUpTransaction refreshed = sumUpService.findTransactionByClientTransactionId(clientTransactionId)
					.orElseThrow(() -> new AdempiereException("SumUp transaction disappeared: " + clientTransactionId));
			final SumUpTransaction.LastSync lastSync = refreshed.getLastSync();
			return lastSync != null && lastSync.getStatus() == SumUpTransaction.LastSyncStatus.Error
					? Optional.of(new AdempiereException("SumUp lookup of transaction " + clientTransactionId + " failed"))
					: Optional.empty();
		}

		final SumUpConfigId sumUpConfigId = getSumUpConfigId(order);
		final SumUpImportResult result = sumUpService.importAcceptedCheckout(
				sumUpConfigId,
				clientTransactionId,
				toSumUpPOSRef(orderAndPaymentId),
				sumUpService.getConfig(sumUpConfigId).getDefaultCardReaderExternalIdNotNull(),
				order.getClientAndOrgId());
		return Optional.ofNullable(result.getLookupError());
	}

	private SumUpConfigId getSumUpConfigId(@NonNull final POSOrder order)
	{
		return Check.assumeNotNull(
				posTerminalService.getPOSTerminalById(order.getPosTerminalId()).getPaymentProcessorConfigNotNull().getSumUpConfigId(),
				"the terminal of {} is configured with SumUp", order.getExternalId());
	}

	private static SumUpPOSRef toSumUpPOSRef(@NonNull final POSOrderAndPaymentId orderAndPaymentId)
	{
		return SumUpPOSRef.builder()
				.posOrderId(orderAndPaymentId.getOrderId().getRepoId())
				.posPaymentId(orderAndPaymentId.getPaymentId().getRepoId())
				.build();
	}

	/**
	 * One AD_Issue per SumUp outage, stored on the card payment ({@code CardLookupFailingSince}), shared by the sweep and the
	 * till's check. In its own transaction under the order lock:
	 * <ul>
	 * <li>the lookup failed and the payment (still with an unknown result) was not failing a lookup → it is failing one since
	 * now, and, after commit, one AD_Issue {@code SumUpLookupFailed} is recorded on the payment (a failure to record it is only
	 * logged);</li>
	 * <li>the lookup failed and the payment was failing one already → no AD_Issue;</li>
	 * <li>the lookup succeeded (whether SumUp knew the transaction or not) → the outage ended.</li>
	 * </ul>
	 */
	private void recordCardLookupOutcome(
			@NonNull final POSOrder order,
			@NonNull final POSOrderAndPaymentId orderAndPaymentId,
			@NonNull final POSPayment paymentAsLookedUp,
			@Nullable final Exception lookupFailure)
	{
		if (lookupFailure == null && paymentAsLookedUp.getCardLookupFailingSince() == null)
		{
			// the lookup succeeded and no outage was ongoing (as read before the lookup): nothing to change, so no lock is taken.
			// An outage that a concurrent lookup started meanwhile is ended by the next successful lookup.
			return;
		}

		final Instant now = SystemTime.asInstant();
		final AtomicBoolean outageStarted = new AtomicBoolean(false);
		trxManager.runInNewTrx(() -> ordersRepository.updatePaymentByIdForUpdate(orderAndPaymentId, (lockedOrder, payment) -> {
			if (lookupFailure == null || !payment.isUnknown())
			{
				return payment.withCardLookupSucceeded();
			}
			outageStarted.set(payment.getCardLookupFailingSince() == null);
			return payment.withCardLookupFailed(now);
		}));

		if (outageStarted.get())
		{
			recordSumUpLookupFailed(order, orderAndPaymentId, Check.assumeNotNull(lookupFailure, "lookupFailure is set"));
		}
	}

	private void recordSumUpLookupFailed(
			@NonNull final POSOrder order,
			@NonNull final POSOrderAndPaymentId orderAndPaymentId,
			@NonNull final Exception lookupFailure)
	{
		final String clientTransactionId = order.getPaymentById(orderAndPaymentId.getPaymentId()).getSumUpClientTransactionId();
		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(order.getClientAndOrgId()))
		{
			errorManager.createIssue(new AdempiereException(lookupFailure, MSG_SumUpLookupFailed)
					.setParameter("posOrderExternalId", order.getExternalId().getAsString())
					.setParameter("sumUpClientTransactionId", clientTransactionId)
					.setRecord(TableRecordReference.of(I_C_POS_Payment.Table_Name, orderAndPaymentId.getPaymentId())));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed recording the failed SumUp lookup of {} as AD_Issue. Ignored.", orderAndPaymentId, ex);
		}
	}

	/**
	 * The cashier's release of a card payment whose result is unknown, after the configured wait ({@link POSCardReleaseWait},
	 * counted from the start of its current checkout), in two requests:
	 * <ol>
	 * <li>without confirmation: cancels the checkout on the terminal's default card reader unless the payment's own SumUp
	 * transaction, refreshed from SumUp first, is no longer PENDING (see {@link #terminateCardReaderCheckoutUnlessFinished}),
	 * then checks the payment ({@link #checkCardPayment}). The cashier must confirm that the card was not charged
	 * ({@code confirmationRequired}) only while that check leaves the result unknown;</li>
	 * <li>with confirmation: under the order lock, only if the payment's status is still {@code expectedStatus} (UNKNOWN) and
	 * its own SumUp transaction, as stored, is not approved, the payment becomes FAILED, its checkout is remembered as released
	 * (a late result of it is never booked), and the release is recorded as an AD_Issue of the payment for the back office.
	 * Otherwise nothing is released and the current state is returned.</li>
	 * </ol>
	 * The outcome is decided on the payment's own SumUp transaction as metasfresh stores it after the check, not on the
	 * payment: the status event that updates the payment is asynchronous, so the returned order may still show the old
	 * status. Without such a transaction, the payment's status decides.
	 * <p>
	 * Refused (nothing changed): not a card payment whose result is unknown ({@code ReleaseOnlyIfResultUnknown}); before the
	 * configured wait ({@code ReleaseNotYetAllowed}); SumUp approved the charge ({@code ReleaseRefusedApproved}).
	 * <p>
	 * SumUp is called outside any transaction and without a lock held.
	 */
	public POSCardPaymentReleaseResult releaseCardPayment(@NonNull final POSCardPaymentReleaseRequest request)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final POSOrder order = ordersRepository.getById(ordersRepository.getIdByExternalId(request.getPosOrderExternalId()));
		assertCanEdit(request.getPosTerminalId(), order, request.getCashierId());
		final POSPayment payment = findPaymentByExternalId(order, request.getPosPaymentExternalId())
				.orElseThrow(() -> new AdempiereException("No payment found for " + request.getPosPaymentExternalId()));

		return request.isConfirmNotCharged()
				? confirmCardPaymentRelease(request, order, payment)
				: checkBeforeCardPaymentRelease(request, order, payment);
	}

	private POSCardPaymentReleaseResult checkBeforeCardPaymentRelease(
			@NonNull final POSCardPaymentReleaseRequest request,
			@NonNull final POSOrder order,
			@NonNull final POSPayment payment)
	{
		if (!payment.isUnknownCardPayment())
		{
			return refusedRelease(order, MSG_ReleaseOnlyIfResultUnknown);
		}
		if (isCardPaymentReleaseTooEarly(order, payment))
		{
			return refusedReleaseTooEarly(order);
		}

		terminateCardReaderCheckoutUnlessFinished(request, order, payment);

		final POSCardPaymentCheckResult checkResult = checkCardPayment(request.getPosTerminalId(), request.getPosOrderExternalId(), request.getPosPaymentExternalId(), request.getCashierId());
		return toReleaseResult(checkResult.getOrder(), request.getPosPaymentExternalId(), checkResult.isCheckFailed());
	}

	private POSCardPaymentReleaseResult confirmCardPaymentRelease(
			@NonNull final POSCardPaymentReleaseRequest request,
			@NonNull final POSOrder order,
			@NonNull final POSPayment payment)
	{
		final POSPaymentProcessingStatus expectedStatus = request.getExpectedStatus();
		if (expectedStatus != POSPaymentProcessingStatus.UNKNOWN)
		{
			throw new AdempiereException("The confirmation of a card payment release needs the expected status UNKNOWN")
					.setParameter("expectedStatus", expectedStatus);
		}
		if (!payment.getPaymentMethod().isCard())
		{
			return refusedRelease(order, MSG_ReleaseOnlyIfResultUnknown);
		}
		if (payment.getPaymentProcessingStatus() != expectedStatus)
		{
			// changed since the cashier was asked (e.g. SumUp's result arrived): nothing to release
			return toReleaseResult(order, request.getPosPaymentExternalId(), false);
		}
		// the first request checked the wait already; repeated so that no request can release earlier
		if (isCardPaymentReleaseTooEarly(order, payment))
		{
			return refusedReleaseTooEarly(order);
		}

		final POSOrderAndPaymentId orderAndPaymentId = POSOrderAndPaymentId.of(order.getLocalIdNotNull(), payment.getLocalIdNotNull());
		trxManager.runInNewTrx(() -> ordersRepository.updatePaymentByIdForUpdate(orderAndPaymentId, (lockedOrder, lockedPayment) -> {
			// a charge SumUp approved meanwhile (its status event may still be under way) is never released
			if (lockedPayment.getPaymentProcessingStatus() != expectedStatus || isApprovedAtSumUp(lockedPayment))
			{
				return lockedPayment;
			}
			final POSPayment released = lockedPayment.changingStatusToReleasedAsNotCharged();
			recordReleasedAsNotCharged(lockedOrder, orderAndPaymentId, released, request.getCashierId());
			return released;
		}));

		return toReleaseResult(ordersRepository.getById(orderAndPaymentId.getOrderId()), request.getPosPaymentExternalId(), false);
	}

	/**
	 * @return the release result for the order as it is now: refused if SumUp approved the payment's charge, a confirmation
	 * prompt if its result is still unknown, else no confirmation (nothing to release). Decided on the payment's own SumUp
	 * transaction as stored, if any (the payment's status follows it asynchronously), else on the payment's status.
	 */
	private POSCardPaymentReleaseResult toReleaseResult(
			@NonNull final POSOrder order,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			final boolean checkFailed)
	{
		final POSPayment payment = findPaymentByExternalId(order, posPaymentExternalId)
				.orElseThrow(() -> new AdempiereException("No payment found for " + posPaymentExternalId));
		final Optional<SumUpTransaction> ownTransaction = findOwnSumUpTransaction(payment);

		final boolean approved = ownTransaction.isPresent()
				? ownTransaction.get().isSuccessfulAndNotRefunded()
				: payment.isSuccessful();
		if (approved)
		{
			return POSCardPaymentReleaseResult.builder()
					.order(order)
					.checkFailed(checkFailed)
					.refusedMessageKey(MSG_ReleaseRefusedApproved)
					.refusedMessage(TranslatableStrings.adMessage(MSG_ReleaseRefusedApproved))
					.build();
		}

		final boolean resultStillUnknown = payment.isUnknown()
				&& ownTransaction.map(sumUpTrx -> sumUpTrx.getStatus().isPending()).orElse(true);
		return POSCardPaymentReleaseResult.builder()
				.order(order)
				.confirmationRequired(resultStillUnknown)
				.checkFailed(checkFailed)
				.build();
	}

	private static POSCardPaymentReleaseResult refusedRelease(@NonNull final POSOrder order, @NonNull final AdMessageKey refusedMessageKey)
	{
		return POSCardPaymentReleaseResult.builder()
				.order(order)
				.refusedMessageKey(refusedMessageKey)
				.refusedMessage(TranslatableStrings.adMessage(refusedMessageKey))
				.build();
	}

	private POSCardPaymentReleaseResult refusedReleaseTooEarly(@NonNull final POSOrder order)
	{
		final long releaseAfterSeconds = cardReleaseWait.getReleaseAfter(order.getClientAndOrgId()).getSeconds();
		return POSCardPaymentReleaseResult.builder()
				.order(order)
				.refusedMessageKey(MSG_ReleaseNotYetAllowed)
				.refusedMessage(TranslatableStrings.adMessage(MSG_ReleaseNotYetAllowed, String.valueOf(releaseAfterSeconds)))
				.build();
	}

	/**
	 * @return whether the configured wait ({@link POSCardReleaseWait}) since the start of the payment's current checkout has not
	 * elapsed yet (by {@link SystemTime})
	 */
	private boolean isCardPaymentReleaseTooEarly(@NonNull final POSOrder order, @NonNull final POSPayment payment)
	{
		final Instant startedAt = Check.assumeNotNull(payment.getCardCheckoutStartedAt(), "the card checkout of {} was started", payment);
		final Instant releasableAt = cardReleaseWait.getReleasableAt(order.getClientAndOrgId(), startedAt);
		return !releasableAt.isBefore(SystemTime.asInstant());
	}

	/**
	 * Cancels the checkout on the terminal's default card reader unless the payment's own SumUp transaction, refreshed from
	 * SumUp first, is no longer PENDING (see {@link POSCardReaderCheckoutTerminator#terminateUnlessFinished}). The refresh is
	 * the check ({@link #checkCardPayment}: the transaction is refreshed, or imported), run only if the payment names a SumUp
	 * transaction. The check after the terminate (see {@link #releaseCardPayment}) sees what the reader did meanwhile.
	 */
	private void terminateCardReaderCheckoutUnlessFinished(
			@NonNull final POSCardPaymentReleaseRequest request,
			@NonNull final POSOrder order,
			@NonNull final POSPayment payment)
	{
		cardReaderCheckoutTerminator.terminateUnlessFinished(
				order.getPosTerminalId(),
				() -> {
					if (payment.getSumUpClientTransactionId() == null)
					{
						return Optional.empty();
					}
					checkCardPayment(request.getPosTerminalId(), request.getPosOrderExternalId(), request.getPosPaymentExternalId(), request.getCashierId());
					return findOwnSumUpTransaction(payment);
				},
				"the card payment " + payment.getExternalId() + " of POS order " + order.getExternalId());
	}

	/**
	 * @return whether the payment's own SumUp transaction, as stored, is approved and not refunded
	 */
	private boolean isApprovedAtSumUp(@NonNull final POSPayment payment)
	{
		return findOwnSumUpTransaction(payment)
				.map(SumUpTransaction::isSuccessfulAndNotRefunded)
				.orElse(false);
	}

	/**
	 * @return the SumUp transaction of the payment's current checkout, as stored (never looked up by the payment's POS reference)
	 */
	private Optional<SumUpTransaction> findOwnSumUpTransaction(@NonNull final POSPayment payment)
	{
		final String clientTransactionId = payment.getSumUpClientTransactionId();
		return clientTransactionId != null
				? sumUpService.findTransactionByClientTransactionId(SumUpClientTransactionId.ofString(clientTransactionId))
				: Optional.empty();
	}

	/**
	 * Records the release as an AD_Issue of the payment, in the order's client / org, so that the back office finds the
	 * charge in the SumUp dashboard if the card was charged after all. Called under the order lock, in the release's
	 * transaction: the AD_Issue is committed in its own transaction; if writing it fails, the release is rolled back and the
	 * cashier can try again (as for the card settlement's release: a duplicate record is better than a release the back
	 * office never hears of).
	 */
	private void recordReleasedAsNotCharged(
			@NonNull final POSOrder order,
			@NonNull final POSOrderAndPaymentId orderAndPaymentId,
			@NonNull final POSPayment released,
			@NonNull final UserId cashierId)
	{
		try (final IAutoCloseable ignored = SumUpContext.temporarySwitchTo(order.getClientAndOrgId()))
		{
			errorManager.createIssue(new AdempiereException(MSG_ReleasedAsNotCharged)
					.setParameter("posOrderExternalId", order.getExternalId().getAsString())
					.setParameter("sumUpClientTransactionId", released.getSumUpClientTransactionId())
					.setParameter("releasedByUserId", cashierId.getRepoId())
					.setRecord(TableRecordReference.of(I_C_POS_Payment.Table_Name, orderAndPaymentId.getPaymentId())));
		}
	}

	public POSOrder refundPayment(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		return ordersRepository.updateByExternalId(posOrderExternalId, posOrder -> {
			assertCanEdit(posTerminalId, posOrder, userId);
			posOrder.assertWaitingForPayment();

			final POSTerminalPaymentProcessorConfig paymentProcessorConfig = posTerminalService.getPOSTerminalById(posOrder.getPosTerminalId()).getPaymentProcessorConfigNotNull();

			posOrder.updatePaymentByExternalId(posPaymentExternalId, posPayment -> posOrderProcessingServices.refundPOSPayment(posPayment, posOrder.getLocalIdNotNull(), paymentProcessorConfig));
		});
	}

	public Optional<Resource> getReceiptPdf(final @NonNull POSOrderExternalId externalId)
	{
		final POSOrderId id = ordersRepository.getIdByExternalId(externalId);
		return archiveBL.getLastArchiveBinaryData(id.toRecordRef());
	}
}
