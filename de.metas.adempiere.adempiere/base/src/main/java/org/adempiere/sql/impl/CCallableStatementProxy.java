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
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.compiere.util.CCallableStatement;
import org.compiere.util.CStatementVO;

/* package */final class CCallableStatementProxy extends CPreparedStatementProxy implements CCallableStatement
{
	public CCallableStatementProxy(final CStatementVO vo)
	{
		super(vo);
	}

	public CCallableStatementProxy(final int resultSetType, final int resultSetConcurrency, final String sql, final String trxName)
	{
		super(resultSetType, resultSetConcurrency, sql, trxName);
	}

	@Override
	protected final CallableStatement createStatement(final Connection conn, final CStatementVO vo) throws SQLException
	{
		final CallableStatement stmt = conn.prepareCall(vo.getSql(), vo.getResultSetType(), vo.getResultSetConcurrency());
		return stmt;
	}

	protected final CCallableStatement getCCallableStatementImpl()
	{
		return (CCallableStatement)super.getStatementImpl();
	}

	@Override
	public final void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException
	{
		getCCallableStatementImpl().registerOutParameter(parameterIndex, sqlType);
	}

	@Override
	public final void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException
	{
		getCCallableStatementImpl().registerOutParameter(parameterIndex, sqlType, scale);
	}

	@Override
	public final boolean wasNull() throws SQLException
	{
		return getCCallableStatementImpl().wasNull();
	}

	@Override
	public final String getString(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getString(parameterIndex);
	}

	@Override
	public final boolean getBoolean(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getBoolean(parameterIndex);
	}

	@Override
	public final byte getByte(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getByte(parameterIndex);
	}

	@Override
	public final short getShort(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getShort(parameterIndex);
	}

	@Override
	public final int getInt(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getInt(parameterIndex);
	}

	@Override
	public final long getLong(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getLong(parameterIndex);
	}

	@Override
	public final float getFloat(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getFloat(parameterIndex);
	}

	@Override
	public final double getDouble(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getDouble(parameterIndex);
	}

	@Override
	@SuppressWarnings("deprecation")
	public final BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException
	{
		return getCCallableStatementImpl().getBigDecimal(parameterIndex, scale);
	}

	@Override
	public final byte[] getBytes(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getBytes(parameterIndex);
	}

	@Override
	public final Date getDate(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getDate(parameterIndex);
	}

	@Override
	public final Time getTime(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getTime(parameterIndex);
	}

	@Override
	public final Timestamp getTimestamp(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getTimestamp(parameterIndex);
	}

	@Override
	public final Object getObject(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getObject(parameterIndex);
	}

	@Override
	public final BigDecimal getBigDecimal(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getBigDecimal(parameterIndex);
	}

	@Override
	public final Object getObject(final int parameterIndex, final Map<String, Class<?>> map) throws SQLException
	{
		return getCCallableStatementImpl().getObject(parameterIndex, map);
	}

	@Override
	public final Ref getRef(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getRef(parameterIndex);
	}

	@Override
	public final Blob getBlob(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getBlob(parameterIndex);
	}

	@Override
	public final Clob getClob(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getClob(parameterIndex);
	}

	@Override
	public final Array getArray(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getArray(parameterIndex);
	}

	@Override
	public final Date getDate(final int parameterIndex, final Calendar cal) throws SQLException
	{
		return getCCallableStatementImpl().getDate(parameterIndex, cal);
	}

	@Override
	public final Time getTime(final int parameterIndex, final Calendar cal) throws SQLException
	{
		return getCCallableStatementImpl().getTime(parameterIndex, cal);
	}

	@Override
	public final Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException
	{
		return getCCallableStatementImpl().getTimestamp(parameterIndex, cal);
	}

	@Override
	public final void registerOutParameter(final int parameterIndex, final int sqlType, final String typeName) throws SQLException
	{
		getCCallableStatementImpl().registerOutParameter(parameterIndex, sqlType, typeName);
	}

	@Override
	public final void registerOutParameter(final String parameterName, final int sqlType) throws SQLException
	{
		getCCallableStatementImpl().registerOutParameter(parameterName, sqlType);
	}

	@Override
	public final void registerOutParameter(final String parameterName, final int sqlType, final int scale) throws SQLException
	{
		getCCallableStatementImpl().registerOutParameter(parameterName, sqlType, scale);
	}

	@Override
	public final void registerOutParameter(final String parameterName, final int sqlType, final String typeName) throws SQLException
	{
		getCCallableStatementImpl().registerOutParameter(parameterName, sqlType, typeName);
	}

	@Override
	public final URL getURL(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getURL(parameterIndex);
	}

	@Override
	public final void setURL(final String parameterName, final URL val) throws SQLException
	{
		getCCallableStatementImpl().setURL(parameterName, val);
	}

	@Override
	public final void setNull(final String parameterName, final int sqlType) throws SQLException
	{
		getCCallableStatementImpl().setNull(parameterName, sqlType);
	}

	@Override
	public final void setBoolean(final String parameterName, final boolean x) throws SQLException
	{
		getCCallableStatementImpl().setBoolean(parameterName, x);
	}

	@Override
	public final void setByte(final String parameterName, final byte x) throws SQLException
	{
		getCCallableStatementImpl().setByte(parameterName, x);
	}

	@Override
	public final void setShort(final String parameterName, final short x) throws SQLException
	{
		getCCallableStatementImpl().setShort(parameterName, x);
	}

	@Override
	public final void setInt(final String parameterName, final int x) throws SQLException
	{
		getCCallableStatementImpl().setInt(parameterName, x);
	}

	@Override
	public final void setLong(final String parameterName, final long x) throws SQLException
	{
		getCCallableStatementImpl().setLong(parameterName, x);
	}

	@Override
	public final void setFloat(final String parameterName, final float x) throws SQLException
	{
		getCCallableStatementImpl().setFloat(parameterName, x);
	}

	@Override
	public final void setDouble(final String parameterName, final double x) throws SQLException
	{
		getCCallableStatementImpl().setDouble(parameterName, x);
	}

	@Override
	public final void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException
	{
		getCCallableStatementImpl().setBigDecimal(parameterName, x);
	}

	@Override
	public final void setString(final String parameterName, final String x) throws SQLException
	{
		getCCallableStatementImpl().setString(parameterName, x);
	}

	@Override
	public final void setBytes(final String parameterName, final byte[] x) throws SQLException
	{
		getCCallableStatementImpl().setBytes(parameterName, x);
	}

	@Override
	public final void setDate(final String parameterName, final Date x) throws SQLException
	{
		getCCallableStatementImpl().setDate(parameterName, x);
	}

	@Override
	public final void setTime(final String parameterName, final Time x) throws SQLException
	{
		getCCallableStatementImpl().setTime(parameterName, x);
	}

	@Override
	public final void setTimestamp(final String parameterName, final Timestamp x) throws SQLException
	{
		getCCallableStatementImpl().setTimestamp(parameterName, x);
	}

	@Override
	public final void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException
	{
		getCCallableStatementImpl().setAsciiStream(parameterName, x, length);
	}

	@Override
	public final void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException
	{
		getCCallableStatementImpl().setBinaryStream(parameterName, x, length);
	}

	@Override
	public final void setObject(final String parameterName, final Object x, final int targetSqlType, final int scale) throws SQLException
	{
		getCCallableStatementImpl().setObject(parameterName, x, targetSqlType, scale);
	}

	@Override
	public final void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException
	{
		getCCallableStatementImpl().setObject(parameterName, x, targetSqlType);
	}

	@Override
	public final void setObject(final String parameterName, final Object x) throws SQLException
	{
		getCCallableStatementImpl().setObject(parameterName, x);
	}

	@Override
	public final void setCharacterStream(final String parameterName, final Reader reader, final int length) throws SQLException
	{
		getCCallableStatementImpl().setCharacterStream(parameterName, reader, length);
	}

	@Override
	public final void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException
	{
		getCCallableStatementImpl().setDate(parameterName, x, cal);
	}

	@Override
	public final void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException
	{
		getCCallableStatementImpl().setTime(parameterName, x, cal);
	}

	@Override
	public final void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException
	{
		getCCallableStatementImpl().setTimestamp(parameterName, x, cal);
	}

	@Override
	public final void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException
	{
		getCCallableStatementImpl().setNull(parameterName, sqlType, typeName);
	}

	@Override
	public final String getString(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getString(parameterName);
	}

	@Override
	public final boolean getBoolean(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getBoolean(parameterName);
	}

	@Override
	public final byte getByte(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getByte(parameterName);
	}

	@Override
	public final short getShort(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getShort(parameterName);
	}

	@Override
	public final int getInt(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getInt(parameterName);
	}

	@Override
	public final long getLong(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getLong(parameterName);
	}

	@Override
	public final float getFloat(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getFloat(parameterName);
	}

	@Override
	public final double getDouble(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getDouble(parameterName);
	}

	@Override
	public final byte[] getBytes(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getBytes(parameterName);
	}

	@Override
	public final Date getDate(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getDate(parameterName);
	}

	@Override
	public final Time getTime(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getTime(parameterName);
	}

	@Override
	public final Timestamp getTimestamp(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getTimestamp(parameterName);
	}

	@Override
	public final Object getObject(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getObject(parameterName);
	}

	@Override
	public final BigDecimal getBigDecimal(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getBigDecimal(parameterName);
	}

	@Override
	public final Object getObject(final String parameterName, final Map<String, Class<?>> map) throws SQLException
	{
		return getCCallableStatementImpl().getObject(parameterName, map);
	}

	@Override
	public final Ref getRef(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getRef(parameterName);
	}

	@Override
	public final Blob getBlob(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getBlob(parameterName);
	}

	@Override
	public final Clob getClob(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getClob(parameterName);
	}

	@Override
	public final Array getArray(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getArray(parameterName);
	}

	@Override
	public final Date getDate(final String parameterName, final Calendar cal) throws SQLException
	{
		return getCCallableStatementImpl().getDate(parameterName, cal);
	}

	@Override
	public final Time getTime(final String parameterName, final Calendar cal) throws SQLException
	{
		return getCCallableStatementImpl().getTime(parameterName, cal);
	}

	@Override
	public final Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException
	{
		return getCCallableStatementImpl().getTimestamp(parameterName, cal);
	}

	@Override
	public final URL getURL(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getURL(parameterName);
	}

	@Override
	public final RowId getRowId(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getRowId(parameterIndex);
	}

	@Override
	public final RowId getRowId(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getRowId(parameterName);
	}

	@Override
	public final void setRowId(final String parameterName, final RowId x) throws SQLException
	{
		getCCallableStatementImpl().setRowId(parameterName, x);
	}

	@Override
	public final void setNString(final String parameterName, final String value) throws SQLException
	{
		getCCallableStatementImpl().setNString(parameterName, value);
	}

	@Override
	public final void setNCharacterStream(final String parameterName, final Reader value, final long length) throws SQLException
	{
		getCCallableStatementImpl().setNCharacterStream(parameterName, value, length);
	}

	@Override
	public final void setNClob(final String parameterName, final NClob value) throws SQLException
	{
		getCCallableStatementImpl().setNClob(parameterName, value);
	}

	@Override
	public final void setClob(final String parameterName, final Reader reader, final long length) throws SQLException
	{
		getCCallableStatementImpl().setClob(parameterName, reader, length);
	}

	@Override
	public final void setBlob(final String parameterName, final InputStream inputStream, final long length) throws SQLException
	{
		getCCallableStatementImpl().setBlob(parameterName, inputStream, length);
	}

	@Override
	public final void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException
	{
		getCCallableStatementImpl().setNClob(parameterName, reader, length);
	}

	@Override
	public final NClob getNClob(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getNClob(parameterIndex);
	}

	@Override
	public final NClob getNClob(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getNClob(parameterName);
	}

	@Override
	public final void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException
	{
		getCCallableStatementImpl().setSQLXML(parameterName, xmlObject);
	}

	@Override
	public final SQLXML getSQLXML(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getSQLXML(parameterIndex);
	}

	@Override
	public final SQLXML getSQLXML(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getSQLXML(parameterName);
	}

	@Override
	public final String getNString(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getNString(parameterIndex);
	}

	@Override
	public final String getNString(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getNString(parameterName);
	}

	@Override
	public final Reader getNCharacterStream(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getNCharacterStream(parameterIndex);
	}

	@Override
	public final Reader getNCharacterStream(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getNCharacterStream(parameterName);
	}

	@Override
	public final Reader getCharacterStream(final int parameterIndex) throws SQLException
	{
		return getCCallableStatementImpl().getCharacterStream(parameterIndex);
	}

	@Override
	public final Reader getCharacterStream(final String parameterName) throws SQLException
	{
		return getCCallableStatementImpl().getCharacterStream(parameterName);
	}

	@Override
	public final void setBlob(final String parameterName, final Blob x) throws SQLException
	{
		getCCallableStatementImpl().setBlob(parameterName, x);
	}

	@Override
	public final void setClob(final String parameterName, final Clob x) throws SQLException
	{
		getCCallableStatementImpl().setClob(parameterName, x);
	}

	@Override
	public final void setAsciiStream(final String parameterName, final InputStream x, final long length) throws SQLException
	{
		getCCallableStatementImpl().setAsciiStream(parameterName, x, length);
	}

	@Override
	public final void setBinaryStream(final String parameterName, final InputStream x, final long length) throws SQLException
	{
		getCCallableStatementImpl().setBinaryStream(parameterName, x, length);
	}

	@Override
	public final void setCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException
	{
		getCCallableStatementImpl().setCharacterStream(parameterName, reader, length);
	}

	@Override
	public final void setAsciiStream(final String parameterName, final InputStream x) throws SQLException
	{
		getCCallableStatementImpl().setAsciiStream(parameterName, x);
	}

	@Override
	public final void setBinaryStream(final String parameterName, final InputStream x) throws SQLException
	{
		getCCallableStatementImpl().setBinaryStream(parameterName, x);
	}

	@Override
	public final void setCharacterStream(final String parameterName, final Reader reader) throws SQLException
	{
		getCCallableStatementImpl().setCharacterStream(parameterName, reader);
	}

	@Override
	public final void setNCharacterStream(final String parameterName, final Reader value) throws SQLException
	{
		getCCallableStatementImpl().setNCharacterStream(parameterName, value);
	}

	@Override
	public final void setClob(final String parameterName, final Reader reader) throws SQLException
	{
		getCCallableStatementImpl().setClob(parameterName, reader);
	}

	@Override
	public final void setBlob(final String parameterName, final InputStream inputStream) throws SQLException
	{
		getCCallableStatementImpl().setBlob(parameterName, inputStream);
	}

	@Override
	public final void setNClob(final String parameterName, final Reader reader) throws SQLException
	{
		getCCallableStatementImpl().setNClob(parameterName, reader);
	}

	@Override
	public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException
	{
		return getCCallableStatementImpl().getObject(parameterIndex, type);
	}

	@Override
	public <T> T getObject(String parameterName, Class<T> type) throws SQLException
	{
		return getCCallableStatementImpl().getObject(parameterName, type);
	}
}
