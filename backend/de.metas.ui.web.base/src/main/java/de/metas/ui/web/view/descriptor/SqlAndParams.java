package de.metas.ui.web.view.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.dao.sql.SqlParamsInliner;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
public class SqlAndParams
{
	public static SqlAndParams EMPTY = builder().build();

	public static Builder builder()
	{
		return new Builder();
	}

	public static SqlAndParams of(@NonNull final String sql)
	{
		return new SqlAndParams(sql, ImmutableList.of());
	}

	public static SqlAndParams of(@NonNull final CharSequence sql, @Nullable final List<Object> sqlParams)
	{
		final Object[] sqlParamsArray = sqlParams != null && !sqlParams.isEmpty() ? sqlParams.toArray() : null;
		return new SqlAndParams(sql, sqlParamsArray);
	}

	public static SqlAndParams of(@NonNull final CharSequence sql, @Nullable final SqlParamsCollector sqlParams)
	{
		return of(sql, sqlParams != null ? sqlParams.toLiveList() : null);
	}

	public static SqlAndParams of(final CharSequence sql, @Nullable final Object... sqlParamsArray)
	{
		return new SqlAndParams(sql, sqlParamsArray);
	}

	public static SqlAndParams and(@NonNull final Collection<SqlAndParams> sqlAndParamsCollection)
	{
		return andNullables(sqlAndParamsCollection)
				.orElseThrow(() -> new AdempiereException("No non null SQLs found in " + sqlAndParamsCollection));
	}

	public static Optional<SqlAndParams> andNullables(final SqlAndParams... sqlAndParamsArray)
	{
		if (sqlAndParamsArray == null || sqlAndParamsArray.length == 0)
		{
			return Optional.empty();
		}

		return andNullables(Arrays.asList(sqlAndParamsArray));
	}

	public static Optional<SqlAndParams> andNullables(final Collection<SqlAndParams> sqlAndParamsCollection)
	{
		return joinNullables("AND", sqlAndParamsCollection);
	}

	public static Optional<SqlAndParams> orNullables(@Nullable final SqlAndParams... sqlAndParamsArray)
	{
		if (sqlAndParamsArray == null || sqlAndParamsArray.length == 0)
		{
			return Optional.empty();
		}

		return orNullables(Arrays.asList(sqlAndParamsArray));
	}

	public static Optional<SqlAndParams> orNullables(@Nullable final Collection<SqlAndParams> sqlAndParamsCollection)
	{
		return joinNullables("OR", sqlAndParamsCollection);
	}

	private static Optional<SqlAndParams> joinNullables(@NonNull final String operator, @Nullable final Collection<SqlAndParams> sqlAndParamsCollection)
	{
		final String operatorNorm = StringUtils.trimBlankToNull(operator);
		if (operatorNorm == null)
		{
			throw new AdempiereException("Invalid blank operator: `" + operator + "`");
		}

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
					builder.append(" ").append(operatorNorm).append(" ");
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

	@Nullable
	public static SqlAndParams emptyToNull(@Nullable final SqlAndParams sqlAndParams)
	{
		return sqlAndParams != null && !sqlAndParams.isEmpty() ? sqlAndParams : null;
	}

	private static final SqlParamsInliner sqlParamsInliner = SqlParamsInliner.builder().failOnError(true).build();

	String sql;
	List<Object> sqlParams;

	private SqlAndParams(@NonNull final CharSequence sql, @Nullable final Object[] sqlParamsArray)
	{
		this(
				sql.toString(),
				sqlParamsArray != null && sqlParamsArray.length > 0 ? Collections.unmodifiableList(Arrays.asList(sqlParamsArray)) : ImmutableList.of());
	}

	private SqlAndParams(@NonNull final String sql, @NonNull final List<Object> sqlParams)
	{
		this.sql = sql;
		this.sqlParams = sqlParams;
	}

	public Builder toBuilder()
	{
		return builder().append(this);
	}

	public static boolean equals(@Nullable final SqlAndParams o1, @Nullable final SqlAndParams o2) {return Objects.equals(o1, o2);}

	@Nullable
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

	public String toSqlStringInlineParams()
	{
		return sqlParamsInliner.inline(sql, sqlParams);
	}

	public <T> IQueryFilter<T> toQueryFilterOrAllowAll()
	{
		return !isEmpty()
				? TypedSqlQueryFilter.of(sql, sqlParams)
				: ConstantQueryFilter.of(true);
	}

	public SqlAndParams negate()
	{
		if (sql.isEmpty())
		{
			return this;
		}

		return new SqlAndParams("NOT (" + this.sql + ")", this.sqlParams);
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

		/**
		 * @deprecated I think you wanted to call {@link #build()}
		 */
		@Override
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

		public Builder clear()
		{
			sql = null;
			sqlParams = null;
			return this;
		}

		public boolean isEmpty()
		{
			return length() <= 0 && !hasParameters();
		}

		public int length()
		{
			return sql != null ? sql.length() : 0;
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

		public Builder appendIfNotEmpty(@NonNull final CharSequence sql, @Nullable final Object... sqlParams)
		{
			if (!isEmpty())
			{
				append(sql, sqlParams);
			}
			return this;
		}

		public Builder append(@NonNull final CharSequence sql, @Nullable final Object... sqlParams)
		{
			final List<Object> sqlParamsList = sqlParams != null && sqlParams.length > 0 ? Arrays.asList(sqlParams) : null;
			return append(sql, sqlParamsList);
		}

		public Builder append(@NonNull final CharSequence sql, @Nullable final List<Object> sqlParams)
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

		public Builder append(@NonNull final SqlAndParams.Builder other)
		{
			return append(other.sql, other.sqlParams);
		}
	}
}
