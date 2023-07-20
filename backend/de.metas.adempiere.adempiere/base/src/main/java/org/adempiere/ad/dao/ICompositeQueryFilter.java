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

package org.adempiere.ad.dao;

import java.util.List;
import java.util.Properties;

@SuppressWarnings("UnusedReturnValue")
public interface ICompositeQueryFilter<T>
		extends
		IQueryFilter<T>,
		ICompositeQueryFilterProxy<T, ICompositeQueryFilter<T>>
{
	/**
	 * Creates a copy of this object.
	 *
	 * @return a copy of this object
	 */
	ICompositeQueryFilter<T> copy();

	/**
	 * Set default behavior in case this composite is empty.
	 */
	ICompositeQueryFilter<T> setDefaultAccept(boolean defaultAccept);

	/**
	 * @return default behavior in case this composite is empty
	 */
	boolean isDefaultAccept();

	/**
	 * @return true if there are no included filters
	 */
	boolean isEmpty();

	/**
	 * @return how many filters are included in this composite filter
	 */
	int getFiltersCount();

	/**
	 * @return true if the JOIN method is AND
	 * @see #setJoinAnd()
	 */
	boolean isJoinAnd();

	/**
	 * @return true if the JOIN method is OR
	 * @see #setJoinOr()
	 */
	boolean isJoinOr();

	// IInSubQueryFilterClause<T, ICompositeQueryFilter<T>> addInSubQueryFilter();
	//
	// <ST> ICompositeQueryFilter<T> addInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);
	//
	// <ST> ICompositeQueryFilter<T> addNotInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);
	//
	// <ST> ICompositeQueryFilter<T> addNotInSubQueryFilter(final ModelColumn<T, ?> column, final ModelColumn<ST, ?> subQueryColumn, final IQuery<ST> subQuery);
	//
	// <ST> ICompositeQueryFilter<T> addInSubQueryFilter(ModelColumn<T, ?> column, ModelColumn<ST, ?> subQueryColumn, IQuery<ST> subQuery);
	//
	// /**
	//  * Add a {@link InSubQueryFilter}
	//  *
	//  * @return this
	//  */
	// <ST> ICompositeQueryFilter<T> addInSubQueryFilter(String columnName, IQueryFilterModifier modifier, String subQueryColumnName, IQuery<ST> subQuery);

	/**
	 * Calling this method means that <b>all</b> filters (not just subsequent ones) added to this composite are joined by OR.
	 */
	ICompositeQueryFilter<T> setJoinOr();

	/**
	 * Calling this method means that <b>all</b> filters (not just subsequent ones) added to this composite are joined by AND.
	 */
	ICompositeQueryFilter<T> setJoinAnd();

	ICompositeQueryFilter<T> addFilters(List<IQueryFilter<T>> filters);

	ICompositeQueryFilter<T> removeFilter(IQueryFilter<T> filter);

	/**
	 * @return a copy of all filters bound to this builder
	 */
	List<IQueryFilter<T>> getFilters();

	/**
	 * @return a readonly list of current nonSQL filters (i.e. which are NOT implementing {@link ISqlQueryFilter} interface).
	 */
	List<IQueryFilter<T>> getNonSqlFilters();

	/**
	 * @return a readonly list of current SQL filters (i.e. which are implementing {@link ISqlQueryFilter} interface).
	 */
	List<ISqlQueryFilter> getSqlFilters();

	/**
	 * @return true if all inner filters are {@link ISqlQueryFilter}s. In this case {@link #getNonSqlFilters()} shall return an empty list.
	 */
	boolean isPureSql();

	/**
	 * @return true if all inner filters are not {@link ISqlQueryFilter}s. In this case {@link #getSqlFilters()} shall return an empty list.
	 */
	boolean isPureNonSql();

	/**
	 * Gets the built SQL where clause from {@link #getSqlFilters()}.
	 *
	 * @return sql where clause
	 */
	String getSqlFiltersWhereClause();

	/**
	 * Gets SQL where clause parameters from {@link #getSqlFilters()}
	 *
	 * @return sql parameters
	 */
	List<Object> getSqlFiltersParams(Properties ctx);

	/**
	 * Gets an query filter which behaves like {@link #getNonSqlFilters()} list.
	 * <p>
	 * If there are no nonSQL filters, this method will return null.
	 *
	 * @return query filter or null
	 */
	IQueryFilter<T> asPartialNonSqlFilterOrNull();

	/**
	 * Gets an query filter which behaves like {@link #getSqlFilters()} list.
	 */
	ISqlQueryFilter asPartialSqlQueryFilter();

	/**
	 * Converts this filter to an {@link ISqlQueryFilter} if {@link #isPureSql()}.
	 * <p>
	 * If this is not a pure SQL filter, an exception will be thrown.
	 *
	 * @throws IllegalStateException if it's not a Pure SQL
	 */
	ISqlQueryFilter asSqlQueryFilter() throws IllegalStateException;

	ICompositeQueryFilter<T> allowSqlFilters(boolean allowSqlFilters);
}
