package de.metas.paypalplus.processor;

import java.io.IOException;
import java.net.URL;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.IClientDAO;
import org.springframework.stereotype.Component;

import com.braintreepayments.http.HttpRequest;
import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.exceptions.HttpException;
import com.google.common.collect.ImmutableList;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersAuthorizeRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;

import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.CurrencyRepository;
import de.metas.payment.PaymentRule;
import de.metas.payment.processor.PaymentProcessor;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationStatus;
import de.metas.paypalplus.PayPalConfig;
import de.metas.paypalplus.controller.PayPalConfigProvider;
import de.metas.paypalplus.logs.PayPalCreateLogRequest;
import de.metas.paypalplus.logs.PayPalCreateLogRequest.PayPalCreateLogRequestBuilder;
import de.metas.paypalplus.logs.PayPalLogRepository;
import de.metas.paypalplus.orders.PayPalOrder;
import de.metas.paypalplus.orders.PayPalOrderService;
import de.metas.util.Services;
import lombok.NonNull;

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

@Component
public class PayPalPaymentProcessor implements PaymentProcessor
{
	private final IMailBL mailService = Services.get(IMailBL.class);
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);
	private final PayPalConfigProvider payPalConfigProvider;
	private final PayPalOrderService payPalOrdersService;
	private final PayPalLogRepository logsRepo;
	private final CurrencyRepository currencyRepo;

	private final PayPalHttpClientFactory payPalHttpClientFactory = new PayPalHttpClientFactory();

	public PayPalPaymentProcessor(
			@NonNull final PayPalConfigProvider payPalConfigProvider,
			@NonNull final PayPalOrderService payPalOrdersService,
			@NonNull final PayPalLogRepository logsRepo,
			@NonNull final CurrencyRepository currencyRepo)
	{
		this.payPalConfigProvider = payPalConfigProvider;
		this.payPalOrdersService = payPalOrdersService;
		this.logsRepo = logsRepo;
		this.currencyRepo = currencyRepo;
	}

	private PayPalHttpClient getClient()
	{
		final PayPalConfig config = getConfig();
		return payPalHttpClientFactory.getPayPalHttpClient(config);
	}

	private PayPalConfig getConfig()
	{
		return payPalConfigProvider.getConfig();
	}

	@Override
	public PaymentRule getPaymentRule()
	{
		return PaymentRule.PayPal;
	}

	@Override
	public boolean canReserveMoney()
	{
		return true;
	}

	@Override
	public void processReservation(@NonNull final PaymentReservation reservation)
	{
		final PaymentReservationStatus status = reservation.getStatus();

		if (PaymentReservationStatus.WAITING_PAYER_APPROVAL.equals(status))
		{
			createPayPalOrderAndRequestPayerApproval(reservation);
		}
		else if (PaymentReservationStatus.APPROVED_BY_PAYER.equals(status))
		{
			authorizePayPalOrder(reservation);
		}
		else if (PaymentReservationStatus.COMPLETED.equals(status))
		{
			throw new AdempiereException("already completed: " + reservation);
		}
		else if (PaymentReservationStatus.VOIDED.equals(status))
		{
			throw new AdempiereException("Request for approval for a voided reservation makes no sense: " + reservation);
		}
		else
		{
			throw new AdempiereException("Unknown status: " + reservation);
		}
	}

	private void createPayPalOrderAndRequestPayerApproval(final PaymentReservation reservation)
	{
		final PayPalConfig config = getConfig();
		final OrdersCreateRequest ordersCreateRequest = createOrdersCreateRequest(reservation, config);
		final HttpResponse<Order> response = executeRequest(ordersCreateRequest, reservation);

		final PayPalOrder order = payPalOrdersService.save(reservation.getId(), response.result());

		final URL payerApproveUrl = order.getPayerApproveUrl();
		sendPayerApprovalRequestEmail(reservation, payerApproveUrl, config.getOrderApproveMailTemplateId());
	}

	private void authorizePayPalOrder(final PaymentReservation reservation)
	{
		PayPalOrder paypalOrder = payPalOrdersService.getByReservationId(reservation.getId());
		final OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(paypalOrder.getExternalId());
		final HttpResponse<Order> response = executeRequest(request, reservation);

		paypalOrder = payPalOrdersService.save(reservation.getId(), response.result());
		if (!paypalOrder.isAuthorized())
		{
			throw new AdempiereException("Not authorized: " + paypalOrder);
		}

		reservation.setStatus(PaymentReservationStatus.COMPLETED);
	}

	private void sendPayerApprovalRequestEmail(
			@NonNull final PaymentReservation reservation,
			@NonNull final URL payerApproveUrl,
			@NonNull final MailTemplateId mailTemplateId)
	{
		final MailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId);
		mailTextBuilder.bpartnerContact(reservation.getPayerContactId());
		mailTextBuilder.customVariable("ApproveURL", payerApproveUrl.toExternalForm());
		mailTextBuilder.customVariable("Amount", TranslatableStrings.amount(reservation.getAmount().toAmount(currencyRepo::getCurrencyCodeById)));

		final Mailbox mailbox = findMailbox(reservation);
		final EMail email = mailService.createEMail(mailbox,
				reservation.getPayerEmail(),
				mailTextBuilder.getMailHeader(),
				mailTextBuilder.getFullMailText(),
				mailTextBuilder.isHtml());

		mailService.send(email);

	}

	private Mailbox findMailbox(@NonNull final PaymentReservation reservation)
	{
		final ClientEMailConfig tenantEmailConfig = clientsRepo.getEMailConfigById(reservation.getClientId());

		return mailService.findMailBox(tenantEmailConfig, reservation.getOrgId());
	}

	private OrdersCreateRequest createOrdersCreateRequest(
			@NonNull final PaymentReservation reservation,
			@NonNull final PayPalConfig config)
	{
		final OrderRequest requestBody = new OrderRequest()
				.intent("AUTHORIZE")
				.applicationContext(new ApplicationContext()
						.returnUrl(config.getOrderApproveCallbackUrl())
						.cancelUrl(config.getOrderApproveCallbackUrl()))
				.purchaseUnits(ImmutableList.of(
						new PurchaseUnitRequest()
								.amount(toAmountWithBreakdown(reservation.getAmount()))));

		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(requestBody);

		return request;
	}

	private AmountWithBreakdown toAmountWithBreakdown(final de.metas.money.Money amount)
	{
		return new AmountWithBreakdown()
				.value(amount.getAsBigDecimal().toString())
				.currencyCode(currencyRepo.getCurrencyCodeById(amount.getCurrencyId()).toThreeLetterCode());
	}

	private <T> HttpResponse<T> executeRequest(
			@NonNull final HttpRequest<T> request,
			@NonNull final PaymentReservation context)
	{
		final PayPalCreateLogRequestBuilder log = PayPalCreateLogRequest.builder()
				.salesOrderId(context.getSalesOrderId())
				.paymentReservationId(context.getId());

		try
		{
			log.request(request);

			final PayPalHttpClient client = getClient();
			final HttpResponse<T> response = client.execute(request);
			log.response(response);
			return response;
		}
		catch (final HttpException ex)
		{
			log.response(ex);
			throw AdempiereException.wrapIfNeeded(ex);
		}
		catch (final IOException ex)
		{
			log.response(ex);
			throw AdempiereException.wrapIfNeeded(ex);
		}
		finally
		{
			logsRepo.log(log.build());
		}
	}

	@Override
	public void captureMoney()
	{
	}

}
