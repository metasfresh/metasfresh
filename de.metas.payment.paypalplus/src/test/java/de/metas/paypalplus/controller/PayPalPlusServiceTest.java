package de.metas.paypalplus.controller;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

import org.junit.Before;
import org.junit.Test;

import com.paypal.base.Constants;

import de.metas.paypalplus.PayPalConfig;
import de.metas.paypalplus.model.BillingAddress;
import de.metas.paypalplus.model.CreditCard;
import de.metas.paypalplus.model.PaymentAmount;
import de.metas.paypalplus.model.PaymentCaptureRequest;
import de.metas.paypalplus.model.PaymentCaptureResponse;
import de.metas.paypalplus.model.PaymentReservationRequest;
import de.metas.paypalplus.model.PaymentReservationResponse;

/*
 * #%L
 * de.metas.paypalplus.controller
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
public class PayPalPlusServiceTest
{
	private static final String CURRENCY_EUR = "EUR";

	private PayPalPlusService service;

	@Before
	public void before()
	{
		final PayPalConfig config = PayPalConfig.builder()
				.clientId("AbrU-xbGF2BJHOaAaF8yC9GZRHCSiNA6UY61kI8P7Ipz5ZZTvXBHTY-nzeIl9eh7xFtsoua1brYcNlQx")
				.clientSecret("EObqX1HbD-LhiHQ-oI3ZdAGnDSluIekyjT2ZHxvL0L924d_c3DA3gH0Qzh8KFpShQemTo3A-qS-5X7oT")
				.executionMode(Constants.SANDBOX)
				.build();

		service = new PayPalPlusService(SingletonPayPalConfigProvider.of(config));
	}

	@Test
	public void reservePayment()
	{
		final PaymentReservationRequest request = createPaymentReservationRequest("123.45");
		final PaymentReservationResponse response = service.reservePayment(request);
		assertEquals(response.getPaymentState(), "approved");
	}

	@Test
	public void capturePayment()
	{
		final PaymentReservationRequest reservationRequest = createPaymentReservationRequest("101.01");
		final PaymentReservationResponse reservationResponse = service.reservePayment(reservationRequest);
		assertEquals(reservationResponse.getPaymentState(), "approved");

		final PaymentCaptureRequest captureRequest = PaymentCaptureRequest.builder()
				.authorizationId(reservationResponse.getAuthorizationId())
				.currency(CURRENCY_EUR)
				.amount(new BigDecimal("44.01"))
				.build();
		final PaymentCaptureResponse captureReponse = service.capturePayment(captureRequest);
		System.out.println("captureResponse: " + captureReponse);
	}

	@Test
	public void cancelPayment()
	{
		final PaymentReservationRequest reservationRequest = createPaymentReservationRequest("101.01");
		final PaymentReservationResponse reservationReponse = service.reservePayment(reservationRequest);
		assertEquals(reservationReponse.getPaymentState(), "approved");

		final String voidResponse = service.cancelPayment(reservationReponse.getAuthorizationId());
		assertEquals(voidResponse, "voided");
	}

	private PaymentReservationRequest createPaymentReservationRequest(final String amount)
	{
		return PaymentReservationRequest.builder()
				.paymentAmount(createPaymentAmount(amount))
				.paymentDate(LocalDate.now())
				.creditCard(CreditCard.builder()
						.cardType("visa")
						.firstName("Dummy first")
						.lastName("Dummy lastname")
						.expirationDate(YearMonth.of(2019, Month.NOVEMBER))
						.cardNumber("4417119669820331")
						.cvv2("123")
						.build())
				.billingAddress(BillingAddress.builder()
						.address1("Franz Liszt 4")
						.address2("ap. 5")
						.city("Timisoara")
						.state("Timis")
						.countryCode("US")
						.postalCode("43210")
						.build())
				.transactionDescription("Transaction description")
				.build();
	}

	private PaymentAmount createPaymentAmount(final String amount)
	{
		return PaymentAmount.builder()
				.currency(CURRENCY_EUR)
				.subTotal(new BigDecimal(amount))
				.shippingTax(BigDecimal.ZERO)
				.tax(BigDecimal.ZERO)
				.build();
	}
}
