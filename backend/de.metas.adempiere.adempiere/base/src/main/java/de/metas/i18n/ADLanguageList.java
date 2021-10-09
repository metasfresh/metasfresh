package de.metas.i18n;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Immutable object which contains languages {@link ValueNamePair}s and also the base AD_Language.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Immutable
@ToString
public final class ADLanguageList
{
	public static Builder builder()
	{
		return new Builder();
	}

	private static final transient Logger logger = LogManager.getLogger(ADLanguageList.class);

	private final ImmutableMap<String, ValueNamePair> languagesByADLanguage;
	private final String baseADLanguage;

	private ADLanguageList(final Builder builder)
	{
		languagesByADLanguage = ImmutableMap.copyOf(builder.languagesByADLanguage);
		baseADLanguage = builder.baseADLanguage;
	}

	/**
	 * @return languages ordered by base language first, then alphabetically by their name
	 */
	public List<ValueNamePair> toValueNamePairsBaseLanguageFirst()
	{
		final Comparator<? super ValueNamePair> comparator = Comparator.<ValueNamePair, Integer> comparing(lang -> isBaseLanguage(lang) ? 0 : 1)
				.thenComparing(ValueNamePair::getName);

		return languagesByADLanguage.values()
				.stream()
				.sorted(comparator)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @return languages ordered alphabetically by their name
	 */
	public List<ValueNamePair> toValueNamePairs()
	{
		return languagesByADLanguage.values()
				.stream()
				.sorted(Comparator.comparing(ValueNamePair::getName))
				.collect(ImmutableList.toImmutableList());
	}

	public Set<String> getAD_Languages()
	{
		return languagesByADLanguage.keySet();
	}

	/**
	 * Extract preferred language from HTTP "Accept-Language" header.
	 * 
	 * @return preferred language or <code>defaultValue</code>
	 * See https://tools.ietf.org/html/rfc5646#section-2.1
	 */
	@Nullable
	public String getAD_LanguageFromHttpAcceptLanguage(
			@Nullable final String acceptLanguageHeader,
			@Nullable final String defaultValue)
	{
		if (Check.isEmpty(acceptLanguageHeader, true))
		{
			return defaultValue;
		}

		try
		{
			final List<LanguageRange> languageRanges = LanguageRange.parse(acceptLanguageHeader);
			if (languageRanges.isEmpty())
			{
				return defaultValue;
			}

			final BiMap<String, String> adLanguage2tag = toHttpLanguageTags();

			final String languageTag = Locale.lookupTag(languageRanges, adLanguage2tag.values());
			if (languageTag == null)
			{
				return defaultValue;
			}

			final String adLanguage = adLanguage2tag.inverse().get(languageTag.toLowerCase());
			if (adLanguage == null)
			{
				return defaultValue;
			}

			return adLanguage;
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching AD_Language from {}. Returning {}", acceptLanguageHeader, defaultValue, ex);
			return defaultValue;
		}
	}

	/** @return "adLanguage" to "language tag" bi-map. */
	private BiMap<String, String> toHttpLanguageTags()
	{
		return languagesByADLanguage.keySet()
				.stream()
				.collect(ImmutableBiMap.toImmutableBiMap(adLanguage -> adLanguage, adLanguage -> toHttpLanguageTag(adLanguage)));
	}

	public static String toHttpLanguageTag(final String adLanguage)
	{
		return adLanguage.replace('_', '-').trim().toLowerCase();
	}

	public boolean isBaseLanguage(final ValueNamePair language)
	{
		return isBaseLanguage(language.getValue());
	}

	public boolean isBaseLanguage(final String adLanguage)
	{
		if (baseADLanguage == null)
		{
			return false;
		}

		return baseADLanguage.equals(adLanguage);
	}

	//
	//
	//
	//

	//
	public static class Builder
	{
		private final Map<String, ValueNamePair> languagesByADLanguage = new LinkedHashMap<>();
		private String baseADLanguage = null;

		private Builder()
		{
		}

		public ADLanguageList build()
		{
			return new ADLanguageList(this);
		}

		public Builder addLanguage(@NonNull final String adLanguage, @NonNull final String caption, final boolean isBaseLanguage)
		{
			final ValueNamePair languageVNP = ValueNamePair.of(adLanguage, caption);
			languagesByADLanguage.put(adLanguage, languageVNP);
			if (isBaseLanguage)
			{
				baseADLanguage = adLanguage;
			}

			return this;
		}
	}

}
