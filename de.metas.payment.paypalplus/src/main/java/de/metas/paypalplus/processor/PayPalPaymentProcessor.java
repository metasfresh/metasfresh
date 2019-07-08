package de.metas.paypalplus.processor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import com.braintreepayments.http.HttpRequest;
import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.exceptions.HttpException;
import com.google.common.collect.ImmutableList;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

import de.metas.money.CurrencyRepository;
import de.metas.payment.PaymentRule;
import de.metas.payment.processor.PaymentProcessor;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.paypalplus.PayPalConfig;
import de.metas.paypalplus.controller.PayPalConfigProvider;
import de.metas.paypalplus.logs.PayPalCreateLogRequest;
import de.metas.paypalplus.logs.PayPalCreateLogRequest.PayPalCreateLogRequestBuilder;
import de.metas.paypalplus.logs.PayPalLogRepository;
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
	private final PayPalLogRepository logsRepo;
	private final CurrencyRepository currencyRepo;

	private final PayPalHttpClientFactory payPalHttpClientFactory = new PayPalHttpClientFactory();

	public PayPalPaymentProcessor(
			@NonNull final PayPalConfigProvider payPalConfigProvider,
			@NonNull final PayPalLogRepository logsRepo,
			@NonNull final CurrencyRepository currencyRepo)
	{
		this.payPalConfigProvider = payPalConfigProvider;
		this.logsRepo = logsRepo;
		this.currencyRepo = currencyRepo;
	}

	private PayPalHttpClient getClient()
	{
		final PayPalConfig config = getConfig();
		return payPalHttpClientFactory.getPayPalHttpClient(config);
	}

	private PayPalConfig getConfig()
	{
		return payPalConfigProvider.getConfig();
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
	public void processReservation(@NonNull final PaymentReservation reservation)
	{
		//
		// Create Order
		{
			final OrdersCreateRequest ordersCreateRequest = createOrdersCreateRequest(reservation, getConfig());
			final HttpResponse<Order> response = executeRequest(ordersCreateRequest);

			final URL approveUrl = extractApproveUrl(response.result());
		}

		// TODO Auto-generated method stub
	}

	private OrdersCreateRequest createOrdersCreateRequest(
			@NonNull final PaymentReservation reservation,
			@NonNull final PayPalConfig config)
	{
		final OrderRequest requestBody = new OrderRequest()
				.intent("AUTHORIZE")
				.applicationContext(new ApplicationContext()
						.returnUrl(config.getOrderApproveCallbackUrl())
						.cancelUrl(config.getOrderApproveCallbackUrl()))
				.purchaseUnits(ImmutableList.of(
						new PurchaseUnitRequest()
								.amount(toAmountWithBreakdown(reservation.getAmount()))));

		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(requestBody);

		return request;
	}

	private static URL extractApproveUrl(final Order order)
	{
		return extractUrl(order, "approve");
	}

	private static URL extractUrl(final Order order, final String rel)
	{
		for (final LinkDescription link : order.links())
		{
			if (rel.contentEquals(link.rel()))
			{
				try
				{
					return new URL(link.href());
				}
				catch (MalformedURLException e)
				{
					throw new AdempiereException("Invalid URL " + link.href());
				}
			}
		}

		throw new AdempiereException("No URL found for `" + rel + "`");
	}

	private AmountWithBreakdown toAmountWithBreakdown(final de.metas.money.Money amount)
	{
		return new AmountWithBreakdown()
				.value(amount.getAsBigDecimal().toString())
				.currencyCode(currencyRepo.getCurrencyCodeById(amount.getCurrencyId()).toThreeLetterCode());
	}

	private <T> HttpResponse<T> executeRequest(final HttpRequest<T> request)
	{
		final PayPalCreateLogRequestBuilder log = PayPalCreateLogRequest.builder();

		try
		{
			log.request(request);

			final PayPalHttpClient client = getClient();
			final HttpResponse<T> response = client.execute(request);
			log.response(response);
			return response;
		}
		catch (final HttpException ex)
		{
			log.response(ex);
			throw AdempiereException.wrapIfNeeded(ex);
		}
		catch (final IOException ex)
		{
			log.response(ex);
			throw AdempiereException.wrapIfNeeded(ex);
		}
		finally
		{
			logsRepo.log(log.build());
		}
	}

	@Override
	public void captureMoney()
	{
	}

}
