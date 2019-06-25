package de.metas.paypalplus.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Authorization;
import com.paypal.api.payments.Capture;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import de.metas.logging.LogManager;
import de.metas.paypalplus.PayPalConfig;
import de.metas.paypalplus.model.BillingAddress;
import de.metas.paypalplus.model.PaymentAmount;
import de.metas.paypalplus.model.PaymentCaptureRequest;
import de.metas.paypalplus.model.PaymentCaptureResponse;
import de.metas.paypalplus.model.PaymentReservationRequest;
import de.metas.paypalplus.model.PaymentReservationResponse;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;

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
public class PayPalPlusService implements PayPalPlusRestEndpoint
{

	private static final Logger logger = LogManager.getLogger(PayPalPlusService.class);

	private static final String PAYMENT_METHOD_CREDIT_CARD = "credit_card";
	private static final String INTENT_AUTHORIZE = "authorize";

	private final PayPalConfigProvider configProvider;

	public PayPalPlusService(@NonNull final PayPalConfigProvider configProvider)
	{
		this.configProvider = configProvider;
		logger.info("Using: {}", configProvider);
	}

	@Override
	public PaymentReservationResponse reservePayment(@NonNull final PaymentReservationRequest request)
	{
		return processPayment(request, INTENT_AUTHORIZE);
	}

	@Override
	public PaymentCaptureResponse capturePayment(@NonNull final PaymentCaptureRequest request)
	{
		final Capture apiRequest = toAPIPaymentCaptureRequest(request);

		try
		{
			final APIContext apiContext = createAPIContext();
			final Authorization authorization = Authorization.get(apiContext, request.getAuthorizationId());
			final Capture apiResponse = authorization.capture(apiContext, apiRequest);

			return toPaymentCaptureResponse(apiResponse);
		}
		catch (final PayPalRESTException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.setParameter("request", request);
		}
	}

	private static Capture toAPIPaymentCaptureRequest(final PaymentCaptureRequest request)
	{
		return new Capture()
				.setAmount(new Amount()
						.setCurrency(request.getCurrency())
						.setTotal(request.getAmount().toPlainString()))
				.setIsFinalCapture(request.isFinalCapture());
	}

	private static PaymentCaptureResponse toPaymentCaptureResponse(final Capture apiResponse)
	{
		return PaymentCaptureResponse.builder()
				.captureId(apiResponse.getId())
				.currency(apiResponse.getAmount().getCurrency())
				.amount(new BigDecimal(apiResponse.getAmount().getTotal()))
				.finalCapture(CoalesceUtil.coalesce(apiResponse.getIsFinalCapture(), Boolean.FALSE))
				.state(apiResponse.getState())
				.reasonCode(apiResponse.getReasonCode())
				.build();
	}

	@Override
	public String cancelPayment(@NonNull final String authorizationId)
	{
		try
		{
			final APIContext apiContext = createAPIContext();
			final Authorization request = Authorization.get(apiContext, authorizationId);
			final Authorization response = request.doVoid(apiContext);
			return response.getState();
		}
		catch (final PayPalRESTException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private APIContext createAPIContext()
	{
		final PayPalConfig config = configProvider.getConfig();

		final APIContext apiContext = new APIContext(
				config.getClientId(),
				config.getClientSecret(),
				config.getExecutionMode());
		apiContext.setRequestId(UUID.randomUUID().toString());

		return apiContext;
	}

	private PaymentReservationResponse processPayment(
			@NonNull final PaymentReservationRequest request,
			final String intent)
	{
		try
		{
			final Payment apiRequest = createAPIPaymentDraft(request, intent);
			final APIContext apiContext = createAPIContext();
			final Payment apiResponse = apiRequest.create(apiContext);
			return toPaymentReservationResponse(apiResponse);
		}
		catch (final PayPalRESTException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private static Payment createAPIPaymentDraft(
			@NonNull final PaymentReservationRequest payment,
			final String intent)
	{
		//
		// Payer
		final Payer payer;
		{
			final CreditCard creditCard = createAPICreditCard(
					payment.getCreditCard(),
					payment.getBillingAddress());

			final FundingInstrument fundingInstrument = new FundingInstrument()
					.setCreditCard(creditCard);

			payer = new Payer();
			payer.setFundingInstruments(ImmutableList.of(fundingInstrument));
			payer.setPaymentMethod(PAYMENT_METHOD_CREDIT_CARD); // FIXME hardcoded
		}

		//
		// Transaction
		final Transaction transaction;
		{
			transaction = new Transaction();
			transaction.setAmount(createAPIAmount(payment.getPaymentAmount()));
			transaction.setDescription(payment.getTransactionDescription());
		}

		//
		// Redirect URLS
		// final RedirectUrls redirectUrls = new RedirectUrls();
		// {
		// redirectUrls.setCancelUrl("https://localhost:3000/cancel"); // FIXME hardcoded
		// redirectUrls.setReturnUrl("https://localhost:3000/return"); // FIXME hardcoded
		// }

		//
		// Payment
		return new Payment()
				.setIntent(intent)
				.setPayer(payer)
				.setTransactions(ImmutableList.of(transaction))
		// .setRedirectUrls(redirectUrls)
		;
	}

	private static Amount createAPIAmount(@NonNull final PaymentAmount paymentAmount)
	{
		return new Amount()
				.setCurrency(paymentAmount.getCurrency())
				.setTotal(paymentAmount.getTotal().toPlainString())
				.setDetails(new Details()
						.setShipping(paymentAmount.getShippingTax().toPlainString())
						.setSubtotal(paymentAmount.getSubTotal().toPlainString())
						.setTax(paymentAmount.getTax().toPlainString()));
	}

	private static CreditCard createAPICreditCard(
			@NonNull final de.metas.paypalplus.model.CreditCard creditCard,
			@NonNull final BillingAddress address)
	{

		return new CreditCard()
				.setType(creditCard.getCardType())
				.setNumber(creditCard.getCardNumber())
				.setExpireMonth(creditCard.getExpirationDate().getMonthValue())
				.setExpireYear(creditCard.getExpirationDate().getYear())
				.setCvv2(creditCard.getCvv2())
				//
				.setFirstName(creditCard.getFirstName())
				.setLastName(creditCard.getLastName())
				//
				.setBillingAddress(createAPIAddress(address))
		//
		;
	}

	private static Address createAPIAddress(@NonNull final BillingAddress address)
	{
		final Address apiAddress = new Address();
		apiAddress.setLine1(address.getAddress1());
		apiAddress.setLine2(address.getAddress2());
		apiAddress.setPostalCode(address.getPostalCode());
		apiAddress.setCity(address.getCity());
		apiAddress.setState(address.getState());
		apiAddress.setCountryCode(address.getCountryCode());
		return apiAddress;
	}

	private static PaymentReservationResponse toPaymentReservationResponse(final Payment apiResponse)
	{
		final List<Transaction> transactions = apiResponse.getTransactions();
		if (transactions == null || transactions.isEmpty())
		{
			throw new AdempiereException("No transactions: " + apiResponse);
		}

		final Transaction transaction = transactions.get(0);
		if (transaction.getRelatedResources() == null
				|| transaction.getRelatedResources().isEmpty())
		{
			throw new AdempiereException("No related resources: " + apiResponse);
		}

		final Authorization authorization = transaction.getRelatedResources().get(0).getAuthorization();
		if (authorization == null)
		{
			throw new AdempiereException("No authorization: " + apiResponse);
		}

		return PaymentReservationResponse.builder()
				.paymentId(apiResponse.getId())
				.authorizationId(authorization.getId())
				.paymentState(apiResponse.getState())
				.build();
	}
}
