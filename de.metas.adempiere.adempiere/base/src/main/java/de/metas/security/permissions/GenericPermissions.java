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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import javax.annotation.concurrent.Immutable;

/**
 * Generic {@link Permissions} collection implementation.
 *
 * It can contain any type of {@link Permission}s.
 *
 * @author tsa
 *
 */
@Immutable
public final class GenericPermissions extends AbstractPermissions<Permission>
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private GenericPermissions(final Builder builder)
	{
		super(builder);
	}

	public Builder toBuilder()
	{
		return new Builder(this);
	}

	public static class Builder extends PermissionsBuilder<Permission, GenericPermissions>
	{
		private Builder()
		{
			super();
		}

		private Builder(final GenericPermissions from)
		{
			super(from.getPermissionsMap());
		}

		@Override
		protected GenericPermissions createPermissionsInstance()
		{
			return new GenericPermissions(this);
		}

		/**
		 * Convenient method add a {@link Permission} only if the <code>condition</code> is evaluated as <code>true</code>.
		 * 
		 * If condition is evaluated as <code>false</code> the permission won't be added.
		 * 
		 * When adding the permission we use {@link CollisionPolicy#Override}.
		 * 
		 * @param condition
		 * @param permission permission to be added.
		 */
		public Builder addPermissionIfCondition(final boolean condition, final Permission permission)
		{
			if (condition)
			{
				addPermission(permission, CollisionPolicy.Override);
			}
			return this;
		}
	}

}
