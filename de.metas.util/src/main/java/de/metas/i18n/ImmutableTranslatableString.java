package de.metas.i18n;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
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
	public static final ITranslatableString ofMap(final Map<String, String> trlMap, final String defaultValue)
	{
		if ((trlMap == null || trlMap.isEmpty())
				&& (defaultValue == null || defaultValue.isEmpty()))
		{
			return ConstantTranslatableString.EMPTY;
		}

		return new ImmutableTranslatableString(trlMap, defaultValue);
	}

	public static final ITranslatableString ofMap(final Map<String, String> trlMap)
	{
		if (trlMap == null || trlMap.isEmpty())
		{
			return ConstantTranslatableString.EMPTY;
		}

		return new ImmutableTranslatableString(trlMap, ConstantTranslatableString.EMPTY.getDefaultValue());
	}

	public static final ITranslatableString constant(final String value)
	{
		return ConstantTranslatableString.of(value);
	}
	
	public static final ITranslatableString empty()
	{
		return ConstantTranslatableString.EMPTY;
	}

	public static final Builder builder()
	{
		return new Builder();
	}

	public static final ITranslatableString copyOf(final ITranslatableString trl)
	{
		Preconditions.checkNotNull(trl, "trl");
		return copyOfNullable(trl);
	}

	/**
	 *
	 * @param trl
	 * @return {@link ImmutableTranslatableString} or {@link #EMPTY} if <code>trl</code> was null
	 */
	public static final ITranslatableString copyOfNullable(@Nullable final ITranslatableString trl)
	{
		if (trl == null)
		{
			return ConstantTranslatableString.EMPTY;
		}

		if (trl instanceof ConstantTranslatableString)
		{
			return trl;
		}
		if (trl instanceof ImmutableTranslatableString)
		{
			return trl;
		}

		final Set<String> adLanguages = trl.getAD_Languages();
		final Map<String, String> trlMap = new LinkedHashMap<>(adLanguages.size());
		for (final String adLanguage : adLanguages)
		{
			final String trlString = trl.translate(adLanguage);
			if (trlString == null)
			{
				continue;
			}

			trlMap.put(adLanguage, trlString);
		}

		return ofMap(trlMap, trl.getDefaultValue());
	}

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

	private ImmutableTranslatableString(final Builder builder)
	{
		super();
		trlMap = ImmutableMap.copyOf(builder.trlMap);
		defaultValue = builder.defaultValue == null ? "" : builder.defaultValue;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("trlString")
				.omitNullValues()
				.add("trls", trlMap.isEmpty() ? null : trlMap)
				.add("default", defaultValue)
				.toString();
	}

	@Override
	public String translate(final String adLanguage)
	{
		return trlMap.getOrDefault(adLanguage, defaultValue);
	}

	@Override
	public String getDefaultValue()
	{
		return defaultValue;
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return trlMap.keySet();
	}

	public static final class Builder
	{
		private final Map<String, String> trlMap = new HashMap<>();
		private String defaultValue;

		private Builder()
		{
			super();
		}

		public ImmutableTranslatableString build()
		{
			return new ImmutableTranslatableString(this);
		}

		public Builder put(final String adLanguage, final String trlString)
		{
			Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
			Check.assumeNotNull(trlString, "trlString is not empty");

			trlMap.put(adLanguage, trlString);

			return this;
		}

		public Builder setDefaultValue(final String defaultValue)
		{
			this.defaultValue = defaultValue;
			return this;
		}
	}
}
