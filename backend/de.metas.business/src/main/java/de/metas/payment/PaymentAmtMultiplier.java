/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.payment;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.currency.Amount;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class PaymentAmtMultiplier
{
	private final PaymentDirection paymentDirection;
	private final boolean isOutboundAdjusted;

	@SuppressWarnings("UnstableApiUsage")
	private static final Interner<PaymentAmtMultiplier> interner = Interners.newStrongInterner();

	@Builder
	public PaymentAmtMultiplier(
			@NonNull final PaymentDirection paymentDirection,
			final boolean isOutboundAdjusted)
	{
		this.paymentDirection = paymentDirection;
		this.isOutboundAdjusted = isOutboundAdjusted;
	}

	public PaymentAmtMultiplier intern()
	{
		//noinspection UnstableApiUsage
		return interner.intern(this);
	}

	public Amount convertToRealValue(@NonNull final Amount amount)
	{
		int toRealValueMultiplier = computeToRealValueMultiplier();
		return toRealValueMultiplier > 0 ? amount : amount.negate();
	}

	private int computeToRealValueMultiplier()
	{
		int multiplier = 1;

		// Adjust by Outbound if needed
		if (!isOutboundAdjusted)
		{
			final int multiplierOutbound = paymentDirection.isOutboundPayment() ? -1 : +1;
			multiplier *= multiplierOutbound;
		}

		return multiplier;
	}
}
