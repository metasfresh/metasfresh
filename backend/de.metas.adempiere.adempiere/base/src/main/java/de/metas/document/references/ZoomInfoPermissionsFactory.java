package de.metas.document.references;

import org.adempiere.ad.element.api.AdWindowId;

import de.metas.security.IUserRolePermissions;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@UtilityClass
public class ZoomInfoPermissionsFactory
{
	public static ZoomInfoPermissions ofRolePermissions(@NonNull final IUserRolePermissions rolePermissions)
	{
		return new RoleBasedZoomInfoPermissions(rolePermissions);
	}

	public static ZoomInfoPermissions allowAll()
	{
		return AllowAllZoomInfoPermissions.instance;
	}

	//
	//
	//
	//
	//

	private static class AllowAllZoomInfoPermissions implements ZoomInfoPermissions
	{
		private static final AllowAllZoomInfoPermissions instance = new AllowAllZoomInfoPermissions();

		@Override
		public boolean hasReadAccess(AdWindowId adWindowId)
		{
			return true;
		}
	}

	@ToString
	private static class RoleBasedZoomInfoPermissions implements ZoomInfoPermissions
	{
		private final IUserRolePermissions rolePermissions;

		private RoleBasedZoomInfoPermissions(@NonNull final IUserRolePermissions rolePermissions)
		{
			this.rolePermissions = rolePermissions;
		}

		@Override
		public boolean hasReadAccess(final AdWindowId adWindowId)
		{
			return rolePermissions.checkWindowPermission(adWindowId).hasReadAccess();
		}
	}
}
