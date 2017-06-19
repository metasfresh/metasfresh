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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IADReferenceDAO.ADRefListItem;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.Preconditions;

import de.metas.i18n.IMsgBL;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;

/**
 * @author teo_sarca
 *
 */
public class MRolePermRequest extends X_AD_Role_PermRequest
{
	private static final long serialVersionUID = 7261084366898196463L;

	private static final String SYSCONFIG_PermLogLevel = "org.compiere.model.MRolePermRequest" + ".PermLogLevel";

	// private static final int PERMLOGLEVEL_AD_Reference_ID = 53356;
	private static final String PERMLOGLEVEL_Disabled = "D";
	// private static final String PERMLOGLEVEL_NotGranted = "E";
	private static final String PERMLOGLEVEL_Full = "F";

	private static final String DEFAULT_PermLogLevel = PERMLOGLEVEL_Disabled;

	public MRolePermRequest(final Properties ctx, final int AD_Role_PermRequest_ID, final String trxName)
	{
		super(ctx, AD_Role_PermRequest_ID, trxName);
	}

	public MRolePermRequest(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	private JavaProcess m_caller = null;

	public void setCaller(final JavaProcess process)
	{
		m_caller = process;
	}

	public static String getPermLogLevel()
	{
		final int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		return Services.get(ISysConfigBL.class).getValue(SYSCONFIG_PermLogLevel, DEFAULT_PermLogLevel, AD_Client_ID);
	}

	public static void setPermLogLevel(final String permLogLevel)
	{
		final Properties ctx = Env.getCtx();

		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_PermLogLevel, permLogLevel, 0);
		Env.setContext(ctx, "P|" + SYSCONFIG_PermLogLevel, permLogLevel);
	}

	public static void logWindowAccess(final int AD_Role_ID, final int id, final Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Window_ID, id, null, null, access, null);
	}

	public static void logWindowAccess(final int AD_Role_ID, final int id, final Boolean access, final String description)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Window_ID, id, null, null, access, description);
	}

	public static void logFormAccess(final int AD_Role_ID, final int id, final Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Form_ID, id, null, null, access, null);
	}

	public static void logProcessAccess(final int AD_Role_ID, final int id, final Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Process_ID, id, null, null, access, null);
	}

	public static void logTaskAccess(final int AD_Role_ID, final int id, final Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Task_ID, id, null, null, access, null);
	}

	public static void logWorkflowAccess(final int AD_Role_ID, final int id, final Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_AD_Workflow_ID, id, null, null, access, null);
	}

	public static void logDocActionAccess(final int AD_Role_ID, final int C_DocType_ID, final String docAction, final Boolean access)
	{
		logAccess(AD_Role_ID, COLUMNNAME_C_DocType_ID, C_DocType_ID, COLUMNNAME_DocAction, docAction, access, null);
	}

	private static void logAccess(final int AD_Role_ID, final String type, final Object value, final String type2, final Object value2, final Boolean access, final String description)
	{
		final String permLogLevel = getPermLogLevel();

		if (PERMLOGLEVEL_Disabled.equals(permLogLevel))
		{
			return;
		}

		final boolean isPermissionGranted = access != null;
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
		final boolean isReadWrite = access != null && access.booleanValue() == true;

		final ArrayList<Object> params = new ArrayList<>();
		final StringBuilder whereClause = new StringBuilder(COLUMNNAME_AD_Role_ID + "=? AND " + type + "=?");
		params.add(AD_Role_ID);
		params.add(value);
		if (type2 != null)
		{
			whereClause.append(" AND " + type2 + "=?");
			params.add(value2);
		}
		MRolePermRequest req = new Query(ctx, Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setOrderBy(COLUMNNAME_AD_Role_PermRequest_ID + " DESC")
				.first();
		if (req == null)
		{
			req = new MRolePermRequest(ctx, 0, trxName);
			req.setAD_Role_ID(AD_Role_ID);
			req.set_ValueOfColumn(type, value);
			if (type2 != null)
			{
				req.set_ValueOfColumn(type2, value2);
			}
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

	private void grantWindowAccess(final int AD_Window_ID)
	{
		Preconditions.checkArgument(AD_Window_ID > 0, "invalid AD_Window_ID");
		final I_AD_Window window = InterfaceWrapperHelper.loadOutOfTrx(AD_Window_ID, I_AD_Window.class);

		Services.get(IUserRolePermissionsDAO.class).createWindowAccess(getAD_Role(), AD_Window_ID, isReadWrite());
		logGranted(I_AD_Window.COLUMNNAME_AD_Window_ID, window.getName());
	}

	private void grantProcessAccess(final int AD_Process_ID)
	{
		setIsReadWrite(true); // we always need read write access to processes

		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final I_AD_Process adProcess = adProcessDAO.retrieveProcessById(getCtx(), AD_Process_ID);

		Services.get(IUserRolePermissionsDAO.class).createProcessAccess(getAD_Role(), AD_Process_ID, isReadWrite());
		logGranted(I_AD_Process.COLUMNNAME_AD_Process_ID, adProcess.getName());

		//
		// Recursively grant access to child elements:
		if (adProcess.getAD_Form_ID() > 0)
		{
			grantFormAccess(adProcess.getAD_Form_ID());
		}
		if (adProcess.getAD_Workflow_ID() > 0)
		{
			grantWorkflowAccess(adProcess.getAD_Workflow_ID());
		}
	}

	private void grantFormAccess(final int AD_Form_ID)
	{
		final I_AD_Form form = InterfaceWrapperHelper.loadOutOfTrx(AD_Form_ID, I_AD_Form.class);
		Services.get(IUserRolePermissionsDAO.class).createFormAccess(getAD_Role(), AD_Form_ID, isReadWrite());
		logGranted(I_AD_Form.COLUMNNAME_AD_Form_ID, form.getName());
	}

	private void grantWorkflowAccess(final int AD_Workflow_ID)
	{
		final I_AD_Workflow wf = InterfaceWrapperHelper.loadOutOfTrx(AD_Workflow_ID, I_AD_Workflow.class);
		Services.get(IUserRolePermissionsDAO.class).createWorkflowAccess(getAD_Role(), AD_Workflow_ID, isReadWrite());
		logGranted(I_AD_Workflow.COLUMNNAME_AD_Workflow_ID, wf.getName());
	}

	private void grantTaskAccess(final int AD_Task_ID)
	{
		final I_AD_Task task = InterfaceWrapperHelper.loadOutOfTrx(AD_Task_ID, I_AD_Task.class);
		Services.get(IUserRolePermissionsDAO.class).createTaskAccess(getAD_Role(), AD_Task_ID, isReadWrite());
		logGranted(I_AD_Task.COLUMNNAME_AD_Task_ID, task.getName());
	}

	private void grantDocActionAccess(final int C_DocType_ID, final String docAction)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.loadOutOfTrx(C_DocType_ID, I_C_DocType.class);

		final ADRefListItem docActionItem = Services.get(IADReferenceDAO.class).retrieveListItemOrNull(X_C_Invoice.DOCACTION_AD_Reference_ID, docAction);
		Check.assumeNotNull(docActionItem, "docActionItem is missing for {}", docAction);
		final int docActionRefListId = docActionItem.getRefListId();

		Services.get(IUserRolePermissionsDAO.class).createDocumentActionAccess(getAD_Role(), C_DocType_ID, docActionRefListId);
		logGranted(I_C_DocType.COLUMNNAME_C_DocType_ID, docType.getName() + "/" + docAction);
	}

	public void revokeAccess()
	{
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		if (getAD_Window_ID() > 0)
		{
			final I_AD_Window window = getAD_Window();
			permissionsDAO.deleteWindowAccess(getAD_Role(), window.getAD_Window_ID());
			logRevoked(I_AD_Window.COLUMNNAME_AD_Window_ID, window.getName());
		}
		else if (getAD_Process_ID() > 0)
		{
			final I_AD_Process process = getAD_Process();
			permissionsDAO.deleteProcessAccess(getAD_Role(), process.getAD_Process_ID());
			logRevoked(I_AD_Process.COLUMNNAME_AD_Process_ID, process.getName());
		}
		else if (getAD_Form_ID() > 0)
		{
			final I_AD_Form form = getAD_Form();
			permissionsDAO.deleteFormAccess(getAD_Role(), form.getAD_Form_ID());
			logRevoked(I_AD_Form.COLUMNNAME_AD_Form_ID, form.getName());
		}
		else if (getAD_Task_ID() > 0)
		{
			final I_AD_Task task = getAD_Task();
			permissionsDAO.deleteTaskAccess(getAD_Role(), task.getAD_Task_ID());
			logRevoked(I_AD_Task.COLUMNNAME_AD_Task_ID, task.getName());
		}
		else if (getAD_Workflow_ID() > 0)
		{
			final I_AD_Workflow wf = getAD_Workflow();
			permissionsDAO.deleteWorkflowAccess(getAD_Role(), wf.getAD_Workflow_ID());
			logRevoked(I_AD_Workflow.COLUMNNAME_AD_Workflow_ID, wf.getName());
		}
		else if (getDocAction() != null)
		{
			final I_C_DocType docType = InterfaceWrapperHelper.loadOutOfTrx(getC_DocType_ID(), I_C_DocType.class);

			final String docAction = getDocAction();
			final ADRefListItem docActionItem = Services.get(IADReferenceDAO.class).retrieveListItemOrNull(X_C_Invoice.DOCACTION_AD_Reference_ID, docAction);
			Check.assumeNotNull(docActionItem, "docActionItem is missing for {}", docAction);
			final int docActionRefListId = docActionItem.getRefListId();

			permissionsDAO.deleteDocumentActionAccess(getAD_Role(), docType.getC_DocType_ID(), docActionRefListId);
			logRevoked(I_C_DocType.COLUMNNAME_C_DocType_ID, docType.getName());
		}
		else
		{
			throw new AdempiereException("Can not identify what permissions to revoke");
		}
		//
		setIsPermissionGranted(false);
	}

	private void logGranted(final String type, final String name)
	{
		if (m_caller != null)
		{
			m_caller.addLog("@" + type + "@:" + name);
		}
		else
		{
			log.info("Access granted: " + Services.get(IMsgBL.class).translate(getCtx(), type) + ":" + name);
		}
	}

	private void logRevoked(final String type, final String name)
	{
		if (m_caller != null)
		{
			m_caller.addLog("@" + type + "@:" + name);
		}
		else
		{
			log.info("Access revoked: " + Services.get(IMsgBL.class).translate(getCtx(), type) + ":" + name);
		}
	}
}
