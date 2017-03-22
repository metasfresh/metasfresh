package org.compiere.model;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.adempiere.ad.dao.ICompositeQueryUpdaterExecutor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryInsertExecutor;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.ad.model.util.Model2IdFunction;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.ModelColumn;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;

public interface IQuery<T>
{

	/**
	 * If this instance is used to get an iterator, then this option tells how many rows the iterator shall load at a time.
	 * 
	 * @see #iterate(Class)
	 */
	String OPTION_IteratorBufferSize = "IteratorBufferSize";

	/**
	 * Boolean value to specify what type of iteration shall be used, when no one is specified explicitly
	 */
	String OPTION_GuaranteedIteratorRequired = "GuaranteedIteratorRequired";

	/**
	 * Default value for {@link #OPTION_GuaranteedIteratorRequired}.
	 * 
	 * Currently it's <code>true</code> because most of the business logic relies on guaranteed iterator.
	 */
	boolean DEFAULT_OPTION_GuaranteedIteratorRequired = true;

	int NO_LIMIT = -1;

	Properties getCtx();

	String getTrxName();

	/**
	 * 
	 * @return a copy of this object
	 */
	IQuery<T> copy();

	IQuery<T> setOrderBy(IQueryOrderBy orderBy);

	Class<T> getModelClass();

	String getTableName();

	<ET extends T> List<ET> list() throws DBException;

	/**
	 * Return a list of all po that match the query criteria.
	 * 
	 * @param clazz all resulting POs will be converted to this interface
	 * @return List
	 * @throws DBException
	 */
	<ET extends T> List<ET> list(Class<ET> clazz) throws DBException;

	/**
	 * Same as {@link #list(Class)} but returns an {@link ImmutableList}.
	 * 
	 * @param clazz
	 * @return {@link ImmutableList}
	 * @throws DBException
	 */
	default <ET extends T> ImmutableList<ET> listImmutable(Class<ET> clazz) throws DBException
	{
		return ImmutableList.copyOf(list(clazz));
	}

	/**
	 * Same as {@link #list(Class)} but instead of returning a list it will return a Map indexed by model's ID.
	 * 
	 * @param clazz
	 * @return Map of Model ID to Model.
	 * @throws DBException
	 */
	<ET extends T> Map<Integer, ET> mapById(Class<ET> clazz) throws DBException;

	int firstId();

	/**
	 * Return first ID. If there are more results and exception is thrown.
	 * 
	 * @return first ID or -1 if not found
	 * @throws DBException
	 */
	int firstIdOnly() throws DBException;

	<ET extends T> ET first() throws DBException;

	<ET extends T> ET first(Class<ET> clazz) throws DBException;

	/**
	 * Same as {@link #first(Class)}, but in case there is no record found an exception will be thrown too.
	 * 
	 * @param clazz
	 * @return
	 * @throws DBException
	 */
	<ET extends T> ET firstNotNull(Class<ET> clazz) throws DBException;

	/**
	 * Return first model that match query criteria. If there are more records that match criteria an exception will be thrown.
	 * 
	 * @return first PO or null.
	 * @throws DBMoreThenOneRecordsFoundException
	 * @see {@link #first()}
	 */
	<ET extends T> ET firstOnly(Class<ET> clazz) throws DBException;

	/**
	 * Same as {@link #firstOnly(Class)}, but in case there is no record found an exception will be thrown too.
	 * 
	 * @param clazz
	 * @return
	 * @throws DBException
	 */
	<ET extends T> ET firstOnlyNotNull(Class<ET> clazz) throws DBException;

	/**
	 * Same as {@link #firstOnly(Class)}, but in case there are more then one record <code>null</code> will be returned instead of throwing exception.
	 * 
	 * @param clazz
	 * @return model or null if not found or if there were more then one record found.
	 * @throws DBException
	 */
	<ET extends T> ET firstOnlyOrNull(Class<ET> clazz) throws DBException;

	/**
	 * Count items that match query criteria
	 * 
	 * @return count
	 * @throws DBException
	 */
	int count() throws DBException;

	/**
	 * Sets a nonSQL filter which will be applied right before retrieving records from database.
	 * 
	 * Using this method you can combine pure SQL where clause with pure Java filters in a seamless way.
	 * 
	 * NOTE: using post-filters is introducing some limitations mainly on {@link #setLimit(int)} and {@link #iterate(Class)} methods.
	 * 
	 * @param postQueryFilter
	 * @return this
	 */
	IQuery<T> setPostQueryFilter(IQueryFilter<T> postQueryFilter);

	IQuery<T> setOption(String name, Object value);

	<OT> OT getOption(String name);

	IQuery<T> setOptions(Map<String, Object> options);

	/**
	 * Check if there items for query criteria
	 * 
	 * @return true if exists, false otherwise
	 * @throws DBException
	 */
	boolean match() throws DBException;

	/**
	 * Returns an {@link Iterator} over current query selection.
	 * 
	 * The iterator will be guaranteed or not based on option {@link #OPTION_GuaranteedIteratorRequired}'s value. If the option is not set then {@link #DEFAULT_OPTION_GuaranteedIteratorRequired} will
	 * be considered.
	 * 
	 * @param clazz model interface class
	 * @return iterator
	 * @throws DBException
	 */
	<ET extends T> Iterator<ET> iterate(Class<ET> clazz) throws DBException;

	/**
	 * Only records that are in T_Selection with AD_PInstance_ID.
	 * 
	 * NOTE: {@link #setOnlySelection(int)} and {@link #setNotInSelection(int)} are complementary and NOT exclusive.
	 * 
	 * @param AD_PInstance_ID
	 */
	IQuery<T> setOnlySelection(int AD_PInstance_ID);

	/**
	 * Only records that are NOT in T_Selection with AD_PInstance_ID.
	 * 
	 * NOTE: {@link #setOnlySelection(int)} and {@link #setNotInSelection(int)} are complementary and NOT exclusive.
	 * 
	 * @param AD_PInstance_ID
	 */
	IQuery<T> setNotInSelection(int AD_PInstance_ID);

	/**
	 * Select only active records (i.e. IsActive='Y')
	 * 
	 * @param onlyActiveRecords
	 */
	IQuery<T> setOnlyActiveRecords(boolean onlyActiveRecords);

	String AGGREGATE_COUNT = "COUNT";
	String AGGREGATE_SUM = "SUM";
	String AGGREGATE_AVG = "AVG";
	String AGGREGATE_MIN = "MIN";
	String AGGREGATE_MAX = "MAX";
	String AGGREGATE_DISTINCT = "DISTINCT";

	/**
	 * Aggregate given expression on this criteria
	 * 
	 * @param <T>
	 * @param columnName
	 * @param sqlFunction
	 * @param returnType
	 * @return aggregated value
	 * @throws DBException
	 */
	<AT> AT aggregate(String columnName, String sqlFunction, Class<AT> returnType) throws DBException;

	default <AT> AT aggregate(final ModelColumn<T, ?> column, final String sqlFunction, final Class<AT> returnType) throws DBException
	{
		final String columnName = column.getColumnName();
		return aggregate(columnName, sqlFunction, returnType);
	}

	/**
	 * Turn on/off the data access filter.
	 * 
	 * i.e. accept only those records on which current role has access to. If you want to accept only those records on which current role has read-write access you might want to use
	 * {@link #setApplyAccessFilterRW(boolean)}.
	 * 
	 * @param flag <code>true</code> if it shall enforced
	 */
	IQuery<T> setApplyAccessFilter(boolean flag);

	/**
	 * Apply read-write access filter to all resulting records.
	 * 
	 * Please note that this method will turn on security filter anyway. If you want to turn this off again, use {@link #setApplyAccessFilter(boolean)}.
	 * 
	 * @param RW true if read-write access is required, false if read-only access is sufficient
	 */
	IQuery<T> setApplyAccessFilterRW(boolean RW);

	/**
	 * Filter by context AD_Client_ID
	 * 
	 * @return this
	 */
	IQuery<T> setClient_ID();

	/**
	 * Sets query LIMIT to be used.
	 * 
	 * For a detailed description about LIMIT and OFFSET concepts, please take a look <a href="http://www.postgresql.org/docs/9.1/static/queries-limit.html">here</a>.
	 * 
	 * @param limit integer greater than zero or {@link #NO_LIMIT}. Note: if the {@link #iterate()} method is used and the underlying database supports paging, then the limit value (if set) is used as
	 *            page size.
	 * @return this
	 */
	IQuery<T> setLimit(int limit);

	/**
	 * Sets query LIMIT and OFFSET to be used.
	 * 
	 * For a detailed description about LIMIT and OFFSET concepts, please take a look <a href="http://www.postgresql.org/docs/9.1/static/queries-limit.html">here</a>.
	 * 
	 * @param limit integer greater than zero or {@link #NO_LIMIT}. Note: if the {@link #iterate()} method is used and the underlying database supports paging, then the limit value (if set) is used as
	 *            page size.
	 * @param offset integer greater than zero or {@link #NO_LIMIT}
	 * @return this
	 */
	IQuery<T> setLimit(int limit, int offset);

	/**
	 * Directly execute DELETE FROM database.
	 * 
	 * Models, won't be loaded so no model validators will be triggered.
	 * 
	 * NOTE: please call it when you know what are you doing.
	 * 
	 * @return how many records were deleted
	 */
	int deleteDirectly();

	/**
	 * Delete all records which are matched by this query.
	 * 
	 * @return how many records were deleted
	 */
	int delete();

	/**
	 * 
	 * @return executor which will assist you to mass-update fields of models which are matched by this query
	 */
	ICompositeQueryUpdaterExecutor<T> updateDirectly();

	/**
	 * Update records which are matched by this query.
	 * 
	 * If <code>queryUpdater</code> implements {@link ISqlQueryUpdater} then an SQL "UPDATE" will be issued directly.
	 * 
	 * Else, records will be updated one by one, by using {@link #update(IQueryUpdater)}.
	 * 
	 * @param queryUpdater
	 * @return how many records were updated
	 */
	int updateDirectly(IQueryUpdater<T> queryUpdater);

	/**
	 * Update records which are matched by this query.
	 * 
	 * The records will be updated one by one, by issuing {@link IQueryUpdater#update(Object)}.
	 * 
	 * In the {@link IQueryUpdater} implementation, there is no need to save the record, because the API will save it after {@link IQueryUpdater#update(Object)} call.
	 * 
	 * @param queryUpdater
	 * @return how many records were updated
	 */
	int update(IQueryUpdater<T> queryUpdater);

	/**
	 * Gets a list of record Ids
	 * 
	 * @return list of record Ids
	 */
	List<Integer> listIds();

	/**
	 * Selects given columns and return the result as a list of ColumnName to Value map.
	 * 
	 * @param columnNames
	 * @return a list of rows, where each row is a {@link Map} having the required columns as keys.
	 */
	List<Map<String, Object>> listColumns(String... columnNames);

	/**
	 * Selects DISTINCT given columns and return the result as a list of ColumnName to Value map.
	 * 
	 * @param columnNames
	 * @return a list of rows, where each row is a {@link Map} having the required columns as keys.
	 * @see #listColumns(String...)
	 */
	List<Map<String, Object>> listDistinct(String... columnNames);

	/**
	 * Selects DISTINCT given column and return the result as a list.
	 * 
	 * @param columnName
	 * @param valueType value type
	 * @see #listColumns(String...)
	 */
	<AT> List<AT> listDistinct(String columnName, Class<AT> valueType);

	/**
	 * Gets an immutable {@link Map} of records.
	 * 
	 * @param modelClass
	 * @param keyFunction function which will be used to create the map key based on result model.
	 * @return key to model map
	 * @see #list(Class)
	 */
	<K, ET extends T> Map<K, ET> map(Class<ET> modelClass, Function<ET, K> keyFunction);

	/**
	 * Gets an immutable ID to model map.
	 * 
	 * @param modelClass
	 * @return ID to model map
	 * @see #map(Class, Function)
	 * @see Model2IdFunction
	 */
	<ET extends T> Map<Integer, ET> mapToId(Class<ET> modelClass);

	/**
	 * Retrieves the records as {@link ListMultimap}.
	 * 
	 * @param modelClass
	 * @param keyFunction function used to generate the key used to group records in lists.
	 * @return list multimap indexed by a key provided by <code>keyFunction</code>.
	 */
	<K, ET extends T> ListMultimap<K, ET> listMultimap(Class<ET> modelClass, Function<ET, K> keyFunction);

	/**
	 * Retrieves the records and then splits them in groups based on the indexing key provided by <code>keyFunction</code>.
	 * 
	 * @param modelClass
	 * @param keyFunction key function used to provide the key used to split the returned records.
	 * @return collection of record groups.
	 */
	<K, ET extends T> Collection<List<ET>> listAndSplit(Class<ET> modelClass, Function<ET, K> keyFunction);

	/**
	 * Adds SQL query to be joined as UNION ALL/DISTINCT.
	 * 
	 * WARNING: atm, the implementation is minimal and was tested only with {@link #list()} methods.
	 * 
	 * @param query
	 * @param distinct
	 */
	void addUnion(IQuery<T> query, boolean distinct);

	/**
	 * Creates a NEW selection from this query result.
	 * 
	 * @return selection's AD_PInstance_ID or <code>-1</code> if there were no records matching
	 */
	int createSelection();

	/**
	 * Appends this query result to an existing selection.
	 * 
	 * @param AD_PInstance_ID selection ID to be used
	 * @return number of records inserted in selection
	 */
	int createSelection(int AD_PInstance_ID);

	/**
	 * Use the result of this query and insert it in given <code>toModelClass</code>'s table.
	 * 
	 * @param toModelClass
	 * @return executor which will assist you with the INSERT.
	 */
	<ToModelType> IQueryInsertExecutor<ToModelType, T> insertDirectlyInto(Class<ToModelType> toModelClass);

	/**
	 * Return a stream of all records that match the query criteria.
	 * 
	 * @return Stream
	 * @throws DBException
	 */
	default Stream<T> stream() throws DBException
	{
		return list().stream();
	}

	default Stream<T> iterateAndStream() throws DBException
	{
		final Iterator<T> iterator = iterate(getModelClass());
		final boolean parallel = false;
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), parallel);
	}

	/**
	 * Return a stream of all records that match the query criteria.
	 * 
	 * @param clazz all resulting models will be converted to this interface
	 * @return Stream
	 * @throws DBException
	 */
	default <ET extends T> Stream<ET> stream(final Class<ET> clazz) throws DBException
	{
		return list(clazz).stream();
	}
}
