package de.metas.paypalplus.controller;

import com.paypal.base.rest.PayPalRESTException;
import de.metas.paypalplus.PayPalProperties;
import de.metas.paypalplus.model.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
@RunWith(SpringJUnit4ClassRunner.class)
public class PayPalPlusRestControllerTest
{
	private static PayPalPlusRestController controller;

	@BeforeClass
	public static void beforeClass()
	{
		controller = new PayPalPlusRestController(PayPalProperties.CONFIG_SANDBOX_PROPERTIES);

	}

	@Test
	public void capturePayment()
	{
		PayPalPlusPayment payPalPlusPayment = buildPaymentObject();
		PaymentStatus paymentStatus = null;
		try
		{
			paymentStatus = controller.capturePayment(payPalPlusPayment);
		}
		catch (PayPalRESTException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(paymentStatus.getPaymentState(), "approved");
	}

	@Test
	public void reservePayment()
	{
		PayPalPlusPayment payPalPlusPayment = buildPaymentObject();
		PaymentStatus paymentStatus = null;
		try
		{
			paymentStatus = controller.authorizePayment(payPalPlusPayment);
		}
		catch (PayPalPlusException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(paymentStatus.getPaymentState(), "approved");
	}

	@Test
	public void cancelPayment()
	{
		PayPalPlusPayment payPalPlusPayment = buildPaymentObject();
		PaymentStatus paymentStatus = null;
		try
		{
			paymentStatus = controller.authorizePayment(payPalPlusPayment);
		}
		catch (PayPalPlusException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(paymentStatus.getPaymentState(), "approved");
		String status = "";
		try
		{
			status = controller.cancelPayment(paymentStatus.getAuthorizationId(), "Cancelling reason - something");
		}
		catch (PayPalPlusException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(status, "voided");
	}

	private PayPalPlusPayment buildPaymentObject()
	{
		return PayPalPlusPayment.builder().
				paymentAmount(PaymentAmount.builder().
						paymentDetails(PaymentDetails.builder().
								shippingTax("0.00").
								subTotal("107.41").
								tax("0.00").
								build()).
						total("107.41").
						currency("EUR").
						build()).
				paymentCurrency("EUR").
				paymentDate(LocalDate.now()).
				creditCard(CreditCard.builder().
						cardType("visa").
						firstName("Dummy first").
						lastName("Dummy lastname").
						expirationMonth(11).
						expirationYear(2019).
						cardNumber("4417119669820331").
						cvv2("123").
						build()).
				billingAddress(BillingAddress.builder().
						address("address").
						city("Johnstown").
						countryCode("US").
						postalCode("43210").
						state("OH")
						.build()).
				transactionDescription("Transaction description").
				build();
	}
}
