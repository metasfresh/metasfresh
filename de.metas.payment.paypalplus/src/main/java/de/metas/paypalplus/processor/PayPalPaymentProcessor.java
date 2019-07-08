package de.metas.paypalplus.processor;

import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.PurchaseUnitRequest;

import de.metas.money.CurrencyRepository;
import de.metas.payment.PaymentRule;
import de.metas.payment.processor.PaymentProcessor;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.paypalplus.PayPalConfig;
import de.metas.paypalplus.controller.PayPalConfigProvider;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class PayPalPaymentProcessor implements PaymentProcessor
{
	private final PayPalConfigProvider payPalConfigProvider;
	private final CurrencyRepository currencyRepo;

	private final PayPalHttpClientFactory payPalHttpClientFactory = new PayPalHttpClientFactory();

	public PayPalPaymentProcessor(
			@NonNull final PayPalConfigProvider payPalConfigProvider,
			@NonNull final CurrencyRepository currencyRepo)
	{
		this.payPalConfigProvider = payPalConfigProvider;
		this.currencyRepo = currencyRepo;
	}

	@Override
	public PaymentRule getPaymentRule()
	{
		return PaymentRule.PayPal;
	}

	@Override
	public boolean canReserveMoney()
	{
		return true;
	}

	@Override
	public void processReservation(final PaymentReservation reservation)
	{

		// TODO Auto-generated method stub
	}

	private OrderRequest createOrderRequest(
			final PaymentReservation reservation,
			final PayPalConfig config)
	{
		// currencyRepo.getById(currencyId)
		reservation.getAmount().getAsBigDecimal();
		return new OrderRequest()
				.intent("AUTHORIZE")
				.applicationContext(new ApplicationContext()
						.returnUrl(config.getOrderApproveCallbackUrl())
						.cancelUrl(config.getOrderApproveCallbackUrl()))
				.purchaseUnits(ImmutableList.of(
						new PurchaseUnitRequest()
								.amount(new AmountWithBreakdown()
										.currencyCode("EUR")
										.value("220.00") //
								) //
				));
	}

	@Override
	public void captureMoney()
	{
		// TODO Auto-generated method stub
	}

}
