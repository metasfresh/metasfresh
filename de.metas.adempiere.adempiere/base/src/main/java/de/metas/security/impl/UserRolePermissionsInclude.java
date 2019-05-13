package de.metas.security.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Set;

import de.metas.security.IUserRolePermissions;
import de.metas.security.RoleId;
import lombok.NonNull;
import lombok.Value;

/**
 * Defines an {@link IUserRolePermissions} which is included in some other {@link IUserRolePermissions}.
 * 
 * Mainly it wraps the included {@link IUserRolePermissions} together with inclusion informations
 * 
 * @author tsa
 *
 */
@Value
final class UserRolePermissionsInclude
{
	public static final UserRolePermissionsInclude of(final UserRolePermissions userRolePermisssions, final int seqNo)
	{
		return new UserRolePermissionsInclude(userRolePermisssions, seqNo);
	}

	private final UserRolePermissions userRolePermissions;
	private final int seqNo;

	private UserRolePermissionsInclude(@NonNull final UserRolePermissions userRolePermissions, final int seqNo)
	{
		this.userRolePermissions = userRolePermissions;
		this.seqNo = seqNo <= 0 ? -1 : seqNo;
	}

	void collectRoleIds(final Set<RoleId> adRoleIds)
	{
		adRoleIds.add(userRolePermissions.getRoleId());
		adRoleIds.addAll(userRolePermissions.getAllRoleIds());
	}
}
