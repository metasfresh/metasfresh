package de.metas.rest_api.utils;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@Service
public class CurrencyService
{
	public CurrencyId getCurrencyId(@Nullable final String currencyCodeStr)
	{
		if (Check.isEmpty(currencyCodeStr, true))
		{
			return null;
		}

		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(currencyCodeStr);

		final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
		return currenciesRepo.getByCurrencyCode(currencyCode).getId();
	}
}
