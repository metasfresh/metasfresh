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


import java.util.Collection;

import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;

public abstract class AbstractPermissions<PermissionType extends Permission> implements Permissions<PermissionType>
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	/** {@link Permission}s indexed by {@link Permission#getResource()} */
	private final ImmutableMap<Resource, PermissionType> permissions;

	public AbstractPermissions(final PermissionsBuilder<PermissionType, ? extends Permissions<PermissionType>> builder)
	{
		super();

		this.permissions = builder.getPermissions();
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen

		final String permissionsName = getClass().getSimpleName();
		final Collection<PermissionType> permissionsList = permissions.values();

		final StringBuilder sb = new StringBuilder();
		sb.append(permissionsName).append(": ");
		if (permissionsList.isEmpty())
		{
			sb.append("@NoRestrictions@");
		}
		else
		{
			sb.append(Env.NL);
		}

		Joiner.on(Env.NL)
				.skipNulls()
				.appendTo(sb, permissionsList);

		return sb.toString();
	}

	@Override
	public final int size()
	{
		return permissions.size();
	}

	@Override
	public final Collection<PermissionType> getPermissionsList()
	{
		return permissions.values();
	}

	/**
	 * Gets the "NO permission".
	 * 
	 * It is an instance of PermissionType with all accesses revoked and with a generic {@link Resource}.
	 *
	 * <br/>
	 * <b>NOTE to implementor: it is highly recommended that extended classes to not return <code>null</code> here.</b>
	 * 
	 * @return no permission instance
	 */
	protected PermissionType noPermission()
	{
		return null;
	}

	@Override
	public final Optional<PermissionType> getPermissionIfExists(final Resource resource)
	{
		return Optional.fromNullable(permissions.get(resource));
	}

	@Override
	public final PermissionType getPermissionOrDefault(final Resource resource)
	{
		//
		// Get the permission for given resource
		final Optional<PermissionType> permission = getPermissionIfExists(resource);
		if (permission.isPresent())
		{
			return permission.get();
		}

		//
		// Fallback: get the permission defined for the resource of "no permission", if any
		final PermissionType nonePermission = noPermission();
		if (nonePermission == null)
		{
			return null;
		}
		final Resource defaultResource = nonePermission.getResource();
		return getPermissionIfExists(defaultResource)
				// Fallback: return the "no permission"
				.or(nonePermission);
	}

	@Override
	public final boolean hasPermission(final Permission permission)
	{
		return permissions.values().contains(permission);
	}

	@Override
	public final boolean hasAccess(final Resource resource, final Access access)
	{
		return getPermissionOrDefault(resource).hasAccess(access);
	}

}
