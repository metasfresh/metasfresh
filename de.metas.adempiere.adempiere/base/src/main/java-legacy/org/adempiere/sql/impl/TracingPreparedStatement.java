package org.adempiere.sql.impl;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.compiere.util.CPreparedStatement;
import org.compiere.util.CStatementVO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General public final License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General public final License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link CPreparedStatement} wrapper which traces SQL queries
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <StatementType>
 */
class TracingPreparedStatement<StatementType extends CPreparedStatementProxy> extends TracingStatement<StatementType>implements CPreparedStatement
{
	public TracingPreparedStatement(final StatementType delegate)
	{
		super(delegate);
	}

	protected final void traceSqlParam(final int parameterIndex, final Object parameterValue)
	{
		final CStatementVO vo = delegate.getVO();
		if (vo != null)
		{
			vo.setDebugSqlParam(parameterIndex, parameterValue);
		}
	}

	@Override
	public final void setNull(final int parameterIndex, final int sqlType) throws SQLException
	{
		traceSqlParam(parameterIndex, null);
		delegate.setNull(parameterIndex, sqlType);
	}

	@Override
	public final void setBoolean(final int parameterIndex, final boolean x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setBoolean(parameterIndex, x);
	}

	@Override
	public final void setByte(final int parameterIndex, final byte x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setByte(parameterIndex, x);
	}

	@Override
	public final void setShort(final int parameterIndex, final short x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setShort(parameterIndex, x);
	}

	@Override
	public final void setInt(final int parameterIndex, final int x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setInt(parameterIndex, x);
	}

	@Override
	public final void setLong(final int parameterIndex, final long x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setLong(parameterIndex, x);
	}

	@Override
	public final void setFloat(final int parameterIndex, final float x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setFloat(parameterIndex, x);
	}

	@Override
	public final void setDouble(final int parameterIndex, final double x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setDouble(parameterIndex, x);
	}

	@Override
	public final void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setBigDecimal(parameterIndex, x);
	}

	@Override
	public final void setString(final int parameterIndex, final String x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setString(parameterIndex, x);
	}

	@Override
	public final void setBytes(final int parameterIndex, final byte[] x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setBytes(parameterIndex, x);
	}

	@Override
	public final void setDate(final int parameterIndex, final Date x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setDate(parameterIndex, x);
	}

	@Override
	public final void setTime(final int parameterIndex, final Time x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setTime(parameterIndex, x);
	}

	@Override
	public final void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setTimestamp(parameterIndex, x);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public final void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setObject(parameterIndex, x);
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException
	{
		traceSqlParam(parameterIndex, reader);
		delegate.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public final void setRef(final int parameterIndex, final Ref x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setRef(parameterIndex, x);
	}

	@Override
	public final void setBlob(final int parameterIndex, final Blob x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setBlob(parameterIndex, x);
	}

	@Override
	public final void setClob(final int parameterIndex, final Clob x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setClob(parameterIndex, x);
	}

	@Override
	public final void setArray(final int parameterIndex, final Array x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setArray(parameterIndex, x);
	}

	@Override
	public final void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setDate(parameterIndex, x, cal);
	}

	@Override
	public final void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setTime(parameterIndex, x, cal);
	}

	@Override
	public final void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public final void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException
	{
		traceSqlParam(parameterIndex, null);
		delegate.setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public final void setURL(final int parameterIndex, final URL x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setURL(parameterIndex, x);
	}

	@Override
	public final void setRowId(final int parameterIndex, final RowId x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setRowId(parameterIndex, x);
	}

	@Override
	public final void setNCharacterStream(final int parameterIndex, final Reader value, final long length) throws SQLException
	{
		traceSqlParam(parameterIndex, value);
		delegate.setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public final void setNClob(final int parameterIndex, final NClob value) throws SQLException
	{
		traceSqlParam(parameterIndex, value);
		delegate.setNClob(parameterIndex, value);
	}

	@Override
	public final void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		traceSqlParam(parameterIndex, reader);
		delegate.setClob(parameterIndex, reader, length);
	}

	@Override
	public final void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException
	{
		traceSqlParam(parameterIndex, inputStream);
		delegate.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public final void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		traceSqlParam(parameterIndex, reader);
		delegate.setNClob(parameterIndex, reader, length);
	}

	@Override
	public final void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException
	{
		traceSqlParam(parameterIndex, xmlObject);
		delegate.setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public final void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scaleOrLength) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException
	{
		traceSqlParam(parameterIndex, reader);
		delegate.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public final void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setAsciiStream(parameterIndex, x);
	}

	@Override
	public final void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException
	{
		traceSqlParam(parameterIndex, x);
		delegate.setBinaryStream(parameterIndex, x);
	}

	@Override
	public final void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException
	{
		traceSqlParam(parameterIndex, reader);
		delegate.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public final void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException
	{
		traceSqlParam(parameterIndex, value);
		delegate.setNCharacterStream(parameterIndex, value);
	}

	@Override
	public final void setClob(final int parameterIndex, final Reader reader) throws SQLException
	{
		traceSqlParam(parameterIndex, reader);
		delegate.setClob(parameterIndex, reader);
	}

	@Override
	public final void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException
	{
		traceSqlParam(parameterIndex, inputStream);
		delegate.setBlob(parameterIndex, inputStream);
	}

	@Override
	public final void setNClob(final int parameterIndex, final Reader reader) throws SQLException
	{
		traceSqlParam(parameterIndex, reader);
		delegate.setNClob(parameterIndex, reader);
	}

	@Override
	public final void setNString(final int parameterIndex, final String value) throws SQLException
	{
		traceSqlParam(parameterIndex, value);
		delegate.setNString(parameterIndex, value);
	}

	@Override
	public final ResultSet executeQuery() throws SQLException
	{
		return trace(() -> delegate.executeQuery());
	}

	@Override
	public final int executeUpdate() throws SQLException
	{
		return trace(() -> delegate.executeUpdate());
	}

	@Override
	public final void clearParameters() throws SQLException
	{
		delegate.clearParameters();
	}

	@Override
	public final boolean execute() throws SQLException
	{
		return trace(() -> delegate.execute());
	}

	@Override
	public final void addBatch() throws SQLException
	{
		trace(() -> {
			delegate.addBatch();
			return null;
		});
	}

	@Override
	public final ResultSetMetaData getMetaData() throws SQLException
	{
		return delegate.getMetaData();
	}

	@Override
	public final ParameterMetaData getParameterMetaData() throws SQLException
	{
		return delegate.getParameterMetaData();
	}
}
