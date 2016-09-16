/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

/**
 * Adempiere Statement Value Object
 *
 * @author Jorg Janke
 * @version $Id: CStatementVO.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public class CStatementVO implements Serializable
{
	private static final long serialVersionUID = -6778280012583949122L;

	/**
	 * VO Constructor
	 *
	 * @param resultSetType - ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_SCROLL_SENSITIVE
	 * @param resultSetConcurrency - ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
	 */
	public CStatementVO(final int resultSetType, final int resultSetConcurrency, final String trxName)
	{
		super();
		m_resultSetType = resultSetType;
		m_resultSetConcurrency = resultSetConcurrency;
		m_sql = null;
		m_trxName = trxName;
	}	// CStatementVO

	/**
	 * VO Constructor
	 *
	 * @param resultSetType - ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_SCROLL_SENSITIVE
	 * @param resultSetConcurrency - ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
	 * @param sql sql
	 */
	public CStatementVO(final int resultSetType, final int resultSetConcurrency, final String sql, final String trxName)
	{
		super();
		m_resultSetType = resultSetType;
		m_resultSetConcurrency = resultSetConcurrency;
		m_sql = sql;
		m_trxName = trxName;
	}	// CStatementVO

	/** Type */
	private final int m_resultSetType;
	/** Concurrency */
	private final int m_resultSetConcurrency;
	/** SQL Statement */
	private String m_sql;
	/** Transaction Name **/
	private final String m_trxName;

	/**
	 * Debugging: collected SQL parameters for this statement
	 */
	private Map<Integer, Object> debugSqlParams;

	/**
	 * String representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("SQL")
				.omitNullValues()
				.add("trxName", m_trxName)
				.addValue(m_sql)
				.toString();
	}

	/**
	 * Get SQL
	 *
	 * @return sql
	 */
	public final String getSql()
	{
		return m_sql;
	}	// getSql

	public void setSql(final String sql)
	{
		m_sql = sql;
	}

	/**
	 * Get ResultSet Concurrency
	 *
	 * @return rs concurrency
	 */
	public int getResultSetConcurrency()
	{
		return m_resultSetConcurrency;
	}

	/**
	 * Get ResultSet Type
	 *
	 * @return rs type
	 */
	public int getResultSetType()
	{
		return m_resultSetType;
	}

	/**
	 * @return transaction name
	 */
	public String getTrxName()
	{
		return m_trxName;
	}

	public final void setDebugSqlParam(final int parameterIndex, final Object parameterValue)
	{
		if (debugSqlParams == null)
		{
			debugSqlParams = new TreeMap<>();
		}
		debugSqlParams.put(parameterIndex, parameterValue);
	}

	public final Map<Integer, Object> getDebugSqlParams()
	{
		final Map<Integer, Object> debugSqlParams = this.debugSqlParams;
		return debugSqlParams == null ? ImmutableMap.of() : debugSqlParams;
	}
}	// CStatementVO
