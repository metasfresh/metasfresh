package de.metas.i18n;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

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

/*package*/final class ConstantTranslatableString implements ITranslatableString
{
	public static final ConstantTranslatableString of(final String value)
	{
		if(value == null || value.isEmpty())
		{
			return EMPTY;
		}
		return new ConstantTranslatableString(value);
	}
	
	public static final ConstantTranslatableString EMPTY = new ConstantTranslatableString("");

	private final String value;

	private ConstantTranslatableString(final String value)
	{
		super();
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value;
	}

	@Override
	public String translate(final String adLanguage)
	{
		return value;
	}

	@Override
	public String getDefaultValue()
	{
		return value;
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return ImmutableSet.of();
	}

}
