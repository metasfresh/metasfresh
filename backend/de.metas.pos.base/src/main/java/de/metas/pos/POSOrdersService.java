package de.metas.pos;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.payment.sumup.SumUpContext;
import de.metas.pos.payment_gateway.POSPaymentProcessResponse;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.pos.repository.model.I_C_POS_Payment;
import de.metas.tax.api.ITaxDAO;
import de.metas.user.UserId;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class POSOrdersService
{
	@NonNull private static final Logger logger = LogManager.getLogger(POSOrdersService.class);
	private static final AdMessageKey MSG_LateApprovalNotBooked = AdMessageKey.of("de.metas.pos.POSPayment.LateApprovalNotBooked");
	private static final AdMessageKey MSG_CardResultNotStored = AdMessageKey.of("de.metas.pos.POSPayment.CardResultNotStored");
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
			@NonNull final POSOrderProcessingServices posOrderProcessingServices)
	{
		this(posTerminalService, ordersRepository, currencyRepository, posOrderProcessingServices, POSOrdersService::sleepUninterruptibly);
	}

	@VisibleForTesting
	POSOrdersService(
			@NonNull final POSTerminalService posTerminalService,
			@NonNull final POSOrdersRepository ordersRepository,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final POSOrderProcessingServices posOrderProcessingServices,
			@NonNull final Sleeper sleeper)
	{
		this.posTerminalService = posTerminalService;
		this.ordersRepository = ordersRepository;
		this.currencyRepository = currencyRepository;
		this.posOrderProcessingServices = posOrderProcessingServices;
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
	 * the database fails, it is tried up to 3 times in all ({@link #CARD_SEND_RESULT_MAX_ATTEMPTS}), each time re-locking and re-reading the
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

	// TODO TC23 RED: stub, replaced by the check of an unknown card payment
	public POSCardPaymentCheckResult checkCardPayment(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		throw new UnsupportedOperationException("TC23 RED");
	}

	// TODO TC23 RED: stub, replaced by the release of an unknown card payment
	public POSCardPaymentReleaseResult releaseCardPayment(@NonNull final POSCardPaymentReleaseRequest request)
	{
		throw new UnsupportedOperationException("TC23 RED");
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
