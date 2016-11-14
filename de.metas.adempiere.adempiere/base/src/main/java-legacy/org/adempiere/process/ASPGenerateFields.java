/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Carlos Ruiz - globalqss                               *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Carlos Ruiz - globalqss                                           *
*                                                                     *
* Sponsors:                                                           *
* - Company (http://www.globalqss.com)                                *
***********************************************************************/
package org.adempiere.process;

import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.MColumn;
import org.compiere.model.MField;
import org.compiere.model.MProcess;
import org.compiere.model.MTab;
import org.compiere.model.X_ASP_Field;
import org.compiere.model.X_ASP_Process;
import org.compiere.model.X_ASP_Process_Para;
import org.compiere.model.X_ASP_Tab;
import org.compiere.model.X_ASP_Workflow;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.wf.MWorkflow;

/**
 * 	Generate ASP fields for a window
 *	
 *  @author Carlos Ruiz
 */
public class ASPGenerateFields extends SvrProcess
{
	private String  p_ASP_Status;
	private int p_ASP_Level_ID;	
	
	private int noFields = 0;
	private int noProcesses = 0;
	private int noParameters = 0;
	private int noWorkflows = 0;

	private int p_ASP_Tab_ID;
	
	/**
	 * 	Prepare
	 */
	@Override
	protected void prepare ()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("ASP_Status"))
				p_ASP_Status = (String) para.getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_ASP_Tab_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	@Override
	protected String doIt () throws Exception
	{
		log.info("ASP_Status=" + p_ASP_Status 
				+ ", ASP_Tab_ID=" + p_ASP_Tab_ID
		);
		
		X_ASP_Tab asptab = new X_ASP_Tab(getCtx(), p_ASP_Tab_ID, get_TrxName());
		p_ASP_Level_ID = asptab.getASP_Window().getASP_Level_ID();

		// tabs
		MTab tab = new MTab(getCtx(), asptab.getAD_Tab_ID(), get_TrxName());
		// fields
		for (MField field : tab.getFields(true, get_TrxName())) {
			if (DB.getSQLValue(
					get_TrxName(),
					"SELECT COUNT(*) FROM ASP_Field WHERE ASP_Tab_ID = ? AND AD_Field_ID = ?",
					p_ASP_Tab_ID, field.getAD_Field_ID()) < 1) {
				X_ASP_Field aspField = new X_ASP_Field(getCtx(), 0, get_TrxName());
				aspField.setASP_Tab_ID(p_ASP_Tab_ID);
				aspField.setAD_Field_ID(field.getAD_Field_ID());
				aspField.setASP_Status(p_ASP_Status);
				if (aspField.save())
					noFields++;
			}
			// verify if a field is a button and assign permission to the corresponding process
			MColumn column = MColumn.get(getCtx(), field.getAD_Column_ID());
			if (column.getAD_Reference_ID() == DisplayType.Button) {
				if (column.getAD_Process_ID() > 0) {
					generateProcess(column.getAD_Process_ID());
				}
			}
		}

		if (noFields > 0)
			addLog("Field " + noFields);
		if (noProcesses > 0)
			addLog("Process " + noProcesses);
		if (noParameters > 0)
			addLog("Process Parameter " + noParameters);
		if (noWorkflows > 0)
			addLog("Workflow " + noWorkflows);

		return "@OK@";
	}	//	doIt

	private void generateProcess(int p_AD_Process_ID) {
		// Add Process and Parameters
		MProcess process = new MProcess(getCtx(), p_AD_Process_ID, get_TrxName());
		int asp_process_id = DB.getSQLValueEx(get_TrxName(),
				"SELECT COUNT(*) FROM ASP_Process WHERE ASP_Level_ID = ? AND AD_Process_ID = ?",
				p_ASP_Level_ID, process.getAD_Process_ID());
		X_ASP_Process aspProcess = null;
		if (asp_process_id < 1) {
			aspProcess = new X_ASP_Process(getCtx(), 0, get_TrxName());
			aspProcess.setASP_Level_ID(p_ASP_Level_ID);
			aspProcess.setAD_Process_ID(process.getAD_Process_ID());
			aspProcess.setASP_Status(p_ASP_Status);
			if (aspProcess.save()) {
				noProcesses++;
				asp_process_id = aspProcess.getASP_Process_ID();
			}
		} else {
			aspProcess = new X_ASP_Process(getCtx(), asp_process_id, get_TrxName());
		}
		// parameters
		for (final I_AD_Process_Para processpara : Services.get(IADProcessDAO.class).retrieveProcessParameters(process))
		{
			if (DB.getSQLValueEx(
					get_TrxName(),
					"SELECT COUNT(*) FROM ASP_Process_Para WHERE ASP_Process_ID = ? AND AD_Process_Para_ID = ?",
					asp_process_id, processpara.getAD_Process_Para_ID()) < 1) {
				X_ASP_Process_Para aspProcess_Para = new X_ASP_Process_Para(getCtx(), 0, get_TrxName());
				aspProcess_Para.setASP_Process_ID(asp_process_id);
				aspProcess_Para.setAD_Process_Para_ID(processpara.getAD_Process_Para_ID());
				aspProcess_Para.setASP_Status(p_ASP_Status);
				if (aspProcess_Para.save())
					noParameters++;
			}
		}
		if (process.getAD_Workflow_ID() > 0) {
			generateWorkflow(process.getAD_Workflow_ID());
		}
	}

	private void generateWorkflow(int p_AD_Workflow_ID) {
		// Add Workflow and Nodes
		MWorkflow workflow = new MWorkflow(getCtx(), p_AD_Workflow_ID, get_TrxName());
		if (DB.getSQLValueEx(
				get_TrxName(),
				"SELECT COUNT(*) FROM ASP_Workflow WHERE ASP_Level_ID = ? AND AD_Workflow_ID = ?",
				p_ASP_Level_ID, workflow.getAD_Workflow_ID()) < 1) {
			X_ASP_Workflow aspWorkflow = new X_ASP_Workflow(getCtx(), 0, get_TrxName());
			aspWorkflow.setASP_Level_ID(p_ASP_Level_ID);
			aspWorkflow.setAD_Workflow_ID(workflow.getAD_Workflow_ID());
			aspWorkflow.setASP_Status(p_ASP_Status);
			if (aspWorkflow.save())
				noWorkflows++;
		}
	}

}	//	ASPGenerateFields
