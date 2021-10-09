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

import de.metas.util.lang.RepoIdAware;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.InSubQueryFilter;
import org.adempiere.ad.dao.impl.NotEqualsQueryFilter;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public interface ICompositeQueryFilter<T> extends IQueryFilter<T>
{
	/**
	 * Creates a copy of this object.
	 *
	 * @return a copy of this object
	 */
	ICompositeQueryFilter<T> copy();

	/**
	 * Set default behavior in case this composite is empty.
	 *
	 * @param defaultAccept
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

	IInSubQueryFilterClause<T, ICompositeQueryFilter<T>> addInSubQueryFilter();

	/**
	 * Add a {@link InSubQueryFilter}
	 *
	 * @param columnName
	 * @param subQueryColumnName
	 * @param subQuery
	 * @return this
	 */
	<ST> ICompositeQueryFilter<T> addInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);

	/**
	 * Add a NOT-{@link InSubQueryFilter}.
	 *
	 * @param columnName
	 * @param subQueryColumnName
	 * @param subQuery
	 * @return
	 */
	<ST> ICompositeQueryFilter<T> addNotInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);

	<ST> ICompositeQueryFilter<T> addNotInSubQueryFilter(final ModelColumn<T, ?> column, final ModelColumn<ST, ?> subQueryColumn, final IQuery<ST> subQuery);

	<ST> ICompositeQueryFilter<T> addInSubQueryFilter(ModelColumn<T, ?> column, ModelColumn<ST, ?> subQueryColumn, IQuery<ST> subQuery);

	/**
	 * Add a {@link InSubQueryFilter}
	 *
	 * @param columnName
	 * @param modifier
	 * @param subQueryColumnName
	 * @param subQuery
	 * @return this
	 */
	<ST> ICompositeQueryFilter<T> addInSubQueryFilter(String columnName, IQueryFilterModifier modifier, String subQueryColumnName, IQuery<ST> subQuery);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is accepted.
	 */
	<V> ICompositeQueryFilter<T> addInArrayOrAllFilter(String columnName, Collection<V> values);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is rejected.
	 */
	<V> ICompositeQueryFilter<T> addInArrayFilter(String columnName, Collection<V> values);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is accepted.
	 *
	 * @param values may also be {@link RepoIdAware}s
	 */
	<V> ICompositeQueryFilter<T> addInArrayOrAllFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is rejected.
	 */
	<V> ICompositeQueryFilter<T> addInArrayFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * NOTE: in case <code>values</code> collection is empty this filter will return <code>true</code> (as intuitively expected).
	 *
	 * @param column
	 * @param values
	 * @return this
	 */
	<V> ICompositeQueryFilter<T> addNotInArrayFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * NOTE: in case <code>values</code> collection is empty this filter will return <code>true</code> (as intuitively expected).
	 *
	 * @param columnName
	 * @param values
	 * @return this
	 * @deprecated  dev note: if the target column (i.e. column identified by @param columnName) has value null the query won't match
	 */
	@Deprecated
	<V> ICompositeQueryFilter<T> addNotInArrayFilter(String columnName, Collection<V> values);

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is accepted.
	 */
	@SuppressWarnings("unchecked")
	<V> ICompositeQueryFilter<T> addInArrayOrAllFilter(String columnName, V... values);

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is rejected.
	 */
	@SuppressWarnings("unchecked")
	<V> ICompositeQueryFilter<T> addInArrayFilter(String columnName, V... values);

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is accepted.
	 */
	@SuppressWarnings("unchecked")
	<V> ICompositeQueryFilter<T> addInArrayOrAllFilter(ModelColumn<T, ?> column, V... values);

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is rejected.
	 */
	@SuppressWarnings("unchecked")
	<V> ICompositeQueryFilter<T> addInArrayFilter(ModelColumn<T, ?> column, V... values);

	/**
	 * Add a {@link ActiveRecordQueryFilter}
	 *
	 * @return this
	 */
	ICompositeQueryFilter<T> addOnlyActiveRecordsFilter();

	/**
	 * Accept only those records which have AD_Client_ID same as the "#AD_Client_ID" from given context.
	 *
	 * @param ctx
	 * @return this
	 */
	ICompositeQueryFilter<T> addOnlyContextClient(Properties ctx);

	/**
	 * Accept only those records which have AD_Client_ID same as the "#AD_Client_ID" from given context or System client.
	 *
	 * @param ctx
	 * @return this
	 */
	ICompositeQueryFilter<T> addOnlyContextClientOrSystem(Properties ctx);

	/**
	 * Add a {@link CompareQueryFilter}
	 *
	 * @return this
	 */
	ICompositeQueryFilter<T> addCompareFilter(String columnName, CompareQueryFilter.Operator operator, @Nullable Object value);

	ICompositeQueryFilter<T> addCompareFilter(ModelColumn<T, ?> column, CompareQueryFilter.Operator operator, @Nullable Object value);

	ICompositeQueryFilter<T> addCompareFilter(String columnName, Operator operator, Object value, IQueryFilterModifier modifier);

	ICompositeQueryFilter<T> addCompareFilter(ModelColumn<T, ?> column, Operator operator, Object value, IQueryFilterModifier modifier);

	/**
	 * Add a {@link EqualsQueryFilter}
	 *
	 * @return this
	 */
	ICompositeQueryFilter<T> addEqualsFilter(String columnName, @Nullable Object value);

	ICompositeQueryFilter<T> addEqualsFilter(ModelColumn<T, ?> column, @Nullable Object value);

	/**
	 * Add a {@link EqualsQueryFilter}
	 */
	ICompositeQueryFilter<T> addEqualsFilter(String columnName, Object value, IQueryFilterModifier modifier);

	ICompositeQueryFilter<T> addEqualsFilter(ModelColumn<T, ?> column, Object value, IQueryFilterModifier modifier);

	/**
	 * Adds a filter for substrings. That filter creates SQL such as <code>columnName LIKE '%substring%'</code>.<br>
	 * The string to filter by may contain {@code _} and {@code %}. Starting and trailing '%' are supplemented if missing.
	 *
	 * Note: if you don't want the starting and trailing '%' to be supplemented, check out {@link #addCompareFilter(String, Operator, Object)}
	 *
	 * @param ignoreCase if <code>true</code> the filter will use <code>ILIKE</code> instead of <code>LIKE</code>
	 */
	ICompositeQueryFilter<T> addStringLikeFilter(String columnName, String substring, boolean ignoreCase);

	/**
	 * See {@link #addStringLikeFilter(String, String, boolean)}
	 */
	ICompositeQueryFilter<T> addStringLikeFilter(ModelColumn<T, ?> column, String substring, boolean ignoreCase);

	ICompositeQueryFilter<T> addStringNotLikeFilter(ModelColumn<T, ?> column, String substring, boolean ignoreCase);

	ICompositeQueryFilter<T> addCoalesceEqualsFilter(Object value, String... columnNames);

	/**
	 * Add a {@link NotEqualsQueryFilter}.
	 * As with all {@link CompareQueryFilter}s: if the filter is about an {@code _ID}, then also {@link RepoIdAware} is supported.
	 */
	ICompositeQueryFilter<T> addNotEqualsFilter(String columnName, @Nullable Object value);

	ICompositeQueryFilter<T> addNotEqualsFilter(ModelColumn<T, ?> column, @Nullable Object value);

	ICompositeQueryFilter<T> addNotNull(ModelColumn<T, ?> column);

	ICompositeQueryFilter<T> addNotNull(String columnName);

	/**
	 * Calling this method means that <b>all</b> filters (not just subsequent ones) added to this composite are joined by OR.
	 *
	 * @return
	 */
	ICompositeQueryFilter<T> setJoinOr();

	/**
	 * Calling this method means that <b>all</b> filters (not just subsequent ones) added to this composite are joined by AND.
	 *
	 * @return
	 */
	ICompositeQueryFilter<T> setJoinAnd();

	/**
	 * Add a group of filters
	 */
	ICompositeQueryFilter<T> addFilters(List<IQueryFilter<T>> filters);

	/**
	 * Unboxes and adds the filters contained in the <code>compositeFilter</code>.
	 * If it could not be unboxed (e.g. because JOIN method does not match) the composite filter is added as is.
	 * Note that by "unboxing" we mean getting the filters included in the given {@code compositeFilter} and adding them to this instance directly, rather than adding the given {@code compositeFilter} itself.
	 */
	ICompositeQueryFilter<T> addFiltersUnboxed(ICompositeQueryFilter<T> compositeFilter);

	/**
	 * Add a single filter
	 *
	 * @param filter
	 * @return this
	 */
	ICompositeQueryFilter<T> addFilter(IQueryFilter<T> filter);

	/**
	 * Remove filter
	 *
	 * @param filter
	 * @return this
	 */
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
	 * @param ctx
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
	 *
	 * @return
	 */
	ISqlQueryFilter asPartialSqlQueryFilter();

	/**
	 * Converts this filter to an {@link ISqlQueryFilter} if {@link #isPureSql()}.
	 * <p>
	 * If this is not a pure SQL filter, an exception will be thrown.
	 *
	 * @return
	 * @throws IllegalStateException if it's not a Pure SQL
	 */
	ISqlQueryFilter asSqlQueryFilter() throws IllegalStateException;

	ICompositeQueryFilter<T> addBetweenFilter(final ModelColumn<T, ?> column, final Object valueFrom, final Object valueTo, final IQueryFilterModifier modifier);

	ICompositeQueryFilter<T> addBetweenFilter(final String columnName, final Object valueFrom, final Object valueTo, final IQueryFilterModifier modifier);

	ICompositeQueryFilter<T> addBetweenFilter(final ModelColumn<T, ?> column, final Object valueFrom, final Object valueTo);

	ICompositeQueryFilter<T> addBetweenFilter(final String columnName, final Object valueFrom, final Object valueTo);

	ICompositeQueryFilter<T> addEndsWithQueryFilter(String columnName, String endsWithString);

	ICompositeQueryFilter<T> addValidFromToMatchesFilter(ModelColumn<T, ?> validFromColumn, ModelColumn<T, ?> validToColumn, Date dateToMatch);

	/**
	 * Creates, appends and returns new composite filter.
	 *
	 * @return created composite filter
	 */
	ICompositeQueryFilter<T> addCompositeQueryFilter();

	ICompositeQueryFilter<T> allowSqlFilters(boolean allowSqlFilters);
}
