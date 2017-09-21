package org.adempiere.service;

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

import ch.qos.logback.classic.Level;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
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
public class RolePermRevokeAccess
{

	private static final Logger logger = LogManager.getLogger(RolePermRevokeAccess.class);

	public void revokeAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		if (request.getAD_Window_ID() > 0)
		{
			final I_AD_Window window = request.getAD_Window();
			permissionsDAO.deleteWindowAccess(request.getAD_Role(), window.getAD_Window_ID());
			logRevoked(I_AD_Window.COLUMNNAME_AD_Window_ID, window.getName());
		}
		else if (request.getAD_Process_ID() > 0)
		{
			final I_AD_Process process = request.getAD_Process();
			permissionsDAO.deleteProcessAccess(request.getAD_Role(), process.getAD_Process_ID());
			logRevoked(I_AD_Process.COLUMNNAME_AD_Process_ID, process.getName());
		}
		else if (request.getAD_Form_ID() > 0)
		{
			final I_AD_Form form = request.getAD_Form();
			permissionsDAO.deleteFormAccess(request.getAD_Role(), form.getAD_Form_ID());
			logRevoked(I_AD_Form.COLUMNNAME_AD_Form_ID, form.getName());
		}
		else if (request.getAD_Task_ID() > 0)
		{
			final I_AD_Task task = request.getAD_Task();
			permissionsDAO.deleteTaskAccess(request.getAD_Role(), task.getAD_Task_ID());
			logRevoked(I_AD_Task.COLUMNNAME_AD_Task_ID, task.getName());
		}
		else if (request.getAD_Workflow_ID() > 0)
		{
			final I_AD_Workflow wf = request.getAD_Workflow();
			permissionsDAO.deleteWorkflowAccess(request.getAD_Role(), wf.getAD_Workflow_ID());
			logRevoked(I_AD_Workflow.COLUMNNAME_AD_Workflow_ID, wf.getName());
		}
		else if (request.getDocAction() != null)
		{
			final I_C_DocType docType = InterfaceWrapperHelper.loadOutOfTrx(request.getC_DocType_ID(), I_C_DocType.class);

			final String docAction = request.getDocAction();
			final ADRefListItem docActionItem = Services.get(IADReferenceDAO.class).retrieveListItemOrNull(X_C_Invoice.DOCACTION_AD_Reference_ID, docAction);
			Check.assumeNotNull(docActionItem, "docActionItem is missing for {}", docAction);
			final int docActionRefListId = docActionItem.getRefListId();

			permissionsDAO.deleteDocumentActionAccess(request.getAD_Role(), docType.getC_DocType_ID(), docActionRefListId);
			logRevoked(I_C_DocType.COLUMNNAME_C_DocType_ID, docType.getName());
		}
		else
		{
			throw new AdempiereException("Can not identify what permissions to revoke");
		}
		//
		request.setIsPermissionGranted(false);
	}

	private void logRevoked(final String type, final String name)
	{
		Loggables
				.getLoggableOrLogger(logger, Level.INFO)
				.addLog("Access revoked: " + Services.get(IMsgBL.class).translate(Env.getCtx(), type) + ":" + name);
	}
}
