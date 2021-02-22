/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;

@Value
public class LanguageKey
{
	@Nullable
	public static LanguageKey ofNullableString(@Nullable final String languageInfo)
	{
		return languageInfo != null && !languageInfo.isBlank()
				? ofString(languageInfo)
				: null;
	}

	public static Optional<LanguageKey> optionalOfNullableString(@Nullable final String languageInfo)
	{
		return Optional.ofNullable(ofNullableString(languageInfo));
	}

	@JsonCreator
	public static LanguageKey ofString(@NonNull final String languageInfo)
	{
		final String separator = languageInfo.contains("-") ? "-" : "_";
		final String[] localeParts = languageInfo.trim().split(separator);
		if (localeParts.length == 0)
		{
			// shall not happen
			throw new IllegalArgumentException("Invalid languageInfo: " + languageInfo);
		}
		else if (localeParts.length == 1)
		{
			final String language = localeParts[0];
			final String country = "";
			final String variant = "";
			return new LanguageKey(language, country, variant);
		}
		else if (localeParts.length == 2)
		{
			final String language = localeParts[0];
			final String country = localeParts[1];
			final String variant = "";
			return new LanguageKey(language, country, variant);
		}
		else
		// if (localeParts.length >= 3)
		{
			final String language = localeParts[0];
			final String country = localeParts[1];
			final String variant = localeParts[2];
			return new LanguageKey(language, country, variant);
		}
	}

	public static LanguageKey ofLocale(@NonNull final Locale locale)
	{
		return new LanguageKey(locale.getLanguage(), locale.getCountry(), locale.getVariant());
	}

	public static LanguageKey getDefault()
	{
		return ofLocale(Locale.getDefault());
	}

	@NonNull
	String language;
	@NonNull
	String country;
	@NonNull
	String variant;

	public Locale toLocale()
	{
		return new Locale(language, country, variant);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(language);
		if (!country.isBlank())
		{
			sb.append("_").append(country);
		}
		if (!variant.isBlank())
		{
			sb.append("_").append(variant);
		}
		return sb.toString();
	}
}
