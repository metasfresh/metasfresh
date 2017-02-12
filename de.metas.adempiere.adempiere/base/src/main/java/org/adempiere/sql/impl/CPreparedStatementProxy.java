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

import javax.sql.RowSet;

import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.util.Check;
import org.compiere.db.AdempiereDatabase;
import org.compiere.util.CCachedRowSet;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.CStatementVO;
import org.compiere.util.DB;

/* package */class CPreparedStatementProxy extends AbstractCStatementProxy<PreparedStatement>implements CPreparedStatement
{
	public CPreparedStatementProxy(final int resultSetType, final int resultSetConcurrency, final String sql0, final String trxName)
	{
		super(createVO(resultSetType, resultSetConcurrency, sql0, trxName));
	}

	private static final CStatementVO createVO(final int resultSetType, final int resultSetConcurrency, final String sql0, final String trxName)
	{
		Check.assumeNotEmpty(sql0, "sql not empty");

		final AdempiereDatabase database = DB.getDatabase();
		if (database == null)
		{
			throw new DBNoConnectionException();
		}

		final String sqlConverted = database.convertStatement(sql0);
		final CStatementVO vo = new CStatementVO(resultSetType, resultSetConcurrency, sqlConverted, trxName);

		return vo;
	}

	public CPreparedStatementProxy(final CStatementVO vo)
	{
		super(vo);
	}

	@Override
	protected PreparedStatement createStatement(final Connection conn, final CStatementVO vo) throws SQLException
	{
		final PreparedStatement pstmt = conn.prepareStatement(vo.getSql(),
				vo.getResultSetType(),
				vo.getResultSetConcurrency());
		return pstmt;
	}

	@Override
	public final RowSet getRowSet()
	{
		RowSet rowSet = null;
		ResultSet rs = null;
		PreparedStatement pstmt = getStatementImpl();
		try
		{
			rs = pstmt.executeQuery();
			rowSet = CCachedRowSet.getRowSet(rs);
		}
		catch (Exception ex)
		{
			final String sql = getSql();
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs);
		}
		return rowSet;

	}

	@Override
	public final ResultSet executeQuery() throws SQLException
	{
		return getStatementImpl().executeQuery();
	}

	@Override
	public final int executeUpdate() throws SQLException
	{
		return getStatementImpl().executeUpdate();
	}

	@Override
	public final void setNull(final int parameterIndex, final int sqlType) throws SQLException
	{
		getStatementImpl().setNull(parameterIndex, sqlType);
	}

	@Override
	public final void setBoolean(final int parameterIndex, final boolean x) throws SQLException
	{
		getStatementImpl().setBoolean(parameterIndex, x);
	}

	@Override
	public final void setByte(final int parameterIndex, final byte x) throws SQLException
	{
		getStatementImpl().setByte(parameterIndex, x);
	}

	@Override
	public final void setShort(final int parameterIndex, final short x) throws SQLException
	{
		getStatementImpl().setShort(parameterIndex, x);
	}

	@Override
	public final void setInt(final int parameterIndex, final int x) throws SQLException
	{
		getStatementImpl().setInt(parameterIndex, x);
	}

	@Override
	public final void setLong(final int parameterIndex, final long x) throws SQLException
	{
		getStatementImpl().setLong(parameterIndex, x);
	}

	@Override
	public final void setFloat(final int parameterIndex, final float x) throws SQLException
	{
		getStatementImpl().setFloat(parameterIndex, x);
	}

	@Override
	public final void setDouble(final int parameterIndex, final double x) throws SQLException
	{
		getStatementImpl().setDouble(parameterIndex, x);
	}

	@Override
	public final void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException
	{
		getStatementImpl().setBigDecimal(parameterIndex, x);
	}

	@Override
	public final void setString(final int parameterIndex, final String x) throws SQLException
	{
		getStatementImpl().setString(parameterIndex, x);
	}

	@Override
	public final void setBytes(final int parameterIndex, final byte[] x) throws SQLException
	{
		getStatementImpl().setBytes(parameterIndex, x);
	}

	@Override
	public final void setDate(final int parameterIndex, final Date x) throws SQLException
	{
		getStatementImpl().setDate(parameterIndex, x);
	}

	@Override
	public final void setTime(final int parameterIndex, final Time x) throws SQLException
	{
		getStatementImpl().setTime(parameterIndex, x);
	}

	@Override
	public final void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException
	{
		getStatementImpl().setTimestamp(parameterIndex, x);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		getStatementImpl().setAsciiStream(parameterIndex, x, length);
	}

	@SuppressWarnings("deprecation")
	@Override
	public final void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		getStatementImpl().setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		getStatementImpl().setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public final void clearParameters() throws SQLException
	{
		getStatementImpl().clearParameters();
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException
	{
		getStatementImpl().setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x) throws SQLException
	{
		getStatementImpl().setObject(parameterIndex, x);
	}

	@Override
	public final boolean execute() throws SQLException
	{
		return getStatementImpl().execute();
	}

	@Override
	public final void addBatch() throws SQLException
	{
		getStatementImpl().addBatch();
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException
	{
		getStatementImpl().setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public final void setRef(final int parameterIndex, final Ref x) throws SQLException
	{
		getStatementImpl().setRef(parameterIndex, x);
	}

	@Override
	public final void setBlob(final int parameterIndex, final Blob x) throws SQLException
	{
		getStatementImpl().setBlob(parameterIndex, x);
	}

	@Override
	public final void setClob(final int parameterIndex, final Clob x) throws SQLException
	{
		getStatementImpl().setClob(parameterIndex, x);
	}

	@Override
	public final void setArray(final int parameterIndex, final Array x) throws SQLException
	{
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
		getStatementImpl().setDate(parameterIndex, x, cal);
	}

	@Override
	public final void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException
	{
		getStatementImpl().setTime(parameterIndex, x, cal);
	}

	@Override
	public final void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException
	{
		getStatementImpl().setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public final void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException
	{
		getStatementImpl().setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public final void setURL(final int parameterIndex, final URL x) throws SQLException
	{
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
		getStatementImpl().setRowId(parameterIndex, x);
	}

	@Override
	public final void setNString(final int parameterIndex, final String value) throws SQLException
	{
		getStatementImpl().setNString(parameterIndex, value);
	}

	@Override
	public final void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException
	{
		getStatementImpl().setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public final void setNClob(final int parameterIndex, final NClob value) throws SQLException
	{
		getStatementImpl().setNClob(parameterIndex, value);
	}

	@Override
	public final void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		getStatementImpl().setClob(parameterIndex, reader, length);
	}

	@Override
	public final void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException
	{
		getStatementImpl().setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public final void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		getStatementImpl().setNClob(parameterIndex, reader, length);
	}

	@Override
	public final void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException
	{
		getStatementImpl().setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength) throws SQLException
	{
		getStatementImpl().setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException
	{
		getStatementImpl().setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException
	{
		getStatementImpl().setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		getStatementImpl().setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException
	{
		getStatementImpl().setAsciiStream(parameterIndex, x);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException
	{
		getStatementImpl().setBinaryStream(parameterIndex, x);
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException
	{
		getStatementImpl().setCharacterStream(parameterIndex, reader);
	}

	@Override
	public final void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException
	{
		getStatementImpl().setNCharacterStream(parameterIndex, value);
	}

	@Override
	public final void setClob(final int parameterIndex, final Reader reader) throws SQLException
	{
		getStatementImpl().setClob(parameterIndex, reader);
	}

	@Override
	public final void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException
	{
		getStatementImpl().setBlob(parameterIndex, inputStream);
	}

	@Override
	public final void setNClob(final int parameterIndex, final Reader reader) throws SQLException
	{
		getStatementImpl().setNClob(parameterIndex, reader);
	}
}
