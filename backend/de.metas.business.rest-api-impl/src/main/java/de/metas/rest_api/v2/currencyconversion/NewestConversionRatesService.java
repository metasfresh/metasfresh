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

import de.metas.common.rest_api.v2.currencyconversion.JsonNewestConversionRate;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.ICurrencyDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Reads the <b>newest</b> stored conversion rate per {@code (from, to, type)} combo from
 * {@code C_Conversion_Rate}.
 * <p>
 * For every distinct {@code (C_Currency_ID, C_Currency_ID_To, C_ConversionType_ID)} combo, exactly one
 * row is returned — the one with the maximum {@code ValidFrom} (the most recently imported rate). Scope
 * is the session client (plus the {@code SYSTEM} client, matching the runtime rate-lookup path) and any
 * org. Optional {@code from}/{@code to}/{@code conversionType} filters narrow the result.
 * <p>
 * The newest-per-combo reduction is done in Java over a {@code ValidFrom}-descending query rather than
 * via SQL {@code DISTINCT ON}: the in-memory POJO query layer used by the unit tests does not support
 * {@code DISTINCT ON}, and the rate table is small per combo, so an ordered scan + first-wins reduction
 * is both portable and cheap.
 */
@Service
@RequiredArgsConstructor
public class NewestConversionRatesService
{
	@NonNull private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	@NonNull private final CurrencyConversionRepository currencyConversionRepository;

	@NonNull
	public List<JsonNewestConversionRate> list(
			@NonNull final ClientId clientId,
			@NonNull final NewestConversionRatesFilter filter)
	{
		final CurrencyId fromCurrencyId = resolveOptionalCurrencyId(filter.getFromCurrencyCode());
		final CurrencyId toCurrencyId = resolveOptionalCurrencyId(filter.getToCurrencyCode());
		final CurrencyConversionTypeId conversionTypeId = resolveOptionalConversionTypeId(filter.getConversionTypeCode());

		final List<I_C_Conversion_Rate> rates = currencyConversionRepository.getConversionRatesOrderedByValidFromDesc(
				clientId, fromCurrencyId, toCurrencyId, conversionTypeId);

		// Ordered ValidFrom-descending, so the FIRST row seen per combo is the newest -> first-wins.
		final Map<ComboKey, I_C_Conversion_Rate> newestByCombo = new LinkedHashMap<>();
		for (final I_C_Conversion_Rate rate : rates)
		{
			newestByCombo.putIfAbsent(ComboKey.ofRate(rate), rate);
		}

		final List<JsonNewestConversionRate> result = new ArrayList<>(newestByCombo.size());
		for (final I_C_Conversion_Rate rate : newestByCombo.values())
		{
			result.add(toJson(rate));
		}
		return result;
	}

	@Nullable
	private CurrencyId resolveOptionalCurrencyId(@Nullable final String isoCode)
	{
		if (Check.isBlank(isoCode))
		{
			return null;
		}
		return currencyConversionRepository.findActiveCurrencyIdByIsoCode(isoCode.trim());
	}

	@Nullable
	private CurrencyConversionTypeId resolveOptionalConversionTypeId(@Nullable final String conversionTypeCode)
	{
		if (Check.isBlank(conversionTypeCode))
		{
			return null;
		}
		final ConversionTypeMethod method;
		try
		{
			method = ConversionTypeMethod.forCode(conversionTypeCode.trim());
		}
		catch (final IllegalArgumentException ex)
		{
			throw new AdempiereException("@Invalid@ @C_ConversionType_ID@: " + conversionTypeCode)
					.markAsUserValidationError();
		}
		return currencyDAO.getConversionTypeId(method);
	}

	@NonNull
	private JsonNewestConversionRate toJson(@NonNull final I_C_Conversion_Rate rate)
	{
		final CurrencyId fromCurrencyId = CurrencyId.ofRepoId(rate.getC_Currency_ID());
		final CurrencyId toCurrencyId = CurrencyId.ofRepoId(rate.getC_Currency_ID_To());
		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoId(rate.getC_ConversionType_ID());

		return JsonNewestConversionRate.builder()
				.fromCurrencyCode(currencyDAO.getCurrencyCodeById(fromCurrencyId).toThreeLetterCode())
				.toCurrencyCode(currencyDAO.getCurrencyCodeById(toCurrencyId).toThreeLetterCode())
				.conversionTypeCode(currencyDAO.getConversionTypeMethodById(conversionTypeId).getCode())
				.validFrom(TimeUtil.asLocalDate(rate.getValidFrom()))
				.multiplyRate(rate.getMultiplyRate())
				.divideRate(rate.getDivideRate())
				.build();
	}

	/** Filter for {@link #list(ClientId, NewestConversionRatesFilter)}; all fields optional (null = no narrowing). */
	@Value
	@Builder
	public static class NewestConversionRatesFilter
	{
		@Nullable String fromCurrencyCode;
		@Nullable String toCurrencyCode;
		@Nullable String conversionTypeCode;
	}

	/** The per-combo grouping key: {@code (from, to, type)} by typed id. */
	@RequiredArgsConstructor
	@EqualsAndHashCode
	private static final class ComboKey
	{
		@NonNull private final CurrencyId fromCurrencyId;
		@NonNull private final CurrencyId toCurrencyId;
		@NonNull private final CurrencyConversionTypeId conversionTypeId;

		private static ComboKey ofRate(@NonNull final I_C_Conversion_Rate rate)
		{
			return new ComboKey(
					CurrencyId.ofRepoId(rate.getC_Currency_ID()),
					CurrencyId.ofRepoId(rate.getC_Currency_ID_To()),
					CurrencyConversionTypeId.ofRepoId(rate.getC_ConversionType_ID()));
		}
	}
}
