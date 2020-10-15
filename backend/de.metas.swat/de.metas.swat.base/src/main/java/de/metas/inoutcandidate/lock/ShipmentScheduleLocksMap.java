package de.metas.inoutcandidate.lock;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@EqualsAndHashCode
@ToString(of = "locks")
public class ShipmentScheduleLocksMap
{
	public static ShipmentScheduleLocksMap of(final Collection<ShipmentScheduleLock> locks)
	{
		if (locks.isEmpty())
		{
			return EMPTY;
		}

		return new ShipmentScheduleLocksMap(locks);
	}

	private static final ShipmentScheduleLocksMap EMPTY = new ShipmentScheduleLocksMap();

	private final ImmutableMap<ShipmentScheduleId, ShipmentScheduleLock> locks;

	private ShipmentScheduleLocksMap(final Collection<ShipmentScheduleLock> locks)
	{
		this.locks = Maps.uniqueIndex(locks, ShipmentScheduleLock::getShipmentScheduleId);
	}

	private ShipmentScheduleLocksMap()
	{
		locks = ImmutableMap.of();
	}

	public boolean isEmpty()
	{
		return locks.isEmpty();
	}

	private boolean isNotLocked(final ShipmentScheduleId shipmentScheduleId)
	{
		return getByShipmentScheduleIdOrNull(shipmentScheduleId) == null;
	}

	private ShipmentScheduleLock getByShipmentScheduleIdOrNull(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return locks.get(shipmentScheduleId);
	}

	public boolean isNotLockedBy(@NonNull final UserId userId)
	{
		if (isEmpty())
		{
			return true;
		}

		return !isLockedBy(userId);
	}

	public boolean isLockedBy(@NonNull final UserId userId)
	{
		if (isEmpty())
		{
			return false;
		}

		return locks.values()
				.stream()
				.anyMatch(lock -> lock.isLockedBy(userId));
	}

	public void assertLockedBy(@NonNull final UserId expectedLockedBy)
	{
		if (isEmpty())
		{
			return;
		}

		final List<ShipmentScheduleLock> locksByOtherUser = locks.values()
				.stream()
				.filter(lock -> lock.isNotLockedBy(expectedLockedBy))
				.collect(ImmutableList.toImmutableList());

		if (!locksByOtherUser.isEmpty())
		{
			throw new AdempiereException("Following locks are not owned by " + expectedLockedBy + ": " + locksByOtherUser);
		}
	}

	public void assertLockType(@NonNull final ShipmentScheduleLockType expectedLockType)
	{
		if (isEmpty())
		{
			return;
		}

		final List<ShipmentScheduleLock> locksWithDifferentLockType = locks.values()
				.stream()
				.filter(lock -> !lock.isLockType(expectedLockType))
				.collect(ImmutableList.toImmutableList());

		if (!locksWithDifferentLockType.isEmpty())
		{
			throw new AdempiereException("Following locks are not for " + expectedLockType + ": " + locksWithDifferentLockType);
		}
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIdsNotLocked(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIdsToCheck)
	{
		if (isEmpty())
		{
			return shipmentScheduleIdsToCheck;
		}

		return shipmentScheduleIdsToCheck.stream()
				.filter(this::isNotLocked)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIdsLocked()
	{
		return locks.keySet();
	}
}
