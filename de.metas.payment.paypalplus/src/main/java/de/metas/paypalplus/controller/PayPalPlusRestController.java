package de.metas.paypalplus.controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import de.metas.paypalplus.PayPalProperties;
import de.metas.paypalplus.model.PayPalPlusException;
import de.metas.paypalplus.model.PayPalPlusPayment;
import de.metas.paypalplus.model.PaymentStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	private PaymentStatus processPayment(PayPalPlusPayment payPalPlusPayment, String sale) throws PayPalPlusException
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
		try
		{
			payment = payment.create(apiContext);
		}
		catch (PayPalRESTException e)
		{
			throw new PayPalPlusException(e.getMessage());
		}
		return new PaymentStatus(payment.getState());
	}

	/**
	 * Reserve a PayPal Plus payment
	 *
	 * @return Payment
	 * @throws PayPalPlusException
	 */
	public PaymentStatus reservePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalPlusException
	{
		return processPayment(payPalPlusPayment, "authorize");
	}

	@Override public PaymentStatus capturePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalPlusException
	{
		return processPayment(payPalPlusPayment, "sale");
	}

	@Override public PaymentStatus refundCapturedPayment(String saleId, Integer transactionNumber) throws PayPalPlusException
	{
		Sale sale = new Sale();
		sale.setId(saleId);
		RefundRequest refund = new RefundRequest();
		DetailedRefund detailedRefund;
		try
		{
			detailedRefund = sale.refund(apiContext, refund);
		}
		catch (PayPalRESTException e)
		{
			throw new PayPalPlusException(e.getMessage());
		}

		return new PaymentStatus(detailedRefund.getState());
	}

	public final static void main(String[] args)
	{
		PayPalPlusRestController controller = new PayPalPlusRestController(PayPalProperties.CONFIG_SANDBOX_PROPERTIES);
		try
		{
			PayPalPlusPayment payPalPlusPayment = new PayPalPlusPayment("1", LocalDate.now(), "15.5", "EUR");
			PaymentStatus paymentStatus = controller.reservePayment(payPalPlusPayment);
			System.out.println("Payment reservation state:" + paymentStatus.toString());
			paymentStatus = controller.capturePayment(payPalPlusPayment);
			System.out.println("Payment capturing state:" + paymentStatus.toString());

		}
		catch (PayPalRESTException e)
		{
			e.printStackTrace();
		}
	}
}
