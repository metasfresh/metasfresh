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
import de.metas.security.requests.RemoveDocActionAccessRequest;
import de.metas.security.requests.RemoveFormAccessRequest;
import de.metas.security.requests.RemoveProcessAccessRequest;
import de.metas.security.requests.RemoveTaskAccessRequest;
import de.metas.security.requests.RemoveWindowAccessRequest;
import de.metas.security.requests.RemoveWorkflowAccessRequest;
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
public class RolePermRevokeAccess
{

	private static final Logger logger = LogManager.getLogger(RolePermRevokeAccess.class);

	public void revokeAccess(@NonNull final I_AD_Role_PermRequest request)
	{
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);

		final RoleId roleId = RoleId.ofRepoId(request.getAD_Role_ID());
		final Role role = Services.get(IRoleDAO.class).getById(roleId);

		if (request.getAD_Window_ID() > 0)
		{
			final AdWindowId windowId = AdWindowId.ofRepoId(request.getAD_Window_ID());
			permissionsDAO.deleteWindowAccess(RemoveWindowAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adWindowId(windowId)
					.build());
			logRevoked(I_AD_Window.COLUMNNAME_AD_Window_ID, Services.get(IADWindowDAO.class).retrieveWindowName(windowId));
		}
		else if (request.getAD_Process_ID() > 0)
		{
			final AdProcessId processId = AdProcessId.ofRepoId(request.getAD_Process_ID());
			permissionsDAO.deleteProcessAccess(RemoveProcessAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adProcessId(processId)
					.build());
			logRevoked(I_AD_Process.COLUMNNAME_AD_Process_ID, Services.get(IADProcessDAO.class).getProcessNameById(processId));
		}
		else if (request.getAD_Form_ID() > 0)
		{
			final int adFormId = request.getAD_Form_ID();

			permissionsDAO.deleteFormAccess(RemoveFormAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adFormId(adFormId)
					.build());

			final I_AD_Form form = InterfaceWrapperHelper.loadOutOfTrx(adFormId, I_AD_Form.class);
			logRevoked(I_AD_Form.COLUMNNAME_AD_Form_ID, form.getName());
		}
		else if (request.getAD_Task_ID() > 0)
		{
			final int adTaskId = request.getAD_Task_ID();

			permissionsDAO.deleteTaskAccess(RemoveTaskAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adTaskId(adTaskId)
					.build());

			final I_AD_Task task = InterfaceWrapperHelper.loadOutOfTrx(adTaskId, I_AD_Task.class);
			logRevoked(I_AD_Task.COLUMNNAME_AD_Task_ID, task.getName());
		}
		else if (request.getAD_Workflow_ID() > 0)
		{
			final WorkflowId workflowId = WorkflowId.ofRepoId(request.getAD_Workflow_ID());

			permissionsDAO.deleteWorkflowAccess(RemoveWorkflowAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adWorkflowId(workflowId.getRepoId())
					.build());

			final String workflowName = Services.get(IADWorkflowBL.class).getWorkflowName(workflowId);
			logRevoked(I_AD_Workflow.COLUMNNAME_AD_Workflow_ID, workflowName);
		}
		else if (request.getDocAction() != null)
		{
			final DocTypeId docTypeId = DocTypeId.ofRepoId(request.getC_DocType_ID());
			final String docAction = request.getDocAction();

			final ADRefListItem docActionItem = ADReferenceService.get().retrieveListItemOrNull(X_C_Invoice.DOCACTION_AD_Reference_ID, docAction);
			Check.assumeNotNull(docActionItem, "docActionItem is missing for {}", docAction);
			final int docActionRefListId = docActionItem.getRefListId().getRepoId();

			permissionsDAO.deleteDocumentActionAccess(RemoveDocActionAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.docTypeId(docTypeId)
					.docActionRefListId(docActionRefListId)
					.build());

			final I_C_DocType docType = Services.get(IDocTypeDAO.class).getRecordById(docTypeId);
			logRevoked(I_C_DocType.COLUMNNAME_C_DocType_ID, docType.getName());
		}
		else
		{
			throw new AdempiereException("Can not identify what permissions to revoke");
		}
		//
		request.setIsPermissionGranted(false);
	}

	private void logRevoked(final String type, final ITranslatableString name)
	{
		logRevoked(type, name.translate(Env.getAD_Language()));
	}

	private void logRevoked(final String type, final String name)
	{
		Loggables
				.getLoggableOrLogger(logger, Level.INFO)
				.addLog("Access revoked: " + Services.get(IMsgBL.class).translate(Env.getCtx(), type) + ":" + name);
	}
}
