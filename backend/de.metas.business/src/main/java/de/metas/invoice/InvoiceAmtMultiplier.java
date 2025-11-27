/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

@EqualsAndHashCode
public final class InvoiceAmtMultiplier
{
	private final SOTrx soTrx;

	@Getter
	private final boolean isCreditMemo;

	@Getter
	private final boolean isAPAdjusted;
	@Getter
	private final boolean isCreditMemoAdjusted;

	private transient int toRealValueMultiplier;

	@SuppressWarnings("UnstableApiUsage")
	private static final Interner<InvoiceAmtMultiplier> interner = Interners.newStrongInterner();

	public static InvoiceAmtMultiplier nonAdjustedFor(@NonNull final InvoiceDocBaseType invoiceDocBaseType)
	{
		return create(invoiceDocBaseType.getSoTrx(), invoiceDocBaseType.isCreditMemo(), false);
	}

	public static InvoiceAmtMultiplier adjustedFor(@NonNull final InvoiceDocBaseType invoiceDocBaseType)
	{
		return create(invoiceDocBaseType.getSoTrx(), invoiceDocBaseType.isCreditMemo(), true);
	}

	/**
	 * @param adjusted indicates if the amount to be multiplied here is assumed to be already adjusted.
	 *                 {@code true} means that actually no multiplication is necessary.
	 */
	public static InvoiceAmtMultiplier create(@NonNull final SOTrx soTrx,
											  final boolean isCreditMemo,
											  final boolean adjusted)
	{
		return InvoiceAmtMultiplier.builder()
				.soTrx(soTrx)
				.isAPAdjusted(adjusted)
				.isCreditMemo(isCreditMemo)
				.isCreditMemoAdjusted(adjusted)
				.build()
				.intern();
	}

	@Builder(toBuilder = true)
	private InvoiceAmtMultiplier(
			@NonNull final SOTrx soTrx,
			final boolean isCreditMemo,
			final boolean isAPAdjusted,
			final boolean isCreditMemoAdjusted)
	{
		this.soTrx = soTrx;
		this.isCreditMemo = isCreditMemo;
		this.isAPAdjusted = isAPAdjusted;
		this.isCreditMemoAdjusted = isCreditMemoAdjusted;
	}

	public InvoiceAmtMultiplier intern()
	{
		//noinspection UnstableApiUsage
		return interner.intern(this);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		if (soTrx.isAP())
		{
			if (sb.length() > 0)
			{
				sb.append(",");
			}
			sb.append("AP");
		}
		if (isAPAdjusted)
		{
			if (sb.length() > 0)
			{
				sb.append(",");
			}
			sb.append("APAdjusted");
		}
		if (isCreditMemo)
		{
			if (sb.length() > 0)
			{
				sb.append(",");
			}
			sb.append("CM");
		}
		if (isCreditMemoAdjusted)
		{
			if (sb.length() > 0)
			{
				sb.append(",");
			}
			sb.append("CMAdjusted");
		}

		return sb.toString();
	}

	public boolean isAP() {return soTrx.isAP();}

	public Amount convertToRealValue(@NonNull final Amount amount)
	{
		final int toRealValueMultiplier = getToRealValueMultiplier();
		return toRealValueMultiplier > 0 ? amount : amount.negate();
	}

	public Money convertToRealValue(@NonNull final Money money)
	{
		final int toRealValueMultiplier = getToRealValueMultiplier();
		return toRealValueMultiplier > 0 ? money : money.negate();
	}

	public Money convertToRelativeValue(@NonNull final Money realValue)
	{
		final int toRelativeValueMultiplier = getToRelativeValueMultiplier();
		return toRelativeValueMultiplier > 0 ? realValue : realValue.negate();
	}

	public Amount convertToRelativeValue(@NonNull final Amount realValue)
	{
		final int toRelativeValueMultiplier = getToRelativeValueMultiplier();
		return toRelativeValueMultiplier > 0 ? realValue : realValue.negate();
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
		if (!isAPAdjusted)
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

	private int getToRelativeValueMultiplier()
	{
		// NOTE: the relative->real and real->relative value multipliers are the same
		return getToRealValueMultiplier();
	}

	public Money fromNotAdjustedAmount(@NonNull final Money money)
	{
		final int multiplier = computeFromNotAdjustedAmountMultiplier();
		return multiplier > 0 ? money : money.negate();
	}

	private int computeFromNotAdjustedAmountMultiplier()
	{
		int multiplier = 1;

		// Do we have to SO adjust?
		if (isAPAdjusted)
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

	/**
	 * @return {@code true} for purchase-invoice and sales-creditmemo. {@code false} otherwise.
	 */
	public boolean isOutgoingMoney()
	{
		return isCreditMemo ^ soTrx.isPurchase();
	}

	public InvoiceAmtMultiplier withAPAdjusted(final boolean isAPAdjustedNew)
	{
		return isAPAdjusted == isAPAdjustedNew ? this : toBuilder().isAPAdjusted(isAPAdjustedNew).build().intern();
	}

	public InvoiceAmtMultiplier withCMAdjusted(final boolean isCreditMemoAdjustedNew)
	{
		return isCreditMemoAdjusted == isCreditMemoAdjustedNew ? this : toBuilder().isCreditMemoAdjusted(isCreditMemoAdjustedNew).build().intern();
	}
}
