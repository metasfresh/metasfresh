package de.metas.currency;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Amount datatype: a {@link BigDecimal} value and a currency code.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class Amount implements Comparable<Amount>
{
	public static Amount of(@NonNull final BigDecimal value, @NonNull final CurrencyCode currencyCode)
	{
		return new Amount(value, currencyCode);
	}

	public static Amount of(final int value, @NonNull final CurrencyCode currencyCode)
	{
		return of(BigDecimal.valueOf(value), currencyCode);
	}

	public static Amount of(@NonNull final String value, @NonNull final CurrencyCode currencyCode)
	{
		return of(new BigDecimal(value), currencyCode);
	}

	public static Amount zero(@NonNull final CurrencyCode currencyCode)
	{
		return new Amount(BigDecimal.ZERO, currencyCode);
	}

	@Getter(AccessLevel.NONE)
	BigDecimal value;
	CurrencyCode currencyCode;

	private Amount(@NonNull final BigDecimal value, @NonNull final CurrencyCode currencyCode)
	{
		this.value = NumberUtils.stripTrailingDecimalZeros(value);
		this.currencyCode = currencyCode;
	}

	public BigDecimal getAsBigDecimal()
	{
		return toBigDecimal();
	}

	public BigDecimal toBigDecimal()
	{
		return value;
	}

	public static void assertSameCurrency(@NonNull final Amount amount1, @NonNull final Amount amount2)
	{
		if (!amount1.getCurrencyCode().equals(amount2.getCurrencyCode()))
		{
			throw new AdempiereException("Amounts shall have the same currency: " + Arrays.asList(amount1, amount2));
		}
	}

	public static void assertSameCurrency(final Amount... amounts)
	{
		if (amounts == null || amounts.length == 0)
		{
			return;
		}

		final CurrencyCode expectedCurrencyCode = amounts[0].getCurrencyCode();
		for (int i = 1; i < amounts.length; i++)
		{
			if (!expectedCurrencyCode.equals(amounts[i].getCurrencyCode()))
			{
				throw new AdempiereException("Amounts shall have the same currency: " + Arrays.asList(amounts));
			}
		}
	}

	public static <T> void assertSameCurrency(
			@NonNull final Collection<T> objects,
			@NonNull final Function<T, Amount> amountExtractor)
	{
		if (objects.isEmpty())
		{
			return;
		}

		CurrencyCode expectedCurrencyCode = null;
		for (final T object : objects)
		{
			final Amount amount = amountExtractor.apply(object);
			if (expectedCurrencyCode == null)
			{
				expectedCurrencyCode = amount.getCurrencyCode();
			}
			else if (!expectedCurrencyCode.equals(amount.getCurrencyCode()))
			{
				throw new AdempiereException("Extracted amount `" + amount + "` of object `" + object + "` was expected to be in " + expectedCurrencyCode);
			}
		}
	}

	public static CurrencyCode getCommonCurrencyCodeOfAll(@NonNull final Amount... amounts)
	{
		Check.assumeNotEmpty(amounts, "The given moneys may not be empty");

		//noinspection ConstantConditions
		final Iterator<Amount> moneysIterator = Stream.of(amounts)
				.filter(Objects::nonNull)
				.iterator();
		final ImmutableListMultimap<CurrencyCode, Amount> amountsByCurrencyCode = Multimaps.index(moneysIterator, Amount::getCurrencyCode);
		if (amountsByCurrencyCode.isEmpty())
		{
			throw new AdempiereException("The given moneys may not be empty");
		}

		final ImmutableSet<CurrencyCode> currencyCodes = amountsByCurrencyCode.keySet();
		Check.errorIf(currencyCodes.size() > 1,
				"at least two money instances have different currencies: {}", amountsByCurrencyCode);

		return CollectionUtils.singleElement(currencyCodes.asList());
	}

	@Override
	public int compareTo(@NonNull final Amount other)
	{
		assertSameCurrency(this, other);
		return getAsBigDecimal().compareTo(other.getAsBigDecimal());
	}

	public boolean isEqualByComparingTo(@Nullable final Amount other)
	{
		if (other == null)
		{
			return false;
		}
		return other.currencyCode.equals(currencyCode)
				&& other.value.compareTo(value) == 0;
	}

	public boolean valueComparingEqualsTo(@NonNull final BigDecimal other)
	{
		return getAsBigDecimal().compareTo(other) == 0;
	}

	public Amount min(@NonNull final Amount other)
	{
		return compareTo(other) <= 0 ? this : other;
	}

	public int signum()
	{
		return getAsBigDecimal().signum();
	}

	public boolean isZero()
	{
		return signum() == 0;
	}

	public Amount negate()
	{
		return isZero()
				? this
				: new Amount(value.negate(), currencyCode);
	}

	public Amount negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public Amount negateIfNot(final boolean condition)
	{
		return !condition ? negate() : this;
	}

	public Amount add(@NonNull final Amount amtToAdd)
	{
		assertSameCurrency(this, amtToAdd);

		if (amtToAdd.isZero())
		{
			return this;
		}
		else if (isZero())
		{
			return amtToAdd;
		}
		else
		{
			return new Amount(value.add(amtToAdd.value), currencyCode);
		}
	}

	public Amount subtract(@NonNull final Amount amtToSubtract)
	{
		assertSameCurrency(this, amtToSubtract);

		if (amtToSubtract.isZero())
		{
			return this;
		}
		else
		{
			return new Amount(value.subtract(amtToSubtract.value), currencyCode);
		}
	}

	public Amount multiply(@NonNull final Percent percent, @NonNull final CurrencyPrecision precision)
	{
		final BigDecimal newValue = percent.computePercentageOf(value, precision.toInt(), precision.getRoundingMode());

		return !newValue.equals(value)
				? new Amount(newValue, currencyCode)
				: this;
	}

	public Amount abs()
	{
		return value.signum() < 0
				? new Amount(value.abs(), currencyCode)
				: this;
	}

	public Money toMoney(@NonNull final Function<CurrencyCode, CurrencyId> currencyIdMapper)
	{
		return Money.of(value, currencyIdMapper.apply(currencyCode));
	}
}
