package de.metas.security.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.ModelValidator;

import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.requests.CreateWorkflowAccessRequest;
import de.metas.util.Services;

@Interceptor(I_AD_Workflow.class)
public class AD_Workflow
{
	public static final transient AD_Workflow instance = new AD_Workflow();

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void addAccessToRolesWithAutomaticMaintenance(final I_AD_Workflow workflow)
	{
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);

		final int adWorkflowId = workflow.getAD_Workflow_ID();
		for (final Role role : Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance())
		{
			permissionsDAO.createWorkflowAccess(CreateWorkflowAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adWorkflowId(adWorkflowId)
					.readWrite(true)
					.build());
		}

	}
}
