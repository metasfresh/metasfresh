package de.metas.costing;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaCosting;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
<<<<<<< HEAD
import de.metas.util.NumberUtils;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.math.RoundingMode;
=======
import de.metas.util.lang.Percent;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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

<<<<<<< HEAD
@Value
public class CostAmount
{
	public static CostAmount of(
			@NonNull final BigDecimal value,
			final CurrencyId currencyId)
	{
		return new CostAmount(value, currencyId);
=======
@EqualsAndHashCode
@ToString
public final class CostAmount
{
	public static CostAmount of(
			@NonNull final BigDecimal value,
			@NonNull final CurrencyId currencyId)
	{
		return new CostAmount(Money.of(value, currencyId), null);
	}

	public static CostAmount of(
			@NonNull final BigDecimal value,
			@NonNull final CurrencyId currencyId,
			@Nullable final BigDecimal sourceValue,
			@Nullable final CurrencyId sourceCurrencyId)
	{
		return new CostAmount(Money.of(value, currencyId), Money.ofOrNull(sourceValue, sourceCurrencyId));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostAmount of(
			final int valueInt,
			final CurrencyId currencyId)
	{
		return of(BigDecimal.valueOf(valueInt), currencyId);
	}

	public static CostAmount of(
			final String valueStr,
			final CurrencyId currencyId)
	{
		return of(new BigDecimal(valueStr), currencyId);
	}

	public static CostAmount ofMoney(@NonNull final Money money)
	{
<<<<<<< HEAD
		return new CostAmount(money.toBigDecimal(), money.getCurrencyId());
=======
		return new CostAmount(money, null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostAmount zero(final CurrencyId currencyId)
	{
<<<<<<< HEAD
		return new CostAmount(BigDecimal.ZERO, currencyId);
=======
		return new CostAmount(Money.zero(currencyId), null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static CostAmount ofProductPrice(@NonNull final ProductPrice price)
	{
		return ofMoney(price.toMoney());
	}

	public static CostAmount multiply(
			@NonNull final ProductPrice price,
			@NonNull final Quantity qty)
	{
		if (!UomId.equals(price.getUomId(), qty.getUomId()))
		{
			throw new AdempiereException("UOM does not match: " + price + ", " + qty);
		}

		return ofMoney(price.toMoney().multiply(qty.toBigDecimal()));
	}

<<<<<<< HEAD
	BigDecimal value;
	CurrencyId currencyId;

	private CostAmount(
			@NonNull final BigDecimal value,
			@NonNull final CurrencyId currencyId)
	{
		this.value = NumberUtils.stripTrailingDecimalZeros(value);
		this.currencyId = currencyId;
	}

	private void assertCurrencyMatching(@NonNull final CostAmount amt)
	{
		if (!currencyId.equals(amt.currencyId))
		{
			throw new AdempiereException("Amount has invalid currency: " + amt + ". Expected: " + currencyId);
		}
	}

=======
	@NonNull private final Money value;
	@Nullable private final Money sourceValue;

	private CostAmount(
			@NonNull final Money value,
			@Nullable final Money sourceValue)
	{
		this.value = value;
		this.sourceValue = sourceValue;
	}

	public CurrencyId getCurrencyId() {return value.getCurrencyId();}

	private void assertCurrencyMatching(@NonNull final CostAmount amt)
	{
		if (!CurrencyId.equals(getCurrencyId(), amt.getCurrencyId()))
		{
			throw new AdempiereException("Amount has invalid currency: " + amt + ". Expected: " + getCurrencyId());
		}
	}

	public static void assertCurrencyMatching(@Nullable final CostAmount... amts)
	{
		CurrencyId.assertCurrencyMatching(CostAmount::getCurrencyId, "Amount", amts);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public int signum()
	{
		return value.signum();
	}

	public boolean isZero()
	{
		return signum() == 0;
	}

	public CostAmount negate()
	{
		if (value.signum() == 0)
		{
			return this;
		}
		else
		{
<<<<<<< HEAD
			return new CostAmount(value.negate(), currencyId);
=======
			return new CostAmount(value.negate(), sourceValue != null ? sourceValue.negate() : null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	public CostAmount negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public CostAmount negateIfNot(final boolean condition)
	{
		return !condition ? negate() : this;
	}

	public CostAmount multiply(@NonNull final BigDecimal multiplicand)
	{
		if (BigDecimal.ONE.compareTo(multiplicand) == 0)
		{
			return this;
		}
		else
		{
<<<<<<< HEAD
			return new CostAmount(value.multiply(multiplicand), currencyId);
=======
			return new CostAmount(
					value.multiply(multiplicand),
					sourceValue != null ? sourceValue.multiply(multiplicand) : null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	public CostAmount multiply(@NonNull final Quantity quantity)
	{
		return multiply(quantity.toBigDecimal());
	}

	public CostAmount multiply(
			@NonNull final Percent percent,
			final CurrencyPrecision precision)
	{
		if (percent.isZero())
		{
<<<<<<< HEAD
			return zero(currencyId);
=======
			return toZero();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else if (percent.isOneHundred())
		{
			return this;
		}
		else
		{
<<<<<<< HEAD
			return new CostAmount(percent.computePercentageOf(value, precision.toInt()), currencyId);
=======
			return new CostAmount(
					value.multiply(percent, precision),
					sourceValue != null ? sourceValue.multiply(percent, precision) : null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	public CostAmount add(@NonNull final CostAmount amtToAdd)
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

<<<<<<< HEAD
		return new CostAmount(value.add(amtToAdd.value), currencyId);
=======
		final Money sourceValueNew = sourceValue != null && amtToAdd.sourceValue != null && Money.isSameCurrency(sourceValue, amtToAdd.sourceValue)
				? sourceValue.add(amtToAdd.sourceValue)
				: null;

		return new CostAmount(value.add(amtToAdd.value), sourceValueNew);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public CostAmount divide(
			@NonNull final BigDecimal divisor,
			@NonNull final CurrencyPrecision precision)
	{
		if (divisor.signum() == 0)
		{
			throw new AdempiereException("Diving " + this + " by ZERO is not allowed");
		}

<<<<<<< HEAD
		final BigDecimal valueNew = value.divide(divisor, precision.toInt(), RoundingMode.HALF_UP);
		return new CostAmount(valueNew, currencyId);
=======
		final Money valueNew = value.divide(divisor, precision);
		final Money sourceValueNew = sourceValue != null
				? sourceValue.divide(divisor, precision)
				: null;
		return new CostAmount(valueNew, sourceValueNew);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public CostAmount divide(
			@NonNull final Quantity divisor,
			@NonNull final CurrencyPrecision precision)
	{
		return divide(divisor.toBigDecimal(), precision);
	}

<<<<<<< HEAD
	public CostAmount roundToPrecisionIfNeeded(final CurrencyPrecision precision)
	{
		final BigDecimal valueRounded = precision.roundIfNeeded(value);
		if (value.equals(valueRounded))
=======
	public Optional<CostAmount> divideIfNotZero(
			@NonNull final Quantity divisor,
			@NonNull final CurrencyPrecision precision)
	{
		return divisor.isZero() ? Optional.empty() : Optional.of(divide(divisor, precision));
	}

	public CostAmount round(@NonNull final Function<CurrencyId, CurrencyPrecision> precisionProvider)
	{
		final Money valueNew = value.round(precisionProvider);
		final Money sourceValueNew = sourceValue != null ? sourceValue.round(precisionProvider) : null;
		if (Money.equals(value, valueNew)
				&& Money.equals(sourceValue, sourceValueNew))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return this;
		}
		else
		{
<<<<<<< HEAD
			return new CostAmount(valueRounded, currencyId);
=======
			return new CostAmount(valueNew, sourceValueNew);
		}
	}

	public CostAmount roundToPrecisionIfNeeded(final CurrencyPrecision precision)
	{
		final Money valueRounded = value.roundIfNeeded(precision);
		if (Money.equals(value, valueRounded))
		{
			return this;
		}
		else
		{
			return new CostAmount(valueRounded, sourceValue);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	public CostAmount roundToCostingPrecisionIfNeeded(final AcctSchema acctSchema)
	{
		final AcctSchemaCosting acctSchemaCosting = acctSchema.getCosting();
		final CurrencyPrecision precision = acctSchemaCosting.getCostingPrecision();
		return roundToPrecisionIfNeeded(precision);
	}

	@NonNull
	public CostAmount subtract(@NonNull final CostAmount amtToSubtract)
	{
		assertCurrencyMatching(amtToSubtract);

		if (amtToSubtract.isZero())
		{
			return this;
		}
<<<<<<< HEAD
		return new CostAmount(value.subtract(amtToSubtract.value), currencyId);
	}

	public CostAmount subtract(@NonNull final BigDecimal amtToSubtract)
	{
		if (amtToSubtract.signum() == 0)
		{
			return this;
		}

		return new CostAmount(value.subtract(amtToSubtract), currencyId);
=======

		return add(amtToSubtract.negate());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public CostAmount toZero()
	{
		if (isZero())
		{
			return this;
		}
		else
		{
<<<<<<< HEAD
			return zero(currencyId);
=======
			return new CostAmount(value.toZero(), sourceValue != null ? sourceValue.toZero() : null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	public Money toMoney()
	{
<<<<<<< HEAD
		return Money.of(value, currencyId);
	}
=======
		return value;
	}

	@Nullable
	public Money toSourceMoney()
	{
		return sourceValue;
	}

	public BigDecimal toBigDecimal() {return value.toBigDecimal();}

	public boolean compareToEquals(@NonNull final CostAmount other)
	{
		return this.value.compareTo(other.value) == 0;
	}

	public static CurrencyId getCommonCurrencyIdOfAll(@Nullable final CostAmount... costAmounts)
	{
		return CurrencyId.getCommonCurrencyIdOfAll(CostAmount::getCurrencyId, "Amount", costAmounts);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
