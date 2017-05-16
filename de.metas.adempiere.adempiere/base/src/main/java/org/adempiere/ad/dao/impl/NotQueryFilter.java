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

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.util.Check;

public class NotQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private final IQueryFilter<T> filter;

	public NotQueryFilter(final IQueryFilter<T> filter)
	{
		super();
		Check.assumeNotNull(filter, "filter not null");

		this.filter = filter;
	}

	@Override
	public String toString()
	{
		return "NOT " + filter;
	}

	@Override
	public boolean accept(T model)
	{
		return !filter.accept(model);
	}

	@Override
	public String getSql()
	{
		final ISqlQueryFilter sqlFilter = ISqlQueryFilter.cast(filter);
		return "NOT (" + sqlFilter.getSql() + ")";
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		final ISqlQueryFilter sqlFilter = ISqlQueryFilter.cast(filter);
		return sqlFilter.getSqlParams(ctx);
	}
}
