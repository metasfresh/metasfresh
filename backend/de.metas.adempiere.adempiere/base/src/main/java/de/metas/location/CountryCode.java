/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Locale;

@Value
public class CountryCode
{
	public static final CountryCode DE;
	public static final CountryCode CH;

	@JsonProperty("alpha2") String alpha2;
	@JsonProperty("alpha3") String alpha3;

	@NonNull private static final ImmutableMap<String, CountryCode> byAlpha2;
	@NonNull private static final ImmutableMap<String, CountryCode> byAlpha3;

	static
	{
		final ImmutableMap.Builder<String, CountryCode> byAlpha2Builder = ImmutableMap.builder();
		final ImmutableMap.Builder<String, CountryCode> byAlpha3Builder = ImmutableMap.builder();

		for (final String countryCodeAlpha2 : Locale.getISOCountries())
		{
			final Locale locale = new Locale("", countryCodeAlpha2);
			final String countryCodeAlpha3 = locale.getISO3Country();
			final CountryCode countryCode = CountryCode.builder()
					.alpha2(countryCodeAlpha2)
					.alpha3(countryCodeAlpha3)
					.build();
			byAlpha2Builder.put(countryCodeAlpha2, countryCode);
			byAlpha3Builder.put(countryCodeAlpha3, countryCode);
		}

		byAlpha2 = byAlpha2Builder.build();
		byAlpha3 = byAlpha3Builder.build();

		DE = Check.assumeNotNull(byAlpha2.get("DE"), "DE shall exist");
		CH = Check.assumeNotNull(byAlpha2.get("CH"), "DE shall exist");
	}

	public static CountryCode ofAlpha2(@NonNull final String countryCodeAlpha2)
	{
		final CountryCode countryCode = byAlpha2.get(countryCodeAlpha2);
		Check.assumeNotNull(countryCode, "countyCode shall exist for '{}' (alpha2)", countryCodeAlpha2);
		return countryCode;
	}

	public static CountryCode ofAlpha3(@NonNull final String countryCodeAlpha3)
	{
		final CountryCode countryCode = byAlpha3.get(countryCodeAlpha3);
		Check.assumeNotNull(countryCode, "countyCode shall exist for '{}' (alpha3)", countryCodeAlpha3);
		return countryCode;
	}

	@Builder
	@Jacksonized
	public CountryCode(@NonNull final String alpha2, @NonNull final String alpha3)
	{
		Check.assumeNotEmpty(alpha2, "alpha2 is not empty");
		Check.assumeNotEmpty(alpha3, "alpha3 is not empty");

		this.alpha2 = alpha2;
		this.alpha3 = alpha3;
	}

}
