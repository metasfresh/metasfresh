package de.metas.paypalplus.controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import de.metas.paypalplus.PayPalProperties;
import de.metas.paypalplus.model.PayPalPlusPayment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
@RestController
@RequestMapping(PayPalPlusRestEndpoint.ENDPOINT)
public class PayPalPlusRestController implements PayPalPlusRestEndpoint
{
	private APIContext apiContext;

	public PayPalPlusRestController()
	{
		PayPalProperties payPalProperties = new PayPalProperties(PayPalProperties.CONFIG_SANDBOX_PROPERTIES);
		apiContext = new APIContext(payPalProperties.getClientId(), payPalProperties.getClientSecret(), payPalProperties.getExecutionMode());
	}

	public PayPalPlusRestController(String propertiesFile)
	{
		PayPalProperties payPalProperties = new PayPalProperties(propertiesFile);
		apiContext = new APIContext(payPalProperties.getClientId(), payPalProperties.getClientSecret(), payPalProperties.getExecutionMode());
	}

	private Optional<Payment> processPayment(PayPalPlusPayment payPalPlusPayment, String sale) throws PayPalRESTException
	{
		Amount amount = new Amount();
		amount.setCurrency(payPalPlusPayment.getPaymentCurrency());
		amount.setTotal(payPalPlusPayment.getPaymentAmount());

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPayerInfo(new PayerInfo());

		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent(sale);
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("https://localhost:3000/cancel");
		redirectUrls.setReturnUrl("https://localhost:3000/return");
		payment.setRedirectUrls(redirectUrls);

		return Optional.ofNullable(payment.create(apiContext));
	}

	/**
	 * Reserve a PayPal Plus payment
	 *
	 * @return Payment
	 * @throws PayPalRESTException
	 */
	public Optional<Payment> reservePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalRESTException
	{
		return processPayment(payPalPlusPayment, "authorize");
	}

	@Override public Optional<Payment> capturePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalRESTException
	{
		return processPayment(payPalPlusPayment, "sale");
	}

	@Override public Optional<DetailedRefund> refundCapturedPayment(Payment payment, int transactionNumber) throws PayPalRESTException
	{
		Sale sale = new Sale();
		sale.setId(payment.getTransactions().get(transactionNumber).getRelatedResources().get(transactionNumber).getSale().getId());
		RefundRequest refund = new RefundRequest();
		return Optional.of(sale.refund(apiContext, refund));
	}

	public final static void main(String[] args)
	{
		PayPalPlusRestController controller = new PayPalPlusRestController(PayPalProperties.CONFIG_SANDBOX_PROPERTIES);
		try
		{
			PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", LocalDate.now(), "15.5", "EUR");
			Optional<Payment> payment = controller.reservePayment(payPalPlusPayment);
			System.out.println("Payment reservation state:" + payment.get().getState());
			payment = controller.capturePayment(payPalPlusPayment);
			System.out.println("Payment capturing state:" + payment.get().getState());

		}
		catch (PayPalRESTException e)
		{
			e.printStackTrace();
		}
	}
}
