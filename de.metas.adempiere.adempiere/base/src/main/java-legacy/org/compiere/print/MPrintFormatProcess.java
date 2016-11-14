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
package org.compiere.print;

import java.math.BigDecimal;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 *	MPrintFormat Process.
 *  Performs Copy existing or Create from Table
 *  Called when pressing the Copy/Create button in Window Print Format
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: MPrintFormatProcess.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 *  @author		Michael Judd BF [ 2730339 ] - Error logging in MPrintFormatProcess
 */
public class MPrintFormatProcess extends SvrProcess
{
	/** PrintFormat             */
	private BigDecimal	m_AD_PrintFormat_ID;
	/** Table	                */
	private BigDecimal	m_AD_Table_ID;

	/**
	 *  Prepare - get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_PrintFormat_ID"))
				m_AD_PrintFormat_ID = ((BigDecimal)para[i].getParameter());
			else if (name.equals("AD_Table_ID"))
				m_AD_Table_ID = ((BigDecimal)para[i].getParameter());
			else
				log.error("prepare - Unknown Parameter=" 
						+ para[i].getParameterName());
		}
	}   //  prepare

	/**
	 *  Perform process.
	 *  <pre>
	 *  If AD_Table_ID is not null, create from table,
	 *  otherwise copy from AD_PrintFormat_ID
	 *  </pre>
	 * @return Message
	 * @throws Exception
	 */
	protected String doIt() throws Exception
	{
		if (m_AD_Table_ID != null && m_AD_Table_ID.intValue() > 0)
		{
			log.info("Create from AD_Table_ID=" + m_AD_Table_ID);
			MPrintFormat pf = MPrintFormat.createFromTable(getCtx(), m_AD_Table_ID.intValue(), getRecord_ID());
			addLog(m_AD_Table_ID.intValue(), null, new BigDecimal(pf.getItemCount()), pf.getName());
			return pf.getName() + " #" + pf.getItemCount();
		}
		else if (m_AD_PrintFormat_ID != null && m_AD_PrintFormat_ID.intValue() > 0)
		{
			log.info("Copy from AD_PrintFormat_ID=" + m_AD_PrintFormat_ID);
			MPrintFormat pf = MPrintFormat.copy (getCtx(), m_AD_PrintFormat_ID.intValue(), getRecord_ID());
			addLog(m_AD_PrintFormat_ID.intValue(), null, new BigDecimal(pf.getItemCount()), pf.getName());
			return pf.getName() + " #" + pf.getItemCount();
		}
		else
			throw new Exception (MSG_InvalidArguments);
	}	//	doIt

}	//	MPrintFormatProcess
