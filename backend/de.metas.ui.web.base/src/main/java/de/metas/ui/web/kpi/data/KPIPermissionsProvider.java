package de.metas.ui.web.kpi.data;

import de.metas.common.util.time.SystemTime;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

@RequiredArgsConstructor
public class KPIPermissionsProvider
{
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO;

	public IUserRolePermissions getUserRolePermissions(@NonNull final KPIDataContext context)
	{
		// assume all these context values are set
		// see de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceDescriptor.PERMISSION_REQUIRED_PARAMS
		final RoleId roleId = context.getRoleId();
		final UserId userId = context.getUserId();
		final ClientId clientId = context.getClientId();
		if (roleId == null || userId == null || clientId == null)
		{
			throw new AdempiereException("Cannot extract role permissions from context: " + context);
		}

		return userRolePermissionsDAO.getUserRolePermissions(
				roleId,
				userId,
				clientId,
				SystemTime.asLocalDate());
	}

	public String addAccessSQL(
			@NonNull final String sql,
			@NonNull final String sourceTableName,
			@NonNull final KPIDataContext context)
	{
		final IUserRolePermissions permissions = getUserRolePermissions(context);
		return permissions.addAccessSQL(sql, sourceTableName, true, Access.READ);
	}

}
