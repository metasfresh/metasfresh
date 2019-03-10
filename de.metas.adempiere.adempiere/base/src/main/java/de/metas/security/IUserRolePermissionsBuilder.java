package de.metas.security;

import org.adempiere.service.ClientId;

import de.metas.security.permissions.ElementPermissions;
import de.metas.security.permissions.OrgPermissions;
import de.metas.security.permissions.TableColumnPermissions;
import de.metas.security.permissions.TablePermissions;
import de.metas.security.permissions.TableRecordPermissions;
import de.metas.user.UserId;

public interface IUserRolePermissionsBuilder
{
	IUserRolePermissions build();

	IUserRolePermissionsBuilder setRoleId(RoleId adRoleId);
	RoleId getRoleId();

	IUserRolePermissionsBuilder setUserId(UserId adUserId);
	UserId getUserId();

	IUserRolePermissionsBuilder setClientId(ClientId adClientId);
	ClientId getClientId();

	IUserRolePermissionsBuilder setUserLevel(TableAccessLevel userLevel);
	TableAccessLevel getUserLevel();

	OrgPermissions getOrgPermissions();
	TablePermissions getTablePermissions();
	TableColumnPermissions getColumnPermissions();
	TableRecordPermissions getRecordPermissions();
	ElementPermissions getWindowPermissions();
	ElementPermissions getProcessPermissions();
	ElementPermissions getTaskPermissions();
	ElementPermissions getWorkflowPermissions();
	ElementPermissions getFormPermissions();

	IUserRolePermissionsBuilder setFormPermissions(ElementPermissions formAccesses);

	IUserRolePermissionsBuilder includeUserRolePermissions(IUserRolePermissions userRolePermisssions, int seqNo);
}
