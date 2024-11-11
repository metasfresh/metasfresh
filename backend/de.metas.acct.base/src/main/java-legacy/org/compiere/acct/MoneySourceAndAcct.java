/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.compiere.acct;

import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class MoneySourceAndAcct
{
	@NonNull private final Money source;
	@NonNull private final Money acct;

	private MoneySourceAndAcct(@NonNull final Money source, @NonNull final Money acct)
	{
		this.source = source;
		this.acct = acct;
	}

	public static MoneySourceAndAcct ofSourceAndAcct(@NonNull final Money source, @NonNull final Money acct)
	{
		return new MoneySourceAndAcct(source, acct);
	}

	public String toString()
	{
		return source + "/" + acct;
	}

	public int signum() {return source.signum();}

	public CurrencyId getSourceCurrencyId() {return source.getCurrencyId();}

	private MoneySourceAndAcct newOfSourceAndAcct(@NonNull final Money newSource, @NonNull final Money newAcct)
	{
		if (Money.equals(this.source, newSource) && Money.equals(this.acct, newAcct))
		{
			return this;
		}
		return ofSourceAndAcct(newSource, newAcct);
	}

	public MoneySourceAndAcct divide(@NonNull final MoneySourceAndAcct divisor, @NonNull final CurrencyPrecision precision)
	{
		return newOfSourceAndAcct(source.divide(divisor.source, precision), acct.divide(divisor.acct, precision));
	}

	public MoneySourceAndAcct multiply(@NonNull final BigDecimal multiplicand)
	{
		return newOfSourceAndAcct(source.multiply(multiplicand), acct.multiply(multiplicand));
	}

	public MoneySourceAndAcct negate()
	{
		return newOfSourceAndAcct(source.negate(), acct.negate());
	}

	public MoneySourceAndAcct negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public MoneySourceAndAcct toZero()
	{
		return newOfSourceAndAcct(source.toZero(), acct.toZero());
	}

	public MoneySourceAndAcct roundIfNeeded(@NonNull final CurrencyPrecision sourcePrecision, @NonNull final CurrencyPrecision acctPrecision)
	{
		return newOfSourceAndAcct(source.roundIfNeeded(sourcePrecision), acct.roundIfNeeded(acctPrecision));
	}
}
