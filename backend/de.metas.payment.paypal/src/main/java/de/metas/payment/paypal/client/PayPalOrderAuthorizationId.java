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

@EqualsAndHashCode
public final class PayPalOrderAuthorizationId
{
	@JsonCreator
	public static PayPalOrderAuthorizationId ofString(@NonNull final String authId)
	{
		return new PayPalOrderAuthorizationId(authId);
	}

	public static PayPalOrderAuthorizationId ofNullableString(@Nullable final String authId)
	{
		return !Check.isEmpty(authId, true) ? new PayPalOrderAuthorizationId(authId) : null;
	}

	private final String id;

	private PayPalOrderAuthorizationId(final String id)
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

	public static String toString(@Nullable final PayPalOrderAuthorizationId id)
	{
		return id != null ? id.getAsString() : null;
	}
}
