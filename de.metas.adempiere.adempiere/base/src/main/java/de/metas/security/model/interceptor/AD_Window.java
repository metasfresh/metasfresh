package de.metas.security.model.interceptor;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Window;
import org.compiere.model.ModelValidator;

import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.requests.CreateWindowAccessRequest;
import de.metas.util.Services;

@Interceptor(I_AD_Window.class)
public class AD_Window
{
	public static final transient AD_Window instance = new AD_Window();

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void addAccessToRolesWithAutomaticMaintenance(final I_AD_Window window)
	{
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);

		final AdWindowId adWindowId = AdWindowId.ofRepoId(window.getAD_Window_ID());

		for (final Role role : Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance())
		{
			permissionsDAO.createWindowAccess(CreateWindowAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adWindowId(adWindowId)
					.readWrite(true)
					.build());
		}

	}
}
