/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.dao.impl;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DateIntervalIntersectionQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	@NonNull private final ModelColumnNameValue<T> lowerBoundColumnName;
	@NonNull private final ModelColumnNameValue<T> upperBoundColumnName;
	@Nullable private final Instant lowerBoundValue;
	@Nullable private final Instant upperBoundValue;

	private String sqlWhereClause; // lazy
	private List<Object> sqlParams; // lazy

	public DateIntervalIntersectionQueryFilter(
			@NonNull final ModelColumnNameValue<T> lowerBoundColumnName,
			@NonNull final ModelColumnNameValue<T> upperBoundColumnName,
			@Nullable final Instant lowerBoundValue,
			@Nullable final Instant upperBoundValue)
	{
		this.lowerBoundColumnName = lowerBoundColumnName;
		this.upperBoundColumnName = upperBoundColumnName;
		this.lowerBoundValue = lowerBoundValue;
		this.upperBoundValue = upperBoundValue;
	}

	@Override
	public boolean accept(@NonNull final T model)
	{
		final Range<Instant> range1 = closedOpenRange(
				TimeUtil.asInstant(lowerBoundColumnName.getValue(model)),
				TimeUtil.asInstant(upperBoundColumnName.getValue(model)));

		final Range<Instant> range2 = closedOpenRange(lowerBoundValue, upperBoundValue);

		return isOverlapping(range1, range2);
	}

	public static Range<Instant> closedOpenRange(@Nullable final Instant lowerBound, @Nullable final Instant upperBound)
	{
		if (lowerBound == null)
		{
			return upperBound == null
					? Range.all()
					: Range.upTo(upperBound, BoundType.OPEN);
		}
		else
		{
			return upperBound == null
					? Range.downTo(lowerBound, BoundType.CLOSED)
					: Range.closedOpen(lowerBound, upperBound);
		}
	}

	public static boolean isOverlapping(@NonNull final Range<Instant> range1, @NonNull final Range<Instant> range2)
	{
		if (!range1.isConnected(range2))
		{
			return false;
		}

		return !range1.intersection(range2).isEmpty();
	}

	@Deprecated
	@Override
	public String toString()
	{
		return getSql();
	}

	@Override
	public String getSql()
	{
		buildSqlIfNeeded();
		return sqlWhereClause;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx_NOTUSED)
	{
		return getSqlParams();
	}

	public List<Object> getSqlParams()
	{
		buildSqlIfNeeded();
		return sqlParams;
	}

	private void buildSqlIfNeeded()
	{
		if (sqlWhereClause != null)
		{
			return;
		}

		if (isConstantTrue())
		{
			final ConstantQueryFilter<Object> acceptAll = ConstantQueryFilter.of(true);
			sqlParams = acceptAll.getSqlParams();
			sqlWhereClause = acceptAll.getSql();
		}
		else
		{
			sqlParams = Arrays.asList(lowerBoundValue, upperBoundValue);
			sqlWhereClause = "NOT ISEMPTY("
					+ "TSTZRANGE(" + lowerBoundColumnName.getColumnName() + ", " + upperBoundColumnName.getColumnName() + ", '[)')"
					+ " * "
					+ "TSTZRANGE(?, ?, '[)')"
					+ ")";
		}
	}

	private boolean isConstantTrue()
	{
		return lowerBoundValue == null && upperBoundValue == null;
	}

}
