package de.metas.payment.paypal.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.payment.reservation.PaymentReservationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder(toBuilder = true)
public class PayPalOrder
{
	@Nullable
	PayPalOrderId id;

	@NonNull
	PaymentReservationId paymentReservationId;

	/** i.e. the paypal order id */
	@NonNull
	PayPalOrderExternalId externalId;

	@NonNull
	PayPalOrderStatus status;

	@Nullable
	PayPalOrderAuthorizationId authorizationId;
	@Nullable
	String payerApproveUrlString;

	@Nullable
	String bodyAsJson;

	public boolean isAuthorized()
	{
		return authorizationId != null;
	}

	public URL getPayerApproveUrl()
	{
		if (payerApproveUrlString == null)
		{
			throw new AdempiereException("No payer url");
		}

		try
		{
			return new URL(payerApproveUrlString);
		}
		catch (MalformedURLException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public PayPalOrder withId(@NonNull final PayPalOrderId id)
	{
		if (Objects.equals(this.id, id))
		{
			return this;
		}
		else
		{
			return toBuilder().id(id).build();
		}
	}
}
