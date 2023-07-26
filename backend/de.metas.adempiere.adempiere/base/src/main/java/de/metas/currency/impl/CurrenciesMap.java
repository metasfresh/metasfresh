package de.metas.currency.impl;

import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.money.CurrencyId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class CurrenciesMap
{
	private final ImmutableMap<CurrencyId, Currency> currenciesById;
	private final ImmutableMap<CurrencyCode, Currency> currenciesByCode;

	public CurrenciesMap(@NonNull final List<Currency> currencies)
	{
		currenciesById = Maps.uniqueIndex(currencies, Currency::getId);
		currenciesByCode = Maps.uniqueIndex(currencies, Currency::getCurrencyCode);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("count", currenciesById.size())
				.toString();
	}

	public Currency getById(@NonNull final CurrencyId id)
	{
		final Currency currency = currenciesById.get(id);
		if (currency == null)
		{
			throw new AdempiereException("@NotFound@ @C_Currency_ID@: " + id);
		}
		return currency;
	}

	public Currency getByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		final Currency currency = currenciesByCode.get(currencyCode);
		if (currency == null)
		{
			throw new AdempiereException("@NotFound@ @ISO_Code@: " + currencyCode);
		}
		return currency;
	}

	public Optional<Currency> getByCurrencyCodeIfExists(@NonNull final CurrencyCode currencyCode)
	{
		return Optional.ofNullable(currenciesByCode.get(currencyCode));
	}

}
