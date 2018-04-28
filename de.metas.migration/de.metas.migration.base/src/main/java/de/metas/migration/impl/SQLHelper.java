package de.metas.migration.impl;

/*
 * #%L
 * de.metas.migration.base
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

public class SQLHelper
{
	private static final transient Logger logger = LoggerFactory.getLogger(SQLHelper.class.getName());

	private final SQLDatabase database;

	public SQLHelper(final SQLDatabase database)
	{
		super();
		if (database == null)
		{
			throw new IllegalArgumentException("database is null");
		}
		this.database = database;
	}

	private static final List<Object> toList(final Object... params)
	{
		if (params == null || params.length == 0)
		{
			return Collections.emptyList();
		}

		final List<Object> paramsList = Arrays.asList(params);
		return paramsList;
	}

	public int getSQLValueInt(final String sql, final Object... params)
	{
		final List<Object> paramsList = toList(params);
		return getSQLValueInt(sql, paramsList);
	}

	public int getSQLValueInt(final String sql, final List<Object> params)
	{
		final Connection conn = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (!rs.next())
			{
				return 0;
			}
			final int valueInt = rs.getInt(1);
			return valueInt;
		}
		catch (final SQLException e)
		{
			throw new RuntimeException("Error while executing '" + sql + "' on " + database, e);
		}
		finally
		{
			close(rs, pstmt, conn);
		}
	}

	public int executeUpdate(final String sql, final Object... params)
	{
		final List<Object> paramsList = toList(params);
		return executeUpdate(sql, paramsList);
	}

	public int executeUpdate(final String sql, final List<Object> params)
	{
		final Connection conn = database.getConnection();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt, params);
			return pstmt.executeUpdate();
		}
		catch (final SQLException e)
		{
			throw new RuntimeException("Error while executing '" + sql + "' on " + database, e);
		}
		finally
		{
			close(null, pstmt, conn);
		}
	}

	@Builder(builderClassName = "RetrieveRecordsBuilder", builderMethodName = "retrieveRecords", buildMethodName = "execute")
	private <T> Collection<T> retrieveRecordsExecutor(
			@NonNull final String sql,
			@Singular final List<Object> sqlParams,
			@NonNull final Supplier<Collection<T>> collectionFactory,
			@NonNull final ResultSetRowLoader<T> rowLoader)
	{
		final Connection conn = database.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final Collection<T> result = collectionFactory.get();
			while (rs.next())
			{
				final T row = rowLoader.loadRow(rs);
				result.add(row);
			}
			return result;
		}
		catch (final SQLException e)
		{
			throw new RuntimeException("Error while executing '" + sql + "' on " + database, e);
		}
		finally
		{
			close(rs, pstmt, conn);
		}
	}

	/**
	 * Set PreparedStatement's parameter. Similar with calling <code>pstmt.setObject(index, param)</code>
	 *
	 * @param pstmt
	 * @param index
	 * @param param
	 * @throws SQLException
	 */
	public static void setParameter(final PreparedStatement pstmt, final int index, final Object param)
			throws SQLException
	{
		if (param == null)
		{
			pstmt.setObject(index, null);
		}
		else if (param instanceof String)
		{
			pstmt.setString(index, (String)param);
		}
		else if (param instanceof Integer)
		{
			pstmt.setInt(index, ((Integer)param).intValue());
		}
		else if (param instanceof Long)
		{
			pstmt.setLong(index, ((Long)param).longValue());
		}
		else if (param instanceof BigDecimal)
		{
			pstmt.setBigDecimal(index, (BigDecimal)param);
		}
		else if (param instanceof Timestamp)
		{
			pstmt.setTimestamp(index, (Timestamp)param);
		}
		else if (param instanceof java.util.Date)
		{
			pstmt.setTimestamp(index, new Timestamp(((java.util.Date)param).getTime()));
		}
		else if (param instanceof Boolean)
		{
			pstmt.setString(index, ((Boolean)param).booleanValue() ? "Y" : "N");
		}
		else
		{
			throw new IllegalArgumentException("Unknown parameter type " + index + " - " + param);
		}
	}

	/**
	 * Set parameters for given statement
	 *
	 * @param stmt statements
	 * @param params parameters array; if null or empty array, no parameters are set
	 */
	public static void setParameters(final PreparedStatement stmt, final Object[] params)
			throws SQLException
	{
		if (params == null || params.length == 0)
		{
			return;
		}
		//
		for (int i = 0; i < params.length; i++)
		{
			setParameter(stmt, i + 1, params[i]);
		}
	}

	/**
	 * Set parameters for given statement
	 *
	 * @param stmt statements
	 * @param params parameters list; if null or empty list, no parameters are set
	 */
	public static void setParameters(final PreparedStatement stmt, final List<?> params)
			throws SQLException
	{
		if (params == null || params.isEmpty())
		{
			return;
		}
		for (int i = 0; i < params.size(); i++)
		{
			setParameter(stmt, i + 1, params.get(i));
		}
	}

	public void close(final ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			}
			catch (final SQLException e)
			{
				logger.debug(e.getLocalizedMessage(), e);
			}
		}
	}

	public void close(final Statement stmt)
	{
		if (stmt != null)
		{
			try
			{
				stmt.close();
			}
			catch (final SQLException e)
			{
				logger.debug(e.getLocalizedMessage(), e);
			}
		}
	}

	public void close(final Connection conn)
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (final SQLException e)
			{
				logger.debug(e.getLocalizedMessage(), e);
			}
		}
	}

	public void close(final ResultSet rs, final PreparedStatement pstmt, final Connection conn)
	{
		close(rs);
		close(pstmt);
		close(conn);
	}

	@FunctionalInterface
	public static interface ResultSetRowLoader<T>
	{
		T loadRow(ResultSet rs) throws SQLException;
	}
}
