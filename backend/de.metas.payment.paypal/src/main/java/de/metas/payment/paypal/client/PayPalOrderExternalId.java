package de.metas.payment.paypal.client;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
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

/** External PayPal Order ID (which is known by paypal.com). */
@EqualsAndHashCode
public final class PayPalOrderExternalId
{
	@JsonCreator
	public static PayPalOrderExternalId ofString(@NonNull final String orderId)
	{
		return new PayPalOrderExternalId(orderId);
	}

	public static PayPalOrderExternalId ofNullableString(@Nullable final String orderId)
	{
		return !Check.isEmpty(orderId, true) ? new PayPalOrderExternalId(orderId) : null;
	}

	private final String id;

	private PayPalOrderExternalId(final String id)
	{
		Check.assumeNotEmpty(id, "id is not empty");
		this.id = id;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return id;
	}

	public static String toString(@Nullable final PayPalOrderExternalId id)
	{
		return id != null ? id.getAsString() : null;
	}
}
