package de.metas.pos;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.tax.api.ITaxDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class POSOrdersService
{
	@NonNull private static final Logger logger = LogManager.getLogger(POSOrdersService.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSOrdersRepository ordersRepository;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final POSOrderProcessingServices posOrderProcessingServices;

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
		return ordersRepository.updateByExternalId(externalId, order -> {
			assertCanEdit(posTerminalId, order, userId);
			order.changeStatusTo(nextStatus, posOrderProcessingServices);
		});
	}

	public void updatePaymentStatusFromRemoteAndTryCompleteOrder(
			@NonNull final POSOrderAndPaymentId posOrderAndPaymentId,
			@NonNull final POSPaymentProcessingStatus paymentProcessingStatus)
	{
		// NOTE: 
		// Make sure we are running out of transaction. 
		// Keep [POS payment update] and [POS order completing] in 2 separate transactions
		// so it might be that updating can succeed (and it's saved) but order processing not.
		trxManager.assertThreadInheritedTrxNotExists();

		updatePaymentStatusFromRemote(posOrderAndPaymentId, paymentProcessingStatus);
		tryCompleteNoFail(posOrderAndPaymentId.getOrderId());
	}

	private void updatePaymentStatusFromRemote(
			@NonNull final POSOrderAndPaymentId posOrderAndPaymentId,
			@NonNull final POSPaymentProcessingStatus paymentProcessingStatus)
	{
		ordersRepository.updatePaymentById(posOrderAndPaymentId, (order, payment) -> payment.changingStatusFromRemote(paymentProcessingStatus));
	}

	private void tryCompleteNoFail(@NonNull final POSOrderId posOrderId)
	{
		try
		{
			ordersRepository.updateById(posOrderId, order -> {
				final BooleanWithReason canComplete = order.checkCanTryComplete();
				if (canComplete.isTrue())
				{
					order.changeStatusTo(POSOrderStatus.Completed, posOrderProcessingServices);
				}
				else
				{
					logger.debug("Skip completing {} because {}", posOrderId, canComplete.getReason());
				}
			});
		}
		catch (final Exception ex)
		{
			logger.warn("Failed completing order {}. Ignored.", posOrderId, ex);
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

	public POSOrder checkoutPayment(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final POSOrderExternalId posOrderExternalId,
			@NonNull final POSPaymentExternalId posPaymentExternalId,
			@NonNull final UserId userId)
	{
		return ordersRepository.updateByExternalId(posOrderExternalId, posOrder -> {
			assertCanEdit(posTerminalId, posOrder, userId);
			posOrder.assertWaitingForPayment();

			final POSTerminalPaymentProcessorConfig paymentProcessorConfig = posTerminalService.getPOSTerminalById(posOrder.getPosTerminalId()).getPaymentProcessorConfigNotNull();

			posOrder.updatePaymentByExternalId(posPaymentExternalId, posPayment -> posOrderProcessingServices.processPOSPayment(posPayment, posOrder, paymentProcessorConfig));
		});
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

}
