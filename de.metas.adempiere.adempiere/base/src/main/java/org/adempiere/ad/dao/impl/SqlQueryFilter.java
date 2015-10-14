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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.util.Check;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

/**
 * Immutable {@link ISqlQueryFilter} implementation
 * 
 * @author tsa
 * 
 */
public class SqlQueryFilter implements ISqlQueryFilter
{
	private final String sql;
	private final List<Object> params;

	public SqlQueryFilter(final String sql)
	{
		this(sql, (List<Object>)null);
	}

	public SqlQueryFilter(final String sql, final Object[] params)
	{
		this(sql, params == null ? null : Arrays.asList(params));
	}

	public SqlQueryFilter(final String sql, final List<Object> params)
	{
		super();

		Check.assumeNotEmpty(sql, "sql not empty");

		this.sql = sql;
		if (params == null || params.isEmpty())
		{
			this.params = Collections.emptyList();
		}
		else
		{
			this.params = Collections.unmodifiableList(new ArrayList<Object>(params));
		}
	}

	@Override
	public String toString()
	{
		return "SqlQueryFilter["
				+ sql
				+ ", params=" + params
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(sql)
				.append(params)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final SqlQueryFilter other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(sql, other.sql)
				.append(params, other.params)
				.isEqual();
	}

	@Override
	public String getSql()
	{
		return sql;
	}

	@Override
	public List<Object> getSqlParams(Properties ctx)
	{
		return params;
	}
}
