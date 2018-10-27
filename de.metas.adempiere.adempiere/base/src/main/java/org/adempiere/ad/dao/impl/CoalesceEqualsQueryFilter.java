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


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.Check;

/* package */ class CoalesceEqualsQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private final List<String> columnNames;
	private final Object value;

	public CoalesceEqualsQueryFilter(final Object value, final String... columnNames)
	{
		super();

		Check.assumeNotNull(columnNames, "columnNames not null");
		Check.assumeNotNull(columnNames.length > 1, "columnNames.length > 1");
		this.columnNames = Arrays.asList(columnNames);
		this.value = value;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		for (final String columnName : columnNames)
		{
			if (sb.length() > 0)
			{
				sb.append(",");
			}
			sb.append(columnName);
		}
		sb.insert(0, "COALESCE(").append(")");

		sb.append("==").append(value);

		return sb.toString();
	}

	@Override
	public boolean accept(final T model)
	{
		Object modelValue = null;

		//
		// Get first not null value
		for (final String columnName : columnNames)
		{
			if (InterfaceWrapperHelper.isNull(model, columnName))
			{
				continue;
			}

			modelValue = InterfaceWrapperHelper.getValue(model, columnName).orElse(null);
		}

		final boolean accepted = Objects.equals(modelValue, value);
		return accepted;
	}

	@Override
	public String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	public List<String> getColumnNames()
	{
		return columnNames;
	}

	public Object getValue()
	{
		return value;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		buildSql();
		return sqlParams;
	}

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	private final void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final StringBuilder sqlWhereClause = new StringBuilder();
		final List<Object> sqlParams;

		for (final String columnName : columnNames)
		{
			if (sqlWhereClause.length() > 0)
			{
				sqlWhereClause.append(",");
			}
			sqlWhereClause.append(columnName);
		}
		sqlWhereClause.insert(0, "COALESCE(").append(")");

		if (value == null)
		{
			sqlWhereClause.append(" IS NULL");
			sqlParams = Collections.emptyList();
		}
		else
		{
			sqlWhereClause.append("=?");
			sqlParams = Collections.singletonList(value);
		}

		this.sqlWhereClause = sqlWhereClause.toString();
		this.sqlParams = sqlParams;
		this.sqlBuilt = true;
	}

	protected void resetSqlBuilt()
	{
		this.sqlBuilt = false;
	}

}
