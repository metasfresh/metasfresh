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

import java.util.Set;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.picking.model.I_M_PickingSlot;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

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
	public static PickingSlotRepoQuery of(final int shipmentScheduleId)
	{
		return builder().currentShipmentScheduleId(shipmentScheduleId).shipmentScheduleId(shipmentScheduleId).build();
	}

	public enum PickingCandidate
	{
		/**
		 * Only retrieve picking slot items that have a processed {@link I_M_Picking_Candidate} assigned to their underlying {@link I_M_PickingSlot}.
		 */
		ONLY_PROCESSED,

		/**
		 * Only retrieve picking slot items that have an unprocessed {@link I_M_Picking_Candidate} assigned to their underlying {@link I_M_PickingSlot}.
		 */
		ONLY_UNPROCESSED,

		/**
		 * Retrieve all picking slot items, no matter whether they have a {@link I_M_Picking_Candidate} assigned to their underlying {@link I_M_PickingSlot} or not.
		 */
		ONLY_NOT_CLOSED,

		/**
		 * Retrieve all picking slot items which are not closed (see {@link #ONLY_NOT_CLOSED}) or their picking slot it is NOT a rack system (gh2740).
		 */
		ONLY_NOT_CLOSED_OR_NOT_RACK_SYSTEM,
	}

	int currentShipmentScheduleId;
	ImmutableSet<Integer> shipmentScheduleIds;
	PickingCandidate pickingCandidates;
	String pickingSlotBarcode;

	@Builder
	private PickingSlotRepoQuery(
			final int currentShipmentScheduleId,
			@Singular final Set<Integer> shipmentScheduleIds,
			final PickingCandidate pickingCandidates,
			final String pickingSlotBarcode)
	{
		if (currentShipmentScheduleId > 0 && !shipmentScheduleIds.contains(currentShipmentScheduleId))
		{
			throw new IllegalArgumentException("Current shipment schedule " + currentShipmentScheduleId + " is not in all shipment schedules list: " + shipmentScheduleIds);
		}

		this.currentShipmentScheduleId = currentShipmentScheduleId;
		this.shipmentScheduleIds = ImmutableSet.copyOf(shipmentScheduleIds);
		this.pickingCandidates = pickingCandidates != null ? pickingCandidates : PickingCandidate.ONLY_NOT_CLOSED_OR_NOT_RACK_SYSTEM;
		this.pickingSlotBarcode = pickingSlotBarcode;
	}

}
