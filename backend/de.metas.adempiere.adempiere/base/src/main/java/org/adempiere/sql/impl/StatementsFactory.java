package org.adempiere.sql.impl;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryStatisticsCollector;
import org.adempiere.sql.IStatementsFactory;
import org.compiere.util.CCallableStatement;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.CStatement;
import org.compiere.util.CStatementVO;

/**
 * Factory helper class used to create {@link CStatement}, {@link CPreparedStatement} and {@link CCallableStatement} instances.
 *
 * @author tsa
 */
public final class StatementsFactory implements IStatementsFactory
{
	public static final StatementsFactory instance = new StatementsFactory();
	private boolean sqlQueriesTracingEnabled = false;

	private StatementsFactory()
	{
	}

	public void enableSqlQueriesTracing(@NonNull final IQueryStatisticsCollector sqlQueriesCollector)
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
