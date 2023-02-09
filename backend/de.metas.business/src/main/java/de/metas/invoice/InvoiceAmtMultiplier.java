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

package de.metas.invoice;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.currency.Amount;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class InvoiceAmtMultiplier
{
	private final SOTrx soTrx;
	private final boolean isCreditMemo;

	@Getter
	private final boolean isSOTrxAdjusted;
	@Getter
	private final boolean isCreditMemoAdjusted;

	private transient int toRealValueMultiplier;

	@SuppressWarnings("UnstableApiUsage")
	private static final Interner<InvoiceAmtMultiplier> interner = Interners.newStrongInterner();

	@Builder
	private InvoiceAmtMultiplier(
			@NonNull final SOTrx soTrx,
			final boolean isCreditMemo,
			final boolean isSOTrxAdjusted,
			final boolean isCreditMemoAdjusted)
	{
		this.soTrx = soTrx;
		this.isCreditMemo = isCreditMemo;
		this.isSOTrxAdjusted = isSOTrxAdjusted;
		this.isCreditMemoAdjusted = isCreditMemoAdjusted;
	}

	public InvoiceAmtMultiplier intern()
	{
		//noinspection UnstableApiUsage
		return interner.intern(this);
	}

	public Amount convertToRealValue(@NonNull final Amount amount)
	{
		int toRealValueMultiplier = getToRealValueMultiplier();
		return toRealValueMultiplier > 0 ? amount : amount.negate();
	}

	public Money convertToRealValue(@NonNull final Money money)
	{
		int toRealValueMultiplier = getToRealValueMultiplier();
		return toRealValueMultiplier > 0 ? money : money.negate();
	}

	public boolean isNegateToConvertToRealValue()
	{
		return getToRealValueMultiplier() < 0;
	}

	private int getToRealValueMultiplier()
	{
		int toRealValueMultiplier = this.toRealValueMultiplier;
		if (toRealValueMultiplier == 0)
		{
			toRealValueMultiplier = this.toRealValueMultiplier = computeToRealValueMultiplier();
		}
		return toRealValueMultiplier;
	}

	private int computeToRealValueMultiplier()
	{
		int multiplier = 1;

		// Adjust by SOTrx if needed
		if (!isSOTrxAdjusted)
		{
			final int multiplierAP = soTrx.isPurchase() ? -1 : +1;
			multiplier *= multiplierAP;
		}

		// Adjust by Credit Memo if needed
		if (!isCreditMemoAdjusted)
		{
			final int multiplierCM = isCreditMemo ? -1 : +1;
			multiplier *= multiplierCM;
		}

		return multiplier;
	}

	public Money fromNotAdjustedAmount(@NonNull final Money money)
	{
		int multiplier = computeFromNotAdjustedAmountMultiplier();
		return multiplier > 0 ? money : money.negate();
	}

	private int computeFromNotAdjustedAmountMultiplier()
	{
		int multiplier = 1;

		// Do we have to SO adjust?
		if (isSOTrxAdjusted)
		{
			final int multiplierAP = soTrx.isPurchase() ? -1 : +1;
			multiplier *= multiplierAP;
		}

		// Do we have to adjust by Credit Memo?
		if (isCreditMemoAdjusted)
		{
			final int multiplierCM = isCreditMemo ? -1 : +1;
			multiplier *= multiplierCM;
		}
		return multiplier;
	}

}
