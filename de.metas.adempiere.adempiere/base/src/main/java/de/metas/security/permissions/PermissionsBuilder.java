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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import lombok.NonNull;

/**
 * Base class for implementing {@link Permissions} builders.
 *
 * @author tsa
 *
 * @param <PermissionType>
 * @param <PermissionsType>
 */
public abstract class PermissionsBuilder<PermissionType extends Permission, PermissionsType extends Permissions<PermissionType>>
{
	/** What shall happen when you are adding a permission which already exists but maybe with different permissions */
	public static enum CollisionPolicy
	{
		/** Override existing permission */
		Override,
		/** Merge the permission INTO existing permission */
		Merge,
		/** Fail by throwing an exception */
		Fail,
		/** Skip */
		Skip,
	}

	private final Map<Resource, PermissionType> _permissions;

	public PermissionsBuilder()
	{
		this(ImmutableMap.of());
	}

	public PermissionsBuilder(@NonNull final Map<Resource, PermissionType> permissions)
	{
		_permissions = new LinkedHashMap<>(permissions);
	}

	public final PermissionsType build()
	{
		buildPrepare();
		return createPermissionsInstance();
	}

	protected void buildPrepare()
	{
		// nothing at this level
	}

	protected abstract PermissionsType createPermissionsInstance();

	public final ImmutableMap<Resource, PermissionType> getPermissions()
	{
		return ImmutableMap.copyOf(_permissions);
	}

	protected final Map<Resource, PermissionType> getPermissionsInternalMap()
	{
		return _permissions;
	}

	public final PermissionsBuilder<PermissionType, PermissionsType> addPermission(final PermissionType permission)
	{
		return addPermission(permission, CollisionPolicy.Fail);
	}

	public final PermissionsBuilder<PermissionType, PermissionsType> addPermission(@NonNull final PermissionType permission, @NonNull final CollisionPolicy collisionPolicy)
	{
		assertValidPermissionToAdd(permission);

		final Map<Resource, PermissionType> permissions = getPermissionsInternalMap();

		final Resource resource = permission.getResource();
		final PermissionType permissionExisting = permissions.get(resource);

		if (permissionExisting == null)
		{
			permissions.put(resource, permission);
			return this;
		}

		final boolean samePermissionAlreadyExists = Objects.equals(permissionExisting, permission);

		if (collisionPolicy == CollisionPolicy.Override)
		{
			permissions.put(resource, permission);
		}
		else if (collisionPolicy == CollisionPolicy.Merge)
		{
			if (!samePermissionAlreadyExists)
			{
				@SuppressWarnings("unchecked")
				final PermissionType permissionMerged = (PermissionType)permissionExisting.mergeWith(permission);
				permissions.put(resource, permissionMerged);
			}
		}
		else if (collisionPolicy == CollisionPolicy.Fail)
		{
			if (!samePermissionAlreadyExists)
			{
				throw new AdempiereException("Found another permission for same resource but with different accesses: " + permissionExisting + ", " + permission);
			}
			// NOTE: if they are equals, do nothing
		}
		else if (collisionPolicy == CollisionPolicy.Skip)
		{
			// do nothing
		}
		else
		{
			throw new AdempiereException("Unknown CollisionPolicy: " + collisionPolicy);
		}

		return this;
	}

	protected void assertValidPermissionToAdd(final PermissionType permission)
	{
		// nothing at this level
	}

	public final PermissionsBuilder<PermissionType, PermissionsType> addPermissions(final Iterable<PermissionType> permissions, final CollisionPolicy collisionPolicy)
	{
		for (final PermissionType perm : permissions)
		{
			addPermission(perm, collisionPolicy);
		}

		return this;
	}

	public final PermissionsBuilder<PermissionType, PermissionsType> addPermissions(final PermissionsType permissions, final CollisionPolicy collisionPolicy)
	{
		addPermissions(permissions.getPermissionsList(), collisionPolicy);
		return this;
	}

	public boolean hasPermission(@NonNull final PermissionType permission)
	{
		final Map<Resource, PermissionType> permissions = getPermissionsInternalMap();

		final Resource resource = permission.getResource();
		final PermissionType permissionExisting = permissions.get(resource);
		if (permissionExisting == null)
		{
			return false;
		}

		return Objects.equals(permission, permissionExisting);
	}

	public final PermissionsBuilder<PermissionType, PermissionsType> removePermission(@NonNull final PermissionType permission)
	{
		_permissions.remove(permission.getResource(), permission);
		return this;
	}
}
