package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
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
	public static Builder builder()
	{
		return new Builder();
	}

	public static SqlAndParams of(final String sql)
	{
		final Object[] sqlParams = null;
		return new SqlAndParams(sql, sqlParams);
	}

	public static SqlAndParams of(final CharSequence sql, final List<Object> sqlParams)
	{
		final Object[] sqlParamsArray = sqlParams != null && !sqlParams.isEmpty() ? sqlParams.toArray() : null;
		return new SqlAndParams(sql, sqlParamsArray);
	}

	public static final SqlAndParams of(final CharSequence sql, final Object... sqlParamsArray)
	{
		return new SqlAndParams(sql, sqlParamsArray);
	}

	public static SqlAndParams and(@NonNull final Collection<SqlAndParams> sqlAndParamsCollection)
	{
		return andNullables(sqlAndParamsCollection)
				.orElseThrow(() -> new AdempiereException("No non null SQLs found in " + sqlAndParamsCollection));
	}

	public static Optional<SqlAndParams> andNullables(final SqlAndParams... sqlAndParamsCollection)
	{
		if (sqlAndParamsCollection == null || sqlAndParamsCollection.length == 0)
		{
			return Optional.empty();
		}

		return andNullables(Arrays.asList(sqlAndParamsCollection));
	}

	public static Optional<SqlAndParams> andNullables(final Collection<SqlAndParams> sqlAndParamsCollection)
	{
		if (sqlAndParamsCollection == null || sqlAndParamsCollection.isEmpty())
		{
			return Optional.empty();
		}

		int countNotNulls = 0;
		SqlAndParams firstNotNull = null;
		Builder builder = null;
		for (final SqlAndParams sqlAndParams : sqlAndParamsCollection)
		{
			if (sqlAndParams == null || sqlAndParams.isEmpty())
			{
				continue;
			}

			if (countNotNulls == 0)
			{
				firstNotNull = sqlAndParams;
			}
			else
			{
				if (builder == null)
				{
					builder = builder();
					builder.append("(").append(firstNotNull).append(")");
				}

				if (!builder.isEmpty())
				{
					builder.append(" AND ");
				}
				builder.append("(").append(sqlAndParams).append(")");
			}

			countNotNulls++;
		}

		if (builder != null)
		{
			return Optional.of(builder.build());
		}
		else if (firstNotNull != null)
		{
			return Optional.of(firstNotNull);
		}
		else
		{
			return Optional.empty();
		}
	}

	private final String sql;
	private final List<Object> sqlParams;

	private SqlAndParams(@NonNull final CharSequence sql, @Nullable final Object[] sqlParamsArray)
	{
		this.sql = sql.toString();
		this.sqlParams = sqlParamsArray != null && sqlParamsArray.length > 0 ? Arrays.asList(sqlParamsArray) : ImmutableList.of();
	}

	public Builder toBuilder()
	{
		return builder().append(this);
	}

	public Object[] getSqlParamsArray()
	{
		return sqlParams == null ? null : sqlParams.toArray();
	}

	public boolean hasParams()
	{
		return sqlParams != null && !sqlParams.isEmpty();
	}

	public boolean isEmpty()
	{
		return Check.isBlank(sql) && !hasParams();
	}

	//
	//
	// ---------------
	//
	//

	public static class Builder
	{
		private StringBuilder sql = null;
		private ArrayList<Object> sqlParams = null;

		private Builder()
		{
		}

		/** @deprecated I think you wanted to call {@link #build()} */
		@Deprecated
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("sql", sql)
					.add("sqlParams", sqlParams)
					.toString();
		}

		public SqlAndParams build()
		{
			final String sql = this.sql != null ? this.sql.toString() : "";
			final Object[] sqlParamsArray = sqlParams != null ? sqlParams.toArray() : null;
			return new SqlAndParams(sql, sqlParamsArray);
		}

		public boolean isEmpty()
		{
			return (sql == null || sql.length() == 0)
					&& !hasParameters();
		}

		public boolean hasParameters()
		{
			return sqlParams != null && !sqlParams.isEmpty();
		}

		public int getParametersCount()
		{
			return sqlParams != null ? sqlParams.size() : 0;
		}

		public Builder appendIfHasParameters(@NonNull final CharSequence sql)
		{
			if (hasParameters())
			{
				return append(sql);
			}
			else
			{
				return this;
			}
		}

		public Builder append(@NonNull final CharSequence sql, final Object... sqlParams)
		{
			final List<Object> sqlParamsList = sqlParams != null && sqlParams.length > 0 ? Arrays.asList(sqlParams) : null;
			return append(sql, sqlParamsList);
		}

		public Builder append(@NonNull final CharSequence sql, final List<Object> sqlParams)
		{
			if (sql.length() > 0)
			{
				if (this.sql == null)
				{
					this.sql = new StringBuilder();
				}
				this.sql.append(sql);
			}

			if (sqlParams != null && !sqlParams.isEmpty())
			{
				if (this.sqlParams == null)
				{
					this.sqlParams = new ArrayList<>();
				}

				this.sqlParams.addAll(sqlParams);
			}

			return this;
		}

		public Builder append(@NonNull final SqlAndParams other)
		{
			return append(other.sql, other.sqlParams);
		}
	}
}
