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

import com.google.common.annotations.VisibleForTesting;
import de.metas.common.rest_api.v2.currencyconversion.JsonNewestConversionRate;
import de.metas.currency.CurrencyConversionRate;
import de.metas.currency.ConversionRateQuery;
import de.metas.currency.ConversionRateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Reads the newest stored conversion rate (max {@code ValidFrom}) per {@code (from, to, type)} combo from
 * {@code C_Conversion_Rate}. The newest-per-combo reduction is done DB-side (native {@code DISTINCT ON}) by
 * {@link ConversionRateRepository#getNewestRatesOrderedByValidFromDesc}; this service only maps each row to its JSON DTO.
 */
@Service
@RequiredArgsConstructor
public class NewestConversionRatesService
{
	@NonNull private final ConversionRateRepository conversionRateRepository;
	@NonNull private final JsonConversionRateConverters jsonConverters;

	/** Per-bean test factory (no JUnit yet — {@code list()} is cucumber-covered via GET newestRates). */
	@VisibleForTesting
	@NonNull
	public static NewestConversionRatesService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(NewestConversionRatesService.class,
				() -> new NewestConversionRatesService(
						ConversionRateRepository.newInstanceForUnitTesting(),
						JsonConversionRateConverters.newInstanceForUnitTesting()));
	}

	@NonNull
	public List<JsonNewestConversionRate> list(@NonNull final ConversionRateQuery query)
	{
		final List<CurrencyConversionRate> rates = conversionRateRepository.getNewestRatesOrderedByValidFromDesc(query);

		final List<JsonNewestConversionRate> result = new ArrayList<>(rates.size());
		for (final CurrencyConversionRate rate : rates)
		{
			result.add(jsonConverters.toJsonNewestConversionRate(rate));
		}
		return result;
	}
}
