package org.adempiere.sql;

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
public interface IStatementsFactory
{

	/**
	 *
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @param trxName
	 * @return CStatement proxy
	 */
	public abstract CStatement newCStatement(int resultSetType, int resultSetConcurrency, String trxName);

	/**
	 *
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @param sql
	 * @param trxName
	 * @return CPreparedStatement proxy
	 */
	public abstract CPreparedStatement newCPreparedStatement(int resultSetType, int resultSetConcurrency, String sql, String trxName);

	/**
	 *
	 * @param resultSetType
	 * @param resultSetConcurrency
	 * @param sql
	 * @param trxName
	 * @return CCallableStatement proxy
	 */
	public abstract CCallableStatement newCCallableStatement(int resultSetType, int resultSetConcurrency, String sql, String trxName);

	/**
	 *
	 * @param info
	 * @return CStatement proxy
	 */
	public abstract CStatement newCStatement(CStatementVO info);

	/**
	 *
	 * @param info
	 * @return CPreparedStatement proxy
	 */
	public abstract CPreparedStatement newCPreparedStatement(CStatementVO info);

	/**
	 *
	 * @param info
	 * @return CCallableStatement proxy
	 */
	public abstract CCallableStatement newCCallableStatement(CStatementVO info);

}
