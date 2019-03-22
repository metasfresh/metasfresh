package de.metas.paypalplus.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import de.metas.paypalplus.PayPalProperties;
import de.metas.paypalplus.model.PayPalPlusPayment;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Optional;

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
		PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", LocalDate.now(), "15.5", "EUR");
		Optional<Payment> payment = Optional.empty();
		try
		{
			payment = controller.capturePayment(payPalPlusPayment);
		}
		catch (PayPalRESTException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(payment.get().getState(), "created");
		System.out.println("Payment capturing state:" + payment.get().getState());
	}

	@Test
	public void reservePayment()
	{
		PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", LocalDate.now(), "15.5", "EUR");
		Optional<Payment> payment = Optional.empty();
		try
		{
			payment = controller.reservePayment(payPalPlusPayment);
		}
		catch (PayPalRESTException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(payment.get().getState(), "created");
		System.out.println("Payment reservation state:" + payment.get().getState());
	}

	@Test
	@Ignore
	public void refundPayment()
	{
		PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", LocalDate.now(), "15.5", "EUR");
		Optional<Payment> payment = Optional.empty();
		try
		{
			payment = controller.reservePayment(payPalPlusPayment);
		}
		catch (PayPalRESTException e)
		{
			fail();
			e.printStackTrace();
		}
		assertEquals(payment.get().getState(), "created");
		assertEquals(payment.get().getTransactions().size(), 1);
		try
		{
			controller.refundCapturedPayment(payment.get(), 0);
		}
		catch (PayPalRESTException e)
		{
			fail();
			e.printStackTrace();
		}
	}
}
