package de.metas.payment.paypal.client;

import java.io.IOException;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.braintreepayments.http.HttpRequest;
import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.exceptions.HttpException;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersAuthorizeRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.payments.AuthorizationsCaptureRequest;
import com.paypal.payments.Capture;
import com.paypal.payments.CaptureRequest;

import de.metas.JsonObjectMapperHolder;
import de.metas.currency.Amount;
import de.metas.payment.paypal.PayPalClientResponse;
import de.metas.payment.paypal.config.PayPalConfig;
import de.metas.payment.paypal.config.PayPalConfigProvider;
import de.metas.payment.paypal.logs.PayPalCreateLogRequest;
import de.metas.payment.paypal.logs.PayPalCreateLogRequest.PayPalCreateLogRequestBuilder;
import de.metas.payment.paypal.logs.PayPalLogRepository;
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

/**
 * PayPal Client (connects to PayPal).
 */
@Service
public class PayPalClientService
{
	private final PayPalConfigProvider payPalConfigProvider;
	private final PayPalLogRepository logsRepo;

	public PayPalClientService(
			@NonNull final PayPalConfigProvider payPalConfigProvider,
			@NonNull final PayPalLogRepository logsRepo)
	{
		this.payPalConfigProvider = payPalConfigProvider;
		this.logsRepo = logsRepo;
	}

	public PayPalClientResponse<Order, PayPalErrorResponse> getAPIOrderById(@NonNull final PayPalOrderExternalId apiOrderId, @NonNull final PayPalClientExecutionContext context)
	{
		final OrdersGetRequest request = new OrdersGetRequest(apiOrderId.getAsString());
		return executeRequest(request, context);
	}

	public Order createOrder(@NonNull final OrderRequest apiRequest, @NonNull PayPalClientExecutionContext context)
	{
		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(apiRequest);

		return executeRequest(request, context)
				.getResult();
	}

	public Order authorizeOrder(
			@NonNull final PayPalOrderExternalId apiOrderId,
			@NonNull final PayPalClientExecutionContext context)
	{
		final OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(apiOrderId.getAsString());
		return executeRequest(request, context)
				.getResult();
	}

	public PayPalHttpClient createPayPalHttpClient(@NonNull final PayPalConfig config)
	{
		final PayPalEnvironment environment = createPayPalEnvironment(config);
		return new PayPalHttpClient(environment);
	}

	private static PayPalEnvironment createPayPalEnvironment(@NonNull final PayPalConfig config)
	{
		if (!config.isSandbox())
		{
			return new PayPalEnvironment(
					config.getClientId(),
					config.getClientSecret(),
					config.getBaseUrl(),
					config.getWebUrl());
		}
		else
		{
			return new PayPalEnvironment.Sandbox(
					config.getClientId(),
					config.getClientSecret());
		}
	}

	public PayPalConfig getConfig()
	{
		return payPalConfigProvider.getConfig();
	}

	private <T> PayPalClientResponse<T, PayPalErrorResponse> executeRequest(
			@NonNull final HttpRequest<T> request,
			@NonNull final PayPalClientExecutionContext context)
	{
		final PayPalCreateLogRequestBuilder log = PayPalCreateLogRequest.builder()
				.paymentReservationId(context.getPaymentReservationId())
				.paymentReservationCaptureId(context.getPaymentReservationCaptureId())
				//
				.salesOrderId(context.getSalesOrderId())
				.salesInvoiceId(context.getSalesInvoiceId())
				.paymentId(context.getPaymentId())
				//
				.internalPayPalOrderId(context.getInternalPayPalOrderId());

		try
		{
			log.request(request);

			final PayPalHttpClient httpClient = createPayPalHttpClient(getConfig());
			final HttpResponse<T> response = httpClient.execute(request);
			log.response(response);

			return PayPalClientResponse.ofResult(response.result());
		}
		catch (final HttpException httpException)
		{
			log.response(httpException);

			final String errorAsJson = httpException.getMessage();
			try
			{
				final PayPalErrorResponse error = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(errorAsJson, PayPalErrorResponse.class);
				return PayPalClientResponse.ofError(error, httpException);
			}
			catch (final Exception jsonException)
			{
				throw AdempiereException.wrapIfNeeded(httpException)
						.suppressing(jsonException);
			}
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

	public Capture captureOrder(
			@NonNull final PayPalOrderAuthorizationId authId,
			@NonNull final Amount amount,
			@Nullable final Boolean finalCapture,
			@NonNull final PayPalClientExecutionContext context)
	{
		final AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId.getAsString())
				.requestBody(new CaptureRequest()
						.amount(new com.paypal.payments.Money()
								.currencyCode(amount.getCurrencyCode().toThreeLetterCode())
								.value(amount.getAsBigDecimal().toPlainString()))
						.finalCapture(finalCapture));

		return executeRequest(request, context)
				.getResult();
	}
}
