package org.adempiere.ad.dao;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Date;

/**
 * Query Statistics Logger: build up a list of top used SQL queries
 *
 * NOTE: it is disabled by default
 *
 * @author tsa
 *
 */
public interface IQueryStatisticsLogger
// extends ISingletonService // commented out because it shall be accessed via spring @Autowired
{
	/**
	 * Enable statistics logging
	 */
	void enable();

	/**
	 * Enable statistics logging and also enable SQL tracing.
	 *
	 * The executed SQLs will be printed to {@link System#err}.
	 */
	void enableWithSqlTracing();

	/**
	 * Disable statistics logging
	 */
	void disable();

	/**
	 * Reset statistics and sets <code>validFrom</code> to the current time. Does <b>not</b> reset <code>filterBy</code>.
	 */
	void reset();

	/**
	 * IF this property is set then only such SQLs which contain the given String as substring are logged.
	 *
	 * @param filterBy
	 */
	void setFilterBy(String filterBy);

	/**
	 * See {@link #setFilterBy(String)}.
	 *
	 * @return
	 */
	String getFilterBy();

	/**
	 * Clears the filtering. See {@link #setFilterBy(String)}.
	 */
	void clearFilterBy();

	/**
	 *
	 * @return date+time on which we started to collect statistics
	 */
	Date getValidFrom();

	/**
	 *
	 * @return string array of top used SQL queries with statistics informations (ordered from most used to less used)
	 */
	String[] getTopTotalDurationQueriesAsString();

	/**
	 * Gets top SQL queries ordered by their total summed executon time (descending).
	 *
	 * @return
	 */
	String[] getTopCountQueriesAsString();

	/**
	 * Gets top SQL queries ordered by their average execution time (descending)
	 *
	 * @return
	 */
	String[] getTopAverageDurationQueriesAsString();
}
