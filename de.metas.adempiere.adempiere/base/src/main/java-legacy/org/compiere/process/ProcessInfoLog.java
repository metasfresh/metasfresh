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
package org.compiere.process;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 	Process Info Log (VO)
 *
 *  @author Jorg Janke
 *  @version $Id: ProcessInfoLog.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public class ProcessInfoLog implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -4064380205358897097L;



	/**
	 * 	Create Process Info Log.
	 *	@param P_ID Process ID
	 *	@param P_Date Process Date
	 *	@param P_Number Process Number
	 *	@param P_Msg Process Messagre
	 */
	public ProcessInfoLog (Timestamp P_Date, BigDecimal P_Number, String P_Msg)
	{
		this (s_Log_ID++, P_Date, P_Number, P_Msg);
	}	//	ProcessInfoLog

	/**
	 * 	Create Process Info Log.
	 *	@param Log_ID Log ID
	 *	@param P_ID Process ID
	 *	@param P_Date Process Date
	 *	@param P_Number Process Number
	 *	@param P_Msg Process Messagre
	 */
	public ProcessInfoLog (int Log_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg)
	{
		setLog_ID (Log_ID);
		setP_Date (P_Date);
		setP_Number (P_Number);
		setP_Msg (P_Msg);
	}	//	ProcessInfoLog

	private static int	s_Log_ID = 0;

	private int 		m_Log_ID;
	private int 		m_P_ID;
	private Timestamp 	m_P_Date;
	private BigDecimal	m_P_Number;
	private String 		m_P_Msg;



	/**
	 * Get Log_ID
	 * @return id
	 */
	public int getLog_ID()
	{
		return m_Log_ID;
	}
	/**
	 * 	Set Log_ID
	 *	@param Log_ID id
	 */
	public void setLog_ID (int Log_ID)
	{
		m_Log_ID = Log_ID;
	}

	/**
	 * Method getP_ID
	 * @return int
	 */
	public int getP_ID()
	{
		return m_P_ID;
	}
	/**
	 * Method setP_ID
	 * @param P_ID int
	 */
	public void setP_ID (int P_ID)
	{
		m_P_ID = P_ID;
	}

	/**
	 * Method getP_Date
	 * @return Timestamp
	 */
	public Timestamp getP_Date()
	{
		return m_P_Date;
	}
	/**
	 * Method setP_Date
	 * @param P_Date Timestamp
	 */
	public void setP_Date (Timestamp P_Date)
	{
		m_P_Date = P_Date;
	}

	/**
	 * Method getP_Number
	 * @return BigDecimal
	 */
	public BigDecimal getP_Number()
	{
		return m_P_Number;
	}
	/**
	 * Method setP_Number
	 * @param P_Number BigDecimal
	 */
	public void setP_Number (BigDecimal P_Number)
	{
		m_P_Number = P_Number;
	}

	/**
	 * Method getP_Msg
	 * @return String
	 */
	public String getP_Msg()
	{
		return m_P_Msg;
	}
	/**
	 * Method setP_Msg
	 * @param P_Msg String
	 */
	public void setP_Msg (String P_Msg)
	{
		m_P_Msg = P_Msg;
	}

}	//	ProcessInfoLog
