package de.metas.security.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_Form;
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.requests.CreateFormAccessRequest;
import de.metas.util.Services;

@Interceptor(I_AD_Form.class)
public class AD_Form
{
	public static final transient AD_Form instance = new AD_Form();

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void addAccessToRolesWithAutomaticMaintenance(final I_AD_Form form)
	{
		final IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);

		final int adFormId = form.getAD_Form_ID();
		for (final Role role : Services.get(IRoleDAO.class).retrieveAllRolesWithAutoMaintenance())
		{
			permissionsDAO.createFormAccess(CreateFormAccessRequest.builder()
					.roleId(role.getId())
					.clientId(role.getClientId())
					.orgId(role.getOrgId())
					.adFormId(adFormId)
					.readWrite(true)
					.build());
		}
	}
}
