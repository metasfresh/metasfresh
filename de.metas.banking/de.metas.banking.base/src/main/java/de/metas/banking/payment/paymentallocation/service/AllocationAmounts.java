package de.metas.banking.payment.paymentallocation.service;

import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.banking.swingui
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
public class AllocationAmounts
{
	public static AllocationAmounts zero(@NonNull final CurrencyId currencyId)
	{
		final Money zero = Money.zero(currencyId);
		return builder()
				.payAmt(zero)
				.discountAmt(zero)
				.writeOffAmt(zero)
				.build();
	}

	public static AllocationAmounts ofPayAmt(@NonNull final Money payAmt)
	{
		return builder().payAmt(payAmt).build();
	}

	private final CurrencyId currencyId;
	private final Money payAmt;
	private final Money discountAmt;
	private final Money writeOffAmt;

	@Builder(toBuilder = true)
	private AllocationAmounts(
			@NonNull final Money payAmt,
			@Nullable final Money discountAmt,
			@Nullable final Money writeOffAmt)
	{
		this.payAmt = payAmt;
		this.discountAmt = discountAmt != null ? discountAmt : payAmt.toZero();
		this.writeOffAmt = writeOffAmt != null ? writeOffAmt : payAmt.toZero();
		this.currencyId = Money.getCommonCurrencyIdOfAll(payAmt, discountAmt, writeOffAmt);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("payAmt", payAmt.toBigDecimal())
				.add("discountAmt", discountAmt.toBigDecimal())
				.add("writeOffAmt", writeOffAmt.toBigDecimal())
				.add("currencyId", currencyId.getRepoId())
				.toString();
	}

	public AllocationAmounts addPayAmt(@NonNull final Money payAmtToAdd)
	{
		final Money newPayAmt = this.payAmt.add(payAmtToAdd);
		return withPayAmt(newPayAmt);
	}

	public AllocationAmounts withPayAmt(@NonNull final Money payAmt)
	{
		return Objects.equals(this.payAmt, payAmt)
				? this
				: toBuilder().payAmt(payAmt).build();
	}

	public AllocationAmounts withZeroPayAmt()
	{
		return withPayAmt(payAmt.toZero());
	}

	public AllocationAmounts addDiscountAmt(@NonNull final Money discountAmtToAdd)
	{
		final Money newDiscountAmt = this.discountAmt.add(discountAmtToAdd);
		return withDiscountAmt(newDiscountAmt);
	}

	public AllocationAmounts withDiscountAmt(@NonNull final Money discountAmt)
	{
		return Objects.equals(this.discountAmt, discountAmt)
				? this
				: toBuilder().discountAmt(discountAmt).build();
	}

	public AllocationAmounts addWriteOffAmt(@NonNull final Money writeOffAmtToAdd)
	{
		final Money newWriteOffAmt = this.writeOffAmt.add(writeOffAmtToAdd);
		return withWriteOffAmt(newWriteOffAmt);
	}

	public AllocationAmounts withWriteOffAmt(@NonNull final Money writeOffAmt)
	{
		return Objects.equals(this.writeOffAmt, writeOffAmt)
				? this
				: toBuilder().writeOffAmt(writeOffAmt).build();
	}

	public AllocationAmounts add(AllocationAmounts other)
	{
		return toBuilder()
				.payAmt(this.payAmt.add(other.payAmt))
				.discountAmt(this.discountAmt.add(other.discountAmt))
				.writeOffAmt(this.writeOffAmt.add(other.writeOffAmt))
				.build();
	}

	public AllocationAmounts subtract(AllocationAmounts other)
	{
		return toBuilder()
				.payAmt(this.payAmt.subtract(other.payAmt))
				.discountAmt(this.discountAmt.subtract(other.discountAmt))
				.writeOffAmt(this.writeOffAmt.subtract(other.writeOffAmt))
				.build();
	}

	public AllocationAmounts negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public AllocationAmounts negate()
	{
		if (isZero())
		{
			return this;
		}

		return toBuilder()
				.payAmt(this.payAmt.negate())
				.discountAmt(this.discountAmt.negate())
				.writeOffAmt(this.writeOffAmt.negate())
				.build();
	}

	public Money getTotalAmt()
	{
		return payAmt.add(discountAmt).add(writeOffAmt);
	}

	public boolean isZero()
	{
		return payAmt.signum() == 0
				&& discountAmt.signum() == 0
				&& writeOffAmt.signum() == 0;
	}

	public AllocationAmounts toZero()
	{
		return isZero() ? this : AllocationAmounts.zero(getCurrencyId());
	}
}
