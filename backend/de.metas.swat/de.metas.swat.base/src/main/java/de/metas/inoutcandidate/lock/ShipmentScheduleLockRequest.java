package de.metas.inoutcandidate.lock;

import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class ShipmentScheduleLockRequest
{
	ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;
	UserId lockedBy;
	ShipmentScheduleLockType lockType;

	@Builder(toBuilder = true)
	private ShipmentScheduleLockRequest(
			@NonNull @Singular final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final UserId lockedBy,
			@NonNull final ShipmentScheduleLockType lockType)
	{
		Check.assumeNotEmpty(shipmentScheduleIds, "shipmentScheduleIds is not empty");

		this.shipmentScheduleIds = shipmentScheduleIds;
		this.lockedBy = lockedBy;
		this.lockType = lockType;
	}

	public ShipmentScheduleLockRequest withShipmentScheduleIds(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (Objects.equals(this.shipmentScheduleIds, shipmentScheduleIds))
		{
			return this;
		}

		return toBuilder().shipmentScheduleIds(shipmentScheduleIds).build();
	}
}
