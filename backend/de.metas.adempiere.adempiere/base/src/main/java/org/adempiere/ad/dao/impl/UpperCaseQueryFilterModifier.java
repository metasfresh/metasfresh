package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import org.adempiere.ad.dao.IQueryFilterModifier;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class UpperCaseQueryFilterModifier implements IQueryFilterModifier
{
	public static final transient UpperCaseQueryFilterModifier instance = new UpperCaseQueryFilterModifier();

	private UpperCaseQueryFilterModifier()
	{
	}

	@Override
	public String getColumnSql(String columnSql)
	{
		final String columnSqlNew = "UPPER(" + columnSql + ")";
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

		return "UPPER(" + valueSql + ")";
	}

	@Override
	public Object convertValue(final String columnName, final Object value, final Object model)
	{
		if (value == null)
		{
			return null;
		}

		final String str = (String)value;
		return str.toUpperCase();
	}

}
