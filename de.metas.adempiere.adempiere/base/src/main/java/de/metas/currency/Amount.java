package de.metas.currency;

import java.math.BigDecimal;
import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.NumberUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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
 *
 */
@Value
public final class Amount implements Comparable<Amount>
{
	public static Amount of(@NonNull final BigDecimal value, @NonNull CurrencyCode currencyCode)
	{
		return new Amount(value, currencyCode);
	}

	@Getter(AccessLevel.NONE)
	BigDecimal value;
	CurrencyCode currencyCode;

	private Amount(@NonNull final BigDecimal value, @NonNull CurrencyCode currencyCode)
	{
		this.value = NumberUtils.stripTrailingDecimalZeros(value);
		this.currencyCode = currencyCode;
	}
	
	public BigDecimal getAsBigDecimal()
	{
		return value;
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

	@Override
	public int compareTo(@NonNull final Amount other)
	{
		assertSameCurrency(this, other);
		return getAsBigDecimal().compareTo(other.getAsBigDecimal());
	}

	public boolean valueComparingEqualsTo(@NonNull final BigDecimal other)
	{
		return getAsBigDecimal().compareTo(other) == 0;
	}

	public Amount min(@NonNull final Amount other)
	{
		return compareTo(other) <= 0 ? this : other;
	}
}
