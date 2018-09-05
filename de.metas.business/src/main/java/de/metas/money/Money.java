package de.metas.money;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.collections.CollectionUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;

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
	public static final Money of(@NonNull final String value, @NonNull final CurrencyId currencyId)
	{
		return of(new BigDecimal(value), currencyId);
	}

	public static final Money of(final int value, @NonNull final CurrencyId currencyId)
	{
		return of(BigDecimal.valueOf(value), currencyId);
	}

	public static final Money of(@NonNull final BigDecimal value, @NonNull final CurrencyId currencyId)
	{
		return new Money(value, currencyId);
	}

	public static final Money ofOrNull(@Nullable final BigDecimal value, @Nullable final CurrencyId currencyId)
	{
		if (value == null || currencyId == null)
		{
			return null;
		}
		return new Money(value, currencyId);
	}

	public static final Money toZeroOrNull(@Nullable final Money money)
	{
		if (money == null)
		{
			return null;
		}
		return money.toZero();
	}

	public static final Money zero(@NonNull final CurrencyId currencyId)
	{
		return new Money(ZERO, currencyId);
	}

	BigDecimal value;
	CurrencyId currencyId;

	@Builder
	private Money(
			@NonNull final BigDecimal value,
			@NonNull final CurrencyId currencyId)
	{
		this.value = NumberUtils.stripTrailingDecimalZeros(value); // stripping trailing zeros to make sure that 4 EUR equal 4.00 EUR
		this.currencyId = currencyId;
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

		return new Money(value.negate(), currencyId);
	}

	public Money toZero()
	{
		if (isZero())
		{
			return this;
		}
		return Money.zero(currencyId);
	}

	public static CurrencyId getCommonCurrencyIdOfAll(@NonNull final Money... moneys)
	{
		Check.assumeNotEmpty(moneys, "The given moneys may not be empty");

		final Iterator<Money> moneysIterator = Stream.of(moneys)
				.filter(Predicates.notNull())
				.iterator();
		final ImmutableListMultimap<CurrencyId, Money> currency2moneys = Multimaps.index(moneysIterator, Money::getCurrencyId);
		if (currency2moneys.isEmpty())
		{
			throw new AdempiereException("The given moneys may not be empty");
		}

		final ImmutableSet<CurrencyId> currencyIds = currency2moneys.keySet();
		Check.errorIf(currencyIds.size() > 1,
				"at least two money instances have different currencies: {}", currency2moneys);

		return CollectionUtils.singleElement(currencyIds.asList());
	}

	public static boolean isSameCurrency(@NonNull final Money... moneys)
	{
		Check.assumeNotEmpty(moneys, "The given moneys may not be empty");
		return isSameCurrency(Arrays.asList(moneys));
	}

	public static boolean isSameCurrency(@NonNull final Collection<Money> moneys)
	{
		Check.assumeNotEmpty(moneys, "The given moneys may not be empty");

		final ImmutableSet<CurrencyId> currencies = moneys.stream().map(Money::getCurrencyId).collect(ImmutableSet.toImmutableSet());
		return currencies.size() == 1;
	}

	public Money add(@NonNull final Money amtToAdd)
	{
		assertCurrencyIdMatching(amtToAdd);

		if (amtToAdd.isZero())
		{
			return this;
		}
		if (isZero())
		{
			return amtToAdd;
		}

		return new Money(value.add(amtToAdd.value), currencyId);
	}

	public Money subtract(@NonNull final Money amtToSubtract)
	{
		assertCurrencyIdMatching(amtToSubtract);

		if (amtToSubtract.isZero())
		{
			return this;
		}
		return new Money(value.subtract(amtToSubtract.value), currencyId);
	}

	public Money subtract(@NonNull final BigDecimal amtToSubtract)
	{
		if (amtToSubtract.signum() == 0)
		{
			return this;
		}

		return new Money(value.subtract(amtToSubtract), currencyId);
	}

	public Money multiply(@NonNull final BigDecimal multiplicand)
	{
		if (BigDecimal.ONE.compareTo(multiplicand) == 0)
		{
			return this;
		}

		return new Money(value.multiply(multiplicand), currencyId);
	}

	public Money min(@NonNull final Money other)
	{
		assertCurrencyIdMatching(other);
		return this.value.compareTo(other.value) <= 0 ? this : other;
	}

	public Money max(@NonNull final Money other)
	{
		assertCurrencyIdMatching(other);
		return this.value.compareTo(other.value) >= 0 ? this : other;
	}

	private final void assertCurrencyIdMatching(
			@NonNull final Money amt)
	{
		if (!Objects.equals(currencyId, amt.currencyId))
		{
			throw new AdempiereException("Amount has invalid currencyId: " + amt + ". Expected: " + currencyId);
		}
	}
}
