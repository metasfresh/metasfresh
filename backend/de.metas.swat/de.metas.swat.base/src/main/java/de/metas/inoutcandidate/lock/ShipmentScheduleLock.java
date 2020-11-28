package de.metas.inoutcandidate.lock;

import java.time.ZonedDateTime;
import java.util.Objects;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class ShipmentScheduleLock
{
	@NonNull
	ShipmentScheduleId shipmentScheduleId;
	@NonNull
	UserId lockedBy;
	@NonNull
	ShipmentScheduleLockType lockType;
	@NonNull
	ZonedDateTime created;

	public boolean isLockedBy(@NonNull final UserId userId)
	{
		return UserId.equals(this.lockedBy, userId);
	}

	public boolean isNotLockedBy(@NonNull final UserId userId)
	{
		return !isLockedBy(userId);
	}

	public boolean isLockType(@NonNull final ShipmentScheduleLockType lockType)
	{
		return Objects.equals(this.lockType, lockType);
	}
}
