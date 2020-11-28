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

import org.adempiere.ad.dao.ICompositeQueryUpdaterExecutor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryUpdater;
import org.compiere.model.IQuery;

import lombok.NonNull;

/* package */class CompositeQueryUpdaterExecutor<T>
		extends CompositeQueryUpdater<T>
		implements ICompositeQueryUpdaterExecutor<T>
{
	private final IQuery<T> query;
	private boolean executeDirectly = true;

	public CompositeQueryUpdaterExecutor(@NonNull final IQuery<T> query)
	{
		this.query = query;
	}

	@Override
	public int execute()
	{
		if (executeDirectly)
		{
			return query.updateDirectly(this);
		}
		else
		{
			return query.update(this);
		}
	}

	@Override
	public ICompositeQueryUpdaterExecutor<T> setExecuteDirectly(final boolean executeDirectly)
	{
		this.executeDirectly = executeDirectly;
		return this;
	}

	@Override
	public ICompositeQueryUpdaterExecutor<T> addQueryUpdater(final IQueryUpdater<T> updater)
	{
		super.addQueryUpdater(updater);
		return this;
	}

	@Override
	public ICompositeQueryUpdaterExecutor<T> addSetColumnValue(final String columnName, final Object value)
	{
		super.addSetColumnValue(columnName, value);
		return this;
	}

	@Override
	public ICompositeQueryUpdaterExecutor<T> addSetColumnFromColumn(final String columnName, final ModelColumnNameValue<T> fromColumnName)
	{
		super.addSetColumnFromColumn(columnName, fromColumnName);
		return this;
	}

	@Override
	public ICompositeQueryUpdaterExecutor<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd)
	{
		super.addAddValueToColumn(columnName, valueToAdd);
		return this;
	}

	@Override
	public ICompositeQueryUpdaterExecutor<T> addAddValueToColumn(String columnName, BigDecimal valueToAdd, IQueryFilter<T> onlyWhenFilter)
	{
		super.addAddValueToColumn(columnName, valueToAdd, onlyWhenFilter);
		return this;
	}
}
