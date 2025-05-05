package org.adempiere.service;

import ch.qos.logback.classic.Level;
import de.metas.ad_reference.ADRefListItem;
import de.metas.ad_reference.ADReferenceService;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateDocActionAccessRequest;
import de.metas.security.requests.CreateFormAccessRequest;
import de.metas.security.requests.CreateProcessAccessRequest;
import de.metas.security.requests.CreateTaskAccessRequest;
import de.metas.security.requests.CreateWindowAccessRequest;
import de.metas.security.requests.CreateWorkflowAccessRequest;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.service.IADWorkflowBL;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
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
		final AdWindowId adWindowId = AdWindowId.ofRepoId(request.getAD_Window_ID());

		final RoleId roleId = RoleId.ofRepoId(request.getAD_Role_ID());
		final Role role = Services.get(IRoleDAO.class).getById(roleId);

		Services.get(IUserRolePermissionsDAO.class)
				.createWindowAccess(CreateWindowAccessRequest.builder()
						.roleId(role.getId())
						.clientId(role.getClientId())
						.orgId(role.getOrgId())
						.adWindowId(adWindowId)
						.readWrite(request.isReadWrite())
						.build());

		final ITranslatableString windowName = Services.get(IADWindowDAO.class).retrieveWindowName(adWindowId);
		logGranted(I_AD_Window.COLUMNNAME_AD_Window_ID, windowName);
	}

	private void grantProcessAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final AdProcessId processId = AdProcessId.ofRepoId(request.getAD_Process_ID());

		final RoleId roleId = RoleId.ofRepoId(request.getAD_Role_ID());
		final Role role = Services.get(IRoleDAO.class).getById(roleId);

		request.setIsReadWrite(true); // we always need read write access to processes

		Services.get(IUserRolePermissionsDAO.class)
				.createProcessAccess(CreateProcessAccessRequest.builder()
						.roleId(role.getId())
						.clientId(role.getClientId())
						.orgId(role.getOrgId())
						.adProcessId(processId)
						.readWrite(request.isReadWrite())
						.build());

		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
		final I_AD_Process adProcess = adProcessDAO.getById(processId);
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

		final RoleId roleId = RoleId.ofRepoId(request.getAD_Role_ID());
		final Role role = Services.get(IRoleDAO.class).getById(roleId);

		Services.get(IUserRolePermissionsDAO.class)
				.createFormAccess(CreateFormAccessRequest.builder()
						.roleId(role.getId())
						.clientId(role.getClientId())
						.orgId(role.getOrgId())
						.adFormId(AD_Form_ID)
						.readWrite(request.isReadWrite())
						.build());

		final I_AD_Form form = InterfaceWrapperHelper.loadOutOfTrx(AD_Form_ID, I_AD_Form.class);
		logGranted(I_AD_Form.COLUMNNAME_AD_Form_ID, form.getName());
	}

	private void grantWorkflowAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final WorkflowId workflowId = WorkflowId.ofRepoId(request.getAD_Workflow_ID());

		final RoleId roleId = RoleId.ofRepoId(request.getAD_Role_ID());
		final Role role = Services.get(IRoleDAO.class).getById(roleId);

		Services.get(IUserRolePermissionsDAO.class)
				.createWorkflowAccess(CreateWorkflowAccessRequest.builder()
						.roleId(role.getId())
						.clientId(role.getClientId())
						.orgId(role.getOrgId())
						.adWorkflowId(workflowId.getRepoId())
						.readWrite(request.isReadWrite())
						.build());

		final String workflowName = Services.get(IADWorkflowBL.class).getWorkflowName(workflowId);
		logGranted(I_AD_Workflow.COLUMNNAME_AD_Workflow_ID, workflowName);
	}

	private void grantTaskAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final int AD_Task_ID = request.getAD_Task_ID();

		final RoleId roleId = RoleId.ofRepoId(request.getAD_Role_ID());
		final Role role = Services.get(IRoleDAO.class).getById(roleId);

		Services.get(IUserRolePermissionsDAO.class)
				.createTaskAccess(CreateTaskAccessRequest.builder()
						.roleId(role.getId())
						.clientId(role.getClientId())
						.orgId(role.getOrgId())
						.adTaskId(AD_Task_ID)
						.readWrite(request.isReadWrite())
						.build());

		final I_AD_Task task = InterfaceWrapperHelper.loadOutOfTrx(AD_Task_ID, I_AD_Task.class);
		logGranted(I_AD_Task.COLUMNNAME_AD_Task_ID, task.getName());
	}

	private void grantDocActionAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoId(request.getC_DocType_ID());
		final String docAction = request.getDocAction();

		final ADRefListItem docActionItem = ADReferenceService.get().retrieveListItemOrNull(X_C_Invoice.DOCACTION_AD_Reference_ID, docAction);
		Check.assumeNotNull(docActionItem, "docActionItem is missing for {}", docAction);
		final int docActionRefListId = docActionItem.getRefListId().getRepoId();

		final RoleId roleId = RoleId.ofRepoId(request.getAD_Role_ID());
		final Role role = Services.get(IRoleDAO.class).getById(roleId);

		Services.get(IUserRolePermissionsDAO.class)
				.createDocumentActionAccess(CreateDocActionAccessRequest.builder()
						.roleId(role.getId())
						.clientId(role.getClientId())
						.orgId(role.getOrgId())
						.docTypeId(docTypeId)
						.docActionRefListId(docActionRefListId)
						.readWrite(request.isReadWrite())
						.build());

		final I_C_DocType docType = Services.get(IDocTypeDAO.class).getRecordById(docTypeId);
		logGranted(I_C_DocType.COLUMNNAME_C_DocType_ID, docType.getName() + "/" + docAction);
	}

	private void logGranted(final String type, final ITranslatableString name)
	{
		logGranted(type, name.translate(Env.getAD_Language()));
	}

	private void logGranted(final String type, final String name)
	{
		Loggables
				.getLoggableOrLogger(logger, Level.INFO)
				.addLog("Access granted: " + Services.get(IMsgBL.class).translate(Env.getCtx(), type) + ":" + name);

	}
}
