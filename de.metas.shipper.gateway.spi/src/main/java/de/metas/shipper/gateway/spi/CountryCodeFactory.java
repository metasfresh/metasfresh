/*
 * #%L
 * de.metas.shipper.gateway.spi
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

package de.metas.shipper.gateway.spi;

import com.google.common.collect.ImmutableMap;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.util.Check;
import lombok.NonNull;

import java.util.Locale;

public class CountryCodeFactory
{
	private final ImmutableMap<String, CountryCode> countryCodesByAlpha2;

	public CountryCodeFactory()
	{
		final ImmutableMap.Builder<String, CountryCode> countryCodesByAlpha2 = ImmutableMap.builder();

		for (final String countryCodeAlpha2 : Locale.getISOCountries())
		{
			final Locale locale = new Locale("", countryCodeAlpha2);
			final String countryCodeAlpha3 = locale.getISO3Country();
			final CountryCode countryCode = CountryCode.builder()
					.alpha2(countryCodeAlpha2)
					.alpha3(countryCodeAlpha3)
					.build();
			countryCodesByAlpha2.put(countryCodeAlpha2, countryCode);
		}

		this.countryCodesByAlpha2 = countryCodesByAlpha2.build();
	}

	public CountryCode getCountryCodeByAlpha2(@NonNull final String countryCodeAlpha2)
	{
		final CountryCode countryCode = countryCodesByAlpha2.get(countryCodeAlpha2);
		Check.assumeNotNull(countryCode, "countyCode shall exist for '{}' (alpha2)", countryCodeAlpha2);
		return countryCode;
	}
}
