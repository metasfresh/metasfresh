package de.metas.paypalplus.controller;

import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import de.metas.paypalplus.PayPalProperties;
import de.metas.paypalplus.model.*;
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

	private PaymentStatus processPayment(PayPalPlusPayment payPalPlusPayment, String intent) throws PayPalPlusException
	{
		Details details = new Details();
		details.setShipping(payPalPlusPayment.getPaymentAmount().getPaymentDetails().getShippingTax());
		details.setSubtotal(payPalPlusPayment.getPaymentAmount().getPaymentDetails().getSubTotal());
		details.setTax(payPalPlusPayment.getPaymentAmount().getPaymentDetails().getTax());

		Amount amount = new Amount();
		amount.setCurrency(payPalPlusPayment.getPaymentCurrency());
		amount.setTotal(payPalPlusPayment.getPaymentAmount().getTotal());
		amount.setDetails(details);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription(payPalPlusPayment.getTransactionDescription());

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Address billingAddress = new Address();
		billingAddress.setCity(payPalPlusPayment.getBillingAddress().getCity());
		billingAddress.setCountryCode(payPalPlusPayment.getBillingAddress().getCountryCode());
		billingAddress.setLine1(payPalPlusPayment.getBillingAddress().getAddress());
		billingAddress.setPostalCode(payPalPlusPayment.getBillingAddress().getPostalCode());
		billingAddress.setState(payPalPlusPayment.getBillingAddress().getState());

		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setCvv2(payPalPlusPayment.getCreditCard().getCvv2());
		creditCard.setExpireMonth(payPalPlusPayment.getCreditCard().getExpirationMonth());
		creditCard.setExpireYear(payPalPlusPayment.getCreditCard().getExpirationYear());
		creditCard.setFirstName(payPalPlusPayment.getCreditCard().getFirstName());
		creditCard.setLastName(payPalPlusPayment.getCreditCard().getLastName());
		creditCard.setNumber(payPalPlusPayment.getCreditCard().getCardNumber());
		creditCard.setType(payPalPlusPayment.getCreditCard().getCardType());

		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);

		List<FundingInstrument> fundingInstruments = new ArrayList<>();
		fundingInstruments.add(fundingInstrument);

		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstruments);
		payer.setPaymentMethod("credit_card");

		Payment payment = new Payment();
		payment.setIntent(intent);
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("https://localhost:3000/cancel");
		redirectUrls.setReturnUrl("https://localhost:3000/return");
		payment.setRedirectUrls(redirectUrls);
		Payment resultedPayment;
		try
		{
			resultedPayment = payment.create(apiContext);
		}
		catch (PayPalRESTException e)
		{
			throw new PayPalPlusException(e.getMessage());
		}
		String authorizationId = "";
		if (resultedPayment.getTransactions().size() > 0)
			if (resultedPayment.getTransactions().get(0).getRelatedResources() != null &&
					resultedPayment.getTransactions().get(0).getRelatedResources().size() > 0)
				if (resultedPayment.getTransactions().get(0)
						.getRelatedResources().get(0).getAuthorization() != null)
					authorizationId = resultedPayment.getTransactions().get(0)
							.getRelatedResources().get(0).getAuthorization().getId();
		return new PaymentStatus(resultedPayment.getId(), authorizationId, resultedPayment.getState());
	}

	/**
	 * Reserve a PayPal Plus payment
	 *
	 * @return Payment
	 * @throws PayPalPlusException
	 */
	public PaymentStatus authorizePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalPlusException
	{
		return processPayment(payPalPlusPayment, "authorize");
	}


	@Override public PaymentStatus capturePayment(PayPalPlusPayment payPalPlusPayment) throws PayPalPlusException
	{
		return processPayment(payPalPlusPayment, "sale");
	}

	@Override public String cancelPayment(String authorizationId, String reason) throws PayPalPlusException
	{
		Authorization authorization;
		Authorization returnAuthorization;
		try
		{
			authorization = Authorization.get(apiContext, authorizationId);
			returnAuthorization = authorization.doVoid(apiContext);
		}
		catch (PayPalRESTException e)
		{
			throw new PayPalPlusException(e.getMessage());
		}

		return returnAuthorization.getState();
	}

	public final static void main(String[] args)
	{
		PayPalPlusRestController controller = new PayPalPlusRestController(PayPalProperties.CONFIG_SANDBOX_PROPERTIES);
		try
		{
			PayPalPlusPayment payPalPlusPayment = PayPalPlusPayment.builder().
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
					creditCard(de.metas.paypalplus.model.CreditCard.builder().
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
			PaymentStatus paymentStatus = controller.authorizePayment(payPalPlusPayment);
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
