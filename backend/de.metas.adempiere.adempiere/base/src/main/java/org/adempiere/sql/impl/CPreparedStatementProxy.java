package org.adempiere.sql.impl;

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

import de.metas.util.Check;
import de.metas.util.StringUtils;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.migration.logger.Sql;
import org.adempiere.ad.migration.logger.SqlBatch;
import org.adempiere.exceptions.DBNoConnectionException;
import org.compiere.db.AdempiereDatabase;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.CStatementVO;
import org.compiere.util.DB;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/* package */class CPreparedStatementProxy extends AbstractCStatementProxy<PreparedStatement> implements CPreparedStatement
{
	private final boolean logMigrationScripts = MigrationScriptFileLoggerHolder.isEnabled();
	private SqlBatch batchedSqlsToLog = null;

	public CPreparedStatementProxy(final CStatementVO vo)
	{
		super(vo);
	}

	public CPreparedStatementProxy(final int resultSetType, final int resultSetConcurrency, final String sql0, final String trxName)
	{
		super(createVO(resultSetType, resultSetConcurrency, sql0, trxName));
	}

	private static CStatementVO createVO(final int resultSetType, final int resultSetConcurrency, final String sql, final String trxName)
	{
		Check.assumeNotEmpty(sql, "sql not empty");

		final AdempiereDatabase database = DB.getDatabase();
		if (database == null)
		{
			throw new DBNoConnectionException();
		}

		final String sqlConverted = database.convertStatement(sql);
		return new CStatementVO(resultSetType, resultSetConcurrency, sqlConverted, trxName);
	}

	private void logMigrationScript()
	{
		if (!logMigrationScripts)
		{
			return;
		}

		final CStatementVO vo = getVO();
		final String sql = StringUtils.trimBlankToNull(vo.getSql());
		if (sql == null)
		{
			return;
		}

		MigrationScriptFileLoggerHolder.logMigrationScript(Sql.ofSql(sql, vo.getAndClearDebugSqlParams()));
	}

	private void logMigrationScript_SetParam(final int parameterIndex, final Object parameterValue)
	{
		if (logMigrationScripts)
		{
			getVO().setDebugSqlParam(parameterIndex, parameterValue);
		}
	}

	private void logMigrationScript_ClearParams()
	{
		if (logMigrationScripts)
		{
			getVO().clearDebugSqlParams();
		}
	}

	private void logMigrationScripts_addBatch()
	{
		if (!logMigrationScripts) {return;}

		final CStatementVO vo = getVO();
		final String sqlCommand = StringUtils.trimBlankToNull(vo.getSql());
		if (sqlCommand == null) {return;}

		if (batchedSqlsToLog == null)
		{
			batchedSqlsToLog = SqlBatch.ofSql(sqlCommand);
		}
		batchedSqlsToLog.addRow(vo.getAndClearDebugSqlParams());
	}

	private void logMigrationScripts_executeBatch()
	{
		if (!logMigrationScripts) {return;}
		if (batchedSqlsToLog == null) {return;}

		MigrationScriptFileLoggerHolder.logMigrationScript(batchedSqlsToLog);
	}

	private void logMigrationScripts_clearBatch()
	{
		if (logMigrationScripts)
		{
			this.batchedSqlsToLog = null;
		}
	}

	@Override
	protected PreparedStatement createStatement(final Connection conn, final CStatementVO vo) throws SQLException
	{
		return conn.prepareStatement(vo.getSql(),
				vo.getResultSetType(),
				vo.getResultSetConcurrency());
	}

	@Override
	public final ResultSet executeQuery() throws SQLException
	{
		return getStatementImpl().executeQuery();
	}

	@Override
	public ResultSet executeQueryAndLogMigationScripts() throws SQLException
	{
		logMigrationScript();
		return executeQuery();
	}

	@Override
	public final int executeUpdate() throws SQLException
	{
		logMigrationScript();
		return getStatementImpl().executeUpdate();
	}

	@Override
	public final int[] executeBatch() throws SQLException
	{
		logMigrationScripts_executeBatch();
		return super.executeBatch();
	}

	@Override
	public void clearBatch() throws SQLException
	{
		logMigrationScripts_clearBatch();
		super.clearBatch();
	}

	@Override
	public final void setNull(final int parameterIndex, final int sqlType) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, null);
		getStatementImpl().setNull(parameterIndex, sqlType);
	}

	@Override
	public final void setBoolean(final int parameterIndex, final boolean x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setBoolean(parameterIndex, x);
	}

	@Override
	public final void setByte(final int parameterIndex, final byte x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setByte(parameterIndex, x);
	}

	@Override
	public final void setShort(final int parameterIndex, final short x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setShort(parameterIndex, x);
	}

	@Override
	public final void setInt(final int parameterIndex, final int x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setInt(parameterIndex, x);
	}

	@Override
	public final void setLong(final int parameterIndex, final long x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setLong(parameterIndex, x);
	}

	@Override
	public final void setFloat(final int parameterIndex, final float x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setFloat(parameterIndex, x);
	}

	@Override
	public final void setDouble(final int parameterIndex, final double x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setDouble(parameterIndex, x);
	}

	@Override
	public final void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setBigDecimal(parameterIndex, x);
	}

	@Override
	public final void setString(final int parameterIndex, final String x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setString(parameterIndex, x);
	}

	@Override
	public final void setBytes(final int parameterIndex, final byte[] x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setBytes(parameterIndex, x);
	}

	@Override
	public final void setDate(final int parameterIndex, final Date x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setDate(parameterIndex, x);
	}

	@Override
	public final void setTime(final int parameterIndex, final Time x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setTime(parameterIndex, x);
	}

	@Override
	public final void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setTimestamp(parameterIndex, x);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setAsciiStream(parameterIndex, x, length);
	}

	@SuppressWarnings("deprecation")
	@Override
	public final void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public final void clearParameters() throws SQLException
	{
		logMigrationScript_ClearParams();
		getStatementImpl().clearParameters();
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setObject(parameterIndex, x);
	}

	@Override
	public final boolean execute() throws SQLException
	{
		logMigrationScript();
		return getStatementImpl().execute();
	}

	@Override
	public final void addBatch() throws SQLException
	{
		logMigrationScripts_addBatch();
		getStatementImpl().addBatch();
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException
	{
		//logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public final void setRef(final int parameterIndex, final Ref x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setRef(parameterIndex, x);
	}

	@Override
	public final void setBlob(final int parameterIndex, final Blob x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setBlob(parameterIndex, x);
	}

	@Override
	public final void setClob(final int parameterIndex, final Clob x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setClob(parameterIndex, x);
	}

	@Override
	public final void setArray(final int parameterIndex, final Array x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setArray(parameterIndex, x);
	}

	@Override
	public final ResultSetMetaData getMetaData() throws SQLException
	{
		return getStatementImpl().getMetaData();
	}

	@Override
	public final void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setDate(parameterIndex, x, cal);
	}

	@Override
	public final void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setTime(parameterIndex, x, cal);
	}

	@Override
	public final void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public final void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, null);
		getStatementImpl().setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public final void setURL(final int parameterIndex, final URL x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setURL(parameterIndex, x);
	}

	@Override
	public final ParameterMetaData getParameterMetaData() throws SQLException
	{
		return getStatementImpl().getParameterMetaData();
	}

	@Override
	public final void setRowId(final int parameterIndex, final RowId x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setRowId(parameterIndex, x);
	}

	@Override
	public final void setNString(final int parameterIndex, final String value) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, value);
		getStatementImpl().setNString(parameterIndex, value);
	}

	@Override
	public final void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, value);
		getStatementImpl().setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public final void setNClob(final int parameterIndex, final NClob value) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, value);
		getStatementImpl().setNClob(parameterIndex, value);
	}

	@Override
	public final void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		// logMigrationScript_SetParam(parameterIndex, reader);
		getStatementImpl().setClob(parameterIndex, reader, length);
	}

	@Override
	public final void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException
	{
		//logMigrationScript_SetParam(parameterIndex, inputStream);
		getStatementImpl().setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public final void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		// logMigrationScript_SetParam(parameterIndex, reader);
		getStatementImpl().setNClob(parameterIndex, reader, length);
	}

	@Override
	public final void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, xmlObject);
		getStatementImpl().setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		//logMigrationScript_SetParam(parameterIndex, reader);
		getStatementImpl().setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setAsciiStream(parameterIndex, x);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, x);
		getStatementImpl().setBinaryStream(parameterIndex, x);
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException
	{
		// logMigrationScript_SetParam(parameterIndex, reader);
		getStatementImpl().setCharacterStream(parameterIndex, reader);
	}

	@Override
	public final void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException
	{
		logMigrationScript_SetParam(parameterIndex, value);
		getStatementImpl().setNCharacterStream(parameterIndex, value);
	}

	@Override
	public final void setClob(final int parameterIndex, final Reader reader) throws SQLException
	{
		// logMigrationScript_SetParam(parameterIndex, reader);
		getStatementImpl().setClob(parameterIndex, reader);
	}

	@Override
	public final void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException
	{
		//logMigrationScript_SetParam(parameterIndex, inputStream);
		getStatementImpl().setBlob(parameterIndex, inputStream);
	}

	@Override
	public final void setNClob(final int parameterIndex, final Reader reader) throws SQLException
	{
		// logMigrationScript_SetParam(parameterIndex, reader);
		getStatementImpl().setNClob(parameterIndex, reader);
	}
}
