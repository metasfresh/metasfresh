package de.metas.i18n;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * A string which can be translated to a given <code>AD_Language</code>.<br>
 * You can use e.g. {@link IMsgBL#getTranslatableMsgText(String, Object...)} to obtain an instance.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ITranslatableString
{
	public static ITranslatableString compose(final String joiningString, final Object... trls)
	{
		if (trls == null || trls.length == 0)
		{
			throw new IllegalArgumentException("trls is null or empty");
		}

		final List<ITranslatableString> trlsList = Stream.of(trls)
				.map(ITranslatableString::toTranslatableStringOrNull)
				.filter(trl -> trl != null) // skip nulls
				.collect(ImmutableList.toImmutableList());

		if (trlsList.isEmpty())
		{
			return empty();
		}
		else if (trlsList.size() == 1)
		{
			return trlsList.get(0);
		}
		else
		{
			return new CompositeTranslatableString(trlsList, joiningString);
		}
	}

	/**
	 * @return translatable string or null if the <code>trlObj</code> is null or empty string
	 */
	static ITranslatableString toTranslatableStringOrNull(final Object trlObj)
	{
		if (trlObj == null)
		{
			return null;
		}
		else if (trlObj instanceof ITranslatableString)
		{
			return (ITranslatableString)trlObj;
		}
		else
		{
			final String trlStr = trlObj.toString();
			if (trlStr == null || trlStr.isEmpty())
			{
				return null;
			}
			else
			{
				return constant(trlStr);
			}
		}
	}

	public static ITranslatableString compose(final String joiningString, @NonNull final List<ITranslatableString> trls)
	{
		if (trls.isEmpty())
		{
			return empty();
		}
		else if (trls.size() == 1)
		{
			return trls.get(0);
		}
		else
		{
			return new CompositeTranslatableString(trls, joiningString);
		}
	}

	public static ITranslatableString constant(final String value)
	{
		return ImmutableTranslatableString.constant(value);
	}

	public static ITranslatableString empty()
	{
		return ImmutableTranslatableString.empty();
	}

	public static ITranslatableString nullToEmpty(final ITranslatableString trl)
	{
		return trl != null ? trl : empty();
	}

	public String translate(final String adLanguage);

	public String getDefaultValue();

	public Set<String> getAD_Languages();

	default boolean isTranslatedTo(final String adLanguage)
	{
		return getAD_Languages().contains(adLanguage);
	}
}
