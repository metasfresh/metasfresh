package de.metas.ui.web.view.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
	public static SqlAndParams ACCEPT_ALL = new SqlAndParams(ConstantQueryFilter.of(true).getSql(), null);
	public static SqlAndParams ACCEPT_NONE = new SqlAndParams(ConstantQueryFilter.of(false).getSql(), null);

	public static Builder builder()
	{
		return new Builder();
	}

	public static SqlAndParams of(@NonNull final String sql)
	{
		return new SqlAndParams(sql, null);
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

	public static Optional<SqlAndParams> andNullables(@Nullable final SqlAndParams... sqlAndParamsCollection)
	{
		return sqlAndParamsCollection != null && sqlAndParamsCollection.length > 0
				? andNullables(Arrays.asList(sqlAndParamsCollection))
				: Optional.empty();
	}

	public static Optional<SqlAndParams> andNullables(@Nullable final Iterable<SqlAndParams> sqlAndParamsIterable)
	{
		return joinNullables("AND", sqlAndParamsIterable);
	}

	public static Optional<SqlAndParams> orNullables(@Nullable final SqlAndParams... sqlAndParamsCollection)
	{
		return sqlAndParamsCollection != null && sqlAndParamsCollection.length > 0
				? orNullables(Arrays.asList(sqlAndParamsCollection))
				: Optional.empty();
	}

	public static Optional<SqlAndParams> orNullables(@Nullable final Iterable<SqlAndParams> sqlAndParamsIterable)
	{
		return joinNullables("OR", sqlAndParamsIterable);
	}

	private static Optional<SqlAndParams> joinNullables(
			@NonNull final String joinKeyword,
			@Nullable final Iterable<SqlAndParams> sqlAndParamsIterable)
	{
		if (sqlAndParamsIterable == null)
		{
			return Optional.empty();
		}
		if (sqlAndParamsIterable instanceof Collection && ((Collection<?>)sqlAndParamsIterable).isEmpty())
		{
			return Optional.empty();
		}

		int countNotNulls = 0;
		SqlAndParams firstNotNull = null;
		Builder builder = null;
		for (final SqlAndParams sqlAndParams : sqlAndParamsIterable)
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
					builder.appendWithBracketsIfRequiredWhenJoining(firstNotNull);
				}

				if (!builder.isEmpty())
				{
					builder.append(" ").append(joinKeyword).append(" ");
				}

				builder.appendWithBracketsIfRequiredWhenJoining(sqlAndParams);
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

	private boolean isBracketsRequiredWhenJoining()
	{
		return !isAcceptAll() && !isAcceptNone();
	}

	String sql;
	List<Object> sqlParams;

	private SqlAndParams(@NonNull final CharSequence sql, @Nullable final Object[] sqlParamsArray)
	{
		// IMPORTANT: do not trim the sql. Let it as it is.
		this.sql = sql.toString();
		this.sqlParams = sqlParamsArray != null && sqlParamsArray.length > 0 ? Arrays.asList(sqlParamsArray) : ImmutableList.of();
	}

	public Builder toBuilder()
	{
		return builder().append(this);
	}

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

	public SqlAndParams and(@NonNull final SqlAndParams other)
	{
		if (this.isEmpty())
		{
			return other;
		}
		else if (other.isEmpty())
		{
			return this;
		}
		else
		{
			return andNullables(this, other)
					.orElseThrow(() -> new AdempiereException("Cannot AND join: " + this + " and " + other)); // shall not happen
		}
	}

	public SqlAndParams or(@NonNull final SqlAndParams other)
	{
		if (this.isEmpty())
		{
			return other;
		}
		else if (other.isEmpty())
		{
			return this;
		}
		else
		{
			return orNullables(this, other)
					.orElseThrow(() -> new AdempiereException("Cannot OR join: " + this + " and " + other)); // shall not happen
		}
	}

	public SqlAndParams andNot(@NonNull final SqlAndParams other)
	{
		return builder()
				.appendWithBracketsIfRequiredWhenJoining(this)
				.append(" AND NOT ")
				.appendWithBracketsIfRequiredWhenJoining(other)
				.build();
	}

	public SqlAndParams not()
	{
		return SqlAndParams.builder()
				.append("NOT ")
				.appendWithBracketsIfRequiredWhenJoining(this)
				.build();
	}

	public boolean isAcceptAll() { return this.equals(ACCEPT_ALL); }

	public boolean isAcceptNone() { return this.equals(ACCEPT_NONE); }

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

		private Builder appendWithBracketsIfRequiredWhenJoining(@NonNull final SqlAndParams other)
		{
			return other.isBracketsRequiredWhenJoining()
					? append("(").append(other).append(")")
					: append(other);
		}

		public Builder appendParam(@Nullable final Object param)
		{
			return append("?", param);
		}
	}
}
