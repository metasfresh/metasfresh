package de.metas.pos;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.pos.remote.RemotePOSOrder;
import de.metas.tax.api.ITaxDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class POSOrdersService
{
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

	public POSOrder changeStatusTo(@NonNull final POSOrderExternalId externalId, @NonNull final POSOrderStatus nextStatus, @NonNull final UserId userId)
	{
		return ordersRepository.updateByExternalId(externalId, order -> {
			assertCanEdit(order, userId);
			order.changeStatusTo(nextStatus, posOrderProcessingServices);
		});
	}

	public void updatePaymentStatusFromRemoteAndTryCompleteOrder(
			@NonNull final POSOrderId posOrderId,
			@NonNull final POSPaymentId posPaymentId,
			@NonNull final POSPaymentProcessingStatus paymentProcessingStatus)
	{
		ordersRepository.updateById(posOrderId, order -> {
			order.updatePaymentById(posPaymentId, payment -> payment.changingStatusFromRemote(paymentProcessingStatus));
			order.changeStatusTo(POSOrderStatus.Completed, posOrderProcessingServices);
		});
	}

	private void assertCanEdit(@NonNull final POSOrder order, @NonNull final UserId userId)
	{
		if (!UserId.equals(order.getCashierId(), userId))
		{
			throw new AdempiereException("Edit not allowed");
		}
	}

	public POSOrder updateOrderFromRemote(final @NonNull RemotePOSOrder remoteOrder, final UserId userId)
	{
		return ordersRepository.createOrUpdateByExternalId(
				remoteOrder.getUuid(),
				externalId -> newPOSOrder(externalId, userId),
				order -> {
					assertCanEdit(order, userId);
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

	private POSOrder newPOSOrder(@NonNull final POSOrderExternalId externalId, @NonNull final UserId userId)
	{
		final POSTerminal posTerminal = posTerminalService.getPOSTerminal();
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
			final @NonNull POSOrderExternalId posOrderExternalId,
			final @NonNull POSPaymentExternalId posPaymentExternalId,
			final @NonNull UserId userId)
	{
		return ordersRepository.updateByExternalId(posOrderExternalId, posOrder -> {
			assertCanEdit(posOrder, userId);
			posOrder.assertWaitingForPayment();

			final POSTerminalPaymentProcessorConfig paymentProcessorConfig = posTerminalService.getPOSTerminalById(posOrder.getPosTerminalId()).getPaymentProcessorConfigNotNull();

			posOrder.updatePaymentByExternalId(posPaymentExternalId, posPayment -> posOrderProcessingServices.processPOSPayment(posPayment, posOrder, paymentProcessorConfig));
		});
	}
}
