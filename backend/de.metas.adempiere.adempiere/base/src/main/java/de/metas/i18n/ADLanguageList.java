package de.metas.i18n;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
 */
@EqualsAndHashCode(exclude = "_languageTagsByADLanguage")
@ToString(exclude = "_languageTagsByADLanguage")
public final class ADLanguageList
{
	public static Builder builder()
	{
		return new Builder();
	}

	private static final Logger logger = LogManager.getLogger(ADLanguageList.class);

	private final ImmutableMap<String, ValueNamePair> languagesByADLanguage;
	@Getter private final String baseADLanguage;

	private transient ImmutableMap<String, String> _languageTagsByADLanguage = null; // lazy

	private ADLanguageList(final Builder builder)
	{
		languagesByADLanguage = ImmutableMap.copyOf(builder.languagesByADLanguage);
		baseADLanguage = builder.baseADLanguage;
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

	public boolean containsADLanguage(final String adLanguage)
	{
		return languagesByADLanguage.containsKey(adLanguage);
	}

	public Optional<String> findSimilarADLanguage(@NonNull final String adLanguage)
	{
		if (languagesByADLanguage.containsKey(adLanguage))
		{
			return Optional.of(adLanguage);
		}

		final String langPart = adLanguage.substring(0, 2);
		if (isADLanguageMatchingLangPart(baseADLanguage, langPart))
		{
			return Optional.of(baseADLanguage);
		}

		for (final String otherADLanguage : languagesByADLanguage.keySet())
		{
			if (isADLanguageMatchingLangPart(otherADLanguage, langPart))
			{
				return Optional.of(otherADLanguage);
			}
		}

		return Optional.empty();
	}

	private static boolean isADLanguageMatchingLangPart(@NonNull final String adLanguage, @NonNull final String expectedLangPart)
	{
		final String langPart = adLanguage.substring(0, 2);
		return langPart.equals(expectedLangPart);
	}

	/**
	 * Extract preferred language from HTTP "Accept-Language" header.
	 *
	 * @return preferred language or <code>defaultValue</code>
	 * See <a href="https://tools.ietf.org/html/rfc5646#section-2.1">RFC 5646</a>
	 */
	@Nullable
	public String getAD_LanguageFromHttpAcceptLanguage(
			@Nullable final String acceptLanguageHeader,
			@Nullable final String defaultValue)
	{
		if (acceptLanguageHeader == null || Check.isBlank(acceptLanguageHeader))
		{
			return defaultValue;
		}

		//
		// Corner-case: Accept-Language is not a valid language tag but an actual AD_Language.
		// (i.e. check if has xx_XX format)
		if (acceptLanguageHeader.length() == 5 && acceptLanguageHeader.charAt(2) == '_')
		{
			return findSimilarADLanguage(acceptLanguageHeader).orElse(defaultValue);
		}

		final Map<String, String> tagsByADLanguage = getLanguageTagsByADLanguageMap();
		try
		{
			final List<LanguageRange> languageRanges = LanguageRange.parse(acceptLanguageHeader);
			if (languageRanges.isEmpty())
			{
				return defaultValue;
			}

			final String languageTag = Locale.lookupTag(languageRanges, tagsByADLanguage.keySet());
			if (languageTag == null)
			{
				return defaultValue;
			}

			final String adLanguage = tagsByADLanguage.get(languageTag.toLowerCase());
			if (adLanguage == null)
			{
				return defaultValue;
			}

			return adLanguage;
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching AD_Language from {} (tagsByADLanguage={}). Returning {}", acceptLanguageHeader, tagsByADLanguage, defaultValue, ex);
			return defaultValue;
		}
	}

	private Map<String, String> getLanguageTagsByADLanguageMap()
	{
		ImmutableMap<String, String> languageTagsByADLanguage = this._languageTagsByADLanguage;
		if (languageTagsByADLanguage == null)
		{
			languageTagsByADLanguage = this._languageTagsByADLanguage = computeLanguageTagsByADLanguageMap();
		}
		return languageTagsByADLanguage;
	}

	private ImmutableMap<String, String> computeLanguageTagsByADLanguageMap()
	{
		final HashMap<String, String> tag2adLanguage = new HashMap<>();
		for (final String adLanguage : languagesByADLanguage.keySet())
		{
			final String tag = toHttpLanguageTag(adLanguage);
			tag2adLanguage.put(tag, adLanguage);

			final String languagePart = extractLanguagePartFromTag(tag);
			if (languagePart != null && (isBaseLanguage(adLanguage) || !tag2adLanguage.containsKey(languagePart)))
			{
				tag2adLanguage.put(languagePart, adLanguage);
			}
		}

		return ImmutableMap.copyOf(tag2adLanguage);
	}

	@Nullable
	private static String extractLanguagePartFromTag(final String tag)
	{
		final int idx = tag.indexOf("-");
		return idx > 0 ? tag.substring(0, idx) : null;
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
