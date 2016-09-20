package de.metas.i18n;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Language;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Translatable parameterized string. It consists of:
 * <ul>
 * <li>string to be used for base language
 * <li>string to be used for any other language. This string can contain a parameter which will be replaced with actual AD_Language when returned
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class TranslatableParameterizedString
{
	/**
	 * Builds a new instance.
	 *
	 * @param adLanguageParamName AD_Language parameter or null
	 * @param stringBaseLang string to be used for base language
	 * @param stringTrlPatternstring to be used for any other language; it might contain the <code>adLanguageParamName</code> parameter
	 * @return
	 */
	public static final TranslatableParameterizedString of(final CtxName adLanguageParamName, final String stringBaseLang, final String stringTrlPattern)
	{
		final String adLanguageParamNameStr = adLanguageParamName == null ? null : adLanguageParamName.toStringWithMarkers();
		return of(adLanguageParamNameStr, stringBaseLang, stringTrlPattern);
	}

	public static final TranslatableParameterizedString of(final String adLanguageParamNameStr, final String stringBaseLang, final String stringTrlPattern)
	{
		if (adLanguageParamNameStr == null)
		{
			return constant(null, stringBaseLang, stringTrlPattern);
		}

		if (stringTrlPattern == null)
		{
			return constant(adLanguageParamNameStr, stringBaseLang, null);
		}

		if (stringTrlPattern.indexOf(adLanguageParamNameStr) < 0)
		{
			return constant(adLanguageParamNameStr, stringBaseLang, stringTrlPattern);
		}

		return new RegularTranslatableParameterizedString(adLanguageParamNameStr, stringBaseLang, stringTrlPattern);
	}

	private static final TranslatableParameterizedString constant(final String adLanguageParamName, final String stringBaseLang, final String stringTrl)
	{
		if (Objects.equals(stringBaseLang, stringTrl))
		{
			if (Objects.equals(adLanguageParamName, EMPTY.getAD_LanguageParamName())
					&& Objects.equals(stringBaseLang, EMPTY.getStringBaseLanguage()))
			{
				return EMPTY;
			}

			return new NoTranslatableParameterizedString(adLanguageParamName, stringBaseLang);
		}

		return new ConstantTranslatableParameterizedString(adLanguageParamName, stringBaseLang, stringTrl);
	}

	public static final TranslatableParameterizedString EMPTY = new EmptyTranslatableParameterizedString();

	public abstract String getAD_LanguageParamName();

	/**
	 * @return string for base language
	 */
	public abstract String getStringBaseLanguage();

	/**
	 * @return string for not base language; it might contain the {@link #getAD_LanguageParamName()} parameter.
	 */
	public abstract String getStringTrlPattern();

	/**
	 * @return translated string using current context language
	 */
	public String translate()
	{
		final String adLanguage = Env.getAD_Language(Env.getCtx());
		return translate(adLanguage);
	}

	/**
	 * @param adLanguage
	 * @return translated string using given language
	 */
	public abstract String translate(String adLanguage);

	/**
	 * Transforms this object to a new instance by applying the mapping function to it's internal strings.
	 * 
	 * @param mappingFunction a function which converts the old string to a new string
	 * @return a new instance or the same one in case the transformation didn't change the strings
	 */
	public TranslatableParameterizedString transform(final Function<String, String> mappingFunction)
	{
		final String stringBaseLangTransformed = mappingFunction.apply(getStringBaseLanguage());
		final String stringTrlPatternTransformed = mappingFunction.apply(getStringTrlPattern());
		return TranslatableParameterizedString.of(getAD_LanguageParamName(), stringBaseLangTransformed, stringTrlPatternTransformed);
	}

	private static final class EmptyTranslatableParameterizedString extends TranslatableParameterizedString
	{
		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).toString();
		}

		@Override
		public String getAD_LanguageParamName()
		{
			return null;
		}

		@Override
		public String getStringBaseLanguage()
		{
			return null;
		}

		@Override
		public String getStringTrlPattern()
		{
			return null;
		}

		@Override
		public String translate()
		{
			return null;
		}

		@Override
		public String translate(final String adLanguage)
		{
			return null;
		}
	}

	private static final class RegularTranslatableParameterizedString extends TranslatableParameterizedString
	{
		private final String stringBaseLang;
		private final String stringTrlPattern;
		private final String adLanguageParamName;

		/** Map of AD_Language to translated string */
		private volatile Map<String, String> _stringTrls; // lazy

		private RegularTranslatableParameterizedString(final String adLanguageParamName, final String stringBaseLang, final String stringTrlPattern)
		{
			super();
			this.adLanguageParamName = adLanguageParamName;
			this.stringBaseLang = stringBaseLang;
			this.stringTrlPattern = stringTrlPattern;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("adLanguageParamName", adLanguageParamName)
					.add("stringBaseLang", stringBaseLang)
					.add("stringTrlPattern", stringTrlPattern)
					.toString();
		}

		@Override
		public String getAD_LanguageParamName()
		{
			return adLanguageParamName;
		}

		@Override
		public String getStringBaseLanguage()
		{
			return stringBaseLang;
		}

		@Override
		public String getStringTrlPattern()
		{
			return stringTrlPattern;
		}

		@Override
		public String translate(final String adLanguage)
		{
			if (adLanguage == null || adLanguage.isEmpty())
			{
				return stringBaseLang;
			}

			if (_stringTrls == null)
			{
				synchronized (this)
				{
					if (_stringTrls == null)
					{
						_stringTrls = new ConcurrentHashMap<>();
					}
				}
			}

			return _stringTrls.computeIfAbsent(adLanguage, adLanguageKey -> buildStringTrl(adLanguageKey));
		}

		private final String buildStringTrl(final String adLanguage)
		{
			if (Language.isBaseLanguage(adLanguage))
			{
				return stringBaseLang;
			}

			return stringTrlPattern.replace(adLanguageParamName, adLanguage);
		}
	}

	private static final class ConstantTranslatableParameterizedString extends TranslatableParameterizedString
	{
		private final String adLanguageParamName;
		private final String stringBaseLang;
		private final String stringTrl;

		private ConstantTranslatableParameterizedString(final String adLanguageParamName, final String stringBaseLang, final String stringTrl)
		{
			super();
			this.adLanguageParamName = adLanguageParamName;
			this.stringBaseLang = stringBaseLang;
			this.stringTrl = stringTrl;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("adLanguageParamName", adLanguageParamName)
					.add("stringBaseLang", stringBaseLang)
					.add("stringTrl", stringTrl)
					.toString();
		}

		@Override
		public String getAD_LanguageParamName()
		{
			return adLanguageParamName;
		}

		@Override
		public String getStringBaseLanguage()
		{
			return stringBaseLang;
		}

		@Override
		public String getStringTrlPattern()
		{
			return stringTrl;
		}

		@Override
		public String translate(final String adLanguage)
		{
			if (Language.isBaseLanguage(adLanguage))
			{
				return stringBaseLang;
			}
			else
			{
				return stringTrl;
			}
		}
	}

	private static final class NoTranslatableParameterizedString extends TranslatableParameterizedString
	{
		private final String adLanguageParamName;
		private final String string;

		private NoTranslatableParameterizedString(final String adLanguageParamName, final String string)
		{
			super();
			this.adLanguageParamName = adLanguageParamName;
			this.string = string;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("adLanguageParamName", adLanguageParamName)
					.add("string", string)
					.toString();
		}

		@Override
		public String getAD_LanguageParamName()
		{
			return adLanguageParamName;
		}

		@Override
		public String getStringBaseLanguage()
		{
			return string;
		}

		@Override
		public String getStringTrlPattern()
		{
			return string;
		}

		@Override
		public String translate()
		{
			return string;
		}

		@Override
		public String translate(final String adLanguage)
		{
			return string;
		}

		@Override
		public NoTranslatableParameterizedString transform(final Function<String, String> mappingFunction)
		{
			final String stringTransformed = mappingFunction.apply(string);
			if (Objects.equals(string, stringTransformed))
			{
				return this;
			}

			return new NoTranslatableParameterizedString(adLanguageParamName, stringTransformed);
		}
	}
}
