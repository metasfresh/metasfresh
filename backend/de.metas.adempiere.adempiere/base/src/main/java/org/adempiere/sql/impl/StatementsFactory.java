package org.adempiere.sql.impl;

import org.adempiere.ad.dao.IQueryStatisticsCollector;

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

import org.adempiere.sql.IStatementsFactory;
import org.compiere.util.CCallableStatement;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.CStatement;
import org.compiere.util.CStatementVO;

/**
 * Factory helper class used to create {@link CStatement}, {@link CPreparedStatement} and {@link CCallableStatement} instances.
 *
 * @author tsa
 *
 */
public final class StatementsFactory implements IStatementsFactory
{
	public static final transient StatementsFactory instance = new StatementsFactory();
	private boolean sqlQueriesTracingEnabled = false;

	private StatementsFactory()
	{
		super();
	}
	
	public void enableSqlQueriesTracing(final IQueryStatisticsCollector sqlQueriesCollector)
	{
		this.sqlQueriesTracingEnabled = true;
		TracingStatement.SQL_QUERIES_COLLECTOR = sqlQueriesCollector;
	}
	
	public void disableSqlQueriesTracing()
	{
		this.sqlQueriesTracingEnabled = false;
	}

	@Override
	public CStatement newCStatement(final int resultSetType, final int resultSetConcurrency, final String trxName)
	{
		final CStatementProxy stmt = new CStatementProxy(resultSetType, resultSetConcurrency, trxName);
		if (sqlQueriesTracingEnabled)
		{
			return new TracingStatement<>(stmt);
		}
		return stmt;
	}

	@Override
	public CPreparedStatement newCPreparedStatement(final int resultSetType, final int resultSetConcurrency, final String sql, final String trxName)
	{
		final CPreparedStatementProxy pstmt = new CPreparedStatementProxy(resultSetType, resultSetConcurrency, sql, trxName);
		if (sqlQueriesTracingEnabled)
		{
			return new TracingPreparedStatement<>(pstmt);
		}
		return pstmt;
	}

	@Override
	public CCallableStatement newCCallableStatement(final int resultSetType, final int resultSetConcurrency, final String sql, final String trxName)
	{
		return new CCallableStatementProxy(resultSetType, resultSetConcurrency, sql, trxName);
	}

	@Override
	public CStatement newCStatement(final CStatementVO info)
	{
		final CStatementProxy stmt = new CStatementProxy(info);
		if (sqlQueriesTracingEnabled)
		{
			return new TracingStatement<>(stmt);
		}
		return stmt;
	}

	@Override
	public CPreparedStatement newCPreparedStatement(final CStatementVO info)
	{
		final CPreparedStatementProxy pstmt = new CPreparedStatementProxy(info);
		if (sqlQueriesTracingEnabled)
		{
			return new TracingPreparedStatement<>(pstmt);
		}
		return pstmt;
	}

	@Override
	public CCallableStatement newCCallableStatement(final CStatementVO info)
	{
		return new CCallableStatementProxy(info);
	}
}
