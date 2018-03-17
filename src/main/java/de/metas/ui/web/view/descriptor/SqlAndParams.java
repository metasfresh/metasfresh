package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
public final class SqlAndParams
{
	public static final SqlAndParams of(final String sql)
	{
		final Object[] sqlParams = null;
		return new SqlAndParams(sql, sqlParams);
	}

	public static final SqlAndParams of(final String sql, final List<Object> sqlParams)
	{
		return new SqlAndParams(sql, sqlParams != null && !sqlParams.isEmpty() ? sqlParams.toArray() : null);
	}

	public static final SqlAndParams of(final String sql, final Object... sqlParamsArray)
	{
		return new SqlAndParams(sql, sqlParamsArray);
	}

	public static SqlAndParams and(@NonNull final Collection<SqlAndParams> sqlAndParamsCollection)
	{
		Check.assumeNotEmpty(sqlAndParamsCollection, "sqlAndParamsCollection is not empty");
		if (sqlAndParamsCollection.size() == 1)
		{
			return sqlAndParamsCollection.iterator().next();
		}

		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sqlAndParamsCollection.forEach(sqlAndParams -> {
			if (sql.length() > 0)
			{
				sql.append(" AND ");
			}
			sql.append("(").append(sqlAndParams.getSql()).append(")");

			sqlParams.addAll(sqlAndParams.getSqlParams());
		});

		return SqlAndParams.of(sql.toString(), sqlParams);
	}

	private final String sql;
	private final List<Object> sqlParams;

	private SqlAndParams(@NonNull final String sql, @Nullable final Object[] sqlParamsArray)
	{
		this.sql = sql;
		this.sqlParams = sqlParamsArray != null && sqlParamsArray.length > 0 ? Arrays.asList(sqlParamsArray) : ImmutableList.of();
	}

	public Object[] getSqlParamsArray()
	{
		return sqlParams == null ? null : sqlParams.toArray();
	}

	public SqlAndParams append(@NonNull final String sql, final Object... sqlParams)
	{
		final String sqlNew = this.sql + sql;

		final List<Object> sqlParamsNew;
		if (sqlParams != null && sqlParams.length > 0)
		{
			if (this.sqlParams.size() > 0)
			{
				sqlParamsNew = new ArrayList<>(this.sqlParams.size() + sqlParams.length);
				sqlParamsNew.addAll(this.sqlParams);
				sqlParamsNew.addAll(Arrays.asList(sqlParams));
			}
			else
			{
				sqlParamsNew = Arrays.asList(sqlParams);
			}
		}
		else
		{
			sqlParamsNew = this.sqlParams;
		}

		return new SqlAndParams(sqlNew, sqlParamsNew.toArray());
	}

	public SqlAndParams append(@NonNull final SqlAndParams other)
	{
		return append(other.getSql(), other.getSqlParamsArray());
	}
}
