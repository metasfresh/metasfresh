package de.metas.payment.paypal.client;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

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

/** Internal PayPal Order ID (which is known only by metasfresh). */
@Value
public class PayPalOrderId implements RepoIdAware
{
	@JsonCreator
	public static PayPalOrderId ofRepoId(final int repoId)
	{
		return new PayPalOrderId(repoId);
	}

	public static PayPalOrderId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new PayPalOrderId(repoId) : null;
	}

	public static int toRepoId(@Nullable final PayPalOrderId orderId)
	{
		return orderId != null ? orderId.getRepoId() : -1;
	}

	int repoId;

	private PayPalOrderId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PayPal_Order_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
