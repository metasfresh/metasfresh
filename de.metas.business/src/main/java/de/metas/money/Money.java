package de.metas.money;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;

import de.metas.currency.Amount;
import de.metas.lang.Percent;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class Money
{
	public static final Money of(
			@NonNull final BigDecimal value,
			@NonNull final Currency currency)
	{
		return new Money(value, currency);
	}

	public static final Money zero(
			@NonNull final Currency currency)
	{
		return new Money(ZERO, currency);
	}

	BigDecimal value;
	Currency currency;

	@Builder
	private Money(
			@NonNull final BigDecimal value,
			@NonNull final Currency currency)
	{
		this.value = value.setScale(currency.getPrecision(), RoundingMode.HALF_UP);
		this.currency = currency;
	}

	private final void assertCurrencyMatching(
			@NonNull final Money amt)
	{
		if (!Objects.equals(currency.getId(), amt.currency.getId()))
		{
			throw new AdempiereException("Amount has invalid currency: " + amt + ". Expected: " + currency);
		}
	}

	public int signum()
	{
		return value.signum();
	}

	public boolean isZero()
	{
		return signum() == 0;
	}

	public Money negate()
	{
		if (value.signum() == 0)
		{
			return this;
		}

		return new Money(value.negate(), currency);
	}

	public Money multiply(@NonNull final BigDecimal multiplicand)
	{
		if (BigDecimal.ONE.compareTo(multiplicand) == 0)
		{
			return this;
		}

		return new Money(value.multiply(multiplicand), currency);
	}

	public Money multiply(@NonNull final Quantity quantity)
	{
		return multiply(quantity.getQty());
	}

	public Money add(@NonNull final Money amtToAdd)
	{
		assertCurrencyMatching(amtToAdd);

		if (amtToAdd.isZero())
		{
			return this;
		}
		if (isZero())
		{
			return amtToAdd;
		}

		return new Money(value.add(amtToAdd.value), currency);
	}

	public Money divide(final BigDecimal divisor)
	{
		final int precision = currency.getPrecision();

		final BigDecimal valueNew = value.divide(divisor, precision, RoundingMode.HALF_UP);
		return new Money(valueNew, currency);
	}

	public Money roundToPrecisionIfNeeded()
	{
		if (value.scale() <= currency.getPrecision())
		{
			return this;
		}

		final BigDecimal valueNew = value.setScale(currency.getPrecision(), RoundingMode.HALF_UP);
		return new Money(valueNew, currency);
	}

	public Money subtract(@NonNull final Money amtToSubtract)
	{
		assertCurrencyMatching(amtToSubtract);

		if (amtToSubtract.isZero())
		{
			return this;
		}
		return new Money(value.subtract(amtToSubtract.value), currency);
	}

	public Money subtract(@NonNull final BigDecimal amtToSubtract)
	{
		if (amtToSubtract.signum() == 0)
		{
			return this;
		}

		return new Money(value.subtract(amtToSubtract), currency);
	}

	/** example: if this instance is 100CHF and {@code percent} is 80%, then the result is 80CHF*/
	public Money percentage(@NonNull final Percent percent)
	{
		if(percent.isOneHundred())
		{
			return this;
		}

		final BigDecimal newValue = percent
				.multiply(value, currency.getPrecision());
		return new Money(newValue, currency);
	}

	public Money toZero()
	{
		if (isZero())
		{
			return this;
		}
		return Money.zero(currency);
	}

	public Amount toAmount()
	{
		return Amount.of(getValue(), currency.getThreeLetterCode());
	}

	public static Currency getCommonCurrencyOfAll(@NonNull final Money... moneys)
	{
		Check.assumeNotEmpty(moneys, "The given moneys may not be empty");

		final ImmutableListMultimap<Currency, Money> currency2moneys = Multimaps.index(Stream.of(moneys).iterator(), Money::getCurrency);

		final ImmutableSet<Currency> currencies = currency2moneys.keySet();
		Check.errorIf(currencies.size() > 1,
				"at least two money instances have different currencies: ", currency2moneys);

		return ListUtils.singleElement(currencies.asList());
	}
}
