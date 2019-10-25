package de.metas.costing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaCosting;
import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.NumberUtils;
import de.metas.util.lang.Percent;
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
public class CostAmount
{
	public static final CostAmount of(@NonNull final BigDecimal value, final CurrencyId currencyId)
	{
		return new CostAmount(value, currencyId);
	}

	public static final CostAmount of(final int valueInt, final CurrencyId currencyId)
	{
		return of(BigDecimal.valueOf(valueInt), currencyId);
	}

	public static final CostAmount ofMoney(@NonNull final Money money)
	{
		return new CostAmount(money.toBigDecimal(), money.getCurrencyId());
	}

	public static final CostAmount zero(final CurrencyId currencyId)
	{
		return new CostAmount(BigDecimal.ZERO, currencyId);
	}

	public static final CostAmount ofProductPrice(@NonNull final ProductPrice price)
	{
		return ofMoney(price.toMoney());
	}

	public static final CostAmount multiply(@NonNull final ProductPrice price, @NonNull final Quantity qty)
	{
		if (price.getUomId().getRepoId() != qty.getUOMId())
		{
			throw new AdempiereException("UOM does not match: " + price + ", " + qty);
		}

		return ofMoney(price.toMoney().multiply(qty.toBigDecimal()));
	}

	BigDecimal value;
	CurrencyId currencyId;

	private CostAmount(@NonNull final BigDecimal value, @NonNull final CurrencyId currencyId)
	{
		this.value = NumberUtils.stripTrailingDecimalZeros(value);
		this.currencyId = currencyId;
	}

	private final void assertCurrencyMatching(@NonNull final CostAmount amt)
	{
		if (!currencyId.equals(amt.currencyId))
		{
			throw new AdempiereException("Amount has invalid currency: " + amt + ". Expected: " + currencyId);
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

	public CostAmount negate()
	{
		if (value.signum() == 0)
		{
			return this;
		}
		else
		{
			return new CostAmount(value.negate(), currencyId);
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
			return new CostAmount(value.multiply(multiplicand), currencyId);
		}
	}

	public CostAmount multiply(@NonNull final Quantity quantity)
	{
		return multiply(quantity.toBigDecimal());
	}

	public CostAmount multiply(@NonNull final Percent percent, final CurrencyPrecision precision)
	{
		if (percent.isZero())
		{
			return zero(currencyId);
		}
		else if (percent.isOneHundred())
		{
			return this;
		}
		else
		{
			return new CostAmount(percent.computePercentageOf(value, precision.toInt()), currencyId);
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

		return new CostAmount(value.add(amtToAdd.value), currencyId);
	}

	public CostAmount divide(@NonNull final BigDecimal divisor, @NonNull final CurrencyPrecision precision)
	{
		final BigDecimal valueNew = value.divide(divisor, precision.toInt(), RoundingMode.HALF_UP);
		return new CostAmount(valueNew, currencyId);
	}

	public CostAmount divide(@NonNull final Quantity divisor, @NonNull final CurrencyPrecision precision)
	{
		return divide(divisor.toBigDecimal(), precision);
	}

	public CostAmount roundToPrecisionIfNeeded(final CurrencyPrecision precision)
	{
		final BigDecimal valueRounded = precision.roundIfNeeded(value);
		if (value.equals(valueRounded))
		{
			return this;
		}
		else
		{
			return new CostAmount(valueRounded, currencyId);
		}
	}

	public CostAmount roundToCostingPrecisionIfNeeded(final AcctSchema acctSchema)
	{
		final AcctSchemaCosting acctSchemaCosting = acctSchema.getCosting();
		final CurrencyPrecision precision = acctSchemaCosting.getCostingPrecision();
		return roundToPrecisionIfNeeded(precision);
	}

	public CostAmount subtract(@NonNull final CostAmount amtToSubtract)
	{
		assertCurrencyMatching(amtToSubtract);

		if (amtToSubtract.isZero())
		{
			return this;
		}
		return new CostAmount(value.subtract(amtToSubtract.value), currencyId);
	}

	public CostAmount subtract(@NonNull final BigDecimal amtToSubtract)
	{
		if (amtToSubtract.signum() == 0)
		{
			return this;
		}

		return new CostAmount(value.subtract(amtToSubtract), currencyId);
	}

	public CostAmount toZero()
	{
		if (isZero())
		{
			return this;
		}
		else
		{
			return zero(currencyId);
		}
	}
}
