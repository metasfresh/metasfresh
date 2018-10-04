package org.adempiere.ad.security.impl;

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

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/**
 * Defines an {@link IUserRolePermissions} which is included in some other {@link IUserRolePermissions}.
 * 
 * Mainly it wraps the included {@link IUserRolePermissions} together with inclusion informations
 * 
 * @author tsa
 *
 */
@Immutable
final class UserRolePermissionsInclude
{
	public static final UserRolePermissionsInclude of(final IUserRolePermissions userRolePermisssions, final int seqNo)
	{
		return new UserRolePermissionsInclude(userRolePermisssions, seqNo);
	}

	private final IUserRolePermissions userRolePermissions;
	private final int seqNo;

	private UserRolePermissionsInclude(IUserRolePermissions userRolePermisssions, int seqNo)
	{
		super();
		Check.assumeNotNull(userRolePermisssions, "userRolePermissions not null");
		this.userRolePermissions = userRolePermisssions;
		this.seqNo = seqNo <= 0 ? -1 : seqNo;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(userRolePermissions)
				.append(seqNo)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final UserRolePermissionsInclude other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.userRolePermissions, other.userRolePermissions)
				.append(this.seqNo, other.seqNo)
				.isEqual();
	}

	/**
	 * @return permissions which were included.
	 */
	public IUserRolePermissions getUserRolePermissions()
	{
		return userRolePermissions;
	}

	/**
	 * @return inclusion sequence number or -1 if not available.
	 */
	public int getSeqNo()
	{
		return seqNo;
	}

	void collectRoleIds(final Set<Integer> adRoleIds)
	{
		adRoleIds.add(userRolePermissions.getAD_Role_ID());
		adRoleIds.addAll(userRolePermissions.getAll_AD_Role_IDs());
	}
}
