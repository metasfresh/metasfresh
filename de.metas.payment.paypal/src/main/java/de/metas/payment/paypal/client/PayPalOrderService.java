package de.metas.payment.paypal.client;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import de.metas.payment.reservation.PaymentReservationId;
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
public class PayPalOrderService
{
	private final PayPalOrderRepository paypalOrderRepo;

	public PayPalOrderService(@NonNull final PayPalOrderRepository paypalOrderRepository)
	{
		this.paypalOrderRepo = paypalOrderRepository;
	}

	public PayPalOrder getById(@NonNull final PayPalOrderId id)
	{
		return paypalOrderRepo.getById(id);
	}

	public PayPalOrder getByReservationId(@NonNull final PaymentReservationId reservationId)
	{
		return getByReservationIdIfExists(reservationId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @PayPal_Order_ID@: " + reservationId));
	}

	public PayPalOrder getByExternalId(@NonNull final PayPalOrderExternalId externalId)
	{
		return paypalOrderRepo.getByExternalId(externalId);
	}

	public Optional<PayPalOrder> getByReservationIdIfExists(@NonNull final PaymentReservationId reservationId)
	{
		return paypalOrderRepo.getByReservationId(reservationId);
	}

	public PayPalOrder create(@NonNull final PaymentReservationId reservationId)
	{
		return paypalOrderRepo.create(reservationId);
	}

	public PayPalOrder save(
			@NonNull final PayPalOrderId id,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		return paypalOrderRepo.save(id, apiOrder);
	}

	public PayPalOrder markRemoteDeleted(@NonNull final PayPalOrderId id)
	{
		return paypalOrderRepo.markRemoteDeleted(id);
	}

}
