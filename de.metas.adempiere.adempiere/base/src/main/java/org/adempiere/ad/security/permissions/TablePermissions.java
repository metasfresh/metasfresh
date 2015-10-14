package org.adempiere.ad.security.permissions;

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


import org.adempiere.ad.security.permissions.PermissionsBuilder.CollisionPolicy;

public final class TablePermissions extends AbstractPermissions<TablePermission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private TablePermissions(PermissionsBuilder<TablePermission, TablePermissions> builder)
	{
		super(builder);
	}

	public Builder asNewBuilder()
	{
		final Builder builder = builder();
		builder.addPermissions(this, CollisionPolicy.Override);
		return builder;
	}

	@Override
	protected TablePermission noPermission()
	{
		return TablePermission.NONE;
	}

	public final boolean hasAccess(final int adTableId, final Access access)
	{
		final TableResource resource = TableResource.ofAD_Table_ID(adTableId);
		return hasAccess(resource, access);
	}

	/**
	 * Access to Table
	 *
	 * @param AD_Table_ID table
	 * @param ro check read only access otherwise read write access level
	 * @return has RO/RW access to table
	 */
	public boolean isTableAccess(final int AD_Table_ID, final boolean ro)
	{
		return hasAccess(AD_Table_ID, ro ? Access.READ : Access.WRITE);
	}

	/**
	 * Can Report on table
	 *
	 * @param AD_Table_ID table
	 * @return true if access
	 */
	public boolean isCanReport(final int AD_Table_ID)
	{
		return hasAccess(AD_Table_ID, Access.REPORT);
	}

	public boolean isCanExport(final int AD_Table_ID)
	{
		return hasAccess(AD_Table_ID, Access.EXPORT);
	}

	public static class Builder extends PermissionsBuilder<TablePermission, TablePermissions>
	{
		@Override
		protected TablePermissions createPermissionsInstance()
		{
			return new TablePermissions(this);
		}
	}
}
