package de.metas.mobile.application.interceptor;

import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.requests.CreateMobileApplicationAccessRequest;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_Mobile_Application;
import org.compiere.model.ModelValidator;

@Interceptor(I_Mobile_Application.class)
@RequiredArgsConstructor
public class Mobile_Application
{
	@NonNull private final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createAccess(@NonNull final I_Mobile_Application record)
	{
		final MobileApplicationRepoId mobileApplicationId = MobileApplicationRepoId.ofRepoId(record.getMobile_Application_ID());

		for (final Role role : roleDAO.retrieveAllRolesWithAutoMaintenance())
		{
			permissionsDAO.createMobileApplicationAccess(CreateMobileApplicationAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.applicationId(mobileApplicationId)
					.build());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteAccess(@NonNull final I_Mobile_Application record)
	{
		final MobileApplicationRepoId mobileApplicationId = MobileApplicationRepoId.ofRepoId(record.getMobile_Application_ID());
		permissionsDAO.deleteMobileApplicationAccess(mobileApplicationId);
	}
}
