package de.metas.shipper.gateway.api.service;

import java.util.Locale;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableMap;

import de.metas.shipper.gateway.api.model.CountryCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DefaultCountryCodeFactory implements CountryCodeFactory
{
	private final ImmutableMap<String, CountryCode> countryCodesByAlpha2;
	private final ImmutableMap<String, CountryCode> countryCodesByAlpha3;

	public DefaultCountryCodeFactory()
	{
		final ImmutableMap.Builder<String, CountryCode> countryCodesByAlpha2 = ImmutableMap.builder();
		final ImmutableMap.Builder<String, CountryCode> countryCodesByAlpha3 = ImmutableMap.builder();

		for (final String countryCodeAlpha2 : Locale.getISOCountries())
		{
			final Locale locale = new Locale("", countryCodeAlpha2);
			final String countryCodeAlpha3 = locale.getISO3Country();
			final CountryCode countryCode = CountryCode.builder()
					.alpha2(countryCodeAlpha2)
					.alpha3(countryCodeAlpha3)
					.build();
			countryCodesByAlpha2.put(countryCodeAlpha2, countryCode);
			countryCodesByAlpha3.put(countryCodeAlpha3, countryCode);
		}

		this.countryCodesByAlpha2 = countryCodesByAlpha2.build();
		this.countryCodesByAlpha3 = countryCodesByAlpha3.build();
	}

	@Override
	public CountryCode getCountryCodeByAlpha2(@NonNull final String countryCodeAlpha2)
	{
		final CountryCode countryCode = countryCodesByAlpha2.get(countryCodeAlpha2);
		Check.assumeNotNull(countryCode, "countyCode shall exist for '{}' (alpha2)", countryCodeAlpha2);
		return countryCode;
	}

	@Override
	public CountryCode getCountryCodeByAlpha3(@NonNull final String countryCodeAlpha3)
	{
		final CountryCode countryCode = countryCodesByAlpha3.get(countryCodeAlpha3);
		Check.assumeNotNull(countryCode, "countyCode shall exist for '{}' (alpha3)", countryCodeAlpha3);
		return countryCode;
	}

}
