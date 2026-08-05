package de.metas.currency;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Currency;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import de.metas.money.CurrencyId;
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

/**
 * Repository Tables: C_Currency
 * Repository Cluster: CurrencyRepository
 */
@Repository
public class CurrencyRepository
{
	final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Currency getById(@NonNull final CurrencyId currencyId)
	{
		return currencyDAO.getById(currencyId);
	}

	@NonNull
	public Currency getById(final int currencyId)
	{
		return getById(CurrencyId.ofRepoId(currencyId));
	}

	public CurrencyId getCurrencyIdByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		final Currency currency = currencyDAO.getByCurrencyCode(currencyCode);
		return currency.getId();
	}

	/**
	 * Resolves the id of the single <b>active</b> {@code C_Currency} whose {@code ISO_Code} equals the given
	 * three-letter code, or {@code null} if no active currency matches (i.e. the code is unknown or the matching
	 * currency is inactive).
	 * <p>
	 * <b>Active-only, no side effects:</b> this is a plain read filtered by {@code IsActive='Y'} on the ISO code.
	 * Unlike {@link #getCurrencyIdByCurrencyCode(CurrencyCode)} / {@link ICurrencyDAO#getByCurrencyCode(CurrencyCode)},
	 * it does <b>not</b> auto-create a missing currency and does not consider inactive rows. Intended for callers
	 * (e.g. REST upserts) that must surface an unknown/inactive currency as an error rather than silently creating one.
	 */
	@Nullable
	public CurrencyId getActiveCurrencyIdByCurrencyCodeOrNull(@NonNull final CurrencyCode currencyCode)
	{
		final I_C_Currency currency = queryBL
				.createQueryBuilderOutOfTrx(I_C_Currency.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Currency.COLUMNNAME_ISO_Code, currencyCode.toThreeLetterCode())
				.create()
				.first(I_C_Currency.class);

		return currency != null ? CurrencyId.ofRepoId(currency.getC_Currency_ID()) : null;
	}

	public CurrencyCode getCurrencyCodeById(@NonNull final CurrencyId currencyId)
	{
		return getById(currencyId).getCurrencyCode();
	}

	public CurrencyPrecision getStdPrecision(@NonNull final CurrencyId currencyId)
	{
		return getById(currencyId).getPrecision();
	}
	
	public CurrencyPrecision getStdPrecision(@NonNull final CurrencyCode currencyCode)
	{
		final CurrencyId currencyId = getCurrencyIdByCurrencyCode(currencyCode);
		return getStdPrecision(currencyId);
	}


	public CurrencyPrecision getCostingPrecision(@NonNull final CurrencyId currencyId)
	{
		return getById(currencyId).getCostingPrecision();
	}
}
