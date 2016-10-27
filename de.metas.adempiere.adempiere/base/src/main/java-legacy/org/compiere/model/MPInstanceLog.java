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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.compiere.util.DB;

/**
 *  Process Instance Log Model.
 * 	(not standard table)
 *
 *  @author Jorg Janke
 *  @version $Id: MPInstanceLog.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public class MPInstanceLog
{

	/**
	 * 	Load Constructor
	 * 	@param rs Result Set
	 * 	@throws SQLException
	 */
	public MPInstanceLog (ResultSet rs) throws SQLException
	{
		setAD_PInstance_ID(rs.getInt("AD_PInstance_ID"));
		setLog_ID(rs.getInt("Log_ID"));
		setP_Date(rs.getTimestamp("P_Date"));
		setP_ID(rs.getInt("P_ID"));
		setP_Number(rs.getBigDecimal("P_Number"));
		setP_Msg(rs.getString("P_Msg"));
	}	//	MPInstance_Log


	private int m_AD_PInstance_ID;
	private int m_Log_ID;
	private Timestamp m_P_Date;
	private int m_P_ID;
	private BigDecimal m_P_Number;
	private String m_P_Msg;


	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer("PPInstance_Log[");
		sb.append(m_Log_ID);
		if (m_P_Date != null)
			sb.append(",Date=").append(m_P_Date);
		if (m_P_ID != 0)
			sb.append(",ID=").append(m_P_ID);
		if (m_P_Number != null)
			sb.append(",Number=").append(m_P_Number);
		if (m_P_Msg != null)
			sb.append(",").append(m_P_Msg);
		sb.append("]");
		return sb.toString();
	}	//	toString


	/**
	 *	Save to Database
	 * 	@return true if saved
	 */
	public boolean save ()
	{
		StringBuffer sql = new StringBuffer("INSERT INTO AD_PInstance_Log "
			+ "(AD_PInstance_ID, P_Date, P_ID, P_Number, P_Msg)"
			+ " VALUES (");
		sql.append(m_AD_PInstance_ID).append(",")
		  .append(m_Log_ID).append(",");
		if (m_P_Date == null)
		{
			sql.append("NULL,");
		}
		else
		{
			sql.append(DB.TO_DATE(m_P_Date, false)).append(",");
		}
		if (m_P_ID == 0)
		{
			sql.append("NULL,");
		}
		else
		{
			sql.append(m_P_ID).append(",");
		}
		if (m_P_Number == null)
		{
			sql.append("NULL,");
		}
		else
		{
			sql.append(m_P_Number).append(",");
		}
		if (m_P_Msg == null)
		{
			sql.append("NULL)");
		}
		else
		{
			sql.append(DB.TO_STRING(m_P_Msg, 2000)).append(")");
			//
		}
		int no = DB.executeUpdate(sql.toString(), null);	//	outside of trx
		return no == 1;
	} 	//	save

	/**
	 * 	Get AD_PInstance_ID
	 *	@return Instance id
	 */
	public int getAD_PInstance_ID ()
	{
		return m_AD_PInstance_ID;
	}

	/**
	 * 	Set AD_PInstance_ID
	 *	@param AD_PInstance_ID instance id
	 */
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		m_AD_PInstance_ID = AD_PInstance_ID;
	}

	/**
	 * 	Get Log_ID
	 *	@return log id
	 */
	public int getLog_ID ()
	{
		return m_Log_ID;
	}

	/**
	 * 	Set Log_ID
	 *	@param Log_ID log id
	 */
	public void setLog_ID (int Log_ID)
	{
		m_Log_ID = Log_ID;
	}

	/**
	 * 	Get P_Date
	 *	@return date
	 */
	public Timestamp getP_Date ()
	{
		return m_P_Date;
	}

	/**
	 * 	Set P_Date
	 *	@param P_Date date
	 */
	public void setP_Date (Timestamp P_Date)
	{
		m_P_Date = P_Date;
	}

	/**
	 * 	Get P_ID
	 *	@return id
	 */
	public int getP_ID ()
	{
		return m_P_ID;
	}

	/**
	 * 	Set P_ID
	 *	@param P_ID id
	 */
	public void setP_ID (int P_ID)
	{
		m_P_ID = P_ID;
	}

	/**
	 * 	Get P_Number
	 *	@return number
	 */
	public BigDecimal getP_Number ()
	{
		return m_P_Number;
	}

	/**
	 * 	Set P_Number
	 *	@param P_Number number
	 */
	public void setP_Number (BigDecimal P_Number)
	{
		m_P_Number = P_Number;
	}

	/**
	 * 	Get P_Msg
	 *	@return Mag
	 */
	public String getP_Msg ()
	{
		return m_P_Msg;
	}

	/**
	 * 	Set P_Msg
	 *	@param P_Msg
	 */
	public void setP_Msg (String P_Msg)
	{
		m_P_Msg = P_Msg;
	}

} //	MPInstance_Log
