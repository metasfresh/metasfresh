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
package org.compiere.model;

import com.google.common.io.BaseEncoding;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import java.io.Serializable;
import java.sql.PreparedStatement;

/**
 * 	Persistent Object LOB.
 * 	Allows to store LOB remotely
 * 	Currently Oracle specific!
 *	
 *  @author Jorg Janke
 *  @version $Id: PO_LOB.java,v 1.2 2006/07/30 00:58:04 jjanke Exp $
 */
class PO_LOB implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -325477490976139224L;

	/**
	 * 	Constructor
	 *	@param tableName table name
	 *	@param columnName column name
	 *	@param whereClause where 
	 *	@param displayType display type
	 *	@param value value
	 */
	public PO_LOB (String tableName, String columnName, String whereClause, 
		int displayType, Object value)
	{
		m_tableName = tableName;
		m_columnName = columnName;
		m_whereClause = whereClause;
		m_displayType = displayType;
		m_value = value;
	}	//	PO_LOB

	/**	Logger					*/
	protected Logger	log = LogManager.getLogger(getClass());
	/**	Table Name				*/
	private String 		m_tableName;
	/** Column Name				*/
	private String 		m_columnName;
	/** Where Clause			*/
	private String		m_whereClause;
	
	/** Display Type			*/
	private int			m_displayType;
	/** Data					*/
	private Object 		m_value;

	/**
	 * 	Save LOB
	 * 	@param whereClause clause
	 * 	@param trxName trx name
	 *	@return true if saved
	 */
	public boolean save (String whereClause, String trxName)
	{
		m_whereClause = whereClause;
		return save(trxName);
	}	//	save

	/**
	 * 	Save LOB.
	 * 	see also org.compiere.session.ServerBean#updateLOB
	 * 	@param trxName trx name
	 *	@return true if saved
	 */
	public boolean save(String trxName)
	{
		final boolean[] success = new boolean[] { false };
		Services.get(ITrxManager.class).run(trxName, new TrxRunnable()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				success[0] = save0(localTrxName);
			}
		});

		return success[0];
	}
	
	private final boolean save0 (String trxName)
	{
		if (m_value == null
			|| (!(m_value instanceof String || m_value instanceof byte[])) 
			|| (m_value instanceof String && m_value.toString().length() == 0)
			|| (m_value instanceof byte[] && ((byte[])m_value).length == 0)
			)
		{
			StringBuilder sql = new StringBuilder("UPDATE ")
				.append(m_tableName)
				.append(" SET ").append(m_columnName)
				.append("=null WHERE ").append(m_whereClause);
			int no = DB.executeUpdate(sql.toString(), trxName);
			log.debug("save [" + trxName + "] #" + no + " - no data - set to null - " + m_value);
			if (no == 0)
				log.warn("[" + trxName + "] - not updated - " + sql);
			return true;
		}

		final String sql;
		final Object[] sqlParams;
		if (Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT))
		{
			final String valueBase64enc = BaseEncoding.base64().encode((byte[])m_value);
			sql = "UPDATE " + m_tableName + " SET " + m_columnName + "=DECODE(" + DB.TO_STRING(valueBase64enc) + ", 'base64') WHERE " + m_whereClause;
			sqlParams = new Object[] {};
		}
		else
		{
			sql = "UPDATE " + m_tableName + " SET " + m_columnName + "=? WHERE " + m_whereClause;
			sqlParams = new Object[]{m_value};
		}

		PreparedStatement pstmt = null;
		boolean success = true;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);
			final int no = pstmt.executeUpdate();
			if (no != 1)
			{
				log.warn("[" + trxName + "] - Not updated #" + no + " - " + sql);
				success = false;
			}
		}
		catch (Throwable e)
		{
			log.error("[" + trxName + "] - " + sql, e);
			success = false;
		}
		finally
		{
			DB.close(pstmt);
			pstmt = null;
		}
		
		return success;
	}	//	save

	
	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("PO_LOB[");
		sb.append(m_tableName).append(".").append(m_columnName)
			.append(",DisplayType=").append(m_displayType)
			.append("]");
		return sb.toString();
	}	//	toString
}	//	PO_LOB
