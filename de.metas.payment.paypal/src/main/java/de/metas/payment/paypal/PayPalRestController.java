package de.metas.payment.paypal;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.annotations.VisibleForTesting;

import de.metas.Profiles;
import de.metas.payment.paypal.client.PayPalOrder;
import de.metas.payment.paypal.client.PayPalOrderId;
import de.metas.payment.paypal.processor.PayPalPaymentProcessor;
import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.payment.reservation.PaymentReservationService;
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

@RestController
@Profile(Profiles.PROFILE_Webui)
@RequestMapping("/paypal")
public class PayPalRestController
{
	private final PaymentReservationService paymentReservationService;
	private final PayPalPaymentProcessor payPalPaymentProcessor;

	public PayPalRestController(
			@NonNull final PaymentReservationService paymentReservationService,
			@NonNull final PayPalPaymentProcessor payPalPaymentProcessor)
	{
		this.paymentReservationService = paymentReservationService;
		this.payPalPaymentProcessor = payPalPaymentProcessor;
	}

	@RequestMapping("/approved")
	public void notifyOrderApprovedByPayer(
			@RequestParam(value = "token", required = true) final String token)
	{
		final PayPalOrderId apiOrderId = PayPalOrderId.ofString(token);
		onOrderApprovedByPayer(apiOrderId);
	}

	@VisibleForTesting
	public PaymentReservation onOrderApprovedByPayer(@NonNull final PayPalOrderId apiOrderId)
	{
		final PaymentReservation reservation = updateReservationFromAPIOrder(apiOrderId);
		if (reservation.getStatus().isApprovedByPayer())
		{
			payPalPaymentProcessor.authorizePayPalOrder(reservation);
			paymentReservationService.save(reservation);
		}

		return reservation;
	}

	private PaymentReservation updateReservationFromAPIOrder(@NonNull final PayPalOrderId apiOrderId)
	{
		final PayPalOrder payPalOrder = payPalPaymentProcessor.updatePayPalOrderFromAPI(apiOrderId);

		final PaymentReservationId reservationId = payPalOrder.getPaymentReservationId();
		final PaymentReservation reservation = paymentReservationService.getById(reservationId);
		PayPalPaymentProcessor.updateReservationFromPayPalOrder(reservation, payPalOrder);
		paymentReservationService.save(reservation);
		return reservation;
	}

}
