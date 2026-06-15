package org.adempiere.ad.dao.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

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
	public static <T> TypedSqlQueryFilter<T> of(final String sql)
	{
		final List<Object> params = ImmutableList.of();
		return new TypedSqlQueryFilter<>(sql, params);
	}

	public static <T> TypedSqlQueryFilter<T> of(final String sql, final List<Object> sqlParams)
	{
		return new TypedSqlQueryFilter<>(sql, sqlParams);
	}

	public static <T> TypedSqlQueryFilter<T> of(final String sql, final Object[] sqlParams)
	{
		return new TypedSqlQueryFilter<>(sql, sqlParams);
	}

	private final String sql;
	private final List<Object> sqlParams;

	private TypedSqlQueryFilter(final String sql, final Object[] sqlParams)
	{
		this(sql, sqlParams == null ? null : Arrays.asList(sqlParams));
	}

	private TypedSqlQueryFilter(@NonNull final String sql, @Nullable final List<Object> sqlParams)
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
