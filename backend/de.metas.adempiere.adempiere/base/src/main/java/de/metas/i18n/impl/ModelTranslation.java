package de.metas.i18n.impl;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IModelTranslation;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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

@ToString
@EqualsAndHashCode
public final class ModelTranslation implements IModelTranslation
{
	public static IModelTranslation of(final String adLanguage, final Map<String, String> trlMap)
	{
		return new ModelTranslation(adLanguage, trlMap);
	}

	private final String adLanguage;
	private final ImmutableMap<String, String> trlMap;

	public ModelTranslation(final String adLanguage, final Map<String, String> trlMap)
	{
		this.adLanguage = adLanguage;
		if (trlMap == null)
		{
			this.trlMap = ImmutableMap.of();
		}
		else
		{
			this.trlMap = ImmutableMap.copyOf(trlMap);
		}
	}

	@Override
	public String getAD_Language()
	{
		return adLanguage;
	}

	@Override
	public boolean isTranslated(final String columnName)
	{
		return trlMap.containsKey(columnName);
	}

	@Override
	public String getTranslation(final String columnName)
	{
		return trlMap.get(columnName);
	}
}
