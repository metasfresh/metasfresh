package org.adempiere.ad.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

/**
 * Immutable SQL query filter.
 *
 * @author tsa
 *
 * @param <T> model class
 */
@Immutable
public final class TypedSqlQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	public static final <T> TypedSqlQueryFilter<T> of(final String sql)
	{
		final List<Object> params = ImmutableList.of();
		return new TypedSqlQueryFilter<>(sql, params);
	}

	public static final <T> TypedSqlQueryFilter<T> of(final String sql, final List<Object> sqlParams)
	{
		return new TypedSqlQueryFilter<>(sql, sqlParams);
	}

	public static final <T> TypedSqlQueryFilter<T> of(final String sql, final Object[] sqlParams)
	{
		return new TypedSqlQueryFilter<>(sql, sqlParams);
	}

	private final String sql;
	private final List<Object> sqlParams;

	private TypedSqlQueryFilter(final String sql, final Object[] sqlParams)
	{
		this(sql, sqlParams == null ? null : Arrays.asList(sqlParams));
	}

	private TypedSqlQueryFilter(final String sql, final List<Object> sqlParams)
	{
		Check.assumeNotEmpty(sql, "sql not empty");
		this.sql = sql;
		
		if (sqlParams == null || sqlParams.isEmpty())
		{
			this.sqlParams = Collections.emptyList();
		}
		else
		{
			// Take an immutable copy of given sqlParams
			// NOTE: we cannot use ImmutableList because it might be that some parameters are null
			this.sqlParams = Collections.unmodifiableList(new ArrayList<>(sqlParams));
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(sql)
				.add("params", sqlParams)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(sql, sqlParams);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof TypedSqlQueryFilter)
		{
			final TypedSqlQueryFilter<?> other = (TypedSqlQueryFilter<?>)obj;
			return Objects.equals(sql, other.sql)
					&& Objects.equals(sqlParams, other.sqlParams);
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getSql()
	{
		return sql;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx_NOTUSED)
	{
		return sqlParams;
	}

	@Override
	public boolean accept(final T model)
	{
		throw new UnsupportedOperationException();
	}
}
