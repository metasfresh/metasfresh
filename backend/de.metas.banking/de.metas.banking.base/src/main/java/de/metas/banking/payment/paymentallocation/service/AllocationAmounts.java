package de.metas.banking.payment.paymentallocation.service;

import com.google.common.base.MoreObjects;
import de.metas.common.util.CoalesceUtil;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

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

	CurrencyId currencyId;
	Money payAmt;
	Money discountAmt;
	Money writeOffAmt;
	Money invoiceProcessingFee;

	@Builder(toBuilder = true)
	private AllocationAmounts(
			@Nullable final Money payAmt,
			@Nullable final Money discountAmt,
			@Nullable final Money writeOffAmt,
			@Nullable final Money invoiceProcessingFee)
	{
		final Money firstNonNull = CoalesceUtil.coalesce(payAmt, discountAmt, writeOffAmt, invoiceProcessingFee);
		if (firstNonNull == null)
		{
			throw new AdempiereException("Provide at least one amount. If you want o create a ZERO instance, use the zero(currencyId) method.");
		}
		final Money zero = firstNonNull.toZero();

		this.payAmt = payAmt != null ? payAmt : zero;
		this.discountAmt = discountAmt != null ? discountAmt : zero;
		this.writeOffAmt = writeOffAmt != null ? writeOffAmt : zero;
		this.invoiceProcessingFee = invoiceProcessingFee != null ? invoiceProcessingFee : zero;

		this.currencyId = Money.getCommonCurrencyIdOfAll(
				this.payAmt,
				this.discountAmt,
				this.writeOffAmt,
				this.invoiceProcessingFee);
	}

	@Override
	public String toString()
	{
		final Function<Money, BigDecimal> toBD = money -> money != null && money.signum() != 0
				? money.toBigDecimal()
				: null;

		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("currencyId", currencyId.getRepoId())
				.add("payAmt", toBD.apply(payAmt))
				.add("discountAmt", toBD.apply(discountAmt))
				.add("writeOffAmt", toBD.apply(writeOffAmt))
				.add("invoiceProcessingFee", toBD.apply(invoiceProcessingFee))
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

	public AllocationAmounts withInvoiceProcessingFee(@NonNull final Money invoiceProcessingFee)
	{
		return Objects.equals(this.invoiceProcessingFee, invoiceProcessingFee)
				? this
				: toBuilder().invoiceProcessingFee(invoiceProcessingFee).build();
	}

	public AllocationAmounts movePayAmtToDiscount()
	{
		if (payAmt.signum() == 0)
		{
			return this;
		}
		else
		{
			return toBuilder()
					.payAmt(payAmt.toZero())
					.discountAmt(discountAmt.add(payAmt))
					.build();
		}
	}

	public AllocationAmounts movePayAmtToWriteOff()
	{
		if (payAmt.signum() == 0)
		{
			return this;
		}
		else
		{
			return toBuilder()
					.payAmt(payAmt.toZero())
					.writeOffAmt(writeOffAmt.add(payAmt))
					.build();
		}
	}

	public AllocationAmounts add(@NonNull final AllocationAmounts other)
	{
		return toBuilder()
				.payAmt(this.payAmt.add(other.payAmt))
				.discountAmt(this.discountAmt.add(other.discountAmt))
				.writeOffAmt(this.writeOffAmt.add(other.writeOffAmt))
				.invoiceProcessingFee(this.invoiceProcessingFee.add(other.invoiceProcessingFee))
				.build();
	}

	public AllocationAmounts subtract(@NonNull final AllocationAmounts other)
	{
		return toBuilder()
				.payAmt(this.payAmt.subtract(other.payAmt))
				.discountAmt(this.discountAmt.subtract(other.discountAmt))
				.writeOffAmt(this.writeOffAmt.subtract(other.writeOffAmt))
				.invoiceProcessingFee(this.invoiceProcessingFee.subtract(other.invoiceProcessingFee))
				.build();
	}

	public AllocationAmounts convertToRealAmounts(@NonNull final InvoiceAmtMultiplier invoiceAmtMultiplier)
	{
		return negateIf(invoiceAmtMultiplier.isNegateToConvertToRealValue());
	}

	private AllocationAmounts negateIf(final boolean condition)
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
				.invoiceProcessingFee(this.invoiceProcessingFee.negate())
				.build();
	}

	public Money getTotalAmt()
	{
		return payAmt.add(discountAmt).add(writeOffAmt).add(invoiceProcessingFee);
	}

	public boolean isZero()
	{
		return payAmt.signum() == 0
				&& discountAmt.signum() == 0
				&& writeOffAmt.signum() == 0
				&& invoiceProcessingFee.signum() == 0;
	}

	public AllocationAmounts toZero()
	{
		return isZero() ? this : AllocationAmounts.zero(getCurrencyId());
	}
}
