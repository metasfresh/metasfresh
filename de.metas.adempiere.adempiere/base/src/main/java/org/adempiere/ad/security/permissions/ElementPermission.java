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


import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import com.google.common.collect.ImmutableSet;

/**
 * Application Dictionary element permission (e.g. Window, Process etc).
 *
 * @author tsa
 *
 */
public final class ElementPermission extends AbstractPermission
{
	public static ElementPermission of(final ElementResource resource, final boolean readWrite)
	{
		final ImmutableSet.Builder<Access> accesses = ImmutableSet.builder();

		// READ access: this is implied if we are here
		accesses.add(Access.READ);

		// WRITE access:
		if (readWrite)
		{
			accesses.add(Access.WRITE);
		}

		return new ElementPermission(resource, accesses.build());
	}
	
	public static final ElementPermission none(final ElementResource resource)
	{
		return new ElementPermission(resource, ImmutableSet.of());
	}
	
	private final ElementResource resource;
	private final ImmutableSet<Access> accesses;

	private ElementPermission(final ElementResource resource, final Set<Access> accesses)
	{
		super();

		Check.assumeNotNull(resource, "resource not null");
		this.resource = resource;

		this.accesses = ImmutableSet.copyOf(accesses);
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(resource)
				.append(accesses)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final ElementPermission other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(resource, other.resource)
				.append(accesses, other.accesses)
				.isEqual();
	}

	@Override
	public ElementResource getResource()
	{
		return resource;
	}

	public int getElementId()
	{
		return resource.getElementId();
	}

	@Override
	public boolean hasAccess(Access access)
	{
		return accesses.contains(access);
	}
	
	public boolean hasReadAccess()
	{
		return accesses.contains(Access.READ);
	}
	
	public boolean hasWriteAccess()
	{
		return accesses.contains(Access.WRITE);
	}

	@Override
	public Permission mergeWith(final Permission permissionFrom)
	{
		final ElementPermission elementPermissionFrom = checkCompatibleAndCast(permissionFrom);

		final ImmutableSet<Access> accesses = ImmutableSet.<Access> builder()
				.addAll(this.accesses)
				.addAll(elementPermissionFrom.accesses)
				.build();

		return new ElementPermission(this.resource, accesses);
	}
}
