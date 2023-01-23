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

import com.google.common.collect.ImmutableMap;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilterBase;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderDAO;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* package */class QueryBuilder<T> implements IQueryBuilder<T>
{
	private final Class<T> modelClass;
	private final String modelTableName;
	private String modelKeyColumnName; // lazy

	private Properties ctx;
	private String trxName;

	private QueryBuilderOrderByClause<T> orderByBuilder;
	private final CompositeQueryFilter<T> filters;

	private PInstanceId onlySelectionId;
	private QueryLimit limit = QueryLimit.NO_LIMIT;

	private Map<String, Object> options = null;

	/**
	 * we need a local instance, because we want to set its {@code ctx} when our {@link #setContext(Object)} or {@link #setContext(Properties, String)} method is called.
	 */
	private ContextClientQueryFilter<T> contextClientQueryFilter;

	public static QueryBuilder<Object> createForTableName(final String modelTableName)
	{
		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		return new QueryBuilder<>(Object.class, modelTableName);
	}

	/**
	 * Note: we don't provide two constructors (one without <code>tableName</code> param), because instances of this class are generally created by {@link IQueryBuilder} and that's how a user should obtain them.
	 * Exceptions might be some test cases, but there is think that a developer can carry the border of explicitly giving a <code>null</code> parameter. On the upside, we don't have multiple different constructors to choose from.
	 *
	 * @param tableName may be <code>null</code>. If <code>null</code>, then the table's name will be deducted from the given modelClass when it is required.
	 */
	public QueryBuilder(final Class<T> modelClass, final String tableName)
	{
		this.modelClass = modelClass;
		this.modelKeyColumnName = null; // lazy

		this.modelTableName = InterfaceWrapperHelper.getTableName(modelClass, tableName);
		this.filters = new CompositeQueryFilter<>(this.modelTableName);
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
		this.contextClientQueryFilter = from.contextClientQueryFilter != null ? from.contextClientQueryFilter.copy() : null;

		this.options = from.options == null ? null : new HashMap<>(from.options);

		this.contextClientQueryFilter = (ContextClientQueryFilter<T>)this.filters.getFilters()
				.stream()
				.filter(ContextClientQueryFilter.class::isInstance)
				.map(ContextClientQueryFilter.class::cast)
				.findAny()
				.orElse(null);
	}

	/**
	 * @implNote toString() which includes the generated SQL, convenient for debugging and hovering on variables
	 */
	@Override
	public String toString()
	{
		return create().toString(); // shows the generated SQL
	}

	@Override
	public IQueryBuilder<T> copy()
	{
		return new QueryBuilder<>(this);
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
	public IQueryBuilder<T> filter(final @NonNull IQueryFilter<T> filter)
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
	@Deprecated
	public IQueryBuilder<T> filterByClientId()
	{
		if (contextClientQueryFilter != null)
		{
			// nothing else to do
			return this;
		}

		return filter(new ContextClientQueryFilter<>(ctx));
	}

	@Override
	public ICompositeQueryFilterBase<T> compositeFiltersBase()
	{
		return filters;
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
	public IQueryBuilder<T> self()
	{
		return this;
	}

	@Override
	public String getModelTableName()
	{
		return modelTableName;
	}

	private String getKeyColumnName()
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
	@Deprecated
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
}
