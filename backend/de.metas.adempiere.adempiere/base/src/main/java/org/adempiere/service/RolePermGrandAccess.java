package org.adempiere.service;

import static org.adempiere.model.InterfaceWrapperHelper.getCtx;

import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IADReferenceDAO.ADRefListItem;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Role_PermRequest;
import org.compiere.model.I_AD_Task;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;

import ch.qos.logback.classic.Level;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class RolePermGrandAccess
{
	private static final Logger logger = LogManager.getLogger(RolePermGrandAccess.class);
	
	public void grantAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		if (request.getAD_Window_ID() > 0)
		{
			grantWindowAccess(request);
		}
		else if (request.getAD_Process_ID() > 0)
		{
			grantProcessAccess(request);
		}
		else if (request.getAD_Form_ID() > 0)
		{
			grantFormAccess(request);
		}
		else if (request.getAD_Task_ID() > 0)
		{
			grantTaskAccess(request);
		}
		else if (request.getAD_Workflow_ID() > 0)
		{
			grantWorkflowAccess(request);
		}
		else if (request.getDocAction() != null)
		{
			grantDocActionAccess(request);
		}
		else
		{
			throw new AdempiereException("Can not identify what permissions to grant");
		}

		request.setIsPermissionGranted(true);
	}

	private void grantWindowAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final int AD_Window_ID = request.getAD_Window_ID();

		Preconditions.checkArgument(AD_Window_ID > 0, "invalid AD_Window_ID");
		final I_AD_Window window = InterfaceWrapperHelper.loadOutOfTrx(AD_Window_ID, I_AD_Window.class);

		Services.get(IUserRolePermissionsDAO.class).createWindowAccess(request.getAD_Role(), AD_Window_ID, request.isReadWrite());
		logGranted(I_AD_Window.COLUMNNAME_AD_Window_ID, window.getName());
	}

	private void grantProcessAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final int AD_Process_ID = request.getAD_Process_ID();

		request.setIsReadWrite(true); // we always need read write access to processes

		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final I_AD_Process adProcess = adProcessDAO.retrieveProcessById(getCtx(request), AD_Process_ID);

		Services.get(IUserRolePermissionsDAO.class).createProcessAccess(request.getAD_Role(), AD_Process_ID, request.isReadWrite());
		logGranted(I_AD_Process.COLUMNNAME_AD_Process_ID, adProcess.getName());

		//
		// Recursively grant access to child elements:
		if (adProcess.getAD_Form_ID() > 0)
		{
			grantFormAccess(request);
		}
		if (adProcess.getAD_Workflow_ID() > 0)
		{
			grantWorkflowAccess(request);
		}
	}

	private void grantFormAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final int AD_Form_ID = request.getAD_Form_ID();

		final I_AD_Form form = InterfaceWrapperHelper.loadOutOfTrx(AD_Form_ID, I_AD_Form.class);
		Services.get(IUserRolePermissionsDAO.class).createFormAccess(request.getAD_Role(), AD_Form_ID, request.isReadWrite());
		logGranted(I_AD_Form.COLUMNNAME_AD_Form_ID, form.getName());
	}

	private void grantWorkflowAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final int AD_Workflow_ID = request.getAD_Workflow_ID();

		final I_AD_Workflow wf = InterfaceWrapperHelper.loadOutOfTrx(AD_Workflow_ID, I_AD_Workflow.class);
		Services.get(IUserRolePermissionsDAO.class).createWorkflowAccess(request.getAD_Role(), AD_Workflow_ID, request.isReadWrite());
		logGranted(I_AD_Workflow.COLUMNNAME_AD_Workflow_ID, wf.getName());
	}

	private void grantTaskAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final int AD_Task_ID = request.getAD_Task_ID();
		final I_AD_Task task = InterfaceWrapperHelper.loadOutOfTrx(AD_Task_ID, I_AD_Task.class);
		Services.get(IUserRolePermissionsDAO.class).createTaskAccess(request.getAD_Role(), AD_Task_ID, request.isReadWrite());
		logGranted(I_AD_Task.COLUMNNAME_AD_Task_ID, task.getName());
	}

	private void grantDocActionAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final int C_DocType_ID = request.getC_DocType_ID();
		final String docAction = request.getDocAction();

		final I_C_DocType docType = InterfaceWrapperHelper.loadOutOfTrx(C_DocType_ID, I_C_DocType.class);

		final ADRefListItem docActionItem = Services.get(IADReferenceDAO.class).retrieveListItemOrNull(X_C_Invoice.DOCACTION_AD_Reference_ID, docAction);
		Check.assumeNotNull(docActionItem, "docActionItem is missing for {}", docAction);
		final int docActionRefListId = docActionItem.getRefListId();

		Services.get(IUserRolePermissionsDAO.class).createDocumentActionAccess(request.getAD_Role(), C_DocType_ID, docActionRefListId);
		logGranted(I_C_DocType.COLUMNNAME_C_DocType_ID, docType.getName() + "/" + docAction);
	}

	private void logGranted(final String type, final String name)
	{
		Loggables
				.getLoggableOrLogger(logger, Level.INFO)
				.addLog("Access granted: " + Services.get(IMsgBL.class).translate(Env.getCtx(), type) + ":" + name);

	}
}
