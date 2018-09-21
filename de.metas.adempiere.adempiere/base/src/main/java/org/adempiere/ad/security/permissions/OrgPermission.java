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


import java.io.Serializable;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/**
 * Organization permissions.
 */
@Immutable
public final class OrgPermission extends AbstractPermission implements Serializable
{
	public static final OrgPermission ofResourceAndReadOnly(final OrgResource resource, final boolean readOnly)
	{
		final ImmutableSet.Builder<Access> accesses = ImmutableSet.builder();

		// READ access: this is implied if we are here
		accesses.add(Access.READ);

		// WRITE access:
		if (!readOnly)
		{
			accesses.add(Access.WRITE);
		}

		// LOGIN access:
		if (!resource.isSummaryOrganization())
		{
			accesses.add(Access.LOGIN);
		}

		return new OrgPermission(resource, accesses.build());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6649713070452921967L;
	public static final int AD_Client_ID_System = Env.CTXVALUE_AD_Client_ID_System;
	/**
	 * System AD_Org_ID.
	 * i.e. the AD_Org_ID used to store records which don't belong to a particular organization.
	 */
	public static final int AD_Org_ID_System = Env.CTXVALUE_AD_Org_ID_System;

	private final OrgResource resource;
	private final Set<Access> accesses;

	public OrgPermission(final OrgResource resource, final Set<Access> accesses)
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
		final OrgPermission other = EqualsBuilder.getOther(this, obj);
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
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(resource);
		sb.append(", ").append(accesses);
		return sb.toString();
	}	// toString

	@Override
	public boolean hasAccess(Access access)
	{
		return accesses.contains(access);
	}

	public boolean isReadOnly()
	{
		return hasAccess(Access.READ) && !hasAccess(Access.WRITE);
	}

	public boolean isReadWrite()
	{
		return hasAccess(Access.WRITE);
	}

	public int getAD_Client_ID()
	{
		return resource.getAD_Client_ID();
	}

	public int getAD_Org_ID()
	{
		return resource.getAD_Org_ID();
	}

	@Override
	public OrgResource getResource()
	{
		return resource;
	}

	@Override
	public Permission mergeWith(final Permission permissionFrom)
	{
		final OrgPermission orgPermissionFrom = checkCompatibleAndCast(permissionFrom);

		final ImmutableSet<Access> accesses = ImmutableSet.<Access> builder()
				.addAll(this.accesses)
				.addAll(orgPermissionFrom.accesses)
				.build();
		return new OrgPermission(resource, accesses);
	}

	/**
	 * Creates a copy of this permission but it will use the given resource.
	 * 
	 * @param resource
	 * @return copy of this permission but having the given resource
	 */
	public OrgPermission copyWithResource(final OrgResource resource)
	{
		return new OrgPermission(resource, this.accesses);
	}
}	// OrgAccess
