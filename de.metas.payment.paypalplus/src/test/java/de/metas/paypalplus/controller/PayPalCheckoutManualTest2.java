package de.metas.paypalplus.controller;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;
import java.util.Optional;

import org.adempiere.service.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;

import com.google.common.collect.ImmutableList;

import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.PaymentRule;
import de.metas.payment.processor.PaymentProcessorService;
import de.metas.payment.reservation.PaymentReservationCreateRequest;
import de.metas.payment.reservation.PaymentReservationRepository;
import de.metas.payment.reservation.PaymentReservationService;
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

	private final OrgId orgId = OrgId.ofRepoId(1);
	private final CurrencyId currencyId;

	private final PaymentReservationService paymentReservationService;

	private PayPalCheckoutManualTest2()
	{
		final PayPalPaymentProcessor payPalProcessor = new PayPalPaymentProcessor(
				new TestPayPalConfigProvider(),
				new PayPalLogRepository(Optional.empty()),
				new CurrencyRepository());

		final PaymentProcessorService paymentProcessors = new PaymentProcessorService(Optional.of(ImmutableList.of(payPalProcessor)));

		this.paymentReservationService = new PaymentReservationService(
				new PaymentReservationRepository(),
				paymentProcessors);

		//
		currencyId = createCurrency("EUR");
	}

	private static CurrencyId createCurrency(String currencyCode)
	{
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code(currencyCode);
		currency.setStdPrecision(2);
		saveRecord(currency);
		return CurrencyId.ofRepoId(currency.getC_Currency_ID());
	}

	private void run()
	{
		final OrderId salesOrderId = OrderId.ofRepoId(123);

		paymentReservationService.create(PaymentReservationCreateRequest.builder()
				.orgId(orgId)
				.amount(Money.of(100, currencyId))
				.salesOrderId(salesOrderId)
				.dateTrx(LocalDate.now())
				.paymentRule(PaymentRule.PayPal)
				.build());
	}

}
