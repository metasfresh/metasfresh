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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryInsertExecutor.QueryInsertExecutorResult;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

public class POJOQuery<T> extends AbstractTypedQuery<T>
{
	private final Properties ctx;
	private final String trxName;
	private final Class<T> modelClass;
	private final String tableName;
	private final ICompositeQueryFilter<T> filters;
	private POJOInSelectionQueryFilter<T> filter_inSelection = null;
	private POJOInSelectionQueryFilter<T> filter_notInSelection = null;
	private IQueryOrderBy orderBy;
	private Map<String, Object> options = null;
	private int limit = NO_LIMIT;
	private int offset = NO_LIMIT;

	private List<SqlQueryUnion<T>> unions;

	@Deprecated
	public POJOQuery(final Class<T> modelClass)
	{
		this(Env.getCtx(), modelClass, null, ITrx.TRXNAME_None);
	}

	public POJOQuery(final Properties ctx, final Class<T> modelClass, final String tablename, final String trxName)
	{
		super();

		Check.assumeNotNull(modelClass, "modelClass not null");
		this.modelClass = modelClass;
		this.ctx = ctx;
		this.trxName = trxName;

		final IQueryBL factory = Services.get(IQueryBL.class);

		if (Check.isEmpty(tablename, true))
		{
			// make sure early if modelClass is valid
			this.tableName = InterfaceWrapperHelper.getTableName(modelClass);
			filters = factory.createCompositeQueryFilter(modelClass);
		}
		else
		{
			this.tableName = tablename;
			filters = factory.createCompositeQueryFilter(tableName);
		}
	}

	@Override
	public String toString()
	{
		return "POJOQuery ["
				+ "modelClass=" + modelClass
				+ ", filters=" + filters
				+ ", orderBy=" + orderBy
				+ ", trxName=" + trxName
				+ "]";
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

	private <ET> String getTableNameToUse(final Class<ET> clazz)
	{
		final String clazzTableName = InterfaceWrapperHelper.getTableNameOrNull(clazz);
		if (clazzTableName == null)
		{
			return this.tableName;
		}

		if (!clazzTableName.equals(tableName))
		{
			throw new AdempiereException("Cannot use class " + clazz
					+ " (TableName=" + clazzTableName + ")"
					+ " in a query for table " + tableName);
		}

		return tableName;
	}

	public POJOQuery<T> addFilter(final IQueryFilter<T> filter)
	{
		filters.addFilter(filter);
		return this;
	}

	public POJOQuery<T> removeFilter(final IQueryFilter<T> filter)
	{
		filters.removeFilter(filter);
		return this;
	}

	@Override
	public final Class<T> getModelClass()
	{
		return modelClass;
	}

	@Override
	public String getTableName()
	{
		return InterfaceWrapperHelper.getTableName(modelClass);
	}

	@Override
	public POJOQuery<T> copy()
	{
		final POJOQuery<T> queryNew = new POJOQuery<T>(modelClass);
		queryNew.filters.addFilters(filters.getFilters());
		queryNew.orderBy = this.orderBy;
		queryNew.options = this.options == null ? null : new HashMap<>(this.options);

		queryNew.unions = unions == null ? null : new ArrayList<>(unions);

		return queryNew;
	}

	/**
	 * @return the orderByComparator
	 */
	private Comparator<Object> getOrderByComparator()
	{
		if (orderBy == null)
		{
			return null;
		}
		return orderBy.getComparator();
	}

	private <ET> Comparator<ET> getOrderByComparator(final Class<ET> clazz)
	{
		final Comparator<Object> orderByComparator = getOrderByComparator();
		if (orderByComparator == null)
		{
			return null;
		}

		return new Comparator<ET>()
		{

			@Override
			public int compare(final ET o1, final ET o2)
			{
				return orderByComparator.compare(o1, o2);
			}
		};
	}

	@Override
	public POJOQuery<T> setOrderBy(final IQueryOrderBy orderBy)
	{
		this.orderBy = orderBy;
		return this;
	}

	@Override
	public List<T> list() throws DBException
	{
		return list(modelClass);
	}

	@Override
	public <ET extends T> List<ET> list(final Class<ET> clazz) throws DBException
	{
		if (limit > 0 && offset > 0)
		{
			throw new UnsupportedOperationException("Using offset option is not implemented");
		}

		final POJOLookupMap db = POJOLookupMap.get();
		final String tableName = getTableNameToUse(clazz);

		final List<T> result = db.getRecords(tableName, modelClass, filters, getOrderByComparator(modelClass), trxName);
		if (result == null || result.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<ET> resultCasted = new ArrayList<ET>(result.size());
		for (final T model : result)
		{
			if (limit > 0 && resultCasted.size() >= limit)
			{
				break;
			}

			final ET modelCasted = InterfaceWrapperHelper.create(model, clazz);
			resultCasted.add(modelCasted);
		}

		//
		// If we have UNION queries, execute those too and merge the results here:
		if (unions != null && !unions.isEmpty())
		{
			for (final SqlQueryUnion<T> union : unions)
			{
				final IQuery<T> unionQuery = union.getQuery();
				final List<ET> unionQueryResult = unionQuery.list(clazz);

				mergeModelLists(resultCasted, unionQueryResult, union.isDistinct());
			}
		}

		return resultCasted;
	}

	private static final <T> void mergeModelLists(final List<T> to, final List<T> from, final boolean distinct)
	{
		// Case: from list is empty => nothing to do
		if (from == null || from.isEmpty())
		{
			return;
		}

		// Case: we were not asked to check for distinct values => merge as is
		if (!distinct)
		{
			to.addAll(from);
			return;
		}

		//
		// Create a set of existing keys (to speed up searching)
		final Set<Integer> existingKeys = new HashSet<>();
		for (final T toModel : to)
		{
			final int toModelId = InterfaceWrapperHelper.getId(toModel);
			existingKeys.add(toModelId);
		}

		//
		// Iterate the "from" list and add elements which does not exist (compared by ID)
		for (final T fromModel : from)
		{
			final int fromModelId = InterfaceWrapperHelper.getId(fromModel);
			if (!existingKeys.add(fromModelId))
			{
				// model already exists in "to" list => skip it
				continue;
			}

			to.add(fromModel);
		}
	}

	private final void assertNoUnionQueries()
	{
		Check.assume(unions == null || unions.isEmpty(), "UNIONs shall be empty because they are not supported in this case");
	}

	@Override
	public <ET extends T> ET first() throws DBException
	{
		@SuppressWarnings("unchecked")
		final ET result = (ET)first(modelClass);
		return result;
	}

	@Override
	public <ET extends T> ET first(final Class<ET> clazz) throws DBException
	{
		assertNoUnionQueries();

		final POJOLookupMap db = POJOLookupMap.get();
		final String tableName = getTableNameToUse(clazz);

		final T result = db.getFirst(tableName, modelClass, filters, getOrderByComparator(modelClass), trxName);
		if (result == null)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(result, clazz);
	}

	@Override
	public int firstId()
	{
		final T result = first(modelClass);
		if (result == null)
		{
			return -1;
		}

		final int id = InterfaceWrapperHelper.getId(result);
		return id;
	}

	@Override
	public int firstIdOnly() throws DBException
	{
		final T result = firstOnly(modelClass);
		if (result == null)
		{
			return -1;
		}

		final int id = InterfaceWrapperHelper.getId(result);
		return id;
	}

	@Override
	protected <ET extends T> ET firstOnly(final Class<ET> clazz, final boolean throwExIfMoreThenOneFound) throws DBException
	{
		assertNoUnionQueries();

		final POJOLookupMap db = POJOLookupMap.get();
		final String tableName = getTableNameToUse(clazz);

		final T result = db.getFirstOnly(tableName, modelClass, filters, getOrderByComparator(modelClass), throwExIfMoreThenOneFound, trxName);
		if (result == null)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(result, clazz);
	}

	@Override
	public int count() throws DBException
	{
		assertNoUnionQueries();

		final POJOLookupMap db = POJOLookupMap.get();

		return db.getRecords(modelClass, filters).size();
	}

	@Override
	public <ET extends T> Iterator<ET> iterate(final Class<ET> clazz) throws DBException
	{
		final boolean guaranteed;

		final Boolean guaranteedIteratorRequired = getOption(OPTION_GuaranteedIteratorRequired);
		if (guaranteedIteratorRequired != null)
		{
			guaranteed = guaranteedIteratorRequired.booleanValue();
		}
		else
		{
			guaranteed = DEFAULT_OPTION_GuaranteedIteratorRequired;
		}
		return iterate(clazz, guaranteed);
	}

	public <ET extends T> Iterator<ET> iterate(final Class<ET> clazz, final boolean guaranteed) throws DBException
	{
		final Integer iteratorBufferSize = getOption(OPTION_IteratorBufferSize);
		if (iteratorBufferSize == null)
		{
			return list(clazz).iterator();
		}
		else
		{
			// NOTE: in case there is a buffer size set, most probably fresh data is required.
			// We don't have a 100% plain implementation for buffered iterators and guaranteed iterators
			// but at least we can refresh each item before returning it.
			// Actually this is the behaviour of guaranteed iterator with BufferSize=1.
			final Iterator<ET> it = list(clazz).iterator();
			return new Iterator<ET>()
			{

				@Override
				public boolean hasNext()
				{
					return it.hasNext();
				}

				@Override
				public ET next()
				{
					final ET item = it.next();
					InterfaceWrapperHelper.refresh(item);
					return item;
				}

				@Override
				public void remove()
				{
					// TODO Auto-generated method stub

				}
			};
		}
	}

	@Override
	public boolean match() throws DBException
	{
		return count() > 0;
	}

	@Override
	public POJOQuery<T> setOption(final String name, final Object value)
	{
		if (options == null)
		{
			options = new HashMap<>();
		}
		options.put(name, value);

		return this;
	}

	@Override
	public POJOQuery<T> setOptions(final Map<String, Object> options)
	{
		if (options == null || options.isEmpty())
		{
			return this;
		}

		if (this.options == null)
		{
			this.options = new HashMap<>(options);
		}
		else
		{
			this.options.putAll(options);
		}

		return this;
	}

	@Override
	public <OT> OT getOption(final String name)
	{
		if (options == null)
		{
			return null;
		}

		@SuppressWarnings("unchecked")
		final OT value = (OT)options.get(name);

		return value;
	}

	@Override
	public POJOQuery<T> setOnlySelection(final int AD_PInstance_ID)
	{
		// If Only selection filter has not changed then do nothing
		if (filter_inSelection != null && filter_inSelection.getSelectionId() == AD_PInstance_ID)
		{
			return this;
		}

		if (filter_inSelection != null)
		{
			removeFilter(filter_inSelection);
		}

		if (AD_PInstance_ID > 0)
		{
			filter_inSelection = POJOInSelectionQueryFilter.inSelection(AD_PInstance_ID);
			addFilter(filter_inSelection);
		}

		return this;
	}
	
	@Override
	public POJOQuery<T> setNotInSelection(final int AD_PInstance_ID)
	{
		// If Only selection filter has not changed then do nothing
		if (filter_notInSelection != null && filter_notInSelection.getSelectionId() == AD_PInstance_ID)
		{
			return this;
		}

		if (filter_notInSelection != null)
		{
			removeFilter(filter_notInSelection);
		}

		if (AD_PInstance_ID > 0)
		{
			filter_notInSelection = POJOInSelectionQueryFilter.notInSelection(AD_PInstance_ID);
			addFilter(filter_notInSelection);
		}

		return this;
	}

	@Override
	public POJOQuery<T> setOnlyActiveRecords(final boolean onlyActiveRecords)
	{
		final IQueryFilter<T> filter_OnlyActiveRecords = ActiveRecordQueryFilter.getInstance(modelClass);
		if (onlyActiveRecords)
		{
			addFilter(filter_OnlyActiveRecords);
		}
		else
		{
			removeFilter(filter_OnlyActiveRecords);
		}

		return this;
	}

	@Override
	public <AT> AT aggregate(final String columnName, final String sqlFunction, final Class<AT> returnType) throws DBException
	{
		AT result = null;
		if (AGGREGATE_SUM.equals(sqlFunction))
		{
			Check.assume(BigDecimal.class.equals(returnType), "Return type shall be {} and not {}", BigDecimal.class, returnType);

			// Setup initial result
			@SuppressWarnings("unchecked")
			final AT result0 = (AT)BigDecimal.ZERO;
			result = result0;
		}
		else if (AGGREGATE_MAX.equals(sqlFunction))
		{

			Check.assume(BigDecimal.class.equals(returnType)
					|| Integer.class.equals(returnType), "Return type shall be {}, {} and not {}", BigDecimal.class, Integer.class, returnType);

			// Setup initial result
			result = null;
		}
		else
		{
			throw new DBException("SQL Function '" + sqlFunction + "' not implemented");
		}

		final List<T> models = list(modelClass);
		for (final T model : models)
		{
			final Object valueObj = InterfaceWrapperHelper.getValue(model, columnName).orNull();
			if (AGGREGATE_SUM.equals(sqlFunction))
			{
				Check.assumeInstanceOfOrNull(valueObj, BigDecimal.class, "value");
				if (valueObj != null)
				{
					final BigDecimal valueBD = (BigDecimal)valueObj;
					final BigDecimal resultBD = (BigDecimal)result;

					@SuppressWarnings("unchecked")
					final AT resultNew = (AT)resultBD.add(valueBD);
					result = resultNew;
				}
			}
			else if (AGGREGATE_MAX.equals(sqlFunction))
			{
				if (valueObj == null)
				{
					// skip null values
					continue;
				}

				final BigDecimal valueBD;
				if (valueObj instanceof BigDecimal)
				{
					valueBD = (BigDecimal)valueObj;
				}
				else if (valueObj instanceof Integer)
				{
					final Integer valueInt = (Integer)valueObj;
					valueBD = BigDecimal.valueOf(valueInt);
				}
				else
				{
					throw new AdempiereException("Unsupported value '" + valueObj + "' (class=" + valueObj.getClass() + ") on model=" + model);
				}

				final BigDecimal resultBD = (BigDecimal)result;

				@SuppressWarnings("unchecked")
				final AT resultNew = (AT)(resultBD == null ? valueBD : resultBD.max(valueBD));
				result = resultNew;
			}
			else
			{
				throw new DBException("SQL Function '" + sqlFunction + "' not implemented");
			}
		}

		if (Integer.class.equals(returnType))
		{
			final BigDecimal resultBD = (BigDecimal)result;
			if (resultBD == null)
			{
				result = (AT)(Integer)0;
			}
			else
			{
				result = (AT)(Integer)resultBD.intValueExact();
			}
		}

		return result;
	}

	@Override
	public POJOQuery<T> setApplyAccessFilter(final boolean flag)
	{
		// nothing at the moment
		// FIXME: implement
		return this;
	}

	@Override
	public POJOQuery<T> setApplyAccessFilterRW(final boolean RW)
	{
		// nothing at the moment
		// FIXME: implement
		return this;
	}

	@Override
	public POJOQuery<T> setClient_ID()
	{
		filters.addFilter(new ContextClientQueryFilter<T>());
		return this;
	}

	@Override
	public int deleteDirectly()
	{
		// NOTE: we cannot issue an SQL command, so we need to delete it one by one
		return delete();
	}

	@Override
	public int delete()
	{
		final List<T> records = list(modelClass);
		if (records.isEmpty())
		{
			return 0;
		}

		int countDeleted = 0;
		for (final Object record : records)
		{
			InterfaceWrapperHelper.delete(record);
			countDeleted++;
		}

		return countDeleted;
	}

	@Override
	public POJOQuery<T> setLimit(final int limit)
	{
		this.limit = limit;
		return this;
	}

	@Override
	public IQuery<T> setLimit(final int limit, final int offset)
	{
		this.limit = limit;
		this.offset = offset;
		return this;
	}

	@Override
	public int updateDirectly(final IQueryUpdater<T> queryUpdater)
	{
		// NOTE: direct updating is not supported, so we go with the clasic one-by-one update.
		return update(queryUpdater);
	}

	@Override
	public int update(final IQueryUpdater<T> queryUpdater)
	{
		int countUpdated = 0;
		for (final T model : list())
		{
			final boolean updated = queryUpdater.update(model);
			if (!updated)
			{
				continue;
			}

			InterfaceWrapperHelper.save(model);
			countUpdated++;
		}

		return countUpdated++;
	}

	@Override
	public List<Integer> listIds()
	{
		final List<T> records = list();
		final List<Integer> idsList = new ArrayList<Integer>(records.size());
		for (final T record : records)
		{
			final int id = InterfaceWrapperHelper.getId(record);
			idsList.add(id);
		}

		return idsList;
	}

	@Override
	public IQuery<T> setPostQueryFilter(final IQueryFilter<T> postQueryFilter)
	{
		throw new UnsupportedOperationException("not implemented. Use addFilter method directly");
	}

	@Override
	protected final List<Map<String, Object>> listColumns(final boolean distinct, String... columnNames)
	{
		final List<T> records = list();

		// Our result, will collect unique records
		final Collection<Map<String, Object>> result;
		if (distinct)
		{
			result = new HashSet<>();
		}
		else
		{
			result = new ArrayList<>();
		}

		for (final T record : records)
		{
			// NOTE: we are using TreeMap because we want to have our keys sorted, because we want to compare them
			final TreeMap<String, Object> row = new TreeMap<>();

			for (final String columnName : columnNames)
			{
				final Object value = InterfaceWrapperHelper.getValue(record, columnName).orNull();
				row.put(columnName, value);
			}
			result.add(row);
		}

		return new ArrayList<>(result);
	}

	@Override
	public final <AT> List<AT> listDistinct(final String columnName, final Class<AT> valueType)
	{
		final List<T> records = list();

		final List<AT> result = new ArrayList<>();

		for (final T record : records)
		{
			final Object valueObj = InterfaceWrapperHelper.getValue(record, columnName).orNull();

			@SuppressWarnings("unchecked")
			final AT value = (AT)valueObj;

			if (result.contains(value))
			{
				continue;
			}

			result.add(value);
		}

		return result;
	}

	@Override
	public void addUnion(final IQuery<T> query, final boolean distinct)
	{
		final SqlQueryUnion<T> sqlQueryUnion = new SqlQueryUnion<T>(query, distinct);
		if (unions == null)
		{
			unions = new ArrayList<>();
		}
		unions.add(sqlQueryUnion);
	}

	/* package */ List<SqlQueryUnion<T>> getUnions()
	{
		if (unions == null)
		{
			return ImmutableList.of();
		}
		return ImmutableList.copyOf(unions);
	}

	@Override
	public int createSelection()
	{
		final List<Integer> ids = listIds();
		return POJOLookupMap.get().createSelection(ids);
	}

	@Override
	public int createSelection(int AD_PInstance_ID)
	{
		final List<Integer> ids = listIds();
		POJOLookupMap.get().createSelection(AD_PInstance_ID, ids);
		return ids.size();
	}

	@Override
	protected <ToModelType> QueryInsertExecutorResult executeInsert(final QueryInsertExecutor<ToModelType, T> queryInserter)
	{
		Check.assume(!queryInserter.isEmpty(), "At least one column to be inserted needs to be specified: {}", queryInserter);
		
		if(queryInserter.isCreateSelectionOfInsertedRows())
		{
			throw new UnsupportedOperationException("CreateSelectionOfInsertedRows not supported for "+queryInserter);
		}
		final int insertSelectionId = -1;

		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		final Class<ToModelType> toModelClass = queryInserter.getToModelClass();
		final Map<String, IQueryInsertFromColumn> fromColumns = queryInserter.getColumnMapping();

		final Iterator<T> fromModels = iterate(getModelClass());
		int countInsert = 0;
		while (fromModels.hasNext())
		{
			final T fromModel = fromModels.next();

			// Create a new model
			final ToModelType toModel = InterfaceWrapperHelper.create(ctx, toModelClass, trxName);

			// Apply the column updaters
			boolean updated = false;
			for (final Map.Entry<String, IQueryInsertFromColumn> toColumnName2from : fromColumns.entrySet())
			{
				final String toColumnName = toColumnName2from.getKey();
				final IQueryInsertFromColumn from = toColumnName2from.getValue();

				if (from.update(toModel, toColumnName, fromModel))
				{
					updated = true;
				}
			}

			if (!updated)
			{
				continue;
			}

			InterfaceWrapperHelper.save(toModel);
			countInsert++;
		}

		return QueryInsertExecutorResult.of(countInsert, insertSelectionId);
	}
}
