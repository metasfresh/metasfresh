package de.metas.paypalplus.controller;

import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;

import de.metas.money.CurrencyRepository;
import de.metas.paypalplus.logs.PayPalLogRepository;
import de.metas.paypalplus.processor.PayPalPaymentProcessor;

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

public class PayPalCheckoutManualTest2
{
	public static void main(final String[] args) throws Exception
	{
		AdempiereTestHelper.get().init();

		new PayPalCheckoutManualTest2().run();
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Done");
	}

	private final PayPalPaymentProcessor processor;

	private PayPalCheckoutManualTest2()
	{
		processor = new PayPalPaymentProcessor(
				new TestPayPalConfigProvider(),
				new PayPalLogRepository(Optional.empty()),
				new CurrencyRepository());
	}

	private void run()
	{
	}

}
