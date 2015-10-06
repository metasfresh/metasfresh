package org.adempiere.sql.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
	public StatementsFactory()
	{
		super();
	}

	@Override
	public CStatement newCStatement(final int resultSetType, final int resultSetConcurrency, final String trxName)
	{
		return new CStatementProxy(resultSetType, resultSetConcurrency, trxName);
	}

	@Override
	public CPreparedStatement newCPreparedStatement(final int resultSetType, final int resultSetConcurrency, final String sql, final String trxName)
	{
		return new CPreparedStatementProxy(resultSetType, resultSetConcurrency, sql, trxName);
	}

	@Override
	public CCallableStatement newCCallableStatement(final int resultSetType, final int resultSetConcurrency, final String sql, final String trxName)
	{
		return new CCallableStatementProxy(resultSetType, resultSetConcurrency, sql, trxName);
	}

	@Override
	public CStatement newCStatement(final CStatementVO info)
	{
		return new CStatementProxy(info);
	}

	@Override
	public CPreparedStatement newCPreparedStatement(final CStatementVO info)
	{
		return new CPreparedStatementProxy(info);
	}

	@Override
	public CCallableStatement newCCallableStatement(final CStatementVO info)
	{
		return new CCallableStatementProxy(info);
	}
}
