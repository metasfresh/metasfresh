/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.dao.impl;

import java.util.List;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilterModifier;

import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

/**
 * Pass-through modifier (i.e. do nothing)
 * 
 * @author tsa
 * 
 */
@EqualsAndHashCode
public final class NullQueryFilterModifier implements IQueryFilterModifier
{
	public static final NullQueryFilterModifier instance = new NullQueryFilterModifier();

	/**
	 * 
	 * @param modifier
	 * @return true if given modifier is null
	 */
	public static boolean isNull(IQueryFilterModifier modifier)
	{
		return modifier == null || modifier == instance;
	}

	private NullQueryFilterModifier()
	{
	}

	@Override
	public String toString()
	{
		return "-";
	}

	@Override
	public @NonNull String getColumnSql(@NonNull String columnName)
	{
		return columnName;
	}

	/**
	 * @return Column Name if value is {@link ModelColumnNameValue} or "?" else.
	 */
	@Override
	public String getValueSql(Object value, List<Object> params)
	{
		if (value instanceof ModelColumnNameValue<?>)
		{
			final ModelColumnNameValue<?> modelValue = (ModelColumnNameValue<?>)value;
			return modelValue.getColumnName();
		}

		params.add(value);
		return "?";
	}

	@Nullable
	@Override
	public Object convertValue(@Nullable final String columnName, @Nullable Object value, final @Nullable Object model)
	{
		return value;
	}
}
