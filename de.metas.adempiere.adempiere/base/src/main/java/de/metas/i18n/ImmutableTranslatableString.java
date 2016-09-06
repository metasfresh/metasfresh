package de.metas.i18n;

import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

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
 * Immutable {@link ITranslatableString} implementation.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ImmutableTranslatableString implements ITranslatableString
{
	public static final ImmutableTranslatableString ofMap(final Map<String, String> trlMap, final String defaultValue)
	{
		if ((trlMap == null || trlMap.isEmpty())
				&& (defaultValue == null || defaultValue.isEmpty()))
		{
			return EMPTY;
		}

		return new ImmutableTranslatableString(trlMap, defaultValue);
	}

	public static final ImmutableTranslatableString ofMap(final Map<String, String> trlMap)
	{
		if (trlMap == null || trlMap.isEmpty())
		{
			return EMPTY;
		}

		return new ImmutableTranslatableString(trlMap, EMPTY.defaultValue);
	}

	public static final ImmutableTranslatableString EMPTY = new ImmutableTranslatableString();

	private final Map<String, String> trlMap;
	private final String defaultValue;

	private ImmutableTranslatableString(final Map<String, String> trlMap, final String defaultValue)
	{
		super();
		this.trlMap = trlMap == null ? ImmutableMap.of() : ImmutableMap.copyOf(trlMap);
		this.defaultValue = defaultValue == null ? "" : defaultValue;
	}

	private ImmutableTranslatableString()
	{
		super();
		trlMap = ImmutableMap.of();
		defaultValue = "";
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("trls", trlMap)
				.add("default", defaultValue)
				.toString();
	}

	@Override
	public String translate(final String adLanguage)
	{
		return trlMap.getOrDefault(adLanguage, defaultValue);
	}
}
