package de.metas.payment.paypal;

import java.net.URL;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.IClientDAO;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.payments.Capture;

import de.metas.currency.Amount;
import de.metas.email.EMail;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.payment.paypal.client.PayPalClientExecutionContext;
import de.metas.payment.paypal.client.PayPalClientService;
import de.metas.payment.paypal.client.PayPalOrder;
import de.metas.payment.paypal.client.PayPalOrderExternalId;
import de.metas.payment.paypal.client.PayPalOrderId;
import de.metas.payment.paypal.client.PayPalOrderService;
import de.metas.payment.paypal.config.PayPalConfig;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.payment.reservation.PaymentReservationRepository;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypal
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

@Service
public class PayPal
{
	//
	// services:
	private final PayPalOrderService payPalOrderService;
	private final PayPalClientService payPalClient;
	private final PaymentReservationRepository paymentReservationRepo;
	private final MailService mailService;
	private final MoneyService moneyService;
	//
	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@VisibleForTesting
	public static final String MAIL_VAR_ApproveURL = "ApproveURL";
	@VisibleForTesting
	public static final String MAIL_VAR_Amount = "Amount";

	public PayPal(
			@NonNull final PayPalOrderService payPalOrderService,
			@NonNull final PayPalClientService payPalClient,
			@NonNull final PaymentReservationRepository paymentReservationRepo,
			@NonNull final MailService mailService,
			@NonNull final MoneyService moneyService)
	{
		this.payPalOrderService = payPalOrderService;
		this.payPalClient = payPalClient;
		this.paymentReservationRepo = paymentReservationRepo;
		this.mailService = mailService;
		this.moneyService = moneyService;
	}

	public PayPalOrder updatePayPalOrderFromAPI(@NonNull final PayPalOrderExternalId externalId)
	{
		final Order apiOrder = payPalClient.getAPIOrderById(externalId);
		return payPalOrderService.save(externalId, apiOrder);
	}

	public PayPalOrder updatePayPalOrderFromAPI(@NonNull final PayPalOrderId id)
	{
		final PayPalOrderExternalId externalId = payPalOrderService.getExternalIdByLocalId(id);
		return updatePayPalOrderFromAPI(externalId);
	}

	public void createPayPalOrderAndRequestPayerApproval(final PaymentReservation reservation)
	{
		final PayPalConfig config = payPalClient.getConfig();

		final OrderRequest apiRequest = toAPIOrderRequest(reservation, config);
		final Order apiOrder = payPalClient.createOrder(apiRequest, toPayPalClientExecutionContext(reservation));
		final PayPalOrder order = payPalOrderService.save(reservation.getId(), apiOrder);

		final URL payerApproveUrl = order.getPayerApproveUrl();
		sendPayerApprovalRequestEmail(reservation, payerApproveUrl, config.getOrderApproveMailTemplateId());
	}

	private OrderRequest toAPIOrderRequest(
			@NonNull final PaymentReservation reservation,
			@NonNull final PayPalConfig config)
	{
		return new OrderRequest()
				.intent("AUTHORIZE")
				.applicationContext(new ApplicationContext()
						.returnUrl(config.getOrderApproveCallbackUrl())
						.cancelUrl(config.getOrderApproveCallbackUrl()))
				.purchaseUnits(ImmutableList.of(
						new PurchaseUnitRequest()
								.amount(toAmountWithBreakdown(reservation.getAmount()))));
	}

	private AmountWithBreakdown toAmountWithBreakdown(final de.metas.money.Money money)
	{
		final Amount amount = moneyService.toAmount(money);
		return new AmountWithBreakdown()
				.value(amount.getAsBigDecimal().toPlainString())
				.currencyCode(amount.getCurrencyCode().toThreeLetterCode());
	}

	private static PayPalClientExecutionContext toPayPalClientExecutionContext(final PaymentReservation reservation)
	{
		return PayPalClientExecutionContext.builder()
				.paymentReservationId(reservation.getId())
				.salesOrderId(reservation.getSalesOrderId())
				.build();
	}

	public void sendPayerApprovalRequestEmail(final PayPalOrderId payPalOrderId)
	{
		final PayPalOrder paypalOrder = payPalOrderService.getById(payPalOrderId);

		final PaymentReservationId reservationId = paypalOrder.getPaymentReservationId();
		final PaymentReservation reservation = paymentReservationRepo.getById(reservationId);

		sendPayerApprovalRequestEmail(
				reservation,
				paypalOrder.getPayerApproveUrl(),
				payPalClient.getConfig().getOrderApproveMailTemplateId());
	}

	private void sendPayerApprovalRequestEmail(
			@NonNull final PaymentReservation reservation,
			@NonNull final URL payerApproveUrl,
			@NonNull final MailTemplateId mailTemplateId)
	{
		final MailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId);
		mailTextBuilder.bpartnerContact(reservation.getPayerContactId());
		mailTextBuilder.customVariable(MAIL_VAR_ApproveURL, payerApproveUrl.toExternalForm());
		mailTextBuilder.customVariable(MAIL_VAR_Amount, moneyService.toTranslatableString(reservation.getAmount()));

		final Mailbox mailbox = findMailbox(reservation);
		final EMail email = mailService.createEMail(mailbox,
				reservation.getPayerEmail(),
				mailTextBuilder.getMailHeader(),
				mailTextBuilder.getFullMailText(),
				mailTextBuilder.isHtml());

		trxManager.runAfterCommit(() -> mailService.send(email));
	}

	private Mailbox findMailbox(@NonNull final PaymentReservation reservation)
	{
		final ClientEMailConfig tenantEmailConfig = clientsRepo.getEMailConfigById(reservation.getClientId());

		return mailService.findMailBox(tenantEmailConfig, reservation.getOrgId());
	}

	public PaymentReservation onOrderApprovedByPayer(@NonNull final PayPalOrderExternalId apiOrderId)
	{
		final PaymentReservation reservation = updateReservationFromAPIOrder(apiOrderId);
		if (reservation.getStatus().isApprovedByPayer())
		{
			authorizePayPalOrder(reservation);
		}

		return reservation;
	}

	private PaymentReservation updateReservationFromAPIOrder(@NonNull final PayPalOrderExternalId apiOrderId)
	{
		final PayPalOrder payPalOrder = updatePayPalOrderFromAPI(apiOrderId);

		final PaymentReservationId reservationId = payPalOrder.getPaymentReservationId();
		final PaymentReservation reservation = paymentReservationRepo.getById(reservationId);
		updateReservationFromPayPalOrder(reservation, payPalOrder);
		paymentReservationRepo.save(reservation);
		return reservation;
	}

	public void authorizePayPalOrder(final PaymentReservation reservation)
	{
		reservation.getStatus().assertApprovedByPayer();

		PayPalOrder paypalOrder = payPalOrderService.getByReservationId(reservation.getId());
		final Order apiOrder = payPalClient.authorizeOrder(paypalOrder.getExternalId(), toPayPalClientExecutionContext(reservation));

		paypalOrder = payPalOrderService.save(reservation.getId(), apiOrder);
		if (!paypalOrder.isAuthorized())
		{
			throw new AdempiereException("Not authorized: " + paypalOrder);
		}

		reservation.changeStatusTo(paypalOrder.getStatus().toPaymentReservationStatus());
		paymentReservationRepo.save(reservation);
	}

	public void captureMoney(final PaymentReservation reservation, final Money money)
	{
		PayPalOrder payPalOrder = payPalOrderService.getByReservationId(reservation.getId());
		final Boolean finalCapture = null;
		final Capture apiCapture = payPalClient.captureOrder(
				payPalOrder.getAuthorizationId(),
				moneyService.toAmount(money),
				finalCapture,
				toPayPalClientExecutionContext(reservation));

		payPalOrder = updatePayPalOrderFromAPI(payPalOrder.getExternalId());
		updateReservationFromPayPalOrder(reservation, payPalOrder);

		// TODO
		// toMoney(apiCapture.amount());
		// apiCapture.status();
		// apiCapture.statusDetails();
		// reservation.captureAmount(amount);
	}

	private static void updateReservationFromPayPalOrder(
			@NonNull final PaymentReservation reservation,
			@NonNull final PayPalOrder payPalOrder)
	{
		reservation.changeStatusTo(payPalOrder.getStatus().toPaymentReservationStatus());
	}

}
