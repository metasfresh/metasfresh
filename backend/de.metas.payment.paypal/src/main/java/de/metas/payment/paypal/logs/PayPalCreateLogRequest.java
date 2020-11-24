package de.metas.payment.paypal.logs;

import org.compiere.util.Util;

import com.braintreepayments.http.Headers;
import com.braintreepayments.http.HttpRequest;
import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.exceptions.HttpException;
import com.braintreepayments.http.serializer.Json;
import com.google.common.collect.ImmutableMap;

import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.paypal.client.PayPalOrderId;
import de.metas.payment.reservation.PaymentReservationCaptureId;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class PayPalCreateLogRequest
{
	String requestPath;
	String requestMethod;
	ImmutableMap<String, String> requestHeaders;
	String requestBodyAsJson;

	int responseStatusCode;
	ImmutableMap<String, String> responseHeaders;
	String responseBodyAsJson;

	PaymentReservationId paymentReservationId;
	PaymentReservationCaptureId paymentReservationCaptureId;
	OrderId salesOrderId;
	InvoiceId salesInvoiceId;
	PaymentId paymentId;
	PayPalOrderId internalPayPalOrderId;

	public static class PayPalCreateLogRequestBuilder
	{
		public PayPalCreateLogRequestBuilder request(@NonNull final HttpRequest<?> request)
		{
			requestPath(request.path());
			requestMethod(request.verb());
			requestHeaders(toMap(request.headers()));
			requestBodyAsJson(toJsonString(request.requestBody()));

			return this;
		}

		public PayPalCreateLogRequestBuilder response(@NonNull final HttpResponse<?> response)
		{
			responseStatusCode(response.statusCode());
			responseHeaders(toMap(response.headers()));
			responseBodyAsJson(toJsonString(response.result()));

			return this;
		}

		public PayPalCreateLogRequestBuilder response(@NonNull final Throwable ex)
		{
			if (ex instanceof HttpException)
			{
				final HttpException httpException = (HttpException)ex;
				responseStatusCode(httpException.statusCode());
				responseHeaders(toMap(httpException.headers()));
				responseBodyAsJson(httpException.getMessage());
			}
			else
			{
				responseStatusCode(0);
				responseHeaders(ImmutableMap.of());
				responseBodyAsJson(Util.dumpStackTraceToString(ex));
			}

			return this;
		}

		private static ImmutableMap<String, String> toMap(final Headers headers)
		{
			if (headers == null)
			{
				return ImmutableMap.of();
			}

			final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
			for (final String header : headers)
			{
				final String value = headers.header(header);

				final String headerNorm = CoalesceUtil.coalesce(header, "");
				final String valueNorm = CoalesceUtil.coalesce(value, "");
				builder.put(headerNorm, valueNorm);
			}

			return builder.build();
		}

		private static String toJsonString(final Object obj)
		{
			if (obj == null)
			{
				return "";
			}

			try
			{
				return new Json().serialize(obj);
			}
			catch (final Exception ex)
			{
				return obj.toString();
			}
		}

	}
}
