/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.currencyconversion;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.v2.currencyconversion.JsonCurrency;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Currency;
import org.springframework.stereotype.Repository;

/**
 * Thin persistence layer for the currency-conversion REST endpoints: keeps the {@code IQueryBL} query
 * construction out of {@link CurrencyConversionRestController} (persistence primitives belong in a
 * DAO/Repository, not in a controller).
 */
@Repository
public class CurrencyConversionRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * The active currencies, ordered by ISO code, mapped to {@link JsonCurrency} ({@code currencyCode} =
	 * ISO code, {@code name} = {@code Description}).
	 */
	@NonNull
	public ImmutableList<JsonCurrency> getActiveCurrencies()
	{
		return queryBL
				.createQueryBuilder(I_C_Currency.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Currency.COLUMNNAME_ISO_Code)
				.create()
				.stream()
				.map(CurrencyConversionRepository::toJsonCurrency)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static JsonCurrency toJsonCurrency(@NonNull final I_C_Currency currency)
	{
		return JsonCurrency.builder()
				.currencyCode(currency.getISO_Code())
				.name(currency.getDescription())
				.build();
	}
}
