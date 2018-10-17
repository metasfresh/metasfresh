package de.metas.i18n.impl;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

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

public final class NullModelTranslationMap implements IModelTranslationMap
{
	public static final transient IModelTranslationMap instance = new NullModelTranslationMap();

	private NullModelTranslationMap()
	{
		super();
	}

	@Override
	public IModelTranslation getTranslation(final String adLanguage)
	{
		return NullModelTranslation.instance;
	}

	@Override
	public Map<String, IModelTranslation> getAllTranslations()
	{
		return ImmutableMap.of();
	}

	@Override
	public ITranslatableString getColumnTrl(final String columnName, final String defaultValue)
	{
		return ImmutableTranslatableString.constant(defaultValue);
	}

	@Override
	public Optional<String> translateColumn(final String columnName, final String adLanguage)
	{
		return Optional.empty();
	}
}
