/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.shopware6.order.processor;

import de.metas.camel.externalsystems.common.DateAndImportStatus;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderTransaction;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderTransactions;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonPaymentMethod;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonStateMachine;
import de.metas.camel.externalsystems.shopware6.api.model.order.PaymentMethodType;
import de.metas.camel.externalsystems.shopware6.api.model.order.TechnicalNameEnum;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.OrderCompositeInfo;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;
import java.util.logging.Logger;

import static de.metas.camel.externalsystems.shopware6.ProcessorHelper.getPropertyOrThrowError;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;

public class OrderFilter implements Processor
{
	private static final Logger logger = Logger.getLogger(OrderFilter.class.getName());

	@Override
	public void process(final Exchange exchange)
	{
		final ImportOrdersRouteContext routeContext = getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		final OrderCandidate orderAndCustomId = exchange.getIn().getBody(OrderCandidate.class);

		final Optional<OrderCompositeInfo> orderToImport = checkOrderState(orderAndCustomId)
				.flatMap(order -> checkOrderTransactionStateAndPayment(order, routeContext.getShopwareClient()));

		if (orderToImport.isEmpty())
		{
			routeContext.setNextImportStartingTimestamp(DateAndImportStatus.of(false, orderAndCustomId.getJsonOrder().getCreatedAt().toInstant()));
			exchange.getIn().setBody(null);
			return;
		}

		routeContext.setOrderCompositeInfo(orderToImport.get());
		routeContext.setNextImportStartingTimestamp(DateAndImportStatus.of(true, orderAndCustomId.getJsonOrder().getCreatedAt().toInstant()));
		exchange.getIn().setBody(orderAndCustomId);
	}

	private Optional<OrderCandidate> checkOrderState(@NonNull final OrderCandidate orderAndCustomId)
	{
		final JsonStateMachine stateMachine = orderAndCustomId.getJsonOrder().getStateMachine();

		if (stateMachine == null
				|| !TechnicalNameEnum.OPEN.getValue().equals(stateMachine.getTechnicalName()))
		{
			logger.warning("*** Skipping the current order due to stateMachineState! StateMachineState: " + stateMachine);
			return Optional.empty();
		}

		return Optional.of(orderAndCustomId);
	}

	private Optional<OrderCompositeInfo> checkOrderTransactionStateAndPayment(
			@NonNull final OrderCandidate orderAndCustomId,
			@NonNull final ShopwareClient shopwareClient)
	{
		final Optional<JsonOrderTransactions> orderTransactions = shopwareClient.getOrderTransactions(orderAndCustomId.getJsonOrder().getId());

		if (orderTransactions.isEmpty()
				|| orderTransactions.get().getTransactionList().get(0) == null)
		{
			logger.warning("*** Skipping the current order as there are no transactions available!OrderId: " + orderAndCustomId.getJsonOrder().getId());
			return Optional.empty();
		}

		if (orderTransactions.get().getTransactionList().size() > 1)
		{
			throw new RuntimeException("Multiple transactions returned for orderID=" + orderAndCustomId.getJsonOrder().getId());
		}

		final JsonOrderTransaction orderTransaction = orderTransactions.get().getTransactionList().get(0);
		final Optional<JsonPaymentMethod> paymentMethod = shopwareClient.getPaymentMethod(orderTransaction.getPaymentMethodId());

		if (paymentMethod.isEmpty())
		{
			logger.warning("No payment method was found for id: " + orderTransaction.getPaymentMethodId() + "; OrderId=" + orderAndCustomId.getJsonOrder().getId());
			return Optional.empty();
		}

		if (!isOrderReadyForImportBasedOnTrx(orderTransaction, paymentMethod.get()))
		{
			logger.warning("*** Skipping the current order based on transaction status & payment method! OrderId = "
								   + orderAndCustomId.getJsonOrder().getId()
								   + "transactionStatus = " + orderTransaction.getStateMachine().getTechnicalName()
								   + "paymentType = " + paymentMethod.get().getShortName());
			return Optional.empty();
		}

		final OrderCompositeInfo orderCompositeInfo = OrderCompositeInfo.builder()
				.orderAndCustomId(orderAndCustomId)
				.orderTransaction(orderTransaction)
				.jsonPaymentMethod(paymentMethod.get())
				.build();

		return Optional.of(orderCompositeInfo);
	}

	private boolean isOrderReadyForImportBasedOnTrx(@NonNull final JsonOrderTransaction orderTransaction, @NonNull final JsonPaymentMethod paymentMethod)
	{
		final JsonStateMachine transactionStateMachine = orderTransaction.getStateMachine();

		final boolean isPaid = TechnicalNameEnum.PAID.getValue().equals(transactionStateMachine.getTechnicalName());
		final boolean isOpen = TechnicalNameEnum.OPEN.getValue().equals(transactionStateMachine.getTechnicalName());

		if (!isOpen && !isPaid)
		{
			return false;
		}

		final PaymentMethodType paymentMethodType = PaymentMethodType.ofValue(paymentMethod.getShortName());

		return switch (paymentMethodType)
				{
					case PRE_PAYMENT, INVOICE_PAYMENT, DEBIT_PAYMENT -> isOpen;
					case PAY_PAL_PAYMENT_HANDLER -> isPaid;
					default -> false;
				};
	}
}
