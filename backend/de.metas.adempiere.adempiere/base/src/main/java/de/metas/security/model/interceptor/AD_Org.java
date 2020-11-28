package de.metas.security.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.util.Services;

@Interceptor(I_AD_Org.class)
public class AD_Org
{
	public static final transient AD_Org instance = new AD_Org();

	private final transient Logger logger = LogManager.getLogger(getClass());

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void addAccessToRolesWithAutomaticMaintenance(final I_AD_Org org)
	{
		final ClientId orgClientId = ClientId.ofRepoId(org.getAD_Client_ID());

		int orgAccessCreatedCounter = 0;
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		for (final Role role : Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance())
		{
			// Don't create org access for system role
			if (role.getId().isSystem())
			{
				continue;
			}

			// Don't create org access for roles which are not defined on system level nor on org's AD_Client_ID level
			final ClientId roleClientId = role.getClientId();
			if (!roleClientId.equals(orgClientId)
					&& !roleClientId.isSystem())
			{
				continue;
			}

			final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());
			permissionsDAO.createOrgAccess(role.getId(), orgId);
			orgAccessCreatedCounter++;
		}
		logger.info("{} - created #{} role org access entries", org, orgAccessCreatedCounter);

		// Reset role permissions, just to make sure we are on the safe side
		// NOTE: not needed shall be triggered automatically
		// if (orgAccessCreatedCounter > 0)
		// {
		// Services.get(IUserRolePermissionsDAO.class).resetCacheAfterTrxCommit();
		// }
	}
}
