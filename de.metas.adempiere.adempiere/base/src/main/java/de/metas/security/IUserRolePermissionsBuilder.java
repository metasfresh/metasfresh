package de.metas.security;

import de.metas.security.permissions.ElementPermissions;
import de.metas.security.permissions.OrgPermissions;
import de.metas.security.permissions.TableColumnPermissions;
import de.metas.security.permissions.TablePermissions;
import de.metas.security.permissions.TableRecordPermissions;

public interface IUserRolePermissionsBuilder
{
	IUserRolePermissions build();

	IUserRolePermissionsBuilder setAD_Role_ID(int adRoleId);
	int getAD_Role_ID();

	IUserRolePermissionsBuilder setAD_User_ID(int adUserId);
	int getAD_User_ID();

	IUserRolePermissionsBuilder setAD_Client_ID(int adClientId);
	int getAD_Client_ID();

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
