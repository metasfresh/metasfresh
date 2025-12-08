package org.adempiere.ad.dao;

import de.metas.process.PInstanceId;
import lombok.NonNull;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;

import java.util.Properties;

/**
 * @param <T> model type
 * @author tsa
 */
@SuppressWarnings("UnusedReturnValue")
public interface IQueryBuilder<T>
		extends
		IQueryBuilderExecutors<T>,
		ICompositeQueryFilterProxy<T, IQueryBuilder<T>>,
		IQueryBuilderOrderBys<T>
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
	IQueryBuilder<T> filter(@NonNull IQueryFilter<T> filter);

	@Override
	default IQueryBuilder<T> addFilter(@NonNull IQueryFilter<T> filter) {return filter(filter);}

	@Override
	String getModelTableName();

	@Deprecated
	IQueryBuilder<T> filterByClientId();

	ICompositeQueryFilter<T> getCompositeFilter();

	IQueryBuilder<T> setLimit(QueryLimit limit);

	@Deprecated
	default IQueryBuilder<T> setLimit(final int limit) {return setLimit(QueryLimit.ofInt(limit));}

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

	@Override
	IQueryBuilderOrderByClause<T> orderBy();

	@Override
	IQuery<T> create();

	@Deprecated
	IQueryBuilder<T> addOnlyContextClient();

	@Deprecated
	IQueryBuilder<T> addOnlyContextClientOrSystem();

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
	 * @param childType                  child model to be used
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
}
