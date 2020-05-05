package de.metas.security.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Process;
import org.compiere.model.ModelValidator;

import de.metas.process.AdProcessId;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.requests.CreateProcessAccessRequest;
import de.metas.util.Services;

@Interceptor(I_AD_Process.class)
public class AD_Process
{
	public static final transient AD_Process instance = new AD_Process();

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void addAccessToRolesWithAutomaticMaintenance(final I_AD_Process process)
	{
		final AdProcessId adProcessId = AdProcessId.ofRepoId(process.getAD_Process_ID());

		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);

		for (final Role role : Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance())
		{
			permissionsDAO.createProcessAccess(CreateProcessAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adProcessId(adProcessId)
					.readWrite(true)
					.build());
		}

	}
}
