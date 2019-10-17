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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;
import lombok.NonNull;

/* package */class CompositeQueryUpdater<T> implements ICompositeQueryUpdater<T>
{
	private final List<IQueryUpdater<T>> queryUpdaters = new ArrayList<>();

	private String sql = null;
	private List<Object> sqlParams = null;
	private boolean sqlBuilt = false;

	public CompositeQueryUpdater()
	{
	}

	@Override
	public ICompositeQueryUpdater<T> addQueryUpdater(@NonNull final IQueryUpdater<T> updater)
	{
		queryUpdaters.add(updater);

		sqlBuilt = false;

		return this;
	}

	@Override
	public ICompositeQueryUpdater<T> addSetColumnValue(final String columnName, final Object value)
	{
		final IQueryUpdater<T> updater = new SetColumnNameQueryUpdater<T>(columnName, value);
		return addQueryUpdater(updater);
	}

	@Override
	public ICompositeQueryUpdater<T> addSetColumnFromColumn(final String columnName, final ModelColumnNameValue<T> fromColumnName)
	{
		final IQueryUpdater<T> updater = new SetColumnNameQueryUpdater<T>(columnName, fromColumnName);
		return addQueryUpdater(updater);
	}

	@Override
	public ICompositeQueryUpdater<T> addAddValueToColumn(final String columnName, final BigDecimal valueToAdd)
	{
		final IQueryFilter<T> onlyWhenFilter = null;
		final IQueryUpdater<T> updater = new AddToColumnQueryUpdater<T>(columnName, valueToAdd, onlyWhenFilter);
		return addQueryUpdater(updater);
	}

	@Override
	public ICompositeQueryUpdater<T> addAddValueToColumn(final String columnName, final BigDecimal valueToAdd, final IQueryFilter<T> onlyWhenFilter)
	{
		final IQueryUpdater<T> updater = new AddToColumnQueryUpdater<T>(columnName, valueToAdd, onlyWhenFilter);
		return addQueryUpdater(updater);
	}

	@Override
	public boolean update(final T model)
	{
		boolean updated = false;
		for (final IQueryUpdater<T> updater : queryUpdaters)
		{
			if (updater.update(model))
			{
				updated = true;
			}
		}
		return updated;
	}

	@Override
	public String getSql(final Properties ctx, final List<Object> params)
	{
		buildSql(ctx);

		params.addAll(sqlParams);
		return sql;
	}

	private final void buildSql(final Properties ctx)
	{
		if (sqlBuilt)
		{
			return;
		}

		if (queryUpdaters.isEmpty())
		{
			throw new AdempiereException("Cannot build sql update query for an empty " + CompositeQueryUpdater.class);
		}

		final StringBuilder sql = new StringBuilder();
		final List<Object> params = new ArrayList<Object>();

		for (final IQueryUpdater<T> updater : queryUpdaters)
		{
			final ISqlQueryUpdater<T> sqlUpdater = (ISqlQueryUpdater<T>)updater;
			final String sqlChunk = sqlUpdater.getSql(ctx, params);

			if (Check.isEmpty(sqlChunk))
			{
				continue;
			}

			if (sql.length() > 0)
			{
				sql.append(", ");
			}
			sql.append(sqlChunk);
		}

		this.sql = sql.toString();
		this.sqlParams = params;
		this.sqlBuilt = true;
	}
}
