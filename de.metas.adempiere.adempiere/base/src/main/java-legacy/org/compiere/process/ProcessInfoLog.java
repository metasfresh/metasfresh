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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Process Info Log (VO)
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfoLog.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
@SuppressWarnings("serial")
public final class ProcessInfoLog implements Serializable
{
	/**
	 * Create Process Info Log.
	 *
	 * @param P_ID Process ID
	 * @param P_Date Process Date
	 * @param P_Number Process Number
	 * @param P_Msg Process Messagre
	 */
	ProcessInfoLog(final int P_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg)
	{
		this(nextLogId.getAndIncrement(), P_ID, P_Date, P_Number, P_Msg);
	}	// ProcessInfoLog

	/**
	 * Create Process Info Log.
	 *
	 * @param Log_ID Log ID
	 * @param P_ID Process ID
	 * @param P_Date Process Date
	 * @param P_Number Process Number
	 * @param P_Msg Process message
	 */
	ProcessInfoLog(final int Log_ID, final int P_ID, final Timestamp P_Date, final BigDecimal P_Number, final String P_Msg)
	{
		super();
		m_Log_ID = Log_ID;
		m_P_ID = P_ID;
		m_P_Date = P_Date;
		m_P_Number = P_Number;
		m_P_Msg = P_Msg;
	}

	private static final AtomicInteger nextLogId = new AtomicInteger(1);

	private final int m_Log_ID;
	private final int m_P_ID;
	private final Timestamp m_P_Date;
	private final BigDecimal m_P_Number;
	private final String m_P_Msg;
	private boolean savedInDB = false;

	public int getLog_ID()
	{
		return m_Log_ID;
	}

	public int getP_ID()
	{
		return m_P_ID;
	}

	public Timestamp getP_Date()
	{
		return m_P_Date;
	}

	public BigDecimal getP_Number()
	{
		return m_P_Number;
	}

	public String getP_Msg()
	{
		return m_P_Msg;
	}
	
	public void markAsSavedInDB()
	{
		this.savedInDB = true;
	}
	
	public boolean isSavedInDB()
	{
		return savedInDB;
	}
}
