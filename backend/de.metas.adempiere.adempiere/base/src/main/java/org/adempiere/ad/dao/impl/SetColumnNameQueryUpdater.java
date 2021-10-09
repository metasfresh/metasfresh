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

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

/* package */class SetColumnNameQueryUpdater<T> implements ISqlQueryUpdater<T>
{
	private final String columnName;
	private final Object value;
	private final ModelColumnNameValue<?> valueColumn;

	public SetColumnNameQueryUpdater(@NonNull final String columnName, @Nullable final Object value)
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
			valueToSet = convertToPOValue(value);
		}

		return InterfaceWrapperHelper.setValue(model, columnName, valueToSet);
	}

	@Nullable
	private static Object convertToPOValue(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof RepoIdAware)
		{
			return ((RepoIdAware)value).getRepoId();
		}
		else if (TimeUtil.isDateOrTimeObject(value))
		{
			return TimeUtil.asTimestamp(value);
		}
		else
		{
			return value;
		}
	}

}
