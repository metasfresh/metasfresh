package de.metas.dunning_gateway.spi.model;

import java.math.BigDecimal;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-invoice.gateway.spi
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
	public static Money of(
			@NonNull final BigDecimal amount,
			@NonNull final String currency)
	{
		return new Money(amount, currency);
	}

	BigDecimal amount;
	String currency;

	public int signum()
	{
		return amount.signum();
	}

	private Money(
			@NonNull final BigDecimal amount,
			@NonNull final String currency)
	{
		this.amount = amount;
		this.currency = Check.assumeNotEmpty(currency, "The given currency may not be empty; amount={}", amount);
	}

}
