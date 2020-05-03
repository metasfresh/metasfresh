package de.metas.currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@ToString
@EqualsAndHashCode
final class FixedConversionRateMap
{
	public static final FixedConversionRateMap EMPTY = new FixedConversionRateMap(ImmutableMap.of());

	private final ImmutableMap<FixedConversionRateKey, FixedConversionRate> rates;

	private FixedConversionRateMap(final Map<FixedConversionRateMap.FixedConversionRateKey, FixedConversionRate> rates)
	{
		this.rates = ImmutableMap.copyOf(rates);
	}

	public FixedConversionRateMap addingConversionRate(@NonNull final FixedConversionRate rate)
	{
		final HashMap<FixedConversionRateMap.FixedConversionRateKey, FixedConversionRate> newRates = new HashMap<>(rates);
		newRates.put(extractKey(rate), rate);
		return new FixedConversionRateMap(newRates);
	}

	private static FixedConversionRateMap.FixedConversionRateKey extractKey(@NonNull final FixedConversionRate rate)
	{
		return FixedConversionRateKey.builder()
				.fromCurrencyId(rate.getFromCurrencyId())
				.toCurrencyId(rate.getToCurrencyId())
				.build();
	}

	public BigDecimal getMultiplyRate(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId)
	{
		final BigDecimal multiplyRate = getMultiplyRateOrNull(fromCurrencyId, toCurrencyId);
		if (multiplyRate == null)
		{
			throw new AdempiereException("No fixed conversion rate found from " + fromCurrencyId + " to " + toCurrencyId + "."
					+ " Available rates are: " + rates.values());
		}
		return multiplyRate;
	}

	public boolean hasMultiplyRate(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId)
	{
		return getMultiplyRateOrNull(fromCurrencyId, toCurrencyId) != null;
	}

	private BigDecimal getMultiplyRateOrNull(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId)
	{
		final FixedConversionRateMap.FixedConversionRate rate = rates.get(FixedConversionRateKey.builder()
				.fromCurrencyId(fromCurrencyId)
				.toCurrencyId(toCurrencyId)
				.build());
		return rate != null ? rate.getMultiplyRate() : null;
	}

	@Value
	@Builder
	private static class FixedConversionRateKey
	{
		@NonNull
		CurrencyId fromCurrencyId;

		@NonNull
		CurrencyId toCurrencyId;
	}

	@Value
	@Builder
	public static class FixedConversionRate
	{
		@NonNull
		CurrencyId fromCurrencyId;

		@NonNull
		CurrencyId toCurrencyId;

		@NonNull
		BigDecimal multiplyRate;
	}
}
