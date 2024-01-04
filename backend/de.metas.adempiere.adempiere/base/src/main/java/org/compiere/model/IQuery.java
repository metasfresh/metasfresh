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

package org.compiere.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.money.Money;
import de.metas.process.PInstanceId;
import de.metas.security.permissions.Access;
import de.metas.util.collections.IteratorUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdaterExecutor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryInsertExecutor;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
	 * If set to {@code true}, then returned records can't be saved or deleted.
	 */
	String OPTION_ReturnReadOnlyRecords = "ReturnReadOnlyRecords";

	/**
	 * Default value for {@link #OPTION_GuaranteedIteratorRequired}.
	 * <p>
	 * Currently it's <code>true</code> because most of the business logic relies on guaranteed iterator.
	 */
	boolean DEFAULT_OPTION_GuaranteedIteratorRequired = true;

	int NO_LIMIT = -1;

	Properties getCtx();

	String getTrxName();

	/**
	 * @return a copy of this object
	 */
	IQuery<T> copy();

	IQuery<T> setOrderBy(IQueryOrderBy orderBy);

	Class<T> getModelClass();

	String getTableName();

	List<T> list() throws DBException;

	/**
	 * Return a list of all po that match the query criteria.
	 *
	 * @param clazz all resulting POs will be converted to this interface
	 * @return List
	 */
	<ET extends T> List<ET> list(Class<ET> clazz) throws DBException;

	default ImmutableList<T> listImmutable() throws DBException
	{
		return ImmutableList.copyOf(list());
	}

	/**
	 * Same as {@link #list(Class)} returns an {@link ImmutableList}. Note: you can update or delete the included records.
	 * If you want read-only records, see {@link #OPTION_ReturnReadOnlyRecords}.
	 */
	default <ET extends T> ImmutableList<ET> listImmutable(final Class<ET> clazz) throws DBException
	{
		return ImmutableList.copyOf(list(clazz));
	}

	/**
	 * Same as {@link #list(Class)} but instead of returning a list it will return a Map indexed by model's ID.
	 *
	 * @return Map of Model ID to Model.
	 */
	<ET extends T> Map<Integer, ET> mapById(Class<ET> clazz) throws DBException;

	default Map<Integer, T> mapById() throws DBException
	{
		return mapById(getModelClass());
	}

	/**
	 * Same as {@link #list(Class)} but instead of returning a list it will return a Map indexed by model's {@link RepoIdAware}.
	 *
	 * @param idMapper function to turn int values into {@link RepoIdAware}, like {@link de.metas.user.UserId#ofRepoId(int)}.
	 */
	<ID extends RepoIdAware, ET extends T> Map<ID, ET> mapByRepoIdAware(final IntFunction<ID> idMapper, Class<ET> clazz) throws DBException;

	/**
	 * @return first ID or -1 if no records are found.
	 * No exception is thrown if multiple results exist, they are just ignored.
	 */
	int firstId();

	/**
	 * @return first ID or null if no records are found.
	 * No exception is thrown if multiple results exist, they are just ignored.
	 */
	@Nullable
	default <ID extends RepoIdAware> ID firstId(@NonNull final java.util.function.Function<Integer, ID> idMapper)
	{
		return idMapper.apply(firstId());
	}

	/**
	 * @return first ID or -1 if no records are found.
	 * An exception is thrown if multiple results exist.
	 */
	int firstIdOnly() throws DBException;

	/**
	 * @return first ID or null if no records are found.
	 * An exception is thrown if multiple results exist.
	 */
	@Nullable
	default <ID extends RepoIdAware> ID firstIdOnly(@NonNull final java.util.function.Function<Integer, ID> idMapper)
	{
		return idMapper.apply(firstIdOnly());
	}

	@NonNull
	default <ID extends RepoIdAware> Optional<ID> firstIdOnlyOptional(@NonNull final java.util.function.Function<Integer, ID> idMapper)
	{
		return Optional.ofNullable(firstIdOnly(idMapper));
	}

	@Nullable
	<ET extends T> ET first() throws DBException;

	/**
	 * @return first record or null
	 */
	@Nullable
	<ET extends T> ET first(Class<ET> clazz) throws DBException;

	default Optional<T> firstOptional() throws DBException
	{
		return Optional.ofNullable(first());
	}

	default <ET extends T> Optional<ET> firstOptional(final Class<ET> clazz) throws DBException
	{
		return Optional.ofNullable(first(clazz));
	}

	@NonNull
	default Optional<T> firstOnlyOptional() throws DBException
	{
		return Optional.ofNullable(firstOnly());
	}

	@NonNull
	default <ET extends T> Optional<ET> firstOnlyOptional(final Class<ET> clazz) throws DBException
	{
		return Optional.ofNullable(firstOnly(clazz));
	}

	/**
	 * Same as {@link #first(Class)}, but in case there is no record found an exception will be thrown too.
	 */
	@NonNull <ET extends T> ET firstNotNull(Class<ET> clazz) throws DBException;

	/**
	 * Return first model that match query criteria. If there are more records that match the criteria, then an exception will be thrown.
	 */
	T firstOnly() throws DBException;

	/**
	 * Return first model that match query criteria. If there are more records that match the criteria, then an exception will be thrown.
	 *
	 * @return first PO or null.
	 * @see #first()
	 */
	@Nullable
	<ET extends T> ET firstOnly(Class<ET> clazz) throws DBException;

	T firstOnlyNotNull() throws DBException;

	/**
	 * Same as {@link #firstOnly(Class)}, but in case there is no record found an exception will be thrown too.
	 */
	@NonNull <ET extends T> ET firstOnlyNotNull(Class<ET> clazz) throws DBException;

	/**
	 * Same as {@link #firstOnly(Class)}, but in case there are more then one record <code>null</code> will be returned instead of throwing exception.
	 *
	 * @return model or null if not found or if there were more then one record found.
	 */
	@Nullable
	<ET extends T> ET firstOnlyOrNull(Class<ET> clazz) throws DBException;

	/**
	 * Count items that match query criteria
	 */
	int count() throws DBException;

	/**
	 * Sets a nonSQL filter which will be applied right before retrieving records from database.
	 * <p>
	 * Using this method you can combine pure SQL where clause with pure Java filters in a seamless way.
	 * <p>
	 * NOTE: using post-filters is introducing some limitations mainly on {@link #setLimit(int)} and {@link #iterate(Class)} methods.
	 */
	IQuery<T> setPostQueryFilter(IQueryFilter<T> postQueryFilter);

	IQuery<T> setOption(String name, Object value);

	<OT> OT getOption(String name);

	IQuery<T> setOptions(Map<String, Object> options);

	/**
	 * Check if there are items for the query criteria.
	 *
	 * @return true if exists, false otherwise
	 */
	boolean anyMatch() throws DBException;

	/**
	 * Returns an {@link Iterator} over current query selection.
	 * <p>
	 * The iterator will be guaranteed or not based on option {@link #OPTION_GuaranteedIteratorRequired}'s value. If the option is not set then {@link #DEFAULT_OPTION_GuaranteedIteratorRequired} will
	 * be considered.
	 *
	 * @param clazz model interface class
	 */
	<ET extends T> Iterator<ET> iterate(Class<ET> clazz) throws DBException;

	default <ET extends T> Iterator<ET> iterateWithGuaranteedIterator(final Class<ET> clazz) throws DBException
	{
		setOption(IQuery.OPTION_GuaranteedIteratorRequired, true);
		return iterate(clazz);
	}

	default <ID extends RepoIdAware> Iterator<ID> iterateIds(@NonNull final IntFunction<ID> idMapper) throws DBException
	{
		// TODO: implement an efficient solution and not this workaround
		final Iterator<T> modelIterator = iterate(getModelClass());
		final Function<T, ID> mapper = model -> idMapper.apply(InterfaceWrapperHelper.getId(model));
		return IteratorUtils.map(modelIterator, mapper);
	}

	<ET extends T> QueryResultPage<ET> paginate(Class<ET> clazz, int pageSize) throws DBException;

	/**
	 * Only records that are in T_Selection with AD_PInstance_ID.
	 * <p>
	 * NOTE: This method and {@link #setNotInSelection(PInstanceId)} are complementary and NOT exclusive.
	 */
	IQuery<T> setOnlySelection(PInstanceId pisntanceId);

	/**
	 * Only records that are NOT in T_Selection with AD_PInstance_ID.
	 * <p>
	 * NOTE: {@link #setOnlySelection(PInstanceId)} and this method are complementary and NOT exclusive.
	 */
	IQuery<T> setNotInSelection(PInstanceId pinstanceId);

	/**
	 * Select only active records (i.e. IsActive='Y')
	 */
	IQuery<T> setOnlyActiveRecords(boolean onlyActiveRecords);

	enum Aggregate
	{
		COUNT("COUNT", false), //
		SUM("SUM", false), //
		AVG("AVG", false), //
		MIN("MIN", false), //
		MAX("MAX", false), //
		DISTINCT("DISTINCT", true), //
		FIRST(null, true);

		@Getter
		@Nullable
		private final String sqlFunction;

		@Getter
		private final boolean useOrderByClause;

		Aggregate(@Nullable final String sqlFunction, final boolean useOrderByClause)
		{
			this.sqlFunction = sqlFunction;
			this.useOrderByClause = useOrderByClause;
		}
	}

	/**
	 * Aggregate given expression on this criteria
	 *
	 * @return aggregated value
	 */
	<AT> AT aggregate(String columnName, Aggregate aggregateType, Class<AT> returnType) throws DBException;

	default <AT> AT aggregate(final ModelColumn<T, ?> column, final Aggregate aggregateType, final Class<AT> returnType) throws DBException
	{
		final String columnName = column.getColumnName();
		return aggregate(columnName, aggregateType, returnType);
	}

	List<Money> sumMoney(@NonNull String amountColumnName, @NonNull String currencyIdColumnName);

	/**
	 * @return maximum int of <code>columnName</code> or ZERO
	 */
	default int maxInt(final String columnName)
	{
		return aggregate(columnName, Aggregate.MAX, Integer.class);
	}

	IQuery<T> setRequiredAccess(@Nullable Access access);

	/**
	 * Filter by context AD_Client_ID
	 *
	 * @return this
	 */
	IQuery<T> setClient_ID();

	/**
	 * Sets query LIMIT to be used.
	 * <p>
	 * For a detailed description about LIMIT and OFFSET concepts, please take a look <a href="http://www.postgresql.org/docs/9.1/static/queries-limit.html">here</a>.
	 *
	 * @param limit integer greater than zero or {@link #NO_LIMIT}. Note: if the {@link #iterate(Class)} method is used and the underlying database supports paging, then the limit value (if set) is used as
	 *              page size.
	 * @return this
	 */
	IQuery<T> setLimit(QueryLimit limit);

	@Deprecated
	default IQuery<T> setLimit(final int limit)
	{
		return setLimit(QueryLimit.ofInt(limit));
	}

	/**
	 * Sets query LIMIT and OFFSET to be used.
	 * <p>
	 * For a detailed description about LIMIT and OFFSET concepts, please take a look <a href="http://www.postgresql.org/docs/9.1/static/queries-limit.html">here</a>.
	 *
	 * @param limit  integer greater than zero or {@link #NO_LIMIT}. Note: if the {@link #iterate(Class)} method is used and the underlying database supports paging, then the limit value (if set) is used as
	 *               page size.
	 * @param offset integer greater than zero or {@link #NO_LIMIT}
	 * @return this
	 */
	IQuery<T> setLimit(QueryLimit limit, int offset);

	@Deprecated
	default IQuery<T> setLimit(final int limit, final int offset)
	{
		return setLimit(QueryLimit.ofInt(limit), offset);
	}

	/**
	 * Directly execute DELETE FROM database.
	 * <p>
	 * WARNING: Models, won't be loaded so no model interceptor will be triggered!
	 * <p>
	 * Also, models will be deleted even if they were marked as Processed=Y.
	 * <p>
	 * NOTE: please call it when you know what are you doing.
	 *
	 * @return how many records were deleted
	 */
	int deleteDirectly();

	/**
	 * Delete all records which are matched by this query.
	 * If any record is Processed this method will fail.
	 *
	 * @return how many records were deleted
	 */
	default int delete()
	{
		final boolean failIfProcessed = true;
		return delete(failIfProcessed);
	}

	/**
	 * Delete all records which are matched by this query.
	 *
	 * @param failIfProcessed fail if any of those records are Processed.
	 * @return how many records were deleted
	 */
	default int delete(final boolean failIfProcessed)
	{
		final List<T> records = list();
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

	default void forEach(@NonNull final Consumer<T> action) {stream().forEach(action);}

	/**
	 * @return executor which will assist you to mass-update fields of models which are matched by this query
	 */
	ICompositeQueryUpdaterExecutor<T> updateDirectly();

	/**
	 * Update records which are matched by this query.
	 * <p>
	 * If <code>queryUpdater</code> implements {@link ISqlQueryUpdater} then an SQL "UPDATE" will be issued directly.
	 * <p>
	 * Else, records will be updated one by one, by using {@link #update(IQueryUpdater)}.
	 *
	 * @return how many records were updated
	 */
	int updateDirectly(IQueryUpdater<T> queryUpdater);

	/**
	 * Update records which are matched by this query.
	 * <p>
	 * The records will be updated one by one, by issuing {@link IQueryUpdater#update(Object)}.
	 * <p>
	 * In the {@link IQueryUpdater} implementation, there is no need to save the record, because the API will save it after {@link IQueryUpdater#update(Object)} call.
	 *
	 * @return how many records were updated
	 */
	int update(IQueryUpdater<T> queryUpdater);

	/**
	 * Gets a list of record Ids
	 *
	 * @return list of record Ids
	 */
	List<Integer> listIds();

	default <ID extends RepoIdAware> ImmutableSet<ID> listIds(@NonNull final java.util.function.Function<Integer, ID> idMapper)
	{
		return listIds().stream().map(idMapper).collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Selects given columns and return the result as a list of ColumnName to Value map.
	 *
	 * @return a list of rows, where each row is a {@link Map} having the required columns as keys.
	 */
	List<Map<String, Object>> listColumns(String... columnNames);

	/**
	 * Selects DISTINCT given columns and return the result as a list of ColumnName to Value map.
	 *
	 * @return a list of rows, where each row is a {@link Map} having the required columns as keys.
	 * @see #listColumns(String...)
	 */
	List<Map<String, Object>> listDistinct(String... columnNames);

	/**
	 * Selects DISTINCT given column and return the result as a list.
	 *
	 * @param valueType value type
	 * @see #listColumns(String...)
	 */
	<AT> ImmutableList<AT> listDistinct(String columnName, Class<AT> valueType);

	/**
	 * @return <code>columnName</code>'s value on first records; if there are no records, null will be returned.
	 */
	@Nullable
	<AT> AT first(final String columnName, final Class<AT> valueType);

	/**
	 * Gets an immutable {@link Map} of records.
	 *
	 * @param keyFunction function which will be used to create the map key based on result model.
	 * @return key to model map
	 * @see #list(Class)
	 */
	<K, ET extends T> ImmutableMap<K, ET> map(Class<ET> modelClass, Function<ET, K> keyFunction);

	<K> ImmutableMap<K, T> map(Function<T, K> keyFunction);
	/**
	 * Retrieves the records as {@link ListMultimap}.
	 *
	 * @param keyFunction function used to generate the key used to group records in lists.
	 * @return list multimap indexed by a key provided by <code>keyFunction</code>.
	 */
	<K, ET extends T> ListMultimap<K, ET> listMultimap(Class<ET> modelClass, Function<ET, K> keyFunction);

	default <K> ListMultimap<K, T> listMultimap(@NonNull Function<T, K> keyFunction) {return listMultimap(getModelClass(), keyFunction);}

	/**
	 * Retrieves the records and then splits them in groups based on the indexing key provided by <code>keyFunction</code>.
	 *
	 * @param keyFunction key function used to provide the key used to split the returned records.
	 * @return collection of record groups.
	 */
	<K, ET extends T> Collection<List<ET>> listAndSplit(Class<ET> modelClass, Function<ET, K> keyFunction);

	/**
	 * "Appends" the given {@code query} to {@code this} query be joined as UNION ALL/DISTINCT.
	 * <p>
	 * WARNING: atm, the implementation is minimal and was tested only with {@link #list()} methods.
	 */
	void addUnion(IQuery<T> query, boolean distinct);

	default IQuery<T> addUnions(final Collection<IQuery<T>> queries, final boolean distinct)
	{
		queries.forEach(query -> addUnion(query, distinct));
		return this;
	}

	/**
	 * @return UNION DISTINCT {@link IQuery} reducer
	 */
	static <T> BinaryOperator<IQuery<T>> unionDistict()
	{
		return (previousDBQuery, dbQuery) -> {
			if (previousDBQuery == null)
			{
				return dbQuery;
			}
			else
			{
				previousDBQuery.addUnion(dbQuery, true);
				return previousDBQuery;
			}
		};
	}

	/**
	 * Creates a NEW selection from this query result.
	 *
	 * @return selection's or <code>null</code> if there were no records matching
	 */
	PInstanceId createSelection();

	/**
	 * Appends this query result to an existing selection.
	 *
	 * @param pinstanceId selection ID to be used
	 * @return number of records inserted in selection
	 */
	int createSelection(PInstanceId pinstanceId);

	/**
	 * Use the result of this query and insert it in given <code>toModelClass</code>'s table.
	 *
	 * @return executor which will assist you with the INSERT.
	 */
	<ToModelType> IQueryInsertExecutor<ToModelType, T> insertDirectlyInto(Class<ToModelType> toModelClass);

	/**
	 * Return a stream of all records that match the query criteria.
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

	default <ID extends RepoIdAware> Stream<ID> iterateAndStreamIds(@NonNull final IntFunction<ID> idMapper) throws DBException
	{
		final Iterator<ID> iterator = iterateIds(idMapper);
		final boolean parallel = false;
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), parallel);
	}

	/**
	 * Return a stream of all records that match the query criteria.
	 *
	 * @param clazz all resulting models will be converted to this interface
	 * @return Stream
	 */
	default <ET extends T> Stream<ET> stream(final Class<ET> clazz) throws DBException
	{
		return list(clazz).stream();
	}
}
