/*******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA *
 * 02111-1307 USA. *
 * *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com *
 * Contributor(s): *
 * Teo Sarca, www.arhipac.ro *
 * __________________________________________ *
 ******************************************************************************/
package org.adempiere.ad.dao.impl;

import lombok.NonNull;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryInsertExecutor.QueryInsertExecutorResult;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.text.TokenizedStringBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POResultSet;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;

/**
 *
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

	private final Properties ctx;
	private final String tableName;
	private String sqlFrom = null;
	private POInfo _poInfo;
	private final Class<T> modelClass;
	private String whereClause;
	private final String trxName;

	private IQueryOrderBy queryOrderBy = null;
	private List<Object> parameters = null;
	private IQueryFilter<T> postQueryFilter;
	private boolean applyAccessFilter = false;
	private boolean applyAccessFilterRW = false;
	private boolean onlyActiveRecords = false;
	private boolean onlyClient_ID = false;
	private PInstanceId onlySelectionId;
	private PInstanceId notInSelectionId;

	private int limit = NO_LIMIT;
	private int offset = NO_LIMIT;

	private List<SqlQueryUnion<T>> unions;

	/**
	 *
	 * @param ctx
	 * @param tableName
	 * @param whereClause
	 * @param trxName
	 */
	protected TypedSqlQuery(final Properties ctx, final Class<T> modelClass, final String tableName, final String whereClause, final String trxName)
	{
		super();
		Check.assumeNotNull(ctx, "ctx not null");

		this.modelClass = modelClass;
		this.tableName = InterfaceWrapperHelper.getTableName(modelClass, tableName);

		this.ctx = ctx;

		this.whereClause = whereClause;
		this.trxName = trxName;
	}

	public TypedSqlQuery(final Properties ctx, final Class<T> modelClass, final String whereClause, final String trxName)
	{
		this(ctx,
				modelClass,
				(String)null, // tableName
				whereClause,
				trxName);
	}

	/**
	 * @return {@link POInfo}; never returns null
	 */
	private final POInfo getPOInfo()
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
	private final String getSqlFrom()
	{
		if (sqlFrom == null || sqlFrom.isEmpty())
		{
			return getTableName();
		}
		return sqlFrom;
	}

	/**
	 * Set query parameters
	 *
	 * @param parameters
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
	public TypedSqlQuery<T> setApplyAccessFilter(final boolean flag)
	{
		this.applyAccessFilter = flag;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setApplyAccessFilterRW(final boolean RW)
	{
		this.applyAccessFilter = true;
		this.applyAccessFilterRW = RW;
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
	 *
	 * @return List
	 * @throws DBException
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
		if (limit > 0 && limit <= 100)
		{
			list = new ArrayList<>(limit);
		}
		else
		{
			// TODO: check if we shall go with LinkedList in this case
			list = new ArrayList<>();
		}

		final String sql = buildSQL(null, true);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = createResultSet(pstmt);

			ET model = null;
			while ((model = retrieveNextModel(rs, clazz)) != null)
			{
				list.add(model);

				if (limit > 0 && list.size() >= limit)
				{
					log.debug("Limit of " + limit + " reached. Stop.");
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
			rs = null;
			pstmt = null;
		}
		return list;
	}

	/**
	 * Move <code>rs</code>'s cursor forward and get next model.
	 *
	 * If we have a post-filter (see {@link #setPostQueryFilter(IQueryFilter)}), the model will be validated againt it and if not matched next row will be checked.
	 *
	 * @param rs
	 * @param clazz
	 * @return model or null if we reached the and of the cursor
	 * @throws SQLException
	 */
	private final <ET extends T> ET retrieveNextModel(final ResultSet rs, final Class<ET> clazz) throws SQLException
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
				continue;
			}
		}

		return null;
	}

	/**
	 * Retrieve model from given {@link ResultSet}.
	 *
	 * @param rs
	 * @param clazz
	 * @return next model
	 */
	private final <ET extends T> ET retrieveModel(final ResultSet rs, final Class<ET> clazz)
	{
		final String tableName = getTableName();

		final Class<?> modelClassToUse = clazz != null ? clazz : this.modelClass;
		final ET model = TableModelLoader.instance.retrieveModel(ctx, tableName, modelClassToUse, rs, trxName);
		return model;
	}

	/**
	 * Return first PO that match query criteria
	 *
	 * @return first PO
	 * @throws DBException
	 */
	@Override
	public <ET extends T> ET first() throws DBException
	{
		return first(null);
	}

	@Override
	public <ET extends T> ET first(final Class<ET> clazz) throws DBException
	{
		ET model = null;

		// metas: begin: not using ORDER BY clause can be a developer error
		final String orderBy = getOrderBy();
		if (Check.isEmpty(orderBy, true))
		{
			final AdempiereException ex = new AdempiereException("Using first() without an ORDER BY clause can be a developer error."
					+ " Please specify ORDER BY clause or in case you know that only one result shall be returned then use firstOnly()."
					+ " Query: " + toString());
			log.warn(ex.getLocalizedMessage(), ex);
		}
		// metas: end

		final String sql = buildSQL(null, true);

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
			rs = null;
			pstmt = null;
		}

		return model;
	}

	/**
	 * Return first PO that match query criteria. If there are more records that match criteria an exception will be throwed
	 *
	 * @return first PO
	 * @throws DBException
	 * @see {@link #first()}
	 */
	public <ET extends T> ET firstOnly() throws DBException
	{
		final Class<ET> clazz = null;
		final boolean throwExIfMoreThenOneFound = true;
		return firstOnly(clazz, throwExIfMoreThenOneFound);
	}

	/**
	 *
	 * @param clazz
	 * @param throwExIfMoreThenOneFound if true and there more then one record found it will throw exception, <code>null</code> will be returned otherwise.
	 * @return model or null
	 * @throws DBException
	 */
	@Override
	protected final <ET extends T> ET firstOnly(final Class<ET> clazz, final boolean throwExIfMoreThenOneFound) throws DBException
	{
		ET model = null;
		final String sql = buildSQL(
				null,    // selectClause: use default (i.e. all columns)
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
					throw new DBMoreThenOneRecordsFoundException(this.toString());
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
			rs = null;
			pstmt = null;
		}

		return model;
	}

	/**
	 * Return first ID
	 *
	 * @return first ID or -1 if not found
	 * @throws DBException
	 */
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

		final StringBuilder selectClause = new StringBuilder("SELECT ");
		selectClause.append(keyColumnName);
		selectClause.append(" FROM ").append(getSqlFrom());
		final String sql = buildSQL(selectClause, true);

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
			rs = null;
			pstmt = null;
		}
		//
		return id;
	}

	/**
	 * red1 - returns full SQL string - for caller needs
	 *
	 * @return buildSQL(null,true)
	 *
	 */
	public String getSQL() throws DBException
	{
		return buildSQL(null, true);
	}

	/**
	 * Aggregate given expression on this criteria
	 *
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

	public <AT> List<AT> aggregateList(
			@NonNull String sqlExpression,
			@NonNull final Aggregate aggregateType,
			@NonNull final Class<AT> returnType)
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
				sqlExpression = "*";
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
		sqlSelect.append(" FROM ").append(getSqlFrom());

		final String sql = buildSQL(sqlSelect, aggregateType.isUseOrderByClause());

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, this.trxName);
			rs = createResultSet(pstmt);
			while (rs.next())
			{
				final AT value = DB.retrieveValueOrDefault(rs, 1, returnType);
				if (value == null)
				{
					continue; // Skip null values
				}
				result.add(value);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
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
		setLimit(1, 0);
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
				.append(sqlColumnNames)
				.append(" FROM ").append(getSqlFrom());
		final boolean useOrderByClause = !distinct;
		final String sql = buildSQL(sqlSelect, useOrderByClause);

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
			rs = null;
			pstmt = null;
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
	 * @param sqlExpression
	 * @return sum
	 */
	public BigDecimal sum(final String sqlExpression)
	{
		return aggregate(sqlExpression, Aggregate.SUM);
	}

	@Override
	public boolean match() throws DBException
	{
		final StringBuilder sqlSelect;
		if (postQueryFilter != null)
		{
			// we expect to build the select with all columns
			// because we will want to retrieve models and match them again our post-filter
			sqlSelect = null;
		}
		else
		{
			setLimit(1); // we don't need more than one row to decide if it matches
			sqlSelect = new StringBuilder("SELECT 1 FROM ")
					.append(getSqlFrom());
		}
		final String sql = buildSQL(sqlSelect, false/*useOrderByClause*/);
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
	 * @throws DBException
	 */
	public <ET extends T> Iterator<ET> iterate() throws DBException
	{
		return iterate((Class<ET>)null);
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

		// metas: 03658: use POBufferedIterator instead of old POIterator, if database paging is supported
		else if (DB.getDatabase().isPagingSupported())
		{
			final POBufferedIterator<T, ET> poBufferedIterator = new POBufferedIterator<>(this, clazz, null);
			if (iteratorBufferSize != null)
			{
				poBufferedIterator.setBufferSize(iteratorBufferSize);
			}
			else if (limit != NO_LIMIT)
			{   // use the set limit as our buffer size, if a limit has been set
				poBufferedIterator.setBufferSize(limit);
			}
			return poBufferedIterator;
		}
		else
		{
			final String tableName = getTableName();
			final List<Object[]> idList = retrieveComposedIDs();
			return new POIterator<>(ctx, tableName, clazz, idList, trxName);
		}
	}

	/**
	 * Get a List of composed IDs for this Query.
	 *
	 * @return List of composed IDs
	 */
	private final List<Object[]> retrieveComposedIDs()
	{
		Check.assumeNull(postQueryFilter, "No post-filter shall be defined when retrieving composed IDs"); // FIXME: not supported

		final StringBuilder sqlBuffer = new StringBuilder();
		final List<String> keyColumnNames = getKeyColumnNames();
		for (final String keyColumnName : keyColumnNames)
		{
			if (sqlBuffer.length() > 0)
			{
				sqlBuffer.append(", ");
			}
			sqlBuffer.append(keyColumnName);
		}
		sqlBuffer.insert(0, " SELECT ");
		sqlBuffer.append(" FROM ").append(getSqlFrom());
		final String sql = buildSQL(sqlBuffer, true);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final List<Object[]> idList = new ArrayList<>();
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = createResultSet(pstmt);
			while (rs.next())
			{
				final Object[] ids = new Object[keyColumnNames.size()];
				for (int i = 0; i < ids.length; i++)
				{
					ids[i] = rs.getObject(i + 1);
				}
				idList.add(ids);
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
			rs = null;
			pstmt = null;
		}

		return idList;
	}

	/**
	 * Return a simple wrapper over a JDBC {@link ResultSet}. It is the caller responsibility to call the close method to release the underlying database resources.
	 *
	 * @return POResultSet
	 * @throws DBException
	 */
	public <ET extends PO> POResultSet<ET> scroll() throws DBException
	{
		return scroll((Class<ET>)null);
	}

	public <ET> POResultSet<ET> scroll(final Class<ET> clazz) throws DBException
	{
		final String tableName = getTableName();
		final String sql = buildSQL(null, true);
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
				rs = null;
				pstmt = null;
			}
		}
	}

	/**
	 * Create a new {@link TypedSqlQuery} object and set it's whereClause
	 *
	 * @param whereClause
	 * @return
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
	 *
	 * If the given <code>whereClause</code> is not empty, then it is <code>AND</code>ed or <code>OR</code>ed to the copy's current where clause. appended.
	 *
	 * @param joinByAnd if <code>true</code>, then the given <code>whereClause</code> (unless empty) is <code>AND</code>ed, otherwise it's <code>OR</code>ed to the new query's where clause.
	 * @param whereClause
	 * @return a copy of this instance
	 */
	public TypedSqlQuery<T> addWhereClause(final boolean joinByAnd, final String whereClause)
	{
		if (Check.isEmpty(whereClause, true))
		{
			return copy();
		}

		final String whereClauseFinal;
		if (!Check.isEmpty(getWhereClause(), true))
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
		if (!Check.isEmpty(this.whereClause, true))
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
	 * @param useOrderByClause true if ORDER BY clause shall be appended
	 * @return final SQL
	 */
	public final String buildSQL(StringBuilder selectClause, final boolean useOrderByClause)
	{
		if (selectClause == null)
		{
			final POInfo info = getPOInfo();
			selectClause = new StringBuilder()
					.append("SELECT ").append(info.getSqlSelectColumns())
					.append("\n FROM ").append(getSqlFrom());
		}

		final StringBuilder sqlBuffer = new StringBuilder(selectClause);

		final String whereClauseEffective = getWhereClauseEffective();
		if (whereClauseEffective != null && !whereClauseEffective.isEmpty())
		{
			sqlBuffer.append("\n WHERE ").append(whereClauseEffective);
		}

		//
		// Build and add UNION SQL queries
		if (unions != null && !unions.isEmpty())
		{
			for (final SqlQueryUnion<T> union : unions)
			{
				final TypedSqlQuery<T> unionQuery = TypedSqlQuery.cast(union.getQuery());
				final boolean unionDistinct = union.isDistinct();

				final String unionSql = unionQuery.buildSQL(selectClause, false); // useOrderByClause=false
				sqlBuffer.append("\nUNION ").append(unionDistinct ? "DISTINCT" : "ALL");
				sqlBuffer.append("\n(\n").append(unionSql).append("\n)\n");
			}
		}

		final String orderBy = getOrderBy();
		if (useOrderByClause && !Check.isEmpty(orderBy, true))
		{
			sqlBuffer.append("\n ORDER BY ").append(orderBy);
		}

		String sql = sqlBuffer.toString();
		if (applyAccessFilter)
		{
			final IUserRolePermissions role = Env.getUserRolePermissions(this.ctx);
			final boolean applyAccessFilterFullyQualified = true; // metas: shall always be true
			sql = role.addAccessSQL(sql, getTableName(), applyAccessFilterFullyQualified, applyAccessFilterRW);
		}

		// metas: begin
		if (hasLimitOrOffset())
		{
			if (DB.getDatabase().isPagingSupported())
			{
				Check.assumeNull(postQueryFilter, "No post-filter shall be defined when using LIMIT/OFFSET"); // FIXME: implement

				final int offsetFixed = offset > 0 ? offset : 0;
				final int start = offsetFixed + 1;
				final int end = limit + offsetFixed;
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

	private final ResultSet createResultSet(final PreparedStatement pstmt) throws SQLException
	{
		final List<Object> parametersEffective = getParametersEffective();
		DB.setParameters(pstmt, parametersEffective);

		final long ts = System.currentTimeMillis();
		final ResultSet rs = pstmt.executeQuery();

		final long durationMillis = System.currentTimeMillis() - ts;
		final int duarationMaxMinutes = 5;
		final long durationMaxMillis = 1000 * 60 * duarationMaxMinutes;
		if (durationMaxMillis > 0 && durationMillis > durationMaxMillis)
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
	}	// get_IDs

	@Override
	public List<Integer> listIds()
	{
		final String keyColumnName = getKeyColumnName();

		final StringBuilder selectClause = new StringBuilder("SELECT ");
		selectClause.append(keyColumnName);
		selectClause.append(" FROM ").append(getSqlFrom());
		final String sql = buildSQL(selectClause, true);

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
			rs = null;
			pstmt = null;
		}

		return list;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName)
				.add("whereClause", whereClause)
				.add("SqlFrom", this.sqlFrom)
				.add("postQueryFilter", postQueryFilter)
				.add("unions", unions != null && !unions.isEmpty() ? unions : null)
				.add("parameters", parameters != null && !parameters.isEmpty() ? parameters : null)
				.add("limit", limit > 0 ? limit : null)
				.add("offset", offset > 0 ? offset : null)
				.add("trxName", trxName)
				.add("applyAccessFilter", applyAccessFilter ? Boolean.TRUE : null)
				.add("applyAccessFilterRW", applyAccessFilterRW ? Boolean.TRUE : null)
				.add("onlyActiveRecords", onlyActiveRecords ? Boolean.TRUE : null)
				.add("onlySelectionId", onlySelectionId)
				.add("notInSelectionId", notInSelectionId)
				.add("options", options != null && !options.isEmpty() ? options : null)
				.toString();
	}

	// metas
	@Override
	public TypedSqlQuery<T> setLimit(final int limit)
	{
		this.limit = limit;
		return this;
	}

	@Override
	public TypedSqlQuery<T> setLimit(final int limit, final int offset)
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
		return this.limit > 0 || this.offset >= 0;
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
		if (keys == null || keys.isEmpty())
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
	 *
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
		queryTo.applyAccessFilter = applyAccessFilter;
		queryTo.applyAccessFilterRW = applyAccessFilterRW;
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
	 * @param AD_PInstance_ID
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
				.append(", ").append(keyColumnName)
				.append(" FROM ").append(getSqlFrom());

		final String sql = buildSQL(selectClause, false);
		final Object[] params = getParametersEffective().toArray();

		final int no = DB.executeUpdateEx(sql, params, trxName);
		return no;
	}

	@Override
	public PInstanceId createSelection()
	{
		// Create new AD_PInstance_ID for our selection
		final PInstanceId newSelectionId = Services.get(IADPInstanceDAO.class).createPInstanceId();

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
		final StringBuilder sqlDeleteFrom = new StringBuilder("DELETE FROM ").append(getTableName());
		final String sql = buildSQL(sqlDeleteFrom, false); // useOrderByClause=false
		final Object[] params = getParametersEffective().toArray();

		final int no = DB.executeUpdateEx(sql, params, trxName);
		return no;
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

	/**
	 * Casts given {@link IQuery} object to {@link TypedSqlQuery}.
	 *
	 * We use this method to track where we do "interface casted to native implementation". This information is useful if we decide to refactor this class in future.
	 *
	 * @param query
	 * @return
	 */
	public static <T> TypedSqlQuery<T> cast(final IQuery<T> query)
	{
		final TypedSqlQuery<T> typedSqlQuery = (TypedSqlQuery<T>)query;
		return typedSqlQuery;
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

	private final int updateSql(final ISqlQueryUpdater<T> sqlQueryUpdater)
	{
		// In case we have LIMIT/OFFSET clauses, we shall update the records differently
		// (i.e. by having a UPDATE FROM (sub select) ).
		final boolean useSelectFromSubQuery = this.limit > 0 || this.offset >= 0;
		if (useSelectFromSubQuery)
		{
			return updateSql_UsingSelectFromSubQuery(sqlQueryUpdater);
		}

		final List<Object> sqlParams = new ArrayList<>();
		final String sqlUpdateSet = sqlQueryUpdater.getSql(getCtx(), sqlParams);

		final StringBuilder sqlUpdate = new StringBuilder("UPDATE ").append(getTableName())
				.append(" SET ").append(sqlUpdateSet);

		final String sql = buildSQL(sqlUpdate, false); // useOrderByClause=false
		final List<Object> sqlWhereClauseParams = getParametersEffective();
		sqlParams.addAll(sqlWhereClauseParams);

		return DB.executeUpdateEx(sql, sqlParams.toArray(), getTrxName());
	}

	/**
	 * Builds the update SQL using a sub query for select.
	 *
	 * i.e.
	 *
	 * <pre>
	 * UPDATE t .. FROM (SELECT subquery) f WHERE t.rowid = f.rowid
	 * </pre>
	 *
	 * @param sqlQueryUpdater
	 * @return how many rows were updated
	 */
	private final int updateSql_UsingSelectFromSubQuery(final ISqlQueryUpdater<T> sqlQueryUpdater)
	{
		//
		// Get the key column name / row id
		final String tableName = getTableName();
		final POInfo info = getPOInfo();
		String keyColumnName = info.getKeyColumnName();
		if (keyColumnName == null)
		{
			// Fallback if table has no primary key: use database specific ROW ID
			keyColumnName = DB.getDatabase().getRowIdSql(tableName);
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
					.append("\n , ").append(keyColumnName).append(" as ZZ_RowId")
					.append("\n FROM ").append(getSqlFrom());
			final String sqlFrom = buildSQL(sqlFrom_Select, true);

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
	public IQuery<T> addUnion(final IQuery<T> query, final boolean distinct)
	{
		final SqlQueryUnion<T> sqlQueryUnion = new SqlQueryUnion<>(query, distinct);
		if (unions == null)
		{
			unions = new ArrayList<>();
		}
		unions.add(sqlQueryUnion);

		return this;
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
		sqlFromSelectColumns.asStringBuilder()
				.insert(0, "SELECT \n")
				.append("\n FROM ").append(getSqlFrom());
		final String sqlFrom = buildSQL(sqlFromSelectColumns.asStringBuilder(), false); // useOrderByClause=false
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
			insertSelectionId = Services.get(IADPInstanceDAO.class).createPInstanceId();

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
