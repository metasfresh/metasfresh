/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

import java.util.List;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

/**
 * Date TRUNC modifier
 * 
 * @author tsa
 * 
 */
@EqualsAndHashCode
public final class DateTruncQueryFilterModifier implements IQueryFilterModifier
{
	public static final DateTruncQueryFilterModifier DAY = new DateTruncQueryFilterModifier(TimeUtil.TRUNC_DAY);
	public static final DateTruncQueryFilterModifier WEEK = new DateTruncQueryFilterModifier(TimeUtil.TRUNC_WEEK);
	public static final DateTruncQueryFilterModifier MONTH = new DateTruncQueryFilterModifier(TimeUtil.TRUNC_MONTH);
	public static final DateTruncQueryFilterModifier YEAR = new DateTruncQueryFilterModifier(TimeUtil.TRUNC_YEAR);

	/**
	 * Gets the {@link DateTruncQueryFilterModifier} to be used for given <code>trunc</code> string
	 * 
	 * @param trunc see {@link TimeUtil}#TRUNC_* constants
	 * @return modifier instance
	 */
	public static DateTruncQueryFilterModifier forTruncString(final String trunc)
	{
		if (TimeUtil.TRUNC_DAY.equals(trunc))
		{
			return DAY;
		}
		else if (TimeUtil.TRUNC_WEEK.equals(trunc))
		{
			return WEEK;
		}
		else if (TimeUtil.TRUNC_MONTH.equals(trunc))
		{
			return MONTH;
		}
		else if (TimeUtil.TRUNC_YEAR.equals(trunc))
		{
			return YEAR;
		}
		else
		{
			return new DateTruncQueryFilterModifier(trunc);
		}
	}

	private final String trunc;
	private final String truncSql;

	/**
	 * 
	 * @param trunc see {@link TimeUtil}.TRUNC_*
	 */
	protected DateTruncQueryFilterModifier(final String trunc)
	{
		this.trunc = trunc;
		if (trunc == null)
		{
			truncSql = null;
		}
		else
		{
			truncSql = getTruncSql(trunc);
		}
	}

	@Override
	public String toString()
	{
		return "DateTrunc-" + trunc;
	}

	/**
	 * Convert {@link TimeUtil}.TRUNC_* values to their correspondent TRUNC db function parameter
	 * 
	 * @param trunc
	 * @return
	 */
	private static String getTruncSql(final String trunc)
	{
		if (TimeUtil.TRUNC_DAY.equals(trunc))
		{
			return "DD";
		}
		else if (TimeUtil.TRUNC_WEEK.equals(trunc))
		{
			return "WW";
		}
		else if (TimeUtil.TRUNC_MONTH.equals(trunc))
		{
			return "MM";
		}
		else if (TimeUtil.TRUNC_YEAR.equals(trunc))
		{
			return "Y";
		}
		else if (TimeUtil.TRUNC_QUARTER.equals(trunc))
		{
			return "Q";
		}
		else
		{
			throw new IllegalArgumentException("Invalid date truncate option: " + trunc);
		}
	}

	@Override
	public @NonNull String getColumnSql(final @NonNull String columnName)
	{
		if (truncSql == null)
		{
			return columnName;
		}

		// TODO: optimization: instead of using TRUNC we shall use BETWEEN and precalculated values because:
		// * using BETWEEN is INDEX friendly
		// * using functions is not INDEX friendly at all and if we have an index on this date column, the index won't be used

		final String columnSqlNew = "TRUNC(" + columnName + ", " + DB.TO_STRING(truncSql) + ")";
		return columnSqlNew;
	}

	@Override
	public String getValueSql(final Object value, final List<Object> params)
	{
		final String valueSql;
		if (value instanceof ModelColumnNameValue<?>)
		{
			final ModelColumnNameValue<?> modelValue = (ModelColumnNameValue<?>)value;
			valueSql = modelValue.getColumnName();
		}
		else
		{
			valueSql = "?";
			params.add(value);
		}

		if (truncSql == null)
		{
			return valueSql;
		}

		// note: cast is needed for postgresql, else you get "ERROR: function trunc(unknown, unknown) is not unique"
		return "TRUNC(?::timestamp, " + DB.TO_STRING(truncSql) + ")";
	}

	/**
	 * @deprecated Please use {@link #convertValue(java.util.Date)}
	 */
	@Nullable
	@Override
	@Deprecated
	public Object convertValue(@Nullable final String columnName, @Nullable final Object value, final @Nullable Object model)
	{
		return convertValue(TimeUtil.asTimestamp(value));
	}

	public java.util.Date convertValue(final java.util.Date date)
	{
		if (date == null)
		{
			return null;
		}
		return TimeUtil.trunc(date, trunc);
	}

}
