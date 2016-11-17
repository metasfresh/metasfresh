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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;

/**
 *
 * @author tsa
 *
 * @param <T> model type
 */
public interface IQueryBuilder<T>
{
	/**
	 * Advice the SQL query builder, in case our filters are joined by OR, to explode them in several UNIONs.
	 *
	 * This is a huge optimization for databases like PostgreSQL which have way better performances on UNIONs instead of WHERE expressions joined by OR.
	 *
	 * Example: A query like
	 *
	 * <pre>
	 * SELECT ... FROM ... WHERE (Expression1 OR Expression2 OR Expression3)
	 * </pre>
	 *
	 * will be exploded to:
	 *
	 * <pre>
	 *  SELECT ... FROM ... WHERE Expression1
	 *  UNION DISTINCT
	 *  SELECT ... FROM ... WHERE Expression2
	 *  UNION DISTINCT
	 *  SELECT ... FROM ... WHERE Expression3
	 * </pre>
	 */
	String OPTION_Explode_OR_Joins_To_SQL_Unions = "Explode_OR_Joins_To_SQL_Unions";

	IQueryBuilder<T> copy();

	Class<T> getModelClass();

	Properties getCtx();

	String getTrxName();

	/**
	 * Add the given filter.
	 *
	 * @param filter
	 * @return
	 */
	IQueryBuilder<T> filter(IQueryFilter<T> filter);

	IQueryBuilder<T> filterByClientId();

	ICompositeQueryFilter<T> getFilters();

	IQueryBuilder<T> setLimit(int limit);

	/**
	 * Sets a query option which will be used while building the query or while executing the query.
	 *
	 * NOTE: all options will be also passed to {@link IQuery} instance when it will be created.
	 *
	 * @param name
	 * @param value
	 * @see IQuery#setOptions(java.util.Map).
	 */
	IQueryBuilder<T> setOption(String name, Object value);

	/**
	 * Convenient way of calling {@link #setOption(String, Object)} with <code>value</code> = {@link Boolean#TRUE}.
	 *
	 * @param name
	 */
	IQueryBuilder<T> setOption(String name);

	int getLimit();

	IQueryBuilderOrderByClause<T> orderBy();

	IQuery<T> create();

	IQueryBuilder<T> addNotEqualsFilter(String columnName, Object value);

	IQueryBuilder<T> addNotEqualsFilter(ModelColumn<T, ?> column, Object value);
	
	IQueryBuilder<T> addNotNull(ModelColumn<T, ?> column);

	IQueryBuilder<T> addNotNull(String columnName);


	IQueryBuilder<T> addCoalesceEqualsFilter(Object value, String... columnNames);

	IQueryBuilder<T> addEqualsFilter(String columnName, Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addEqualsFilter(ModelColumn<T, ?> column, Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addEqualsFilter(String columnName, Object value);

	IQueryBuilder<T> addEqualsFilter(ModelColumn<T, ?> column, Object value);

	/**
	 * Adds a substring filter to this instance's internal composite filter.
	 *
	 * @param columnname
	 * @param substring
	 * @return this
	 * @see ICompositeQueryFilter#addSubstringFilter(String, String)
	 *
	 */
	IQueryBuilder<T> addSubstringFilter(String columnname, String substring, boolean ignoreCase);

	/**
	 * See {@link #addSubstringFilter(String, String, boolean)}.
	 *
	 * @param column
	 * @param substring
	 * @param ignoreCase
	 * @return
	 */
	IQueryBuilder<T> addSubstringFilter(ModelColumn<T, ?> column, String substring, boolean ignoreCase);

	IQueryBuilder<T> addCompareFilter(String columnName, Operator operator, Object value);

	IQueryBuilder<T> addCompareFilter(ModelColumn<T, ?> column, Operator operator, Object value);

	IQueryBuilder<T> addCompareFilter(String columnName, Operator operator, Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addCompareFilter(ModelColumn<T, ?> column, Operator operator, Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addOnlyContextClient(Properties ctx);

	IQueryBuilder<T> addOnlyContextClient();

	IQueryBuilder<T> addOnlyContextClientOrSystem();

	IQueryBuilder<T> addOnlyActiveRecordsFilter();

	@SuppressWarnings("unchecked")
	<V> IQueryBuilder<T> addInArrayFilter(String columnName, V... values);

	@SuppressWarnings("unchecked")
	<V> IQueryBuilder<T> addInArrayFilter(ModelColumn<T, ?> column, V... values);

	<V> IQueryBuilder<T> addInArrayFilter(String columnName, Collection<V> values);

	<V> IQueryBuilder<T> addInArrayFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * NOTE: in case <code>values</code> collection is empty this filter will return <code>true</code> (as intuitively expected).
	 *
	 * @param column
	 * @param values
	 * @return this
	 */
	<V> IQueryBuilder<T> addNotInArrayFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * NOTE: in case <code>values</code> collection is empty this filter will return <code>true</code> (as intuitively expected).
	 *
	 * @param columnName
	 * @param values
	 * @return this
	 */
	<V> IQueryBuilder<T> addNotInArrayFilter(String columnName, Collection<V> values);

	<ST> IQueryBuilder<T> addInSubQueryFilter(String columnName, IQueryFilterModifier modifier, String subQueryColumnName, IQuery<ST> subQuery);

	/**
	 *
	 * @param columnName the key column from the "main" query
	 * @param subQueryColumnName the key column from the "sub" query
	 * @param subQuery the actual sub query
	 * @return this
	 */
	<ST> IQueryBuilder<T> addInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);

	<ST> IQueryBuilder<T> addNotInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);

	/**
	 *
	 * @param column the key column from the "main" query
	 * @param subQueryColumn the key column from the "sub" query
	 * @param subQuery the actual sub query
	 * @return this
	 */
	<ST> IQueryBuilder<T> addInSubQueryFilter(ModelColumn<T, ?> column, ModelColumn<ST, ?> subQueryColumn, IQuery<ST> subQuery);

	<ST> IQueryBuilder<T> addNotInSubQueryFilter(ModelColumn<T, ?> column, ModelColumn<ST, ?> subQueryColumn, IQuery<ST> subQuery);

	/**
	 * Create a new {@link IQueryBuilder} which collects models from given model column.
	 *
	 * Example: collect all invoice business partners (<code>Bill_Partner_ID</code>) from matched <code>C_Order</code>:
	 *
	 * <pre>
	 * final IQueryBuilder&lt;I_C_Order&gt; ordersQueryBuilder = ....;
	 *
	 * final List&lt;I_C_BPartner&gt; bpartners = ordersQueryBuilder
	 *   .addCollect(I_C_Order.COLUMN_Bill_Partner_ID) // an IQueryBuilder&lt;I_C_BPartner&gt; is returned here
	 *   .create() // create IQuery&lt;I_C_BPartner&gt;
	 *   .list()   // list bpartners
	 * </pre>
	 *
	 *
	 * @param column model column
	 * @return list of collected models
	 */
	<CollectedBaseType, ParentModelType> IQueryBuilder<CollectedBaseType> andCollect(ModelColumn<ParentModelType, CollectedBaseType> column);

	/**
	 * Same as {@link #andCollect(ModelColumn)} but you can specify what interface to use for returning values.
	 *
	 * @param column
	 * @param collectedType
	 * @return
	 */
	<CollectedBaseType, CollectedType extends CollectedBaseType, ParentModelType> IQueryBuilder<CollectedType> andCollect(ModelColumn<ParentModelType, CollectedBaseType> column,
			Class<CollectedType> collectedType);

	/**
	 * Returns a query to retrieve those records that reference the result of the query which was specified so far.<br>
	 * Example: first, configure a query builder to select a certain kind of <code>M_InOuts</code>. then use this method to retrieve not the specified inOuts, but its M_InOutLines:
	 *
	 * <pre>
	 * final IQueryBuilder&lt;I_M_InOut&gt; inoutsQueryBuilder = ....;
	 *
	 * final List&lt;I_M_InOutLine&gt; inoutLines = inoutsQueryBuilder
	 *   .andCollectChildren(I_M_InOutLine.COLUMN_M_InOut_ID, I_M_InOutLine.class) // an IQueryBuilder&lt;I_M_InOutLine&gt; is returned here
	 *   .create() // create IQuery&lt;I_M_InOutLine&gt;
	 *   .list()   // list inout lines
	 * </pre>
	 *
	 * @param linkColumnInChildTable the column in child model which will be used to join the child records to current record's primary key
	 * @param childType child model to be used
	 * @return query build for <code>ChildType</code>
	 */
	<ChildType, ExtChildType extends ChildType> IQueryBuilder<ExtChildType> andCollectChildren(ModelColumn<ChildType, ?> linkColumnInChildTable, Class<ExtChildType> childType);

	/**
	 * Returns a query to retrieve those records that reference the result of the query which was specified so far.<br>
	 * .
	 * 
	 * This is a convenient version of {@link #andCollectChildren(ModelColumn, Class)} for the case when you don't have to retrieve an extended interface of the child type.
	 * 
	 * @param linkColumnInChildTable the column in child model which will be used to join the child records to current record's primary key
	 * @return query build for <code>ChildType</code>
	 */
	default <ChildType> IQueryBuilder<ChildType> andCollectChildren(final ModelColumn<ChildType, ?> linkColumnInChildTable)
	{
		final Class<ChildType> childType = linkColumnInChildTable.getModelClass();
		return andCollectChildren(linkColumnInChildTable, childType);
	}

	/**
	 * Sets the join mode of this instance's internal composite filter.
	 *
	 * @return this
	 * @see ICompositeQueryFilter#setJoinOr()
	 */
	IQueryBuilder<T> setJoinOr();

	/**
	 * Sets the join mode of this instance's internal composite filter.
	 *
	 * @return this
	 * @see ICompositeQueryFilter#setJoinAnd()
	 */
	IQueryBuilder<T> setJoinAnd();

	/**
	 * Will only return records that are referenced by a <code>T_Selection</code> records which has the given <code>AD_PInstance_ID</code>.
	 *
	 * @param AD_PInstance_ID
	 * @return
	 */
	IQueryBuilder<T> setOnlySelection(int AD_PInstance_ID);

	/**
	 * Start an aggregation of different columns, everything groupped by given <code>column</code>
	 *
	 * @param column
	 * @return aggregation builder
	 */
	<TargetModelType> IQueryAggregateBuilder<T, TargetModelType> aggregateOnColumn(ModelColumn<T, TargetModelType> column);

	IQueryBuilder<T> addBetweenFilter(final ModelColumn<T, ?> column, final Object valueFrom, final Object valueTo, final IQueryFilterModifier modifier);

	IQueryBuilder<T> addBetweenFilter(final String columnName, final Object valueFrom, final Object valueTo, final IQueryFilterModifier modifier);

	IQueryBuilder<T> addBetweenFilter(final ModelColumn<T, ?> column, final Object valueFrom, final Object valueTo);

	IQueryBuilder<T> addBetweenFilter(final String columnName, final Object valueFrom, final Object valueTo);

	IQueryBuilder<T> addEndsWithQueryFilter(String columnName, String endsWithString);

	/**
	 * Creates, appends and returns new composite filter.
	 *
	 * @return created composite filter
	 */
	ICompositeQueryFilter<T> addCompositeQueryFilter();

	IQueryBuilder<T> addValidFromToMatchesFilter(ModelColumn<T, ?> validFromColumn, ModelColumn<T, ?> validToColumn, Date dateToMatch);
}
