package de.metas.security.permissions;

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


import javax.annotation.concurrent.Immutable;

import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;

/**
 * {@link TableColumnResource}'s permission.
 * 
 * @author tsa
 *
 */
@Immutable
public final class TableColumnPermissions extends AbstractPermissions<TableColumnPermission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private TableColumnPermissions(final Builder builder)
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
	protected TableColumnPermission noPermission()
	{
		return TableColumnPermission.NONE;
	}

	public boolean isColumnAccess(final int AD_Table_ID, final int AD_Column_ID, final Access access)
	{
		// If we were asked for a AD_Column_ID <= 0 then return false automatically
		// NOTE: this is the case of GridTable.addField which is checking for access to a generated field (not one that has AD_Column_ID binding).
		if (AD_Column_ID <= 0)
		{
			return false;
		}

		final TableColumnResource resource = TableColumnResource.of(AD_Table_ID, AD_Column_ID);
		return hasAccess(resource, access);
	}

	public static class Builder extends PermissionsBuilder<TableColumnPermission, TableColumnPermissions>
	{

		@Override
		protected TableColumnPermissions createPermissionsInstance()
		{
			return new TableColumnPermissions(this);
		}
	}
}
