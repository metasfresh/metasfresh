package de.metas.paypalplus.processor;

import org.springframework.stereotype.Service;

import de.metas.payment.reservation.PaymentReservation;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.payment.reservation.PaymentReservationService;
import de.metas.paypalplus.orders.PayPalOrder;
import de.metas.paypalplus.orders.PayPalOrderId;
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

@Service
public class PayPalCallbacksService
{
	private final PaymentReservationService paymentReservationService;
	private final PayPalPaymentProcessor payPalPaymentProcessor;

	public PayPalCallbacksService(
			@NonNull final PaymentReservationService paymentReservationService,
			@NonNull final PayPalPaymentProcessor payPalPaymentProcessor)
	{
		this.paymentReservationService = paymentReservationService;
		this.payPalPaymentProcessor = payPalPaymentProcessor;
	}

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
		reservation.changeStatusTo(payPalOrder.getStatus().toPaymentReservationStatus());
		paymentReservationService.save(reservation);
		return reservation;
	}

}
