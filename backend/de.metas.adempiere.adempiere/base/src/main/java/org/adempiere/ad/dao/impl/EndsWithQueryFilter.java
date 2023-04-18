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

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author cg
 *
 */
public class EndsWithQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private final String columnName;
	private final String endsWithString;

	public EndsWithQueryFilter(@NonNull final String columnName, @NonNull final String endsWithString)
	{
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
			return ((String)value).endsWith(endsWithString);

		}
		else
		{
			throw new IllegalArgumentException("Invalid '" + columnName + "' value for " + model);
		}
	}

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	private void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final String sqlWhereClause = columnName
				+ " LIKE "
				+ "'%'||? ";
		this.sqlParams = Collections.singletonList(endsWithString);

		this.sqlWhereClause = sqlWhereClause;
		this.sqlBuilt = true;
	}
}
