/**
 * 
 */
package org.compiere.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.compiere.wf.MWorkflowAccess;

import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;

/**
 * @author teo_sarca
 *
 */
public class MRolePermRequest extends X_AD_Role_PermRequest
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4186724988700700783L;

	public static final String SYSCONFIG_PermLogLevel = "org.compiere.model.MRolePermRequest"+".PermLogLevel";
	
	public static final int PERMLOGLEVEL_AD_Reference_ID = 53356;
	public static final String PERMLOGLEVEL_Disabled = "D";
	public static final String PERMLOGLEVEL_NotGranted = "E";
	public static final String PERMLOGLEVEL_Full = "F";
	
	public static final String DEFAULT_PermLogLevel = PERMLOGLEVEL_Disabled;
	
	public MRolePermRequest(Properties ctx, int AD_Role_PermRequest_ID, String trxName)
	{
		super(ctx, AD_Role_PermRequest_ID, trxName);
	}

	public MRolePermRequest(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	private JavaProcess m_caller = null;
	public void setCaller(JavaProcess process)
	{
		m_caller = process;
	}
	
	public static String getPermLogLevel()
	{
		final int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		return Services.get(ISysConfigBL.class).getValue(SYSCONFIG_PermLogLevel, DEFAULT_PermLogLevel, AD_Client_ID);
	}
	
	public static void setPermLogLevel(String permLogLevel)
	{
		final Properties ctx = Env.getCtx();
		
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_PermLogLevel, permLogLevel, 0);
		Env.setContext(ctx, "P|"+SYSCONFIG_PermLogLevel, permLogLevel);
	}

	public static void logWindowAccess(int AD_Role_ID, int id, Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Window_ID, id, null, null, access, null);
	}
	public static void logWindowAccess(int AD_Role_ID, int id, Boolean access, String description)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Window_ID, id, null, null, access, description);
	}
	public static void logFormAccess(int AD_Role_ID, int id, Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Form_ID, id, null, null, access, null);
	}
	public static void logProcessAccess(int AD_Role_ID, int id, Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Process_ID, id, null, null, access, null);
	}
	public static void logTaskAccess(int AD_Role_ID, int id, Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Task_ID, id, null, null, access, null);
	}
	public static void logWorkflowAccess(int AD_Role_ID, int id, Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Workflow_ID, id, null, null, access, null);
	}
	public static void logDocActionAccess(int AD_Role_ID, int C_DocType_ID, String docAction, Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_C_DocType_ID, C_DocType_ID, COLUMNNAME_DocAction, docAction, access, null);
	}
	
	private static void logAccess(int AD_Role_ID, String type, Object value, String type2, Object value2, Boolean access, String description)
	{
		final String permLogLevel = getPermLogLevel();
		
		if (PERMLOGLEVEL_Disabled.equals(permLogLevel))
			return;
		
		final boolean isPermissionGranted = (access != null);
		if (isPermissionGranted && !PERMLOGLEVEL_Full.equals(permLogLevel))
		{
			// Permission granted, and there is no need to log all requests => nothing to do
			return;
		}
		if (value instanceof Integer && ((Integer)value).intValue() <= 0)
		{
			// Skip invalid permissions request
			return;
		}
		
		final Properties ctx = Env.getCtx();
		final String trxName = null;
		final boolean isReadWrite = (access != null && access.booleanValue() == true);
		
		final ArrayList<Object> params = new ArrayList<Object>();
		final StringBuilder whereClause = new StringBuilder(COLUMNNAME_AD_Role_ID+"=? AND "+type+"=?");
		params.add(AD_Role_ID);
		params.add(value);
		if (type2 != null)
		{
			whereClause.append(" AND "+type2+"=?");
			params.add(value2);
		}
		MRolePermRequest req = new Query(ctx, Table_Name, whereClause.toString(), trxName)
		.setParameters(params)
		.setOrderBy(COLUMNNAME_AD_Role_PermRequest_ID+" DESC")
		.first();
		if (req == null)
		{
			req = new MRolePermRequest(ctx, 0, trxName);
			req.setAD_Role_ID(AD_Role_ID);
			req.set_ValueOfColumn(type, value);
			if (type2 != null)
				req.set_ValueOfColumn(type2, value2);
		}
		req.setIsActive(true);
		req.setIsReadWrite(isReadWrite);
		req.setIsPermissionGranted(isPermissionGranted);
		req.setDescription(description);
		req.saveEx();
	}

	public void grantAccess()
	{
		if (getAD_Window_ID() > 0)
		{
			grantWindowAccess(getAD_Window_ID());
		}
		else if (getAD_Process_ID() > 0)
		{
			grantProcessAccess(getAD_Process_ID());
		}
		else if (getAD_Form_ID() > 0)
		{
			grantFormAccess(getAD_Form_ID());
		}
		else if (getAD_Task_ID() > 0)
		{
			grantTaskAccess(getAD_Task_ID());
		}
		else if (getAD_Workflow_ID() > 0)
		{
			grantWorkflowAccess(getAD_Workflow_ID());
		}
		else if (getDocAction() != null)
		{
			grantDocActionAccess(getC_DocType_ID(), getDocAction());
		}
		else
		{
			throw new AdempiereException("Can not identify what permissions to grant");
		}
		//
		setIsPermissionGranted(true);
	}
	private void grantWindowAccess(int AD_Window_ID)
	{
		final MWindow window = new MWindow(getCtx(), AD_Window_ID, get_TrxName());
		MWindowAccess a = getAccessRecord(MWindowAccess.Table_Name, MWindowAccess.COLUMNNAME_AD_Window_ID, AD_Window_ID, null);
		if (a == null)
		{
			a = new MWindowAccess(window, getAD_Role());
		}
		a.setIsReadWrite(isReadWrite());
		a.setIsActive(true);
		a.saveEx();
		logGranted(MWindow.COLUMNNAME_AD_Window_ID, window.getName());
	}
	private void grantProcessAccess(final int AD_Process_ID)
	{
		setIsReadWrite(true); // we always need read write access to processes
		
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final I_AD_Process adProcess = adProcessDAO.retrieveProcessById(getCtx(), AD_Process_ID);
		final I_AD_Process_Access a = adProcessDAO.retrieveProcessAccessOrCreateDraft(getCtx(), AD_Process_ID, getAD_Role());
		a.setIsReadWrite(isReadWrite());
		a.setIsActive(true);
		InterfaceWrapperHelper.save(a);
		logGranted(I_AD_Process.COLUMNNAME_AD_Process_ID, adProcess.getName());
		//
		// Recursively grant access to child elements:
		if (adProcess.getAD_Form_ID() > 0)
			grantFormAccess(adProcess.getAD_Form_ID());
		if (adProcess.getAD_Workflow_ID() > 0)
			grantWorkflowAccess(adProcess.getAD_Workflow_ID());
	}
	private void grantFormAccess(int AD_Form_ID)
	{
		final MForm form = new MForm(getCtx(), AD_Form_ID, get_TrxName());
		MFormAccess a = getAccessRecord(MFormAccess.Table_Name, MFormAccess.COLUMNNAME_AD_Form_ID, AD_Form_ID, null);
		if (a == null)
		{
			a = new MFormAccess(form, getAD_Role());
		}
		a.setIsReadWrite(isReadWrite());
		a.setIsActive(true);
		a.saveEx();
		logGranted(MForm.COLUMNNAME_AD_Form_ID, form.getName());
	}
	private void grantWorkflowAccess(int AD_Workflow_ID)
	{
		final MWorkflow wf = new MWorkflow(getCtx(), AD_Workflow_ID, get_TrxName());
		MWorkflowAccess a = getAccessRecord(MWorkflowAccess.Table_Name, MWorkflowAccess.COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID, null);
		if (a == null)
		{
			a = new MWorkflowAccess(wf, getAD_Role());
		}
		a.setIsReadWrite(isReadWrite());
		a.setIsActive(true);
		a.saveEx();
		logGranted(MWorkflow.COLUMNNAME_AD_Workflow_ID, wf.getName());
	}
	private void grantTaskAccess(int AD_Task_ID)
	{
		final MTask task = new MTask(getCtx(), AD_Task_ID, get_TrxName());
		MTaskAccess a = getAccessRecord(MTaskAccess.Table_Name, MTaskAccess.COLUMNNAME_AD_Task_ID, AD_Task_ID, null);
		if (a == null)
		{
			a = new MTaskAccess(task, getAD_Role());
		}
		a.setIsReadWrite(isReadWrite());
		a.setIsActive(true);
		a.saveEx();
		logGranted(MTask.COLUMNNAME_AD_Task_ID, task.getName());
	}
	private void grantDocActionAccess(int C_DocType_ID, String docAction)
	{
		final MDocType dt = new MDocType(getCtx(), C_DocType_ID, get_TrxName());
		final MRefList rl = MRefList.get(getCtx(), X_C_Invoice.DOCACTION_AD_Reference_ID, docAction, get_TrxName());
		MDocumentActionAccess a = getAccessRecord(MDocumentActionAccess.Table_Name,
				MDocumentActionAccess.COLUMNNAME_C_DocType_ID, C_DocType_ID,
				MDocumentActionAccess.COLUMNNAME_AD_Ref_List_ID+"="+rl.getAD_Ref_List_ID());
		if (a == null)
		{
			a = new MDocumentActionAccess(dt, getAD_Role());
			a.setAD_Ref_List_ID(rl.getAD_Ref_List_ID());
		}
		a.setIsActive(true);
		a.saveEx();
		logGranted(MDocType.COLUMNNAME_C_DocType_ID, dt.getName()+"/"+docAction);
	}
	
	public void revokeAccess()
	{
		if (getAD_Window_ID() > 0)
		{
			MWindowAccess a = getAccessRecord(MWindowAccess.Table_Name, MWindowAccess.COLUMNNAME_AD_Window_ID, getAD_Window_ID(), null);
			if (a != null)
			{
				a.setIsActive(false);
				a.saveEx();
			}
			final I_AD_Window window = getAD_Window();
			logRevoked(MWindow.COLUMNNAME_AD_Window_ID, window.getName());
		}
		else if (getAD_Process_ID() > 0)
		{
			final I_AD_Process_Access a = Services.get(IADProcessDAO.class).retrieveProcessAccess(getCtx(), getAD_Process_ID(), getAD_Role_ID());
			if (a != null)
			{
				a.setIsActive(false);
				InterfaceWrapperHelper.save(a);
			}
			final I_AD_Process process = getAD_Process();
			logRevoked(I_AD_Process.COLUMNNAME_AD_Process_ID, process.getName());
		}
		else if (getAD_Form_ID() > 0)
		{
			MFormAccess a = getAccessRecord(MFormAccess.Table_Name, MFormAccess.COLUMNNAME_AD_Form_ID, getAD_Form_ID(), null);
			if (a != null)
			{
				a.setIsActive(false);
				a.saveEx();
			}
			final I_AD_Form form = getAD_Form();
			logRevoked(MForm.COLUMNNAME_AD_Form_ID, form.getName());
		}
		else if (getAD_Task_ID() > 0)
		{
			MTaskAccess a = getAccessRecord(MTaskAccess.Table_Name, MTaskAccess.COLUMNNAME_AD_Task_ID, getAD_Task_ID(), null);
			if (a != null)
			{
				a.setIsActive(false);
				a.saveEx();
			}
			final I_AD_Task task = getAD_Task();
			logRevoked(MTask.COLUMNNAME_AD_Task_ID, task.getName());
		}
		else if (getAD_Workflow_ID() > 0)
		{
			MWorkflowAccess a = getAccessRecord(MWorkflowAccess.Table_Name, MWorkflowAccess.COLUMNNAME_AD_Workflow_ID, getAD_Workflow_ID(), null);
			if (a != null)
			{
				a.setIsActive(false);
				a.saveEx();
			}
			final I_AD_Workflow wf = getAD_Workflow();
			logRevoked(MWorkflow.COLUMNNAME_AD_Workflow_ID, wf.getName());
		}
		else if (getDocAction() != null)
		{
			MRefList rl = MRefList.get(getCtx(), X_C_Invoice.DOCACTION_AD_Reference_ID, getDocAction(), get_TrxName());
			MDocumentActionAccess a = getAccessRecord(MDocumentActionAccess.Table_Name,
					MDocumentActionAccess.COLUMNNAME_C_DocType_ID, getC_DocType_ID(),
					MDocumentActionAccess.COLUMNNAME_AD_Ref_List_ID+"="+rl.getAD_Ref_List_ID());
			if (a != null)
			{
				a.setIsActive(false);
				a.saveEx();
			}
			final I_C_DocType dt = getC_DocType();
			logRevoked(MDocType.COLUMNNAME_C_DocType_ID, dt.getName());
		}
		else
		{
			throw new AdempiereException("Can not identify what permissions to grant");
		}
		//
		setIsPermissionGranted(false);
	}

	/**
	 * Get AD_*_Access Record
	 * @param accessTableName
	 * @param parentColumnName
	 * @param parent_id
	 * @param additionalWhereClause
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends PO> T getAccessRecord(String accessTableName,
			String parentColumnName, int parent_id,
			String additionalWhereClause)
	{
		final StringBuilder whereClause = new StringBuilder("AD_Role_ID=?");
		whereClause.append(" AND ").append(parentColumnName).append("=?");
		if (!Check.isEmpty(additionalWhereClause, true))
		{
			whereClause.append(" AND (").append(additionalWhereClause).append(")");
		}
		T a = (T)new Query(getCtx(), accessTableName, whereClause.toString(), get_TrxName())
		.setParameters(getAD_Role_ID(), parent_id)
		.firstOnly();
		return a;
	}

	private void logGranted(String type, String name)
	{
		if (m_caller != null)
		{
			m_caller.addLog("@"+type+"@:"+name);
		}
		else
		{
			log.info("Access granted: " + Services.get(IMsgBL.class).translate(getCtx(), type) + ":" + name);
		}
	}
	private void logRevoked(String type, String name)
	{
		if (m_caller != null)
		{
			m_caller.addLog("@"+type+"@:"+name);
		}
		else
		{
			log.info("Access revoked: " + Services.get(IMsgBL.class).translate(getCtx(), type) + ":" + name);
		}
	}
}
