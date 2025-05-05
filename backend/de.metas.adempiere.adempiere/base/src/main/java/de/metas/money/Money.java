package de.metas.money;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Money implements Comparable<Money>
{
	public static Money of(@NonNull final String value, @NonNull final CurrencyId currencyId)
	{
		return of(new BigDecimal(value), currencyId);
	}

	public static Money of(final int value, @NonNull final CurrencyId currencyId)
	{
		return of(BigDecimal.valueOf(value), currencyId);
	}

	public static Money of(@NonNull final BigDecimal value, @NonNull final CurrencyId currencyId)
	{
		return new Money(value, currencyId);
	}

	@Nullable
	public static Money ofOrNull(@Nullable final BigDecimal value, @Nullable final CurrencyId currencyId)
	{
		if (value == null || currencyId == null)
		{
			return null;
		}
		return new Money(value, currencyId);
	}

	@Nullable
	public static Money toZeroOrNull(@Nullable final Money money)
	{
		if (money == null)
		{
			return null;
		}
		return money.toZero();
	}

	public static Money zero(@NonNull final CurrencyId currencyId)
	{
		return new Money(ZERO, currencyId);
	}

	@JsonProperty("value")
	@Getter(AccessLevel.NONE)
	BigDecimal value;

	@JsonProperty("currencyId")
	CurrencyId currencyId;

	@Builder
	@JsonCreator
	private Money(
			@JsonProperty("value") @NonNull final BigDecimal value,
			@JsonProperty("currencyId") @NonNull final CurrencyId currencyId)
	{
		this.value = NumberUtils.stripTrailingDecimalZeros(value); // stripping trailing zeros to make sure that 4 EUR equal 4.00 EUR
		this.currencyId = currencyId;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(value);
		if (CurrencyId.equals(currencyId, CurrencyId.USD))
		{
			sb.append(" USD");
		}
		else if (CurrencyId.equals(currencyId, CurrencyId.EUR))
		{
			sb.append(" EUR");
		}
		else if (CurrencyId.equals(currencyId, CurrencyId.CHF))
		{
			sb.append(" CHF");
		}
		else
		{
			sb.append(" [").append(currencyId.getRepoId()).append("]");
		}

		return sb.toString();
	}

	public BigDecimal toBigDecimal()
	{
		return value;
	}

	public static BigDecimal toBigDecimalOrZero(@Nullable final Money money)
	{
		if (money == null)
		{
			return ZERO;
		}
		return money.toBigDecimal();
	}

	public int signum()
	{
		return value.signum();
	}

	public boolean isZero()
	{
		return signum() == 0;
	}

	public boolean isNegative()
	{
		return signum() < 0;
	}

	public Money negate()
	{
		if (value.signum() == 0)
		{
			return this;
		}

		return new Money(value.negate(), currencyId);
	}

	public Money negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public Money toZero()
	{
		if (isZero())
		{
			return this;
		}
		return Money.zero(currencyId);
	}

	public Money toZeroIfNegative()
	{
		return signum() >= 0 ? this : zero(currencyId);
	}

	public Money abs() {return withValue(value.abs());}

	public static void assertSameCurrency(final Money... moneys)
	{
		getCommonCurrencyIdOfAll(moneys);
	}

	@NonNull
	public static CurrencyId getCommonCurrencyIdOfAll(final Money... moneys)
	{
		return CurrencyId.getCommonCurrencyIdOfAll(Money::getCurrencyId, "Money", moneys);
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

	public Money multiply(final int multiplicand)
	{
		return multiply(BigDecimal.valueOf(multiplicand));
	}

	public Money multiply(@NonNull final Percent percent, @NonNull final CurrencyPrecision precision)
	{
		final BigDecimal newValue = percent.computePercentageOf(value, precision.toInt(), precision.getRoundingMode());

		return !newValue.equals(value)
				? new Money(newValue, currencyId)
				: this;
	}

	public Money divide(@NonNull final Money divisor, @NonNull final CurrencyPrecision precision)
	{
		assertCurrencyIdMatching(divisor);
		return divide(divisor.value, precision);
	}

	public Money divide(@NonNull final BigDecimal divisor, @NonNull final CurrencyPrecision precision)
	{
		final BigDecimal resultingValue = this.value.divide(divisor, precision.toInt(), precision.getRoundingMode());
		return of(resultingValue, this.currencyId);
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

	private void assertCurrencyIdMatching(@NonNull final Money amt)
	{
		if (!Objects.equals(currencyId, amt.currencyId))
		{
			throw new AdempiereException("Amount has invalid currencyId: " + amt + ". Expected: " + currencyId);
		}
	}

	public Money assertCurrencyId(@NonNull final CurrencyId expectedCurrencyId)
	{
		if (!Objects.equals(currencyId, expectedCurrencyId))
		{
			throw new AdempiereException("Amount has invalid currencyId: " + this + ". Expected: " + expectedCurrencyId);
		}
		return this;
	}

	@Override
	public int compareTo(@NonNull final Money other)
	{
		assertCurrencyIdMatching(other);
		return this.value.compareTo(other.value);
	}

	public boolean isLessThan(@NonNull final Money other) {return compareTo(other) < 0;}

	public boolean isLessThanOrEqualTo(@NonNull final Money other) {return compareTo(other) <= 0;}

	public boolean isGreaterThanOrEqualTo(@NonNull final Money other) {return compareTo(other) >= 0;}

	public boolean isGreaterThan(@NonNull final Money other) {return compareTo(other) > 0;}

	public boolean isEqualByComparingTo(@Nullable final Money other)
	{
		if (other == null)
		{
			return false;
		}
		return other.getCurrencyId().equals(currencyId) && other.toBigDecimal().compareTo(toBigDecimal()) == 0;
	}

	public static Collector<Money, ?, Stream<Money>> sumByCurrencyAndStream()
	{
		return sumByCurrencyAnd(map -> map.values().stream());
	}

	public static Collector<Money, ?, ImmutableMap<CurrencyId, Money>> sumByCurrency()
	{
		return sumByCurrencyAnd(ImmutableMap::copyOf);
	}

	public static <T> Collector<Money, ?, T> sumByCurrencyAnd(final Function<Map<CurrencyId, Money>, T> finisher)
	{
		final Supplier<Map<CurrencyId, Money>> supplier = HashMap::new;
		final BiConsumer<Map<CurrencyId, Money>, Money> accumulator = (map, money) -> map.compute(money.getCurrencyId(), (currency, moneyOld) -> moneyOld != null ? moneyOld.add(money) : money);
		final BinaryOperator<Map<CurrencyId, Money>> combiner = (l, r) -> {
			r.values().forEach(money -> accumulator.accept(l, money));
			return l;
		};

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public Amount toAmount(@NonNull final Function<CurrencyId, CurrencyCode> currencyCodeMapper)
	{
		return Amount.of(toBigDecimal(), currencyCodeMapper.apply(getCurrencyId()));
	}

	public Money round(@NonNull final CurrencyPrecision precision)
	{
		return withValue(precision.round(this.value));
	}

	public Money roundIfNeeded(@NonNull final CurrencyPrecision precision)
	{
		return withValue(precision.roundIfNeeded(this.value));
	}

	public Money round(@NonNull final Function<CurrencyId, CurrencyPrecision> precisionProvider)
	{
		final CurrencyPrecision precision = precisionProvider.apply(currencyId);
		if (precision == null)
		{
			throw new AdempiereException("No precision was returned by " + precisionProvider + " for " + currencyId);
		}
		return round(precision);
	}

	private Money withValue(@NonNull final BigDecimal newValue)
	{
		return value.compareTo(newValue) != 0 ? of(newValue, currencyId) : this;
	}

	public static int countNonZero(final Money... array)
	{
		if (array == null || array.length == 0)
		{
			return 0;
		}

		int count = 0;
		for (final Money money : array)
		{
			if (money != null && money.signum() != 0)
			{
				count++;
			}
		}

		return count;
	}

	public static boolean equals(@Nullable Money money1, @Nullable Money money2) {return Objects.equals(money1, money2);}

	public Percent percentageOf(@NonNull final Money whole)
	{
		assertCurrencyIdMatching(whole);
		return Percent.of(toBigDecimal(), whole.toBigDecimal());
	}
}
