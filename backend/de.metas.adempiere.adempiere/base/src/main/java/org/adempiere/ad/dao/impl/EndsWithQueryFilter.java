/**
 * 
 */
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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.Check;

/**
 * @author cg
 *
 */
/* package */ class EndsWithQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{

	private String columnName;
	private String endsWithString;

	public EndsWithQueryFilter(final String columnName, final String endsWithString)
	{
		super();

		Check.assumeNotNull(columnName, "columnName not null");
		Check.assumeNotNull(endsWithString, "endsWithString not null");
		this.columnName = columnName;
		this.endsWithString = endsWithString;
	}

	@Override
	public String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public List<Object> getSqlParams(Properties ctx)
	{
		buildSql();
		return sqlParams;
	}

	@Override
	public boolean accept(T model)
	{
		final Object value = InterfaceWrapperHelper.getValueOrNull(model, columnName);
		if (value == null)
		{
			return false;
		}
		else  if (value instanceof String)
		{
			if (((String)value).endsWith(endsWithString))
			{
				return true;
			}
			
			return false;
			
		}
		else
		{
			throw new IllegalArgumentException("Invalid '" + columnName + "' value for " + model);
		}
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

		sqlWhereClause.append(columnName);

		sqlWhereClause.append(" LIKE ");

		sqlWhereClause.append("'%'||? ");
		this.sqlParams = Arrays.asList((Object)endsWithString);

		this.sqlWhereClause = sqlWhereClause.toString();
		this.sqlBuilt = true;
	}

	protected void resetSqlBuilt()
	{
		this.sqlBuilt = false;
	}

}
