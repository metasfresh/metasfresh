package de.metas.handlingunits.picking;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Set;

/*
 * #%L
 * de.metas.handlingunits.base
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
public class PickingCandidatesQuery
{
	ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;

	boolean includeShippedHUs;

	boolean onlyNotClosedOrNotRackSystem;
	ImmutableSet<PickingSlotId> onlyPickingSlotIds;
	PickingSlotQRCode pickingSlotQRCode;

	@Builder
	private PickingCandidatesQuery(
			@Singular final Set<ShipmentScheduleId> shipmentScheduleIds,
			final boolean includeShippedHUs,
			final boolean onlyNotClosedOrNotRackSystem,
			@Nullable final Set<PickingSlotId> onlyPickingSlotIds,
			@Nullable final PickingSlotQRCode pickingSlotQRCode)
	{
		this.shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIds);

		this.includeShippedHUs = includeShippedHUs;

		this.onlyNotClosedOrNotRackSystem = onlyNotClosedOrNotRackSystem;
		this.onlyPickingSlotIds = onlyPickingSlotIds != null ? ImmutableSet.copyOf(onlyPickingSlotIds) : ImmutableSet.of();
		this.pickingSlotQRCode = pickingSlotQRCode;
	}

}
