package de.metas.i18n.impl;

import de.metas.i18n.IModelTranslation;

import javax.annotation.Nullable;

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

public final class NullModelTranslation implements IModelTranslation
{
	public static final transient NullModelTranslation instance = new NullModelTranslation();

	public static boolean isNull(@Nullable final IModelTranslation trl)
	{
		return trl == null || trl == instance;
	}

	private NullModelTranslation()
	{
		super();
	}
	
	@Override
	public boolean isTranslated(final String columnName)
	{
		return false;
	}

	@Override
	@Nullable
	public String getTranslation(final String columnName)
	{
		return null;
	}

	@Override
	public String getAD_Language()
	{
		throw new UnsupportedOperationException("" + NullModelTranslation.class + " does not have a language");
	}
}
