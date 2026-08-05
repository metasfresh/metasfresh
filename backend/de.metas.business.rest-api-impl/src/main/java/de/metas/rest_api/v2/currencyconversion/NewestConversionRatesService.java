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
import de.metas.currency.CurrencyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Reads the <b>newest</b> stored conversion rate per {@code (from, to, type)} combo from
 * {@code C_Conversion_Rate}.
 * <p>
 * For every distinct {@code (C_Currency_ID, C_Currency_ID_To, C_ConversionType_ID)} combo, exactly one row is
 * returned — the one with the maximum {@code ValidFrom} (the most recently imported rate). Scope is the
 * {@code METASFRESH} client (plus the {@code SYSTEM} client, matching the runtime rate-lookup path) and any org
 * (or the one org the optional {@code orgCode} filter selects). Optional {@code from}/{@code to}/{@code type}
 * filters narrow the result further.
 * <p>
 * The newest-per-combo reduction is done <b>DB-side</b> by
 * {@link ConversionRateRepository#getNewestRatesOrderedByValidFromDesc} (native {@code DISTINCT ON}), so the repository
 * already returns exactly one row per combo — this service only maps each row to its JSON DTO.
 */
@Service
@RequiredArgsConstructor
public class NewestConversionRatesService
{
	@NonNull private final ConversionRateRepository conversionRateRepository;
	@NonNull private final JsonConversionRateConverters jsonConverters;

	/**
	 * Test-only factory mirroring {@code CustomColumnService.newInstanceForUnitTesting}: asserts unit-test mode and
	 * wires the collaborators (repository + JSON converters). The collaborators are reachable via
	 * {@link #getConversionRateRepository()} / {@link #getJsonConverters()} so a test can drive both the service and
	 * its collaborators from a single factory call.
	 */
	@VisibleForTesting
	@NonNull
	public static NewestConversionRatesService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		final ConversionRateRepository conversionRateRepository = new ConversionRateRepository();
		final JsonConversionRateConverters jsonConverters = new JsonConversionRateConverters(new CurrencyRepository(), conversionRateRepository);
		return new NewestConversionRatesService(conversionRateRepository, jsonConverters);
	}

	@VisibleForTesting
	@NonNull
	public ConversionRateRepository getConversionRateRepository()
	{
		return conversionRateRepository;
	}

	@VisibleForTesting
	@NonNull
	public JsonConversionRateConverters getJsonConverters()
	{
		return jsonConverters;
	}

	@NonNull
	public List<JsonNewestConversionRate> list(@NonNull final ConversionRateQuery query)
	{
		// The repository already reduces to exactly one row per (from, to, type) combo (DB-side DISTINCT ON),
		// so this only maps each returned rate to its JSON DTO.
		final List<CurrencyConversionRate> rates = conversionRateRepository.getNewestRatesOrderedByValidFromDesc(query);

		final List<JsonNewestConversionRate> result = new ArrayList<>(rates.size());
		for (final CurrencyConversionRate rate : rates)
		{
			result.add(jsonConverters.toJsonNewestConversionRate(rate));
		}
		return result;
	}
}
