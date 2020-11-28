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

import java.math.BigDecimal;
import org.compiere.report.MReportLine;
import org.compiere.report.MReportLineSet;
import org.compiere.report.MReportSource;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 *	Copy Line Set at the end of the Line Set
 *
 *  @author Jorg Janke
 *  @version $Id: ReportLineSet_Copy.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ReportLineSet_Copy extends JavaProcess
{
	/**
	 * 	Constructor
	 */
	public ReportLineSet_Copy()
	{
		super();
	}	//	ReportLineSet_Copy

	/**	Source Line Set					*/
	private int		m_PA_ReportLineSet_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("PA_ReportLineSet_ID"))
				m_PA_ReportLineSet_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws Exception
	{
		int to_ID = super.getRecord_ID();
		log.info("From PA_ReportLineSet_ID=" + m_PA_ReportLineSet_ID + ", To=" + to_ID);
		if (to_ID < 1)
			throw new Exception(MSG_SaveErrorRowNotFound);
		//
		MReportLineSet to = new MReportLineSet(getCtx(), to_ID, get_TrxName());
		MReportLineSet rlSet = new MReportLineSet(getCtx(), m_PA_ReportLineSet_ID, get_TrxName());
		MReportLine[] rls = rlSet.getLiness();
		for (int i = 0; i < rls.length; i++)
		{
			MReportLine rl = MReportLine.copy (getCtx(), to.getAD_Client_ID(), to.getAD_Org_ID(), to_ID, rls[i], get_TrxName());
			rl.save();
			MReportSource[] rss = rls[i].getSources();
			if (rss != null)
			{
				for (int ii = 0; ii < rss.length; ii++)
				{
					MReportSource rs = MReportSource.copy (getCtx(), to.getAD_Client_ID(), to.getAD_Org_ID(), rl.get_ID(), rss[ii], get_TrxName());
					rs.save();
				}
			}
			//	Oper 1/2 were set to Null ! 
		}
		return "@Copied@=" + rls.length;
	}	//	doIt

}	//	ReportLineSet_Copy
