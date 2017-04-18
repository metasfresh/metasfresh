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

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

/**
 * Lock Owner.
 *
 * @author tsa
 *
 */
@Immutable
@lombok.Value
public final class LockOwner
{
	public static enum OwnerType
	{
		None,
		Any,
		RealOwner,
	};

	private static final String OWNERNAME_Null = "NULL"; // use the string "NULL", because we don't want null to end up in a database.
	public static final LockOwner NONE = new LockOwner(OWNERNAME_Null, OwnerType.None);

	/**
	 * This instance is used to indicate that the lock owner is not a filter criterion
	 */
	public static final LockOwner ANY = new LockOwner(OWNERNAME_Null, OwnerType.Any);

	/**
	 * Creates a new owner whose name starts with <code>"Unknown"</code> and ands with a unique suffix.
	 *
	 * @return
	 */
	public static final LockOwner newOwner()
	{
		final String ownerNamePrefix = null;
		return newOwner(ownerNamePrefix);
	}

	/**
	 * Creates a new owner whose name consists of the given <code>ownerNamePrefix</code> and a unique suffix.
	 *
	 * @param ownerNamePrefix
	 * @return
	 */
	public static final LockOwner newOwner(final String ownerNamePrefix)
	{
		final Object ownerNameUniquePart = UUID.randomUUID();
		return newOwner(ownerNamePrefix, ownerNameUniquePart);
	}

	/**
	 * Creates a new owner whose name consists of the given <code>ownerNamePrefix</code> and the given <code>ownerNameUniquePart</code> which is supposed to be unique.
	 *
	 * @param ownerNamePrefix the owner name's prefix. <code>null</code> is substituted with <code>"Unknown"</code>.
	 * @param ownerNameUniquePart
	 * @return
	 */
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

	/**
	 * Creates a new instance with "static" name.
	 *
	 * @param ownerName the name of the new owner. Other than with the <code>newOwner(...)</code> methods, nothing will be added to it.
	 * @return
	 */
	public static final LockOwner forOwnerName(final String ownerName)
	{
		Check.assumeNotEmpty(ownerName, "ownerName not empty");
		return new LockOwner(ownerName, OwnerType.RealOwner);
	}

	private final String ownerName;
	private final OwnerType ownerType;

	private LockOwner(final String ownerName, final OwnerType ownerType)
	{
		Check.assumeNotNull(ownerName, "Param ownerName is not null");

		this.ownerName = ownerName;
		this.ownerType = ownerType;
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
