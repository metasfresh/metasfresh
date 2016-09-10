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


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.compiere.util.CStatementVO;

/* package */final class CStatementProxy extends AbstractCStatementProxy<Statement>
{
	public CStatementProxy(final int resultSetType, final int resultSetConcurrency, final String trxName)
	{
		super(new CStatementVO(resultSetType, resultSetConcurrency, trxName));
	}

	public CStatementProxy(final CStatementVO vo)
	{
		super(vo);
	}

	@Override
	protected final Statement createStatement(final Connection conn, final CStatementVO vo) throws SQLException
	{
		final Statement stmt = conn.createStatement(vo.getResultSetType(), vo.getResultSetConcurrency());
		return stmt;
	}
}
