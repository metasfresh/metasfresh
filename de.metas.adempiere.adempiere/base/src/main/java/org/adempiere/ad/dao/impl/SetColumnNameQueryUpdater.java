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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

/*package*/class SetColumnNameQueryUpdater<T> implements ISqlQueryUpdater<T>
{
	private final String columnName;
	private final Object value;
	private final ModelColumnNameValue<?> valueColumn;

	public SetColumnNameQueryUpdater(final String columnName, final Object value)
	{
		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;

		if (value instanceof ModelColumnNameValue<?>)
		{
			this.valueColumn = (ModelColumnNameValue<?>)value;
			this.value = null;
		}
		else
		{
			this.valueColumn = null;
			this.value = value;
		}
	}

	@Override
	public String getSql(final Properties ctx, final List<Object> params)
	{
		final StringBuilder sql = new StringBuilder();

		if (valueColumn != null)
		{
			sql.append(columnName).append("=").append(valueColumn.getColumnName());
		}
		else
		{
			sql.append(columnName).append("=?");
			params.add(value);
		}

		return sql.toString();
	}

	@Override
	public boolean update(final T model)
	{
		final Object valueToSet;
		if (valueColumn != null)
		{
			valueToSet = InterfaceWrapperHelper.getValueOrNull(model, valueColumn.getColumnName());
		}
		else
		{
			valueToSet = value;
		}

		final boolean updated = InterfaceWrapperHelper.setValue(model, columnName, valueToSet);
		return updated;
	}

}
