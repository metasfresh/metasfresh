package de.metas.security;

import de.metas.menu.AdMenuId;
import de.metas.organization.OrgId;
import de.metas.security.permissions.Constraints;
import de.metas.security.permissions.GenericPermissions;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class Role
{
	@NonNull
	RoleId id;

	@NonNull
	String name;
	String description;

	@Nullable RoleGroup roleGroup;

	@NonNull
	ClientId clientId;

	@NonNull
	OrgId orgId;
	boolean accessAllOrgs;
	boolean useUserOrgAccess;

	UserId supervisorId;

	@NonNull
	TableAccessLevel userLevel;

	@NonNull
	GenericPermissions permissions;

	@NonNull
	Constraints constraints;

	boolean manualMaintainance;

	//
	// Menu
	AdTreeId menuTreeId;
	AdMenuId rootMenuId;

	AdTreeId orgTreeId;

	boolean webuiRole;

	UserId updatedBy;

	public boolean isSystem() {return id.isSystem();}
}
