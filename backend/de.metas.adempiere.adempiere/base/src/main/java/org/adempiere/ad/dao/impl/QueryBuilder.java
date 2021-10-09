package org.adempiere.ad.dao.impl;

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

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IInSubQueryFilterClause;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderDAO;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;

import com.google.common.collect.ImmutableMap;

import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/* package */class QueryBuilder<T> implements IQueryBuilder<T>
{
	private final Class<T> modelClass;
	private final String modelTableName;
	private String modelKeyColumnName; // lazy

	private Properties ctx;
	private String trxName;

	private QueryBuilderOrderByClause<T> orderByBuilder;
	private final ICompositeQueryFilter<T> filters;

	private PInstanceId onlySelectionId;
	private QueryLimit limit = QueryLimit.NO_LIMIT;

	private Map<String, Object> options = null;

	/**
	 * we need a local instance, because we want to set its {@code ctx} when our {@link #setContext(Object)} or {@link #setContext(Properties, String)} method is called.
	 */
	private ContextClientQueryFilter<T> contextClientQueryFilter;

	public static final QueryBuilder<Object> createForTableName(final String modelTableName)
	{
		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		return new QueryBuilder<>(Object.class, modelTableName);
	}

	/**
	 * Note: we don't provide two constructors (one without <code>tableName</code> param), because instances of this class are generally created by {@link IQueryBuilder} and that's how a user should obtain them.
	 * Exceptions might be some test cases, but there is think that a developer can carry the border of explicitly giving a <code>null</code> parameter. On the upside, we don't have multiple different constructors to choose from.
	 *
	 * @param modelClass
	 * @param tableName may be <code>null</code>. If <code>null</code>, then the table's name will be deducted from the given modelClass when it is required.
	 */
	public QueryBuilder(final Class<T> modelClass, final String tableName)
	{
		this.modelClass = modelClass;
		this.modelKeyColumnName = null; // lazy

		this.modelTableName = InterfaceWrapperHelper.getTableName(modelClass, tableName);

		final IQueryBL factory = Services.get(IQueryBL.class);
		filters = factory.createCompositeQueryFilter(this.modelTableName); // always use the tableName we just fetched because it might be that modelClass is not providing a tableName.
	}

	private QueryBuilder(final QueryBuilder<T> from)
	{
		this.modelClass = from.modelClass;
		this.modelTableName = from.modelTableName;
		this.modelKeyColumnName = from.modelKeyColumnName;
		this.ctx = from.ctx;
		this.trxName = from.trxName;
		this.filters = from.filters.copy();
		this.orderByBuilder = from.orderByBuilder == null ? null : from.orderByBuilder.copy();
		this.onlySelectionId = from.onlySelectionId;
		this.limit = from.limit;

		this.options = from.options == null ? null : new HashMap<>(from.options);
	}

	@Override
	public IQueryBuilder<T> copy()
	{
		final QueryBuilder<T> copy = new QueryBuilder<>(this);
		return copy;
	}

	/**
	 * Note: the given {@code ctx} is also propagated to this instance's client ID filter (if any)
	 *
	 * @see #filterByClientId()
	 */
	public IQueryBuilder<T> setContext(final Properties ctx, final String trxName)
	{
		this.ctx = ctx;
		if (contextClientQueryFilter != null)
		{
			contextClientQueryFilter.setContext(ctx);
		}

		this.trxName = trxName;
		return this;
	}

	/**
	 * Note: the given {@code contextProvider}'s context is also propagated to this instance's client ID filter (if any)
	 *
	 * @see #filterByClientId()
	 */
	public IQueryBuilder<T> setContext(final Object contextProvider)
	{
		this.ctx = InterfaceWrapperHelper.getCtx(contextProvider);
		if (contextClientQueryFilter != null)
		{
			contextClientQueryFilter.setContext(ctx);
		}

		this.trxName = InterfaceWrapperHelper.getTrxName(contextProvider);
		return this;
	}

	@Override
	public IQueryBuilder<T> filter(final IQueryFilter<T> filter)
	{

		if (filter instanceof ContextClientQueryFilter)
		{
			// we add the contextClientQueryFilter only once
			if (contextClientQueryFilter == null)
			{
				contextClientQueryFilter = (ContextClientQueryFilter<T>)filter;
				this.filters.addFilter(filter);
			}
		}
		else
		{
			this.filters.addFilter(filter);
		}

		return this;
	}

	@Override
	public IQueryBuilder<T> addFiltersUnboxed(final ICompositeQueryFilter<T> compositeFilter)
	{
		filters.addFiltersUnboxed(compositeFilter);
		return this;
	}

	@Override
	public ICompositeQueryFilter<T> getCompositeFilter()
	{
		return filters;
	}

	@Override
	public IQueryBuilder<T> filterByClientId()
	{
		if (contextClientQueryFilter != null)
		{
			// nothing else to do
			return this;
		}

		return filter(new ContextClientQueryFilter<T>(ctx));
	}

	@Override
	public QueryBuilderOrderByClause<T> orderBy()
	{
		if (orderByBuilder == null)
		{
			orderByBuilder = new QueryBuilderOrderByClause<>(this);
		}
		return orderByBuilder;
	}

	public IQueryOrderBy createQueryOrderBy()
	{
		if (orderByBuilder == null)
		{
			return null;
		}
		else
		{
			return orderByBuilder.createQueryOrderBy();
		}
	}

	@Override
	public IQuery<T> create()
	{
		return Services.get(IQueryBuilderDAO.class).create(this);
	}

	@Override
	public Class<T> getModelClass()
	{
		return modelClass;
	}

	@Override
	public String getModelTableName()
	{
		return modelTableName;
	}

	private final String getKeyColumnName()
	{
		if (modelKeyColumnName == null)
		{
			modelKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(getModelTableName());
		}
		return modelKeyColumnName;
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}

	public IQueryFilter<T> createFilter()
	{
		return filters;
	}

	@Override
	public IQueryBuilder<T> setOnlySelection(final PInstanceId pinstanceId)
	{
		this.onlySelectionId = pinstanceId;
		return this;
	}

	public PInstanceId getSelectionId()
	{
		return onlySelectionId;
	}

	@Override
	public QueryLimit getLimit()
	{
		return limit;
	}

	@Override
	public IQueryBuilder<T> setLimit(@NonNull final QueryLimit limit)
	{
		this.limit = limit;
		return this;
	}

	@Override
	public final QueryBuilder<T> setOption(final String name, final Object value)
	{
		Check.assumeNotEmpty(name, "name not empty");
		if (options == null)
		{
			if (value == null)
			{
				return this;
			}

			options = new HashMap<>();
		}

		if (value == null)
		{
			options.remove(name);
		}
		else
		{
			options.put(name, value);
		}

		return this;
	}

	@Override
	public IQueryBuilder<T> setOption(final String name)
	{
		setOption(name, true);
		return this;
	}

	public final Map<String, Object> getOptions()
	{
		if (options == null)
		{
			return ImmutableMap.of();
		}
		return ImmutableMap.copyOf(options);
	}

	@Override
	public <ST> IQueryBuilder<T> addInSubQueryFilter(final String columnName, final String subQueryColumnName, final IQuery<ST> subQuery)
	{
		filters.addInSubQueryFilter(columnName, subQueryColumnName, subQuery);
		return this;
	}

	@Override
	public <ST> IQueryBuilder<T> addNotInSubQueryFilter(final String columnName, final String subQueryColumnName, final IQuery<ST> subQuery)
	{
		filters.addNotInSubQueryFilter(columnName, subQueryColumnName, subQuery);
		return this;
	}

	@Override
	public <ST> IQueryBuilder<T> addInSubQueryFilter(final ModelColumn<T, ?> column, final ModelColumn<ST, ?> subQueryColumn, final IQuery<ST> subQuery)
	{
		final String columnName = column.getColumnName();
		final String subQueryColumnName = subQueryColumn.getColumnName();
		return addInSubQueryFilter(columnName, subQueryColumnName, subQuery);
	}

	@Override
	public <ST> IQueryBuilder<T> addNotInSubQueryFilter(final ModelColumn<T, ?> column, final ModelColumn<ST, ?> subQueryColumn, final IQuery<ST> subQuery)
	{
		filters.addNotInSubQueryFilter(column, subQueryColumn, subQuery);
		return this;
	}

	@Override
	public IInSubQueryFilterClause<T, IQueryBuilder<T>> addInSubQueryFilter()
	{
		return new InSubQueryFilterClause<>(getModelTableName(), this, this::filter);
	}

	@Override
	public <ST> IQueryBuilder<T> addInSubQueryFilter(final String columnName, final IQueryFilterModifier modifier, final String subQueryColumnName, final IQuery<ST> subQuery)
	{
		filters.addInSubQueryFilter(columnName, modifier, subQueryColumnName, subQuery);
		return this;
	}

	@Override
	public <V> IQueryBuilder<T> addInArrayOrAllFilter(final String columnName, final Collection<V> values)
	{
		filters.addInArrayOrAllFilter(columnName, values);
		return this;
	}

	@Override
	public <V> IQueryBuilder<T> addInArrayFilter(final String columnName, final Collection<V> values)
	{
		filters.addInArrayFilter(columnName, values);
		return this;
	}

	@Override
	public <V> IQueryBuilder<T> addInArrayOrAllFilter(final ModelColumn<T, ?> column, final Collection<V> values)
	{
		filters.addInArrayOrAllFilter(column, values);
		return this;
	}

	@Override
	public <V> IQueryBuilder<T> addInArrayFilter(final ModelColumn<T, ?> column, final Collection<V> values)
	{
		filters.addInArrayFilter(column, values);
		return this;
	}

	@Override
	public <V> IQueryBuilder<T> addNotInArrayFilter(ModelColumn<T, ?> column, Collection<V> values)
	{
		filters.addNotInArrayFilter(column, values);
		return this;
	}

	@Override
	public <V> IQueryBuilder<T> addNotInArrayFilter(String columnName, Collection<V> values)
	{
		filters.addNotInArrayFilter(columnName, values);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> IQueryBuilder<T> addInArrayOrAllFilter(final String columnName, final V... values)
	{
		filters.addInArrayOrAllFilter(columnName, values);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> IQueryBuilder<T> addInArrayFilter(final String columnName, final V... values)
	{
		filters.addInArrayFilter(columnName, values);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> IQueryBuilder<T> addInArrayOrAllFilter(final ModelColumn<T, ?> column, final V... values)
	{
		filters.addInArrayOrAllFilter(column, values);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> IQueryBuilder<T> addInArrayFilter(final ModelColumn<T, ?> column, final V... values)
	{
		filters.addInArrayFilter(column, values);
		return this;
	}

	@Override
	public IQueryBuilder<T> addOnlyActiveRecordsFilter()
	{
		filters.addOnlyActiveRecordsFilter();
		return this;
	}

	@Override
	public IQueryBuilder<T> addOnlyContextClient(final Properties ctx)
	{
		filters.addOnlyContextClient(ctx);
		return this;
	}

	@Override
	public IQueryBuilder<T> addOnlyContextClient()
	{
		final Properties ctx = getCtx();
		filters.addOnlyContextClient(ctx);
		return this;
	}

	@Override
	public IQueryBuilder<T> addOnlyContextClientOrSystem()
	{
		final Properties ctx = getCtx();
		filters.addOnlyContextClientOrSystem(ctx);
		return this;
	}

	@Override
	public IQueryBuilder<T> addCompareFilter(final String columnName, final Operator operator, final Object value)
	{
		filters.addCompareFilter(columnName, operator, value);
		return this;
	}

	@Override
	public IQueryBuilder<T> addCompareFilter(final ModelColumn<T, ?> column, final Operator operator, final Object value)
	{
		filters.addCompareFilter(column, operator, value);
		return this;
	}

	@Override
	public IQueryBuilder<T> addCompareFilter(final String columnName, final Operator operator, final Object value, final IQueryFilterModifier modifier)
	{
		filters.addCompareFilter(columnName, operator, value, modifier);
		return this;
	}

	@Override
	public IQueryBuilder<T> addCompareFilter(final ModelColumn<T, ?> column, final Operator operator, final Object value, final IQueryFilterModifier modifier)
	{
		filters.addCompareFilter(column, operator, value, modifier);
		return this;
	}

	@Override
	public IQueryBuilder<T> addEqualsFilter(final String columnName, @Nullable final Object value)
	{
		filters.addEqualsFilter(columnName, value);
		return this;
	}

	@Override
	public IQueryBuilder<T> addEqualsFilter(final ModelColumn<T, ?> column, final Object value)
	{
		filters.addEqualsFilter(column, value);
		return this;
	}

	@Override
	public IQueryBuilder<T> addEqualsFilter(final String columnName, final Object value, final IQueryFilterModifier modifier)
	{
		filters.addEqualsFilter(columnName, value, modifier);
		return this;
	}

	@Override
	public IQueryBuilder<T> addEqualsFilter(final ModelColumn<T, ?> column, final Object value, final IQueryFilterModifier modifier)
	{
		filters.addEqualsFilter(column, value, modifier);
		return this;
	}

	@Override
	public IQueryBuilder<T> addStringLikeFilter(final String columnname, final String substring, final boolean ignoreCase)
	{
		filters.addStringLikeFilter(columnname, substring, ignoreCase);
		return this;
	}

	@Override
	public IQueryBuilder<T> addCoalesceEqualsFilter(final Object value, final String... columnNames)
	{
		filters.addCoalesceEqualsFilter(value, columnNames);
		return this;
	}

	@Override
	public IQueryBuilder<T> addNotEqualsFilter(final String columnName, final Object value)
	{
		filters.addNotEqualsFilter(columnName, value);
		return this;
	}

	@Override
	public IQueryBuilder<T> addNotEqualsFilter(final ModelColumn<T, ?> column, final Object value)
	{
		filters.addNotEqualsFilter(column, value);
		return this;
	}

	@Override
	public IQueryBuilder<T> addNotNull(final String columnName)
	{
		filters.addNotNull(columnName);
		return this;
	}

	@Override
	public IQueryBuilder<T> addNotNull(final ModelColumn<T, ?> column)
	{
		filters.addNotNull(column);
		return this;
	}

	@Override
	public <CollectedType, ParentModelType> IQueryBuilder<CollectedType> andCollect(final ModelColumn<ParentModelType, CollectedType> column)
	{
		return andCollect(column.getColumnName(), column.getColumnModelType());
	}

	@Override
	public <CollectedBaseType, CollectedType extends CollectedBaseType, ParentModelType> IQueryBuilder<CollectedType> andCollect(
			@NonNull final ModelColumn<ParentModelType, CollectedBaseType> column,
			@NonNull final Class<CollectedType> collectedType)
	{
		return andCollect(column.getColumnName(), collectedType);
	}

	@Override
	public <CollectedType> IQueryBuilder<CollectedType> andCollect(
			@NonNull final String columnName,
			@NonNull Class<CollectedType> collectedType)
	{
		final IQuery<T> query = create();

		String tableName = InterfaceWrapperHelper.getTableNameOrNull(collectedType);
		Check.assumeNotEmpty(tableName, "TableName not found for column={} and collectedType={}", columnName, collectedType);

		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);

		return new QueryBuilder<>(collectedType, null) // tableName=null
				.setContext(ctx, trxName)
				.addInSubQueryFilter(keyColumnName, columnName, query);
	}

	@Override
	public <ChildType> IQueryBuilder<ChildType> andCollectChildren(
			@NonNull final String linkColumnNameInChildTable,
			@NonNull final Class<ChildType> childType)
	{
		final String thisIDColumnName = getKeyColumnName();
		final IQuery<T> thisQuery = create();

		return new QueryBuilder<>(childType, null) // tableName=null
				.setContext(ctx, trxName)
				.addInSubQueryFilter(linkColumnNameInChildTable, thisIDColumnName, thisQuery);
	}

	@Override
	public IQueryBuilder<T> setJoinOr()
	{
		filters.setJoinOr();
		return this;
	}

	@Override
	public IQueryBuilder<T> setJoinAnd()
	{
		filters.setJoinAnd();
		return this;
	}

	@Override
	public <TargetModelType> QueryAggregateBuilder<T, TargetModelType> aggregateOnColumn(final ModelColumn<T, TargetModelType> column)
	{
		return aggregateOnColumn(column.getColumnName(), column.getColumnModelType());
	}

	@Override
	public <TargetModelType> QueryAggregateBuilder<T, TargetModelType> aggregateOnColumn(final String collectOnColumnName, final Class<TargetModelType> targetModelType)
	{
		return new QueryAggregateBuilder<>(
				this,
				collectOnColumnName,
				targetModelType);
	}

	@Override
	public IQueryBuilder<T> addBetweenFilter(ModelColumn<T, ?> column, Object valueFrom, Object valueTo, IQueryFilterModifier modifier)
	{
		filters.addBetweenFilter(column, valueFrom, valueTo, modifier);
		return this;
	}

	@Override
	public IQueryBuilder<T> addBetweenFilter(String columnName, Object valueFrom, Object valueTo, IQueryFilterModifier modifier)
	{
		filters.addBetweenFilter(columnName, valueFrom, valueTo, modifier);
		return this;
	}

	@Override
	public IQueryBuilder<T> addBetweenFilter(ModelColumn<T, ?> column, Object valueFrom, Object valueTo)
	{
		filters.addBetweenFilter(column, valueFrom, valueTo);
		return this;
	}

	@Override
	public IQueryBuilder<T> addBetweenFilter(String columnName, Object valueFrom, Object valueTo)
	{
		filters.addBetweenFilter(columnName, valueFrom, valueTo);
		return this;
	}

	@Override
	public IQueryBuilder<T> addEndsWithQueryFilter(String columnName, String endsWithString)
	{
		filters.addEndsWithQueryFilter(columnName, endsWithString);
		return this;
	}

	@Override
	public ICompositeQueryFilter<T> addCompositeQueryFilter()
	{
		return filters.addCompositeQueryFilter();
	}

	@Override
	public IQueryBuilder<T> addValidFromToMatchesFilter(final ModelColumn<T, ?> validFromColumn, final ModelColumn<T, ?> validToColumn, final Date dateToMatch)
	{
		filters.addValidFromToMatchesFilter(validFromColumn, validToColumn, dateToMatch);
		return this;
	}
}
