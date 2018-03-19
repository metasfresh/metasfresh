package org.adempiere.ad.dao.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryFilterModifier;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

/**
 * Date TRUNC modifier
 * 
 * @author tsa
 * 
 */
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
	public static final DateTruncQueryFilterModifier forTruncString(final String trunc)
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
		super();
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
	public String getColumnSql(final String columnSql)
	{
		if (truncSql == null)
		{
			return columnSql;
		}

		// TODO: optimization: instead of using TRUNC we shall use BETWEEN and precalculated values because:
		// * using BETWEEN is INDEX friendly
		// * using functions is not INDEX friendly at all and if we have an index on this date column, the index won't be used

		final String columnSqlNew = "TRUNC(" + columnSql + ", " + DB.TO_STRING(truncSql) + ")";
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
	@Override
	@Deprecated
	public Object convertValue(final String columnName, final Object value, final Object model)
	{
		return convertValue((java.util.Date)value);
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
