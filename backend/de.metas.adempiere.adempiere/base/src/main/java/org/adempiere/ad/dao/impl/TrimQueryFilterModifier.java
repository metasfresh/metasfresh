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

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilterModifier;

import javax.annotation.Nullable;
import java.util.List;

@EqualsAndHashCode
public final class TrimQueryFilterModifier implements IQueryFilterModifier
{
	public static final transient TrimQueryFilterModifier instance = new TrimQueryFilterModifier();

	private TrimQueryFilterModifier()
	{
	}

	@Override
	public @NonNull String getColumnSql(@NonNull String columnName)
	{
		final String columnSqlNew = "TRIM(" + columnName + ")";
		return columnSqlNew;
	}

	@Override
	public String getValueSql(Object value, List<Object> params)
	{
		final String valueSql;
		if (value instanceof ModelColumnNameValue<?>)
		{
			final ModelColumnNameValue<?> modelValue = (ModelColumnNameValue<?>)value;
			valueSql = modelValue.getColumnName();
		}
		else
		{
			valueSql = "?";
			params.add(value);
		}

		return "TRIM(" + valueSql + ")";
	}

	@Nullable
	@Override
	public Object convertValue(@Nullable final String columnName, @Nullable final Object value, final @Nullable Object model)
	{
		if (value == null)
		{
			return null;
		}

		final String str = (String)value;
		return str.trim();
	}

}
