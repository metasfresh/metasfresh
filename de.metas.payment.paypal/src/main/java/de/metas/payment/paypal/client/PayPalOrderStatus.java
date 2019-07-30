package de.metas.payment.paypal.client;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.payment.reservation.PaymentReservationStatus;
import lombok.EqualsAndHashCode;
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

@EqualsAndHashCode
public final class PayPalOrderStatus
{
	@JsonCreator
	public static PayPalOrderStatus ofCode(@NonNull final String code)
	{
		final PayPalOrderStatus status = cacheByCode.get(code);
		return status != null ? status : new PayPalOrderStatus(code);
	}

	public static final PayPalOrderStatus UNKNOWN = new PayPalOrderStatus("-");
	public static final PayPalOrderStatus CREATED = new PayPalOrderStatus("CREATED");
	public static final PayPalOrderStatus APPROVED = new PayPalOrderStatus("APPROVED");
	public static final PayPalOrderStatus COMPLETED = new PayPalOrderStatus("COMPLETED");
	public static final PayPalOrderStatus REMOTE_DELETED = new PayPalOrderStatus("MF_REMOTE_DELETED");

	private static final ImmutableMap<String, PayPalOrderStatus> cacheByCode = Maps.uniqueIndex(
			Arrays.asList(UNKNOWN, CREATED, APPROVED, COMPLETED, REMOTE_DELETED),
			PayPalOrderStatus::getCode);

	private final String code;

	private PayPalOrderStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getCode();
	}

	@JsonValue
	public String getCode()
	{
		return code;
	}

	public boolean isApproved()
	{
		return APPROVED.equals(this);
	}

	public PaymentReservationStatus toPaymentReservationStatus()
	{
		if (this == CREATED)
		{
			return PaymentReservationStatus.WAITING_PAYER_APPROVAL;
		}
		else if (this == APPROVED)
		{
			return PaymentReservationStatus.APPROVED_BY_PAYER;
		}
		else if (this == COMPLETED)
		{
			return PaymentReservationStatus.COMPLETED;
		}
		else if (this == REMOTE_DELETED)
		{
			return PaymentReservationStatus.WAITING_PAYER_APPROVAL;
		}
		else
		{
			throw new AdempiereException("Cannot convert " + this + " to " + PaymentReservationStatus.class);
		}
	}
}
