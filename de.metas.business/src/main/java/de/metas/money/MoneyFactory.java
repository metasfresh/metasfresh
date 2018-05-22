package de.metas.money;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.NonNull;

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

@Service
public class MoneyFactory
{
	private final CurrencyRepository currencyRepository;

	public MoneyFactory(
			@NonNull final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	public Money forAmountAndCurrencyId(
			@NonNull final BigDecimal value,
			@NonNull final CurrencyId currencyId)
	{
		final Currency currency = currencyRepository.getById(currencyId);
		return Money.of(value, currency);
	}

	public Money zero(
			@NonNull final CurrencyId currencyId)
	{
		final Currency currency = currencyRepository.getById(currencyId);
		return Money.zero(currency);
	}
}
