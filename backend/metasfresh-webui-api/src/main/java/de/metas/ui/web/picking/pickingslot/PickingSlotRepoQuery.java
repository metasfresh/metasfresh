package de.metas.ui.web.picking.pickingslot;
/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.Set;

/**
 * Used in the repo services, to specify which data we want to be retrieved.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class PickingSlotRepoQuery
{
	@VisibleForTesting
	public static PickingSlotRepoQuery of(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return builder().currentShipmentScheduleId(shipmentScheduleId).shipmentScheduleId(shipmentScheduleId).build();
	}

	@NonNull
	ShipmentScheduleId currentShipmentScheduleId;

	ImmutableSet<ShipmentScheduleId> shipmentScheduleIds;

	boolean onlyNotClosedOrNotRackSystem;

	String pickingSlotBarcode;

	@Builder
	private PickingSlotRepoQuery(
			@NonNull final ShipmentScheduleId currentShipmentScheduleId,
			@Singular final Set<ShipmentScheduleId> shipmentScheduleIds,
			final Boolean onlyNotClosedOrNotRackSystem,
			final String pickingSlotBarcode)
	{
		if (!Check.isEmpty(shipmentScheduleIds) && !shipmentScheduleIds.contains(currentShipmentScheduleId))
		{
			throw new AdempiereException("Current shipment schedule " + currentShipmentScheduleId + " is not in all shipment schedules list: " + shipmentScheduleIds);
		}

		this.currentShipmentScheduleId = currentShipmentScheduleId;
		this.shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIds);
		this.onlyNotClosedOrNotRackSystem = onlyNotClosedOrNotRackSystem != null ? onlyNotClosedOrNotRackSystem : true;
		this.pickingSlotBarcode = pickingSlotBarcode;
	}
}
