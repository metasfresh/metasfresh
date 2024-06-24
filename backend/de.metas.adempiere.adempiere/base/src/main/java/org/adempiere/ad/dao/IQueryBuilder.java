package org.adempiere.ad.dao;

import de.metas.process.PInstanceId;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 * @param <T> model type
 * @author tsa
 */
public interface IQueryBuilder<T>
{
	/**
	 * Advice the SQL query builder, in case our filters are joined by OR, to explode them in several UNIONs.
	 * <p>
	 * This is a huge optimization for databases like PostgreSQL which have way better performances on UNIONs instead of WHERE expressions joined by OR.
	 * <p>
	 * Example: A query like
	 *
	 * <pre>
	 * SELECT ... FROM ... WHERE (Expression1 OR Expression2 OR Expression3)
	 * </pre>
	 * <p>
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
	 */
	IQueryBuilder<T> filter(IQueryFilter<T> filter);

	/**
	 * Unboxes and adds the filters contained in the <code>compositeFilter</code>.
	 * If it could not be unboxed (e.g. because JOIN method does not match) the composite filter is added as is.
	 * Note that by "unboxing" we mean getting the filters included in the given {@code compositeFilter} and adding them to this instance directly, rather than adding the given {@code compositeFilter} itself.
	 */
	IQueryBuilder<T> addFiltersUnboxed(ICompositeQueryFilter<T> compositeFilter);

	IQueryBuilder<T> filterByClientId();

	ICompositeQueryFilter<T> getCompositeFilter();

	IQueryBuilder<T> setLimit(QueryLimit limit);

	@Deprecated
	default IQueryBuilder<T> setLimit(final int limit)
	{
		return setLimit(QueryLimit.ofInt(limit));
	}

	/**
	 * Sets a query option which will be used while building the query or while executing the query.
	 * <p>
	 * NOTE: all options will be also passed to {@link IQuery} instance when it will be created.
	 *
	 * @see IQuery#setOptions(java.util.Map).
	 */
	IQueryBuilder<T> setOption(String name, Object value);

	/**
	 * Convenient way of calling {@link #setOption(String, Object)} with <code>value</code> = {@link Boolean#TRUE}.
	 */
	IQueryBuilder<T> setOption(String name);

	QueryLimit getLimit();

	/**
	 * Make sure this instance now has an order-by-builder and return it.
	 */
	IQueryBuilderOrderByClause<T> orderBy();

	//@formatter:off
	default IQueryBuilder<T> clearOrderBys() { orderBy().clear(); return this; }
	
	/** order ascending, with {@code NULLS LAST} */
	default IQueryBuilder<T> orderBy(final String columnName) { orderBy().addColumn(columnName); return this; }
	
	/** order ascending, with {@code NULLS LAST} */
	default IQueryBuilder<T> orderBy(final ModelColumn<T, ?> column) { orderBy().addColumn(column); return this; }
	default IQueryBuilder<T> orderByDescending(final String columnName) { orderBy().addColumnDescending(columnName); return this; }
	default IQueryBuilder<T> orderByDescending(final ModelColumn<T, ?> column) { orderBy().addColumnDescending(column.getColumnName()); return this; }
	//@formatter:on

	IQuery<T> create();

	IQueryBuilder<T> addNotEqualsFilter(String columnName, @Nullable Object value);

	IQueryBuilder<T> addNotEqualsFilter(ModelColumn<T, ?> column, @Nullable Object value);

	IQueryBuilder<T> addNotNull(ModelColumn<T, ?> column);

	IQueryBuilder<T> addNotNull(String columnName);

	IQueryBuilder<T> addCoalesceEqualsFilter(Object value, String... columnNames);

	IQueryBuilder<T> addEqualsFilter(String columnName, @Nullable Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addEqualsFilter(ModelColumn<T, ?> column, @Nullable Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addEqualsFilter(String columnName, @Nullable Object value);

	IQueryBuilder<T> addEqualsFilter(ModelColumn<T, ?> column, @Nullable Object value);

	/**
	 * Filters using the given string as a <b>substring</b>.
	 * If this "substring" behavior is too opinionated for your case, consider using e.g. {@link #addCompareFilter(String, Operator, Object)}.
	 *
	 * @param substring will be complemented with {@code %} at both the string's start and end, if the given string doesn't have them yet.
	 * @param ignoreCase if {@code true}, then {@code ILIKE} is used as operator instead of {@code LIKE}
	 */
	IQueryBuilder<T> addStringLikeFilter(String columnname, String substring, boolean ignoreCase);

	IQueryBuilder<T> addCompareFilter(String columnName, Operator operator, Object value);

	IQueryBuilder<T> addCompareFilter(ModelColumn<T, ?> column, Operator operator, Object value);

	IQueryBuilder<T> addCompareFilter(String columnName, Operator operator, Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addCompareFilter(ModelColumn<T, ?> column, Operator operator, Object value, IQueryFilterModifier modifier);

	IQueryBuilder<T> addOnlyContextClient(Properties ctx);

	IQueryBuilder<T> addOnlyContextClient();

	IQueryBuilder<T> addOnlyContextClientOrSystem();

	IQueryBuilder<T> addOnlyActiveRecordsFilter();

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is accepted.
	 */
	@SuppressWarnings("unchecked")
	<V> IQueryBuilder<T> addInArrayOrAllFilter(String columnName, V... values);

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is rejected.
	 *
	 * Note that "InArray*Filters" also support {@link RepoIdAware} and {@link de.metas.util.lang.ReferenceListAwareEnum}
	 */
	@SuppressWarnings("unchecked")
	<V> IQueryBuilder<T> addInArrayFilter(String columnName, V... values);

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is accepted.
	 */
	@SuppressWarnings("unchecked")
	<V> IQueryBuilder<T> addInArrayOrAllFilter(ModelColumn<T, ?> column, V... values);

	/**
	 * Filters those rows for whom the columnName's value is in given array.
	 * If no values were provided the record is rejected.
	 *
	 * @param values the values to check again also supports {@code null} value among them.
	 */
	@SuppressWarnings("unchecked")
	<V> IQueryBuilder<T> addInArrayFilter(ModelColumn<T, ?> column, V... values);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is accepted.
	 */
	<V> IQueryBuilder<T> addInArrayOrAllFilter(String columnName, Collection<V> values);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is rejected.
	 * Note: also works with {@link RepoIdAware} values.
	 */
	<V> IQueryBuilder<T> addInArrayFilter(String columnName, Collection<V> values);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is accepted.
	 * Note: also works with {@link RepoIdAware} values.
	 */
	<V> IQueryBuilder<T> addInArrayOrAllFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * Filters those rows for whom the columnName's value is in given collection.
	 * If no values were provided the record is rejected.
	 * Note: also works with {@link RepoIdAware} values.
	 */
	<V> IQueryBuilder<T> addInArrayFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * Notes:
	 * <li>This filter <b>will not</b> match {@code null} column values.</li>
	 * <li>If {@code values} is empty, then this filter will return {@code true} (as intuitively expected).</li>
	 * <li>Also works with {@link RepoIdAware} values.</li>
	 */
	<V> IQueryBuilder<T> addNotInArrayFilter(ModelColumn<T, ?> column, Collection<V> values);

	/**
	 * NOTE: in case <code>values</code> collection is empty this filter will return <code>true</code> (as intuitively expected).
	 */
	<V> IQueryBuilder<T> addNotInArrayFilter(String columnName, Collection<V> values);

	IInSubQueryFilterClause<T, IQueryBuilder<T>> addInSubQueryFilter();

	<ST> IQueryBuilder<T> addInSubQueryFilter(String columnName, IQueryFilterModifier modifier, String subQueryColumnName, IQuery<ST> subQuery);

	/**
	 * @param columnName the key column from the "main" query
	 * @param subQueryColumnName the key column from the "sub" query
	 * @param subQuery the actual sub query
	 * @return this
	 */
	<ST> IQueryBuilder<T> addInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);

	<ST> IQueryBuilder<T> addNotInSubQueryFilter(String columnName, String subQueryColumnName, IQuery<ST> subQuery);

	/**
	 * @param column the key column from the "main" query
	 * @param subQueryColumn the key column from the "sub" query
	 * @param subQuery the actual sub query
	 * @return this
	 */
	<ST> IQueryBuilder<T> addInSubQueryFilter(ModelColumn<T, ?> column, ModelColumn<ST, ?> subQueryColumn, IQuery<ST> subQuery);

	<ST> IQueryBuilder<T> addNotInSubQueryFilter(ModelColumn<T, ?> column, ModelColumn<ST, ?> subQueryColumn, IQuery<ST> subQuery);

	/**
	 * Create a new {@link IQueryBuilder} which collects models from given model column.
	 * <p>
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
	 * @param column model column
	 * @return list of collected models
	 */
	<CollectedBaseType, ParentModelType> IQueryBuilder<CollectedBaseType> andCollect(ModelColumn<ParentModelType, CollectedBaseType> column);

	/**
	 * Same as {@link #andCollect(ModelColumn)} but you can specify what interface to use for returning values.
	 */
	<CollectedBaseType, CollectedType extends CollectedBaseType, ParentModelType> IQueryBuilder<CollectedType> andCollect(
			ModelColumn<ParentModelType, CollectedBaseType> column,
			Class<CollectedType> collectedType);

	<CollectedType> IQueryBuilder<CollectedType> andCollect(String columnName, Class<CollectedType> collectedType);

	/**
	 * Returns a query to retrieve those records that reference the result of the query which was specified so far.<br>
	 * Example: first, configure a query builder to select a certain kind of <code>M_InOuts</code>. then use this method to retrieve not the specified inOuts, but its M_InOutLines:
	 *
	 * <pre>
	 * final IQueryBuilder&lt;I_M_InOut&gt; inoutsQueryBuilder = ....;
	 *
	 * final List&lt;I_M_InOutLine&gt; inoutLines = inoutsQueryBuilder
	 *   .andCollectChildren(I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOutLine.class) // an IQueryBuilder&lt;I_M_InOutLine&gt; is returned here
	 *   .create() // create IQuery&lt;I_M_InOutLine&gt;
	 *   .list()   // list inout lines
	 * </pre>
	 *
	 * @param linkColumnNameInChildTable the column in child model which will be used to join the child records to current record's primary key
	 * @param childType child model to be used
	 * @return query build for <code>ChildType</code>
	 */
	<ChildType> IQueryBuilder<ChildType> andCollectChildren(String linkColumnNameInChildTable, Class<ChildType> childType);

	default <ChildType, ExtChildType extends ChildType> IQueryBuilder<ExtChildType> andCollectChildren(
			@NonNull final ModelColumn<ChildType, ?> linkColumnInChildTable,
			@NonNull final Class<ExtChildType> childType)
	{
		final String linkColumnNameInChildTable = linkColumnInChildTable.getColumnName();
		return andCollectChildren(linkColumnNameInChildTable, childType);
	}

	/**
	 * Returns a query to retrieve those records that reference the result of the query which was specified so far.<br>
	 * .
	 * <p>
	 * This is a convenient version of {@link #andCollectChildren(String, Class)} for the case when you don't have to retrieve an extended interface of the child type.
	 *
	 * @param linkColumnInChildTable the column in child model which will be used to join the child records to current record's primary key
	 * @return query build for <code>ChildType</code>
	 */
	default <ChildType> IQueryBuilder<ChildType> andCollectChildren(final ModelColumn<ChildType, ?> linkColumnInChildTable)
	{
		final String linkColumnNameInChildTable = linkColumnInChildTable.getColumnName();
		final Class<ChildType> childType = linkColumnInChildTable.getModelClass();
		return andCollectChildren(linkColumnNameInChildTable, childType);
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
	 * Will only return records that are referenced by a <code>T_Selection</code> records which has the given selection ID.
	 */
	IQueryBuilder<T> setOnlySelection(PInstanceId pinstanceId);

	/**
	 * Start an aggregation of different columns, everything grouped by given <code>column</code>
	 *
	 * @return aggregation builder
	 */
	<TargetModelType> IQueryAggregateBuilder<T, TargetModelType> aggregateOnColumn(ModelColumn<T, TargetModelType> column);

	<TargetModelType> IQueryAggregateBuilder<T, TargetModelType> aggregateOnColumn(String collectOnColumnName, Class<TargetModelType> targetModelType);

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

	String getModelTableName();
}
