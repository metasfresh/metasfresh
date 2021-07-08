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

package de.metas.security.permissions2;

import de.metas.security.UserRolePermissionsKey;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.Env;

import java.util.Properties;

@UtilityClass
public class PermissionServiceFactories
{
	public PermissionServiceFactory currentContext()
	{
		return ContextPermissionServiceFactory.instance;
	}

	public PermissionServiceFactory singleton(@NonNull final PermissionService permissionService)
	{
		return new SingletonPermissionServiceFactory(permissionService);
	}

	private static final class ContextPermissionServiceFactory implements PermissionServiceFactory
	{
		public static final transient ContextPermissionServiceFactory instance = new ContextPermissionServiceFactory();

		@Override
		public PermissionService createPermissionService()
		{
			final Properties ctx = Env.getCtx();
			return PermissionService.builder()
					.userRolePermissionsKey(UserRolePermissionsKey.fromContext(ctx))
					.defaultOrgId(Env.getOrgId(ctx))
					.build();
		}
	}

	private static final class SingletonPermissionServiceFactory implements PermissionServiceFactory
	{
		private final PermissionService permissionService;

		public SingletonPermissionServiceFactory(@NonNull final PermissionService permissionService)
		{
			this.permissionService = permissionService;
		}

		@Override
		public PermissionService createPermissionService()
		{
			return permissionService;
		}

	}
}
