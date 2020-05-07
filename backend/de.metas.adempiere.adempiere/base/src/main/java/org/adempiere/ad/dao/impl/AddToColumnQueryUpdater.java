package org.adempiere.ad.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

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

class AddToColumnQueryUpdater<T> implements ISqlQueryUpdater<T>
{
	private final String columnName;
	private final BigDecimal value;
	private final IQueryFilter<T> onlyWhenFilter;

	public AddToColumnQueryUpdater(final String columnName, final BigDecimal value, final IQueryFilter<T> onlyWhenFilter)
	{
		super();
		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;

		Check.assumeNotNull(value, "value not null");
		this.value = value;
		
		this.onlyWhenFilter = onlyWhenFilter;
	}

	@Override
	public String getSql(final Properties ctx, final List<Object> params)
	{
		final StringBuilder sql = new StringBuilder();
		final StringBuilder sqlEnding = new StringBuilder();
		
		sql.append(columnName).append("=");
		
		if (onlyWhenFilter == null)
		{
			// nothing
		}
		else if (onlyWhenFilter instanceof ISqlQueryFilter)
		{
			final ISqlQueryFilter onlyWhenSqlFilter = ISqlQueryFilter.cast(onlyWhenFilter);
			
			sql.append("(CASE WHEN ").append(onlyWhenSqlFilter.getSql()).append(" THEN ");
			params.addAll(onlyWhenSqlFilter.getSqlParams(ctx));
			//
			sqlEnding.append(" ELSE ").append(columnName).append(" END)");
		}
		else
		{
			throw new DBException("Cannot convert filter to SQL: "+onlyWhenFilter);
		}

		sql.append(columnName).append(" + ?").append(sqlEnding);
		params.add(value);

		return sql.toString();
	}

	@Override
	public boolean update(final T model)
	{
		if (onlyWhenFilter != null && !onlyWhenFilter.accept(model))
		{
			return MODEL_SKIPPED; // not updated
		}
		
		BigDecimal valueOld = InterfaceWrapperHelper.getValueOrNull(model, columnName);
		if(valueOld == null)
		{
			valueOld = BigDecimal.ZERO;
		}
		
		final BigDecimal valueNew = valueOld.add(value);

		final boolean updated = InterfaceWrapperHelper.setValue(model, columnName, valueNew);
		return updated;
	}

}
