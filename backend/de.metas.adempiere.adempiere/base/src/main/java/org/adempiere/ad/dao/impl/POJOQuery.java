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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.metas.common.util.time.SystemTime;
import de.metas.dao.selection.pagination.PageDescriptor;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.money.Money;
import de.metas.process.PInstanceId;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.UIDStringUtil;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryInsertExecutor.QueryInsertExecutorResult;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

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
	private QueryLimit limit = QueryLimit.NO_LIMIT;
	private int offset = NO_LIMIT;

	private List<SqlQueryUnion<T>> unions;

	@Deprecated
	public POJOQuery(final Class<T> modelClass)
	{
		this(Env.getCtx(), modelClass, null, ITrx.TRXNAME_None);
	}

	public POJOQuery(
			final Properties ctx,
			@NonNull final Class<T> modelClass,
			final String tablename,
			final String trxName)
	{
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
		final POJOQuery<T> queryNew = new POJOQuery<>(modelClass);
		queryNew.filters.addFilters(filters.getFilters());
		queryNew.orderBy = this.orderBy;
		queryNew.options = this.options == null ? null : new HashMap<>(this.options);

		queryNew.unions = unions == null ? null : new ArrayList<>(unions);
		queryNew.limit = limit;

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

		return (o1, o2) -> orderByComparator.compare(o1, o2);
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
		if (limit.isLimited() && offset > 0)
		{
			throw new UnsupportedOperationException("Using offset option is not implemented");
		}

		final POJOLookupMap db = POJOLookupMap.get();
		final String tableName = getTableNameToUse(clazz);

		final List<T> result = db.getRecords(
				tableName,
				modelClass,
				filters,
				getOrderByComparator(modelClass),
				trxName);
		Check.assumeNotNull(result, "Return value of POJOLookupMap.getRecords is *never* null");

		final boolean readOnly = isReadOnlyRecords();
		final List<ET> resultCasted = new ArrayList<>(result.size());
		for (final T model : result)
		{
			if(limit.isLimitHitOrExceeded(resultCasted))
			{
				break;
			}

			final ET modelCasted = InterfaceWrapperHelper.create(model, clazz);
			InterfaceWrapperHelper.setSaveDeleteDisabled(modelCasted, readOnly);

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
	public boolean anyMatch() throws DBException
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
	public POJOQuery<T> setOnlySelection(final PInstanceId AD_PInstance_ID)
	{
		// If Only selection filter has not changed then do nothing
		if (filter_inSelection != null && PInstanceId.equals(filter_inSelection.getSelectionId(), AD_PInstance_ID))
		{
			return this;
		}

		if (filter_inSelection != null)
		{
			removeFilter(filter_inSelection);
		}

		if (AD_PInstance_ID != null)
		{
			filter_inSelection = POJOInSelectionQueryFilter.inSelection(AD_PInstance_ID);
			addFilter(filter_inSelection);
		}

		return this;
	}

	@Override
	public POJOQuery<T> setNotInSelection(final PInstanceId AD_PInstance_ID)
	{
		// If Only selection filter has not changed then do nothing
		if (filter_notInSelection != null && PInstanceId.equals(filter_notInSelection.getSelectionId(), AD_PInstance_ID))
		{
			return this;
		}

		if (filter_notInSelection != null)
		{
			removeFilter(filter_notInSelection);
		}

		if (AD_PInstance_ID != null)
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
	public <AT> AT aggregate(final String columnName,
			@NonNull final Aggregate aggregateType,
			final Class<AT> returnType) throws DBException
	{
		AT result = null;
		final BiFunction<Object, Object, AT> aggregateOperator;
		if (Aggregate.SUM.equals(aggregateType))
		{
			aggregateOperator = getSumOperator(returnType);

			// Setup initial result
			@SuppressWarnings("unchecked")
			final AT result0 = (AT)BigDecimal.ZERO;
			result = result0;
		}
		else if (Aggregate.MAX.equals(aggregateType))
		{
			aggregateOperator = getMaxOperator(returnType);

			// Setup initial result
			result = null;
		}
		else
		{
			throw new DBException("SQL Function '" + aggregateType + "' not implemented");
		}

		final List<T> models = list(modelClass);
		for (final T model : models)
		{
			final Object valueObj = InterfaceWrapperHelper.getValue(model, columnName).orElse(null);
			if (Aggregate.SUM.equals(aggregateType))
			{
				if (valueObj == null)
				{
					// skip null values
					continue;
				}
			}

			result = aggregateOperator.apply(result, valueObj);
		}

		if (Integer.class.equals(returnType))
		{
			final Number resultNumber = (Number)result;
			if (resultNumber == null)
			{
				result = (AT)(Integer)0;
			}
			else
			{
				result = (AT)(Integer)resultNumber.intValue();
			}
		}

		return result;
	}

	private static final <R> BiFunction<Object, Object, R> getSumOperator(final Class<R> type)
	{
		if (BigDecimal.class.equals(type))
		{
			return (result, value) -> {
				final BigDecimal resultBD = toBigDecimal(result);
				final BigDecimal valueBD = toBigDecimal(value);
				@SuppressWarnings("unchecked")
				final R newResult = (R)(resultBD == null ? valueBD : resultBD.add(valueBD));
				return newResult;
			};
		}
		else
		{
			throw new AdempiereException("Unsupported returnType '" + type + "' for SUM aggregation");
		}
	}

	private static final <R> BiFunction<Object, Object, R> getMaxOperator(final Class<R> type)
	{
		if (BigDecimal.class.equals(type))
		{
			return (result, value) -> {
				final BigDecimal resultBD = toBigDecimal(result);
				final BigDecimal valueBD = toBigDecimal(value);
				@SuppressWarnings("unchecked")
				final R newResult = (R)(resultBD == null ? valueBD : resultBD.max(valueBD));
				return newResult;
			};
		}
		else if (Integer.class.equals(type))
		{
			return (result, value) -> {
				final Integer resultInt = (Integer)result;
				final Integer valueInt = (Integer)value;
				@SuppressWarnings("unchecked")
				final R newResult = (R)(resultInt == null ? valueInt : (Integer)Math.max(resultInt, valueInt));
				return newResult;
			};
		}
		else if (Timestamp.class.equals(type))
		{
			return (result, value) -> {
				final Timestamp resultTS = (Timestamp)result;
				final Timestamp valueTS = (Timestamp)value;
				if (resultTS == null)
				{
					@SuppressWarnings("unchecked")
					final R newResult = (R)valueTS;
					return newResult;
				}
				else
				{
					@SuppressWarnings("unchecked")
					final R newResult = (R)(resultTS.compareTo(valueTS) >= 0 ? resultTS : valueTS);
					return newResult;
				}
			};
		}
		else if (Comparable.class.isAssignableFrom(type))
		{
			return (result, value) -> {
				final Comparable resultCmp = (Comparable)result;
				final Comparable valueCmp = (Comparable)value;
				if (resultCmp == null)
				{
					@SuppressWarnings("unchecked")
					final R newResult = (R)valueCmp;
					return newResult;
				}
				else
				{
					@SuppressWarnings("unchecked")
					final R newResult = (R)(resultCmp.compareTo(valueCmp) >= 0 ? resultCmp : valueCmp);
					return newResult;
				}
			};
		}
		else
		{
			throw new AdempiereException("Unsupported returnType '" + type + "' for MAX aggregation");
		}
	}

	private static final BigDecimal toBigDecimal(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else if (value instanceof Integer)
		{
			return BigDecimal.valueOf((Integer)value);
		}
		else
		{
			return new BigDecimal(value.toString());
		}
	}

	@Override
	public POJOQuery<T> setRequiredAccess(final Access access)
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
		final boolean failIfProcessed = false; // don't fail because we want to be consistent with the SQL version
		return delete(failIfProcessed);
	}

	@Override
	public int delete(final boolean failIfProcessed)
	{
		final List<T> records = list(modelClass);
		if (records.isEmpty())
		{
			return 0;
		}

		int countDeleted = 0;
		for (final Object record : records)
		{
			InterfaceWrapperHelper.delete(record, failIfProcessed);
			countDeleted++;
		}

		return countDeleted;
	}

	@Override
	public POJOQuery<T> setLimit(@NonNull final QueryLimit limit)
	{
		this.limit = limit;
		return this;
	}

	@Override
	public IQuery<T> setLimit(@NonNull final QueryLimit limit, final int offset)
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

		return countUpdated;
	}

	@Override
	public List<Integer> listIds()
	{
		final List<T> records = list();
		final List<Integer> idsList = new ArrayList<>(records.size());
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
				final Object value = InterfaceWrapperHelper.getValue(record, columnName).orElse(null);
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
			final Object valueObj = InterfaceWrapperHelper
					.getValue(record, columnName)
					.orElseGet(() -> DB.retrieveDefaultValue(valueType));

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
	public <AT> AT first(final String columnName, final Class<AT> valueType)
	{
		final List<T> records = list();
		if (records.isEmpty())
		{
			return null;
		}

		final T record = records.get(0);

		final Object valueObj = InterfaceWrapperHelper.getValue(record, columnName).orElse(null);

		@SuppressWarnings("unchecked")
		final AT value = (AT)valueObj;
		return value;
	}

	@Override
	public void addUnion(final IQuery<T> query, final boolean distinct)
	{
		final SqlQueryUnion<T> sqlQueryUnion = new SqlQueryUnion<>(query, distinct);
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
	public PInstanceId createSelection()
	{
		final List<Integer> ids = listIds();
		return POJOLookupMap.get().createSelection(ids);
	}

	@Override
	public int createSelection(PInstanceId pinstanceId)
	{
		final List<Integer> ids = listIds();
		POJOLookupMap.get().createSelection(pinstanceId, ids);
		return ids.size();
	}

	@Override
	protected <ToModelType> QueryInsertExecutorResult executeInsert(final QueryInsertExecutor<ToModelType, T> queryInserter)
	{
		Check.assume(!queryInserter.isEmpty(), "At least one column to be inserted needs to be specified: {}", queryInserter);

		if (queryInserter.isCreateSelectionOfInsertedRows())
		{
			throw new UnsupportedOperationException("CreateSelectionOfInsertedRows not supported for " + queryInserter);
		}
		final PInstanceId insertSelectionId = null;

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

	/**
	 * Used for unit testing
	 */
	private static final Map<String, QueryResultPage<?>> UUID_TO_PAGE = new ConcurrentHashMap<>();

	/**
	 * Invoked by the test helper after each individual test.
	 */
	public static void clear_UUID_TO_PAGE()
	{
		UUID_TO_PAGE.clear();
	}

	@Override
	public <ET extends T> QueryResultPage<ET> paginate(Class<ET> clazz, int pageSize) throws DBException
	{
		final List<ET> bigList = list(clazz);

		final String firstUUID = UIDStringUtil.createRandomUUID();
		final Instant resultTimestamp = SystemTime.asInstant();

		PageDescriptor currentPageDescriptor = PageDescriptor.createNew(firstUUID, pageSize, bigList.size(), resultTimestamp);

		final List<List<ET>> pages = Lists.partition(bigList, pageSize);
		if (bigList.isEmpty())
		{
			return new QueryResultPage<>(currentPageDescriptor, null, 0, resultTimestamp, ImmutableList.of());
		}

		PageDescriptor nextPageDescriptor = pages.size() > 1 ? currentPageDescriptor.createNext() : null;
		final QueryResultPage<ET> firstQueryResultPage = new QueryResultPage<>(currentPageDescriptor, nextPageDescriptor, bigList.size(), resultTimestamp, ImmutableList.copyOf(pages.get(0)));
		UUID_TO_PAGE.put(firstQueryResultPage.getCurrentPageDescriptor().getPageIdentifier().getCombinedUid(), firstQueryResultPage);

		currentPageDescriptor = nextPageDescriptor;

		for (int i = 1; i < pages.size(); i++)
		{
			final boolean lastPage = pages.size() <= i + 1;
			nextPageDescriptor = lastPage ? null : currentPageDescriptor.createNext();
			final QueryResultPage<ET> queryResultPage = new QueryResultPage<>(currentPageDescriptor, nextPageDescriptor, bigList.size(), resultTimestamp, ImmutableList.copyOf(pages.get(i)));
			UUID_TO_PAGE.put(queryResultPage.getCurrentPageDescriptor().getPageIdentifier().getCombinedUid(), queryResultPage);

			currentPageDescriptor = nextPageDescriptor;
		}
		return firstQueryResultPage;
	}

	@SuppressWarnings("unchecked")
	public static <T> QueryResultPage<T> getPage(Class<T> clazz, String next)
	{
		return (QueryResultPage<T>)UUID_TO_PAGE.get(next);
	}

	@Override
	public List<Money> sumMoney(@NonNull String amountColumnName, @NonNull String currencyIdColumnName)
	{
		throw new UnsupportedOperationException("sumMoney");
	}
}
