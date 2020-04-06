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
import org.compiere.report.MReportColumn;
import org.compiere.report.MReportColumnSet;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 *  Copy Column Set at the end of the Column Set
 *
 *  @author Jorg Janke
 *  @version $Id: ReportColumnSet_Copy.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class ReportColumnSet_Copy extends JavaProcess
{
	/**
	 * 	Constructor
	 */
	public ReportColumnSet_Copy()
	{
		super();
	}	//	ReportColumnSet_Copy

	/**	Source Line Set					*/
	private int		m_PA_ReportColumnSet_ID = 0;

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
			else if (name.equals("PA_ReportColumnSet_ID"))
				m_PA_ReportColumnSet_ID = ((BigDecimal)para[i].getParameter()).intValue();
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
		log.info("From PA_ReportColumnSet_ID=" + m_PA_ReportColumnSet_ID + ", To=" + to_ID);
		if (to_ID < 1)
			throw new Exception(MSG_SaveErrorRowNotFound);
		//
		MReportColumnSet to = new MReportColumnSet(getCtx(), to_ID, get_TrxName());
		MReportColumnSet rcSet = new MReportColumnSet(getCtx(), m_PA_ReportColumnSet_ID, get_TrxName());
		MReportColumn[] rcs = rcSet.getColumns();
		for (int i = 0; i < rcs.length; i++)
		{
			MReportColumn rc = MReportColumn.copy (getCtx(), to.getAD_Client_ID(), to.getAD_Org_ID(), to_ID, rcs[i], get_TrxName());
			rc.save();
		}
		//	Oper 1/2 were set to Null !
		return "@Copied@=" + rcs.length;
	}	//	doIt

}	//	ReportColumnSet_Copy
