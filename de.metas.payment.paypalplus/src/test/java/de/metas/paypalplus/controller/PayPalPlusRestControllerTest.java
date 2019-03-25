package de.metas.paypalplus.controller;

import com.paypal.base.rest.PayPalRESTException;
import de.metas.paypalplus.PayPalProperties;
import de.metas.paypalplus.model.PayPalPlusException;
import de.metas.paypalplus.model.PayPalPlusPayment;
import de.metas.paypalplus.model.PaymentStatus;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
		PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", "1", LocalDate.now(), "15.5", "EUR");
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
		assertEquals(paymentStatus.getPaymentState(), "created");
	}

	@Test
	public void reservePayment()
	{
		PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", "1", LocalDate.now(), "15.5", "EUR");
		PaymentStatus paymentStatus = null;
		try
		{
			paymentStatus = controller.reservePayment(payPalPlusPayment);
		}
		catch (PayPalPlusException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(paymentStatus.getPaymentState(), "created");
	}

	@Test
	@Ignore
	public void refundPayment()
	{
		PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", "1", LocalDate.now(), "15.5", "EUR");
		PaymentStatus paymentStatus = null;
		try
		{
			paymentStatus = controller.capturePayment(payPalPlusPayment);
		}
		catch (PayPalPlusException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(paymentStatus.getPaymentState(), "created");
		try
		{
			controller.refundCapturedPayment(paymentStatus.getPaymentId(), "Refund reason - something");
		}
		catch (PayPalPlusException e)
		{
			fail();
			e.printStackTrace();
		}
	}
}
