package org.adempiere.ad.security;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.security.permissions.ElementPermissions;
import org.adempiere.ad.security.permissions.OrgPermissions;
import org.adempiere.ad.security.permissions.TableColumnPermissions;
import org.adempiere.ad.security.permissions.TablePermissions;
import org.adempiere.ad.security.permissions.TableRecordPermissions;

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
