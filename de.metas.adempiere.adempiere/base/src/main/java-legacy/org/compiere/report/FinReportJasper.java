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
package org.compiere.report;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.ad.service.IADPInstanceDAO;
import org.adempiere.util.ProcessUtil;
import org.adempiere.util.Services;
import org.compiere.model.MPInstance;
import org.compiere.model.MProcess;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;


/**
 *  Financial Report Engine
 *
 *  @author Jorg Janke
 *  @version $Id: FinReport.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class FinReportJasper extends FinReport
{

	/**	Report Definition				*/
	private MReport				m_report = null;

	/**************************************************************************
	 *  Perform process.
	 *  @return Message to be translated
	 *  @throws Exception
	 */
	protected String doIt() throws Exception
	{
		// Call the normal FinReport to fill the T_Report table
		String finReportMsg = super.doIt();

		// Now invoke the associated jasper report (must report on the T_Report table)
		ArrayList<ProcessInfoParameter> list = new ArrayList<ProcessInfoParameter>();

		// Copy the list of parameters from the financial report
		ProcessInfoParameter oldpara[] = getParameter();
		for (int i = 0; i < oldpara.length; i++)
			list.add (oldpara[i]);
		// and add the T_Report_AD_PInstance_ID parameter
		list.add (new ProcessInfoParameter("T_Report_AD_PInstance_ID", new Integer(getAD_PInstance_ID()), null, null, null));
		ProcessInfoParameter[] pars = new ProcessInfoParameter[list.size()];
		list.toArray(pars);

		//	Load Report Definition
		m_report = new MReport (getCtx(), getRecord_ID(), get_TrxName());

		MProcess proc = new MProcess(getCtx(), m_report.getJasperProcess_ID(), get_TrxName());
	    MPInstance instance = new MPInstance(proc, getTable_ID(), getRecord_ID());
	    instance.setWhereClause(getProcessInfo().getWhereClause());
	    instance.save();
	    ProcessInfo poInfo = new ProcessInfo(proc.getName(), proc.getAD_Process_ID());
	    poInfo.setParameter(pars);
	    poInfo.setRecord_ID(getRecord_ID());
	    poInfo.setAD_Process_ID(proc.getAD_Process_ID());
	    poInfo.setAD_PInstance_ID(instance.getAD_PInstance_ID());
	    
	    Services.get(IADPInstanceDAO.class).saveParameterToDB(poInfo); // 07154
	    
	    // need to commit in order to allow jasper to view the data
	    Trx trx = Trx.get(get_TrxName(), true);
	    trx.commit();
	    
	    // CarlosRuiz - globalqss - allow procedure preprocess
	    if (proc.getProcedureName() != null && proc.getProcedureName().length() > 0) {
			//  execute on this thread/connection
			String sql = "{call " + proc.getProcedureName() + "(?)}";
			try(final CallableStatement cstmt = DB.prepareCall(sql)) //	ro??
			{
				cstmt.setInt(1, getAD_PInstance_ID());
				cstmt.executeUpdate();
				cstmt.close();
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
				poInfo.setThrowable(e); // 03152
				poInfo.setSummary (Msg.getMsg(Env.getCtx(), "ProcessRunError") + " " + e.getLocalizedMessage());
			}
	    }
	    
	    // TODO - allow java class preprocess if the classname <> ProcessUtil.JASPER_STARTER_CLASS

	    ProcessUtil.startJavaProcess(getCtx(), poInfo, trx);
	    
	    return finReportMsg;
	}	//	doIt

}	//	FinReport
