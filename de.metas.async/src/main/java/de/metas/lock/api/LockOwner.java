package de.metas.lock.api;

/*
 * #%L
 * de.metas.async
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


import java.util.UUID;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.EqualsBuilder;
import org.compiere.util.HashcodeBuilder;

/**
 * Lock Owner
 *
 * @author tsa
 *
 */
public final class LockOwner
{
	public static enum OwnerType
	{
		None,
		Any,
		RealOwner,
	};

	private static final String OWNERNAME_Null = null;
	public static final LockOwner NONE = new LockOwner(OWNERNAME_Null, OwnerType.None);
	public static final LockOwner ANY = new LockOwner(OWNERNAME_Null, OwnerType.Any);

	public static final LockOwner newOwner()
	{
		final String ownerNamePrefix = null;
		return newOwner(ownerNamePrefix);
	}

	public static final LockOwner newOwner(final String ownerNamePrefix)
	{
		final Object ownerNameUniquePart = UUID.randomUUID();
		return newOwner(ownerNamePrefix, ownerNameUniquePart);
	}

	public static final LockOwner newOwner(final String ownerNamePrefix, final Object ownerNameUniquePart)
	{
		Check.assumeNotNull(ownerNameUniquePart, "ownerNameUniquePart not null");

		final String ownerNamePrefixToUse;
		if (Check.isEmpty(ownerNamePrefix, true))
		{
			ownerNamePrefixToUse = "Unknown";
		}
		else
		{
			ownerNamePrefixToUse = ownerNamePrefix.trim();
		}

		final String ownerName = ownerNamePrefixToUse + "_" + ownerNameUniquePart;
		return new LockOwner(ownerName, OwnerType.RealOwner);
	}

	public static final LockOwner forOwnerName(final String ownerName)
	{
		Check.assumeNotEmpty(ownerName, "ownerName not empty");
		return new LockOwner(ownerName, OwnerType.RealOwner);
	}

	private final String ownerName;
	private final OwnerType ownerType;

	private LockOwner(final String ownerName, final OwnerType ownerType)
	{
		super();
		this.ownerName = ownerName;
		this.ownerType = ownerType;
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
				.append(ownerName)
				.append(ownerType)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final LockOwner other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(ownerName, other.ownerName)
				.append(ownerType, other.ownerType)
				.isEqual();
	}

	public String getOwnerName()
	{
		return ownerName;
	}

	public OwnerType getOwnerType()
	{
		return ownerType;
	}

	public boolean isRealOwner()
	{
		return ownerType == OwnerType.RealOwner;
	}

	public boolean isRealOwnerOrNoOwner()
	{
		return isRealOwner()
				|| isNoOwner();
	}

	public boolean isNoOwner()
	{
		return ownerType == OwnerType.None;
	}

	public boolean isAnyOwner()
	{
		return ownerType == OwnerType.Any;
	}
}
