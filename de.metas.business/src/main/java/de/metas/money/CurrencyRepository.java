package de.metas.money;

import org.compiere.model.I_C_Currency;
import org.springframework.stereotype.Repository;

import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.util.Services;
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

@Repository
public class CurrencyRepository
{
	public Currency getById(@NonNull final CurrencyId currencyId)
	{
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
		final I_C_Currency currencyRecord = currencyDAO.getById(currencyId);
		return toCurrency(currencyRecord);
	}

	public Currency getById(final int currencyId)
	{
		return getById(CurrencyId.ofRepoId(currencyId));
	}

	public CurrencyCode getCurrencyCodeById(@NonNull final CurrencyId currencyId)
	{
		return getById(currencyId).getCurrencyCode();
	}

	public static Currency toCurrency(@NonNull final I_C_Currency currencyRecord)
	{
		return Currency.builder()
				.id(CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID()))
				.precision(CurrencyPrecision.ofInt(currencyRecord.getStdPrecision()))
				.currencyCode(CurrencyCode.ofThreeLetterCode(currencyRecord.getISO_Code()))
				.build();
	}
}
