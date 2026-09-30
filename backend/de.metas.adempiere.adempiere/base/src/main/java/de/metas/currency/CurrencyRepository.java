package de.metas.currency;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.currency.impl.CurrencyDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
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
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static CurrencyRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(CurrencyRepository.class, CurrencyRepository::new);
	}

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
	 * The id of the single active {@code C_Currency} for the ISO code, or {@code null} if none matches.
	 * Unlike {@link #getCurrencyIdByCurrencyCode(CurrencyCode)}, does not auto-create a missing currency.
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

	/** The active currencies, ordered by ISO code. */
	@NonNull
	public ImmutableList<Currency> getActiveCurrenciesOrderedByCode()
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_Currency.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Currency.COLUMNNAME_ISO_Code)
				.create()
				.stream()
				.map(CurrencyDAO::toCurrency)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The {@code C_Currency} record for the given ISO code (any active state), or {@code null} if none exists.
	 * Not active-filtered on purpose, so a caller can obtain and mutate an inactive currency too.
	 */
	@Nullable
	public I_C_Currency getRecordByCurrencyCodeOrNull(@NonNull final CurrencyCode currencyCode)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_Currency.class)
				.addEqualsFilter(I_C_Currency.COLUMNNAME_ISO_Code, currencyCode.toThreeLetterCode())
				.create()
				.first(I_C_Currency.class);
	}

	/** {@code true} iff a {@code C_Currency} row exists for the given ISO code (any active state). */
	public boolean existsByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		return getRecordByCurrencyCodeOrNull(currencyCode) != null;
	}
}
