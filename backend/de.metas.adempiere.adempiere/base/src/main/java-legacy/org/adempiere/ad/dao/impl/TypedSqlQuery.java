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
package org.adempiere.ad.dao.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.dao.selection.pagination.PaginationService;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.dao.sql.SqlParamsInliner;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryInsertExecutor.QueryInsertExecutorResult;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.text.TokenizedStringBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POResultSet;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Low Heng Sin
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 1981760 ] Improve Query class
 *         <li>BF [ 2030280 ] org.compiere.model.Query apply access filter issue
 *         <li>FR [ 2041894 ] Add Query.match() method
 *         <li>FR [
 *         2107068 ] Query.setOrderBy should be more error tolerant
 *         <li>FR [ 2107109 ] Add method Query.setOnlyActiveRecords
 *         <li>FR [ 2421313 ] Introduce Query.firstOnly convenient method
 *         <li>FR [
 *         2546052 ] Introduce Query aggregate methods
 *         <li>FR [ 2726447 ] Query aggregate methods for all return types
 *         <li>FR [ 2818547 ] Implement Query.setOnlySelection
 *         https://sourceforge.net/tracker/?func=detail&aid=2818547&group_id=176962&atid=879335
 *         <li>FR [ 2818646 ] Implement Query.firstId/firstIdOnly
 *         https://sourceforge.net/tracker/?func=detail&aid=2818646&group_id=176962&atid=879335
 * @author Redhuan D. Oon
 *         <li>FR: [ 2214883 ] Remove SQL code and Replace for Query // introducing SQL String prompt in log.info
 *         <li>FR: [ 2214883 ] - to introduce .setClient_ID
 */
public class TypedSqlQuery<T> extends AbstractTypedQuery<T>
{
	private static final Logger log = LogManager.getLogger(TypedSqlQuery.class);

	private static final SqlParamsInliner sqlParamsInliner = SqlParamsInliner.builder()
			.failOnError(false)
			.build();

	private final Properties ctx;
	private final String tableName;
	private String sqlFrom = null;
	private POInfo _poInfo;
	private final Class<T> modelClass;
	private String whereClause;
	private final String trxName;

	private IQueryOrderBy queryOrderBy = null;
	@Nullable
	private List<Object> parameters = null;
	private IQueryFilter<T> postQueryFilter;
	@Nullable
	private Access requiredAccess;
	private boolean onlyActiveRecords = false;
	private boolean onlyClient_ID = false;
	private PInstanceId onlySelectionId;
	private PInstanceId notInSelectionId;

	private QueryLimit limit = QueryLimit.NO_LIMIT;
	private int offset = NO_LIMIT;

	@Nullable
	private List<SqlQueryUnion<T>> unions;

	protected TypedSqlQuery(
			@NonNull final Properties ctx,
			final Class<T> modelClass,
			@Nullable final String tableName,
			final String whereClause,
			@Nullable final String trxName)
	{
		this.modelClass = modelClass;
		this.tableName = InterfaceWrapperHelper.getTableName(modelClass, tableName);

		this.ctx = ctx;

		this.whereClause = whereClause;
		this.trxName = trxName;
	}

	public TypedSqlQuery(
			final Properties ctx,
			final Class<T> modelClass,
			final String whereClause,
			@Nullable final String trxName)
	{
		this(ctx,
			 modelClass,
			 null, // tableName
			 whereClause,
			 trxName);
	}

	/**
	 * @return {@link POInfo}; never returns null
	 */
	private POInfo getPOInfo()
	{
		if (this._poInfo == null)
		{
			final String tableName = getTableName();
			_poInfo = POInfo.getPOInfo(tableName);
			if (_poInfo == null || _poInfo.getColumnCount() <= 0)
			{
				throw new DBException("POInfo not found for " + tableName);
			}
		}
		return _poInfo;
	}

	/**
	 * Sets custom SQL FROM clause to be used instead of {@link #getTableName()}.
	 *
	 * @param sqlFrom SQL FROM clause (e.g. Table1 as t1 INNER JOIN Table t2 ON (...) .... )
	 */
	public TypedSqlQuery<T> setSqlFrom(final String sqlFrom)
	{
		this.sqlFrom = sqlFrom;
		return this;
	}

	/**
	 * Gets SQL to be used in FROM clause.
	 *
	 * @return SQL to be used in FROM clause; it returns the custom SQL FROM set by {@link #setSqlFrom(String)} or {@link #getTableName()}.
	 */
	private String getSqlFrom()
	{
		if (sqlFrom == null || sqlFrom.isEmpty())
		{
			return getTableName();
		}
		return sqlFrom;
	}

	/**
	 * Set query parameters
	 */
	public TypedSqlQuery<T> setParameters(final Object... parameters)
	{
		this.parameters = Arrays.asList(parameters);
		return this;
	}

	/**
	 * Set query parameters
	 *
	 * @param parameters collection of parameters
	 */
	public TypedSqlQuery<T> setParameters(final List<Object> parameters)
	{
		if (parameters == null)
		{
			this.parameters = null;
			return this;
		}
		this.parameters = new ArrayList<>(parameters);
		return this;
	}

	/**
	 * Set order by clause. If the string starts with "ORDER BY" then "ORDER BY" keywords will be discarded.
	 *
	 * @param orderBy SQL ORDER BY clause
	 */
	public TypedSqlQuery<T> setOrderBy(final String orderBy)
	{
		this.queryOrderBy = Services.get(IQueryBL.class).createSqlQueryOrderBy(orderBy);
		return this;
	}

	@Override
	public TypedSqlQuery<T> setOrderBy(final IQueryOrderBy orderBy)
	{
		this.queryOrderBy = orderBy;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setRequiredAccess(@Nullable final Access access)
	{
		this.requiredAccess = access;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setOnlyActiveRecords(final boolean onlyActiveRecords)
	{
		this.onlyActiveRecords = onlyActiveRecords;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setClient_ID()
	{
		this.onlyClient_ID = true;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setOnlySelection(final PInstanceId pinstanceId)
	{
		this.onlySelectionId = pinstanceId;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setNotInSelection(final PInstanceId pinstanceId)
	{
		this.notInSelectionId = pinstanceId;
		return this;
	}

	/**
	 * Return a list of all po that match the query criteria.
	 */
	@Override
	public List<T> list() throws DBException
	{
		return list(modelClass);
	}

	@Override
	public <ET extends T> List<ET> list(final Class<ET> clazz) throws DBException
	{
		final List<ET> list;
		if (limit.isLessThanOrEqualTo(100))
		{
			list = new ArrayList<>(limit.toInt());
		}
		else
		{
			// TODO: check if we shall go with LinkedList in this case
			list = new ArrayList<>();
		}

		final String sql = buildSQL(null, null, null, true);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = createResultSet(pstmt);

			final boolean readOnly = isReadOnlyRecords();

			ET model;
			while ((model = retrieveNextModel(rs, clazz)) != null)
			{
				InterfaceWrapperHelper.setSaveDeleteDisabled(model, readOnly);
				list.add(model);

				if(limit.isLimitHitOrExceeded(list))
				{
					log.debug("Limit of {} reached. Stop.", limit);
					break;
				}
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, getParametersEffective());
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return list;
	}

	/**
	 * Move <code>rs</code>'s cursor forward and get next model.
	 * <p>
	 * If we have a post-filter (see {@link #setPostQueryFilter(IQueryFilter)}), the model will be validated againt it and if not matched next row will be checked.
	 *
	 * @return model or null if we reached the and of the cursor
	 */
	@Nullable
	private <ET extends T> ET retrieveNextModel(@NonNull final ResultSet rs, @Nullable final Class<ET> clazz) throws SQLException
	{
		while (rs.next())
		{
			final ET model = retrieveModel(rs, clazz);
			if (postQueryFilter == null)
			{
				return model;
			}
			else if (postQueryFilter.accept(model))
			{
				return model;
			}
			else
			{
				// model was not accepted by our post-filter, skip it
			}
		}

		return null;
	}

	/**
	 * Retrieve model from given {@link ResultSet}.
	 *
	 * @return next model
	 */
	private <ET extends T> ET retrieveModel(final ResultSet rs, @Nullable final Class<ET> clazz)
	{
		final String tableName = getTableName();

		final Class<?> modelClassToUse = clazz != null ? clazz : this.modelClass;
		return TableModelLoader.instance.retrieveModel(ctx, tableName, modelClassToUse, rs, trxName);
	}

	/**
	 * Return first PO that match query criteria
	 *
	 * @return first PO
	 */
	@Override
	public <ET extends T> ET first() throws DBException
	{
		return first(null);
	}

	@Override
	public <ET extends T> ET first(@Nullable final Class<ET> clazz) throws DBException
	{
		ET model;

		// metas: begin: not using ORDER BY clause can be a developer error
		final String orderBy = getOrderBy();
		if (Check.isEmpty(orderBy, true))
		{
			final AdempiereException ex = new AdempiereException("Using first() without an ORDER BY clause can be a developer error."
					+ " Please specify ORDER BY clause or in case you know that only one result shall be returned then use firstOnly()."
					+ " Query: " + this);
			log.warn(ex.getLocalizedMessage(), ex);
		}
		// metas: end

		final String sql = buildSQL(null/* selectClause */, null/* fromClause */, null/* groupByClause */, true/* useOrderByClause */);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);

			// Optimization: if we don't have post-query filters, it's fine to set the Maximum Rows to fetch to one.
			if (postQueryFilter == null)
			{
				pstmt.setMaxRows(1);
			}

			rs = createResultSet(pstmt);
			model = retrieveNextModel(rs, clazz);
		}
		catch (final SQLException e)
		{
			log.info(sql, e);
			throw new DBException(e, sql, getParametersEffective());
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return model;
	}

	/**
	 * Return first PO that match query criteria. If there are more records that match criteria an exception will be throwed
	 *
	 * @return first PO
	 * @see  #first()
	 */
	@Nullable
	public <ET extends T> ET firstOnly() throws DBException
	{
		final Class<ET> clazz = null;
		final boolean throwExIfMoreThenOneFound = true;
		return firstOnly(clazz, throwExIfMoreThenOneFound);
	}

	/**
	 * @param throwExIfMoreThenOneFound if true and there more then one record found it will throw exception, <code>null</code> will be returned otherwise.
	 * @return model or null
	 */
	@Override
	@Nullable
	protected final <ET extends T> ET firstOnly(
			@Nullable final Class<ET> clazz,
			final boolean throwExIfMoreThenOneFound) throws DBException
	{
		ET model = null;
		final String sql = buildSQL(
				null,    // selectClause: use default (i.e. all columns)
				null, // fromClause
				null, // groupByClause
				false // useOrderByClause=false because we expect only one record
		);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = createResultSet(pstmt);
			model = retrieveNextModel(rs, clazz);

			//
			// Make sure there are no other results
			final ET modelNext = retrieveNextModel(rs, clazz);
			if (modelNext != null)
			{
				if (throwExIfMoreThenOneFound)
				{
					throw new DBMoreThanOneRecordsFoundException(this.toString());
				}
				else
				{
					return null;
				}
			}
		}
		catch (final SQLException e)
		{
			log.info(sql, e);
			throw new DBException(e, sql, getParametersEffective());
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return model;
	}

	@Override
	public int firstId() throws DBException
	{
		final boolean assumeOnlyOneResult = false;
		return firstId(assumeOnlyOneResult);
	}

	@Override
	public int firstIdOnly() throws DBException
	{
		return firstId(true);
	}

	private int firstId(final boolean assumeOnlyOneResult) throws DBException
	{
		final String keyColumnName = getKeyColumnName();

		final StringBuilder selectClause = new StringBuilder("SELECT ").append(keyColumnName);
		final StringBuilder fromClause = new StringBuilder(" FROM ").append(getSqlFrom());
		final String groupByClause = null;

		final String sql = buildSQL(selectClause, fromClause, groupByClause, true);

		int id = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = createResultSet(pstmt);
			if (rs.next())
			{
				id = rs.getInt(1);
			}
			if (assumeOnlyOneResult && rs.next())
			{
				throw new DBException("QueryMoreThanOneRecordsFound"); // TODO : translate
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		//
		return id;
	}

	/**
	 * red1 - returns full SQL string - for caller needs
	 *
	 * @return buildSQL(null, true)
	 */
	public String getSQL() throws DBException
	{
		return buildSQL(null, null, null, true);
	}

	/**
	 * Aggregate given expression on this criteria
	 */
	public BigDecimal aggregate(
			final String sqlExpression,
			@NonNull final Aggregate aggregateType) throws DBException
	{
		return aggregate(sqlExpression, aggregateType, BigDecimal.class);
	}

	/**
	 * Aggregate given expression on this criteria
	 */
	@Nullable
	@Override
	public <AT> AT aggregate(
			final String columnName,
			final Aggregate aggregateType,
			final Class<AT> returnType) throws DBException
	{
		final List<AT> list = aggregateList(columnName, aggregateType, returnType);

		if (list.isEmpty())
		{
			return null;
		}
		else if (list.size() > 1)
		{
			throw new DBException("QueryMoreThanOneRecordsFound"); // TODO : translate
		}

		return list.get(0);
	}

	@Override
	public List<Money> sumMoney(
			@NonNull final String amountColumnName,
			@NonNull final String currencyIdColumnName)
	{
		return aggregateList(
				amountColumnName,
				Aggregate.SUM,
				ImmutableList.of(currencyIdColumnName),
				rs -> {
					final BigDecimal value = CoalesceUtil.coalesceNotNull(rs.getBigDecimal(1), BigDecimal.ZERO);
					final CurrencyId currencyId = CurrencyId.ofRepoId(rs.getInt(currencyIdColumnName));
					return Money.of(value, currencyId);
				});
	}

	public <AT> List<AT> aggregateList(
			@NonNull final String sqlExpression,
			@NonNull final Aggregate aggregateType,
			@NonNull final Class<AT> returnType)
	{
		return aggregateList(sqlExpression,
				aggregateType,
				null, // groupBys
				rs -> DB.retrieveValueOrDefault(rs, 1, returnType));
	}

	@FunctionalInterface
	private interface ValueFetcher<T>
	{
		T retrieveValue(ResultSet rs) throws SQLException;
	}

	public <AT> List<AT> aggregateList(
			@NonNull String sqlExpression,
			@NonNull final Aggregate aggregateType,
			@Nullable final List<String> groupBys,
			@NonNull final ValueFetcher<AT> valueFetcher)
	{
		// NOTE: it's OK to have the sqlFunction null. Methods like first(columnName, valueClass) are relying on this.
		// if (Check.isEmpty(aggregateType.sqlFunction, true)) throw new DBException("No Aggregate Function defined");

		if (postQueryFilter != null)
		{
			throw new DBException("Aggration when 'postQueryFilter' is not null is not supported");
		}

		// metas-tsa: If the sqlExpression is a virtual column, then replace it with it's ColumnSQL
		final POInfo poInfo = getPOInfo();
		final int columnIndex = poInfo.getColumnIndex(sqlExpression);
		if (columnIndex >= 0 && poInfo.isVirtualColumn(columnIndex))
		{
			sqlExpression = poInfo.getColumnSql(columnIndex);
		}

		if (Check.isEmpty(sqlExpression, true))
		{
			if (Aggregate.COUNT.equals(aggregateType))
			{
				sqlExpression = "1";
			}
			else
			{
				throw new DBException("No SQL expression defined");
			}
		}

		final List<AT> result = new ArrayList<>();

		final StringBuilder sqlSelect = new StringBuilder("SELECT ");
		if (Check.isEmpty(aggregateType.getSqlFunction(), true))
		{
			sqlSelect.append(sqlExpression);
		}
		else
		{
			sqlSelect
					.append(aggregateType.getSqlFunction())
					.append("(").append(sqlExpression).append(")");
		}

		final StringBuilder fromClause = new StringBuilder(" FROM ").append(getSqlFrom());

		final String groupBysClause;
		if (groupBys != null && !groupBys.isEmpty())
		{
			groupBysClause = Joiner.on(", ").join(groupBys);

			sqlSelect.append("\n, ").append(groupBysClause);
		}
		else
		{
			groupBysClause = null;
		}

		final String sql = buildSQL(sqlSelect, fromClause, groupBysClause, aggregateType.isUseOrderByClause());

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, this.trxName);
			rs = createResultSet(pstmt);
			while (rs.next())
			{
				final AT value = valueFetcher.retrieveValue(rs);
				if (value == null)
				{
					continue; // Skip null values
				}
				result.add(value);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, getParametersEffective());
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		//
		return result;
	}

	@Override
	public final <AT> List<AT> listDistinct(final String columnName, final Class<AT> valueType)
	{
		return aggregateList(columnName, Aggregate.DISTINCT, valueType);
	}

	@Override
	public <AT> AT first(final String columnName, final Class<AT> valueType)
	{
		setLimit(QueryLimit.ONE, 0);
		final List<AT> result = aggregateList(columnName, Aggregate.FIRST, valueType);
		if (result == null || result.isEmpty())
		{
			return null;
		}
		return result.get(0);
	}

	@Override
	protected final List<Map<String, Object>> listColumns(final boolean distinct, final String... columnNames)
	{
		Check.assumeNotEmpty(columnNames, "columnNames not empty");

		final String tableName = getTableName();

		//
		// Build columns SQL
		final POInfo poInfo = getPOInfo();
		final Map<String, Class<?>> columnName2class = new HashMap<>(columnNames.length);
		final StringBuilder sqlColumnNames = new StringBuilder();
		for (final String columnName : columnNames)
		{
			final int columnIndex = poInfo.getColumnIndex(columnName);
			if (columnIndex < 0)
			{
				throw new DBException("Column '" + columnName + "' not found for table " + tableName);
			}

			final String columnSql = poInfo.getColumnSqlForSelect(columnIndex);
			if (sqlColumnNames.length() > 0)
			{
				sqlColumnNames.append(", ");
			}
			sqlColumnNames.append(columnSql);

			final Class<?> columnClass = poInfo.getColumnClass(columnIndex);
			columnName2class.put(columnName, columnClass);
		}

		//
		// Build SQL query
		final StringBuilder sqlSelect = new StringBuilder("SELECT ")
				.append(distinct ? " DISTINCT " : "")
				.append(sqlColumnNames);

		final StringBuilder fromClause = new StringBuilder(" FROM ").append(getSqlFrom());
		final String groupByClause = null;
		final boolean useOrderByClause = !distinct;
		final String sql = buildSQL(sqlSelect, fromClause, groupByClause, useOrderByClause);

		final List<Map<String, Object>> result = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, this.trxName);
			rs = createResultSet(pstmt);
			while (rs.next())
			{
				final Map<String, Object> row = new HashMap<>();
				for (final String columnName : columnNames)
				{
					final Class<?> columnClass = columnName2class.get(columnName);
					final Object value = DB.retrieveValue(rs, columnName, columnClass);
					row.put(columnName, value);
				}
				result.add(row);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, getParametersEffective());
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		//
		return result;
	}

	@Override
	public int count() throws DBException
	{
		return aggregate("*", Aggregate.COUNT).intValue();
	}

	/**
	 * SUM sqlExpression for items that match query criteria
	 *
	 * @return sum
	 */
	public BigDecimal sum(final String sqlExpression)
	{
		return aggregate(sqlExpression, Aggregate.SUM);
	}

	@Override
	public boolean anyMatch() throws DBException
	{
		final StringBuilder sqlSelect;
		final StringBuilder fromClause;
		if (postQueryFilter != null)
		{
			// we expect to build the select with all columns
			// because we will want to retrieve models and match them again our post-filter
			sqlSelect = null;
			fromClause = null;
		}
		else
		{
			setLimit(QueryLimit.ONE); // no postQueryFilter => we don't need more than one row to decide if it matches
			sqlSelect = new StringBuilder("SELECT 1 ");
			fromClause = new StringBuilder(" FROM ").append(getSqlFrom());
		}
		final String sql = buildSQL(sqlSelect, fromClause, null/* groupByClause */, false/* useOrderByClause */);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, this.trxName);
			rs = createResultSet(pstmt);

			//
			// Case: we have a post-filter => we need to retrieve next model
			if (postQueryFilter != null)
			{
				final T model = retrieveNextModel(rs, modelClass);
				return model != null;
			}
			//
			// Case: we don't have a post-filter
			// => if the ResultSet has any rows, we can consider it as matched
			else
			{
				return rs.next();
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * Return an Iterator implementation to fetch one PO at a time. The implementation first retrieve all IDS that match the query criteria and issue sql query to fetch the PO when caller want to
	 * fetch the next PO. This minimize memory usage but it is slower than the list method.
	 *
	 * @return Iterator
	 */
	public <ET extends T> Iterator<ET> iterate() throws DBException
	{
		return iterate(null/*clazz*/);
	}

	@Override
	public <ET extends T> Iterator<ET> iterate(@Nullable final Class<ET> clazz) throws DBException
	{
		final boolean guaranteed;

		final Boolean guaranteedIteratorRequired = getOption(OPTION_GuaranteedIteratorRequired);
		if (guaranteedIteratorRequired != null)
		{
			guaranteed = guaranteedIteratorRequired;
		}
		else if (getKeyColumnNames().size() != 1)
		{
			// case: no guaranteed option specified and this table has zero or more than one primary key columns
			// => cannot use guaranteed iterators
			guaranteed = false;
		}
		else
		{
			guaranteed = DEFAULT_OPTION_GuaranteedIteratorRequired;
		}
		return iterate(clazz, guaranteed);
	}

	@Override
	public <ET extends T> QueryResultPage<ET> paginate(@Nullable final Class<ET> clazz, final int pageSize) throws DBException
	{
		return SpringContextHolder.instance
				.getBean(PaginationService.class)
				.loadFirstPage(clazz, this, pageSize);
	}

	public <ET extends T> Iterator<ET> iterate(final Class<ET> clazz, final boolean guaranteed) throws DBException
	{
		Check.assumeNull(postQueryFilter, "No post-filter shall be defined when iterating");

		final Integer iteratorBufferSize = getOption(OPTION_IteratorBufferSize);

		if (guaranteed)
		{
			final GuaranteedPOBufferedIterator<T, ET> it = new GuaranteedPOBufferedIterator<>(this, clazz);
			if (iteratorBufferSize != null)
			{
				it.setBufferSize(iteratorBufferSize);
			}
			return it;
		}

		final POBufferedIterator<T, ET> poBufferedIterator = new POBufferedIterator<>(this, clazz, null);
		if (iteratorBufferSize != null)
		{
			poBufferedIterator.setBufferSize(iteratorBufferSize);
		}
		else if (limit.isLimited())
		{   // use the set limit as our buffer size, if a limit has been set
			poBufferedIterator.setBufferSize(limit.toInt());
		}
		return poBufferedIterator;

	}

	/**
	 * Return a simple wrapper over a JDBC {@link ResultSet}. It is the caller responsibility to call the close method to release the underlying database resources.
	 *
	 * @return POResultSet
	 */
	public <ET extends PO> POResultSet<ET> scroll() throws DBException
	{
		return scroll(null/*clazz*/);
	}

	public <ET> POResultSet<ET> scroll(final Class<ET> clazz) throws DBException
	{
		final String tableName = getTableName();
		final String sql = buildSQL(null, null, null, true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		POResultSet<ET> rsPO = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = createResultSet(pstmt);
			rsPO = new POResultSet<>(ctx, tableName, clazz, pstmt, rs, trxName);
			rsPO.setCloseOnError(true);
			return rsPO;
		}
		catch (final SQLException e)
		{
			log.info(sql, e);
			throw new DBException(e, sql, getParametersEffective());
		}
		finally
		{
			// If there was an error, then close the statement and resultset
			if (rsPO == null)
			{
				DB.close(rs, pstmt);
			}
		}
	}

	/**
	 * Create a new {@link TypedSqlQuery} object and set it's whereClause
	 */
	public TypedSqlQuery<T> setWhereClause(final String whereClause)
	{
		final TypedSqlQuery<T> query = copy();
		query.whereClause = whereClause;
		return query;
	}

	@Override
	public TypedSqlQuery<T> setPostQueryFilter(final IQueryFilter<T> postQueryFilter)
	{
		this.postQueryFilter = postQueryFilter;
		return this;
	}

	/**
	 * This method returns a copy of this instance.
	 * <p>
	 * If the given <code>whereClause</code> is not empty, then it is <code>AND</code>ed or <code>OR</code>ed to the copy's current where clause. appended.
	 *
	 * @param joinByAnd if <code>true</code>, then the given <code>whereClause</code> (unless empty) is <code>AND</code>ed, otherwise it's <code>OR</code>ed to the new query's where clause.
	 * @return a copy of this instance
	 */
	public TypedSqlQuery<T> addWhereClause(final boolean joinByAnd, final String whereClause)
	{
		if (Check.isEmpty(whereClause, true))
		{
			return copy();
		}

		final String whereClauseFinal;
		if (Check.isNotBlank(getWhereClause()))
		{
			whereClauseFinal = new StringBuilder()
					.append("(").append(getWhereClause()).append(")")
					.append(joinByAnd ? " AND " : " OR ")
					.append("(").append(whereClause).append(")")
					.toString();
		}
		else
		{
			whereClauseFinal = whereClause;
		}

		return setWhereClause(whereClauseFinal);
	}

	public String getWhereClause()
	{
		return whereClause;
	}

	protected final String getWhereClauseEffective()
	{
		final StringBuilder whereBuffer = new StringBuilder();
		if (Check.isNotBlank(this.whereClause))
		{
			if (whereBuffer.length() > 0)
			{
				whereBuffer.append(" AND ");
			}
			whereBuffer.append("(").append(this.whereClause).append(")");
		}
		if (this.onlyActiveRecords)
		{
			if (whereBuffer.length() > 0)
			{
				whereBuffer.append(" AND ");
			}
			whereBuffer.append("IsActive=?");
		}
		if (this.onlyClient_ID)    // red1
		{
			if (whereBuffer.length() > 0)
			{
				whereBuffer.append(" AND ");
			}
			whereBuffer.append("AD_Client_ID=?");
		}

		//
		// IN selection
		if (this.onlySelectionId != null)
		{
			final String keyColumnName = getKeyColumnName();
			//
			if (whereBuffer.length() > 0)
			{
				whereBuffer.append(" AND ");
			}
			whereBuffer.append(" EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID=? AND s.T_Selection_ID=" + getTableName() + "." + keyColumnName + ")");
		}

		//
		// NOT IN selection
		if (this.notInSelectionId != null)
		{
			final String keyColumnName = getKeyColumnName();
			//
			if (whereBuffer.length() > 0)
			{
				whereBuffer.append(" AND ");
			}
			whereBuffer.append(" NOT EXISTS (SELECT 1 FROM T_Selection s WHERE s.AD_PInstance_ID=? AND s.T_Selection_ID=" + getTableName() + "." + keyColumnName + ")");
		}

		return whereBuffer.toString();
	}

	@NonNull
	public final List<Object> getParametersEffective()
	{
		final List<Object> parametersEffective = new ArrayList<>();

		if (parameters != null && !parameters.isEmpty())
		{
			parametersEffective.addAll(parameters);
		}

		if (this.onlyActiveRecords)
		{
			parametersEffective.add(true);
			log.trace("Parameter IsActive = Y");
		}
		if (this.onlyClient_ID)
		{
			final int AD_Client_ID = Env.getAD_Client_ID(ctx);
			parametersEffective.add(AD_Client_ID);
			log.trace("Parameter AD_Client_ID = {}", AD_Client_ID);
		}
		if (this.onlySelectionId != null)
		{
			parametersEffective.add(this.onlySelectionId);
			log.trace("Parameter Selection AD_PInstance_ID = {}", this.onlySelectionId);
		}
		if (this.notInSelectionId != null)
		{
			parametersEffective.add(this.notInSelectionId);
			log.trace("Parameter NotInSelection AD_PInstance_ID = {}", this.notInSelectionId);
		}

		//
		// Append parameters from UNIONs
		if (unions != null && !unions.isEmpty())
		{
			for (final SqlQueryUnion<T> union : unions)
			{
				final TypedSqlQuery<T> unionQuery = cast(union.getQuery());
				final List<Object> unionSqlParams = unionQuery.getParametersEffective();
				parametersEffective.addAll(unionSqlParams);
			}
		}

		return parametersEffective;
	}

	/**
	 * Build SQL Clause
	 *
	 * @param selectClause optional; if null the select clause will be build according to POInfo
	 * @param fromClause optional; if null the from clause will be build according to {@link #getSqlFrom()}
	 * @param useOrderByClause true if ORDER BY clause shall be appended
	 * @return final SQL
	 */
	public final String buildSQL(
			@Nullable final CharSequence selectClause,
			@Nullable final CharSequence fromClause,
			@Nullable final CharSequence groupByClause,
			final boolean useOrderByClause)
	{
		CharSequence selectClauseToUse = selectClause;
		if (selectClauseToUse == null)
		{
			final POInfo info = getPOInfo();
			selectClauseToUse = new StringBuilder("SELECT ").append(info.getSqlSelectColumns());
		}
		CharSequence fromClauseToUse = fromClause;
		if (fromClauseToUse == null)
		{
			fromClauseToUse = new StringBuilder(" FROM ").append(getSqlFrom());
		}

		final StringBuilder sqlBuffer = new StringBuilder(selectClauseToUse)
				.append(" ")
				.append(fromClauseToUse);

		final String whereClauseEffective = getWhereClauseEffective();
		if (whereClauseEffective != null && !whereClauseEffective.isEmpty())
		{
			sqlBuffer.append("\n WHERE ").append(whereClauseEffective);
		}

		//
		// Build and add UNION SQL queries
		if (unions != null)
		{
			final boolean useOrderByClauseInUnions = Check.isBlank(getOrderBy());

			for (final SqlQueryUnion<T> union : unions)
			{
				final TypedSqlQuery<T> unionQuery = TypedSqlQuery.cast(union.getQuery());

				final boolean unionDistinct = union.isDistinct();

				final String unionSql = unionQuery.buildSQL(
						selectClause,
						null/* don't assume the union-query's from-clause is identical! */,
						null/* groupByClause */,
						useOrderByClauseInUnions);
				sqlBuffer.append("\nUNION ").append(unionDistinct ? "DISTINCT" : "ALL");
				sqlBuffer.append("\n(\n").append(unionSql).append("\n)\n");
			}
		}

		if (groupByClause != null && groupByClause.length() > 0)
		{
			sqlBuffer.append("\n GROUP BY ").append(groupByClause);
		}

		if (useOrderByClause)
		{
			final String orderBy = getOrderBy();
			if (Check.isNotBlank(orderBy))
			{
				sqlBuffer.append("\n ORDER BY ").append(orderBy);
			}
		}

		String sql = sqlBuffer.toString();
		if (requiredAccess != null)
		{
			final IUserRolePermissions role = Env.getUserRolePermissions(this.ctx);
			sql = role.addAccessSQL(sql, getTableName(), IUserRolePermissions.SQL_FULLYQUALIFIED, requiredAccess);
		}

		// metas: begin
		if (hasLimitOrOffset())
		{
			if (DB.getDatabase().isPagingSupported())
			{
				Check.assumeNull(postQueryFilter, "No post-filter shall be defined when using LIMIT/OFFSET"); // FIXME: implement

				final int offsetFixed = Math.max(offset, 0);
				final int start = offsetFixed + 1;
				final int end = limit.toIntOrZero() + offsetFixed;
				sql = DB.getDatabase().addPagingSQL(sql, start, end);
			}
			else
			{
				log.error("Paging is not supported. Ignored", new Exception());
			}
		}
		// metas: end

		if (LogManager.isLevelFinest())
		{
			log.trace("TableName = " + getTableName() + "... SQL = " + sql); // red1 - to assist in debugging SQL
		}
		return sql;
	}

	private ResultSet createResultSet(final PreparedStatement pstmt) throws SQLException
	{
		final List<Object> parametersEffective = getParametersEffective();
		DB.setParameters(pstmt, parametersEffective);

		final long ts = System.currentTimeMillis();
		final ResultSet rs = pstmt.executeQuery();

		final long durationMillis = System.currentTimeMillis() - ts;
		final int duarationMaxMinutes = 5;
		final long durationMaxMillis = 1000 * 60 * duarationMaxMinutes; // durationMillis is always >0
		if (durationMillis > durationMaxMillis)
		{
			log.warn(
					"Query " + this + " took " + durationMillis + " millis (longer than " + duarationMaxMinutes + " minutes) to create the ResultSet",
					new Exception("Just to print the Stacktrace"));
		}

		return rs;
	}

	/**
	 * Get a Array with the IDs for this Query
	 *
	 * @return Get a Array with the IDs
	 */
	public int[] getIDs()
	{
		// Convert to array
		final List<Integer> idsList = listIds();
		final int[] retValue = new int[idsList.size()];
		for (int i = 0; i < retValue.length; i++)
		{
			retValue[i] = idsList.get(i);
		}
		return retValue;
	}    // get_IDs

	@Override
	public List<Integer> listIds()
	{
		final String keyColumnName = getKeyColumnName();

		final StringBuilder selectClause = new StringBuilder("SELECT ").append(keyColumnName);
		final StringBuilder fromClause = new StringBuilder(" FROM ").append(getSqlFrom());
		final String groupByClause = null;
		final String sql = buildSQL(selectClause, fromClause, groupByClause, true);

		final List<Integer> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = createResultSet(pstmt);
			while (rs.next())
			{
				final int recordId = rs.getInt(1);
				list.add(recordId);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, getParametersEffective());
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return list;
	}

	@Override
	public String toString()
	{
		final String selectClause = "SELECT *";
		final String fromClause = null;
		final String groupByClause = null;
		final boolean useOrderByClause = true;
		String sql = buildSQL(
				selectClause,
				fromClause,
				groupByClause,
				useOrderByClause);

		final List<Object> sqlParams = getParametersEffective();
		if (!sqlParams.isEmpty())
		{
			sql = inlineSqlParams(sql, sqlParams);
		}

		return sql;
	}

	@VisibleForTesting
	static String inlineSqlParams(final String sql, final List<Object> params)
	{
		return sqlParamsInliner.inline(sql, params);
	}

	// metas
	@Override
	public TypedSqlQuery<T> setLimit(@NonNull final QueryLimit limit)
	{
		this.limit = limit;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setLimit(@NonNull final QueryLimit limit, final int offset)
	{
		this.limit = limit;
		this.offset = offset;
		return this;
	}

	/**
	 * @return true if the query has the LIMIT or OFFSET set
	 */
	public boolean hasLimitOrOffset()
	{
		return this.limit.isLimited() || this.offset >= 0;
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

	@Nullable
	public String getOrderBy()
	{
		if (queryOrderBy == null)
		{
			return null;
		}
		return queryOrderBy.getSql();
	}

	@Override
	public final String getTableName()
	{
		return this.tableName;
	}

	@Override
	public final Class<T> getModelClass()
	{
		return modelClass;
	}

	/**
	 * Gets single key column name.
	 *
	 * @return key column name.
	 * @throws DBException if table does not have a key column or it has composed primary key
	 */
	public String getKeyColumnName()
	{
		final POInfo poInfo = getPOInfo();
		final String keyColumnName = poInfo.getKeyColumnName();
		if (keyColumnName != null)
		{
			return keyColumnName;
		}

		//
		// No single primary key was found.
		// Build a nice error message and throw it.
		final List<String> keys = poInfo.getKeyColumnNames();
		if (keys.isEmpty())
		{
			throw new DBException("Table " + getTableName() + " has no key columns");
		}
		else
		{
			throw new DBException("Table " + getTableName() + " has more than one key column defined: " + keys);
		}
	}

	public List<String> getKeyColumnNames()
	{
		final POInfo poInfo = getPOInfo();
		return poInfo.getKeyColumnNames();

	}

	@Nullable
	private Map<String, Object> options = null;

	@Override
	public TypedSqlQuery<T> setOption(final String name, final Object value)
	{
		if (options == null)
		{
			options = new HashMap<>();
		}
		options.put(name, value);

		return this;
	}

	@Override
	public TypedSqlQuery<T> setOptions(final Map<String, Object> options)
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

	@Nullable
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

	protected TypedSqlQuery<T> newInstance()
	{
		return new TypedSqlQuery<>(ctx, modelClass, tableName, whereClause, trxName);
	}

	/**
	 * @return a copy of this object
	 */
	@Override
	public TypedSqlQuery<T> copy()
	{
		final TypedSqlQuery<T> queryTo = newInstance();
		queryTo.sqlFrom = sqlFrom;
		queryTo.whereClause = whereClause;
		queryTo.postQueryFilter = postQueryFilter;
		//
		queryTo.queryOrderBy = queryOrderBy;
		queryTo.requiredAccess = requiredAccess;
		queryTo.onlyActiveRecords = onlyActiveRecords;
		queryTo.onlyClient_ID = onlyClient_ID;
		queryTo.onlySelectionId = onlySelectionId;
		queryTo.notInSelectionId = notInSelectionId;
		queryTo.limit = limit;
		queryTo.offset = offset;
		queryTo.unions = unions == null ? null : new ArrayList<>(unions);

		if (parameters == null)
		{
			queryTo.parameters = null;
		}
		else
		{
			queryTo.parameters = new ArrayList<>(parameters);
		}

		queryTo.options = options == null ? null : new HashMap<>(options);

		return queryTo;
	}

	@Override
	public Object clone()
	{
		return copy();
	}

	/**
	 * Inserts the query result into a <code>T_Selection</code> for the given AD_PInstance_ID
	 *
	 * @return number of records inserted in selection
	 */
	@Override
	public int createSelection(@NonNull final PInstanceId pinstanceId)
	{
		final String keyColumnName = getKeyColumnName();

		final StringBuilder selectClause = new StringBuilder(80)
				.append("INSERT INTO T_SELECTION(AD_PINSTANCE_ID, T_SELECTION_ID) ")
				.append(" SELECT ")
				.append(pinstanceId.getRepoId())
				.append(", ").append(keyColumnName);
		final StringBuilder fromClause = new StringBuilder(" FROM ").append(getSqlFrom());
		final String groupByClause = null;

		final String sql = buildSQL(selectClause, fromClause, groupByClause, false);
		final Object[] params = getParametersEffective().toArray();

		return DB.executeUpdateEx(sql, params, trxName);
	}

	@Nullable
	@Override
	public PInstanceId createSelection()
	{
		// Create new AD_PInstance_ID for our selection
		final PInstanceId newSelectionId = Services.get(IADPInstanceDAO.class).createSelectionId();

		// Populate the selection
		final int count = createSelection(newSelectionId);
		if (count <= 0)
		{
			return null;
		}

		return newSelectionId;
	}

	@Override
	public int deleteDirectly()
	{
		// NOTE: avoid leading/trailing "spaces" in sqlDeleteFrom and fromClause,
		// in order to be matched by our migration scripts "dontLog" matcher.
		// In case of fromClause we need a trailing space.
		final StringBuilder sqlDeleteFrom = new StringBuilder("DELETE");
		final StringBuilder fromClause = new StringBuilder("FROM ").append(getTableName()).append(" ");
		final String groupByClause = null;
		final String sql = buildSQL(sqlDeleteFrom, fromClause, groupByClause, false); // useOrderByClause=false
		final Object[] params = getParametersEffective().toArray();

		return DB.executeUpdateEx(sql, params, trxName);
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

	/**
	 * Casts given {@link IQuery} object to {@link TypedSqlQuery}.
	 * <p>
	 * We use this method to track where we do "interface casted to native implementation". This information is useful if we decide to refactor this class in future.
	 */
	public static <T> TypedSqlQuery<T> cast(final IQuery<T> query)
	{
		return (TypedSqlQuery<T>)query;
	}

	@Override
	public int updateDirectly(@NonNull final IQueryUpdater<T> queryUpdater)
	{
		// Check if it's an ISqlQueryUpdater then we can update it directly
		if (queryUpdater instanceof ISqlQueryUpdater)
		{
			final ISqlQueryUpdater<T> sqlQueryUpdater = (ISqlQueryUpdater<T>)queryUpdater;
			return updateSql(sqlQueryUpdater);
		}
		//
		// Else: fallback to standard updater
		else
		{
			return update(queryUpdater);
		}
	}

	@Override
	public int update(final IQueryUpdater<T> queryUpdater)
	{
		final Iterator<T> records = iterate(modelClass, true); // guaranteed=true
		try
		{
			int countUpdated = 0;
			while (records.hasNext())
			{
				final T record = records.next();
				final boolean updated = queryUpdater.update(record);
				if (updated)
				{
					InterfaceWrapperHelper.save(record);
					countUpdated++;
				}

			}
			return countUpdated;
		}
		finally
		{
			IteratorUtils.closeQuietly(records);
		}

	}

	private int updateSql(final ISqlQueryUpdater<T> sqlQueryUpdater)
	{
		// In case we have LIMIT/OFFSET clauses, we shall update the records differently
		// (i.e. by having a UPDATE FROM (sub select) ).
		final boolean useSelectFromSubQuery = this.limit.isLimited() || this.offset >= 0;
		if (useSelectFromSubQuery)
		{
			return updateSql_UsingSelectFromSubQuery(sqlQueryUpdater);
		}

		final List<Object> sqlParams = new ArrayList<>();
		final String sqlUpdateSet = sqlQueryUpdater.getSql(getCtx(), sqlParams);

		final StringBuilder sqlUpdate = new StringBuilder("UPDATE ")
				.append(getTableName())
				.append(" SET ").append(sqlUpdateSet);
		final String fromClause = "";
		final String groupByClause = null;

		final String sql = buildSQL(sqlUpdate, fromClause, groupByClause, false); // useOrderByClause=false
		final List<Object> sqlWhereClauseParams = getParametersEffective();
		sqlParams.addAll(sqlWhereClauseParams);

		return DB.executeUpdateEx(sql, sqlParams.toArray(), getTrxName());
	}

	/**
	 * Builds the update SQL using a sub query for select.
	 * <p>
	 * i.e.
	 *
	 * <pre>
	 * UPDATE t .. FROM (SELECT subquery) f WHERE t.rowid = f.rowid
	 * </pre>
	 *
	 * @return how many rows were updated
	 */
	private int updateSql_UsingSelectFromSubQuery(final ISqlQueryUpdater<T> sqlQueryUpdater)
	{
		//
		// Get the key column name / row id
		final String tableName = getTableName();
		final POInfo info = getPOInfo();
		final String keyColumnName = info.getKeyColumnName();
		if (keyColumnName == null)
		{
			throw new AdempiereException("Cannot update table `" + tableName + "`directly because it does not have a single primary key defined");
		}

		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder(100);

		//
		// UPDATE
		final String sqlUpdateSet = sqlQueryUpdater.getSql(getCtx(), sqlParams);
		sql.append("UPDATE ").append(tableName).append(" t ")
				.append("\n SET ").append(sqlUpdateSet);

		//
		// FROM
		{
			final StringBuilder sqlFrom_Select = new StringBuilder()
					.append("\n SELECT ").append(info.getSqlSelectColumns())
					.append("\n , ").append(keyColumnName).append(" as ZZ_RowId");

			final StringBuilder fromClause = new StringBuilder(" FROM ").append(getSqlFrom());

			final String groupByClause = null;

			final String sqlFrom = buildSQL(sqlFrom_Select, fromClause, groupByClause, true);

			sql.append("\n FROM (").append(sqlFrom).append(") f ");
			sqlParams.addAll(getParametersEffective());
		}

		//
		// WHERE
		sql.append("\n WHERE t.").append(keyColumnName).append(" = f.ZZ_RowId");

		//
		// Execute
		return DB.executeUpdateEx(sql.toString(), sqlParams.toArray(), getTrxName());
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

	public boolean hasUnions()
	{
		final List<SqlQueryUnion<T>> unions = this.unions;
		return unions != null && !unions.isEmpty();
	}

	@Override
	<ToModelType> QueryInsertExecutorResult executeInsert(
			@NonNull final QueryInsertExecutor<ToModelType, T> queryInserter)
	{
		Check.assume(!queryInserter.isEmpty(), "At least one column to be inserted needs to be specified: {}", queryInserter);

		final List<Object> sqlParams = new ArrayList<>();
		final boolean collectSqlParamsFromSelectClause = unions == null || unions.isEmpty();

		final TokenizedStringBuilder sqlToSelectColumns = new TokenizedStringBuilder("\n, ")
				.setAutoAppendSeparator(true);
		final TokenizedStringBuilder sqlFromSelectColumns = new TokenizedStringBuilder("\n, ")
				.setAutoAppendSeparator(true);

		for (final Map.Entry<String, IQueryInsertFromColumn> toColumnName2from : queryInserter.getColumnMapping().entrySet())
		{
			// To
			final String toColumnName = toColumnName2from.getKey();
			sqlToSelectColumns.append(toColumnName);

			// From
			final IQueryInsertFromColumn from = toColumnName2from.getValue();
			final String fromSql = from.getSql(collectSqlParamsFromSelectClause ? sqlParams : null);
			sqlFromSelectColumns.append(fromSql);
		}

		//
		// Build sql: SELECT ... FROM ... WHERE ...
		sqlFromSelectColumns.asStringBuilder().insert(0, "SELECT \n");

		final StringBuilder fromClause = new StringBuilder(" FROM ").append(getSqlFrom());

		final String groupByClause = null;

		final String sqlFrom = buildSQL(sqlFromSelectColumns.asStringBuilder(), fromClause, groupByClause, false); // useOrderByClause=false
		sqlParams.addAll(getParametersEffective());

		//
		// Build sql: INSERT INTO ... SELECT ... FROM ... WHERE ...
		final StringBuilder sqlInsert = new StringBuilder()
				.append("INSERT INTO ").append(queryInserter.getToTableName()).append(" (")
				.append("\n").append(sqlToSelectColumns)
				.append("\n)\n")
				.append(sqlFrom);

		//
		// Wrap the INSERT SQL and create the insert selection ID if required
		final PInstanceId insertSelectionId;
		final String sql;
		if (queryInserter.isCreateSelectionOfInsertedRows())
		{
			insertSelectionId = Services.get(IADPInstanceDAO.class).createSelectionId();

			final String toKeyColumnName = queryInserter.getToKeyColumnName();
			sql = new StringBuilder()
					.append("WITH insert_code AS (")
					.append("\n").append(sqlInsert)
					.append("\n RETURNING ").append(toKeyColumnName)
					.append("\n )")
					//
					.append("\n INSERT INTO T_Selection (AD_PInstance_ID, T_Selection_ID)")
					.append("\n SELECT ").append(insertSelectionId.getRepoId()).append(", ").append(toKeyColumnName).append(" FROM insert_code")
					//
					.toString();
		}
		else
		{
			insertSelectionId = null;
			sql = sqlInsert.toString();
		}

		//
		// Execute the INSERT and return how many records were inserted
		final int countInsert = DB.executeUpdateEx(sql, sqlParams.toArray(), getTrxName());
		return QueryInsertExecutorResult.of(countInsert, insertSelectionId);
	}
}
