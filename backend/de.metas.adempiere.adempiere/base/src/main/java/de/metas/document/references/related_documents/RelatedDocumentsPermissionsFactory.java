/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.related_documents;

import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.element.api.AdWindowId;

@UtilityClass
public class RelatedDocumentsPermissionsFactory
{
	public static RelatedDocumentsPermissions ofRolePermissions(@NonNull final IUserRolePermissions rolePermissions)
	{
		return new RoleBasedRelatedDocumentsPermissions(rolePermissions);
	}

	public static RelatedDocumentsPermissions allowAll()
	{
		return AllowAllRelatedDocumentsPermissions.instance;
	}

	//
	//
	//
	//
	//

	private static class AllowAllRelatedDocumentsPermissions implements RelatedDocumentsPermissions
	{
		private static final AllowAllRelatedDocumentsPermissions instance = new AllowAllRelatedDocumentsPermissions();

		@Override
		public boolean hasReadAccess(final AdWindowId adWindowId)
		{
			return true;
		}

		@Override
		public String addAccessSQL(final String sql, final String tableNameFQ)
		{
			return sql;
		}
	}

	@ToString
	private static class RoleBasedRelatedDocumentsPermissions implements RelatedDocumentsPermissions
	{
		private final IUserRolePermissions rolePermissions;

		private RoleBasedRelatedDocumentsPermissions(@NonNull final IUserRolePermissions rolePermissions)
		{
			this.rolePermissions = rolePermissions;
		}

		@Override
		public boolean hasReadAccess(final AdWindowId adWindowId)
		{
			return rolePermissions.checkWindowPermission(adWindowId).hasReadAccess();
		}

		@Override
		public String addAccessSQL(final String sql, final String tableNameFQ)
		{
			return rolePermissions.addAccessSQL(sql, tableNameFQ, true, Access.READ);
		}
	}
}
