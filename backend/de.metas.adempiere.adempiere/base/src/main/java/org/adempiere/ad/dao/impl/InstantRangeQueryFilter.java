package org.adempiere.ad.dao.impl;

import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class InstantRangeQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private final String columnName;
	private final Range<Instant> range;

	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;

	private InstantRangeQueryFilter(@NonNull final String columnName, @NonNull Range<Instant> range)
	{
		this.columnName = columnName;
		this.range = range;
	}

	public static <T> InstantRangeQueryFilter<T> of(@NonNull final String columnName, @NonNull Range<Instant> range)
	{
		return new InstantRangeQueryFilter<>(columnName, range);
	}

	@Override
	public boolean accept(final T model)
	{
		final Instant value = InterfaceWrapperHelper.getValue(model, columnName)
				.map(TimeUtil::asInstant)
				.orElse(null);

		return value != null && range.contains(value);
	}

	@Override
	public final String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public final List<Object> getSqlParams(final Properties ctx)
	{
		return getSqlParams();
	}

	public final List<Object> getSqlParams()
	{
		buildSql();
		return sqlParams;
	}

	private void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final ArrayList<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sql.append(columnName).append(" IS NOT NULL");

		if (range.hasLowerBound())
		{
			final String operator;
			final BoundType boundType = range.lowerBoundType();
			switch (boundType)
			{
				case OPEN:
					operator = ">";
					break;
				case CLOSED:
					operator = ">=";
					break;
				default:
					throw new AdempiereException("Unknown bound: " + boundType);
			}

			sql.append(" AND ").append(columnName).append(operator).append("?");
			sqlParams.add(range.lowerEndpoint());
		}

		if (range.hasUpperBound())
		{
			final String operator;
			final BoundType boundType = range.upperBoundType();
			switch (boundType)
			{
				case OPEN:
					operator = "<";
					break;
				case CLOSED:
					operator = "<=";
					break;
				default:
					throw new AdempiereException("Unknown bound: " + boundType);
			}

			sql.append(" AND ").append(columnName).append(operator).append("?");
			sqlParams.add(range.upperEndpoint());
		}

		//
		this.sqlWhereClause = sql.toString();
		this.sqlParams = !sqlParams.isEmpty() ? Collections.unmodifiableList(sqlParams) : ImmutableList.of();
		this.sqlBuilt = true;
	}
}
