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

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrder;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderTransaction;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonPaymentMethod;
import de.metas.camel.externalsystems.shopware6.api.model.order.PaymentMethodType;
import de.metas.camel.externalsystems.shopware6.api.model.order.TechnicalNameEnum;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;

public class PaymentRequestProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public PaymentRequestProcessor(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportOrdersRouteContext ordersRouteContext = ProcessorHelper
				.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);

		final JsonOrderPaymentCreateRequest orderPaymentCreateRequest = buildOrderPaymentCreateRequest(ordersRouteContext)
				.orElse(null);

		exchange.getIn().setBody(orderPaymentCreateRequest);
	}

	@NonNull
	private Optional<JsonOrderPaymentCreateRequest> buildOrderPaymentCreateRequest(@NonNull final ImportOrdersRouteContext context)
	{
		final JsonPaymentMethod paymentMethod = context.getCompositeOrderNotNull().getJsonPaymentMethod();
		final JsonOrderTransaction orderTransaction = context.getCompositeOrderNotNull().getOrderTransaction();
		final JsonOrder order = context.getOrderNotNull().getJsonOrder();

		final boolean isPaypalType = PaymentMethodType.PAY_PAL_PAYMENT_HANDLER.getValue().equals(paymentMethod.getShortName());
		final boolean isPaid = TechnicalNameEnum.PAID.getValue().equals(orderTransaction.getStateMachine().getTechnicalName());

		if (!(isPaypalType && isPaid))
		{
			processLogger.logMessage("Order " + order.getOrderNumber() + " (ID=" + order.getId() + "): Not sending current payment to metasfresh; it would have to be 'paypal' and 'paid'!"
											 + " PaymentId = " + orderTransaction.getId()
											 + " paidStatus = " + isPaid
											 + " paypalType = " + isPaypalType, JsonMetasfreshId.toValue(context.getPInstanceId()));
			return Optional.empty();
		}

		final String currencyCode = context.getCurrencyInfoProvider().getIsoCodeByCurrencyIdNotNull(order.getCurrencyId());

		final String bPartnerIdentifier = context.getMetasfreshId().getIdentifier();

		return Optional.of(JsonOrderPaymentCreateRequest.builder()
								   .orgCode(context.getOrgCode())
								   .externalPaymentId(orderTransaction.getId())
								   .bpartnerIdentifier(bPartnerIdentifier)

								   .amount(orderTransaction.getAmount().getTotalPrice())
								   .currencyCode(currencyCode)

								   .orderIdentifier(ExternalIdentifierFormat.formatOldStyleExternalId(order.getId()))

								   .transactionDate(orderTransaction.getCreatedAt().toLocalDate())
								   .build());
	}
}
