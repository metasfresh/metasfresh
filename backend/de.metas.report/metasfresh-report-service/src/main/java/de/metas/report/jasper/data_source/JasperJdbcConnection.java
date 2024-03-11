package de.metas.report.jasper.data_source;

import com.google.common.annotations.VisibleForTesting;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * An {@link Connection} wrapper which inserts a given "query info" comment at the beginning of the SQL queries, right before they are executed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
final class JasperJdbcConnection implements Connection
{
	private static final Logger logger = LogManager.getLogger(JasperJdbcConnection.class);

	private final Connection delegate;
	private final String queryInfo;
	private final String securityWhereClause;

	public JasperJdbcConnection(@NonNull final Connection delegate, final String queryInfo, final String securityWhereClause)
	{
		this.delegate = delegate;

		this.queryInfo = normalizeQueryInfo(queryInfo);
		this.securityWhereClause = Check.isEmpty(securityWhereClause, true) ? null : securityWhereClause;
	}

	private static String normalizeQueryInfo(final String queryInfo)
	{
		if (queryInfo == null || Check.isBlank(queryInfo))
		{
			return null;
		}

		return "/* "
				+ queryInfo.replace("/*", " ").replace("*/", " ")
				+ " */ ";
	}

	private String customizeSql(@Nullable final String sql)
	{
		String sqlCustomized = addQueryInfo(sql);
		sqlCustomized = injectSecurityWhereClauses(sqlCustomized, securityWhereClause);

		return sqlCustomized;
	}

	private String addQueryInfo(final String sql)
	{
		if (queryInfo == null)
		{
			return sql;
		}
		if (sql == null)
		{
			return sql;
		}

		return queryInfo + sql;
	}

	@VisibleForTesting
	static String injectSecurityWhereClauses(@Nullable final String sql, @Nullable final String securityWhereClause)
	{
		if (sql == null)
		{
			logger.debug("injectSecurityWhereClauses - sql=null; -> return null");
			return null;
		}
		if (securityWhereClause == null)
		{
			logger.debug("injectSecurityWhereClauses - securityWhereClause=null; -> return sql={}", sql);
			return sql;
		}

		logger.debug("injectSecurityWhereClauses - sql={}", sql);
		logger.debug("injectSecurityWhereClauses - securityWhereClause={}", securityWhereClause);

		final StringBuilder sqlCustomized = new StringBuilder();

		// Cut off last ORDER BY clause
		final String orderBy;
		final int idxOrderBy = sql.lastIndexOf("ORDER BY ");

		final int lastIdxOfSemicolon = sql.lastIndexOf(";");
		final int idxSemicolon = lastIdxOfSemicolon > -1 ? lastIdxOfSemicolon : sql.length();

		if (idxOrderBy != -1)
		{
			logger.debug("injectSecurityWhereClauses - found a 'ORDER BY' at idx={}; -> later append it to the end", idxSemicolon);
			orderBy = sql.substring(idxOrderBy, idxSemicolon);
			sqlCustomized.append(sql, 0, idxOrderBy);
		}
		else if (idxSemicolon != -1)
		{
			logger.debug("injectSecurityWhereClauses - found a semicolon at idx={}; -> will omit it", idxSemicolon);
			orderBy = null;
			sqlCustomized.append(sql, 0, idxSemicolon);
		}
		else
		{
			orderBy = null;
			sqlCustomized.append(sql);
		}

		// Do we have to add WHERE or AND
		if (!sql.contains(" WHERE "))
		{
			logger.debug("injectSecurityWhereClauses - found no ' WHERE '; -> will add the securityWhereClause with a WHERE");
			sqlCustomized.append("\nWHERE ");
		}
		else
		{
			logger.debug("injectSecurityWhereClauses - found a ' WHERE '; -> will add the securityWhereClause with an AND");
			sqlCustomized.append("\nAND ");
		}

		sqlCustomized.append(securityWhereClause).append(" /*JasperJdbcConnection.securityWhereClause*/");

		if (orderBy != null)
		{
			sqlCustomized.append("\n").append(orderBy);
		}

		sqlCustomized.append(";");

		return sqlCustomized.toString();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException
	{
		return delegate.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException
	{
		return delegate.isWrapperFor(iface);
	}

	@Override
	public Statement createStatement() throws SQLException
	{
		return delegate.createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return delegate.prepareStatement(customizeSql(sql));
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException
	{
		return delegate.prepareCall(customizeSql(sql));
	}

	@Override
	public String nativeSQL(String sql) throws SQLException
	{
		return delegate.nativeSQL(customizeSql(sql));
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		delegate.setAutoCommit(autoCommit);
	}

	@Override
	public boolean getAutoCommit() throws SQLException
	{
		return delegate.getAutoCommit();
	}

	@Override
	public void commit() throws SQLException
	{
		delegate.commit();
	}

	@Override
	public void rollback() throws SQLException
	{
		delegate.rollback();
	}

	@Override
	public void close() throws SQLException
	{
		delegate.close();
	}

	@Override
	public boolean isClosed() throws SQLException
	{
		return delegate.isClosed();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException
	{
		return delegate.getMetaData();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException
	{
		delegate.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() throws SQLException
	{
		return delegate.isReadOnly();
	}

	@Override
	public void setCatalog(String catalog) throws SQLException
	{
		delegate.setCatalog(catalog);
	}

	@Override
	public String getCatalog() throws SQLException
	{
		return delegate.getCatalog();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException
	{
		delegate.setTransactionIsolation(level);
	}

	@Override
	public int getTransactionIsolation() throws SQLException
	{
		return delegate.getTransactionIsolation();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException
	{
		return delegate.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException
	{
		delegate.clearWarnings();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return delegate.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return delegate.prepareStatement(customizeSql(sql), resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return delegate.prepareCall(customizeSql(sql), resultSetType, resultSetConcurrency);
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException
	{
		return delegate.getTypeMap();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException
	{
		delegate.setTypeMap(map);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException
	{
		delegate.setHoldability(holdability);
	}

	@Override
	public int getHoldability() throws SQLException
	{
		return delegate.getHoldability();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException
	{
		return delegate.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException
	{
		return delegate.setSavepoint(name);
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException
	{
		delegate.rollback(savepoint);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException
	{
		delegate.releaseSavepoint(savepoint);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return delegate.prepareStatement(customizeSql(sql), resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return delegate.prepareCall(customizeSql(sql), resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException
	{
		return delegate.prepareStatement(customizeSql(sql), autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
	{
		return delegate.prepareStatement(customizeSql(sql), columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
	{
		return delegate.prepareStatement(customizeSql(sql), columnNames);
	}

	@Override
	public Clob createClob() throws SQLException
	{
		return delegate.createClob();
	}

	@Override
	public Blob createBlob() throws SQLException
	{
		return delegate.createBlob();
	}

	@Override
	public NClob createNClob() throws SQLException
	{
		return delegate.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException
	{
		return delegate.createSQLXML();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException
	{
		return delegate.isValid(timeout);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException
	{
		delegate.setClientInfo(name, value);
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException
	{
		delegate.setClientInfo(properties);
	}

	@Override
	public String getClientInfo(String name) throws SQLException
	{
		return delegate.getClientInfo(name);
	}

	@Override
	public Properties getClientInfo() throws SQLException
	{
		return delegate.getClientInfo();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException
	{
		return delegate.createArrayOf(typeName, elements);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException
	{
		return delegate.createStruct(typeName, attributes);
	}

	@Override
	public void setSchema(String schema) throws SQLException
	{
		delegate.setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException
	{
		return delegate.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException
	{
		delegate.abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException
	{
		delegate.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException
	{
		return delegate.getNetworkTimeout();
	}
}
