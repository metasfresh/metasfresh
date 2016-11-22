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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderDAO;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import com.google.common.collect.ImmutableMap;

/* package */class QueryBuilder<T> implements IQueryBuilder<T>
{
	public static final QueryBuilder<Object> createForTableName(final String modelTableName)
	{
		Check.assumeNotEmpty(modelTableName, "modelTableName is not empty");
		return new QueryBuilder<>(modelTableName, Object.class);
	}
	
	private final Class<T> modelClass;
	private final String modelTableName;
	private String modelKeyColumnName; // lazy

	private Properties ctx;
	private String trxName;

	private QueryBuilderOrderByClause<T> orderByBuilder;
	private final ICompositeQueryFilter<T> filters;

	private int onlySelection_ID = -1;
	private int limit = IQuery.NO_LIMIT;

	private Map<String, Object> options = null;

	/**
	 * we need a local instance, because we want to set its {@code ctx} when our {@link #setContext(Object)} or {@link #setContext(Properties, String)} method is called.
	 */
	private ContextClientQueryFilter<T> contextClientQueryFilter;

	public QueryBuilder(final Class<T> modelClass)
	{
		this(InterfaceWrapperHelper.getTableName(modelClass), modelClass);
	}
	
	private QueryBuilder(final String modelTableName, final Class<T> modelClass)
	{
		super();
		
		this.modelClass = modelClass;
		this.modelTableName = modelTableName;
		this.modelKeyColumnName = null; // lazy

		filters = new CompositeQueryFilter<>(modelTableName);
	}



	private QueryBuilder(final QueryBuilder<T> from)
	{
		super();
		this.modelClass = from.modelClass;
		this.modelTableName = from.modelTableName;
		this.modelKeyColumnName = from.modelKeyColumnName;
		this.ctx = from.ctx;
		this.trxName = from.trxName;
		this.filters = from.filters.copy();
		this.orderByBuilder = from.orderByBuilder == null ? null : from.orderByBuilder.copy();
		this.onlySelection_ID = from.onlySelection_ID;
		this.limit = from.limit;

		this.options = from.options == null ? null : new HashMap<>(from.options);
	}

	@Override
	public IQueryBuilder<T> copy()
	{
		final QueryBuilder<T> copy = new QueryBuilder<T>(this);
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
	public ICompositeQueryFilter<T> getFilters()
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
			orderByBuilder = new QueryBuilderOrderByClause<T>(this);
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

	/* package */ String getModelTableName()
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
	public IQueryBuilder<T> setOnlySelection(final int AD_PInstance_ID)
	{
		this.onlySelection_ID = AD_PInstance_ID;
		return this;
	}

	public int getSelectionId()
	{
		return onlySelection_ID;
	}

	@Override
	public int getLimit()
	{
		return limit;
	}

	@Override
	public IQueryBuilder<T> setLimit(final int limit)
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
	public <ST> IQueryBuilder<T> addInSubQueryFilter(final String columnName, final IQueryFilterModifier modifier, final String subQueryColumnName, final IQuery<ST> subQuery)
	{
		filters.addInSubQueryFilter(columnName, modifier, subQueryColumnName, subQuery);
		return this;
	}

	@Override
	public <V> IQueryBuilder<T> addInArrayFilter(final String columnName, final Collection<V> values)
	{
		filters.addInArrayFilter(columnName, values);
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
	public <V> IQueryBuilder<T> addInArrayFilter(final String columnName, final V... values)
	{
		filters.addInArrayFilter(columnName, values);
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
	public IQueryBuilder<T> addEqualsFilter(final String columnName, final Object value)
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
	public IQueryBuilder<T> addSubstringFilter(final String columnname, final String substring, final boolean ignoreCase)
	{
		filters.addSubstringFilter(columnname, substring, ignoreCase);
		return this;
	}

	@Override
	public IQueryBuilder<T> addSubstringFilter(final ModelColumn<T, ?> column, final String substring, final boolean ignoreCase)
	{
		final String columnName = column.getColumnName();
		return addSubstringFilter(columnName, substring, ignoreCase);
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
		final Class<CollectedType> fieldType = column.getColumnModelType();
		return andCollect(column, fieldType);
	}

	@Override
	public <CollectedBaseType, CollectedType extends CollectedBaseType, ParentModelType> IQueryBuilder<CollectedType> andCollect(final ModelColumn<ParentModelType, CollectedBaseType> column,
			Class<CollectedType> collectedType)
	{
		final IQuery<T> query = create();

		String tableName = null;

		// Get TableName from collectedType (if any)
		if (tableName == null && collectedType != null)
		{
			tableName = InterfaceWrapperHelper.getTableNameOrNull(collectedType);
		}
		// Get tableName from model column definition
		if (tableName == null)
		{
			final Class<CollectedBaseType> fieldType = column.getColumnModelType();
			tableName = InterfaceWrapperHelper.getTableName(fieldType);
		}
		Check.assumeNotEmpty(tableName, "TableName not found for column={} and collectedType={}", column, collectedType);

		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final String columnName = column.getColumnName();

		return new QueryBuilder<CollectedType>(collectedType)
				.setContext(ctx, trxName)
				.addInSubQueryFilter(keyColumnName, columnName, query);
	}

	@Override
	public <ChildType, ExtChildType extends ChildType> IQueryBuilder<ExtChildType> andCollectChildren(
			final ModelColumn<ChildType, ?> childTableColumn,
			final Class<ExtChildType> childType)
	{
		final String childTableColumnName = childTableColumn.getColumnName();
		final String thisIDColumnName = getKeyColumnName();
		final IQuery<T> thisQuery = create();

		return new QueryBuilder<ExtChildType>(childType)
				.setContext(ctx, trxName)
				.addInSubQueryFilter(childTableColumnName, thisIDColumnName, thisQuery);
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
		return new QueryAggregateBuilder<T, TargetModelType>(this, column);
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
		final ICompositeQueryFilter<T> filter = new CompositeQueryFilter<>(getModelTableName());
		filters.addFilter(filter);
		return filter;
	}

	@Override
	public IQueryBuilder<T> addValidFromToMatchesFilter(final ModelColumn<T, ?> validFromColumn, final ModelColumn<T, ?> validToColumn, final Date dateToMatch)
	{
		filters.addValidFromToMatchesFilter(validFromColumn, validToColumn, dateToMatch);
		return this;
	}

}
