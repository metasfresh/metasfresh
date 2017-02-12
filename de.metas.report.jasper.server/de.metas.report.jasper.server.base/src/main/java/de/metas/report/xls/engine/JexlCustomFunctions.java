package de.metas.report.xls.engine;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.JexlEngine;
import org.compiere.util.TimeUtil;
import org.jxls.expression.ExpressionEvaluator;
import org.jxls.expression.JexlExpressionEvaluator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * metasfresh Jxls/Jexl custom helper functions.
 * 
 * Excel usage example:
 * 
 * <pre>
 * jx:hide-column(condition='not m:monthBetween(0, p.StartDate, p.EndDate)' lastCell='N3')
 * </pre>
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JexlCustomFunctions
{
	private static final Logger logger = LogManager.getLogger(JexlCustomFunctions.class);

	public static final void registerIfNeeded(final ExpressionEvaluator expressionEvaluator)
	{
		if (expressionEvaluator instanceof JexlExpressionEvaluator)
		{
			final JexlExpressionEvaluator jexlExpressionEvaluator = (JexlExpressionEvaluator)expressionEvaluator;
			final JexlEngine jexlEngine = jexlExpressionEvaluator.getJexlEngine();

			final Map<String, Object> existingFunctions = jexlEngine.getFunctions();
			if (existingFunctions != null && existingFunctions.get(NAMESPACE) instanceof JexlCustomFunctions)
			{
				// already registered
				return;
			}

			final Map<String, Object> functions = new HashMap<>();
			if (existingFunctions != null && !existingFunctions.isEmpty())
			{
				functions.putAll(existingFunctions);
			}

			final JexlCustomFunctions functionsInstance = new JexlCustomFunctions();
			functions.put(NAMESPACE, functionsInstance);
			jexlEngine.setFunctions(functions);

			logger.debug("Registered {} to namespace {}", functionsInstance, NAMESPACE);
		}
		else
		{
			logger.warn("Skip registering our custom functions because {} is not supported", expressionEvaluator);
		}
	}

	private static final String NAMESPACE = "m";

	/**
	 *
	 * @param month month (1-Jan, 2-Feb ... 12-Dec)
	 * @param dateStart date or null; null will be ignored
	 * @param dateEnd date or null; null will be ignored
	 * @return true if given <code>month</code> is between given dates
	 */
	public boolean monthBetween(final int monthOneBased, final Date dateStart, final Date dateEnd)
	{
		if (monthOneBased < 1 || monthOneBased > 12)
		{
			throw new IllegalArgumentException("Invalid month value '" + monthOneBased + "'. It shall be between 1 and 12.");
		}
		
		// convert the "one based" month (i.e. Jan=1) to "zero based" (i.e. Jan=0)
		final int month = monthOneBased - 1;

		logger.trace("Evaluating monthBetween: month(zero based)={}, date={} -> {}", month, dateStart, dateEnd);

		@SuppressWarnings("deprecation")
		final int fromMonth = dateStart == null ? Calendar.JANUARY : dateStart.getMonth();
		if (month < fromMonth)
		{
			logger.trace("Not between because month is before start date");
			return false;
		}

		@SuppressWarnings("deprecation")
		final int toMonth = dateEnd == null ? Calendar.DECEMBER : dateEnd.getMonth();
		if (month > toMonth)
		{
			logger.trace("Not between because month is after end date");
			return false;
		}

		logger.trace("Returning true because month is between given dates");
		return true;
	}
	/**
	 * @param month month (1-Jan, 2-Feb ... 12-Dec)
	 * @param year year (2000, ... 2016)
	 * @param dateStart date or null; null will be ignored
	 * @param dateEnd date or null; null will be ignored
	 * @return true if given <code>month</code> and <code>year</code> is between given dates
	 */
	public boolean monthBetween(final int monthOneBased, final int year, final Date dateStart, final Date dateEnd)
	{
		if (monthOneBased < 1 || monthOneBased > 12)
		{
			throw new IllegalArgumentException(
					"Invalid month value '" + monthOneBased + "'. It shall be between 1 and 12.");
		}
		
		final int yearFrom = dateStart == null ? year : TimeUtil.getYearFromTimestamp(dateStart);
		final int yearTo = dateEnd == null ? year : TimeUtil.getYearFromTimestamp(dateEnd);

		// if year is after endDate, false
		if (year > yearTo)
		{
			logger.trace("Not between because year is after endDate");
			return false;
		}

		// if year is before startDate, false
		if (year < yearFrom)
		{
			logger.trace("Not between because year is before startDate");
			return false;
		}

		// convert the "one based" month (i.e. Jan=1) to "zero based" (i.e. Jan=0)
		final int month = monthOneBased - 1;

		logger.trace("Evaluating monthBetween: month(zero based)={}, date={} -> {}", month, dateStart, dateEnd);

		@SuppressWarnings("deprecation")
		final int fromMonth = dateStart == null ? Calendar.JANUARY : dateStart.getMonth();
		if ((month < fromMonth) && (year == yearFrom))
		{
			logger.trace("Not between because month is before start date");
			return false;
		}

		@SuppressWarnings("deprecation")
		final int toMonth = dateEnd == null ? Calendar.DECEMBER : dateEnd.getMonth();
		if ((month > toMonth) && (year == yearTo))
		{
			logger.trace("Not between because month is after end date");
			return false;
		}

		logger.trace("Returning true because month is between given dates");
		return true;
	}
}
